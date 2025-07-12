package com.untildawn.Enums.GameMenus;

import com.untildawn.views.AppMenu;
import com.untildawn.views.InGameMenus.*;
import com.untildawn.views.PreGameMenus.*;
import com.untildawn.views.PreGameMenus.ExitMenu;
import java.util.Scanner;

/*
    This class has enums containing every menu in the game.
    later we use Menus.InGameMenus.HOME_MENU for example to change the current menu.
 */
public class Menus {
    public enum PreGameMenus implements Menu {
        SIGNUP_MENU(new SignupMenuView()),
        LOGIN_MENU(new LoginMenuView()),
        MAIN_MENU(new MainMenu()),
        PROFILE_MENU(new ProfileMenu()),
        AVATAR_MENU(new AvatarMenu()),
        GAME_MENU(new GameMenu()),
        EXIT_MENU(new ExitMenu());

        private final AppMenu menu;

        PreGameMenus(AppMenu menu) {
            this.menu = menu;
        }

        public void check(Scanner sc) {
            this.menu.handleInput(sc);
        }
    }

    public enum InGameMenus implements Menu {
        HOME_MENU(new HomeMenu()),
        CRAFTING_MENU(new CraftingMenu()),
        COOKING_MENU(new CookingMenu()),
        ACTION_MENU(new ActionMenuView()),
        SHOP_MENU(new ShopMenuView()),
        EXIT_MENU(new ExitMenu()),
        INVENTORY_MENU(new InventoryMenu()),
        MENU_SWITCHER(new MenuSwitcherView()),
        TRADE_MENU(new TradeMenuView()),
        ;
        private final AppMenu menu;

        InGameMenus(AppMenu menu) {
            this.menu = menu;
        }

        public void check(Scanner sc) {
            this.menu.handleInput(sc);
        }
    }
}
