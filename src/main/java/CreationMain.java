import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * ДЗ_15 работа с базой данных.
 * Спроектировать базу
 * -      Таблица USER содержит поля id, name, birthday, login_ID, city, email, description
 * -      Таблица ROLE содержит поля id, name (принимает значения Administration, Clients, Billing), description
 * -      Таблица USER_ROLE содержит поля id, user_id, role_id
 */
public class CreationMain {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        Class.forName("org.postgresql.Driver");

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/innoDB", "postgres", "postgres");
             Statement statement = connection.createStatement()) {

            statement.execute("-- Database: innoDB\n"
                + "DROP TABLE IF EXISTS \"USER\";\n"
                + "CREATE TABLE \"USER\" (\n"
                + "     id bigserial primary key,\n"
                + "     name varchar(100) NOT NULL,\n"
                + "     birthday date NOT NULL,\n"
                + "     login_ID varchar(100) NOT NULL,\n"
                + "     city varchar(100) NOT NULL,\n"
                + "     email varchar(100) NOT NULL,\n"
                + "     description varchar(100) NOT NULL\n"
                + ");"
                + "DROP TABLE IF EXISTS \"ROLE\";\n"
                + "CREATE TABLE \"ROLE\" (\n"
                + "     id bigserial primary key,\n"
                + "     name varchar(100) NOT NULL,\n"
                + "     description varchar(100) NOT NULL\n"
                + ");\n"
                + "DROP TABLE IF EXISTS \"USER_ROLE\";\n"
                + "CREATE TABLE \"USER_ROLE\" (\n"
                + "     id bigserial primary key,\n"
                + "     user_id integer NOT NULL,\n"
                + "     role_id integer NOT NULL\n"
                + ");\n"
            );
        }
    }
}
