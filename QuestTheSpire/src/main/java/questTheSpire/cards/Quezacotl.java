package questTheSpire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import questTheSpire.QuestTheSpireMod;
import questTheSpire.actions.DecreaseMaxHealthAction;

import static questTheSpire.QuestTheSpireMod.makeCardPath;
import static questTheSpire.characters.MasteryCards.Enums.COLOR_MASTERY;

public class Quezacotl extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = QuestTheSpireMod.makeID("Quezacotl");
    public static final String IMG = makeCardPath("Quezacotl.jpg");


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = COLOR_MASTERY;

    private static final int COST = 3;

    private static final int DAMAGE = 40;
    private static final int UPGRADE_PLUS_DMG = 10;

    private static final int HP_LOSS = 1;

    // /STAT DECLARATION/


    public Quezacotl() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        this.isMultiDamage = true;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.LIGHTNING));
        this.addToBot(new DecreaseMaxHealthAction(p, HP_LOSS));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
        }
    }
}