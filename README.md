# Readme for Aston

## Overview
This README provides an overview and explanation of the projects in Aston's Java course.

## Contents
1. [Branches](#branches)
2. 
   
## Branches
1. [aston_hw_1](https://github.com/AVladykina/aston/tree/aston_hw_1)
2. [aston_hw_2](https://github.com/AVladykina/aston/tree/aston_hw_2)
3. [aston_hw_3](https://github.com/AVladykina/aston/tree/aston_hw_3)
4. [aston_hw_4](https://github.com/AVladykina/aston/tree/aston_hw_4)

## Tasks
1. Реализовать свой ArrayList. Реализоватьалгоритмquicksortдляреализованнойвами реализации ArrayList.
2. Реализовать программу для получения прогноза погоды через REST запрос.
   * Ваша программа должна получить данные о погоде, у стороннего сервиса, обработать их и вывести в удобочитаемом виде.
   * Доп задача: Погода должна выводиться в трёх вариантах (на момент времени запроса, на весь день по часам, на три дня)
   * Для запроса информации можно использовать любые открытые api.
3. Подключить базу данных к REST приложению.
   * Создать базу данных городов. Где для каждого города будет сохраняться погода. Все результаты вызова сервиса погоды сохранять в таблицу.
   * Должна быть таблица минимум по пяти городам. Для каждого города сохранены данные о температуре, облачности, количеству осадков.
   * Реализовать чтение из базы, чтобы формировать анализ данных погоды.
4. Реализовать многопользовательское приложение (2 и более пользователей могут изменять данные в вашем приложении)
   * Приложение должно обладать функционлом создания, изменения, удаления пользователей
   * Хранить данные о том по какому городу, пользователь запрашивал погоду последний раз когда пользователь входит в систему выдавать ему погоду по этому городу
   * В вашем приложении всё также должна хранится база данных о запросах погоды пользователем
   * Вы должны реализовать не менее 3 таблиц в 3 нормальной форме
   * У каждого пользователя история подзапросов
   * Создать связь между двумя таблицами many to many
   * Доп. задание. Реализовать транзакционный процесс, в котором  метод выполняет две и более операций записи в бд в рамках транзакции
   * Реализуйте авторизацию пользователя
