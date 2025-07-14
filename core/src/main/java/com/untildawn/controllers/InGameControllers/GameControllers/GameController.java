package com.untildawn.controllers.InGameControllers.GameControllers;

import com.untildawn.models.App;
import com.untildawn.models.Game;
import com.untildawn.views.InGameMenus.GameView;

public class GameController {
    private final Game game;
    private final PlayerController playerController;
    private GameView view;

    public void setView(GameView view) {
        this.view = view;
    }

    public GameController() {
        game = App.getCurrentGame();
        playerController = new PlayerController();
    }

    public void update(float delta) {
        playerController.update(delta);
    }
}
