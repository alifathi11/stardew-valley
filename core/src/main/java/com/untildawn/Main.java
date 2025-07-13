package com.untildawn;

import com.badlogic.gdx.Game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import com.untildawn.controllers.PreGameControllers.LoginMenuController;
import com.untildawn.views.PreGameMenus.LoginMenuView;


public class Main extends Game {
    private static SpriteBatch batch;
    private static Main main;

    @Override
    public void create() {
        main = this;
        batch = new SpriteBatch();
        main.setScreen(new LoginMenuView(new LoginMenuController()));
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        batch.end();
        super.render();

    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public static SpriteBatch getBatch() {
        return batch;
    }

    public static Main getMain() {
        return main;
    }
}
