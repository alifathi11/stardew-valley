package com.untildawn.Enums.GameMenus;

import com.badlogic.gdx.Screen;
import com.untildawn.controllers.PreGameControllers.MainMenuController;
import com.untildawn.controllers.PreGameControllers.ProfileMenuController;
import com.untildawn.views.AppMenu;
import com.untildawn.views.InGameMenus.*;
import com.untildawn.views.PreGameMenus.*;
import com.untildawn.views.PreGameMenus.ExitMenuView;
import java.util.Scanner;

/*
    This class has enums containing every menu in the game.
    later we use Menus.InGameMenus.HOME_MENU for example to change the current menu.
 */
public class Menus {
    public enum PreGameMenus implements Menu {
        SIGNUP_MENU(new SignupMenuView()),
        LOGIN_MENU(new LoginMenuView()),
        MAIN_MENU(new MainMenuView(new MainMenuController())),
        PROFILE_MENU(new ProfileMenuView(new ProfileMenuController())),
        AVATAR_MENU(new AvatarMenuView()),
        GAME_MENU(new GameMenuView()),
        EXIT_MENU(new ExitMenuView());

        private final Screen menu;

        PreGameMenus(Screen menu) {
            this.menu = menu;
        }

        public void check(Scanner sc) {
//            this.menu.handleInput(sc);
        }

        public Screen getMenu() {
            return menu;
        }
    }

    public enum InGameMenus implements Menu {
        HOME_MENU(new HomeMenu()),
        CRAFTING_MENU(new CraftingMenu()),
        COOKING_MENU(new CookingMenu()),
        ACTION_MENU(new ActionMenuView()),
        SHOP_MENU(new ShopMenuView()),
        EXIT_MENU(new ExitMenuView()),
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
