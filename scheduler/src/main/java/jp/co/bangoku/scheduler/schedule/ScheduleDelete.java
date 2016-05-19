package jp.co.bangoku.scheduler.schedule;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jp.co.dhw.osaka.scheduler.dao.ScheduleDao;
import jp.co.dhw.osaka.scheduler.entity.Schedule;
import jp.co.dhw.osaka.scheduler.util.DBUtil;
import jp.co.whizz_tech.ocean.config.ParameterConfig;
import jp.co.whizz_tech.ocean.cui.CuiAppBase;
import jp.co.whizz_tech.ocean.cui.CuiAppManager;

/**
 * スケジュール削除
 * 
 * @author bangoku
 * @date 2016/05/19
 */
public class ScheduleDelete extends CuiAppBase {

	@Override
	public void execute() {
		Connection con = null;

		System.out.println("\nスケジュール削除を開始します...");

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
				String date = inputDate("削除したい日付を入力してください。", "入力した日付はパターンと一致しませんので、再入力してください。", "yyyy-MM-dd");
				java.util.Date scheduleDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
				schedule.setScheduleDate(new java.sql.Date(scheduleDate.getTime()));

				// 時間(24時制)入力
				String time = inputDate("スケジュールの時間を入力してください。", "入力したスケジュール時間はパターンと一致しませんので、再入力してください。", "HH:mm");
				Date scheduleTime = new SimpleDateFormat("HH:mm").parse(time);
				schedule.setScheduleTime(new java.sql.Time(scheduleTime.getTime()));

				SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy年MM月dd日");
				SimpleDateFormat timeSdf = new SimpleDateFormat("HH時mm分");

				// 削除を行う前に確認します。
				if (inputBoolean("本当に削除してもよろしいですか?")) {
					// SQL実行
					int count = scheduleDao.delete(schedule);

					// 結果出力
					if (count > 0) {
						System.out.println(dateSdf.format(schedule.getScheduleDate())
								+ timeSdf.format("【" + schedule.getScheduleTime()) + "のスケジュールを削除しました。】");
					} else {
						System.err.println("【エラー】指定した日付と時間に対して該当のデータが存在しません。");
					}

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