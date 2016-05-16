package jp.co.dhw.osaka.scheduler.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import jp.co.whizz_tech.ocean.cui.CuiAppBase;

/**
 * ユーザ登録を行うクラスです。
 * @author bangoku
 * @date 2016/05/16
 */
public class UserRegister extends CuiAppBase {

	/**
	 * ユーザ登録を行う
	 */
	@Override
	public void execute() {
		// ユーザ名
		String username = inputStr("ユーザ名を入力してください。", "入力可能なユーザ名の最大長を超えています。", 16);
		System.out.println(username);
		
		// パスワード
		String password = inputStr("パスワードを入力してください。", "入力可能なパスワードの最大長を超えています。", 16);
		System.out.println(password);
		
		// 氏名
		String name     = inputStr("名前を入力してください。", "入力可能な名前の最大長を超えています。", 32);
		System.out.println(name);
		
		// 誕生日
		String birthday = inputDate("生年月日を入力してください", "生年月日が正しくありません。", "yyyyMMdd");
//		try {
//			java.util.Date bday = new SimpleDateFormat("yyyyMMdd").parse(birthday);
//		} catch (ParseException e) {
//			System.err.println("生年月日のパースに失敗しました。");
//			e.printStackTrace();
//		}
		System.out.println(birthday);
		
	}
}
