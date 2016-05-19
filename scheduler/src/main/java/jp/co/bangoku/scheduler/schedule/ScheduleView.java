package jp.co.bangoku.scheduler.schedule;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import jp.co.dhw.osaka.scheduler.dao.ScheduleDao;
import jp.co.dhw.osaka.scheduler.entity.Schedule;
import jp.co.dhw.osaka.scheduler.util.DBUtil;
import jp.co.whizz_tech.ocean.config.ParameterConfig;
import jp.co.whizz_tech.ocean.cui.CuiAppBase;
import jp.co.whizz_tech.ocean.cui.CuiAppManager;

/**
 * スケジュール確認
 * 
 * @author bangoku
 * @date 2016/05/19
 */
public class ScheduleView extends CuiAppBase {

	@Override
	public void execute() {
		Connection con = null;

		System.out.println("\nスケジュール確認を開始します...");

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
				String date = inputDate("スケジュールの日付を入力してください。", "入力したスケジュール日付はパターンと一致しませんので、再入力してください。", "yyyy-MM-dd");
				java.util.Date scheduleDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
				schedule.setScheduleDate(new java.sql.Date(scheduleDate.getTime()));

				// スケジュール時間のソート順
				boolean sortFlg = inputBoolean("スケジュール時間は昇順で表示してもよろしいですか?");

				// SQL実行
				List<Schedule> list = scheduleDao.viewByDate(schedule, sortFlg);

				// 結果出力
				SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy年MM月dd日");
				SimpleDateFormat timeSdf = new SimpleDateFormat("HH時mm分");
				if (list.size() > 0) {
					System.out.println("\n\t≪ ★★★" + dateSdf.format(schedule.getScheduleDate()) + "に "
							+ schedule.getUsername() + "さんのスケジュールは以下の通りです。 ★★★≫");
					for (Schedule resultSchedule : list) {
						System.out.println("------------------------------");
						System.out.println("タイトル\t: " + resultSchedule.getTitle());
						System.out.println("時間\t： " + timeSdf.format(resultSchedule.getScheduleTime()));
						System.out.println("内容\t: " + resultSchedule.getContent());
						System.out.println("------------------------------\n");
					}
				} else {
					System.out.println("\n ≪" + (dateSdf.format(schedule.getScheduleDate()) + "に"
							+ schedule.getUsername() + "さんのスケジュールがありません。≫"));
				}
			} else {
				System.err.println("【エラー】　未ログインの状態でスケジュール登録ができません。");
			}
		} catch (ClassNotFoundException e) {
			System.err.println("JDBCドライバがロードできませんでした。");
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("SQL例外が発生しました。");
			e.printStackTrace();
		} catch (ParseException e) {
			System.err.println("スケジュールの日付のバースに失敗しました。");
			e.printStackTrace();
		}
	}
}