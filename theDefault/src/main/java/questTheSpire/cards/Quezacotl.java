package questTheSpire.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.ShakeScreenAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.DieDieDieEffect;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;
import questTheSpire.QuestTheSpire;

import static questTheSpire.QuestTheSpire.makeCardPath;

public class Quezacotl extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = QuestTheSpire.makeID("Quezacotl");
    public static final String IMG = makeCardPath("Quezacotl.jpg");


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = 3;

    private static final int DAMAGE = 40;
    private static final int UPGRADE_PLUS_DMG = 10;

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

        AbstractDungeon.player.maxHealth -= 1;
        if(AbstractDungeon.player.currentHealth> AbstractDungeon.player.maxHealth){AbstractDungeon.player.currentHealth = AbstractDungeon.player.maxHealth;}
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