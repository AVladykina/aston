import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class WeatherApp {


    public static void main(String[] args) {

        WeatherService ws = new WeatherService();

        String currentWeatherFilePath = "/Users/anastasia/IdeaProjects/aston/aston/src/out/currentWeatherData.txt";
        String threeDayForecastFilePath = "/Users/anastasia/IdeaProjects/aston/aston/src/out/threeDayForecastData.txt";
        String hourlyForecastFilePath = "/Users/anastasia/IdeaProjects/aston/aston/src/out/hourlyForecastData.txt";

        String city = "London,uk";

        // Получение текущей погоды
        JSONObject currentWeatherData = (JSONObject) JSONValue.parse(ws.getCurrentWeather(city, Settings.valueOf("BASE_URL").toString(), Settings.valueOf("API_KEY").toString()));
        System.out.println("Текущая погода:");
        ws.displayWeather(new JSONObject(currentWeatherData));

        // Запись данных о текущей погоде в файл
        ws.writeDisplayWeatherToFile(currentWeatherFilePath, new JSONObject(currentWeatherData));

        // Получение прогноза на три дня
        JSONObject threeDayForecastData = (JSONObject) JSONValue.parse(ws.getThreeDayForecast(city, Settings.valueOf("BASE_URL").toString(), Settings.valueOf("API_KEY").toString()));
        System.out.println("\nПрогноз на три дня:");
        ws.displayThreeDayForecast(threeDayForecastData);

        // Запись данных о прогнозе погоды на три дня в файл
        //ws.writeThreeDayForecastToFile(threeDayForecastFilePath, new JSONObject(threeDayForecastData));


        // Получение прогноза на весь день по часам
        JSONObject hourlyForecastData = (JSONObject) JSONValue.parse(ws.getHourlyForecast(city, Settings.valueOf("BASE_URL").toString(), Settings.valueOf("API_KEY_SECOND").toString()));
        System.out.println("\nПогода на весь день по часам:");
        ws.displayHourlyForecast(hourlyForecastData);
    }
}
