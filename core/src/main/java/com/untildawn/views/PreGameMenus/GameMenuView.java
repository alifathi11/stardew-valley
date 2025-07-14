package com.untildawn.views.PreGameMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.untildawn.Enums.GameMenus.Menus;
import com.untildawn.Enums.PreGameMenuCommands.GameMenuCommands;
import com.untildawn.Main;
import com.untildawn.controllers.PreGameControllers.GameMenuController;
import com.untildawn.controllers.PreGameControllers.ProfileMenuController;
import com.untildawn.models.GameAssetManager.PreGameAssetManager;
import com.untildawn.views.AppMenu;


import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenuView implements AppMenu, Screen {
    private Stage stage;
    private Table table;
    private BitmapFont font;
    private Texture backgroundTexture;
    private String errorMessage;
    private Label gameMenuTitle;
    private GameMenuController controller;
    private TextButton newGameButton;
    private TextButton exitButton;
    private TextButton loadGameButton;
    public GameMenuView(GameMenuController controller) {
        this.controller = controller;
        controller.setView(this);
        table = new Table();
        font = new BitmapFont();
        newGameButton = new TextButton("New Game", PreGameAssetManager.getSkin());
        exitButton = new TextButton("Exit", PreGameAssetManager.getSkin());
        gameMenuTitle = new Label("Game Menu", PreGameAssetManager.getSkin());
        loadGameButton = new TextButton("Load Game", PreGameAssetManager.getSkin());
        backgroundTexture = PreGameAssetManager.getGameMenuBG();
    }


    @Override
    public void show() {
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        this.gameMenuTitle.setFontScale(1.9f);
        this.font.setColor(Color.WHITE);
        stage.setKeyboardFocus(table);
        table.setFillParent(true);

        table.add(newGameButton).width(600).padBottom(32f).height(80);
        table.row();
        table.add(loadGameButton).width(600).padBottom(32f).height(80);
        table.row();
        table.add(exitButton).width(600).padBottom(32f).height(80);
        table.row();
        stage.addActor(table);
        controller.setListener();

    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0.5f, 0.5f, 0.5f, 1f);

        Main.getBatch().begin();
        Main.getBatch().draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        font.getData().setScale(2f);
        if (errorMessage != null && !errorMessage.isEmpty()) {
            font.draw(Main.getBatch(), errorMessage, Gdx.graphics.getWidth() / 2 - errorMessage.length() * 5, Gdx.graphics.getHeight() - 50);
        }
        Main.getBatch().end();
        stage.act(v);
        stage.draw();
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

    @Override
    public void handleInput(Scanner sc) {
    }

    private static void executeCommand(GameMenuCommands command, Matcher matcher, Scanner sc) {
        switch (command) {
            case NEW_GAME:
                System.out.printf(GameMenuController.makeNewGame(sc));
                break;
            case Exit_Menu:
                GameMenuController.changeMenu(Menus.PreGameMenus.MAIN_MENU, "main menu");
        }
    }
    @Override
    public void showMessage(String message) {

    }

    @Override
    public String prompt(String message) {
        return "";
    }

    public TextButton getNewGameButton() {
        return newGameButton;
    }

    public TextButton getExitButton() {
        return exitButton;
    }

    public TextButton getLoadGameButton() {
        return loadGameButton;
    }
}
