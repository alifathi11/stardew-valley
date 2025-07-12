package com.untildawn.views.PreGameMenus;

import com.untildawn.Enums.PreGameMenuCommands.LoginMenuCommands;
import com.untildawn.controllers.PreGameControllers.LoginMenuController;
import com.untildawn.views.AppMenu;


import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenuView implements AppMenu {
    private LoginMenuController controller = new LoginMenuController(this);
    private Scanner scanner;
    @Override
    public void handleInput(Scanner sc) {

        if (checkAutoLogin()) {
            return;
        }

        this.scanner = sc;
        String input = sc.nextLine();
        Matcher matcher;
        boolean matched = false;
        for (LoginMenuCommands command : LoginMenuCommands.values()) {
            if ((matcher = command.getMatcher(input)) != null) {
                matched = true;
                executeCommand(command, matcher, sc);
            }
        }
        if (!matched) {
            System.out.printf("Invalid command. please try again.\n");
        }
    }

    private void executeCommand(LoginMenuCommands command, Matcher matcher, Scanner sc) {

        switch (command) {
            case CHANGE_MENU:
                System.out.println(controller.changeMenu(matcher.group("menu")));
                break;
            case MENU_EXIT:
                System.out.println(controller.exitMenu());
                break;
            case SHOW_CURRENT_MENU:
                System.out.println(controller.showCurrentMenu());
                break;
            case LOGIN:
                boolean stayLoggedIn = matcher.group("stayLoggedIn") != null;
                System.out.println(controller.login(
                        sc
                        , matcher.group("username")
                        , matcher.group("password")
                        , stayLoggedIn));
                break;
            case FORGET_PASSWORD:
                System.out.println(controller.forgetPassword(sc, matcher.group("username")));
                break;
        }
    }


    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }

    @Override
    public String prompt(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }

    public boolean checkAutoLogin() {
        if (controller.autoLogin()) return true;
        return false;
    }
}

