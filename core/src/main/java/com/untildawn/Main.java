package com.untildawn;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.untildawn.models.UserDataHandler;
import com.untildawn.views.AppView;

import java.io.IOException;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */

//public class Main {
//    public static void main(String[] args) throws IOException {
//
//        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "off");
//        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "warn");
//
//        runProgram();
//    }
//
//    public static void runProgram() {
//        Runtime.getRuntime().addShutdownHook(new Thread(UserDataHandler::saveUsers));
//        UserDataHandler.loadUsers();
//
//        AppView appView = new AppView();
//        appView.run();
//    }
//}

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;

    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        batch.draw(image, 140, 210);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}
