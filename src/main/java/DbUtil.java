import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DbUtil {

    /**
     * Производится выполнение DDL (Data Definition Language) запроса, переданного в виде строки ddl.
     * Этот метод открывает соединение с базой данных, создает Statement и выполняет переданный DDL-запрос.
     *
     * @param ddl
     * @param dataSource
     * @throws SQLException
     */
    public static void applyDdl(String ddl, DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(ddl);
        }
    }

    /**
     * Настраивается и создается объект PGSimpleDataSource, который является конкретной реализацией
     * интерфейса javax.sql.DataSource для PostgreSQL.
     * Затем в этом методе устанавливаются параметры для подключения к базе данных, такие как имя сервера,
     * имя пользователя, пароль, название базы данных и номер порта.
     *
     * @return
     * @throws SQLException
     */
    public static DataSource buildDataSource() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setServerName("localhost");
        dataSource.setUser("postgres");
        dataSource.setPassword("mysecretpassword");
        dataSource.setDatabaseName("postgres");
        dataSource.setPortNumber(5432);
        //Проверка, что соединение может быть установлено и закрыто успешно
        dataSource.getConnection().close();
        return dataSource;
    }
}
