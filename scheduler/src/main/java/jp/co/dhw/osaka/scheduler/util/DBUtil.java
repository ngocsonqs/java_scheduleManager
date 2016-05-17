package jp.co.dhw.osaka.scheduler.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.whizz_tech.ocean.config.ParameterConfig;
import jp.co.whizz_tech.ocean.cui.CuiAppManager;

/**
 * DBのユーティリティクラスです。
 * 
 * @author bangoku
 * @date 2016/05/16
 */
public final class DBUtil {
	static ParameterConfig config = CuiAppManager.getParameterConfig();

	/** JDBCドライバ名 */
	private static String jdbcDriver = config.getParameter("driverclass");

	/** DB接続URL */
	private static String url = config.getParameter("url");
	
	/** DBユーザ名 */
	private static String user = config.getParameter("user");
	
	/** DBパスワード */
	private static String password = config.getParameter("password");
	
	/**
	 * データベースに接続します。
	 * 
	 * @return Connectionオブジェクト
	 * @throws ClassNotFoundException
	 *             JDBCドライバが見つからない場合
	 * @throws SQLException
	 *             接続に失敗した場合
	 */
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		// 1. JDBCドライバのロード
		Class.forName(jdbcDriver);

		// 2. データベースへの接続
		Connection con = DriverManager.getConnection(url, user, password);

		return con;
	}

	/**
	 * Connectionをクローズします。
	 * 
	 * @param con
	 *            Connectionオブジェクト
	 */
	public static void close(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * PreparedStatementをクローズします。
	 * 
	 * @param pstmt
	 *            PreparedStatementオブジェクト
	 */
	public static void close(PreparedStatement pstmt) {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * ResultSetをクローズします。
	 * 
	 * @param rs
	 *            ResultSetオブジェクト
	 */
	public static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
