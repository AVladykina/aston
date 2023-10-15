import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class WeatherAppTest {

    @Test
    public void testMainMethodOutput() throws ParseException {
        // Перенаправляем стандартный вывод в буфер
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Здесь вызывайте метод main из WeatherApp с имитацией аргументов командной строки (args)
        // Пример:
        WeatherApp.main(new String[0]);

        // Здесь можно проверить содержимое вывода (outContent.toString()) на наличие ожидаемых строк
        // Например, можно использовать assertTrue для проверки наличия определенного текста в выводе
        assertTrue(outContent.toString().contains("Текущая погода:"));

        // Восстанавливаем стандартный вывод
        System.setOut(System.out);
    }
}