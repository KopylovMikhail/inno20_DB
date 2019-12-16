import java.sql.*;

/**
 * ДЗ_15 работа с базой данных.
 * Перевести connection в ручное управление транзакциями
 * a)   Выполнить 2-3 SQL операции на ваше усмотрение (например, Insert в 3 таблицы – USER, ROLE, USER_ROLE)
 *      между sql операциями установить логическую точку сохранения(SAVEPOINT)
 * б)   Выполнить 2-3 SQL операции на ваше усмотрение (например, Insert в 3 таблицы – USER, ROLE, USER_ROLE)
 *      между sql операциями установить точку сохранения (SAVEPOINT A), намеренно ввести некорректные данные на последней операции,
 *      что бы транзакция откатилась к логической точке SAVEPOINT A
 */
public class Main4 {

    public static void main(String[] args) {

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/innoDB", "postgres", "postgres");
             Statement statement = connection.createStatement();) {
            connection.setAutoCommit(false); //переводим connection в ручное управление транзакциями

            //Insert в таблицу USER
            statement.execute("INSERT INTO \"USER\"(\n" +
                    "\tname, login_ID, email, city, birthday, description)\n" +
                    "\tVALUES ('Bob', 'Bob90', 'bob@mail.ru', 'Moscow', '1990-02-28', 'User Bob');");

            //Создание Savepoint
            Savepoint savepoint = connection.setSavepoint("before ROLE");

            //Insert в таблицу ROLE
            statement.execute("INSERT INTO public.\"ROLE\"(\n" +
                    "\tname, description)\n" +
                    "\tVALUES ('Bob', 'Role Bob');");

            try {
                //Insert в таблицу USER_ROLE
                statement.execute("INSERT INTO \"USER_ROLE\"(\n" +
                        "\tuser_id, role_id)\n" +
                    "\tVALUES ((SELECT id FROM \"USER\" WHERE name = 'Bob'), (SELECT id FROM \"ROLE\" WHERE name = 'Bob'));");
//                    "\tVALUES ((SELECT id FROM \"USER\" WHERE login_ID = 'Bob90'), (SELECT id FROM \"ROLE\" WHERE login_ID = 'Bob90'));"); //столбец login_ID в таблице ROLE не существует
            } catch (Exception e) {
                //Rollback к savepoint
                connection.rollback(savepoint);
                e.printStackTrace();
            }

            //Commit транзакции
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
