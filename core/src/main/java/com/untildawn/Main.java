package com.untildawn;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.untildawn.Enums.GameConsts.Gender;
import com.untildawn.controllers.InGameControllers.GameControllers.GameController;
import com.untildawn.controllers.PreGameControllers.LoginMenuController;
import com.untildawn.models.App;
import com.untildawn.models.Items.ItemLoader;
import com.untildawn.models.MapElements.Position;
import com.untildawn.models.Players.Player;
import com.untildawn.models.Question;
import com.untildawn.models.User;
import com.untildawn.models.UserDataHandler;
import com.untildawn.views.InGameMenus.GameView;
import com.untildawn.views.PreGameMenus.LoginMenuView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main extends Game {
    private static SpriteBatch batch;
    private static Main main;

    @Override
    public void create() {
        main = this;
        batch = new SpriteBatch();
        main.setScreen(new GameView(new GameController())); // temp
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
