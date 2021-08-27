package questTheSpire.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.CharacterManager;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import com.megacrit.cardcrawl.screens.mainMenu.MenuButton;
import com.megacrit.cardcrawl.screens.mainMenu.SaveSlotScreen;
import javassist.CtBehavior;
import questTheSpire.QuestTheSpire;
import questTheSpire.screens.LoadoutScreen;

public class MainMenuPatches {

    private static final UIStrings uiStrings;
    public static final String[] TEXT;

    //TODO move to main mod file?
    public static class Enums {
        @SpireEnum
        public static MenuButton.ClickResult LOADOUT_BUTTON;
        @SpireEnum
        public static MainMenuScreen.CurScreen LOADOUT_VIEW;
    }

    @SpirePatch(clz = MainMenuScreen.class, method = SpirePatch.CLASS)
    public static class LoadoutScreenField {
        public static SpireField<LoadoutScreen> loadoutScreen = new SpireField<>(() -> null);
    }

    @SpirePatch2(clz = MainMenuScreen.class, method = "setMainMenuButtons")
    public static class ButtonAdderPatch {
        @SpireInsertPatch(locator= ButtonLocator.class, localvars={"index"})
        public static void setMainMenuButtons(MainMenuScreen __instance, int index) {
            //TODO grab index by reference and actually update it
            if (CardCrawlGame.characterManager.anySaveFileExists()) {
                __instance.buttons.add(new MenuButton(Enums.LOADOUT_BUTTON, index+2));
            } else {
                __instance.buttons.add(new MenuButton(Enums.LOADOUT_BUTTON, index+1));
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
            if (__instance.result == Enums.LOADOUT_BUTTON) {
                ___label[0] = TEXT[0];
            }
        }
    }

    @SpirePatch2(clz = MenuButton.class, method = "buttonEffect")
    public static class OnClickButton {
        @SpirePostfixPatch
        public static void useLocalizedText(MenuButton __instance) {
            if (__instance.result == Enums.LOADOUT_BUTTON) {
                LoadoutScreenField.loadoutScreen.get(CardCrawlGame.mainMenuScreen).open();
            }
        }
    }

    @SpirePatch2(clz = MainMenuScreen.class, method = "<ctor>", paramtypez = {boolean.class})
    private static class AddNewScreenToSpireField {
        @SpirePostfixPatch()
        public static void screenTime(MainMenuScreen __instance) {
            LoadoutScreenField.loadoutScreen.set(__instance, new LoadoutScreen());
            LoadoutScreenField.loadoutScreen.get(__instance).initialize();
        }
    }

    @SpirePatch2(clz = MainMenuScreen.class, method = "update")
    public static class UpdateLoadoutScreen {
        @SpireInsertPatch(locator= UpdateLocator.class)
        public static void updateTime(MainMenuScreen __instance) {
            if (__instance.screen == Enums.LOADOUT_VIEW) {
                LoadoutScreenField.loadoutScreen.get(__instance).update();
            }
        }

        private static class UpdateLocator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(SaveSlotScreen.class, "update");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

    @SpirePatch2(clz = MainMenuScreen.class, method = "render")
    public static class RenderLoadoutScreen {
        @SpireInsertPatch(locator= RenderLocator.class)
        public static void renderTime(MainMenuScreen __instance, SpriteBatch sb) {
            if (__instance.screen == Enums.LOADOUT_VIEW) {
                LoadoutScreenField.loadoutScreen.get(__instance).render(sb);
            }
        }

        private static class RenderLocator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(SaveSlotScreen.class, "render");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(QuestTheSpire.makeID("MainMenu"));
        TEXT = uiStrings.TEXT;
    }
}
