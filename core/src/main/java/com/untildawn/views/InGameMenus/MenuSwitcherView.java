package com.untildawn.views.InGameMenus;

import com.untildawn.Enums.InGameMenuCommands.MenuSwitcherCommands;
import com.untildawn.controllers.InGameControllers.MenuSwitcher;
import com.untildawn.views.AppMenu;


import java.util.Scanner;
import java.util.regex.Matcher;

public class MenuSwitcherView implements AppMenu {
    public void handleInput(Scanner sc) {
        printMenus();
        String input = sc.nextLine();
        Matcher matcher;
        boolean matched = false;
        for (MenuSwitcherCommands command : MenuSwitcherCommands.values()) {
            if ((matcher = command.getMatcher(input)) != null) {
                matched = true;
                executeCommand(command, matcher, input);
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

    private static void executeCommand(MenuSwitcherCommands command, Matcher matcher, String input) {
        MenuSwitcher controller = new MenuSwitcher();
        switch (command) {
            case MENU_ENTER -> System.out.println(controller.switchMenu(matcher));
        }
    }
    public void printMenus() {

        System.out.println("1. Action Menu\n"
                + "2. Cooking Menu\n"
                + "3. Crafting Menu\n"
                + "4. Inventory Menu\n"
                + "5. Home Menu\n"
                + "6. Shop Menu\n"
                + "7. Trade Menu\n"
                + "8. Exit Menu\n");
    }

}
