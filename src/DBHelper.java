import com.sun.deploy.net.URLEncoder;
import org.apache.http.util.TextUtils;
import org.telegram.telegrambots.api.objects.Update;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.sql.*;
import java.util.ArrayList;

public class DBHelper {

    public static Statement statement;
    public static PreparedStatement preparedStatement;

    public static void initialize() {
        try {
            Class.forName(Constant.DRIVER_NAME);
            Connection connection = DriverManager.getConnection(Constant.CONNECTION_STR, BotKey.DB_USER_NAME, BotKey.DB_PASSWORD);
            statement = connection.createStatement();
            preparedStatement = connection.prepareStatement("UPDATE user SET lastname=?,firstname=?,username=? WHERE id=?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void checkContactSql(Update update) {
        try {
            final Long chatId = update.getCallbackQuery().getMessage().getChatId();
            ResultSet resultSet = (ResultSet) statement.executeQuery("SELECT * FROM user WHERE id=" + chatId + "");
            final String lastName = update.getCallbackQuery().getFrom().getLastName();
            final String firstName = update.getCallbackQuery().getFrom().getFirstName();
            final String userName = update.getCallbackQuery().getFrom().getUserName();
            if (!resultSet.next()) {
                statement.execute("INSERT INTO user(id) VALUES ('" + chatId + "')");
            }
            preparedStatement.setString(1, lastName != null ? lastName : "");
            preparedStatement.setString(2, firstName != null ? firstName : "");
            preparedStatement.setString(3, userName != null ? userName : "");
            preparedStatement.setLong(4, chatId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addSaleSql(int dayNum, String phoneNumber, Long userId) {
        String date = Util.addDay(dayNum);
        String day=Util.getDayName(dayNum);
        String query = String.format("INSERT INTO orders(orderdate,day,phonenumber,userid) VALUES ('%s','%s','%s','%d')", date, day, phoneNumber, userId);
        try {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> getAllUsers() {
        ArrayList<String> users = new ArrayList<>();
        try {

            ResultSet resultSet = (ResultSet) statement.executeQuery("SELECT * FROM user");
            while (resultSet.next()) {
                users.add(resultSet.getString("id"));
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
