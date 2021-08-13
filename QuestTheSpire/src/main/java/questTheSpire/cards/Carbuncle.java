package questTheSpire.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RegenPower;
import questTheSpire.QuestTheSpire;

import static questTheSpire.QuestTheSpire.makeCardPath;

public class Carbuncle extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * In-Progress Form At the start of your turn, play a TOUCH.
     */

    // TEXT DECLARATION

    public static final String ID = QuestTheSpire.makeID("Carbuncle");
    public static final String IMG = makeCardPath("carbuncleAlt.jpg");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = 3;

    private static final int MAGIC = 15;
    private static final int UPGRADE_MAGIC = 5;

    // /STAT DECLARATION/


    public Carbuncle() {

        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = MAGIC;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new RegenPower(p, magicNumber), magicNumber));
                    AbstractDungeon.player.maxHealth -= 3;
                    if(AbstractDungeon.player.currentHealth> AbstractDungeon.player.maxHealth){AbstractDungeon.player.currentHealth = AbstractDungeon.player.maxHealth;}
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC);
            initializeDescription();
        }
    }
}
