package questTheSpire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMiscAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import questTheSpire.QuestTheSpire;

import static com.megacrit.cardcrawl.cards.AbstractCard.CardColor.COLORLESS;
import static com.megacrit.cardcrawl.cards.AbstractCard.CardColor.RED;
import static questTheSpire.QuestTheSpire.makeCardPath;

// public class ${NAME} extends AbstractDynamicCard
// Remove this line when you make a template. Refer to https://github.com/daviscook477/BaseMod/wiki/AutoAdd if you want to know what it does.
public class FireFlask extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = QuestTheSpire.makeID("AlchemistFire");
    public static final String IMG = makeCardPath("FireFlask.jpg");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = COLORLESS;



    private static final int COST = 2;  // COST = ${COST}

    // /STAT DECLARATION/


    public FireFlask() { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = 10;
        this.baseMagicNumber = 5;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        for(int i = 0; i < this.magicNumber; ++i) {
            this.addToBot(new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.FIRE));
        }
        this.addToBot(new MakeTempCardInDrawPileAction(new Burn(), 1, false, true));
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeMagicNumber(2);
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }

    }
}
