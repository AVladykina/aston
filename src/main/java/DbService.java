import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.sql.DataSource;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DbService {

    public JSONObject readJsonFromFile(String filePath){
        JSONParser parser = new JSONParser();

        try (FileReader fileReader = new FileReader(filePath)) {
            Object obj = parser.parse(fileReader);

            if (obj instanceof JSONObject) {
                return (JSONObject) obj;
            } else {
                System.err.println("JSON файл должен содержать объект JSON.");
                return null;
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    public JSONArray readJsonArrayFromFile(String filePath) {
        JSONParser parser = new JSONParser();

        try (FileReader fileReader = new FileReader(filePath)) {
            Object obj = parser.parse(fileReader);

            if (obj instanceof JSONArray) {
                return (JSONArray) obj;
            } else {
                System.err.println("JSON файл должен содержать массив JSON.");
                return null;
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void createCityTable(DataSource dataSource) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS city ("
                + "id SERIAL PRIMARY KEY,"
                + "name VARCHAR(255) NOT NULL,"
                + "state VARCHAR(255),"
                + "country CHAR(2) NOT NULL,"
                + "longitude NUMERIC(10, 6) NOT NULL,"
                + "latitude NUMERIC(10, 6) NOT NULL"
                + ");";

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(createTableSQL);
        }
    }

    public static void saveCityData(JSONArray cityData, DataSource dataSource) throws SQLException {
        String insertSQL = "INSERT INTO city (name, state, country, longitude, latitude) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            for (Object obj : cityData) {
                JSONObject cityObject = (JSONObject) obj;
                String name = (String) cityObject.get("name");
                String state = (String) cityObject.get("state");
                String country = (String) cityObject.get("country");
                JSONObject coord = (JSONObject) cityObject.get("coord");
                double longitude = (double) coord.get("lon");
                double latitude = (double) coord.get("lat");

                preparedStatement.setString(1, name);
                preparedStatement.setString(2, state);
                preparedStatement.setString(3, country);
                preparedStatement.setDouble(4, longitude);
                preparedStatement.setDouble(5, latitude);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }
}
