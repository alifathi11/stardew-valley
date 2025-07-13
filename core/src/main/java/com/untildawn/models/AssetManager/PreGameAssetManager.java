package com.untildawn.models.AssetManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class PreGameAssetManager implements GameAssetManager {
    private static PreGameAssetManager preGameAssetManager;

    private PreGameAssetManager() {}

    public static PreGameAssetManager getInstance() {
        if (preGameAssetManager == null) {
            preGameAssetManager = new PreGameAssetManager();
        }
        return preGameAssetManager;
    }

    private Skin skin = new Skin(Gdx.files.internal("skin/flat-earth-ui.json"));
    public Skin getSkin() {
        return skin;
    }
}
