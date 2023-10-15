import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SettingsTest {

    @Test
    public void testSettingsToString() {
        // Проверка, что метод toString возвращает ожидаемое значение
        String apiKeyValue = "6744201e3df69813a7d4958731ea1b11";
        Settings apiKeySetting = Settings.API_KEY;
        String actual = apiKeySetting.toString();
        assertEquals(apiKeyValue, actual);
    }
}
