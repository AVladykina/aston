# Branch aston_hw_2
## Overview for aston_hw_2
Task conditions:
*  Implement a program to obtain weather forecast through a REST request. Your program should fetch weather data from an external service, process it, and display it in a user-friendly format.

* Additional task:
Weather should be displayed in three variations (at the time of the request, hourly for the entire day, for the next three days).

You can use any open APIs for requesting information.

## WeatherApp

The Weather App is a Java application designed to retrieve weather information from an external service, process it, and present it in various formats. The app consists of three classes: Settings, WeatherService, and WeatherApp.


## Project Structure WeatherApp

The project consists of several Java classes organized as follows:
* `Settings`: class is an enumeration that provides essential configuration parameters for the Weather App. It includes API keys and the base URL for accessing weather data from an external service.
* `WeatherService`: class is the core of the Weather App. It provides methods to interact with the weather API, process weather data, and display or save it to files. Here's an overview of its functionality:

  * `getCurrentWeather`: Retrieves the current weather for a specified city.
  * `getHourlyForecast`: Fetches the hourly weather forecast for a city.
  * `getThreeDayForecast`: Retrieves a three-day weather forecast for a city.
  * `displayWeather`: Displays the current weather data in a user-readable format.
  * `displayHourlyForecast`: Presents the hourly weather forecast data.
  * `displayThreeDayForecast`: Shows a three-day weather forecast.
  * `writeDisplayWeatherToFile`: Writes the current weather data to a file.
  * `writeThreeDayForecastToFile`: Writes the three-day weather forecast to a file.
  * `writeHourlyForecastToFile`: Writes the hourly weather forecast to a file.
* `WestherApp`: class is the application's entry point. It utilizes the WeatherService to request weather data, parse it, and display or save the information. The main method demonstrates how to use the WeatherService class to perform the following tasks:

    1. Fetch the current weather for a specified city.
    2. Write the current weather data to a file.
    3. Retrieve a three-day weather forecast.
    4. Display the three-day forecast.
    5. Write the three-day forecast to a file.
    6. Get an hourly weather forecast for the same city using a different API key.
    7. Display the hourly weather forecast.
    8. Write the hourly forecast to a file.

##Dependencies

The app uses the org.json.simple library for parsing JSON data. Ensure that you have this library in your project's dependencies.

## How to Use WeatherApp

To use the WeatherApp, follow these steps:
1. To run the Weather App, make sure you have the necessary API keys and configure the file paths for saving the weather data. You can customize the city for which you want to retrieve weather information by modifying the city variable in the WeatherApp class.
2. To run the application, execute the main method located in the `WeatherApp` class.
