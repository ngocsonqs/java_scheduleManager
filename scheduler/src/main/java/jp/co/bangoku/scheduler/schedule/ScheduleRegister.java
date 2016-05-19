package jp.co.bangoku.scheduler.schedule;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

import jp.co.dhw.osaka.scheduler.dao.ScheduleDao;
import jp.co.dhw.osaka.scheduler.entity.Schedule;
import jp.co.dhw.osaka.scheduler.util.DBUtil;
import jp.co.whizz_tech.ocean.config.ParameterConfig;
import jp.co.whizz_tech.ocean.cui.CuiAppBase;
import jp.co.whizz_tech.ocean.cui.CuiAppManager;

/**
 * スケジュール登録
 * 
 * @author bangoku
 * @date 2016/05/19
 */
public class ScheduleRegister extends CuiAppBase {

	@Override
	public void execute() {

		Connection con = null;
		System.out.println("\nスケジュール登録を開始します...");

		try {
			// データベース接続
			con = DBUtil.getConnection();

			// DAO生成
			ScheduleDao scheduleDao = new ScheduleDao(con);

			// Schedule生成
			Schedule schedule = new Schedule();

			// ログイン済かどうかをチェックします。
			ParameterConfig config = CuiAppManager.getParameterConfig();
			if (config.getParameter("username") != null) {

				// ログイン済のユーザ名からscheduleインスタンスのユーザ名を設定します。
				schedule.setUsername(config.getParameter("username"));

				// 現在日付を取得します。
				Date nowDate = new Date();

				// 日より下の項目を切り捨てた時刻を取得
				Date dateTruncate = DateUtils.truncate(nowDate, Calendar.DAY_OF_MONTH);

				// 日付（YYYY-MM-DD）入力 (入力させた日付と現在の日付より遅れるかどうかもチェックします。)
				String date = inputDate("スケジュールの日付を入力してください。", "入力したスケジュール日付はパターンと一致しませんので、再入力してください。", "yyyy-MM-dd");
				java.util.Date scheduleDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);

				// 入力させた日付と現在日付を比較して、入力した日付は現在日付より遅れている場合 -->
				// 過去の日付はスケジュールが登録できませんというエラーメッセージ表示
				int diff = scheduleDate.compareTo(dateTruncate);
				if (diff < 0) {
					System.err.println("【エラー】 入力した日付は現在日付より遅れていますので、再入力してください。");
				} else {

					schedule.setScheduleDate(new java.sql.Date(scheduleDate.getTime()));

					// 時間(24時制)入力
					String time = inputDate("スケジュールの時間を入力してください。", "入力したスケジュール時間はパターンと一致しませんので、再入力してください。", "HH:mm");

					Date scheduleTime = new SimpleDateFormat("HH:mm").parse(time);
					schedule.setScheduleTime(new java.sql.Time(scheduleTime.getTime()));

					// 件名入力
					String title = inputStr("スケジュールのタイトルを入力してください。", "名前の長さは範囲を超えていますので、再入力してください。", 128);
					schedule.setTitle(title);

					// 内容入力 (省略可)
					if (inputBoolean("スケジュールの内容が必要ですか?")) {
						String content = inputStr("スケジュールの内容を入力してください。");
						schedule.setContent(content);
					}

					// 登録日時
					java.util.Date created = new java.util.Date();
					schedule.setInsertDatetime(new java.sql.Timestamp(created.getTime()));

					int count = scheduleDao.insert(schedule);
					System.out.println(count + "件登録しました。");
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
		} finally {
			DBUtil.close(con);
		}
	}

}