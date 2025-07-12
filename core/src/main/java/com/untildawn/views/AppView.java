package com.untildawn.views;

import com.untildawn.Enums.GameMenus.Menus;
import com.untildawn.models.App;


import java.util.Scanner;


/*
    Main loop of the program!
 */
public class AppView {
    public void run() {
        Scanner sc = new Scanner(System.in);
        do {
            App.getCurrentMenu().check(sc);
        } while (App.getCurrentMenu()
                != Menus.PreGameMenus.EXIT_MENU &&
                App.getCurrentMenu()
                != Menus.InGameMenus.EXIT_MENU);
    }
}
