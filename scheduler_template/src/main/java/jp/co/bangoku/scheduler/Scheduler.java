package jp.co.bangoku.scheduler;

import jp.co.whizz_tech.ocean.cui.CuiAppManager;

/**
 * �X�P�W���[�����C���imain���\�b�h�j
 * @author bangoku
 * @date 2016/05/16
 */
public class Scheduler {
	public static void main(String[] args) {
		try {
			CuiAppManager.initialize();
			System.out.println("�X�P�W���[���Ǘ��A�v���P�[�V����");
			CuiAppManager.run();
		} catch (Exception e) {
			System.err.println("�V�X�e���G���[���������܂����B");
			e.printStackTrace();
			System.exit(1);
		}
	}
}