package questTheSpire.events;

import com.evacipated.cardcrawl.mod.stslib.patches.relicInterfaces.SuperRareRelicPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import questTheSpire.QuestTheSpire;

import java.util.Random;

import static questTheSpire.QuestTheSpire.makeEventPath;

public class dragonHoard extends AbstractImageEvent {


    public static final String ID = QuestTheSpire.makeID("dragonHoard");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = makeEventPath("hoardReward.jpg");

    private int screenNum = 0; // The initial screen we will see when encountering the event - screen 0;
    private boolean spentGold;

    public dragonHoard() {
        super(NAME, DESCRIPTIONS[0], IMG);

            imageEventText.setDialogOption(OPTIONS[0],AbstractDungeon.player.gold <= 0); // Pay all your gold for 5 relics
            imageEventText.setDialogOption(OPTIONS[1]); // Refuse
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

                        AbstractDungeon.player.loseGold(AbstractDungeon.player.gold);
                        spentGold = true;

                        screenNum = 1;
                        break; // Onto screen 1 we go.

                    case 1:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]); // Update the text of the event
                        this.imageEventText.updateDialogOption(0, OPTIONS[2]); // 1. Change the first button to the [Leave] button
                        this.imageEventText.clearRemainingOptions(); // 2. and remove all others
                        spentGold = false;
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
        if (!AbstractDungeon.isScreenUp && spentGold) {
            AbstractDungeon.combatRewardScreen.open();
            AbstractDungeon.combatRewardScreen.rewards.clear();
            AbstractDungeon.combatRewardScreen.rewards.add(new RewardItem(AbstractDungeon.returnRandomScreenlessRelic(AbstractRelic.RelicTier.COMMON)));
            AbstractDungeon.combatRewardScreen.rewards.add(new RewardItem(AbstractDungeon.returnRandomScreenlessRelic(AbstractRelic.RelicTier.UNCOMMON)));
            AbstractDungeon.combatRewardScreen.rewards.add(new RewardItem(AbstractDungeon.returnRandomScreenlessRelic(AbstractRelic.RelicTier.UNCOMMON)));
            AbstractDungeon.combatRewardScreen.rewards.add(new RewardItem(AbstractDungeon.returnRandomScreenlessRelic(AbstractRelic.RelicTier.UNCOMMON)));
            AbstractDungeon.combatRewardScreen.rewards.add(new RewardItem(AbstractDungeon.returnRandomScreenlessRelic(AbstractRelic.RelicTier.RARE)));
            AbstractDungeon.combatRewardScreen.positionRewards();
            AbstractDungeon.overlayMenu.proceedButton.setLabel(DESCRIPTIONS[3]);
            AbstractDungeon.getCurrRoom().rewardPopOutTimer = 0.25F;
            spentGold = false;
        }

    }
}
