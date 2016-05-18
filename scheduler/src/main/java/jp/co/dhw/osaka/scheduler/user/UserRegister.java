package jp.co.dhw.osaka.scheduler.user;
 
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
 
import jp.co.dhw.osaka.scheduler.dao.UserDao;
import jp.co.dhw.osaka.scheduler.entity.User;
import jp.co.dhw.osaka.scheduler.util.DBUtil;
import jp.co.whizz_tech.ocean.cui.CuiAppBase;
 
/**
 * ユーザ登録
 * 
 * @author ngocsonqs
 * 
 */
public class UserRegister extends CuiAppBase {
 
	@Override
	public void execute() {
		Connection con = null;
		System.out.println("\n登録を開始します...");
		try {
			// データベースに接続
			con = DBUtil.getConnection();
 
			// DAO生成
			UserDao userDao = new UserDao(con);
 
			// User生成
			User user = new User();
 
			// ユーザ名入力
			String username = inputStr("ユーザ名を入力してください。", "ユーザ名の長さは範囲を超えていますので、再入力してください。", 16);
 
			// 入力させたユーザ名は既に登録したかどうかをチェックします。
			if (userDao.findByUsername(username) != null) {
				System.err.println("既に" + username + "さんは登録済みです。もう一度別のユーザ名で登録してください。");
			} else {
				user.setUsername(username);
 
				// パスワード入力
				String password = inputStr("パスワードを入力してください。", "パスワードの長さは範囲を超えていますので、再入力してください。", 16);
				user.setPassword(password);
 
				// 確認用のパスワード入力
				String rePassword = inputStr("パスワードを入力してください。【確認用】");
				if (!rePassword.equals(password)) {
					System.err.println("パスワードが一致しませんので、再入力してください。");
				} else {
					// 名前入力
					String name = inputStr("名前を入力してください。", "名前の長さは範囲を超えていますので、再入力してください。", 32);
					user.setName(name);
 
					// 生年月日入力
					String bday = inputDate("生年月日を入力してください。", "入力した生年月日はパターンと一致しませんので、再入力してください。", "yyyyMMdd");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					java.util.Date birthday = sdf.parse(bday);
					user.setBirthday(new java.sql.Date(birthday.getTime()));
 
					// 管理者かどうか
					int adminFlag = (inputBoolean("あなたは一般なユーザですか? (y/n)")) ? 1 : 0;
					user.setAdminFlag(adminFlag);
 
					// 登録日時
					java.util.Date created = new java.util.Date();
					user.setCreated(new java.sql.Timestamp(created.getTime()));
 
					// insert SQLを実行
					int count = userDao.insert(user);
 
					// 結果出力
					System.out.println(count + "件登録しました。");
				}
			}
		} catch (ClassNotFoundException e) {
			System.err.println("JDBCドライバがロードできませんでした。");
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("SQL例外が発生しました。");
			e.printStackTrace();
		} catch (ParseException e) {
			System.err.println("誕生日のバースに失敗しました。");
			e.printStackTrace();
		} finally {
			DBUtil.close(con);
		}
	}
 
}




/******************************************************************************/
//package jp.co.dhw.osaka.scheduler.user;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//
//import jp.co.dhw.osaka.scheduler.dao.UserDao;
//import jp.co.dhw.osaka.scheduler.entity.User;
//import jp.co.dhw.osaka.scheduler.util.DBUtil;
//import jp.co.whizz_tech.ocean.cui.CuiAppBase;
//
///**
// * ユーザ登録
// * 
// * @author bangoku
// * @date 2016/05/17
// */
//public class UserRegister extends CuiAppBase {
//
//	@Override
//	public void execute() {
//		Connection con = null;
//		System.out.println("登録を開始します。");
//		try {
//			// データベースに接続
//			con = DBUtil.getConnection();
//
//			// DAO生成
//			UserDao userDao = new UserDao(con);
//
//			// Student生成
//			User user = new User();
//
//			// ユーザ名入力
//			String username = inputStr("ユーザ名を入力してください。", "ユーザ名の長さは範囲を超えていますので、再入力してください。", 16);
//			user.setUsername(username);
//
//			// パスワード入力
//			String password = inputStr("パスワードを入力してください。", "パスワードの長さは範囲を超えていますので、再入力してください。", 16);
//			user.setPassword(password);
//
//			// 名前入力
//			String name = inputStr("名前を入力してください。", "名前の長さは範囲を超えていますので、再入力してください。", 32);
//			user.setName(name);
//
//			// 生年月日入力
//			String bday = inputDate("生年月日を入力してください。", "入力した生年月日はパターンと一致しませんので、再入力してください。", "yyyyMMdd");
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//			java.util.Date birthday = sdf.parse(bday);
//			user.setBirthday(new java.sql.Date(birthday.getTime()));
//
//			// 管理者かどうか
//			int adminFlag = (inputBoolean("あなたは一般なユーザですか? (y/n)")) ? 1 : 0;
//			user.setAdminFlag(adminFlag);
//
//			// 登録日時
//			java.util.Date today = new java.util.Date();
//			user.setCreated(new java.sql.Timestamp(today.getTime()));
//
//			// insert SQLを実行
//			int count = userDao.insert(user);
//
//			// 結果出力
//			System.out.println(count + "件登録しました。");
//
//		} catch (ClassNotFoundException e) {
//			System.err.println("JDBCドライバがロードできませんでした。");
//			e.printStackTrace();
//		} catch (SQLException e) {
//			System.err.println("SQL例外が発生しました。");
//			e.printStackTrace();
//		} catch (ParseException e) {
//			System.err.println("誕生日のバースに失敗しました。");
//			e.printStackTrace();
//		} finally {
//			DBUtil.close(con);
//		}
//	}
//
//}

/******************************************************************************/
