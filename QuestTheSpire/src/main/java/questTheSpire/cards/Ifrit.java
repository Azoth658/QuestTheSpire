package questTheSpire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;
import questTheSpire.QuestTheSpireMod;
import questTheSpire.actions.DecreaseMaxHealthAction;

import static questTheSpire.QuestTheSpireMod.makeCardPath;
import static questTheSpire.characters.MasteryCards.Enums.COLOR_MASTERY;

public class Ifrit extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = QuestTheSpireMod.makeID("Ifrit");
    public static final String IMG = makeCardPath("Ifrit.jpg");


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = COLOR_MASTERY;

    private static final int COST = 3;

    private static final int DAMAGE = 70;
    private static final int UPGRADE_PLUS_DMG = 15;

    private static final int HP_LOSS = 1;

    // /STAT DECLARATION/


    public Ifrit() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            this.addToBot(new VFXAction(new WeightyImpactEffect(m.hb.cX, m.hb.cY)));
        }
        this.addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
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