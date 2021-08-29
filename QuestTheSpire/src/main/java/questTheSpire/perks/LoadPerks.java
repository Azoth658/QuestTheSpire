package questTheSpire.perks;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.neow.NeowRoom;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.EmptyRoom;
import questTheSpire.patches.CurrentRoomHack;

import static questTheSpire.QuestTheSpire.activeCharacterFile;

public class LoadPerks {

    public static void onDungeonSetup() {
        int hpPerk;
        int goldPerk;
        hpPerk = activeCharacterFile.getStartMaxHP();
        AbstractDungeon.player.increaseMaxHp(hpPerk,true);

        goldPerk = activeCharacterFile.getStartGold();
        AbstractDungeon.player.gainGold(goldPerk);

        CurrentRoomHack.roomHack = true;
        //TODO make loading relics with can spawn criteria work
        if (activeCharacterFile.getCommonRelic() > 0){
            for (int i = 1; i <= activeCharacterFile.getCommonRelic(); i++) {
                AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.COMMON).instantObtain();
            }
        }
        if (activeCharacterFile.getUncommonRelic() > 0){
            for (int i = 1; i <= activeCharacterFile.getUncommonRelic(); i++) {
                AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.UNCOMMON).instantObtain();
            }
        }
        if (activeCharacterFile.getRareRelic() > 0){
            for (int i = 1; i <= activeCharacterFile.getRareRelic(); i++) {
                AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.RARE).instantObtain();
            }
        }
        CurrentRoomHack.roomHack = false;
    }
}
