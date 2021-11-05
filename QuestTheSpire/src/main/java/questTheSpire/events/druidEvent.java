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
import questTheSpire.QuestTheSpireMod;
import questTheSpire.cards.EntSpirit;
import questTheSpire.cards.FangSpirit;

import static questTheSpire.QuestTheSpireMod.makeEventPath;

public class druidEvent extends AbstractImageEvent {


    public static final String ID = QuestTheSpireMod.makeID("druidEvent");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = makeEventPath("druidEvent.png");

    private int screenNum = 0; // The initial screen we will see when encountering the event - screen 0;

    private float HEALTH_LOSS_PERCENTAGE = 0.15F; // 10%
    private float HEALTH_LOSS_PERCENTAGE_HIGH_ASCENSION = 0.20F; // 15%
    private float MAXHEALTH_LOSS_PERCENTAGE = 0.10F; // 10%
    private float MAXHEALTH_LOSS_PERCENTAGE_HIGH_ASCENSION = 0.15F; // 15%

    private int healthdamage; //The actual number of how much Max HP we're going to lose.
    private int maxhealthdamage; //The actual number of how much Max HP we're going to lose.

    public druidEvent() {
        super(NAME, DESCRIPTIONS[0], IMG);

        if (AbstractDungeon.ascensionLevel < 15) { // If the player is ascension 15 or above, lose 5% max hp. Else, lose just 3%.
            maxhealthdamage = (int) ((float) AbstractDungeon.player.maxHealth * MAXHEALTH_LOSS_PERCENTAGE);
        } else {
            maxhealthdamage = (int) ((float) AbstractDungeon.player.maxHealth * MAXHEALTH_LOSS_PERCENTAGE_HIGH_ASCENSION);
        }

        if (AbstractDungeon.ascensionLevel <= 15) { // If the player is ascension 15 or above, lose 5% max hp. Else, lose just 3%.
            healthdamage = (int) ((float) AbstractDungeon.player.maxHealth * HEALTH_LOSS_PERCENTAGE);
        } else {
            healthdamage = (int) ((float) AbstractDungeon.player.maxHealth * HEALTH_LOSS_PERCENTAGE_HIGH_ASCENSION);
        }

        imageEventText.setDialogOption(OPTIONS[0] + 15 + OPTIONS[1], new FangSpirit() ); // Earn Spirit Wolf for damage
        imageEventText.setDialogOption(OPTIONS[2] + maxhealthdamage + OPTIONS[3], new EntSpirit() ); // Earn Ent Spirit for lose Max HP
        imageEventText.setDialogOption(OPTIONS[4] + healthdamage + OPTIONS [5]); // Gain Max Hp for current life
        imageEventText.setDialogOption(OPTIONS[6]); // Leave

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

                        CardCrawlGame.sound.play("BLUNT_FAST");  // Play a hit sound
                        AbstractDungeon.player.damage(new DamageInfo((AbstractCreature)null, 15));

                        AbstractCard c = CardLibrary.getCard(FangSpirit.ID);
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float) Settings.WIDTH * 0.3F, (float)Settings.HEIGHT / 2.0F));


                        screenNum = 1;
                        break; // Onto screen 1 we go.

                    case 1:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]); // Update the text of the event
                        this.imageEventText.updateDialogOption(0, OPTIONS[6]); // 1. Change the first button to the [Leave] button
                        this.imageEventText.clearRemainingOptions(); // 2. and remove all others

                        CardCrawlGame.sound.play("BLUNT_FAST");  // Play a hit sound
                        AbstractDungeon.player.decreaseMaxHealth(maxhealthdamage);

                        AbstractCard d = CardLibrary.getCard(EntSpirit.ID);
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(d, (float) Settings.WIDTH * 0.3F, (float)Settings.HEIGHT / 2.0F));

                        screenNum = 1;
                        break;
                    case 2:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]); // Update the text of the event
                        this.imageEventText.updateDialogOption(0, OPTIONS[6]); // 1. Change the first button to the [Leave] button
                        this.imageEventText.clearRemainingOptions(); // 2. and remove all others
                        CardCrawlGame.sound.play("BLUNT_FAST");  // Play a hit sound
                        AbstractDungeon.player.damage(new DamageInfo((AbstractCreature)null, healthdamage));
                        AbstractDungeon.player.maxHealth += 10;

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
