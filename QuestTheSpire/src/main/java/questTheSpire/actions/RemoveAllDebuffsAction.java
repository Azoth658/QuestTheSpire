package questTheSpire.actions;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class RemoveAllDebuffsAction extends AbstractGameAction {

    private AbstractGameEffect vfx;
    private String key;
    private float pitchVar;

    public RemoveAllDebuffsAction(AbstractCreature target) {
        this(target, null, null, 0);
    }

    public RemoveAllDebuffsAction(AbstractCreature target, AbstractGameEffect effect) {
        this(target, effect, null, 0);
    }

    public RemoveAllDebuffsAction(AbstractCreature target, String sfxKey, float pitchVariance) {
        this(target, null, sfxKey, pitchVariance);
    }

    public RemoveAllDebuffsAction(AbstractCreature target, AbstractGameEffect effect, String sfxKey, float pitchVariance) {
        this.target = target;
        vfx = effect;
        key = sfxKey;
        pitchVar = pitchVariance;
    }

    @Override
    public void update() {
        if (vfx != null) {
            AbstractDungeon.effectList.add(this.vfx);
        }
        if (key != null) {
            CardCrawlGame.sound.play(this.key, this.pitchVar);
        }
        for (AbstractPower p : target.powers) {
            if (p.type == AbstractPower.PowerType.DEBUFF && !(p instanceof InvisiblePower)) {
                this.addToTop(new RemoveSpecificPowerAction(target, target, p));
            }
        }
        this.isDone = true;
    }
}
