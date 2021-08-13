package questTheSpire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class DecreaseMaxHealthAction extends AbstractGameAction {

    private AbstractGameEffect vfx;
    private String key;
    private float pitchVar;

    public DecreaseMaxHealthAction(AbstractCreature target, int amount) {
        this.target = target;
        this.amount = amount;
        this.duration = Settings.ACTION_DUR_XFAST;
    }

    public DecreaseMaxHealthAction(AbstractCreature target, int amount, AbstractGameEffect effect) {
        this(target, amount);
        vfx = effect;
    }

    public DecreaseMaxHealthAction(AbstractCreature target, int amount, AbstractGameEffect effect, String sfxKey, float pitchVariance) {
        this(target, amount, effect);
        key = sfxKey;
        pitchVar = pitchVariance;
    }

    public DecreaseMaxHealthAction(AbstractCreature target, int amount, AttackEffect effect) {
        this(target, amount, effect, false);
    }

    public DecreaseMaxHealthAction(AbstractCreature target, int amount, AttackEffect effect, boolean muteEffect) {
        this(target, amount, new FlashAtkImgEffect(target.hb.cX, target.hb.cY, effect, muteEffect));
    }

    public DecreaseMaxHealthAction(AbstractCreature target, int amount, AttackEffect effect, boolean muteEffect, String sfxKey, float pitchVariance) {
        this(target, amount, new FlashAtkImgEffect(target.hb.cX, target.hb.cY, effect, muteEffect), sfxKey, pitchVariance);
    }

    @Override
    public void update() {
        if (duration == Settings.ACTION_DUR_XFAST) {
            if (vfx != null) {
                AbstractDungeon.effectList.add(this.vfx);
            }
            if (key != null) {
                CardCrawlGame.sound.play(this.key, this.pitchVar);
            }
            target.decreaseMaxHealth(amount);
        }
        tickDuration();
    }
}
