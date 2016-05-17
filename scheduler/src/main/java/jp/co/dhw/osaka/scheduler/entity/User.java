package jp.co.dhw.osaka.scheduler.entity;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * userテーブルのValueObjectです。
 * 
 * @author bangoku
 * @date 2016/05/17
 */
public class User {
	/** ユーザ名 */
	private String username;

	/** パスワード */
	private String password;

	/** 名前 */
	private String name;

	/** 生年月日 */
	private Date birthday;

	/** 管理者フラグ */
	private int adminFlag;

	/** 登録日時 */
	 private Timestamp created;
//	private Date created;

	/** 更新日時 */
	private Timestamp modified;

	/**
	 * ユーザ名の取得
	 * 
	 * @return ユーザ名
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * ユーザ名の設定
	 * 
	 * @param username
	 *            ユーザ名
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * パスワードの取得
	 * 
	 * @return パスワード
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * パスワードの設定
	 * 
	 * @param password
	 *            パスワード
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 名前の取得
	 * 
	 * @return 名前
	 */
	public String getName() {
		return name;
	}

	/**
	 * 名前の設定
	 * 
	 * @param name
	 *            名前
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 生年月日の取得
	 * 
	 * @return 生年月日
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * 生年月日の設定
	 * 
	 * @param birthday
	 *            生年月日
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	/**
	 * 管理者かどうかの取得
	 * 
	 * @return 管理者かどうかの値（0:一般, 1:管理者）
	 */
	public int getAdminFlag() {
		return adminFlag;
	}

	/**
	 * 管理者かどうかの設定
	 * 
	 * @param adminFlag
	 *            管理者かどうかの値(0: 一般, 1:管理者)
	 */
	public void setAdminFlag(int adminFlag) {
		this.adminFlag = adminFlag;
	}

	/**
	 * ユーザ登録の日時の取得
	 * 
	 * @return ユーザ登録の日時
	 */
	public Timestamp getCreated() {
		return created;
	}

	/**
	 * ユーザ登録の日時の設定
	 * 
	 * @param created
	 *            ユーザ登録の日時
	 */
	public void setCreated(Timestamp created) {
		this.created = created;
	}

	/**
	 * 登録されたユーザ情報の更新の日時の取得
	 * 
	 * @return 更新の日時
	 */
	public Timestamp getModified() {
		return modified;
	}

	/**
	 * 登録されたユーザ情報更新の日時の設定
	 * 
	 * @param modified
	 *            更新の日時
	 */
	public void setModified(Timestamp modified) {
		this.modified = modified;
	}
}
