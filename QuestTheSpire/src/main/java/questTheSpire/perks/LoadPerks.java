package questTheSpire.perks;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static questTheSpire.QuestTheSpire.activeCharacterFile;

public class LoadPerks {

    public static void onDungeonSetup() {
        int hpPerk;
        int goldPerk;
        hpPerk = activeCharacterFile.getStartMaxHP() * 2;
        AbstractDungeon.player.increaseMaxHp(hpPerk,true);

        goldPerk = activeCharacterFile.getStartGold() * 20;
        AbstractDungeon.player.gainGold(goldPerk);

    }
}
