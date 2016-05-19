package jp.co.dhw.osaka.scheduler.dao;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
 
import jp.co.dhw.osaka.scheduler.entity.Schedule;
import jp.co.dhw.osaka.scheduler.util.DBUtil;
 
/**
 * scheduleテーブルにアクセスするクラスです。
 * 
 * @author ngocsonqs
 *
 */
public class ScheduleDao {
 
    /** データベースに接続 */
    private Connection con;
 
    /** スケジュール登録のSQL文 */
    private static final String INSERT_SCHEDULE_SQL = "INSERT INTO schedule (username, schedule_date, schedule_time, title, content, insert_datetime) VALUES (?, ?, ?, ?, ?, ?)";
 
    /** スケジュール確認のSQL文 */
    private static final String VIEW_SCHEDULE_SQL = "SELECT schedule_time, title, content FROM schedule WHERE schedule_date = ? AND username = ? ORDER BY schedule_time %s";
 
    /** スケジュール更新のSQL文 */
    private static final String UPDATE_SCHEDULE_SQL = "UPDATE schedule SET title = ?, content = ?, update_datetime = NOW() WHERE schedule_date = ? AND schedule_time = ? AND username = ?";
 
    /** スケジュール削除のSQL文 */
    private static final String DELETE_SCHEDULE_SQL = "DELETE FROM schedule WHERE schedule_date = ? AND schedule_time = ? AND username = ?";
    
    /** 指定した年月でスケジュールを検索するSQL文 */
    private static final String FIND_BY_YEARMONTH_SQL = "SELECT * FROM schedule WHERE username = ? and schedule_date LIKE ? ORDER BY schedule_date %s";
    
    /** xuất dữ liệu trực tiếp từ câu lệnh SQL */
//    private static final String FIND_BY_YEARMONTH_SQL = "SELECT * into OUTFILE ? FIELDS TERMINATED BY ? LINES TERMINATED BY ? FROM schedule WHERE username = ? and schedule_date LIKE ? ORDER BY schedule_date %s";
    
    /**
     * このクラスのオブジェクトを構築します。
     * 
     * @param con
     *            データベース接続
     */
    public ScheduleDao(Connection con) {
        this.con = con;
    }
 
    /**
     * スケジュール登録をします。
     * 
     * @param schedule
     *            Scheduleオブジェクト
     * @return 登録件数、登録されない場合は 0
     * @throws SQLException
     *             データベース例外が発生した場合
     */
    public int insert(Schedule schedule) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
 
        int insertedCount = 0;
 
        try {
            pstmt = con.prepareStatement(INSERT_SCHEDULE_SQL);
            pstmt.setString(1, schedule.getUsername());
            pstmt.setDate(2, schedule.getScheduleDate());
            pstmt.setTime(3, schedule.getScheduleTime());
            pstmt.setString(4, schedule.getTitle());
            pstmt.setString(5, schedule.getContent());
            pstmt.setTimestamp(6, schedule.getInsertDatetime());
 
            // SQL実行
            insertedCount = pstmt.executeUpdate();
            // System.out.println(pstmt.toString());
        } finally {
            DBUtil.close(pstmt);
            DBUtil.close(rs);
        }
 
        return insertedCount;
    }
 
    /**
     * スケジュール確認をします。
     * 
     * @param schedule
     *            Scheduleオブジェクト
     * @param sortFlg
     *            ソート順 (昇順の場合は true, 降順の場合は false)
     * @return Scheduleオブジェクト
     * @throws SQLException
     *             データベース例外が発生した場合
     */
    public List<Schedule> viewByDate(Schedule schedule, boolean sortFlg) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
 
        List<Schedule> list = new ArrayList<Schedule>();
        try {
            // true->昇順、 false:降順
            String sort = sortFlg ? "ASC" : "DESC";
            String sql = String.format(VIEW_SCHEDULE_SQL, sort);
            pstmt = con.prepareStatement(sql);
            pstmt.setDate(1, schedule.getScheduleDate());
            pstmt.setString(2, schedule.getUsername());
 
            // SQLを実行
            rs = pstmt.executeQuery();
 
            while (rs.next()) {
                // レコードがあれば、VOに詰め替え
                Schedule scheduleResult = new Schedule();
                scheduleResult.setScheduleTime(rs.getTime("schedule_time"));
                scheduleResult.setTitle(rs.getString("title"));
                scheduleResult.setContent(rs.getString("content"));
 
                // リストに追加
                list.add(scheduleResult);
            }
        } finally {
            DBUtil.close(pstmt);
            DBUtil.close(rs);
        }
        return list;
    }
 
    /**
     * スケジュールを更新します
     * 
     * @param schedule
     *            Scheduleオブジェクト
     * @return 更新件数、更新されない場合は0
     * @throws SQLException
     *             データベース例外が発生した場合
     */
    public int update(Schedule schedule) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
 
        int updatedCount = 0;
        try {
            pstmt = con.prepareStatement(UPDATE_SCHEDULE_SQL);
            pstmt.setString(1, schedule.getTitle());
            pstmt.setString(2, schedule.getContent());
            pstmt.setDate(3, schedule.getScheduleDate());
            pstmt.setTime(4, schedule.getScheduleTime());
            pstmt.setString(5, schedule.getUsername());
 
            // 更新SQLを実行します。
            updatedCount = pstmt.executeUpdate();
        } finally {
            DBUtil.close(pstmt);
            DBUtil.close(rs);
        }
        return updatedCount;
    }
 
    /**
     * スケジュールを削除します。
     * 
     * @param schedule
     *            Scheduleオブジェクト
     * @return 削除件数、削除されない場合は0
     * @throws SQLException
     *             データベース例外が発生した場合
     */
    public int delete(Schedule schedule) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
 
        int deletedCount = 0;
        try {
            pstmt = con.prepareStatement(DELETE_SCHEDULE_SQL);
            pstmt.setDate(1, schedule.getScheduleDate());
            pstmt.setTime(2, schedule.getScheduleTime());
            pstmt.setString(3, schedule.getUsername());
 
            // 削除SQLを実行します。
            deletedCount = pstmt.executeUpdate();
 
        } finally {
            DBUtil.close(pstmt);
            DBUtil.close(rs);
        }
        return deletedCount;
    }
 
    /**
     * xử lý xuất dữ liệu ra file csv trực tiếp từ câu lệnh SQL (chú ý lỗi quyền ghi file trong sql)
     * @param schedule Scheduleオブジェクト
     * @param sortFlg ソート順 (昇順の場合は true, 降順の場合は false)
     * @return Scheduleオブジェクト
     * @throws SQLException データベース例外が発生した場合
     */
    public List<Schedule> findByYearMonth1(Schedule schedule, boolean sortFlg, String fileName) throws SQLException {
    	PreparedStatement pstmt = null;
        ResultSet rs = null;
 
        List<Schedule> list = new ArrayList<Schedule>();
        try {
            // true->昇順、 false:降順
            String sort = sortFlg ? "ASC" : "DESC";
            String sql = String.format(FIND_BY_YEARMONTH_SQL, sort);
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, fileName);
            pstmt.setString(2, ",");
            pstmt.setString(3, "\n");
            pstmt.setString(4, schedule.getUsername());
            pstmt.setString(5, schedule.getScheduleDateStr().concat("%"));
 
            // SQLを実行
//            rs = pstmt.executeQuery();
// 
//            while (rs.next()) {
//                // レコードがあれば、VOに詰め替え
//                Schedule scheduleResult = new Schedule();
//                scheduleResult.setScheduleDate(rs.getDate("schedule_date"));
//                scheduleResult.setScheduleTime(rs.getTime("schedule_time"));
//                scheduleResult.setTitle(rs.getString("title"));
//                scheduleResult.setContent(rs.getString("content"));
//                scheduleResult.setInsertDatetime(rs.getTimestamp("insert_datetime"));
//                scheduleResult.setUpdateDatetime(rs.getTimestamp("update_datetime"));
// 
//                // リストに追加
//                list.add(scheduleResult);
//            }
            System.out.println(pstmt.toString());
        } finally {
            DBUtil.close(pstmt);
            DBUtil.close(rs);
        }
        return list;
    }
    
    /**
     * 指定した年月でスケジュールを検索します。
     * @param schedule Scheduleオブジェクト
     * @param sortFlg ソート順 (昇順の場合は true, 降順の場合は false)
     * @return Scheduleオブジェクト
     * @throws SQLException データベース例外が発生した場合
     */
    public List<Schedule> findByYearMonth(Schedule schedule, boolean sortFlg) throws SQLException {
    	PreparedStatement pstmt = null;
        ResultSet rs = null;
 
        List<Schedule> list = new ArrayList<Schedule>();
        try {
            // true->昇順、 false:降順
            String sort = sortFlg ? "ASC" : "DESC";
            String sql = String.format(FIND_BY_YEARMONTH_SQL, sort);
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, schedule.getUsername());
            pstmt.setString(2, schedule.getScheduleDateStr().concat("%"));
 
            // SQLを実行
            rs = pstmt.executeQuery();
 
            while (rs.next()) {
                // レコードがあれば、VOに詰め替え
                Schedule scheduleResult = new Schedule();
                scheduleResult.setScheduleDate(rs.getDate("schedule_date"));
                scheduleResult.setScheduleTime(rs.getTime("schedule_time"));
                scheduleResult.setTitle(rs.getString("title"));
                scheduleResult.setContent(rs.getString("content"));
                scheduleResult.setInsertDatetime(rs.getTimestamp("insert_datetime"));
                scheduleResult.setUpdateDatetime(rs.getTimestamp("update_datetime"));
 
                // リストに追加
                list.add(scheduleResult);
            }
//            System.out.println(pstmt.toString());
        } finally {
            DBUtil.close(pstmt);
            DBUtil.close(rs);
        }
        return list;
    }
}