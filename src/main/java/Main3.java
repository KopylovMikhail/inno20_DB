import java.sql.*;

/**
 * ДЗ_15 работа с базой данных.
 * Сделать параметризированную выборку по login_ID и name одновременно
 */
public class Main3 {
    private static final String SELECT_SQL = "SELECT id, name, birthday, login_id, city, email, description "
            + "FROM \"USER\" WHERE login_id LIKE ? AND name NOT LIKE ?";

    public static void main(String[] args) throws SQLException {

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/innoDB", "postgres", "postgres")) {
            try (PreparedStatement insertStmt = connection.prepareStatement(SELECT_SQL)) {
                insertStmt.setString(1, "%9%");
                insertStmt.setString(2, "%T%");
                ResultSet resultSet = insertStmt.executeQuery();

                System.out.println("id |  name |  birthday  |login_id| city  |     email      | description"); //выводим шапку таблицы

                //выводим построчно результаты выборки
                while (resultSet.next()) {
                    System.out.println(resultSet.getInt("id") + " | " + resultSet.getString("name") + " | "
                        + resultSet.getString("birthday") + " | " + resultSet.getString("login_id") + " | "
                        + resultSet.getString("city") + " | " + resultSet.getString("email") + " | "
                        + resultSet.getString("description"));
                }
            }
        }
    }
}
