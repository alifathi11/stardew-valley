package com.untildawn;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.untildawn.Enums.GameConsts.Gender;
import com.untildawn.Enums.GameMenus.Menus;
import com.untildawn.models.App;
import com.untildawn.models.Question;
import com.untildawn.models.User;

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

public class Main extends Game {
    private static SpriteBatch batch;
    private static Main main;

    @Override
    public void create() {
        main = this;
        batch = new SpriteBatch();
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
