package jp.co.bangoku.scheduler.main;

import jp.co.whizz_tech.ocean.cui.CuiAppBase;
import jp.co.whizz_tech.ocean.cui.CuiAppManager;

/**
 * �A�v���P�[�V�����̏I�����s���N���X�ł��B
 * @author bangoku
 * @date 2016/05/16
 */
public class ExitApp extends CuiAppBase{
	/**
	 * �A�v���P�[�V���������s���܂��B
	 */
	@Override
	public void execute() {
		if (inputBoolean("�I�����Ă���낵���ł���?")) {
			System.out.println("\n�I�����܂�...");
			CuiAppManager.exit();
		}
	}
}