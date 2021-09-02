package questTheSpire.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import questTheSpire.QuestTheSpire;
import questTheSpire.powers.PoisonMasteryPower;

import static questTheSpire.QuestTheSpire.makeCardPath;
import static questTheSpire.characters.MasteryCards.Enums.COLOR_MASTERY;

public class PoisonMastery extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = QuestTheSpire.makeID("PoisonMastery");
    public static final String IMG = makeCardPath("poisonMastery.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = COLOR_MASTERY;

    private static int COST = 2;
    private static final int UPGRADE_COST = 1;


    // /STAT DECLARATION/


    public PoisonMastery() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p,p,new PoisonMasteryPower(p,p,magicNumber)));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (this.timesUpgraded == 2){
            this.upgradeBaseCost(UPGRADE_COST);
            this.timesUpgraded = 3;
            this.name = cardStrings.NAME + "+" + this.timesUpgraded;
            initializeDescription();
        } else if (this.timesUpgraded == 1){
            this.timesUpgraded = 2;
            this.upgradeMagicNumber(1);
            this.name = cardStrings.NAME + "+" + this.timesUpgraded;
            initializeDescription();
        } else if (this.timesUpgraded == 0){
            this.timesUpgraded = 1;
            this.upgradeMagicNumber(1);
            this.name = cardStrings.NAME + "+" + this.timesUpgraded;
            initializeDescription();
            this.upgraded = true;
        }
    }

    @Override
    public boolean canUpgrade(){
        if (timesUpgraded<3){
            return true;
        } else return false;
    }
}