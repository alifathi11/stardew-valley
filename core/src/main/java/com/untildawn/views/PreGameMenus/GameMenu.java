package com.untildawn.views.PreGameMenus;

import com.untildawn.Enums.GameMenus.Menus;
import com.untildawn.Enums.PreGameMenuCommands.GameMenuCommands;
import com.untildawn.controllers.PreGameControllers.GameMenuController;
import com.untildawn.views.AppMenu;


import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu implements AppMenu {
    @Override
    public void handleInput(Scanner sc) {
        String input = sc.nextLine();
        Matcher matcher;
        boolean matched = false;
        for (GameMenuCommands command : GameMenuCommands.values()) {
            if ((matcher = command.getMatcher(input)) != null) {
                matched = true;
                executeCommand(command, matcher, sc);
            }
        }
        if (!matched) {
            System.out.printf("Invalid command. please try again.\n");
        }
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public String prompt(String message) {
        return "";
    }

    private static void executeCommand(GameMenuCommands command, Matcher matcher, Scanner sc) {
        switch (command) {
            case NEW_GAME:
                System.out.printf(GameMenuController.makeNewGame(sc));
                break;
            case Exit_Menu:
                GameMenuController.changeMenu(Menus.PreGameMenus.MAIN_MENU, "main menu");
        }
    }
}
