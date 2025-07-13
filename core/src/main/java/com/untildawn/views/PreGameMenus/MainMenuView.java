package com.untildawn.views.PreGameMenus;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.untildawn.Enums.GameMenus.Menus;
import com.untildawn.Enums.PreGameMenuCommands.MainMenuCommands;
import com.untildawn.Main;
import com.untildawn.controllers.PreGameControllers.MainMenuController;
import com.untildawn.models.App;
import com.untildawn.models.GameAssetManager.PreGameAssetManager;
import com.untildawn.models.User;
import com.untildawn.views.AppMenu;
import java.util.Scanner;

public class MainMenuView implements AppMenu, Screen {
    private Game game;
    private Stage stage;
    private Skin skin;
    private MainMenuController controller;
    private Texture backgroundTexture;
    public MainMenuView(MainMenuController controller) {
        this.skin = PreGameAssetManager.getSkin();
        this.backgroundTexture = PreGameAssetManager.getMenusBG();
        this.controller =controller;
        this.stage = new Stage(new ScreenViewport());

        buildUI();
    }

    private void buildUI() {
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        stage.addActor(table);

        User currentPlayer = App.getCurrentUser();
        if (currentPlayer != null) {


            Texture avatarTexture = currentPlayer.getAvatar();
            if (avatarTexture != null) {
                Image avatarImage = new Image(avatarTexture);
                table.add(avatarImage).size(128, 128).padBottom(20);
                table.row();
            }
        }

        Label titleLabel = new Label("Stardew Valley", skin, "title");
        table.add(titleLabel).padBottom(50);
        table.row();

        TextButton gameMenuButton = new TextButton("Game Menu", skin);
        gameMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.changeMenu(Menus.PreGameMenus.GAME_MENU);
                Main.getMain().setScreen(Menus.PreGameMenus.GAME_MENU.getMenu());
                App.setCurrentMenu(Menus.PreGameMenus.GAME_MENU);

            }
        });

        TextButton profileMenuButton = new TextButton("Profile Menu", skin);
        profileMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.changeMenu(Menus.PreGameMenus.PROFILE_MENU);
            }
        });

        TextButton avatarMenuButton = new TextButton("Avatar Menu", skin);
        avatarMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.changeMenu(Menus.PreGameMenus.AVATAR_MENU);
            }
        });

        TextButton logoutButton = new TextButton("Logout", skin);
        logoutButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.userLogout();
            }
        });

        TextButton exitButton = new TextButton("Exit Game", skin);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.exit();
            }
        });

        float buttonWidth = 300f;
        float buttonHeight = 50f;
        float pad = 10f;

        table.add(gameMenuButton).width(buttonWidth).height(buttonHeight).pad(pad);
        table.row();
        table.add(profileMenuButton).width(buttonWidth).height(buttonHeight).pad(pad);
        table.row();
        table.add(avatarMenuButton).width(buttonWidth).height(buttonHeight).pad(pad);
        table.row();
        table.add(logoutButton).width(buttonWidth).height(buttonHeight).pad(pad).padTop(30);
        table.row();
        table.add(exitButton).width(buttonWidth).height(buttonHeight).pad(pad);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        Main.getBatch().begin();
        Main.getBatch().draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Main.getBatch().end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        // ما Texture آواتار را اینجا dispose نمی کنیم چون این کلاس صاحب آن نیست.
        stage.dispose();
    }

    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}



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


}
