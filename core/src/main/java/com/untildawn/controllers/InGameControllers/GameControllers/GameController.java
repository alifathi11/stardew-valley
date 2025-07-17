package com.untildawn.controllers.InGameControllers.GameControllers;

import com.untildawn.controllers.InGameControllers.InventoryController;
import com.untildawn.models.App;
import com.untildawn.models.Game;
import com.untildawn.views.InGameMenus.GameView;

public class GameController {
    private final Game game;
    private final PlayerController playerController;
    private GameView view;
    private InventoryController inventoryController;

    public void setView(GameView view) {
        this.view = view;
        inventoryController.setGameView(view);

    }

    public GameController() {
        game = App.getCurrentGame();
        playerController = new PlayerController();
        inventoryController = new InventoryController();
    }

    public void update(float delta) {
        playerController.update(delta);
        inventoryController.update();
    }

}
