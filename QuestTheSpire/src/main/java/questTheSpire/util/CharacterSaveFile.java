package questTheSpire.util;

import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Properties;

public class CharacterSaveFile {
    public static Properties questTheSpireCharacterStats = new Properties();

    public enum SaveDataEnum {
        EXPERIENCE,
        LEVEL(1),
        PRESTIGE_LEVEL,
        MAX_PERK_POINTS(1),
        CURRENT_PERK_POINTS(1),
        RUNS,
        DEATHS,
        WINS,

        //Character data above, perk allocation data below.

        MAX_HP,
        GOLD,
        POTION_SLOTS,
        ENERGY,
        DRAW_AMOUNT,
        RETAIN_BLOCK,
        STRENGTH,
        DEXTERITY,
        FOCUS,
        MANTRA,
        DEVOTION,
        REGEN,
        ARTIFACT,
        PLATED_ARMOR,
        THORNS,
        COMMON_RELIC,
        UNCOMMON_RELIC,
        RARE_RELIC,

        //Unimplemented options below

        COMMON_CARD,
        UNCOMMON_CARD,
        RARE_CARD,
        CARD_REMOVALS,
        CARD_UPGRADES,
        HOARDER_ASPECT;

        private final int defaultValue;

        SaveDataEnum(int defaultValue) {
            this.defaultValue = defaultValue;
        }

        SaveDataEnum() {
            this(0);
        }

        public int getDefaultValue() {
            return defaultValue;
        }

    }

    private final static EnumSet<SaveDataEnum> CHARACTER_DATA_SET = EnumSet.range(SaveDataEnum.EXPERIENCE, SaveDataEnum.WINS); //Update me if you add more character data.
    private final static EnumSet<SaveDataEnum> PERK_ALLOCATION_DATA_SET = EnumSet.complementOf(CHARACTER_DATA_SET);

    public static final int BASE_REQ = 500;
    public static final int REQ_INCREASE_PER_LEVEL = 100;
    public static final int MAX_LEVEL = 20;
    public static final int EXP_PER_PRESTIGE = 10000;
    public static final int[] LOOKUP_TABLE = new int[MAX_LEVEL]; //index offset

    SpireConfig config;
    //final AbstractPlayer.PlayerClass playerClass;
    final String filePath;

    public CharacterSaveFile() {
        this(AbstractDungeon.player.chosenClass);
    }

    public CharacterSaveFile(AbstractPlayer.PlayerClass playerClass) {
        this(playerClass.toString());
    }

    public CharacterSaveFile(String playerClass) {
        filePath = makeConfigPath(playerClass);
        try {
            config = new SpireConfig("QuestTheSpire", filePath, questTheSpireCharacterStats);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SpireConfig getWrappedConfig() {
        return config;
    }

    public int getData(SaveDataEnum s) {
        return config.getInt(s.toString());
    }

    public void addExp(int exp) {
        setData(SaveDataEnum.EXPERIENCE, getData(SaveDataEnum.EXPERIENCE) +exp);
    }

    public void addLevel(int level) {
        setData(SaveDataEnum.LEVEL, getData(SaveDataEnum.LEVEL) +level);
    }

    public void addPrestigeLevel(int pLevel) {
        setData(SaveDataEnum.PRESTIGE_LEVEL, getData(SaveDataEnum.PRESTIGE_LEVEL) +pLevel);
    }

    public void incrementDeaths() {
        setData(SaveDataEnum.DEATHS, getData(SaveDataEnum.DEATHS) +1);
    }

    public void incrementRuns() {
        setData(SaveDataEnum.RUNS, getData(SaveDataEnum.RUNS) +1);
    }

    public void incrementWins() {
        setData(SaveDataEnum.WINS, getData(SaveDataEnum.WINS) +1);
    }

    public void setData(SaveDataEnum s, int value) {
        config.setInt(s.toString(), value);
        saveConfig();
    }

    public void resetPerkAllocations() {
        setData(SaveDataEnum.CURRENT_PERK_POINTS, getData(SaveDataEnum.MAX_PERK_POINTS));
        for (SaveDataEnum s : PERK_ALLOCATION_DATA_SET) {
            setData(s, s.getDefaultValue());
        }
    }

    public void wipeAllStoredData() {
        for (SaveDataEnum s : SaveDataEnum.values()) {
            setData(s, s.getDefaultValue());
        }
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
            return currentExp;
        } else {
            int l = calculateLevel(currentExp);
            if (l == 20){
                return currentExp-LOOKUP_TABLE[l-1];
            }
            else {
                return currentExp - LOOKUP_TABLE[l-2];
            }
        }
    }

    private static String makeConfigPath(AbstractPlayer.PlayerClass pc) {
        return makeConfigPath(pc.toString());
    }

    private static String makeConfigPath(String pc) {
        return "QuestTheSpire_" + pc + "_Stats" + "-Slot" + CardCrawlGame.saveSlot;
    }

    static {
        for (SaveDataEnum s : SaveDataEnum.values()) {
            questTheSpireCharacterStats.setProperty(s.toString(), String.valueOf(s.getDefaultValue()));
        }


        LOOKUP_TABLE[0] = BASE_REQ;
        for (int i = 1 ; i < MAX_LEVEL ; i++) {
            LOOKUP_TABLE[i] = BASE_REQ+REQ_INCREASE_PER_LEVEL*i+LOOKUP_TABLE[i-1];
        }
    }
}
