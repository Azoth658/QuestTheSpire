package questTheSpire.events;

import Azoth.relics.Omelette;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.potions.FairyPotion;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import questTheSpire.QuestTheSpire;
import questTheSpire.cards.EntSpirit;
import questTheSpire.cards.FangSpirit;
import questTheSpire.relics.FairyBlessing;

import static questTheSpire.QuestTheSpire.makeEventPath;

public class fairyEvent extends AbstractImageEvent {


    public static final String ID = QuestTheSpire.makeID("fairyEvent");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = makeEventPath("fairyEvent.jpg");

    private int screenNum = 0; // The initial screen we will see when encountering the event - screen 0;

    public fairyEvent() {
        super(NAME, DESCRIPTIONS[0], IMG);

        imageEventText.setDialogOption(OPTIONS[0] + AbstractDungeon.player.potionSlots + OPTIONS[1]); // Bottle the fairies - fill your potion slots with Fairy in a bottle.
        imageEventText.setDialogOption(OPTIONS[2]); // Fairies Blessing - When you discard a fairy in the bottle gain 10 max HP

    }



    @Override
    protected void buttonEffect(int i) { // This is the event:

        switch (screenNum) {
            case 0: // While you are on screen number 0 (The starting screen)
                switch (i) {
                    case 0: // If you press button the first button (Button at index 0), in this case: Gain a cloak.
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]); // Update the text of the event
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]); // 1. Change the first button to the [Leave] button
                        this.imageEventText.clearRemainingOptions(); // 2. and remove all others

                        int remove;
                        int slotsToFill = AbstractDungeon.player.potionSlots;
                        for(remove = 0; remove < slotsToFill; ++remove) {
                            AbstractDungeon.getCurrRoom().addPotionToRewards(PotionHelper.getPotion(FairyPotion.POTION_ID));
                        }

                        AbstractDungeon.combatRewardScreen.open(this.DESCRIPTIONS[3]);
                        AbstractDungeon.getCurrRoom().rewardPopOutTimer = 0.0F;

                        AbstractDungeon.combatRewardScreen.rewards.remove(remove);


                        screenNum = 1;
                        break; // Onto screen 1 we go.

                    case 1:
                        imageEventText.loadImage("questTheSpireResources/images/events/fairyEvent1.jpg");
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]); // Update the text of the event
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]); // 1. Change the first button to the [Leave] button
                        this.imageEventText.clearRemainingOptions(); // 2. and remove all others

                        RelicLibrary.getRelic(FairyBlessing.ID).makeCopy().instantObtain();

                        screenNum = 1;
                        break;
                }
                break;

            case 1: // Welcome to screenNum = 1;
                switch (i) {
                    case 0: // If you press the first (and this should be the only) button,
                        openMap(); // You'll open the map and end the event.
                        break;
                }
                break;
        }
    }

    public void update() { // We need the update() when we use grid screens (such as, in this case, the screen for selecting a card to remove)
        super.update(); // Do everything the original update()
    }
}
