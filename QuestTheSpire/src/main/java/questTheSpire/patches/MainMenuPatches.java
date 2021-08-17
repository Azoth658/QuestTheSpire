package questTheSpire.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.CharacterManager;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import com.megacrit.cardcrawl.screens.mainMenu.MenuButton;
import com.megacrit.cardcrawl.screens.mainMenu.MenuPanelScreen;
import javassist.CtBehavior;
import questTheSpire.QuestTheSpire;

public class MainMenuPatches {

    private static final UIStrings uiStrings;
    public static final String[] TEXT;

    public static class Enums {
        @SpireEnum
        public static MenuButton.ClickResult LOADOUTS;
    }

    @SpirePatch2(clz = MainMenuScreen.class, method = "setMainMenuButtons")
    public static class ButtonAdderPatch {
        @SpireInsertPatch(locator= ButtonLocator.class, localvars={"index"})
        public static void setMainMenuButtons(MainMenuScreen __instance, int index) {
            if (CardCrawlGame.characterManager.anySaveFileExists()) {
                __instance.buttons.add(new MenuButton(Enums.LOADOUTS, index+2));
            } else {
                __instance.buttons.add(new MenuButton(Enums.LOADOUTS, index+1));
            }
        }

        private static class ButtonLocator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(CharacterManager.class, "anySaveFileExists");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

    @SpirePatch2(clz = MenuButton.class, method = "setLabel")
    public static class SetText {
        @SpirePostfixPatch
        public static void useLocalizedText(MenuButton __instance, @ByRef String[] ___label) {
            if (__instance.result == Enums.LOADOUTS) {
                ___label[0] = TEXT[0];
            }
        }
    }

    @SpirePatch2(clz = MenuButton.class, method = "buttonEffect")
    public static class OnClickButton {
        @SpirePostfixPatch
        public static void useLocalizedText(MenuButton __instance) {
            if (__instance.result == Enums.LOADOUTS) {
                //TODO we need an action screen to open, lol
                CardCrawlGame.mainMenuScreen.panelScreen.open(MenuPanelScreen.PanelScreen.COMPENDIUM);
            }
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(QuestTheSpire.makeID("MainMenu"));
        TEXT = uiStrings.TEXT;
    }
}
