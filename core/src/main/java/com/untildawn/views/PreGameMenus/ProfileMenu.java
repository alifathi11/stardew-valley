package com.untildawn.views.PreGameMenus;

import com.untildawn.Enums.GameMenus.Menus;
import com.untildawn.Enums.PreGameMenuCommands.ProfileMenuCommands;
import com.untildawn.controllers.PreGameControllers.ProfileMenuController;
import com.untildawn.models.App;
import com.untildawn.views.AppMenu;


import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu implements AppMenu {
    @Override
    public void handleInput(Scanner sc) {
        String command = sc.nextLine();
        command = command.trim();
        ProfileMenuController controller = new ProfileMenuController();
        Matcher matcher;
        if (ProfileMenuCommands.Current_Menu.getMatcher(command) != null) {
            System.out.println("you are in profile menu");
        } else if ((matcher = ProfileMenuCommands.Change_Username.getMatcher(command)) != null) {
            String username = matcher.group("username");
            username = username.trim();
            if (ProfileMenuCommands.Username_Validation.getMatcher(username) == null) {
                System.out.println("username format is invalid");
            } else if (App.getCurrentUser().getUsername().equals(username)) {
                System.out.println("Please enter a new username!");
            } else if (App.userExists(username)) {
                System.out.println("Username already exists!");
            } else {
                controller.changeUsername(username, App.getCurrentUser().getUsername());
                System.out.println("Username has been changed to " + username + "!");
            }
        } else if ((matcher = ProfileMenuCommands.Change_NickName.getMatcher(command)) != null) {
            String nickname = matcher.group("nickname");
            nickname = nickname.trim();
            if (App.getCurrentUser().getName().equals(nickname)) {
                System.out.println("Please enter a new nickname!");
            } else {
                controller.changeNickname(nickname);
                System.out.println("Nickname has been changed to " + nickname + "!");
            }
        } else if ((matcher = ProfileMenuCommands.Change_Email.getMatcher(command)) != null) {
            String email = matcher.group("email");
            email = email.trim();
            if (App.getCurrentUser().getEmail().equals(email)) {
                System.out.println("Please enter a new email!");
            } else if (ProfileMenuCommands.Email_Validation.getMatcher(email) == null) {
                System.out.println("Email format is invalid");
            } else {
                controller.changeEmail(email);
                System.out.println("Email has been changed to " + email + "!");
            }
        } else if ((matcher = ProfileMenuCommands.Change_Password.getMatcher(command)) != null) {
            String newPassword = matcher.group("newPassword");
            String oldPassword = matcher.group("oldPassword");
            newPassword = newPassword.trim();
            oldPassword = oldPassword.trim();
            if (!App.getCurrentUser().verifyPassword(oldPassword)) {
                System.out.println("Password does not match!");
            } else if (App.getCurrentUser().getPasswordHash().equals(newPassword)) {
                System.out.println("Please enter a new password!");
            } else if (ProfileMenuCommands.Password_Validation.getMatcher(newPassword) == null) {
                System.out.println("Password format is invalid");
            } else {
                controller.changePassword(newPassword);
                System.out.println("Password has been changed to " + newPassword + "!");
            }
        } else if (ProfileMenuCommands.User_Info.getMatcher(command) != null) {
            System.out.println("Username: " + App.getCurrentUser().getUsername());
            System.out.println("Nickname: " + App.getCurrentUser().getName());
            System.out.println("Highest coins earned: " + controller.getHighestCoinsEarned());
            System.out.println("Number of games: " + App.getCurrentUser().getGameHistory().size());
        } else if (ProfileMenuCommands.Menu_Exit.getMatcher(command) != null) {
            controller.exitMenu();
        } else if (ProfileMenuCommands.Go_To_MainMenu.getMatcher(command) != null) {
            System.out.println("you can't go to main menu");
        } else if(ProfileMenuCommands.Menu_Exit.getMatcher(command) != null) {
            controller.changeMenu(Menus.PreGameMenus.MAIN_MENU, "main menu");
        }
        else {
            System.out.println("Invalid command!");
        }
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public String prompt(String message) {
        return "";
    }
}
