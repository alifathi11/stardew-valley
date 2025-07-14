package com.untildawn.controllers.InGameControllers.GameControllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.untildawn.Main;
import com.untildawn.models.App;
import com.untildawn.models.MapElements.Position;
import com.untildawn.models.Players.Player;

public class PlayerController {
    private Player player;

    public PlayerController() {
        player = App.getCurrentGame().getCurrentPlayer();
    }

    public void update(float delta) {
        handleInput();
    }

    public void handleInput() {
        float dx = 0, dy = 0;
        float speed = player.getSpeed();

        if (Gdx.input.isKeyPressed(Input.Keys.A)) dx -= speed;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) dx += speed;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) dy += speed;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) dy -= speed;

        movePlayer(dx, dy);
    }

    private void movePlayer(float dx, float dy) {
        Position pos = player.getPosition();
        pos.setX(pos.getX() + dx);
        pos.setY(pos.getY() + dy);
    }

    public boolean canMoveTo(float newX, float newY) {

        return true;
    }
}

