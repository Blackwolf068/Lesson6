package Lesson7.awesome_project;

import Lesson7.awesome_project.view.IUserInterface;
import Lesson7.awesome_project.view.UserInterface;

public class Main {

    public static void main(String[] args) {
        IUserInterface userInterface = new UserInterface();
        userInterface.showUI();
    }
}
