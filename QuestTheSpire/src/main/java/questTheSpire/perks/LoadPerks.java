package questTheSpire.perks;

import basemod.interfaces.PostInitializeSubscriber;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static questTheSpire.QuestTheSpire.activeCharacterFile;

public class LoadPerks {
    public static int hpPerk;
    private static int goldPerk;

    public static void onDungeonSetup() {
        hpPerk = activeCharacterFile.getStartMaxHP() * 2;
        AbstractDungeon.player.increaseMaxHp(hpPerk,true);

        goldPerk = activeCharacterFile.getStartGold() * 20;
        AbstractDungeon.player.gainGold(goldPerk);
    }
}
