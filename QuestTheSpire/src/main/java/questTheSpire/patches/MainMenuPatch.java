package questTheSpire.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.CharacterManager;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import com.megacrit.cardcrawl.screens.mainMenu.MenuButton;
import javassist.CtBehavior;

@SpirePatch2(clz = MainMenuScreen.class, method = "setMainMenuButtons")


public class MainMenuPatch {
    @SpireInsertPatch(
        locator= BlockFinalLocator.class,
        localvars={"index"})

    public static void setMainMenuButtons(MainMenuScreen __instance, int index) {
        __instance.buttons.add(new MenuButton(MenuButton.ClickResult.PLAY, index++));
    }

    private static class BlockFinalLocator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(CharacterManager.class, "anySaveFileExists");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }

}
