package jp.co.bangoku.scheduler.main;
 
import java.sql.Connection;
import java.sql.SQLException;
 
import jp.co.dhw.osaka.scheduler.dao.UserDao;
import jp.co.dhw.osaka.scheduler.entity.User;
import jp.co.dhw.osaka.scheduler.util.DBUtil;
import jp.co.whizz_tech.ocean.config.ParameterConfig;
import jp.co.whizz_tech.ocean.cui.CuiAppBase;
import jp.co.whizz_tech.ocean.cui.CuiAppManager;
 
/**
 * ログインするためのクラスです。
 * @author bangoku
 * @date 2016/05/18
 */
public class Login extends CuiAppBase {
 
    @Override
    public void execute() {
        System.out.println("\nログインを開始します...");
 
        Connection con = null;
 
        try {
            // データベースに接続
            con = DBUtil.getConnection();
 
            // UserDao生成
            UserDao userDao = new UserDao(con);
 
            // User生成
            User user = new User();
 
            // ユーザ名入力
            String username = inputStr("ユーザ名を入力してください。", "ユーザ名の長さは範囲を超えていますので、再入力してください。", 16);
            user.setUsername(username);
 
            // パスワード入力
            String password = inputStr("パスワードを入力してください。", "パスワードの長さは範囲を超えていますので、再入力してください。", 16);
            user.setPassword(password);
 
            // 入力させたユーザ名はuserテーブルに存在するかどうかをチェックします。
            if (userDao.findByUsernameAndPass(username, password) != null) {
                /** if exists username at DB */
                 
                ParameterConfig config = CuiAppManager.getParameterConfig();
                config.addParameter("username", username);
                 
                // 管理者か一般ユーザかを判定します。
                user = userDao.findByUsernameAndPass(username, password);
                 
                String admin_flg = (user.getAdminFlag() == 1) ? "Admin" : "User";
                 
                config.addParameter("admin_flg", admin_flg);
                 
                if (user.getAdminFlag() == 1) {
                    // 管理者のメニュー
                     
                } else {
                    // 一般のメニュー
                     
                }
                System.out.println("【ユーザ認証できました。" + username + "さん、こんにちは!!!】");
                 
                // 当日のスケジュールを表示する
                 
            } else {
                /** not exists username at DB */
                System.err.println("【ユーザ認証に失敗しました。もう一度入力し直してください。】");
            }
 
        } catch (ClassNotFoundException e) {
            System.err.println("JDBCドライバがロードできませんでした。");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("SQL例外が発生しました。");
            e.printStackTrace();
        }
    }
}