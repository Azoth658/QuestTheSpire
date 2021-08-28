package questTheSpire.perks;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static questTheSpire.QuestTheSpire.activeCharacterFile;

public class LoadPerks {

    public static void onDungeonSetup() {
        int hpPerk;
        int goldPerk;
        hpPerk = activeCharacterFile.getStartMaxHP();
        AbstractDungeon.player.increaseMaxHp(hpPerk,true);

        goldPerk = activeCharacterFile.getStartGold();
        AbstractDungeon.player.gainGold(goldPerk);

    }
}
