package questTheSpire.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.Calipers;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import questTheSpire.QuestTheSpireMod;
import questTheSpire.util.TextureLoader;

import static questTheSpire.QuestTheSpireMod.makePowerPath;

//Gain 1 dex for the turn for each card played.

public class RetainBlockPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = QuestTheSpireMod.makeID("RetainBlockPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("RetainBlock84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("RetainBlock32.png"));

    public RetainBlockPower(final AbstractCreature owner, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        int turnblock = this.owner.currentBlock;
        if (this.owner.currentBlock > 0 && !this.owner.hasPower("Blur") && !this.owner.hasPower("Barricade")) {
            this.flash();
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.owner.hb.cX, this.owner.hb.cY, AbstractGameAction.AttackEffect.SHIELD));
            if (this.owner instanceof AbstractPlayer && ((AbstractPlayer)this.owner).hasRelic("Calipers") && this.owner.currentBlock > Calipers.BLOCK_LOSS) {
                    if ((turnblock - Calipers.BLOCK_LOSS) < this.amount) {
                        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this.owner, this.owner, this.amount - (turnblock-Calipers.BLOCK_LOSS)));
                    }
            } else {
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this.owner, this.owner, Math.min(this.amount, this.owner.currentBlock)));
            }
        }
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        } else if (amount > 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new RetainBlockPower(owner, amount);
    }
}
