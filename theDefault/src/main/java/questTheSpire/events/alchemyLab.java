package questTheSpire.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.Alchemize;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import questTheSpire.QuestTheSpire;

import static questTheSpire.QuestTheSpire.makeEventPath;

public class alchemyLab extends AbstractImageEvent {


    public static final String ID = QuestTheSpire.makeID("alchemyLab");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = makeEventPath("alchemyLab.jpg");

    private int screenNum = 0; // The initial screen we will see when encountering the event - screen 0;
    private boolean spentGold;

    public alchemyLab() {
        super(NAME, DESCRIPTIONS[0], IMG);

            imageEventText.setDialogOption(OPTIONS[0],AbstractDungeon.player.gold < 100); // Gain Alchemise
            imageEventText.setDialogOption(OPTIONS[1],AbstractDungeon.player.gold < 200); // Buy Slot
            imageEventText.setDialogOption(OPTIONS[2]); // Leave
    }



    @Override
    protected void buttonEffect(int i) { // This is the event:

        switch (screenNum) {
            case 0: // While you are on screen number 0 (The starting screen)
                switch (i) {
                    case 0: // If you press button the first button (Button at index 0), in this case: Gain a cloak.
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]); // Update the text of the event
                        this.imageEventText.updateDialogOption(0, OPTIONS[2]); // 1. Change the first button to the [Leave] button
                        this.imageEventText.clearRemainingOptions(); // 2. and remove all others

                        AbstractCard c = CardLibrary.getCard(Alchemize.ID);
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float) Settings.WIDTH * 0.3F, (float)Settings.HEIGHT / 2.0F));


                        AbstractDungeon.player.loseGold(100);

                        screenNum = 1;
                        break; // Onto screen 1 we go.

                    case 1:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]); // Update the text of the event
                        this.imageEventText.updateDialogOption(0, OPTIONS[2]); // 1. Change the first button to the [Leave] button
                        this.imageEventText.clearRemainingOptions(); // 2. and remove all others

                            AbstractDungeon.player.potionSlots++;
                            AbstractDungeon.player.potions.add(new PotionSlot(AbstractDungeon.player.potionSlots - 1));
                            AbstractDungeon.player.loseGold(200);

                        screenNum = 1;
                        break;
                    case 2:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]); // Update the text of the event
                        this.imageEventText.updateDialogOption(0, OPTIONS[2]); // 1. Change the first button to the [Leave] button
                        this.imageEventText.clearRemainingOptions(); // 2. and remove all others
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
