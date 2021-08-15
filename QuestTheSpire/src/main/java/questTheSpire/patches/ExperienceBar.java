package questTheSpire.patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
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

        try {
            AbstractPlayer.PlayerClass pc = AbstractDungeon.player.chosenClass;
            SpireConfig config = new SpireConfig("QuestTheSpire", pc.toString() + "_QuestTheSpire_Stats", questTheSpireCharacterStats);
            Level = config.getInt("Level");
            OverFlowExperience = config.getInt("OverFlowExperience");
            config.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Level Green = R-0.0, G-0.8, B-0.3
        //Prestige Blue = R-0.0, G-1.57 B-1.96
        if (Level==20) {
            sb.setColor(new Color(0.0F, 1.57F, 1.96F, ___progressBarAlpha * 0.9F)); //TODO 157 green and 196 blue do not map to 1.57F and 1.96F, they map to 0.62F and 0.77F (the value is: color / 255)
        }   else sb.setColor(new Color(0.0F, 0.8F, 0.3F, ___progressBarAlpha * 0.9F));

        sb.draw(ImageMaster.WHITE_SQUARE_IMG, ___progressBarX, (float)Settings.HEIGHT * 0.3F, ___progressBarWidth * ___progressPercent, 14.0F * Settings.scale);
        sb.setColor(new Color(0.0F, 0.0F, 0.0F, ___progressBarAlpha * 0.25F));
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, ___progressBarX, (float)Settings.HEIGHT * 0.3F, ___progressBarWidth * ___progressPercent, 5.0F * Settings.scale);

        String derp = "[" + (int)(Experience) + "/" + LevelCosts.levelcost(LevelRequirement) + "]";
        ___creamUiColor.a = ___progressBarAlpha * 0.9F;
        FontHelper.renderFontLeftTopAligned(sb, FontHelper.topPanelInfoFont, derp, 576.0F * Settings.xScale, (float)Settings.HEIGHT * 0.3F - 12.0F * Settings.scale, ___creamUiColor);

        if (Level < 20) {
            derp = "Level " + (Level+1);
        } else {
            derp = "Prestige " + (Level+1);
        }

        FontHelper.renderFontRightTopAligned(sb, FontHelper.topPanelInfoFont, derp, 1344.0F * Settings.xScale, (float)Settings.HEIGHT * 0.3F - 12.0F * Settings.scale, ___creamUiColor);
     }
}
