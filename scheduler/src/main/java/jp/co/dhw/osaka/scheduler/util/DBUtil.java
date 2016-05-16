package jp.co.dhw.osaka.scheduler.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * �f�[�^�x�[�X�̃��[�e�B���e�B�N���X�ł��B
 * @author bangoku
 * @date 2016/05/16
 */
public final class DBUtil {
    /** JDBC�h���C�o�� */
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	
	/** �z�X�g�� */
	private static final String HOST_NAME = "localhost";
	
	/** �f�[�^�x�[�X�� */
	private static final String DATABASE_NAME = "schedule";
	
	/** DB�ڑ�URL */
	private static final String URL = "jdbc:mysql://" + HOST_NAME + "/" + DATABASE_NAME + "?useUnicode=true&characterEncoding=utf8&useSSL=false";
	
	/** DB���[�U�� */
	private static final String USERNAME = "ngocsonqs";
	
	/** DB�p�X���[�h */
	private static final String PASSWORD = "y3ulasai";
	
	/**
	 * �f�[�^�x�[�X�ɐڑ����܂��B
	 * @return Connection�I�u�W�F�N�g
	 * @throws ClassNotFoundException JDBC�h���C�o��������Ȃ��ꍇ
	 * @throws SQLException�@�ڑ��Ɏ��s�����ꍇ
	 */
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		// 1. JDBC�h���C�o�̃��[�h
		Class.forName(JDBC_DRIVER);
		
		// 2. �f�[�^�x�[�X�̐ڑ�
		Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		
		return con;
	}
	
	/**
	 * Connection���N���[�Y���܂��B
	 * @param con Connection�I�u�W�F�N�g
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
	 * PreparedStatement���N���[�Y���܂��B
	 * @param pstmt PreparedStatement�I�u�W�F�N�g
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
	 * ResultSet���N���[�Y���܂��B
	 * @param rs ResultSet�I�u�W�F�N�g
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