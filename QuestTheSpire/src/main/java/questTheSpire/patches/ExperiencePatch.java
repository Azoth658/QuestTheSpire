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
        //Grab the current level and prestige, we could inline this but its easier to read this way
        int currentLevel = activeCharacterFile.getLevel();
        int currentPrestige = activeCharacterFile.getPrestigeLevel();
        //Determine how much exp we just made
        int xp = GameOverScreen.calcScore(isVictory);
        //See what our level would be with the new exp to see how many times we have levelled up. Then do the same for prestige
        int levelUps = currentLevel - CharacterSaveFile.calculateLevel(activeCharacterFile.getExp()+xp);
        int prestigeUps = currentPrestige - CharacterSaveFile.calculatePrestigeLevel(activeCharacterFile.getExp()+xp);
        //Add the xp to our character
        activeCharacterFile.addExp(xp);
        //If we actually levelled up, add the number of levels we went up and add out perk points and all that
        if (levelUps > 0) {
            //TODO add perk points and whatever unlocks at certain level
            activeCharacterFile.addLevel(levelUps);
        }
        //If our prestige went up, same dice
        if (prestigeUps > 0) {
            //TODO add perk points from prestige up
            activeCharacterFile.addPrestigeLevel(prestigeUps);
        }
    }
}
