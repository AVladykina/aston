import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Класс для работы с погодными данными.
 */
public class WeatherService {

    /**
     * Получение текущей погоды для указанного города.
     *
     * @param city    Название города.
     * @param baseUrl Базовый URL для запросов к API погоды.
     * @param apiKey  API-ключ для доступа к погодным данным.
     * @return Строка с JSON-данными текущей погоды.
     */
    public String getCurrentWeather(String city, String baseUrl, String apiKey) {
        try {
            String endpoint = "/weather";
            URL url = new URL(baseUrl + endpoint + "?q=" + city + "&appid=" + apiKey);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Ошибка при получении текущей погоды.";
        }
    }

    /**
     * Получение погодного прогноза на весь день по часам для указанного города.
     *
     * @param city    Название города.
     * @param baseUrl Базовый URL для запросов к API погоды.
     * @param apiKey  API-ключ для доступа к погодным данным.
     * @return Строка с JSON-данными прогноза на весь день по часам.
     */
    public String getHourlyForecast(String city, String baseUrl, String apiKey) {
        try {
            String endpoint = "/forecast";
            URL url = new URL(baseUrl + endpoint + "?q=" + city + "&appid=" + apiKey);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Ошибка при получении погоды на весь день по часам.";
        }
    }

    /**
     * Получение погодного прогноза на три дня для указанного города.
     *
     * @param city    Название города.
     * @param baseUrl Базовый URL для запросов к API погоды.
     * @param apiKey  API-ключ для доступа к погодным данным.
     * @return Строка с JSON-данными прогноза на три дня.
     */
    public String getThreeDayForecast(String city, String baseUrl, String apiKey) {
        try {
            String endpoint = "/forecast";
            URL url = new URL(baseUrl + endpoint + "?q=" + city + "&appid=" + apiKey);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Ошибка при получении прогноза на три дня.";
        }
    }

    /**
     * Отображение данных о текущей погоде.
     *
     * @param data JSON-данные текущей погоды.
     */
    public void displayWeather(JSONObject data) {
        // Вывод погоды в удобочитаемом формате
        JSONObject mainData = (JSONObject) data.get("main");
        double temperatureInKelvin = (double) mainData.get("temp");

        // Конвертация из Кельвинов в Цельсии
        long temperatureInCelsius = Math.round(temperatureInKelvin - 273.15);

        System.out.println("Погода в городе: " + data.get("name"));
        System.out.println("Температура: " + temperatureInCelsius + "°C");
        System.out.println("Влажность: " + (mainData.get("humidity") + "%"));
        System.out.println("Скорость ветра: " + ((JSONObject) data.get("wind")).get("speed") + "м/с");

    }

    /**
     * Отображение погодного прогноза на весь день.
     *
     * @param data JSON-данные прогноза на весь день по часам.
     */
    public void displayHourlyForecast(JSONObject data) {
        // Вывод погоды на весь день по часам
        JSONArray list = (JSONArray) data.get("list");
        for (Object item : list) {
            JSONObject hourData = (JSONObject) item;
            JSONObject mainData = (JSONObject) hourData.get("main");
            double temperatureInKelvin = (double) mainData.get("temp");

            // Конвертация из Кельвинов в Цельсии
            long temperatureInCelsius = Math.round(temperatureInKelvin - 273.15);

            System.out.println("Время: " + hourData.get("dt_txt") + ", Температура: " +
                    temperatureInCelsius + "°C");
        }
    }

    /**
     * Отображение погодного прогноза на три дня.
     *
     * @param data JSON-данные прогноза на три дня.
     */
    public void displayThreeDayForecast(JSONObject data) {
        // Вывод прогноза на три дня
        JSONArray list = (JSONArray) data.get("list");
        for (Object item : list) {
            JSONObject dayData = (JSONObject) item;
            JSONObject mainData = (JSONObject) dayData.get("main");
            try {
                long temperatureInKelvin = (long) mainData.get("temp");
                // Конвертация из Кельвинов в Цельсии
                long temperatureInCelsius = Math.round(temperatureInKelvin - 273.15);
                System.out.println("Дата: " + dayData.get("dt_txt") + ", Температура: " +
                        temperatureInCelsius + "°C");
            } catch (ClassCastException e) {
                double temperatureInKelvin = (double) mainData.get("temp");
                // Конвертация из Кельвинов в Цельсии
                long temperatureInCelsius = Math.round(temperatureInKelvin - 273.15);
                System.out.println("Дата: " + dayData.get("dt_txt") + ", Температура: " +
                        temperatureInCelsius + "°C");
            }
        }
    }

    /**
     * Запись данных о текущей погоде в файл.
     *
     * @param fileName Имя файла для записи данных.
     * @param data     JSON-данные текущей погоды.
     */
    public void writeDisplayWeatherToFile(String fileName, JSONObject data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            JSONObject mainData = (JSONObject) data.get("main");
            double temperatureInKelvin = ((double) mainData.get("temp"));
            long temperatureInCelsius = Math.round(temperatureInKelvin - 273.15);

            writer.write("Погода в городе: " + data.get("name") + "\n");
            writer.write("Температура: " + temperatureInCelsius + "°C\n");
            writer.write("Влажность: " + mainData.get("humidity") + "%\n");
            writer.write("Скорость ветра: " + ((JSONObject) data.get("wind")).get("speed") + "м/с\n");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Ошибка при записи данных в файл: " + e.getMessage());
        }
    }

    /**
     * Запись данных о погодном прогнозе на три дня в файл.
     *
     * @param fileName Имя файла для записи данных.
     * @param data     JSON-данные прогноза на три дня.
     */
    public void writeThreeDayForecastToFile(String fileName, JSONObject data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            JSONArray list = (JSONArray) data.get("list");
            for (Object item : list) {
                JSONObject dayData = (JSONObject) item;
                JSONObject mainData = (JSONObject) dayData.get("main");
                long temperatureInKelvin = ((Double) mainData.get("temp")).longValue();

                // Конвертация из Кельвинов в Цельсии
                long temperatureInCelsius = Math.round(temperatureInKelvin - 273.15);

                writer.write("Дата: " + dayData.get("dt_txt") + "\n");
                writer.write("Температура: " + temperatureInCelsius + "°C\n");
                writer.write("Влажность: " + mainData.get("humidity") + "%\n");
                writer.write("Скорость ветра: " + ((JSONObject) dayData.get("wind")).get("speed") + "м/с\n");
                writer.write("\n"); // Разделяем записи для каждого дня
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Ошибка при записи данных в файл: " + e.getMessage());
        }
    }

    /**
     * Запись данных о погодном прогнозе на весь день по часам.
     *
     * @param fileName Имя файла для записи данных.
     * @param data     JSON-данные прогноза на три дня.
     */
    public void writeHourlyForecastToFile(String fileName, JSONObject data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            JSONArray list = (JSONArray) data.get("list");
            for (Object item : list) {
                JSONObject hourData = (JSONObject) item;
                JSONObject mainData = (JSONObject) hourData.get("main");
                long temperatureInKelvin = ((Double) mainData.get("temp")).longValue();

                // Конвертация из Кельвинов в Цельсии
                long temperatureInCelsius = Math.round(temperatureInKelvin - 273.15);

                writer.write("Время: " + hourData.get("dt_txt") + "\n");
                writer.write("Температура: " + temperatureInCelsius + "°C\n");
                writer.write("Влажность: " + mainData.get("humidity") + "%\n");
                writer.write("Скорость ветра: " + ((JSONObject) hourData.get("wind")).get("speed") + "м/с\n");
                writer.write("\n"); // Разделяем записи для каждого часа
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Ошибка при записи данных в файл: " + e.getMessage());
        }
    }
}