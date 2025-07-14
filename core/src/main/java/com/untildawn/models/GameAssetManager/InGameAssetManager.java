package com.untildawn.models.GameAssetManager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

import java.io.File;

public class InGameAssetManager implements GameAssetManager {

    private static InGameAssetManager inGameAssetManager;
    private Animation<Texture> characterAnimation = buildCharacterAnimation("character1", "walk");
    private Texture characterTexture = characterAnimation.getKeyFrames()[0];

    private InGameAssetManager() {}

    public static InGameAssetManager getInGameAssetManager() {
        if (inGameAssetManager == null) {
            inGameAssetManager = new InGameAssetManager();
        }
        return inGameAssetManager;
    }

    private Animation<Texture> buildCharacterAnimation(String characterName, String type) {

        String pathToFolder = String.format("assets/%s/%s", characterName, type);
        int numberOfFrames = countFiles(pathToFolder);

        Texture[] textureArray = new Texture[numberOfFrames];

        for (int i = 1; i <= numberOfFrames; i++) {
            String rawPath = String.format("%s/%s/sam_%d.png", characterName, type, i);
            Texture texture = new Texture(rawPath);
            textureArray[i - 1] = texture;
        }

        return new Animation<>(0.1f, textureArray);
    }

    public Animation<Texture> getCharacterAnimation() {
        return characterAnimation;
    }

    public Texture getCharacterTexture() {
        return characterTexture;
    }

    private int countFiles(String path) {

        File folder = new File(path);


        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null) {
            int fileCount = 0;
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    fileCount++;
                }
            }
            return fileCount;
        } else {
            return 0;
        }
    }
}
