package questTheSpire.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.GameOverScreen;
import questTheSpire.Level.LevelCosts;
import questTheSpire.QuestTheSpire;

import static com.megacrit.cardcrawl.screens.GameOverScreen.calcScore;
import static com.megacrit.cardcrawl.screens.GameOverScreen.isVictory;
import static questTheSpire.QuestTheSpire.*;

@SpirePatch2(clz = GameOverScreen.class, method = "calculateUnlockProgress")

public class ExperiencePatch {
    @SpirePostfixPatch
    public static void calculateUnlockProgress(GameOverScreen __instance) {
        try {
                AbstractPlayer.PlayerClass pc = AbstractDungeon.player.chosenClass;
                SpireConfig config = new SpireConfig("QuestTheSpire", pc.toString() + "_QuestTheSpire_Stats", questTheSpireCharacterStats);
                OverFlowExperience = config.getInt("OverFlowExperience");
                config.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Experience = GameOverScreen.calcScore(__instance.isVictory) + OverFlowExperience;
        if (Experience >= LevelCosts.levelcost(LevelRequirement)) {
            QuestTheSpire.Level++;
            OverFlowExperience = Experience - LevelRequirement;
        } else {
            OverFlowExperience = Experience;
        }
        try {
                AbstractPlayer.PlayerClass pc = AbstractDungeon.player.chosenClass;
                SpireConfig config = new SpireConfig("QuestTheSpire", pc.toString() + "_QuestTheSpire_Stats", questTheSpireCharacterStats);
                config.setInt("Level", Level);
                config.setInt("OverFlowExperience", OverFlowExperience);
                config.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
