package com.untildawn.models.AssetManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class PreGameAssetManager implements GameAssetManager {
    private static PreGameAssetManager preGameAssetManager;

    private static final Texture MenusBG = new Texture(Gdx.files.internal("Images/backgrounds/MenusBG.png"));
    private Skin skin = new Skin(Gdx.files.internal("skin/flat-earth-ui.json"));

    public static Texture getMenusBG() {
        return MenusBG;
    }

    private PreGameAssetManager() {}

    public static PreGameAssetManager getInstance() {
        if (preGameAssetManager == null) {
            preGameAssetManager = new PreGameAssetManager();
        }
        return preGameAssetManager;
    }

    public Skin getSkin() {
        return skin;
    }


}
