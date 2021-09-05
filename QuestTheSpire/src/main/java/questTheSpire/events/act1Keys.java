package questTheSpire.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.city.Colosseum;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import questTheSpire.QuestTheSpire;

import static questTheSpire.QuestTheSpire.makeEventPath;

public class act1Keys extends AbstractImageEvent {


    public static final String ID = QuestTheSpire.makeID("act1Keys");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = makeEventPath("act1Keys.jpg");

    private int screenNum = 0;

    public act1Keys() {
        super(NAME, DESCRIPTIONS[0], IMG);

        imageEventText.setDialogOption(OPTIONS[0]); // Open
        imageEventText.setDialogOption(OPTIONS[5]); // Leave
    }


    @Override
    protected void buttonEffect(int i) {
        int maxHPloss;
        if(AbstractDungeon.ascensionLevel < 15){
            maxHPloss = AbstractDungeon.player.maxHealth / 3;
        } else maxHPloss = AbstractDungeon.player.maxHealth / 2;

        switch (screenNum) {
            case 0:
                switch (i) {
                    case 0:
                        imageEventText.loadImage("questTheSpireResources/images/events/act1Keys2.jpg");
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[1]);

                        Settings.hasEmeraldKey = false;
                        Settings.hasRubyKey = false;
                        Settings.hasSapphireKey = false;

                        screenNum = 1;
                        break;

                    case 1:

                        this.imageEventText.updateBodyText(DESCRIPTIONS[5]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 5;
                        break;
                }
                break;

            case 1: //
                switch (i){
                    case 0:
                        imageEventText.loadImage("questTheSpireResources/images/events/act1Keys3.jpg");
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[2]);

                        AbstractDungeon.player.decreaseMaxHealth(maxHPloss);

                        screenNum = 2;
                        break;

                    case 1:

                        this.imageEventText.updateBodyText(DESCRIPTIONS[5]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 5;
                        break;
                }
                break;

            case 2:
                switch (i){
                    case 0:
                        imageEventText.loadImage("questTheSpireResources/images/events/act1Keys3.jpg");
                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]);

                        screenNum = 3;
                        AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter("Book of Stabbing");
                        AbstractDungeon.getCurrRoom().rewards.clear();
                        AbstractDungeon.getCurrRoom().addRelicToRewards(AbstractRelic.RelicTier.RARE);
                        AbstractDungeon.getCurrRoom().addRelicToRewards(AbstractRelic.RelicTier.RARE);
                        AbstractDungeon.getCurrRoom().addRelicToRewards(AbstractRelic.RelicTier.RARE);
                        AbstractDungeon.getCurrRoom().addGoldToRewards(250);
                        AbstractDungeon.getCurrRoom().eliteTrigger = true;
                        this.enterCombatFromImage();
                        return;

                    case 1:

                        this.imageEventText.updateBodyText(DESCRIPTIONS[5]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 5;
                        break;
                }
                break;

            case 3:
                switch (i){
                    case 0:
                        imageEventText.loadImage("questTheSpireResources/images/events/act1Keys4.jpg");
                        this.imageEventText.updateBodyText(DESCRIPTIONS[4]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]);

                        //TODO Add new curse to cast to master deck in order to cast the spell

                        screenNum = 4;
                        break;

                    case 1:

                        this.imageEventText.updateBodyText(DESCRIPTIONS[5]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 5;
                        break;

                }
                break;

            case 4:
                switch (i){
                    case 0:

                        this.imageEventText.updateBodyText(DESCRIPTIONS[5]);
                        this.imageEventText.clearRemainingOptions();

                        AbstractDungeon.player.maxHealth = AbstractDungeon.player.maxHealth * 3;

                        screenNum = 5;
                        break;

                    case 1:

                        //TODO add Apple Relic you take instead of eating
                        this.imageEventText.updateBodyText(DESCRIPTIONS[5]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 5;
                        break;
                }
                break;

            case 5:
                switch (i) {
                    case 0:
                        openMap();
                        break;
                }
                break;
        }
    }

    public void reopen() {
        if (screenNum == 3) {
            AbstractDungeon.resetPlayer();
            AbstractDungeon.player.drawX = (float)Settings.WIDTH * 0.25F;
            AbstractDungeon.player.preBattlePrep();
            this.enterImageFromCombat();
        }

    }

    public void update() { // We need the update() when we use grid screens (such as, in this case, the screen for selecting a card to remove)
        super.update(); // Do everything the original update()
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) { // Once the grid screen isn't empty (we selected a card for removal)
            AbstractCard c = (AbstractCard) AbstractDungeon.gridSelectScreen.selectedCards.get(0); // Get the card
            AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2))); // Create the card removal effect
            AbstractDungeon.player.masterDeck.removeCard(c); // Remove it from the deck
            AbstractDungeon.gridSelectScreen.selectedCards.clear(); // Or you can .remove(c) instead of clear,
            // if you want to continue using the other selected cards for something
        }

    }
}