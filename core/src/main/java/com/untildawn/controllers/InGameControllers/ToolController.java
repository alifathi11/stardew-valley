package com.untildawn.controllers.InGameControllers;

import com.untildawn.Enums.ItemConsts.ItemAttributes;
import com.untildawn.Enums.ItemConsts.ItemIDs;
import com.untildawn.Enums.ItemConsts.ItemType;
import com.untildawn.Enums.NPCConsts.NPCConst;
import com.untildawn.models.Animals.Animal;
import com.untildawn.models.App;
import com.untildawn.models.Game;
import com.untildawn.models.Items.Inventory;
import com.untildawn.models.Items.ItemDefinition;
import com.untildawn.models.Items.ItemInstance;
import com.untildawn.models.MapElements.Tile;
import com.untildawn.models.Players.Player;
import com.untildawn.views.InGameMenus.ActionMenuView;


import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;

public class ToolController {
    static ActionMenuView view;

    public ToolController(ActionMenuView view) {
        ToolController.view = view;
    }

    public void equipTool(Matcher matcher) {
        Game game = App.getCurrentGame();
        if (!game.isPlayerActive(game.getCurrentPlayer())) {
            view.showMessage("You are ran out of energy for this turn!");
            return;
        }
        String toolName = matcher.group("toolName").toLowerCase();
        Inventory inventory = game.getCurrentPlayer().getInventory();
        boolean found = false;
        for (ItemDefinition itemDefinition : App.getItemDefinitions()) {
            if (itemDefinition.getId().name().equalsIgnoreCase(toolName)) {
                found = true;
            }
        }
        if (!found) {
            view.showMessage("please enter a valid tool name!");
            return;
        }
        ItemInstance tool = null;

        for (Map.Entry<ItemIDs, ArrayList<ItemInstance>> entry : inventory.getItems().entrySet()) {
            ArrayList<ItemInstance> items = entry.getValue();
            for (ItemInstance item : items) {
                if (item.getDefinition().getId().name().equalsIgnoreCase(toolName)) {
                    tool = item;
                }
            }

        }

        if (tool == null) {
            view.showMessage("you don't have " + toolName + " in your inventory!");
            return;
        }
        game.getCurrentPlayer().setCurrentTool(tool);
        view.showMessage("your current tool has been set to " + toolName + "!");
    }

    public void showCurrentTool() {
        Game game = App.getCurrentGame();
        if (!game.isPlayerActive(game.getCurrentPlayer())) {
            view.showMessage("You are ran out of energy for this turn!");
            return;
        }
        Player currentPlayer = game.getCurrentPlayer();
        if (currentPlayer.getCurrentTool() == null) {
            view.showMessage("you don't have a current tool!");
            return;
        }
        view.showMessage(currentPlayer.getCurrentTool().getDefinition().getDisplayName().toLowerCase());
    }

    public void showInventoryTools() {
        Game game = App.getCurrentGame();
        if (!game.isPlayerActive(game.getCurrentPlayer())) {
            view.showMessage("You are ran out of energy for this turn!");
            return;
        }
        Inventory inventory = game.getCurrentPlayer().getInventory();
        StringBuilder toolsStr = new StringBuilder();
        for (Map.Entry<ItemIDs, ArrayList<ItemInstance>> entry : inventory.getItems().entrySet()) {
            ArrayList<ItemInstance> items = entry.getValue();
            for (ItemInstance item : items) {
                if (item.getDefinition().getType().equals(ItemType.tool)) {
                    toolsStr.append(item.getDefinition().getDisplayName().toLowerCase()).append("\n");
                }
            }
        }
        view.showMessage(toolsStr.toString());
    }

    public void useTool(Matcher matcher) {
        Game game = App.getCurrentGame();
        if (!game.isPlayerActive(game.getCurrentPlayer())) {
            view.showMessage("You are ran out of energy for this turn!");
            return;
        }
        String direction = matcher.group("direction").trim();
        Player player = game.getCurrentPlayer();
        Tile tile = player.getPlayerTile(game);
        ItemInstance tool = player.getCurrentTool();
        if (tool == null) {
            view.showMessage("you don't have a tool in your hand!");
            return;
        }
        switch (direction) {
            case "up" -> applyTool(tool, game.getGameMap().getTile
                    ((int) tile.getPosition().getY() - 1, (int) tile.getPosition().getX()), player, game);
            case "down" -> applyTool(tool, game.getGameMap().getTile
                    ((int) tile.getPosition().getY() + 1, (int) tile.getPosition().getX()), player, game);
            case "left" -> applyTool(tool, game.getGameMap().getTile
                    ((int) tile.getPosition().getY(), (int) tile.getPosition().getX() - 1), player, game);
            case "right" -> applyTool(tool, game.getGameMap().getTile
                    ((int) tile.getPosition().getY(), (int) tile.getPosition().getX() + 1), player, game);
            case "up left" -> applyTool(tool, game.getGameMap().getTile
                    ((int) tile.getPosition().getY() - 1, (int) tile.getPosition().getX() - 1), player, game);
            case "up right" -> applyTool(tool, game.getGameMap().getTile
                    ((int) tile.getPosition().getY() - 1, (int) tile.getPosition().getX() + 1), player, game);
            case "down left" -> applyTool(tool, game.getGameMap().getTile
                    ((int) tile.getPosition().getY() + 1, (int) tile.getPosition().getX() - 1), player, game);
            case "down right" -> applyTool(tool, game.getGameMap().getTile
                    ((int) tile.getPosition().getY() + 1, (int) tile.getPosition().getX() + 1), player, game);
            default -> view.showMessage("please select a valid direction!");
        }
    }

    public void applyTool(ItemInstance tool, Tile tile, Player player, Game game) {
        String name = tool.getDefinition().getDisplayName().toLowerCase();
        if (name.contains("hoe")) {
            if (!checkIfPlayerHasEnoughEnergy(player, tool, player.getAbilities().getAbilityLevel
                    (player.getAbilities().getFarmingAbility()))) {
                view.showMessage("You don't have enough energy!");
                return;
            }
            player.reduceEnergy(player.getAbilities().getFarmingAbility(), tool, player, true, game,
                    (int) tool.getDefinition().getAttribute(ItemAttributes.energyCost));
            if (tile.getPlowed()) {
                view.showMessage("This tile has been plowed!");
                return;
            }
            if (tile.getItem().getDefinition().getType().equals(ItemType.lake)) {
                view.showMessage("You can't use hoe in lake!");
                return;
            }
            if (!tile.isEmpty()) {
                view.showMessage("This tile is not empty!");
                return;
            }
            if (tile.getPlowed()) {
                view.showMessage("This tile has already been plowed!");
                return;
            }
            tile.setPlowed(true);
            view.showMessage("You've successfully plowed the tile!");
        } else if (name.contains("pickaxe")) {
            if (!checkIfPlayerHasEnoughEnergy(player, tool, player.getAbilities().getAbilityLevel
                    (player.getAbilities().getMiningAbility()))) {
                view.showMessage("You don't have enough energy!");
                return;
            }
            player.reduceEnergy(player.getAbilities().getMiningAbility(), tool, player, true, game,
                    (int) tool.getDefinition().getAttribute(ItemAttributes.energyCost));
            if (tile.getItem().getDefinition().getType().equals(ItemType.lake)) {
                view.showMessage("You can't use pickaxe in lake!");
                return;
            }
            if (tile.isEmpty() && tile.isPlowed()) {
                tile.setPlowed(false);
                view.showMessage("This tile has been successfully unplowed!");
                return;
            }
            if (tile.getItem().getDefinition().getType().equals(ItemType.rock)) {
                tile.setItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("VOID"))));
                view.showMessage("You've removed rock from this tile!");
                return;
            }
            if (!tile.getItem().isDroppedByPlayer()) {
                view.showMessage("This item hasn't been dropped by player!");
                return;
            }
            player.getInventory().addItem(tile.getItem());
            if (player.getAbilities().getAbilityLevel(player.getAbilities().getMiningAbility()) >= 2)
                player.getInventory().addItem(tile.getItem());
            tile.setItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("VOID"))));
            player.getAbilities().increaseMiningAbility();
            view.showMessage("Item has been successfully removed from the tile!");
        } else if (name.contains("axe")) {
            if (!checkIfPlayerHasEnoughEnergy(player, tool, player.getAbilities().getAbilityLevel
                    (player.getAbilities().getNatureAbility()))) {
                view.showMessage("You don't have enough energy!");
                return;
            }
            player.reduceEnergy(player.getAbilities().getNatureAbility(), tool, player, true, game,
                    (int) tool.getDefinition().getAttribute(ItemAttributes.energyCost));
            if (tile.getItem().getDefinition().getType().equals(ItemType.lake)) {
                view.showMessage("You can't use axe in lake!");
                return;
            }
            if (tile.isEmpty()) {
                view.showMessage("This tile is empty!");
                return;
            }
            if (tile.getItem().getDefinition().getType().equals(ItemType.wood)) {//TODO
                player.getInventory().addItem(tile.getItem());
                tile.setItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("VOID"))));
                view.showMessage("1 wood has been successfully added to the inventory!");
                return;
            }
            if (tile.getItem().getDefinition().getType().equals(ItemType.tree)) {
                String seedId = tile.getItem().getAttribute(ItemAttributes.source).toString();
                ItemInstance seed = new ItemInstance(Objects.requireNonNull(App.getItemDefinition(seedId)));
                player.getInventory().addItem(seed);
                tile.setItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("wood"))));
                view.showMessage("You've successfully cut the tree and a " + seedId + " is added to your inventory!");
                return;
            }

            view.showMessage("Use axe in a proper tile");
        } else if (name.contains("watering can")) {
            if (!checkIfPlayerHasEnoughEnergy(player, tool, player.getAbilities().getAbilityLevel
                    (player.getAbilities().getFarmingAbility()))) {
                view.showMessage("You don't have enough energy!");
                return;
            }
            player.reduceEnergy(player.getAbilities().getFarmingAbility(), tool, player, false, game,
                    (int) tool.getDefinition().getAttribute(ItemAttributes.energyCost));
            if (tile.getItem().getDefinition().getType().equals(ItemType.lake)) {
                addWaterToWateringCan(tool);
                return;
            }
            if (tile.getItem().getDefinition().getType().equals(ItemType.all_crops)) {
                if (tile.hasGiantPlant()) {
                    for (Tile giantTile : tile.getGiantGroup()) {
                        giantTile.setWatered(true);
                    }
                } else {
                    tile.setWatered(true);
                }
                return;
            }
            view.showMessage("Use the can for lake or plants!");
        } else if (name.contains("fishing pole")) {

        } else if (name.contains("scythe")) {
            if (!checkIfPlayerHasEnoughEnergy(player, tool, 0)) {
                view.showMessage("You don't have enough energy!");
                return;
            }
            player.reduceEnergy(0, tool, player, false, game,
                    (int) tool.getDefinition().getAttribute(ItemAttributes.energyCost));
            if (tile.getItem().getDefinition().getType().equals(ItemType.fiber)) {
                cutFiber(tile, player);
                return;
            }
            ItemInstance item = tile.getItem();
            if (item.getDefinition().getType().equals(ItemType.all_crops)) {
                if (tile.getDayLeftFromPlant() <= tile.getDayPassedFromPlant()) {
                    if (tile.hasGiantPlant()) {
                        harvestGiantCrop(tile, player);
                    } else {
                        harvestCrop(tile, player);
                    }
                    return;
                } else {
                    view.showMessage("This plant isn't harvestable yet!");
                    return;
                }
            }
            view.showMessage("Use scythe for crops or fibers!");
        } else if (name.contains("milk pale")) {
            if (!checkIfPlayerHasEnoughEnergy(player, tool, 0)) {
                view.showMessage("You don't have enough energy!");
                return;
            }
            player.reduceEnergy(0, tool, player, false, game,
                    (int) tool.getDefinition().getAttribute(ItemAttributes.energyCost));
            if (tile.getItem().getDefinition().getType().equals(ItemType.barn_animal)) {
                Animal animal = (Animal) tile.getItem();
                milkAnimal(tool, player, animal);
                return;
            }
            view.showMessage("Use milk pale on cows, sheep or goats!");

        } else if (name.contains("shear")) {
            if (!checkIfPlayerHasEnoughEnergy(player, tool, 0)) {
                view.showMessage("You don't have enough energy!");
                return;
            }
            player.reduceEnergy(0, tool, player, false, game,
                    (int) tool.getDefinition().getAttribute(ItemAttributes.energyCost));
            if (tile.getItem().getDefinition().getId().equals(ItemIDs.sheep)) {
                Animal animal = (Animal) tile.getItem();
                cutWool(player, animal);
            }
        } else {
            view.showMessage("Please select a valid tool!");
        }
    }

    public boolean checkIfPlayerHasEnoughEnergy(Player player, ItemInstance tool, int ability) {
        if (ability == 4 && player.getEnergy() < (int) tool.getDefinition().getAttribute(ItemAttributes.energyCost) - 1) {
            return false;
        }
        if (player.getEnergy() < (int) tool.getDefinition().getAttribute(ItemAttributes.energyCost)) {
            return false;
        }
        return true;
    }

    public void addWaterToWateringCan(ItemInstance tool) {
        int capacity = (int) tool.getDefinition().getAttribute(ItemAttributes.capacity);
        int durability = (int) tool.getDefinition().getAttribute(ItemAttributes.durability);
        if (durability + 1 > capacity) {
            view.showMessage("your can is full!");
            return;
        }
        tool.increaseDurability(durability + 1);
        int finalDurability = durability + 1;
        view.showMessage("Your can has " + finalDurability + " water");
    }

    public void cutFiber(Tile tile, Player player) {
        player.getInventory().addItem(tile.getItem());
        tile.setItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("VOID"))));
        view.showMessage("You've cut fiber!");
    }

    public void harvestCrop(Tile tile, Player player) {
        player.getInventory().addItem(tile.getItem());
        ItemInstance plant = tile.getItem();
        if (plant.getDefinition().hasAttribute(ItemAttributes.oneTime)
                && !(boolean) plant.getAttribute(ItemAttributes.oneTime)) {
            tile.setItem(new ItemInstance(Objects.requireNonNull(
                    App.getItemDefinition(plant.getDefinition().getId().name()))));
            tile.setDayPassedFromPlant(0);
            tile.setDayLeftFromPlant((int) plant.getAttribute(ItemAttributes.regrowthTime));
        }
        tile.setItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("VOID"))));
        player.getAbilities().increaseFarmingAbility();
        view.showMessage("You've harvested crop!");
    }

    public void harvestGiantCrop(Tile tile, Player player) {
        for (int i = 0; i < 10; i++) {
            player.getInventory().addItem(tile.getItem());
        }
        ItemInstance plant = tile.getItem();
        if (plant.getDefinition().hasAttribute(ItemAttributes.oneTime)
                && !(boolean) plant.getAttribute(ItemAttributes.oneTime)) {
            for (Tile giantTile : tile.getGiantGroup()) {
                giantTile.setItem(new ItemInstance(Objects.requireNonNull
                        (App.getItemDefinition(plant.getDefinition().getId().name()))));
                tile.setDayPassedFromPlant(0);
                tile.setDayLeftFromPlant((int) plant.getAttribute(ItemAttributes.regrowthTime));
            }
        } else {
            for (Tile giantTile : tile.getGiantGroup()) {
                giantTile.setItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition("VOID"))));
            }
        }
        player.getAbilities().increaseFarmingAbility();
        view.showMessage("You've harvested giant crop!");
    }

    public void milkAnimal(ItemInstance tool, Player player, Animal animal) {
        tool.setAttribute(ItemAttributes.isFull, true);
        player.getInventory().addItem
                (new ItemInstance(Objects.requireNonNull(App.getItemDefinition("milk"))));
        player.getAbilities().increaseFarmingAbility();
        animal.increaseFriendShip(5);
        view.showMessage("You've milked the animal!");
    }

    public void cutWool(Player player, Animal animal) {
        animal.setAttribute(ItemAttributes.isCut, true);
        player.getInventory().addItem
                (new ItemInstance(Objects.requireNonNull(App.getItemDefinition("wool"))));
        player.getAbilities().increaseFarmingAbility();
        animal.increaseFriendShip(5);
        view.showMessage("You've collected sheep wool!");
    }

    public void upgradeTool(Matcher matcher) {
        Game game = App.getCurrentGame();
        Player player = game.getCurrentPlayer();
        int playerX = (int) player.getPosition().getX();
        int playerY = (int) player.getPosition().getY();
        int pierreX = NPCConst.ShopPositions.PierreGeneralStore.getX();
        int pierreY = NPCConst.ShopPositions.PierreGeneralStore.getY();
        int smithX = NPCConst.ShopPositions.BlackSmith.getX();
        int smithY = NPCConst.ShopPositions.BlackSmith.getY();
        Inventory inventory = player.getInventory();
        String toolName = matcher.group("toolName").trim().toLowerCase();
        ItemIDs id;
        try {
            id = ItemIDs.valueOf(toolName);
        } catch (IllegalArgumentException e) {
            view.showMessage("Invalid tool name!");
            return;
        }
        if (toolName.contains("backpack")) {
            if (!(pierreX - 1 <= playerX && playerX <= pierreX + 1
                    && playerY - 1 <= playerY && playerY <= pierreY + 1)) {
                view.showMessage("You should be near pierre's store to upgrade your backpack!");
                return;
            }
            if (!inventory.hasItem(id)) {
                view.showMessage("This item does not exist in your inventory!");
                return;
            }
            ItemInstance backpack = inventory.getItem(id);
            if (player.getWallet().getCoin() < (int) backpack.getAttribute(ItemAttributes.upgradeCost)) {
                view.showMessage("You can't afford to upgrade your backpack!");
                return;
            }
            if (toolName.contains("deluxe")) {
                view.showMessage("You can't upgrade your backpack anymore!");
                return;
            }
            inventory.trashItemAll(id);
            player.getWallet().decreaseCoin((int) backpack.getAttribute(ItemAttributes.upgradeCost));
            if (toolName.contains("base")) {
                inventory.addItem(new ItemInstance(Objects.requireNonNull
                        (App.getItemDefinition("big_backpack"))));
                view.showMessage("You've upgraded your backpack to large backpack!");
            } else if (toolName.contains("big")) {
                inventory.addItem(new ItemInstance(Objects.requireNonNull
                        (App.getItemDefinition("deluxe_backpack"))));
                view.showMessage("You've upgraded your backpack to deluxe backpack!");
            }
        } else {
            if (!(smithX - 1 <= playerX && playerX <= smithX + 1
                    && smithY - 1 <= playerY && playerY <= smithY + 1)) {
                view.showMessage("You should be near blackSmith store to upgrade your tools!");
                return;
            }
            if (!inventory.hasItem(id)) {
                view.showMessage("This item does not exist in your inventory!");
                return;
            }
            ItemInstance tool = inventory.getItem(id);
            if (player.getWallet().getCoin() < (int) tool.getAttribute(ItemAttributes.upgradeCost)) {
                view.showMessage("You can't afford to upgrade your tool!");
                return;
            }
            if (toolName.contains("base")) {
                if (!inventory.hasItem(ItemIDs.valueOf("copper_bar"), 5)) {
                    view.showMessage("You need copper bar to upgrade your tool!");
                    return;
                }
                inventory.trashItemAll(id);
                inventory.trashItem(ItemIDs.valueOf("copper_bar"), 5);
                player.getWallet().decreaseCoin((int) tool.getAttribute(ItemAttributes.upgradeCost));
                String newToolName = toolName.replace("base", "copper");
                inventory.addItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition(newToolName))));
                view.showMessage("You've upgraded your tool!");

            } else if (toolName.contains("copper")) {
                if (!inventory.hasItem(ItemIDs.valueOf("iron_bar"), 5)) {
                    view.showMessage("You need copper bar to upgrade your tool!");
                    return;
                }
                inventory.trashItemAll(id);
                inventory.trashItem(ItemIDs.valueOf("iron_bar"), 5);
                player.getWallet().decreaseCoin((int) tool.getAttribute(ItemAttributes.upgradeCost));
                String newToolName = toolName.replace("copper", "iron");
                inventory.addItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition(newToolName))));
                view.showMessage("You've upgraded your tool!");
            } else if (toolName.contains("iron")) {
                if (!inventory.hasItem(ItemIDs.valueOf("gold_bar"), 5)) {
                    view.showMessage("You need copper bar to upgrade your tool!");
                    return;
                }
                inventory.trashItemAll(id);
                inventory.trashItem(ItemIDs.valueOf("gold_bar"), 5);
                player.getWallet().decreaseCoin((int) tool.getAttribute(ItemAttributes.upgradeCost));
                String newToolName = toolName.replace("iron", "golden");
                inventory.addItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition(newToolName))));
                view.showMessage("You've upgraded your tool!");
            } else if (toolName.contains("golden")) {
                if (!inventory.hasItem(ItemIDs.valueOf("iridium_bar"), 5)) {
                    view.showMessage("You need copper bar to upgrade your tool!");
                    return;
                }
                inventory.trashItemAll(id);
                inventory.trashItem(ItemIDs.valueOf("iridium_bar"), 5);
                player.getWallet().decreaseCoin((int) tool.getAttribute(ItemAttributes.upgradeCost));
                String newToolName = toolName.replace("golden", "iridium");
                inventory.addItem(new ItemInstance(Objects.requireNonNull(App.getItemDefinition(newToolName))));
                view.showMessage("You've upgraded your tool!");
            } else if (toolName.contains("iridium")) {
                view.showMessage("You can't afford to upgrade your tool anymore!");
                return;
            }
        }
    }

    public void removeItemFromInventory(ItemInstance trashCan, Inventory inventory, ItemInstance tool) {
    }
}
