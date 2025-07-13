package com.untildawn.views.PreGameMenus;

import com.badlogic.gdx.Screen;
import com.untildawn.Enums.GameMenus.Menus;
import com.untildawn.Enums.PreGameMenuCommands.MainMenuCommands;
import com.untildawn.controllers.PreGameControllers.MainMenuController;
import com.untildawn.views.AppMenu;
import java.util.Scanner;

public class MainMenuView implements AppMenu, Screen {
    @Override
    public void handleInput(Scanner sc) {
        String command = sc.nextLine();
        command = command.trim();
        MainMenuController controller = new MainMenuController();
        if (MainMenuCommands.Current_Menu.getMatcher(command) != null) {
            System.out.println("you are in main menu");
        } else if (MainMenuCommands.Go_To_AvatarMenu.getMatcher(command) != null) {
            controller.changeMenu(Menus.PreGameMenus.AVATAR_MENU, "avatar menu");
        } else if (MainMenuCommands.Go_To_GameMenu.getMatcher(command) != null) {
            controller.changeMenu(Menus.PreGameMenus.GAME_MENU, "game menu");
        } else if (MainMenuCommands.Go_To_ProfileMenu.getMatcher(command) != null) {
            controller.changeMenu(Menus.PreGameMenus.PROFILE_MENU, "profile menu");
        } else if (MainMenuCommands.User_Logout.getMatcher(command) != null) {
            controller.userLogout();
        }else if (MainMenuCommands.Exit_Menu.getMatcher(command) != null) {
            controller.exit();
        }
        else {
            System.out.println("Invalid command");
        }
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public String prompt(String message) {
        return "";
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
