package Lesson7.awesome_project.controller;

import java.sql.SQLException;

public interface IWeatherController {
    void onUserInput(int command) throws SQLException;
}
