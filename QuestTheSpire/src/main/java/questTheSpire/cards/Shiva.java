package questTheSpire.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;
import questTheSpire.QuestTheSpireMod;
import questTheSpire.actions.DecreaseMaxHealthAction;

import static questTheSpire.QuestTheSpireMod.makeCardPath;
import static questTheSpire.characters.MasteryCards.Enums.COLOR_MASTERY;

public class Shiva extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = QuestTheSpireMod.makeID("Shiva");
    public static final String IMG = makeCardPath("ShivaAlt.jpg");


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = COLOR_MASTERY;

    private static final int COST = 3;
    private static final int HP_LOSS = 1;
    private static final int BLUR = 3;

    // /STAT DECLARATION/


    public Shiva() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = block = 60;
        magicNumber = baseMagicNumber = BLUR;
        this.exhaust = true;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GainBlockAction(p, p, this.block));
        this.addToBot(new ApplyPowerAction(p, p, new BlurPower(p, magicNumber), magicNumber));
        this.addToBot(new DecreaseMaxHealthAction(p, HP_LOSS));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.upgradeBlock(10);
        }
    }
}