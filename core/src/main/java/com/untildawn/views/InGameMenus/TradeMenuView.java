package com.untildawn.views.InGameMenus;

import com.untildawn.Enums.InGameMenuCommands.ActionMenuCommands;
import com.untildawn.controllers.InGameControllers.TradeController;
import com.untildawn.views.AppMenu;


import java.util.Scanner;
import java.util.regex.Matcher;


public class TradeMenuView implements AppMenu {

    Scanner scanner;

    @Override
    public void handleInput(Scanner sc) {
        this.scanner = sc;
        String input = sc.nextLine();
        Matcher matcher;
        boolean matched = false;
        for (ActionMenuCommands command : ActionMenuCommands.values()) { // TODO: change to -> TradeMenuCommands
            if ((matcher = command.getMatcher(input)) != null) {
                matched = true;
                executeCommand(command, matcher, input);
            }
        }
        if (!matched) {
            System.out.print("Invalid command. please try again.\n");
        }
    }

    private void executeCommand(ActionMenuCommands command, Matcher matcher, String input) {
        TradeController controller = new TradeController(this);

        switch (command) {
            case TRADE:
                controller.trade(matcher.group("username"));
                break;
            case TRADE_HISTORY:
                controller.tradeHistory();
                break;
            case SWITCH_MENU:
                controller.changeMenu();
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
}
