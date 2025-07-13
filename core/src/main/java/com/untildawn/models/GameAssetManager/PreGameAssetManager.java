package com.untildawn.models.GameAssetManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class PreGameAssetManager {
    private static Skin skin;
    private static final Texture MenusBG = new Texture(Gdx.files.internal("Images/backgrounds/MenusBG.png"));
    public static Skin getSkin() {
        if (skin == null) {
            skin = new Skin(Gdx.files.internal("skin/flat-earth-ui.json"));
        }
        return skin;
    }
    public static Texture getMenusBG() {
        return MenusBG;
    }
}
