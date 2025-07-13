package com.untildawn.controllers.PreGameControllers;

import com.untildawn.Enums.GameMenus.Menus;
import com.untildawn.models.App;
import com.untildawn.models.User;
import com.untildawn.models.UserDataHandler;
import com.untildawn.views.PreGameMenus.LoginMenuView;
import com.untildawn.views.PreGameMenus.TerminalAnimation;


import java.util.Scanner;

import static com.untildawn.controllers.PreGameControllers.SecurityQuestions.askPersonalSecurityQuestion;


public class LoginMenuController {

    private LoginMenuView view;
    public LoginMenuController(LoginMenuView view) {
        this.view = view;
    }

    public boolean autoLogin() {
        String rememberedUser = SessionManager.loadSession();
        if (rememberedUser != null) {
            User user = App.getUser(rememberedUser);
            if (user != null) {
                App.setCurrentUser(user);
                this.view.showMessage("🔓 Auto-logged in as " + rememberedUser);
                App.setCurrentMenu(Menus.PreGameMenus.MAIN_MENU);
                this.view.showMessage("You are now in main menu.\n");
                return true;
            }
        }

        return false;
    }

    public String changeMenu(String menu) {
        switch (menu.toLowerCase()) {
            case "signup menu", "signupmenu", "signup" -> {
                App.setCurrentMenu(Menus.PreGameMenus.SIGNUP_MENU);
                try {
                    TerminalAnimation.loadingAnimation("redirecting to signup menu");
                    return "\nYou are now in signup menu.\n";
                } catch (InterruptedException e) {
                    return "Problem redirecting to signup menu. Please try again later.\n";
                }
            }
            case "login menu", "loginmenu", "login" -> {
                return "You are in login menu now!\n";
            }
            default -> {
                return "You only have access to signup and login menus right now.\n";
            }
        }
    }

    public String exitMenu() {
        try {
            TerminalAnimation.loadingAnimation("Exiting the game\n");
        } catch (InterruptedException e) {
            return "Problem exiting the game. Please try again later.\n";
        }

        UserDataHandler.saveUsers();

        System.exit(0);
        return "";
    }
    public String showCurrentMenu() {
        return "You are in login menu.\n";
    }

    public String login(Scanner sc, String username, String password, boolean stayLoggedIn) {
        if(!App.userExists(username)) {
            return "User not found.\n";
        }

        if (!App.getUser(username).verifyPassword(password)) {
            return "Password is not correct.\n";
        }
        if (!SecurityQuestions.askSecurityQuestion(sc)) {
            return "Login failed. Please try again.\n";
        }

        if (stayLoggedIn) {
            SessionManager.saveSession(username);
        }

        App.setCurrentUser(App.getUser(username));
        App.setCurrentMenu(Menus.PreGameMenus.MAIN_MENU);
        try {
            TerminalAnimation.loadingAnimation("Redirecting to main menu");
        } catch (InterruptedException e) {
            return "Problem logging you in. Please try again later.\n";
        }
        return "Logged in successfully. You are now in main menu.\n";

}
    public String forgetPassword(Scanner sc, String username) {
        if (!App.userExists(username)) {
            return "User not found.\n";
        }
        System.out.printf("Enter your email address.\n");
        String email = sc.nextLine();
        String correctEmail = App.getUser(username).getEmail();
        if (!email.equals(correctEmail)) {
            return "Incorrect email address.\n";
        }

        if (!askPersonalSecurityQuestion(username, sc)) return "Retrieving failed. Please try again later.";

        User currentUser = App.getCurrentUser();
        String newPassword = this.view.prompt("Enter your new password: ");
        String passwordHash = SHA256Hasher.hashPassword(newPassword);
        currentUser.setPasswordHash(passwordHash);

        return "Your password has been changed successfully.";
    }
}
