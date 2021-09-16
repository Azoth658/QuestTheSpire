package questTheSpire.perks;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import questTheSpire.patches.CurrentRoomHack;

import static questTheSpire.QuestTheSpire.activeCharacterFile;

public class LoadPerks {

    public static void onDungeonSetup() {
        int hpPerk;
        int goldPerk;
        int energyPerk;
        int potionPerk;
        int drawPerk;

        energyPerk = activeCharacterFile.getEnergy();
        potionPerk = activeCharacterFile.getPotionSlots();
        drawPerk = activeCharacterFile.getDraw();
        hpPerk = activeCharacterFile.getStartMaxHP();

        AbstractDungeon.player.increaseMaxHp(hpPerk,true);

        goldPerk = activeCharacterFile.getStartGold();
        AbstractDungeon.player.gainGold(goldPerk);

        AbstractDungeon.player.energy.energyMaster = AbstractDungeon.player.energy.energyMaster + energyPerk;

        AbstractPlayer var10000 = AbstractDungeon.player;
        var10000.potionSlots += potionPerk;
        if (potionPerk == 1){
            AbstractDungeon.player.potions.add(new PotionSlot(AbstractDungeon.player.potionSlots - 1));
        }
        if (potionPerk == 2) {
            AbstractDungeon.player.potions.add(new PotionSlot(AbstractDungeon.player.potionSlots - 2));
            AbstractDungeon.player.potions.add(new PotionSlot(AbstractDungeon.player.potionSlots - 1));
        }

        AbstractDungeon.player.masterHandSize = AbstractDungeon.player.masterHandSize + drawPerk;

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
