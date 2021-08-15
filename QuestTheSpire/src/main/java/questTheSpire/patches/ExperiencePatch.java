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
import questTheSpire.util.CharacterSaveFile;

import static com.megacrit.cardcrawl.screens.GameOverScreen.calcScore;
import static com.megacrit.cardcrawl.screens.GameOverScreen.isVictory;
import static questTheSpire.QuestTheSpire.*;

@SpirePatch2(clz = GameOverScreen.class, method = "calculateUnlockProgress")

public class ExperiencePatch {
    @SpirePostfixPatch
    public static void calculateUnlockProgress(GameOverScreen __instance) {
        //TODO you should be loading this value from the config first, and then saving it again.
        int currentLevel = activeCharacterFile.getLevel();
        int currentPrestige = activeCharacterFile.getPrestigeLevel();
        int xp = GameOverScreen.calcScore(isVictory) + OverFlowExperience;
        int levelUps = currentLevel - CharacterSaveFile.calculateLevel(activeCharacterFile.getExp()+xp);
        int prestigeUps = currentPrestige - CharacterSaveFile.calculatePrestigeLevel(activeCharacterFile.getExp()+xp);
        activeCharacterFile.addExp(xp);
        if (levelUps > 0) {
            //TODO add perk points and whatever unlocks at certain level
            activeCharacterFile.addLevel(levelUps);
        }
        if (prestigeUps > 0) {
            //TODO add perk points from prestige up
            activeCharacterFile.addPrestigeLevel(prestigeUps);
        }
    }
}
