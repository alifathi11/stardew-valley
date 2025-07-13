package com.untildawn.controllers.PreGameControllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.untildawn.Enums.GameMenus.Menu;
import com.untildawn.Enums.GameMenus.Menus;
import com.untildawn.Enums.PreGameMenuCommands.ProfileMenuCommands;
import com.untildawn.models.App;
import com.untildawn.models.GameAssetManager.PreGameAssetManager;
import com.untildawn.models.GameHistory;
import com.untildawn.models.User;
import com.untildawn.views.PreGameMenus.ProfileMenuView;
import com.untildawn.views.PreGameMenus.TerminalAnimation;

import java.util.ArrayList;
import java.util.Collections;

public class ProfileMenuController {
    ProfileMenuView view;

    public void setView(ProfileMenuView view) {
        this.view = view;
    }

    public void changeUsername(String newUsername, String oldUsername) {
        if (ProfileMenuCommands.Username_Validation.getMatcher(newUsername) == null) {
            System.out.println("username format is invalid");
        } else if (App.getCurrentUser().getUsername().equals(newUsername)) {
            System.out.println("Please enter a new username!");
        } else if (App.userExists(newUsername)) {
            System.out.println("Username already exists!");
        } else {
            User user = App.getUser(oldUsername);
            user.setUsername(newUsername);
            App.getUsers().remove(newUsername);
            App.addUser(newUsername, user);
            System.out.println("Username has been changed to " + newUsername + "!");
        }
    }

    public int getHighestCoinsEarned() {
        ArrayList<Integer> coinsEarned = new ArrayList<>();
        for (GameHistory gameHistory : App.getCurrentUser().getGameHistory()) {
            coinsEarned.add(gameHistory.getCoinsEarned());
        }
        if (coinsEarned.isEmpty()) {
            return 0;
        }
        return Collections.max(coinsEarned);
    }

    public void exitMenu() {
        App.setCurrentMenu(Menus.PreGameMenus.MAIN_MENU);
    }

    public void changePassword(String newPassword, String oldPassword) {
        if (!App.getCurrentUser().verifyPassword(oldPassword)) {
            view.setErrorMessage("Password does not match!");
        } else if (App.getCurrentUser().getPasswordHash().equals(newPassword)) {
            view.setErrorMessage("Please enter a new password!");
        } else if (ProfileMenuCommands.Password_Validation.getMatcher(newPassword) == null) {
            view.setErrorMessage("Password format is invalid");
        } else {
            String passwordHash = SHA256Hasher.hashPassword(newPassword);
            App.getCurrentUser().setPasswordHash(passwordHash);
            view.setErrorMessage("Password has been changed to " + newPassword + "!");
        }
    }

    public void changeEmail(String email) {
        if (App.getCurrentUser().getEmail().equals(email)) {
            view.setErrorMessage("Please enter a new email!");
        } else if (ProfileMenuCommands.Email_Validation.getMatcher(email) == null) {
            view.setErrorMessage("Email format is invalid");
        } else {
            App.getCurrentUser().setEmail(email);
            view.setErrorMessage("Email has been changed to " + email + "!");
        }
    }

    public void changeNickname(String nickname) {
        if (App.getCurrentUser().getName().equals(nickname)) {
            view.setErrorMessage("Please enter a new nickname!");
        } else {
            App.getCurrentUser().setName(nickname);
            view.setErrorMessage("Nickname has been changed to " + nickname + "!");
        }
    }

    public void changeMenu(Menu menu, String menuName) {
        try {
            TerminalAnimation.loadingAnimation("redirecting to " + menuName + " menu");
        } catch (InterruptedException e) {

        }
        App.setCurrentMenu(menu);
        System.out.println("Your are now in " + menuName);
    }

    public void handleProfileButtons() {
        TextButton changeUsername = view.getChangeUsername();
        TextButton changePassword = view.getChangePassword();
        TextButton changeEmail = view.getChangeEmail();
        TextButton changeNickname = view.getChangeNickname();
        TextButton UserInfo = view.getUserInfo();

        if (changeUsername.isChecked()) {
            view.setInUsernameChange(true);
            changeUsername.setChecked(false);

        } else if (changePassword.isChecked()) {
            view.setInPasswordChange(true);
            changePassword.setChecked(false);

        } else if (changeEmail.isChecked()) {
            view.setInEmailChange(true);
            changeEmail.setChecked(false);

        } else if (changeNickname.isChecked()) {
            view.setInNickNameChange(true);
            changeNickname.setChecked(false);

        } else if (UserInfo.isChecked()) {
            view.setInUserInfo(true);
            UserInfo.setChecked(false);
            showUserInfoDialog();

        } else if(view.getRandomPasswordButton().isChecked()) {
            showRandomPasswordDialog();
            view.getRandomPasswordButton().setChecked(false);
        }
        else if (view.getSubmitButton().isChecked()) {
            if (view.isInUsernameChange()) {
                String newUsername = view.getUsernameField().getText();
                changeUsername(newUsername, App.getCurrentUser().getUsername());

            } else if (view.isInPasswordChange()) {
                String newPassword = view.getNewPasswordField().getText();
                String oldPassword = view.getOldPasswordField().getText();
                changePassword(newPassword, oldPassword);

            } else if (view.isInEmailChange()) {
                String newEmail = view.getEmailField().getText();
                changeEmail(newEmail);

            } else if (view.isInNickNameChange()) {
                String newNickname = view.getNicknameField().getText();
                changeNickname(newNickname);

            } else if (view.isInUserInfo()) {
            }

            view.getSubmitButton().setChecked(false);
        }
    }
    private void showRandomPasswordDialog() {
        Skin skin = PreGameAssetManager.getSkin();
        String randomPassword = PasswordGenerator.generatePassword(App.getCurrentUser().getUsername());
        Dialog dialog = new Dialog("Random Password", skin) {
            @Override
            protected void result(Object object) {
                boolean accepted = (Boolean) object;
                if (accepted) {
                    view.getNewPasswordField().setText(randomPassword);
                }
            }
        };
        dialog.text("Generated Password:\n" + randomPassword);

        dialog.button("Accept", true);
        dialog.button("Deny", false);

        dialog.show(view.getStage());

        dialog.setSize(300, 300);
        dialog.setPosition(
            (Gdx.graphics.getWidth() - dialog.getWidth()) / 2f,
            (Gdx.graphics.getHeight() - dialog.getHeight()) / 2f
        );
    }
    public void showUserInfoDialog() {
        Skin skin = PreGameAssetManager.getSkin();
        User user = App.getCurrentUser();
        int highestCoins = getHighestCoinsEarned();

        String infoText =
            "Username: " + user.getUsername() + "\n" +
                "Nickname: " + user.getName() + "\n" +
                "Email: " + user.getEmail() + "\n" +
                "Highest Coins Earned: " + highestCoins + "\n" +
                "Number of Games: " + user.getGameHistory().size();

        Dialog dialog = new Dialog("User Info", skin) {
            @Override
            protected void result(Object object) {
                boolean accepted = (Boolean) object;
                if (accepted) {
                    view.makeThemFalse();
                    view.show();
                }
            }
        };
        dialog.text(infoText);
        dialog.button("Close", true);

        dialog.show(view.getStage());

        dialog.setSize(400, 300);
        dialog.setPosition(
            (Gdx.graphics.getWidth() - dialog.getWidth()) / 2f,
            (Gdx.graphics.getHeight() - dialog.getHeight()) / 2f
        );
    }
}
