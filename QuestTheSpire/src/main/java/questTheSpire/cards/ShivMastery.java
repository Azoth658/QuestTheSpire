package questTheSpire.cards;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.green.Accuracy;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AccuracyPower;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;
import questTheSpire.QuestTheSpireMod;

import java.util.ArrayList;
import java.util.List;

import static questTheSpire.QuestTheSpireMod.makeCardPath;
import static questTheSpire.characters.MasteryCards.Enums.COLOR_MASTERY;

public class ShivMastery extends AbstractDynamicCard {

    private static ArrayList<TooltipInfo> fakeTip;
    // TEXT DECLARATION

    public static final String ID = QuestTheSpireMod.makeID("ShivMastery");
    public static final String IMG = makeCardPath("shivMastery.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = COLOR_MASTERY;

    private static final int COST = 0;

    private static final int DAMAGE = 4;
    private static boolean EXHAUST = true ;

    // /STAT DECLARATION/


    public ShivMastery() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = EXHAUST;
    }

    @Override
    public List<TooltipInfo> getCustomTooltipsTop() {
        if (fakeTip == null) {
            fakeTip = new ArrayList<>();
            String title = CardCrawlGame.languagePack.getCardStrings(Accuracy.ID).NAME;
            String[] parts = CardCrawlGame.languagePack.getPowerStrings(AccuracyPower.POWER_ID).DESCRIPTIONS;
            fakeTip.add(new TooltipInfo(title, parts[0].substring(0, parts[0].length()-3)+parts[1]));
        }
        return fakeTip;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            this.addToBot(new VFXAction(new WeightyImpactEffect(m.hb.cX, m.hb.cY)));
        }
        this.addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        this.addToBot(new ApplyPowerAction(p,p,new AccuracyPower(p,this.magicNumber)));
        this.addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractGameAction action : AbstractDungeon.actionManager.actions) {
                    if (action instanceof UseCardAction) {
                        ((UseCardAction) action).reboundCard = true;
                        break;
                    }
                }
                this.isDone = true;
                AbstractDungeon.player.hand.refreshHandLayout();
            }
        });
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (this.timesUpgraded == 2){
            this.upgradeMagicNumber(1);
            this.timesUpgraded = 3;
            this.name = cardStrings.NAME + "+" + this.timesUpgraded;
            initializeDescription();
            EXHAUST = false;
        } else if (this.timesUpgraded == 1){
            this.baseDamage = 6;
            this.timesUpgraded = 2;
            this.name = cardStrings.NAME + "+" + this.timesUpgraded;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
            EXHAUST = false;
        } else if (this.timesUpgraded == 0){
            this.baseDamage = 4;
            this.upgradeMagicNumber(1);
            this.timesUpgraded = 1;
            this.name = cardStrings.NAME + "+" + this.timesUpgraded;
            EXHAUST = true;
        }
    }

    @Override
    public boolean canUpgrade(){
        if (timesUpgraded<3){
            return true;
        } else return false;
    }
}