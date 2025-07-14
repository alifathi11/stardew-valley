package com.untildawn;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.untildawn.Enums.GameConsts.Gender;
import com.untildawn.Enums.GameMenus.Menus;
import com.untildawn.controllers.InGameControllers.GameControllers.GameController;
import com.untildawn.controllers.PreGameControllers.LoginMenuController;
import com.untildawn.models.App;
import com.untildawn.models.Items.ItemLoader;
import com.untildawn.models.MapElements.Position;
import com.untildawn.models.Players.Player;
import com.untildawn.models.GameAssetManager.PreGameAssetManager;
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
        PreGameAssetManager.load();
        PreGameAssetManager.manager.finishLoading();
        PreGameAssetManager.createAnimations();

        User ali1 = new User("ali1", "ali1", "ali1", "ali1", Gender.OTHER, new Question("ali1", "ali1"));
        User ali2 = new User("ali2", "ali2", "ali2", "ali2", Gender.OTHER, new Question("ali2", "ali2"));
        User ali3 = new User("ali3", "ali3", "ali3", "ali3", Gender.OTHER, new Question("ali3", "ali3"));
        User ali4 = new User("ali4", "ali4", "ali4", "ali4", Gender.OTHER, new Question("ali4", "ali4"));
        App.addUser("ali1", ali1);
        App.addUser("ali2", ali2);
        App.addUser("ali3", ali3);
        App.addUser("ali4", ali4);

        App.setCurrentUser(ali1);

        main.setScreen(Menus.PreGameMenus.MAIN_MENU.getMenu());
        App.setCurrentMenu(Menus.PreGameMenus.MAIN_MENU);
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
