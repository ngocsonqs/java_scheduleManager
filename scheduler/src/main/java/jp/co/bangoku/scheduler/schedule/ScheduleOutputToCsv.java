package jp.co.bangoku.scheduler.schedule;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import jp.co.dhw.osaka.scheduler.dao.ScheduleDao;
import jp.co.dhw.osaka.scheduler.entity.Schedule;
import jp.co.dhw.osaka.scheduler.util.DBUtil;
import jp.co.whizz_tech.ocean.config.ParameterConfig;
import jp.co.whizz_tech.ocean.cui.CuiAppBase;
import jp.co.whizz_tech.ocean.cui.CuiAppManager;

/**
 * スケジュール出力
 * 
 * @author bangoku
 * @date 2016/05/19
 */
public class ScheduleOutputToCsv extends CuiAppBase {

	static final String FILE_NAME = "E:\\git_data\\java\\java_scheduleManager\\scheduler\\csv\\schedule.csv";

	@Override
	public void execute() {
		Connection con = null;
		System.out.println("\nスケジュールをCSV形式で出力する機能を開始します...");

		// CSVデータファイル
		try {
			// データベース接続
			con = DBUtil.getConnection();

			// ScheduleDao生成
			ScheduleDao scheduleDao = new ScheduleDao(con);

			// Schedule生成
			Schedule schedule = new Schedule();

			// ログイン済かどうかをチェックします。
			ParameterConfig config = CuiAppManager.getParameterConfig();
			if (config.getParameter("username") != null) {
				// ログイン済のユーザ名からscheduleインスタンスのユーザ名を設定します。
				schedule.setUsername(config.getParameter("username"));

				// 日付（YYYY-MM-DD）入力 (入力させた日付と現在の日付より遅れるかどうかもチェックします。)
				String date = inputDate("スケジュールの日付を入力してください。", "入力したスケジュール日付はパターンと一致しませんので、再入力してください。", "yyyy-MM");
				schedule.setScheduleDateStr(date);

				// スケジュール時間のソート順
				boolean sortFlg = inputBoolean("スケジュール時間は昇順で表示してもよろしいですか?");

				// SQL実行
				List<Schedule> list = scheduleDao.findByYearMonth(schedule, sortFlg);

				if (list.size() > 0) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

					for (Schedule resultSchedule : list) {
						System.out.println("------------------------------");
						System.out.println(resultSchedule.getScheduleDate());
						System.out.println(resultSchedule.getScheduleTime());
						System.out.println(resultSchedule.getTitle());
						System.out.println(resultSchedule.getContent());
						System.out.println(sdf.format(resultSchedule.getInsertDatetime()));

						if (resultSchedule.getUpdateDatetime() == null) {
							System.out.println("0000-00-00 00:00:00");
						} else {
							System.out.println(sdf.format(resultSchedule.getUpdateDatetime()));
						}

						System.out.println("------------------------------");
					}

					// CSV形式で出力
					// FileWriter file = new FileWriter(FILE_NAME, false);
					File file = new File(FILE_NAME);
					PrintWriter printWriter = new PrintWriter(
							new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8")));

					String data = "";

					// データを書き込む
					for (Schedule resultSchedule : list) {
						String dateData = resultSchedule.getScheduleDate() + "";
						String timeData = resultSchedule.getScheduleTime() + "";
						String titleData = resultSchedule.getTitle();
						String content = resultSchedule.getContent();
						String insertDateTime = sdf.format(resultSchedule.getInsertDatetime());
						String updateDateTime = "";
						if (resultSchedule.getUpdateDatetime() == null) {
							updateDateTime = "0000-00-00 00:00:00";
						} else {
							updateDateTime = sdf.format(resultSchedule.getUpdateDatetime());
						}
						data = dateData + "," + timeData + "," + titleData + "," + content + "," + insertDateTime + ","
								+ updateDateTime;

						// 新しいデータ行の追加
						printWriter.println(data);
					}
					printWriter.close();

				} else {
					System.err.println("【エラー】　未ログインの状態でスケジュール登録ができません。");
				}
			} else {
				System.err.println("【エラー】　未ログインの状態でスケジュールをCSV形式で出力できません。");
			}

		} catch (ClassNotFoundException e) {
			System.err.println("JDBCドライバがロードできませんでした。");
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("SQL例外が発生しました。");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("BufferedWriterオブジェクトのクローズ時の例外補足");
			e.printStackTrace();
		}
	}
}