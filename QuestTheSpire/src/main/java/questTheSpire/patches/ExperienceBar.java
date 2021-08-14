package questTheSpire.patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.screens.GameOverScreen;
import questTheSpire.Level.LevelCosts;

import static basemod.DevConsole.unlockLevel;
import static questTheSpire.QuestTheSpire.*;

@SpirePatch2(clz = GameOverScreen.class, method = "renderProgressBar")

public class ExperienceBar {
    @SpirePostfixPatch
    public static void renderProgressBar(GameOverScreen __instance, SpriteBatch sb, @ByRef Color[] ___whiteUiColor, Color ___creamUiColor, float ___progressBarAlpha, float ___progressBarWidth, float ___progressBarX, float ___progressPercent){
        ___whiteUiColor[0].a = ___progressBarAlpha * 0.3F;
        sb.setColor(___whiteUiColor[0]);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, ___progressBarX, (float) Settings.HEIGHT * 0.3F, ___progressBarWidth, 14.0F * Settings.scale);
        //Level Green = R-0.0, G-0.8, B-0.3
        //Prestige Blue = R-0.0, G-1.57 B-1.96
        sb.setColor(new Color(0.0F, 1.57F, 1.96F, ___progressBarAlpha * 0.9F));
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, ___progressBarX, (float)Settings.HEIGHT * 0.3F, ___progressBarWidth * ___progressPercent, 14.0F * Settings.scale);
        sb.setColor(new Color(0.0F, 0.0F, 0.0F, ___progressBarAlpha * 0.25F));
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, ___progressBarX, (float)Settings.HEIGHT * 0.3F, ___progressBarWidth * ___progressPercent, 5.0F * Settings.scale);

        String derp = "[" + (int)Experience + "/" + LevelCosts.levelcost(LevelRequirement) + "]";
        ___creamUiColor.a = ___progressBarAlpha * 0.9F;
        FontHelper.renderFontLeftTopAligned(sb, FontHelper.topPanelInfoFont, derp, 576.0F * Settings.xScale, (float)Settings.HEIGHT * 0.3F - 12.0F * Settings.scale, ___creamUiColor);

        if (5 - unlockLevel == 1) {
            derp = "Level " + (Level+1);
        } else {
            derp = "Level " + (Level+1);
        }

        FontHelper.renderFontRightTopAligned(sb, FontHelper.topPanelInfoFont, derp, 1344.0F * Settings.xScale, (float)Settings.HEIGHT * 0.3F - 12.0F * Settings.scale, ___creamUiColor);
     }
}
