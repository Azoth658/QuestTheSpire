package questTheSpire.events;

import Azoth.relics.TrickstersCloak;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.colorless.Apotheosis;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.neow.NeowEvent;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import questTheSpire.QuestTheSpire;

import static questTheSpire.QuestTheSpire.makeEventPath;

public class wanderingMerchant extends AbstractImageEvent {


    public static final String ID = QuestTheSpire.makeID("wanderingMerchant");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = makeEventPath("fashionMerchant.jpg");

    private int screenNum = 0; // The initial screen we will see when encountering the event - screen 0;

    public wanderingMerchant() {
        super(NAME, DESCRIPTIONS[0], IMG);

            imageEventText.setDialogOption(OPTIONS[0],AbstractDungeon.player.gold < 50); // Pay 50 Gold and choose an Attack Card
            imageEventText.setDialogOption(OPTIONS[1],AbstractDungeon.player.gold < 50); // Pay 50 Gold and choose a Skill Card
            imageEventText.setDialogOption(OPTIONS[2],AbstractDungeon.player.gold < 50); // Pay 50 Gold and choose a Power Card
            imageEventText.setDialogOption(OPTIONS[3],AbstractDungeon.player.gold < 95); // Pay 95 Gold and choose a Rare Card
         imageEventText.setDialogOption(OPTIONS[4]); // Leave
    }



    @Override
    protected void buttonEffect(int i) { // This is the event:

        int roll = AbstractDungeon.cardRandomRng.random(99);
        AbstractCard.CardRarity cardRarity;
        AbstractCard.CardRarity cardRarityPower;

        if (roll < 55) {
            cardRarity = AbstractCard.CardRarity.COMMON;
        } else if (roll < 85) {
            cardRarity = AbstractCard.CardRarity.UNCOMMON;
        } else {
            cardRarity = AbstractCard.CardRarity.RARE;
        }

        int powerroll = AbstractDungeon.cardRandomRng.random(99);
        if (powerroll < 50) {
            cardRarityPower = AbstractCard.CardRarity.UNCOMMON;
        } else {
            cardRarityPower = AbstractCard.CardRarity.RARE;
        }

        switch (screenNum) {
            case 0: // While you are on screen number 0 (The starting screen)
                switch (i) {
                    case 0: // If you press button the first button (Button at index 0), in this case: Gain a cloak.
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]); // Update the text of the event
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]); // 1. Change the first button to the [Leave] button
                        this.imageEventText.clearRemainingOptions(); // 2. and remove all others

                        AbstractDungeon.player.loseGold(50);

                        AbstractCard c = CardLibrary.getAnyColorCard(AbstractCard.CardType.ATTACK, cardRarity);
                        c.upgrade();
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float)Settings.WIDTH * 0.3F, (float)Settings.HEIGHT / 2.0F));
                        screenNum = 1;
                        break; // Onto screen 1 we go.

                    case 1: // If you press button the second button (Button at index 1), in this case: Denial

                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]); // Update the text of the event
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]); // 1. Change the first button to the [Leave] button
                        this.imageEventText.clearRemainingOptions(); // 2. and remove all others

                        AbstractDungeon.player.loseGold(50);

                        AbstractCard d = CardLibrary.getAnyColorCard(AbstractCard.CardType.SKILL, cardRarity);
                        d.upgrade();
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(d, (float)Settings.WIDTH * 0.3F, (float)Settings.HEIGHT / 2.0F));

                        screenNum = 1;

                       //DO THE THING

                        break; // Onto screen 1 we go.
                    case 2: // If you press button the second button (Button at index 1), in this case: Denial

                    this.imageEventText.updateBodyText(DESCRIPTIONS[1]); // Update the text of the event
                    this.imageEventText.updateDialogOption(0, OPTIONS[4]); // 1. Change the first button to the [Leave] button
                    this.imageEventText.clearRemainingOptions(); // 2. and remove all others

                        AbstractDungeon.player.loseGold(50);

                        AbstractCard e = CardLibrary.getAnyColorCard(AbstractCard.CardType.POWER, cardRarityPower);
                        e.upgrade();
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(e, (float)Settings.WIDTH * 0.3F, (float)Settings.HEIGHT / 2.0F));

                    screenNum = 1;

                    break; // Onto screen 1 we go.
                    case 3:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]); // Update the text of the event
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]); // 1. Change the first button to the [Leave] button
                        this.imageEventText.clearRemainingOptions(); // 2. and remove all others

                        AbstractDungeon.player.loseGold(95);

                        AbstractCard f = CardLibrary.getAnyColorCard(AbstractCard.CardRarity.RARE);
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(f, (float)Settings.WIDTH * 0.3F, (float)Settings.HEIGHT / 2.0F));

                        screenNum = 1;
                        break;

                    case 4:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]); // Update the text of the event
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]); // 1. Change the first button to the [Leave] button
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
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) { // Once the grid screen isn't empty (we selected a card for removal)
            AbstractCard c = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0); // Get the card
            AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c, (float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2))); // Create the card removal effect
            AbstractDungeon.player.masterDeck.removeCard(c); // Remove it from the deck
            AbstractDungeon.gridSelectScreen.selectedCards.clear(); // Or you can .remove(c) instead of clear,
            // if you want to continue using the other selected cards for something
        }

    }
}
