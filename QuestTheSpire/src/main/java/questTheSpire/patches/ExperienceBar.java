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

import static basemod.DevConsole.unlockLevel;
import static questTheSpire.QuestTheSpire.Experience;

@SpirePatch2(clz = GameOverScreen.class, method = "renderProgressBar")

public class ExperienceBar {
    @SpirePostfixPatch
    public static void renderProgressBar(GameOverScreen __instance, SpriteBatch sb, @ByRef Color[] ___whiteUiColor, Color ___creamUiColor, float ___progressBarAlpha, float ___progressBarWidth, float ___progressBarX, float ___progressPercent){
        ___whiteUiColor[0].a = ___progressBarAlpha * 0.3F;
        sb.setColor(___whiteUiColor[0]);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, ___progressBarX, (float) Settings.HEIGHT * 0.2F, ___progressBarWidth, 14.0F * Settings.scale);
        sb.setColor(new Color(1.0F, 0.8F, 0.3F, ___progressBarAlpha * 0.9F));
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, ___progressBarX, (float)Settings.HEIGHT * 0.2F, ___progressBarWidth * ___progressPercent, 14.0F * Settings.scale);
        sb.setColor(new Color(0.0F, 0.0F, 0.0F, ___progressBarAlpha * 0.25F));
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, ___progressBarX, (float)Settings.HEIGHT * 0.2F, ___progressBarWidth * ___progressPercent, 4.0F * Settings.scale);
        String derp = "[" + (int)Experience + "/" + "6000" + "]";
        ___creamUiColor.a = ___progressBarAlpha * 0.9F;
        FontHelper.renderFontLeftTopAligned(sb, FontHelper.topPanelInfoFont, derp, 576.0F * Settings.xScale, (float)Settings.HEIGHT * 0.2F - 12.0F * Settings.scale, ___creamUiColor);
        if (5 - unlockLevel == 1) {
            derp = "TEST";
        } else {
            derp = "TEST";
        }

        FontHelper.renderFontRightTopAligned(sb, FontHelper.topPanelInfoFont, derp, 1344.0F * Settings.xScale, (float)Settings.HEIGHT * 0.2F - 12.0F * Settings.scale, ___creamUiColor);
     }
}
