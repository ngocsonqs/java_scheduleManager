package jp.co.bangoku.scheduler;

import jp.co.bangoku.scheduler.main.Login;
import jp.co.whizz_tech.ocean.config.ParameterConfig;
import jp.co.whizz_tech.ocean.cui.CuiAppManager;

/**
 * ログインユーザ保持用
 * 
 * @author bangoku
 * @date 2016/05/16
 */
public class LoginUser {
	
	/**
	 * 認証したユーザからセッション生成してログインユーザ保持します。
	 * @param username ユーザ名
	 * @param admin 管理かの場合は {@link Login#ADMIN}, 一般ユーザの場合は{@link Login#USER}
	 */
	public static void setSessionLogin(String username, String authAdmin) {
		ParameterConfig config = CuiAppManager.getParameterConfig();
		config.addParameter("username", username);
		config.addParameter("admin_flg", authAdmin);
	}
	
}
