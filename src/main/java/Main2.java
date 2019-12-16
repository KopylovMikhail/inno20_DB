import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * ДЗ_15 работа с базой данных.
 * Через jdbc интерфейс сделать запись данных(INSERT) в таблицу
 * a)      Используя параметризированный запрос
 * b)      Используя batch процесс
 */
public class Main2 {
    private static final String INSERT_SQL = "INSERT INTO \"USER\""
            + "(name, login_ID, email, city, birthday, description) VALUES (?,?,?,?,?,?)";

    public static void main(String[] args) throws SQLException {

        SimpleDateFormat format = new SimpleDateFormat(); //формат даты для поля birthday
        format.applyPattern("yyyy-MM-dd");

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/innoDB", "postgres", "postgres")) {
            try (PreparedStatement insertStmt = connection.prepareStatement(INSERT_SQL)) {

                //параметризированный запрос
                insertStmt.setString(1, "Tom");
                insertStmt.setString(2, "tom89");
                insertStmt.setString(3, "tom@mail.ru");
                insertStmt.setString(4, "Ufa");
                insertStmt.setDate(5, new Date(format.parse("1989-01-31").getTime())); //преобразуем строку в формат java.sql.Date
                insertStmt.setString(6, "User Tom");
                insertStmt.executeUpdate(); //выполняем параметризированный запрос

                //batch процесс
                for (int i = 0; i < 10; i++) {
                    insertStmt.setString(1, "Name" + i);
                    insertStmt.setString(2, "name0" + i);
                    insertStmt.setString(3, "name0" + i + "@mail.ru");
                    insertStmt.setString(4, "City" + i);
                    insertStmt.setDate(5, new Date(format.parse("200" +i +"-01-31").getTime()));
                    insertStmt.setString(6, "User Name0" + i);
                    insertStmt.addBatch();
                }
                insertStmt.executeBatch(); //выполняем batch-запрос
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
