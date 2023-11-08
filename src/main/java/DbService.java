import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Класс `DbService` предоставляет методы для работы с базой данных, включая чтение данных из JSON,
 * сохранение данных в таблицы и создание таблиц для прогноза погоды.
 */
public class DbService {

    private DataSource dataSource;

    public DbService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Считывает данные из JSON-файла и преобразует их в массив объектов City.
     *
     * @param fileName имя JSON-файла для чтения.
     * @return массив объектов City, представляющих данные.
     */
    public City[] readJsonFromFile(String fileName) {
        Gson gson = new Gson();

        try (var br = new BufferedReader(new FileReader(fileName))) {
            return gson.fromJson(br, City[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Проверяет, пуста ли таблица в базе данных, выполнив запрос COUNT.
     *
     * @param dataSource источник данных для подключения к базе данных.
     * @param tableName  имя таблицы для проверки.
     * @return количество строк в таблице или -1 в случае ошибки.
     */
    int isTableEmpty(DataSource dataSource, String tableName) {
        try (Connection connection = dataSource.getConnection()) {
            // Проверка, пуста ли таблица
            String countQuery = "SELECT COUNT(*) FROM " + tableName;
            PreparedStatement countStatement = connection.prepareStatement(countQuery);
            ResultSet resultSet = countStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Сохраняет массив данных City в таблицу "cities" в базе данных.
     *
     * @param data       массив объектов City для сохранения.
     * @param dataSource источник данных для подключения к базе данных.
     */
    public void saveCitiesToDatabase(City[] data, DataSource dataSource) {
        String tableName = "cities";
        if (isTableEmpty(dataSource, tableName) == 0) {
            String insertQuery = "INSERT INTO cities (id, name, state, country, longitude, latitude) VALUES (?, ?, ?, ?, ?, ?)";
            try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                for (City i : data) {
                    preparedStatement.setInt(1, i.getId());
                    preparedStatement.setString(2, i.getName());
                    preparedStatement.setString(3, i.getState());
                    preparedStatement.setString(4, i.getCountry());
                    preparedStatement.setDouble(5, i.getCoord().getLon());
                    preparedStatement.setDouble(6, i.getCoord().getLat());
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Создает таблицу "ThreeDayForecast" в базе данных, если она не существует.
     *
     * @param dataSource источник данных для подключения к базе данных.
     */
    public static void createThreeDayForecastTable(DataSource dataSource) {
        String ddl = "" + "CREATE TABLE IF NOT EXISTS ThreeDayForecast (\n"
                + "id SERIAL PRIMARY KEY,\n"
                + "forecasted_time BIGINT,\n"
                + "temp NUMERIC(5, 2),\n"
                + "pressure INTEGER,\n"
                + "weather_id INTEGER,\n"
                + "weather_main VARCHAR(255),\n"
                + "weather_description VARCHAR(255),\n"
                + "clouds INTEGER,\n"
                + "wind_speed NUMERIC(5, 2),\n"
                + "dt_txt VARCHAR(255),\n"
                + "city_id INTEGER,\n"
                + "FOREIGN KEY (city_id) REFERENCES cities(id)"
                + ");";
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(ddl)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Сохраняет трехдневный прогноз погоды в базу данных.
     *
     * @param dataSource           источник данных для подключения к базе данных.
     * @param threeDayForecastData данные JSON, представляющие трехдневный прогноз погоды.
     */
    public void saveThreeDayForecastToDatabase(DataSource dataSource, JsonObject threeDayForecastData) {
        int city_id = threeDayForecastData.get("city").getAsJsonObject().get("id").getAsInt();
        JsonArray jsonArray = threeDayForecastData.getAsJsonArray("list");
        String insertQuery = "INSERT INTO ThreeDayForecast (forecasted_time, temp, pressure, weather_id, weather_main, weather_description, clouds, wind_speed, dt_txt, city_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            for (JsonElement i : jsonArray) {
                preparedStatement.setLong(1, i.getAsJsonObject().get("dt").getAsLong());
                JsonObject dataMain = (JsonObject) i.getAsJsonObject().get("main");
                preparedStatement.setDouble(2, dataMain.get("temp").getAsDouble());
                preparedStatement.setInt(3, dataMain.get("pressure").getAsInt());
                preparedStatement.setInt(4, i.getAsJsonObject().get("weather").getAsJsonArray().get(0).getAsJsonObject().get("id").getAsInt());
                preparedStatement.setString(5, i.getAsJsonObject().get("weather").getAsJsonArray().get(0).getAsJsonObject().get("main").getAsString());
                preparedStatement.setString(6, i.getAsJsonObject().get("weather").getAsJsonArray().get(0).getAsJsonObject().get("description").getAsString());
                preparedStatement.setInt(7, i.getAsJsonObject().get("clouds").getAsJsonObject().get("all").getAsInt());
                preparedStatement.setDouble(8, i.getAsJsonObject().get("wind").getAsJsonObject().get("speed").getAsDouble());
                preparedStatement.setString(9, i.getAsJsonObject().get("dt_txt").getAsString());
                preparedStatement.setInt(10, city_id);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
