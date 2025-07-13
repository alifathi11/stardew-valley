package com.untildawn.controllers.PreGameControllers;

import com.untildawn.Enums.GameMenus.Menu;
import com.untildawn.Enums.GameMenus.Menus;
import com.untildawn.controllers.utils.SessionManager;
import com.untildawn.models.App;
import com.untildawn.models.UserDataHandler;
import com.untildawn.views.PreGameMenus.MainMenuView;
import com.untildawn.views.PreGameMenus.TerminalAnimation;


public class MainMenuController {

    private MainMenuView view;

    public void setView(MainMenuView view) {
        this.view = view;
    }

    public void handleButtons() {

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
    public void userLogout() {
        SessionManager.clearSession();
        App.setCurrentUser(null);
        App.setCurrentMenu(Menus.PreGameMenus.LOGIN_MENU);
        System.out.println("User logged out successfully");
    }

    public void exit() {
        UserDataHandler.saveUsers();
        try {
            TerminalAnimation.loadingAnimation("Exiting the program");
        } catch (InterruptedException e) {

        }
        System.exit(0);
    }


}
