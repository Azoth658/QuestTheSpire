package questTheSpire.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RegenPower;
import questTheSpire.QuestTheSpire;
import questTheSpire.actions.DecreaseMaxHealthAction;

import static questTheSpire.QuestTheSpire.makeCardPath;
import static questTheSpire.characters.MasteryCards.Enums.COLOR_MASTERY;

public class Carbuncle extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = QuestTheSpire.makeID("Carbuncle");
    public static final String IMG = makeCardPath("carbuncleAlt.jpg");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = COLOR_MASTERY;

    private static final int COST = 3;

    private static final int REGEN = 15;
    private static final int UPGRADE_PLUS_REGEN = 5;

    private static final int HP_LOSS = 3;

    // /STAT DECLARATION/


    public Carbuncle() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = REGEN;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //Apply 15(20) Regen to the player
        this.addToBot(new ApplyPowerAction(p, p, new RegenPower(p, magicNumber), magicNumber));

        //Lose 3 Max HP, no fancy VFX but you can add them if you want
        this.addToBot(new DecreaseMaxHealthAction(p, HP_LOSS));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_REGEN);
            initializeDescription();
        }
    }
}
