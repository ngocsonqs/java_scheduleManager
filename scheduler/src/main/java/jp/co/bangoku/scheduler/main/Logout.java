package jp.co.bangoku.scheduler.main;

import jp.co.whizz_tech.ocean.config.ParameterConfig;
import jp.co.whizz_tech.ocean.cui.CuiAppBase;
import jp.co.whizz_tech.ocean.cui.CuiAppManager;

/**
 * ログアウトのクラスです。
 * 
 * @author bangoku
 * @date 2016/05/16
 */
public class Logout extends CuiAppBase {

	/**
	 * アプリケーションを実行します。
	 */
	@Override
	public void execute() {
		ParameterConfig config = CuiAppManager.getParameterConfig();
		 
		// ユーザ名のパラメータの値をnullにする。
		if (config.getParameter("username") != null) {			
			config.addParameter("username", null);
			System.out.println("【システムをログアウトしました】");
		}
	}
}
