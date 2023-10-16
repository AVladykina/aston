/**
 * Перечисление {@code Settings} предоставляет конфигурационные настройки для Weather App.
 * Оно включает API-ключи и базовый URL для доступа к данным о погоде из внешнего сервиса.
 */
public enum Settings {

    API_KEY("6744201e3df69813a7d4958731ea1b11"),
    API_KEY_SECOND("c56543cb53f13f82e35678b2304286b2"),
    BASE_URL("http://api.openweathermap.org/data/2.5"),
    BASE_URL_HOURLY("https://pro.openweathermap.org/data/2.5");

    private String value;
    Settings(String value) {
        this.value = value;
    }
    public String toString() {
        return value;
    }
}