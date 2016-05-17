package jp.co.bangoku.scheduler.main;

import jp.co.whizz_tech.ocean.cui.CuiAppBase;
import jp.co.whizz_tech.ocean.cui.CuiAppManager;

/**
 * アプリケーソンの終了を行うクラスです。
 * @author bangoku
 * @date 2016/05/16
 */
public class ExitApp extends CuiAppBase{
	/**
	 * アプリケーションを実行します。
	 */
	@Override
	public void execute() {
		if (inputBoolean("終了してもよろしですか?")) {
			System.out.println("\n終了します...");
			CuiAppManager.exit();
		}
	}
}
