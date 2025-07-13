package com.untildawn.views.PreGameMenus;

import com.badlogic.gdx.Screen;
import com.untildawn.Enums.PreGameMenuCommands.SignupMenuCommands;
import com.untildawn.controllers.PreGameControllers.SignupMenuController;
import com.untildawn.views.AppMenu;


import java.util.Scanner;
import java.util.regex.Matcher;


public class SignupMenuView implements AppMenu, Screen {

    Scanner scanner;

    @Override
    public void handleInput(Scanner sc) {
        this.scanner = sc;

        String input = sc.nextLine();
        Matcher matcher;
        boolean matched = false;
        for (SignupMenuCommands command : SignupMenuCommands.values()) {
            if ((matcher = command.getMatcher(input)) != null) {
                matched = true;
                executeCommand(command, matcher, sc);
            }
        }
        if (!matched) {
            System.out.print("Invalid command. please try again.\n");
        }
    }

    private void executeCommand(SignupMenuCommands command, Matcher matcher, Scanner sc) {

        SignupMenuController controller = new SignupMenuController(this);

        switch (command) {
            case CHANGE_MENU:
                controller.changeMenu(matcher.group("menu"));
                break;
            case MENU_EXIT:
                controller.exitMenu();
                break;
            case SHOW_CURRENT_MENU:
                controller.showCurrentMenu();
                break;
            case REGISTER:
                controller.register(
                        sc
                        , matcher.group("username")
                        , matcher.group("password")
                        , matcher.group("nickname")
                        , matcher.group("email")
                        , matcher.group("gender"));
                break;
        }
    }

    public String prompt(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {

    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
