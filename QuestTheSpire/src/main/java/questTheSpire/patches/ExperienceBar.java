package questTheSpire.patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.GameOverScreen;
import questTheSpire.Level.LevelCosts;
import questTheSpire.QuestTheSpire;
import questTheSpire.util.CharacterSaveFile;

import static basemod.DevConsole.unlockLevel;
import static questTheSpire.QuestTheSpire.*;

@SpirePatch2(clz = GameOverScreen.class, method = "renderProgressBar")
public class ExperienceBar {

    private static final UIStrings uiStrings;
    public static final String[] TEXT;


    @SpirePostfixPatch
    public static void renderProgressBar(GameOverScreen __instance, SpriteBatch sb, @ByRef Color[] ___whiteUiColor, Color ___creamUiColor, float ___progressBarAlpha, float ___progressBarWidth, float ___progressBarX, float ___progressPercent){
        ___whiteUiColor[0].a = ___progressBarAlpha * 0.3F;
        sb.setColor(___whiteUiColor[0]);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, ___progressBarX, (float) Settings.HEIGHT * 0.3F, ___progressBarWidth, 14.0F * Settings.scale);

        //TODO get level, numerator, and denominator from save file
        int currentLevel = activeCharacterFile.getLevel();
        int currentPrestige = activeCharacterFile.getPrestigeLevel();
        int current = CharacterSaveFile.getExpBarNumerator(activeCharacterFile.getExp());
        int next = CharacterSaveFile.getExpBarDenominator(activeCharacterFile.getExp());
        float xpBarProgressPercent = current / (float)next;

        if (currentLevel==20) {
            sb.setColor(new Color(0.0F, 0.62F, 0.77F, ___progressBarAlpha * 0.9F)); //157 green and 196 blue do not map to 1.57F and 1.96F, they map to 0.62F and 0.77F (the value is: color / 255)
        }   else sb.setColor(new Color(0.0F, 0.8F, 0.3F, ___progressBarAlpha * 0.9F));

        sb.draw(ImageMaster.WHITE_SQUARE_IMG, ___progressBarX, (float)Settings.HEIGHT * 0.3F, ___progressBarWidth * xpBarProgressPercent, 14.0F * Settings.scale);
        sb.setColor(new Color(0.0F, 0.0F, 0.0F, ___progressBarAlpha * 0.25F));
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, ___progressBarX, (float)Settings.HEIGHT * 0.3F, ___progressBarWidth * xpBarProgressPercent, 5.0F * Settings.scale);

        String derp = "[" + (int)(current) + "/" + (int)(next)+ "]";
        ___creamUiColor.a = ___progressBarAlpha * 0.9F;
        FontHelper.renderFontLeftTopAligned(sb, FontHelper.topPanelInfoFont, derp, 576.0F * Settings.xScale, (float)Settings.HEIGHT * 0.3F - 12.0F * Settings.scale, ___creamUiColor);

        //TODO test localization
        if (Level < 20) {
            derp = TEXT[0] + " " + (currentLevel+1);
        } else {
            derp = TEXT[1] + " " + (currentPrestige+1);
        }

        FontHelper.renderFontRightTopAligned(sb, FontHelper.topPanelInfoFont, derp, 1344.0F * Settings.xScale, (float)Settings.HEIGHT * 0.3F - 12.0F * Settings.scale, ___creamUiColor);
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(QuestTheSpire.makeID("Experience"));
        TEXT = uiStrings.TEXT;
    }
}
