package com.untildawn.views.InGameMenus;

import com.untildawn.views.AppMenu;


import java.util.Scanner;

public class FarmingView implements AppMenu {

    Scanner scanner;
    @Override
    public void handleInput(Scanner sc) {
        scanner = sc;
    }


    public String prompt(String message) {
        System.out.println(message);
        String input = scanner.nextLine();
        return input;
    }

    public void showMessage(String message) {
        System.out.println(message);
    }
}
