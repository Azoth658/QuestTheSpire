package questTheSpire.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import questTheSpire.QuestTheSpire;

import static questTheSpire.QuestTheSpire.makeCardPath;

public class Phoenix extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = QuestTheSpire.makeID("Phoenix");
    public static final String IMG = makeCardPath("phoenixAlt.jpg");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static int COST = 2;


    // /STAT DECLARATION/


    public Phoenix() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.player.increaseMaxHp(10, true);
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(1);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}