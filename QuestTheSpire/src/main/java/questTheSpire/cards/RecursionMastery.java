package questTheSpire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.RedoAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import questTheSpire.QuestTheSpireMod;

import static questTheSpire.QuestTheSpireMod.makeCardPath;
import static questTheSpire.characters.MasteryCards.Enums.COLOR_MASTERY;

public class RecursionMastery extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = QuestTheSpireMod.makeID("RecursionMastery");
    public static final String IMG = makeCardPath("recursionMastery.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF_AND_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = COLOR_MASTERY;

    private static int COST = 1;
    private static final int UPGRADE_COST = 0;

    // /STAT DECLARATION/


    public RecursionMastery() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = 6;
        this.magicNumber = this.baseMagicNumber;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(m, new DamageInfo(p, magicNumber, damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
        int i = 0;
        if(this.timesUpgraded == 0){
            this.addToBot(new RedoAction());
        } else while (i < (this.timesUpgraded)) {
                this.addToBot(new RedoAction());
                i++;
                }
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
            this.upgradeMagicNumber(2);
            this.timesUpgraded = 2;
            this.name = cardStrings.NAME + "+" + this.timesUpgraded;
            rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
            initializeDescription();
        } else if (this.timesUpgraded == 0){
            this.upgradeMagicNumber(2);
            this.timesUpgraded = 1;
            this.name = cardStrings.NAME + "+" + this.timesUpgraded;
            this.upgraded = true;
            rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
            initializeDescription();
        }
    }

    @Override
    public boolean canUpgrade(){
        if (timesUpgraded<3){
            return true;
        } else return false;
    }
}