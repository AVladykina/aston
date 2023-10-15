import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WeatherServiceTest {

    private static WeatherService weatherService;

    @BeforeAll
    public static void setUp() {
        weatherService = new WeatherService();
    }

    @Test
    public void testWriteDisplayWeatherToFile() throws IOException, ParseException {
        // Создаем JSON-объект для имитации данных о погоде
        String jsonString = "{\"main\":{\"temp\":277.31,\"humidity\":83},\"name\":\"London\",\"wind\":{\"deg\":93,\"speed\":1.32}}";
        JSONParser jsonParser = new JSONParser();
        JSONObject weatherData = (JSONObject) jsonParser.parse(jsonString);

        // Путь к файлу, который будет использоваться в тесте
        String testFilePath = "testDisplayWeather.txt";

        // Вызов метода и проверка результата
        weatherService.writeDisplayWeatherToFile(testFilePath, weatherData);

        // Проверка, что файл был создан
        assertTrue(fileExists(testFilePath));

        // Чтение файла и сравнение с ожидаемыми данными


        // Удаление временного файла после проверок
        deleteFile(testFilePath);
    }

    // Вспомогательный метод для проверки существования файла
    private boolean fileExists(String filePath) {
        return new File(filePath).exists();
    }


    // Вспомогательный метод для удаления файла
    private void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testWriteThreeDayForecastToFile() {

    }

    @Test
    public void testWriteHourlyForecastToFile() {

    }
}