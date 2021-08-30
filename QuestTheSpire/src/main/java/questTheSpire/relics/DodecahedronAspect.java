package questTheSpire.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.RetainCardPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.deprecated.DEPRECATEDDodecahedron;
import questTheSpire.QuestTheSpire;
import questTheSpire.util.TextureLoader;

import static questTheSpire.QuestTheSpire.makeRelicOutlinePath;
import static questTheSpire.QuestTheSpire.makeRelicPath;

public class DodecahedronAspect extends CustomRelic {

    public static final String ID = QuestTheSpire.makeID("DodecahedronAspect");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("dodecahedron.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("dodecahedron.png"));

    public DodecahedronAspect() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    public void atBattleStart() {
        this.controlPulse();
    }

    public void onVictory() {
        this.stopPulse();
    }

    public void atTurnStart() {
        this.addToBot(new AbstractGameAction() {
            public void update() {
                if (DodecahedronAspect.this.isActive()) {
                    DodecahedronAspect.this.flash();
                    this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, DodecahedronAspect.this));
                    this.addToBot(new GainEnergyAction(1));
                }

                this.isDone = true;
            }
        });
    }

    public int onPlayerHeal(int healAmount) {
        this.controlPulse();
        return super.onPlayerHeal(healAmount);
    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        if (damageAmount > 0) {
            this.stopPulse();
        }

        return super.onAttacked(info, damageAmount);
    }

    private boolean isActive() {
        return AbstractDungeon.player.currentHealth >= AbstractDungeon.player.maxHealth;
    }

    private void controlPulse() {
        if (this.isActive()) {
            this.beginLongPulse();
        } else {
            this.stopPulse();
        }

    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
