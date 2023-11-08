import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DbServiceTest {

    @Mock
    private DataSource dataSource;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private Statement statement;
    @Mock
    private ResultSet resultSet;

    private DbService dbService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        dbService = new DbService(dataSource);
    }

    @Test
    public void testReadJsonFromFile() {
        // Create a temporary JSON test file
        try {
            File testJsonFile = createTestJsonFile();
            DbService dbService = new DbService(dataSource);

            // Call the method to read JSON data
            City[] cities = dbService.readJsonFromFile(testJsonFile.getAbsolutePath());

            // Perform assertions on the retrieved data
            assertNotNull(cities);
            assertEquals(2, cities.length); // Adjust this based on your test JSON data

            // Cleanup: Delete the temporary test file
            testJsonFile.delete();
        } catch (IOException e) {
            fail("Error creating test JSON file: " + e.getMessage());
        }
    }

    // Helper method to create a temporary JSON test file
    private File createTestJsonFile() throws IOException {
        File testJsonFile = File.createTempFile("test", ".json");
        Writer writer = new FileWriter(testJsonFile);
        writer.write("[{\"id\": 1, \"name\": \"City1\"}, {\"id\": 2, \"name\": \"City2\"}]");
        writer.close();
        return testJsonFile;
    }

    @Test
    public void testIsTableEmptyWithEmptyTable() throws SQLException {
        Connection connection = mock(Connection.class);
        when(dataSource.getConnection()).thenReturn(connection);

        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(connection.prepareStatement(any())).thenReturn(preparedStatement);

        ResultSet resultSet = mock(ResultSet.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(0);

        DbService dbService = new DbService(dataSource);
        int result = dbService.isTableEmpty(dataSource, "test_table");

        verify(connection).prepareStatement("SELECT COUNT(*) FROM test_table");
        verify(preparedStatement).executeQuery();
        verify(resultSet).next();
        verify(resultSet).getInt(1);

        assert (result == 0);
    }

    @Test
    public void testIsTableEmptyWithNonEmptyTable() throws SQLException {
        Connection connection = mock(Connection.class);
        when(dataSource.getConnection()).thenReturn(connection);

        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(connection.prepareStatement(any())).thenReturn(preparedStatement);

        ResultSet resultSet = mock(ResultSet.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(5);

        DbService dbService = new DbService(dataSource);
        int result = dbService.isTableEmpty(dataSource, "test_table");

        verify(connection).prepareStatement("SELECT COUNT(*) FROM test_table");
        verify(preparedStatement).executeQuery();
        verify(resultSet).next();
        verify(resultSet).getInt(1);

        assert (result == 5);
    }

    @Test
    public void testCreateThreeDayForecastTable() throws SQLException {
        DbService dbService = new DbService(dataSource);

        // Mock the database interactions
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(connection.createStatement()).thenReturn(statement);

        // Call the method to create the "ThreeDayForecast" table
        dbService.createThreeDayForecastTable(dataSource);

        // Verify that the SQL DDL statement was executed
        verify(preparedStatement).executeUpdate();

        // Cleanup: Close resources
        verify(preparedStatement).close();
        verify(connection).close();
    }

    @Test
    public void testSaveThreeDayForecastToDatabase() throws SQLException {
        DbService dbService = new DbService(dataSource);

        // Mock the database interactions
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        // Create a sample JSON object
        JsonObject threeDayForecastData = createSampleThreeDayForecastData();

        // Call the method to save three-day forecast data to the database
        dbService.saveThreeDayForecastToDatabase(dataSource, threeDayForecastData);

        // Verify that the prepared statement was executed
        verify(preparedStatement, times(1)).executeBatch(); // Adjust the count based on your data

        // Verify the correct order of closing resources
        InOrder inOrder = inOrder(resultSet, preparedStatement, connection);
        inOrder.verify(preparedStatement).close();
        inOrder.verify(connection).close();
    }

    private JsonObject createSampleThreeDayForecastData() {
        String jsonString = "{\"list\":[{\"dt\":1698894000,\"main\":{\"temp\":11.39,\"pressure\":967},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\"}],\"clouds\":{\"all\":75},\"wind\":{\"speed\":9.95},\"dt_txt\":\"2023-11-02 03:00:00\"}],\"city\":{\"id\":2643743}}";
        JsonObject data = JsonParser.parseString(jsonString).getAsJsonObject();
        return data;
    }
}
