package questTheSpire.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.screens.GameOverScreen;

import static com.megacrit.cardcrawl.screens.GameOverScreen.calcScore;
import static com.megacrit.cardcrawl.screens.GameOverScreen.isVictory;
import static questTheSpire.QuestTheSpire.Experience;

@SpirePatch2(clz = GameOverScreen.class, method = "calculateUnlockProgress")

public class ExperiencePatch {
    @SpirePostfixPatch
    public static void calculateUnlockProgress(GameOverScreen __instance) {
        Experience = GameOverScreen.calcScore(__instance.isVictory);
     }
}
