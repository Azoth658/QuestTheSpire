package questTheSpire.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import questTheSpire.QuestTheSpire;
import questTheSpire.cards.EntSpirit;
import questTheSpire.cards.FangSpirit;

import static questTheSpire.QuestTheSpire.makeEventPath;
import static questTheSpire.QuestTheSpire.reincarnations;

public class tavernEvent extends AbstractImageEvent {


    public static final String ID = QuestTheSpire.makeID("tavernEvent");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = makeEventPath("cityTavern.jpg");

    private int screenNum = 0; // The initial screen we will see when encountering the event - screen 0;


    public tavernEvent() {
        super(NAME, DESCRIPTIONS[0] + reincarnations, IMG);

        imageEventText.setDialogOption(OPTIONS[0]); // Pay 100 Gold for a rest
        imageEventText.setDialogOption(OPTIONS[1]); // Steal some supplies
        imageEventText.setDialogOption(OPTIONS[2]); //
        imageEventText.setDialogOption(OPTIONS[3]); // Leave

    }



    @Override
    protected void buttonEffect(int i) { // This is the event:

        switch (screenNum) {
            case 0: // While you are on screen number 0 (The starting screen)
                switch (i) {
                    case 0: // If you press button the first button (Button at index 0), in this case: Gain a cloak.
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]); // Update the text of the event
                        this.imageEventText.updateDialogOption(0, OPTIONS[6]); // 1. Change the first button to the [Leave] button
                        this.imageEventText.clearRemainingOptions(); // 2. and remove all others





                        screenNum = 1;
                        break; // Onto screen 1 we go.

                    case 1:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]); // Update the text of the event
                        this.imageEventText.updateDialogOption(0, OPTIONS[6]); // 1. Change the first button to the [Leave] button
                        this.imageEventText.clearRemainingOptions(); // 2. and remove all others





                        screenNum = 1;
                        break;
                    case 2:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]); // Update the text of the event
                        this.imageEventText.updateDialogOption(0, OPTIONS[6]); // 1. Change the first button to the [Leave] button
                        this.imageEventText.clearRemainingOptions(); // 2. and remove all others





                        screenNum = 1;
                        break;

                    case 3:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[4]); // Update the text of the event
                        this.imageEventText.updateDialogOption(0, OPTIONS[6]); // 1. Change the first button to the [Leave] button
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
