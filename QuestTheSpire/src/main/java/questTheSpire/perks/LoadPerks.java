package questTheSpire.perks;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import questTheSpire.patches.CurrentRoomHack;
import questTheSpire.util.CharacterSaveFile;

import static questTheSpire.QuestTheSpireMod.activeCharacterFile;

public class LoadPerks {

    public static void onDungeonSetup() {
        int hpPerk;
        int goldPerk;
        int energyPerk;
        int potionPerk;
        int drawPerk;

        energyPerk = activeCharacterFile.getData(CharacterSaveFile.SaveDataEnum.ENERGY);
        potionPerk = activeCharacterFile.getData(CharacterSaveFile.SaveDataEnum.POTION_SLOTS);
        drawPerk = activeCharacterFile.getData(CharacterSaveFile.SaveDataEnum.DRAW_AMOUNT);
        hpPerk = activeCharacterFile.getData(CharacterSaveFile.SaveDataEnum.MAX_HP);

        AbstractDungeon.player.increaseMaxHp(hpPerk,true);

        goldPerk = activeCharacterFile.getData(CharacterSaveFile.SaveDataEnum.GOLD);
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
        if (activeCharacterFile.getData(CharacterSaveFile.SaveDataEnum.COMMON_RELIC) > 0){
            for (int i = 1; i <= activeCharacterFile.getData(CharacterSaveFile.SaveDataEnum.COMMON_RELIC); i++) {
                AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.COMMON).instantObtain();
            }
        }
        if (activeCharacterFile.getData(CharacterSaveFile.SaveDataEnum.UNCOMMON_RELIC) > 0){
            for (int i = 1; i <= activeCharacterFile.getData(CharacterSaveFile.SaveDataEnum.UNCOMMON_RELIC); i++) {
                AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.UNCOMMON).instantObtain();
            }
        }
        if (activeCharacterFile.getData(CharacterSaveFile.SaveDataEnum.RARE_RELIC) > 0){
            for (int i = 1; i <= activeCharacterFile.getData(CharacterSaveFile.SaveDataEnum.RARE_RELIC); i++) {
                AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.RARE).instantObtain();
            }
        }
        CurrentRoomHack.roomHack = false;
    }
}
