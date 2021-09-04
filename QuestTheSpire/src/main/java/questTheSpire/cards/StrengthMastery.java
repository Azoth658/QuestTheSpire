package questTheSpire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.RedoAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import questTheSpire.QuestTheSpire;
import questTheSpire.actions.StrengthMasteryAction;

import static questTheSpire.QuestTheSpire.makeCardPath;
import static questTheSpire.characters.MasteryCards.Enums.COLOR_MASTERY;

public class StrengthMastery extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = QuestTheSpire.makeID("StrengthMastery");
    public static final String IMG = makeCardPath("strengthMastery.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = COLOR_MASTERY;

    private static int COST = 1;
    private static final int UPGRADE_COST = 0;

    // /STAT DECLARATION/


    public StrengthMastery() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = 6;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        this.addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber), magicNumber));
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
            this.upgradeDamage(2);
            this.upgradeMagicNumber(1);
            this.timesUpgraded = 2;
            this.name = cardStrings.NAME + "+" + this.timesUpgraded;
            initializeDescription();
        } else if (this.timesUpgraded == 0){
            this.upgradeDamage(2);
            this.upgradeMagicNumber(1);
            this.timesUpgraded = 1;
            this.name = cardStrings.NAME + "+" + this.timesUpgraded;
            this.upgraded = true;
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