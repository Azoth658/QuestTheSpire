package questTheSpire.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import questTheSpire.QuestTheSpireMod;

import static questTheSpire.QuestTheSpireMod.makeCardPath;
import static questTheSpire.characters.MasteryCards.Enums.COLOR_MASTERY;

public class PowerMastery extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = QuestTheSpireMod.makeID("PowerMastery");
    public static final String IMG = makeCardPath("powerMastery.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = COLOR_MASTERY;

    private static int COST = 2;
    private static final int UPGRADE_COST = 1;
    private static boolean EXHAUST = true ;

    // /STAT DECLARATION/


    public PowerMastery() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;

        this.exhaust = EXHAUST;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int countbuffs = 0;
        for (AbstractPower ap : p.powers) {
            if (ap.type == AbstractPower.PowerType.BUFF) {
                countbuffs++;
            }
        }
        this.addToBot(new ApplyPowerAction(p,p,new FocusPower(p,this.magicNumber * countbuffs)));
        magicNumber = 1;
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (this.timesUpgraded == 2){
            this.upgradeBaseCost(UPGRADE_COST);
            this.timesUpgraded = 3;
            this.name = cardStrings.NAME + "+" + this.timesUpgraded;
            rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
            initializeDescription();
            this.retain = true;
            EXHAUST = false;
        } else if (this.timesUpgraded == 1){
            this.timesUpgraded = 2;
            this.name = cardStrings.NAME + "+" + this.timesUpgraded;
            rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
            initializeDescription();
            this.retain = true;
            EXHAUST = false;
        } else if (this.timesUpgraded == 0){
            this.timesUpgraded = 1;
            this.name = cardStrings.NAME + "+" + this.timesUpgraded;
            this.upgraded = true;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
            this.retain = true;
            EXHAUST = true;
        }
    }

    @Override
    public boolean canUpgrade(){
        if (timesUpgraded<3){
            return true;
        } else return false;
    }
}