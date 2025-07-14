package com.untildawn.controllers.PreGameControllers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.untildawn.Enums.GameMenus.Menu;
import com.untildawn.Enums.GameMenus.Menus;
import com.untildawn.Main;
import com.untildawn.models.App;
import com.untildawn.models.UserDataHandler;
import com.untildawn.views.PreGameMenus.MainMenuView;
import com.untildawn.views.PreGameMenus.ProfileMenuView;
import com.untildawn.views.PreGameMenus.TerminalAnimation;


public class MainMenuController {
    private Game game;
    private Skin skin;
    private MainMenuView view;
    public void setView(MainMenuView view) {
        this.view = view;
    }

    public void changeMenu(Menu menu) {
        // کلاس های Screen زیر باید بعدا ساخته شوند.
        // شما باید آنها را از حالت کامنت خارج کنید وقتی ساخته شدند.
        if (menu == Menus.PreGameMenus.AVATAR_MENU) {
            // game.setScreen(new AvatarMenuScreen(game, skin));
            System.out.println("Switching to Avatar Menu...");
        } else if (menu == Menus.PreGameMenus.GAME_MENU) {
            // game.setScreen(new GameMenuScreen(game, skin));
            System.out.println("Switching to Game Menu...");
        } else if (menu == Menus.PreGameMenus.PROFILE_MENU) {
            Main.getMain().setScreen(new ProfileMenuView(new ProfileMenuController()));
        }
    }

    public void userLogout() {
        // SessionManager.clearSession();
        App.setCurrentUser(null);
         Main.getMain().setScreen(Menus.PreGameMenus.LOGIN_MENU.getMenu());
         App.setCurrentMenu(Menus.PreGameMenus.LOGIN_MENU);
        System.out.println("User logged out. Switching to Login Screen...");
    }

    public void exit() {
        UserDataHandler.saveUsers();
        Gdx.app.exit();
    }


public void changeMenu(Menu menu, String menuName) {
        try {
            TerminalAnimation.loadingAnimation("redirecting to " + menuName + " menu");
        }
        catch (InterruptedException e) {

        }
        App.setCurrentMenu(menu);
        System.out.println("Your are now in " + menuName);
    }
//    public void userLogout() {
//        SessionManager.clearSession();
//        App.setCurrentUser(null);
//        App.setCurrentMenu(Menus.PreGameMenus.LOGIN_MENU);
//        System.out.println("User logged out successfully");
//    }

//    public void exit() {
//        UserDataHandler.saveUsers();
//        try {
//            TerminalAnimation.loadingAnimation("Exiting the program");
//        } catch (InterruptedException e) {
//
//        }
//        System.exit(0);
//    }


}
