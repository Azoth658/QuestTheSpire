package questTheSpire.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;
import questTheSpire.QuestTheSpire;

import static questTheSpire.QuestTheSpire.makeCardPath;

public class Yojimbo extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = QuestTheSpire.makeID("Yojimbo");
    public static final String IMG = makeCardPath("Yojimbo.jpg");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = 3;
    private static int GOLDGAIN = 100;


    // /STAT DECLARATION/


    public Yojimbo() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.player.maxHealth -= 1;
        if(AbstractDungeon.player.currentHealth> AbstractDungeon.player.maxHealth){AbstractDungeon.player.currentHealth = AbstractDungeon.player.maxHealth;}
        AbstractDungeon.player.gainGold(GOLDGAIN);
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            GOLDGAIN = 125;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}