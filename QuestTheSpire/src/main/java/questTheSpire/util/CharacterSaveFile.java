package questTheSpire.util;

import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.io.IOException;
import java.util.Properties;

public class CharacterSaveFile {
    public static Properties questTheSpireCharacterStats = new Properties();
    public static final String EXP = "Experience";
    public static final String LEVEL = "Level";
    public static final String PRESTIGE_LEVEL = "PrestigeLevel";
    public static final String RUNS = "Runs";
    public static final String DEATHS = "Deaths";

    public static final int BASE_REQ = 500;
    public static final int REQ_INCREASE_PER_LEVEL = 250;
    public static final int MAX_LEVEL = 20;
    public static final int EXP_PER_PRESTIGE = 10000;
    public static final int[] LOOKUP_TABLE = new int[MAX_LEVEL]; //index offset

    SpireConfig config;
    final AbstractPlayer.PlayerClass playerClass;
    final String filePath;

    public CharacterSaveFile() {
        this(AbstractDungeon.player.chosenClass);
    }

    public CharacterSaveFile(AbstractPlayer.PlayerClass playerClass) {
        this.playerClass = playerClass;
        filePath = makeConfigPath(playerClass);
        try {
            AbstractPlayer.PlayerClass pc = AbstractDungeon.player.chosenClass;
            config = new SpireConfig("QuestTheSpire", filePath, questTheSpireCharacterStats);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getExp() {
        return config.getInt(EXP);
    }

    public int getLevel() {
        return config.getInt(LEVEL);
    }

    public int getPrestigeLevel() {
        return config.getInt(PRESTIGE_LEVEL);
    }

    public int getDeaths() {
        return config.getInt(DEATHS);
    }

    public int getRuns() {
        return config.getInt(RUNS);
    }

    public void setExp(int exp) {
        config.setInt(EXP, exp);
        saveConfig();
    }

    public void addExp(int exp) {
        setExp(getExp()+exp);
    }

    public void setLevel(int level) {
        config.setInt(LEVEL, level);
        saveConfig();
    }

    public void addLevel(int level) {
        setLevel(getLevel()+level);
    }

    public void setPrestigeLevel(int pLevel) {
        config.setInt(PRESTIGE_LEVEL, pLevel);
        saveConfig();
    }

    public void addPrestigeLevel(int pLevel) {
        setPrestigeLevel(getPrestigeLevel()+pLevel);
    }

    public void setDeaths(int deaths) {
        config.setInt(DEATHS, deaths);
        saveConfig();
    }

    public void incrementDeaths() {
        setDeaths(getDeaths()+1);
    }

    public void setRuns(int runs) {
        config.setInt(RUNS, runs);
        saveConfig();
    }

    public void incrementRuns() {
        setRuns(getRuns()+1);
    }

    public void saveConfig() {
        try {
            config.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int calculateLevel(int currentExp) {
        for (int i = 0 ; i < MAX_LEVEL ; i++) {
            if (currentExp < LOOKUP_TABLE[i]) {
                return i+1;
            }
        }
        return MAX_LEVEL;
    }

    public static int calculatePrestigeLevel(int currentExp) {
        if (currentExp < LOOKUP_TABLE[MAX_LEVEL-1]) {
            return 0;
        } else {
            return (currentExp-LOOKUP_TABLE[MAX_LEVEL-1])/EXP_PER_PRESTIGE;
        }
    }

    public static int getExpBarDenominator(int currentExp) {
        if (currentExp < LOOKUP_TABLE[0]) {
            return LOOKUP_TABLE[0];
        } else {
            int l = calculateLevel(currentExp);
            return LOOKUP_TABLE[l-1] - LOOKUP_TABLE[l-2];
        }
    }

    public static int getExpBarNumerator(int currentExp) {
        if (currentExp < LOOKUP_TABLE[0]) {
            return LOOKUP_TABLE[0];
        } else {
            int l = calculateLevel(currentExp);
            return currentExp - LOOKUP_TABLE[l-1];
        }
    }

    private static String makeConfigPath(AbstractPlayer.PlayerClass pc) {
        return "QuestTheSpire_" + pc.toString() + "_Stats" + "-Slot" + CardCrawlGame.saveSlot;
    }

    static {
        questTheSpireCharacterStats.setProperty(EXP, String.valueOf(0));
        questTheSpireCharacterStats.setProperty(LEVEL, String.valueOf(0));
        questTheSpireCharacterStats.setProperty(PRESTIGE_LEVEL, String.valueOf(0));
        questTheSpireCharacterStats.setProperty(DEATHS, String.valueOf(0));
        questTheSpireCharacterStats.setProperty(RUNS, String.valueOf(0));
        LOOKUP_TABLE[0] = BASE_REQ;
        for (int i = 1 ; i < MAX_LEVEL ; i++) {
            LOOKUP_TABLE[i] = BASE_REQ+REQ_INCREASE_PER_LEVEL*i+LOOKUP_TABLE[i-1];
        }
    }
}
