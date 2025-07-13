package com.untildawn.views.PreGameMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.untildawn.Enums.GameMenus.Menus;
import com.untildawn.Enums.PreGameMenuCommands.ProfileMenuCommands;
import com.untildawn.Main;
import com.untildawn.controllers.PreGameControllers.ProfileMenuController;
import com.untildawn.models.App;
import com.untildawn.models.GameAssetManager.PreGameAssetManager;
import com.untildawn.views.AppMenu;


import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenuView implements AppMenu, Screen {
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
//                controller.changePassword(newPassword);
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
        } else if (ProfileMenuCommands.Menu_Exit.getMatcher(command) != null) {
            controller.changeMenu(Menus.PreGameMenus.MAIN_MENU, "main menu");
        } else {
            System.out.println("Invalid command!");
        }
    }

    private Stage stage;
    private Table table;
    private BitmapFont font;
    private Texture backgroundTexture;
    private String errorMessage;
    private Label profileTitle;
    private ProfileMenuController controller;
    private boolean isInUsernameChange;
    private boolean isInPasswordChange;
    private boolean isInNickNameChange;
    private boolean isInEmailChange;
    private boolean isInUserInfo;
    private TextButton changeUsername;
    private TextButton changePassword;
    private TextButton changeEmail;
    private TextButton changeNickname;
    private TextButton UserInfo;
    private TextField usernameField;
    private TextField newPasswordField;
    private TextField oldPasswordField;
    private TextField emailField;
    private TextField nicknameField;
    private TextButton backButton;
    private TextButton submitButton;
    private TextButton randomPasswordButton;

    public ProfileMenuView(ProfileMenuController controller) {
        Skin skin = PreGameAssetManager.getSkin();
        this.controller = controller;
        controller.setView(this);
        table = new Table();
        font = new BitmapFont();
        makeThemFalse();
        changeUsername = new TextButton("Change username", skin);
        changePassword = new TextButton("Change password", skin);
        changeEmail = new TextButton("Change email", skin);
        changeNickname = new TextButton("Change nickname", skin);
        UserInfo = new TextButton("User info", skin);
        usernameField = new TextField("", skin);
        newPasswordField = new TextField("", skin);
        oldPasswordField = new TextField("", skin);
        emailField = new TextField("", skin);
        nicknameField = new TextField("", skin);
        profileTitle = new Label("Profile", skin, "title");
        backButton = new TextButton("Back", skin, "naked");
        submitButton = new TextButton("Submit", skin);
        backgroundTexture = PreGameAssetManager.getMenusBG();
        randomPasswordButton = new TextButton("Generate random password", skin);

    }

    private void setUp() {
        if (isInUsernameChange) {
            usernameField.setMessageText("enter a new username");
            table.add(usernameField).width(600).padBottom(32f).height(80);
            table.row();
            addSubmitAndBackButton();

        } else if (isInPasswordChange) {
            newPasswordField.setMessageText("enter a new password");
            oldPasswordField.setMessageText("enter your current password");
            table.add(newPasswordField).width(600).padBottom(32f).height(80);
            table.row();
            table.add(oldPasswordField).width(600).padBottom(32f).height(80);
            table.row();
            table.add(randomPasswordButton).width(600).padBottom(32f).height(80);
            table.row();
            addSubmitAndBackButton();

        } else if (isInEmailChange) {
            emailField.setMessageText("enter a new email address");
            table.add(emailField).width(600).padBottom(32f).height(80);
            table.row();
            addSubmitAndBackButton();

        } else if (isInNickNameChange) {
            nicknameField.setMessageText("enter a new nickname");
            table.add(nicknameField).width(600).padBottom(32f).height(80);
            table.row();
            addSubmitAndBackButton();

        } else if (isInUserInfo) {

        } else {
            table.add(changeUsername).width(600).padBottom(32f).height(80);
            table.row();
            table.add(changePassword).width(600).padBottom(32f).height(80);
            table.row();
            table.add(changeEmail).width(600).padBottom(32f).height(80);
            table.row();
            table.add(changeNickname).width(600).padBottom(32f).height(80);
            table.row();
            table.add(UserInfo).width(600).padBottom(32f).height(80);
            table.row();

        }
    }

    @Override
    public void show() {
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        this.profileTitle.setFontScale(1.9f);
        this.font.setColor(Color.WHITE);

        table.setFillParent(true);
        table.clear();
        table.reset();
        table.center();
        table.add(profileTitle).padBottom(20f);
        table.row();
        setUp();
        table.add(backButton).width(600).padBottom(32f).height(80);
        stage.addActor(table);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0.5f, 0.5f, 0.5f, 1f);

        Main.getBatch().begin();
        Main.getBatch().draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        if (backButton.isChecked()) {
            if (isAllFalse()) {
                Main.getMain().setScreen(Menus.PreGameMenus.MAIN_MENU.getMenu());
                App.setCurrentMenu(Menus.PreGameMenus.MAIN_MENU);
            } else {
                makeThemFalse();
                show();
            }
            backButton.setChecked(false);
        }

        font.getData().setScale(2f);
        if (errorMessage != null && !errorMessage.isEmpty()) {
            font.draw(Main.getBatch(), errorMessage, Gdx.graphics.getWidth() / 2 - errorMessage.length() * 5, Gdx.graphics.getHeight() - 50);
        }
        Main.getBatch().end();
        stage.act(v);
        stage.draw();
        controller.handleProfileButtons();
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

    public TextButton getUserInfo() {
        return UserInfo;
    }

    public TextButton getChangeUsername() {
        return changeUsername;
    }

    public TextButton getChangePassword() {
        return changePassword;
    }

    public TextButton getChangeEmail() {
        return changeEmail;
    }

    public TextButton getChangeNickname() {
        return changeNickname;
    }

    public TextButton getBackButton() {
        return backButton;
    }

    public TextButton getSubmitButton() {
        return submitButton;
    }

    public TextButton getRandomPasswordButton() {
        return randomPasswordButton;
    }

    public void setInUsernameChange(boolean inUsernameChange) {
        makeThemFalse();
        isInUsernameChange = inUsernameChange;
        show();
    }

    public void setInPasswordChange(boolean inPasswordChange) {
        makeThemFalse();
        isInPasswordChange = inPasswordChange;
        show();
    }

    public void setInNickNameChange(boolean inNickNameChange) {
        makeThemFalse();
        isInNickNameChange = inNickNameChange;
        show();
    }

    public void setInEmailChange(boolean inEmailChange) {
        makeThemFalse();
        isInEmailChange = inEmailChange;
        show();
    }

    public void setInUserInfo(boolean inUserInfo) {
        makeThemFalse();
        isInUserInfo = inUserInfo;
        show();
    }

    public boolean isInUsernameChange() {
        return isInUsernameChange;
    }

    public boolean isInPasswordChange() {
        return isInPasswordChange;
    }

    public boolean isInNickNameChange() {
        return isInNickNameChange;
    }

    public boolean isInEmailChange() {
        return isInEmailChange;
    }

    public boolean isInUserInfo() {
        return isInUserInfo;
    }

    public void makeThemFalse() {
        isInUsernameChange = false;
        isInPasswordChange = false;
        isInNickNameChange = false;
        isInEmailChange = false;
        isInUserInfo = false;
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public TextField getNewPasswordField() {
        return newPasswordField;
    }

    public TextField getOldPasswordField() {
        return oldPasswordField;
    }

    public TextField getEmailField() {
        return emailField;
    }

    public TextField getNicknameField() {
        return nicknameField;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    private void addSubmitAndBackButton() {
        table.add(submitButton).width(600).padBottom(32f).height(80);
        table.row();
        table.add(backButton).width(600).padBottom(32f).height(80);
        table.row();
    }

    public Stage getStage() {
        return stage;
    }

    public boolean isAllFalse() {
        return !isInUsernameChange
                && !isInPasswordChange
                && !isInNickNameChange
                && !isInEmailChange
                && !isInUserInfo;
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public String prompt(String message) {
        return "";
    }
}
