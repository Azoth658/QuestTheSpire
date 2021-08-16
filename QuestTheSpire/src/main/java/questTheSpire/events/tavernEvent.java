package questTheSpire.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.Flex;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.ui.campfire.RestOption;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSleepEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import questTheSpire.QuestTheSpire;
import questTheSpire.cards.EntSpirit;
import questTheSpire.cards.FangSpirit;

import static questTheSpire.QuestTheSpire.*;

public class tavernEvent extends AbstractImageEvent {


    public static final String ID = QuestTheSpire.makeID("tavernEvent");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = makeEventPath("cityTavern.jpg");

    private int screenNum = 0; // The initial screen we will see when encountering the event - screen 0;
    private int healCost;
    private int healAmt;


    public tavernEvent() {
        super(NAME, DESCRIPTIONS[0], IMG);

        healCost = (AbstractDungeon.player.maxHealth - AbstractDungeon.player.currentHealth) *3;
        healAmt = (AbstractDungeon.player.maxHealth - AbstractDungeon.player.currentHealth);

        imageEventText.setDialogOption(OPTIONS[0] + healCost + OPTIONS[1],AbstractDungeon.player.gold < healCost); // Pay for a full heal
        imageEventText.setDialogOption(OPTIONS[2]); // Steal 99 Gold
        imageEventText.setDialogOption(OPTIONS[3], new Flex().upgraded); // Arm Wrestle
        imageEventText.setDialogOption(OPTIONS[4]); // Leave


    }



    @Override
    protected void buttonEffect(int i) { // This is the event:

        switch (screenNum) {
            case 0: // While you are on screen number 0 (The starting screen)
                switch (i) {
                    case 0: // If you press button the first button (Button at index 0), in this case: Gain a cloak.
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]); // Update the text of the event
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]); // 1. Change the first button to the [Leave] button
                        this.imageEventText.clearRemainingOptions(); // 2. and remove all others

                        AbstractDungeon.player.loseGold(healCost);
                        AbstractDungeon.player.heal(healAmt);

                        screenNum = 1;
                        break; // Onto screen 1 we go.

                    case 1:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]); // Update the text of the event
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]); // 1. Change the first button to the [Leave] button
                        this.imageEventText.clearRemainingOptions(); // 2. and remove all others

                        AbstractDungeon.player.gainGold(99);

                        screenNum = 1;
                        break;
                    case 2:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]); // Update the text of the event
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]); // 1. Change the first button to the [Leave] button
                        this.imageEventText.clearRemainingOptions(); // 2. and remove all others

                        AbstractCard c = CardLibrary.getCard(Flex.ID);
                        c.upgrade();
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float)Settings.WIDTH * 0.4F, (float)Settings.HEIGHT / 2.0F));
                        c = CardLibrary.getCard(Wound.ID);
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float)Settings.WIDTH * 0.6F, (float)Settings.HEIGHT / 2.0F));

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
