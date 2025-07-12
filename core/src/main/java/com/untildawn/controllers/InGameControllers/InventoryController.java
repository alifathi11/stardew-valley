package com.untildawn.controllers.InGameControllers;

import com.untildawn.Enums.GameMenus.Menus;
import com.untildawn.Enums.ItemConsts.ItemAttributes;
import com.untildawn.Enums.ItemConsts.ItemIDs;
import com.untildawn.models.App;
import com.untildawn.models.Game;
import com.untildawn.models.Items.Inventory;
import com.untildawn.models.Items.ItemDefinition;
import com.untildawn.models.Items.ItemInstance;
import com.untildawn.models.Players.Player;
import com.untildawn.views.InGameMenus.InventoryMenu;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

public class InventoryController {
    InventoryMenu view;

    public InventoryController(InventoryMenu view) {
        this.view = view;
    }

    public void changeMenu() {
        App.setCurrentMenu(Menus.InGameMenus.MENU_SWITCHER);
    }

    public void showInventory(Game game) {
        if (!game.isPlayerActive(game.getCurrentPlayer())) {
            view.showMessage("You are ran out of energy for this turn!");
            return;
        }
        Inventory inventory = game.getCurrentPlayer().getInventory();
        StringBuilder inventoryStr = new StringBuilder();
        for (Map.Entry<ItemIDs, ArrayList<ItemInstance>> entry : inventory.getItems().entrySet()) {
            for (ItemInstance item : entry.getValue()) {
                inventoryStr.append("name: \"").append(item.getDefinition().getDisplayName())
                        .append("\", number in inventory: ").append(entry.getValue().size()).append("\n");
                break;
            }
        }
        view.showMessage(inventoryStr.toString());
    }

    public void inventoryTrash(Game game, Matcher matcher, String input) {
        if (!game.isPlayerActive(game.getCurrentPlayer())) {
            view.showMessage("You are ran out of energy for this turn!");
            return;
        }
        String itemName = matcher.group("itemName");
        Inventory inventory = game.getCurrentPlayer().getInventory();
        if (input.contains("-n")) {
            String numberStr = matcher.group("number");
            int number;
            try {
                number = Integer.parseInt(numberStr);
            } catch (NumberFormatException e) {
                view.showMessage("please enter a valid number!");
                return;
            }
            Map<ItemIDs, Integer> itemToTrash = new HashMap<>();
            for (Map.Entry<ItemIDs, ArrayList<ItemInstance>> entry : inventory.getItems().entrySet()) {
                if (entry.getKey().name().equals(itemName)) {
                    if(!inventory.hasItem(entry.getKey(), number)) {
                        view.showMessage("You don't have enough " + entry.getKey().name() + " to trash!");
                        return;
                    }
                    checkTrashCanLevel(entry.getValue().get(0).getDefinition(), game.getCurrentPlayer(),
                            game.getCurrentPlayer().getTrashCan(), number);
                    itemToTrash.put(entry.getKey(), number);
                }
            }
            for (Map.Entry<ItemIDs, Integer> entryToTrash : itemToTrash.entrySet()) {
                inventory.trashItem(entryToTrash.getKey(), entryToTrash.getValue());
            }
            view.showMessage(number + " number of " + itemName + " has been trashed!");
        } else {
            ArrayList<ItemIDs> itemToTrash = new ArrayList<>();
            for (Map.Entry<ItemIDs, ArrayList<ItemInstance>> entry : inventory.getItems().entrySet()) {
                if (entry.getKey().name().equals(itemName)) {
                    checkTrashCanLevel(entry.getValue().get(0).getDefinition(), game.getCurrentPlayer(),
                            game.getCurrentPlayer().getTrashCan(), entry.getValue().size());
                    itemToTrash.add(entry.getKey());
                }
            }
            if(itemToTrash.isEmpty()) {
                view.showMessage("You don't have " + itemName + " to trash!");
            }
            for (ItemIDs toTrash : itemToTrash) {
                inventory.trashItemAll(toTrash);
            }
            view.showMessage(itemName + " has been removed from your inventory!");
        }
        game.getCurrentPlayer().decreaseEnergy(5);
    }

    public void checkTrashCanLevel(ItemDefinition item, Player player, ItemInstance trashCan, int amount) {
        int price = 0;
        if (item.hasAttribute(ItemAttributes.price)) {
            if (trashCan.getDefinition().getId().name().equals("copper_trash_can")) {
                price = (int) ((int) item.getAttribute(ItemAttributes.price) * 0.15 * amount);
                player.getWallet().increaseCoin(price);
            } else if (trashCan.getDefinition().getId().name().equals("iron_trash_can")) {
                price = (int) ((int) item.getAttribute(ItemAttributes.price) * 0.3 * amount);
                player.getWallet().increaseCoin(price);
            } else if (trashCan.getDefinition().getId().name().equals("golden_trash_can")) {
                price = (int) ((int) item.getAttribute(ItemAttributes.price) * 0.45 * amount);
                player.getWallet().increaseCoin(price);
            } else if (trashCan.getDefinition().getId().name().equals("iridium_trash_can")) {
                price = (int) ((int) item.getAttribute(ItemAttributes.price) * 0.6 * amount);
                player.getWallet().increaseCoin(price);
            }
        } else if (item.hasAttribute(ItemAttributes.baseSellPrice)) {
            if (trashCan.getDefinition().getId().name().equals("copper_trash_can")) {
                price = (int) ((int) item.getAttribute(ItemAttributes.baseSellPrice) * 0.15 * amount);
                player.getWallet().increaseCoin(price);
            } else if (trashCan.getDefinition().getId().name().equals("iron_trash_can")) {
                price = (int) ((int) item.getAttribute(ItemAttributes.baseSellPrice) * 0.3 * amount);
                player.getWallet().increaseCoin(price);
            } else if (trashCan.getDefinition().getId().name().equals("golden_trash_can")) {
                price = (int) ((int) item.getAttribute(ItemAttributes.baseSellPrice) * 0.45 * amount);
                player.getWallet().increaseCoin(price);
            } else if (trashCan.getDefinition().getId().name().equals("iridium_trash_can")) {
                price = (int) ((int) item.getAttribute(ItemAttributes.baseSellPrice) * 0.6 * amount);
                player.getWallet().increaseCoin(price);
            }
        } else {
            view.showMessage("This item does not have a price!");
            return;
        }
        view.showMessage("Your trash can gave you " + price + "g for " + item.getId() + "!");
    }
}

