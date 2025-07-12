package com.untildawn.controllers.PreGameControllers;

import com.untildawn.Enums.GameMenus.Menu;
import com.untildawn.Enums.GameMenus.Menus;
import com.untildawn.models.App;
import com.untildawn.models.GameHistory;
import com.untildawn.models.User;
import com.untildawn.views.PreGameMenus.TerminalAnimation;

import java.util.ArrayList;
import java.util.Collections;

public class ProfileMenuController {
    public void changeUsername(String newUsername, String oldUsername) {
        User user = App.getUser(oldUsername);
        user.setUsername(newUsername);
        App.getUsers().remove(newUsername);
        App.addUser(newUsername, user);
    }
    public int getHighestCoinsEarned() {
        ArrayList<Integer> coinsEarned = new ArrayList<>();
        for (GameHistory gameHistory : App.getCurrentUser().getGameHistory()) {
            coinsEarned.add(gameHistory.getCoinsEarned());
        }
        if(coinsEarned.isEmpty()) {
            return 0;
        }
        return Collections.max(coinsEarned);
    }
    public void exitMenu() {
        App.setCurrentMenu(Menus.PreGameMenus.MAIN_MENU);
    }
    public void changePassword(String password) {
        String passwordHash = SHA256Hasher.hashPassword(password);
        App.getCurrentUser().setPasswordHash(passwordHash);
    }
    public void changeEmail(String email) {
        App.getCurrentUser().setEmail(email);
    }
    public void changeNickname(String nickname) {
        App.getCurrentUser().setName(nickname);
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
}
