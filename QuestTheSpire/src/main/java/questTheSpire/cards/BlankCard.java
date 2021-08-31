package questTheSpire.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RegenPower;
import questTheSpire.QuestTheSpire;
import questTheSpire.actions.DecreaseMaxHealthAction;
import questTheSpire.patches.HiddenCard;

import static questTheSpire.QuestTheSpire.makeCardPath;
import static questTheSpire.characters.MasteryCards.Enums.COLOR_MASTERY;

public class BlankCard extends AbstractDynamicCard implements HiddenCard {

    // TEXT DECLARATION

    public static final String ID = QuestTheSpire.makeID("BlankCard");
    public static final String IMG = makeCardPath("BlankCard.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = COLOR_MASTERY;
    private static final int COST = -2;

    // /STAT DECLARATION/
    public BlankCard() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
    }
}
