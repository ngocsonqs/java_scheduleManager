package jp.co.dhw.osaka.scheduler.entity;
 
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
 
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
 
/**
 * scheduleテーブルのValue Objectです。
 * 
 * @author ngocsonqs
 *
 */
public class Schedule {
    /** ユーザ名 */
    private String username;
 
    /** スケジュールの日付 */
    private Date scheduleDate;
    
    private String scheduleDateStr;
 
    /** スケジュールの時間 */
    private Time scheduleTime;
 
    /** スケジュールのタイトル */
    private String title;
 
    /** スケジュールの内容 */
    private String content;
 
    /** レコードの登録日時 */
    private Timestamp insertDatetime;
 
    /** レコードの更新日時 */
    private Timestamp updateDatetime;
 
    /**
     * ユーザ名を取得します。
     * 
     * @return ユーザ名
     */
    public String getUsername() {
        return username;
    }
 
	/**
     * ユーザ名を設定します。
     * 
     * @param username
     *            ユーザ名
     */
    public void setUsername(String username) {
        this.username = username;
    }
 
    /**
     * スケジュールの日付を取得します。
     * 
     * @return スケジュールの日付
     */
    public Date getScheduleDate() {
        return scheduleDate;
    }
 
    /**
     * スケジュールの日付を設定します。
     * 
     * @param scheduleDate
     *            スケジュールの日付
     */
    public void setScheduleDate(Date scheduleDate) {
        this.scheduleDate = scheduleDate;
    }
 
    /**
     * スケジュールの日付を文字列で設定します。
     * @return 文字型のスケジュールの日付
     */
    public String getScheduleDateStr() {
		return scheduleDateStr;
	}
 
    /**
     * スケジュールの日付を文字列で取得します。
     * @param scheduleDateStr 文字型のスケジュール日付
     */
	public void setScheduleDateStr(String scheduleDateStr) {
		this.scheduleDateStr = scheduleDateStr;
	}
	
    /**
     * スケジュールの時間を取得します。
     * 
     * @return スケジュールの時間
     */
    public Time getScheduleTime() {
        return scheduleTime;
    }
 
    /**
     * スケジュールの時間を設定します。
     * 
     * @param scheduleTime
     *            スケジュールの時間
     */
    public void setScheduleTime(Time scheduleTime) {
        this.scheduleTime = scheduleTime;
    }
 
    /**
     * スケジュールのタイトルを取得します。
     * 
     * @return スケジュールのタイトル
     */
    public String getTitle() {
        return title;
    }
 
    /**
     * スケジュールのタイトルを設定します。
     * 
     * @param title
     *            スケジュールのタイトル
     */
    public void setTitle(String title) {
        this.title = title;
    }
 
    /**
     * スケジュールの内容を取得します。
     * 
     * @return スケジュールの内容
     */
    public String getContent() {
        return content;
    }
 
    /**
     * スケジュールの内容を設定します。
     * 
     * @param content
     *            スケジュールの内容
     */
    public void setContent(String content) {
        this.content = content;
    }
 
    /**
     * レコードの登録日時を取得します。
     * 
     * @return レコードの登録日時
     */
    public Timestamp getInsertDatetime() {
        return insertDatetime;
    }
 
    /**
     * レコードの登録日時を設定します。
     * 
     * @param insertDatetime
     *            レコードの登録日時
     */
    public void setInsertDatetime(Timestamp insertDatetime) {
        this.insertDatetime = insertDatetime;
    }
 
    /**
     * レコードの更新日時を取得します。
     * 
     * @return レコードの更新日時
     */
    public Timestamp getUpdateDatetime() {
        return updateDatetime;
    }
 
    /**
     * レコードの更新日時を設定します。
     * 
     * @param updateDatetime
     *            レコードの更新日時
     */
    public void setUpdateDatetime(Timestamp updateDatetime) {
        this.updateDatetime = updateDatetime;
    }
 
    /**
     * ToStringBuilderの形で結果出力
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}