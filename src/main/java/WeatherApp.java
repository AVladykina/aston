import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.sql.DataSource;
import java.sql.SQLException;

public class WeatherApp {

    /**
     * Главный метод приложения Weather App.
     *
     * @param args Аргументы командной строки (не используются).
     * @throws ParseException Бросается в случае проблем с разбором данных в формате JSON.
     */

    public static void main(String[] args) throws ParseException, SQLException {

        WeatherService ws = new WeatherService();

        String currentWeatherFilePath = "/Users/anastasia/IdeaProjects/aston/aston/src/out/currentWeatherData.txt";
        String threeDayForecastFilePath = "/Users/anastasia/IdeaProjects/aston/aston/src/out/threeDayForecastData.txt";
        String hourlyForecastFilePath = "/Users/anastasia/IdeaProjects/aston/aston/src/out/hourlyForecastData.txt";

        String cityLondon = "London,uk";
        String cityAmsterdam = "Amsterdam,NL";
        String cityParis = "Paris,FR";
        String cityNewYork = "New York,US";
        String cityBarcelona = "Barcelona,ES";

        JSONParser parser = new JSONParser();

        // Получение текущей погоды
        JSONObject currentWeatherData = (JSONObject) parser.parse(ws.getCurrentWeather(cityLondon, Settings.valueOf("BASE_URL").toString(), Settings.valueOf("API_KEY").toString()));
        System.out.println("Текущая погода:");
        ws.displayWeather(new JSONObject(currentWeatherData));

        // Запись данных о текущей погоде в файл
        ws.writeDisplayWeatherToFile(currentWeatherFilePath, new JSONObject(currentWeatherData));

        // Получение прогноза на три дня London
        String dataStringLondon = ws.getThreeDayForecast(cityLondon, Settings.valueOf("BASE_URL").toString(), Settings.valueOf("API_KEY").toString());
        JSONObject threeDayForecastData = (JSONObject) parser.parse(dataStringLondon);
        System.out.println("\nПрогноз на три дня:");
        ws.displayThreeDayForecast(threeDayForecastData);

        // Запись данных о прогнозе погоды на три дня в файл
        ws.writeThreeDayForecastToFile(threeDayForecastFilePath, new JSONObject(threeDayForecastData));

        // Получение прогноза на весь день по часам
        JSONObject hourlyForecastData = (JSONObject) parser.parse(ws.getHourlyForecast(cityLondon, Settings.valueOf("BASE_URL").toString(), Settings.valueOf("API_KEY_SECOND").toString()));
        System.out.println("\nПогода на весь день по часам:");
        ws.displayHourlyForecast(hourlyForecastData);

        // Запись данных о прогнозе погоды на весь день по часам в файл
        ws.writeHourlyForecastToFile(hourlyForecastFilePath, new JSONObject(hourlyForecastData));


        DataSource dataSource = initDb();
        DbService dbService = new DbService(dataSource);

        // Fill table cities from Json file
        dbService.saveCitiesToDatabase(dbService.readJsonFromFile("cityList.json"), dataSource);

        DbService.createThreeDayForecastTable(dataSource);

        JsonObject threeDayForecastDataJsonLondon = JsonParser.parseString(dataStringLondon).getAsJsonObject();

        String dataStringAmsterdam = ws.getThreeDayForecast(cityAmsterdam, Settings.valueOf("BASE_URL").toString(), Settings.valueOf("API_KEY").toString());
        JsonObject threeDayForecastDataJsonAmsterdam = JsonParser.parseString(dataStringAmsterdam).getAsJsonObject();

        String dataStringParis = ws.getThreeDayForecast(cityParis, Settings.valueOf("BASE_URL").toString(), Settings.valueOf("API_KEY").toString());
        JsonObject threeDayForecastDataJsonParis = JsonParser.parseString(dataStringParis).getAsJsonObject();

        String dataStringNewYork = ws.getThreeDayForecast(cityNewYork, Settings.valueOf("BASE_URL").toString(), Settings.valueOf("API_KEY").toString());
        JsonObject threeDayForecastDataJsonNewYork = JsonParser.parseString(dataStringNewYork).getAsJsonObject();

        String dataStringBarcelona = ws.getThreeDayForecast(cityBarcelona, Settings.valueOf("BASE_URL").toString(), Settings.valueOf("API_KEY").toString());
        JsonObject threeDayForecastDataJsonBarcelona = JsonParser.parseString(dataStringBarcelona).getAsJsonObject();

        dbService.saveThreeDayForecastToDatabase(dataSource, threeDayForecastDataJsonLondon);
        dbService.saveThreeDayForecastToDatabase(dataSource, threeDayForecastDataJsonAmsterdam);
        dbService.saveThreeDayForecastToDatabase(dataSource, threeDayForecastDataJsonParis);
        dbService.saveThreeDayForecastToDatabase(dataSource, threeDayForecastDataJsonNewYork);
        dbService.saveThreeDayForecastToDatabase(dataSource, threeDayForecastDataJsonBarcelona);
    }

    private static DataSource initDb() throws SQLException {
        String ddl = ""
                //  + "DROP SCHEMA public CASCADE;\n"
                //  + "CREATE SCHEMA public;\n"
                + "CREATE TABLE IF NOT EXISTS cities (\n"
                + "id SERIAL PRIMARY KEY,\n"
                + "name VARCHAR(255) NOT NULL,\n"
                + "state VARCHAR(255),\n"
                + "country CHAR(2) NOT NULL,\n"
                + "longitude NUMERIC(10, 6) NOT NULL,\n"
                + "latitude NUMERIC(10, 6) NOT NULL\n"
                + ");";
        DataSource dataSource = DbUtil.buildDataSource();
        DbUtil.applyDdl(ddl, dataSource);
        return dataSource;
    }
}