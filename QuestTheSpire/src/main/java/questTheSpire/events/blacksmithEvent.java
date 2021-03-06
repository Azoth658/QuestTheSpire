package questTheSpire.events;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import questTheSpire.QuestTheSpireMod;
import questTheSpire.cards.Excalibur;

import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK;
import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL;
import static questTheSpire.QuestTheSpireMod.makeEventPath;

public class blacksmithEvent extends AbstractImageEvent {


    public static final String ID = QuestTheSpireMod.makeID("blacksmithEvent");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = makeEventPath("blacksmithEvent.jpg");

    private int screenNum = 0; // The initial screen we will see when encountering the event - screen 0;

    public blacksmithEvent() {

        super(NAME, DESCRIPTIONS[0] + DESCRIPTIONS[2], IMG);

        //TODO fix description based on whether player has keys or not.
        if (Settings.hasEmeraldKey && Settings.hasRubyKey && Settings.hasSapphireKey) {
            this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
        }


        imageEventText.setDialogOption(OPTIONS[0],!Settings.hasEmeraldKey || !Settings.hasRubyKey || !Settings.hasSapphireKey, new Excalibur() ); // Forge Excalibur
        imageEventText.setDialogOption(OPTIONS[1],AbstractDungeon.player.gold < 1); // Lose All Gold and Upgrade All Attacks
        imageEventText.setDialogOption(OPTIONS[2],AbstractDungeon.player.gold < 1); // Lose All Gold and Upgrade All Skills
        imageEventText.setDialogOption(OPTIONS[3]); // Leave
    }


    @Override
    protected void buttonEffect(int i) { // This is the event:

        switch (screenNum) {
            case 0: // While you are on screen number 0 (The starting screen)
                switch (i) {
                    case 0: // If you press button the first button (Button at index 0), in this case: Gain a cloak.
                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]); // Update the text of the event
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]); // 1. Change the first button to the [Leave] button
                        this.imageEventText.clearRemainingOptions(); // 2. and remove all others

                        CardCrawlGame.sound.play("CARD_UPGRADE");  // Play an upgrade sound

                        AbstractCard c = CardLibrary.getCard(Excalibur.ID);
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float) Settings.WIDTH * 0.3F, (float)Settings.HEIGHT / 2.0F));


                        screenNum = 1;
                        break; // Onto screen 1 we go.

                    case 1:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[4]); // Update the text of the event
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]); // 1. Change the first button to the [Leave] button
                        this.imageEventText.clearRemainingOptions(); // 2. and remove all others

                        AbstractDungeon.player.loseGold(AbstractDungeon.player.gold);
                        CardCrawlGame.sound.play("CARD_UPGRADE");  // Play an upgrade sound
                        for (AbstractCard d : AbstractDungeon.player.masterDeck.group) {
                            if (d.type == ATTACK && d.canUpgrade()) {
                                d.upgrade();
                                AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(d.makeStatEquivalentCopy(), MathUtils.random(0.1F, 0.9F) * (float)Settings.WIDTH, MathUtils.random(0.2F, 0.8F) * (float)Settings.HEIGHT));
                            }
                        }

                        screenNum = 1;
                        break;
                    case 2:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[5]); // Update the text of the event
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]); // 1. Change the first button to the [Leave] button
                        this.imageEventText.clearRemainingOptions(); // 2. and remove all others

                        AbstractDungeon.player.loseGold(AbstractDungeon.player.gold);
                        CardCrawlGame.sound.play("CARD_UPGRADE");  // Play an upgrade sound
                        for (AbstractCard d : AbstractDungeon.player.masterDeck.group) {
                            if (d.type == SKILL && d.canUpgrade()) {
                                d.upgrade();
                                AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(d.makeStatEquivalentCopy(), MathUtils.random(0.1F, 0.9F) * (float)Settings.WIDTH, MathUtils.random(0.2F, 0.8F) * (float)Settings.HEIGHT));
                            }
                        }

                        screenNum = 1;
                        break;

                    case 3:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[6]); // Update the text of the event
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]); // 1. Change the first button to the [Leave] button
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
