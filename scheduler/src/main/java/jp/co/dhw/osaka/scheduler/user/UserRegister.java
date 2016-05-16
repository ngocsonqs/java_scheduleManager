package jp.co.dhw.osaka.scheduler.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import jp.co.whizz_tech.ocean.cui.CuiAppBase;

/**
 * ���[�U�o�^���s���N���X�ł��B
 * @author bangoku
 * @date 2016/05/16
 */
public class UserRegister extends CuiAppBase {

	/**
	 * ���[�U�o�^���s��
	 */
	@Override
	public void execute() {
		// ���[�U��
		String username = inputStr("���[�U������͂��Ă��������B", "���͉\�ȃ��[�U���̍ő咷�𒴂��Ă��܂��B", 16);
		System.out.println(username);
		
		// �p�X���[�h
		String password = inputStr("�p�X���[�h����͂��Ă��������B", "���͉\�ȃp�X���[�h�̍ő咷�𒴂��Ă��܂��B", 16);
		System.out.println(password);
		
		// ����
		String name     = inputStr("���O����͂��Ă��������B", "���͉\�Ȗ��O�̍ő咷�𒴂��Ă��܂��B", 32);
		System.out.println(name);
		
		// �a����
		String birthday = inputDate("���N��������͂��Ă�������", "���N����������������܂���B", "yyyyMMdd");
//		try {
//			java.util.Date bday = new SimpleDateFormat("yyyyMMdd").parse(birthday);
//		} catch (ParseException e) {
//			System.err.println("���N�����̃p�[�X�Ɏ��s���܂����B");
//			e.printStackTrace();
//		}
		System.out.println(birthday);
		
	}
}