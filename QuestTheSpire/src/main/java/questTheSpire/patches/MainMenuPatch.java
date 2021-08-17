package questTheSpire.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.CharacterManager;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import com.megacrit.cardcrawl.screens.mainMenu.MenuButton;

@SpirePatch2(clz = MainMenuScreen.class, method = "setMainMenuButtons")


public class MainMenuPatch {
    @SpireInsertPatch
        loc=123,
        localvars={"index"}
    public static void setMainMenuButtons(MainMenuScreen __instance, int index) {
        __instance.buttons.add(new MenuButton(MenuButton.ClickResult.PLAY, index++));
    }
}
