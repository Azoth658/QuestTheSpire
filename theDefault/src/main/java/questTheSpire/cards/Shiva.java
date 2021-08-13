package questTheSpire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;
import questTheSpire.QuestTheSpire;

import static questTheSpire.QuestTheSpire.makeCardPath;

public class Shiva extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = QuestTheSpire.makeID("Shiva");
    public static final String IMG = makeCardPath("ShivaAlt.jpg");


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = 3;

    // /STAT DECLARATION/


    public Shiva() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = 60;
        this.exhaust = true;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GainBlockAction(p, p, this.block));
        this.addToBot(new ApplyPowerAction(p, p, new BlurPower(p, 3), 3));
        AbstractDungeon.player.maxHealth -= 1;
        if(AbstractDungeon.player.currentHealth> AbstractDungeon.player.maxHealth){AbstractDungeon.player.currentHealth = AbstractDungeon.player.maxHealth;}
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