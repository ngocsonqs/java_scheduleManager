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
 * スケジュール更新
 * 
 * @author bangoku
 * @date 2016/05/19
 */
public class ScheduleUpdate extends CuiAppBase {

	@Override
	public void execute() {
		Connection con = null;

		System.out.println("\nスケジュール更新を開始します...");

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
				String date = inputDate("更新したい日付を入力してください。", "入力した日付はパターンと一致しませんので、再入力してください。", "yyyy-MM-dd");
				java.util.Date scheduleDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
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

				// 全部入力済だったら、見やすい形で表示して確認します。
				SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy年MM月dd日");
				SimpleDateFormat timeSdf = new SimpleDateFormat("HH時mm分");
				System.out.println(
						"\n\t≪ ★★★" + schedule.getUsername() + "さんは " + dateSdf.format(schedule.getScheduleDate())
								+ timeSdf.format(schedule.getScheduleTime()) + "に更新したい内容は以下の通りです。もう一度確認してください。 ★★★≫");
				System.out.println("------------------------------");
				System.out.println("タイトル\t: " + schedule.getTitle());
				System.out.println("内容\t: " + schedule.getContent());
				System.out.println("------------------------------\n");

				if (inputBoolean("以上の内容で本当に更新してもよろしいですか?")) {

					// SQL実行
					int count = scheduleDao.update(schedule);

					// 結果出力
					if (count > 0) {
						System.out.println(count + "件更新しました。");
					} else {
						System.err.println("【エラー】スケジュール更新が失敗しました。");
					}

				}
			} else {
				System.err.println("【エラー】　未ログインの状態でスケジュール更新ができません。");
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