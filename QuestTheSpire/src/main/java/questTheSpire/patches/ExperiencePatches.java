package questTheSpire.patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.GameOverScreen;
import questTheSpire.QuestTheSpireMod;
import questTheSpire.util.CharacterSaveFile;

import static com.megacrit.cardcrawl.screens.GameOverScreen.isVictory;
import static questTheSpire.QuestTheSpireMod.activeCharacterFile;
import static questTheSpire.util.CharacterSaveFile.EXP_PER_PRESTIGE;

public class ExperiencePatches {

    // Adds an XP bar to Death/Victory Screen
    @SpirePatch2(clz = GameOverScreen.class, method = "renderProgressBar")
    public static class ExperienceBar {

        private static final UIStrings uiStrings;
        public static final String[] TEXT;


        @SpirePostfixPatch
        public static void renderProgressBar(GameOverScreen __instance, SpriteBatch sb, @ByRef Color[] ___whiteUiColor, Color ___creamUiColor, float ___progressBarAlpha, float ___progressBarWidth, float ___progressBarX, float ___progressPercent, int ___unlockCost) {
            float placeHeight = 0.2F;
            if(___unlockCost > 0){
                placeHeight = 0.25F;
            }
            ___whiteUiColor[0].a = ___progressBarAlpha * 0.3F;
            sb.setColor(___whiteUiColor[0]);
            sb.draw(ImageMaster.WHITE_SQUARE_IMG, ___progressBarX, (float) Settings.HEIGHT * placeHeight, ___progressBarWidth, 14.0F * Settings.scale);


            int currentLevel = activeCharacterFile.getData(CharacterSaveFile.SaveDataEnum.LEVEL);
            int currentPrestige = activeCharacterFile.getData(CharacterSaveFile.SaveDataEnum.PRESTIGE_LEVEL);
            int current = CharacterSaveFile.getExpBarNumerator(activeCharacterFile.getData(CharacterSaveFile.SaveDataEnum.EXPERIENCE));
            int next = CharacterSaveFile.getExpBarDenominator(activeCharacterFile.getData(CharacterSaveFile.SaveDataEnum.EXPERIENCE));
            if(currentLevel == 20){
                next = EXP_PER_PRESTIGE;
                current = current - (currentPrestige*EXP_PER_PRESTIGE);
            }
            float xpBarProgressPercent = current / (float) next;


            if (currentLevel == 20) {
                sb.setColor(new Color(0.0F, 0.62F, 0.77F, ___progressBarAlpha * 0.9F)); //157 green and 196 blue do not map to 1.57F and 1.96F, they map to 0.62F and 0.77F (the value is: color / 255)
            } else sb.setColor(new Color(0.0F, 0.8F, 0.3F, ___progressBarAlpha * 0.9F));

            sb.draw(ImageMaster.WHITE_SQUARE_IMG, ___progressBarX, (float) Settings.HEIGHT * placeHeight, ___progressBarWidth * xpBarProgressPercent, 14.0F * Settings.scale);
            sb.setColor(new Color(0.0F, 0.0F, 0.0F, ___progressBarAlpha * 0.25F));
            sb.draw(ImageMaster.WHITE_SQUARE_IMG, ___progressBarX, (float) Settings.HEIGHT * placeHeight, ___progressBarWidth * xpBarProgressPercent, 5.0F * Settings.scale);

            String derp = "[" + (int) (current) + "/" + (int) (next) + "]";
            ___creamUiColor.a = ___progressBarAlpha * 0.9F;
            FontHelper.renderFontLeftTopAligned(sb, FontHelper.topPanelInfoFont, derp, 576.0F * Settings.xScale, (float) Settings.HEIGHT * placeHeight - 12.0F * Settings.scale, ___creamUiColor);

            if (currentLevel < 20) {
                derp = TEXT[0] + " " + (currentLevel + 1);
            } else {
                derp = TEXT[1] + " " + (currentPrestige + 1);
            }

            FontHelper.renderFontRightTopAligned(sb, FontHelper.topPanelInfoFont, derp, 1344.0F * Settings.xScale, (float) Settings.HEIGHT * placeHeight - 12.0F * Settings.scale, ___creamUiColor);
        }

        static {
            uiStrings = CardCrawlGame.languagePack.getUIString(QuestTheSpireMod.makeID("Experience"));
            TEXT = uiStrings.TEXT;
        }
    }


    //Grabs the score at the end of the run and adds the XP to the character
    @SpirePatch2(clz = GameOverScreen.class, method = "calculateUnlockProgress")
    public static class ExperiencePatch {
        @SpirePostfixPatch
        public static void calculateUnlockProgress(GameOverScreen __instance) {
            //Grab the current level and prestige, we could inline this but its easier to read this way
            int currentLevel = activeCharacterFile.getData(CharacterSaveFile.SaveDataEnum.LEVEL);
            int currentPrestige = activeCharacterFile.getData(CharacterSaveFile.SaveDataEnum.PRESTIGE_LEVEL);
            //Determine how much exp we just made
            int xp = GameOverScreen.calcScore(isVictory);
            //See what our level would be with the new exp to see how many times we have levelled up. Then do the same for prestige
            int levelUps = CharacterSaveFile.calculateLevel(activeCharacterFile.getData(CharacterSaveFile.SaveDataEnum.EXPERIENCE) + xp) - currentLevel;
            int prestigeUps = CharacterSaveFile.calculatePrestigeLevel(activeCharacterFile.getData(CharacterSaveFile.SaveDataEnum.EXPERIENCE) + xp) - currentPrestige;
            //Add the xp to our character
            activeCharacterFile.addExp(xp);
            //If we actually levelled up, add the number of levels we went up and add out perk points and all that
            if (levelUps > 0) {
                activeCharacterFile.addLevel(levelUps);
                activeCharacterFile.setData(CharacterSaveFile.SaveDataEnum.CURRENT_PERK_POINTS, activeCharacterFile.getData(CharacterSaveFile.SaveDataEnum.CURRENT_PERK_POINTS) +levelUps);
                currentLevel = activeCharacterFile.getData(CharacterSaveFile.SaveDataEnum.LEVEL);
                currentPrestige = activeCharacterFile.getData(CharacterSaveFile.SaveDataEnum.PRESTIGE_LEVEL);
                int MaxPerkPoints = currentLevel + currentPrestige;
                activeCharacterFile.setData(CharacterSaveFile.SaveDataEnum.MAX_PERK_POINTS, MaxPerkPoints);
            }
            //If our prestige went up, same dice
            if (prestigeUps > 0) {
                activeCharacterFile.addPrestigeLevel(prestigeUps);
                activeCharacterFile.setData(CharacterSaveFile.SaveDataEnum.CURRENT_PERK_POINTS, activeCharacterFile.getData(CharacterSaveFile.SaveDataEnum.CURRENT_PERK_POINTS) +prestigeUps);
                activeCharacterFile.addLevel(levelUps);
                currentLevel = activeCharacterFile.getData(CharacterSaveFile.SaveDataEnum.LEVEL);
                currentPrestige = activeCharacterFile.getData(CharacterSaveFile.SaveDataEnum.PRESTIGE_LEVEL);
                int MaxPerkPoints = currentLevel + currentPrestige;
                activeCharacterFile.setData(CharacterSaveFile.SaveDataEnum.MAX_PERK_POINTS, MaxPerkPoints);
            }
            //If winning run add to character save file
            if (isVictory){
                activeCharacterFile.incrementWins();
            }
        }

    }
}
