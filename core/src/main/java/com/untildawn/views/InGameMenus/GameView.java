package com.untildawn.views.InGameMenus;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.untildawn.Main;
import com.untildawn.controllers.InGameControllers.GameControllers.GameController;
import com.untildawn.models.App;
import com.untildawn.models.AssetManager.InventoryAssetManager;
import com.untildawn.models.AssetManager.ToolAssetManager;
import com.untildawn.models.Game;
import com.untildawn.models.Items.Inventory;
import com.untildawn.models.Items.ItemInstance;
import com.untildawn.models.Players.Player;

import java.util.Objects;

public class GameView implements InputProcessor, Screen {

    private OrthographicCamera camera;
    private Viewport viewport;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private ShapeRenderer shapeRenderer;
    private Game game;
    private Player player;
    private GameController controller;
    private Stage uiStage;
    private Table inventoryTable;
    private boolean isInventoryOpen = false;
    private Table fullInventoryTable = null;

    public GameView(GameController controller) {
        camera = new OrthographicCamera();
        viewport = new FillViewport(600, 400, camera);

        map = new TmxMapLoader().load("map.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        shapeRenderer = new ShapeRenderer();

        this.controller = controller;
        controller.setView(this);

        game = App.getCurrentGame();
        player = game.getCurrentPlayer();
        uiStage = new Stage(new ScreenViewport());

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(uiStage);
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);
        inventoryTable = new Table();
        fullInventoryTable = new Table();
    }

    private void updateCamera() {
        Player player = game.getCurrentPlayer();
        float playerX = player.getPosition().getX();
        float playerY = player.getPosition().getY();
        float playerWidth = player.getWidth();
        float playerHeight = player.getHeight();
        camera.position.set(player.getPosition().getX(), player.getPosition().getY(), 0);
        clampCameraToMap();
        camera.update();
    }

    private void clampCameraToMap() {
        MapProperties props = map.getProperties();
        int mapWidthInTiles = props.get("width", Integer.class);
        int mapHeightInTiles = props.get("height", Integer.class);
        int tilePixelWidth = props.get("tilewidth", Integer.class);
        int tilePixelHeight = props.get("tileheight", Integer.class);

        float mapWidth = mapWidthInTiles * tilePixelWidth;
        float mapHeight = mapHeightInTiles * tilePixelHeight;

        float halfCamWidth = camera.viewportWidth * 0.5f * camera.zoom;
        float halfCamHeight = camera.viewportHeight * 0.5f * camera.zoom;

        float minX = halfCamWidth;
        float minY = halfCamHeight;
        float maxX = mapWidth - halfCamWidth;
        float maxY = mapHeight - halfCamHeight;

        camera.position.x = MathUtils.clamp(camera.position.x, minX, maxX);
        camera.position.y = MathUtils.clamp(camera.position.y, minY, maxY);
    }

    @Override
    public void render(float delta) {
        controller.update(delta);
        updateCamera();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        mapRenderer.setView(camera);
        mapRenderer.render();

        Main.getBatch().begin();
        player.getSprite().setPosition(player.getPosition().getX(), player.getPosition().getY());
        player.getSprite().draw(Main.getBatch());
        player.getCollisionRect().move(player.getPosition().getX(), player.getPosition().getY());
        uiStage.act(delta);
        uiStage.draw();

        Main.getBatch().end();
    }

    @Override
    public boolean keyDown(int i) {
//        Gdx.input.isKeyPressed(Input.Keys)
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        mapRenderer.dispose();
        shapeRenderer.dispose();
    }

    public Stage getUiStage() {
        return uiStage;
    }

    public Table getInventoryTable() {
        return inventoryTable;
    }

    public boolean isInventoryOpen() {
        return isInventoryOpen;
    }

    public Table getFullInventoryTable() {
        return fullInventoryTable;
    }
    public void setIsInventoryOpen(boolean isInventoryOpen) {
        this.isInventoryOpen = isInventoryOpen;
    }

}
