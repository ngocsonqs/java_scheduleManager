package jp.co.bangoku.scheduler.main;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.bangoku.scheduler.LoginUser;
import jp.co.dhw.osaka.scheduler.dao.UserDao;
import jp.co.dhw.osaka.scheduler.entity.User;
import jp.co.dhw.osaka.scheduler.util.DBUtil;
import jp.co.whizz_tech.ocean.cui.CuiAppBase;

/**
 * ログインするためのクラスです。
 * 
 * @author bangoku
 * @date 2016/05/18
 */
public class Login extends CuiAppBase {
	/** 管理者 */
	private static final String ADMIN = "Admin";

	/** 一般ユーザ */
	private static final String USER = "User";

	/**
	 * アプリケーションを実行します。
	 */
	@Override
	public void execute() {
		System.out.println("\nログインを開始します...");

		// ユーザ名入力
		String username = inputStr("ユーザ名を入力してください。", "ユーザ名の長さは範囲を超えていますので、再入力してください。", 16);

		// パスワード入力
		String password = inputStr("パスワードを入力してください。", "パスワードの長さは範囲を超えていますので、再入力してください。", 16);

		// 入力させたユーザ名はuserテーブルに存在するかどうかをチェックします。
		if (authUser(username, password)) {
			System.out.println("【ユーザ認証できました。" + username + "さん、こんにちは!!!】");
			new LoginUser();
			LoginUser.setSessionLogin(username, isAdmin(username));
		} else {
			System.err.println("【ユーザ認証に失敗しました。もう一度入力し直してください。】");
		}
	}

	/**
	 * 入力させたユーザとパスワードに対してデータベースに存在しているかどうかをチェックします。
	 * 
	 * @param username
	 *            ユーザ名
	 * @param password
	 *            パスワード
	 * @return 存在している場合はtrue、存在しない場合はfalse
	 */
	public static boolean authUser(String username, String password) {
		try {
			// データベースに接続
			Connection con = DBUtil.getConnection();

			// UserDao生成
			UserDao userDao = new UserDao(con);

			return (userDao.findByUsernameAndPass(username, password) != null) ? true : false;

		} catch (ClassNotFoundException e) {
			System.err.println("JDBCドライバがロードできませんでした。");
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("SQL例外が発生しました。");
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * ログインしているユーザは管理者か一般ユーザかをチェックします。
	 * 
	 * @param username
	 *            ユーザ名
	 * @param password
	 *            パスワード
	 * @return 管理かの場合は {@link Login#ADMIN}, 一般ユーザの場合は{@link Login#USER}
	 */
	public static String isAdmin(String username) {
		String admin_flg = "";
		try {
			// データベースに接続
			Connection con = DBUtil.getConnection();

			// User生成
			User user = new User();

			// UserDao生成
			UserDao userDao = new UserDao(con);

			// 管理者か一般ユーザかを判定します。
			user = userDao.findByUsername(username);

			admin_flg = (user.getAdminFlag() == 1) ? ADMIN : USER;
		} catch (ClassNotFoundException e) {
			System.err.println("JDBCドライバがロードできませんでした。");
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("SQL例外が発生しました。");
			e.printStackTrace();
		}
		return admin_flg;
	}
}