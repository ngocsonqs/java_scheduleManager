package jp.co.bangoku.scheduler.menu;

import jp.co.whizz_tech.ocean.config.ParameterConfig;
import jp.co.whizz_tech.ocean.cui.CuiAppManager;
import jp.co.whizz_tech.ocean.cui.CuiMenu;

/**
 * メインメニュー
 * 
 * @author bangoku
 * @date 2016/05/16
 */
public class MainMenu extends CuiMenu {
	/**
	 * メインメニューの切り替え
	 */
	public void startup() {
		ParameterConfig config = CuiAppManager.getParameterConfig();
		String username = config.getParameter("username");
		String admin_flg = config.getParameter("admin_flg");

		// ログイン済の場合
		if (username != null && admin_flg != null) {
			// メインメニューの【ログイン】を隠れる
			setItemEnabled(0, false);

			// メインメニューの【スケジュール管理】を表示します。
			setItemEnabled(1, true);

			// メインメニューの【ユーザ管理】を表示します。
			setItemEnabled(2, true);

			// メインメニューの【ログアウト】を表示します。
			setItemEnabled(4, true);

			if (admin_flg.equals("User")) {
				// メインメニューの【システム管理】を隠れます。
				setItemEnabled(3, false);
			} else if (admin_flg.equals("Admin")) {
				// メインメニューの【システム管理】を表示します。
				setItemEnabled(3, true);
			}
		} else {
			// 未ログインの場合、【ログイン】を表示します。
			setItemEnabled(0, true);
			
			// 未ログインの場合、【スケジュール管理】を隠れる
			setItemEnabled(1, false);

			// 未ログインの場合は、【システム管理】を隠れる
			setItemEnabled(3, false);

			// 未ログインの場合は、【ログアウト】を隠れる
			setItemEnabled(4, false);
		}
	}
}
