package questTheSpire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.function.Predicate;

public class RemoveAllPowersViaPredicateAction extends AbstractGameAction {

    private AbstractGameEffect vfx;
    private String key;
    private float pitchVar;
    private Predicate<AbstractPower> predicate;

    public RemoveAllPowersViaPredicateAction(AbstractCreature target, Predicate<AbstractPower> predicate) {
        this(target, predicate,null, null, 0);
    }

    public RemoveAllPowersViaPredicateAction(AbstractCreature target, Predicate<AbstractPower> predicate, AbstractGameEffect effect) {
        this(target, predicate, effect, null, 0);
    }

    public RemoveAllPowersViaPredicateAction(AbstractCreature target, Predicate<AbstractPower> predicate, String sfxKey, float pitchVariance) {
        this(target, predicate, null, sfxKey, pitchVariance);
    }

    public RemoveAllPowersViaPredicateAction(AbstractCreature target, Predicate<AbstractPower> predicate, AbstractGameEffect effect, String sfxKey, float pitchVariance) {
        this.target = target;
        this.predicate = predicate;
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
            if (predicate.test(p)) {
                this.addToTop(new RemoveSpecificPowerAction(target, target, p));
            }
        }
        this.isDone = true;
    }
}
