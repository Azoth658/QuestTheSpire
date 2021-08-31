package questTheSpire.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.RegenPower;
import questTheSpire.QuestTheSpire;
import questTheSpire.util.TextureLoader;

import static questTheSpire.QuestTheSpire.makeRelicOutlinePath;
import static questTheSpire.QuestTheSpire.makeRelicPath;

public class DrawAspect extends CustomRelic {

    public static final String ID = QuestTheSpire.makeID("DrawAspect");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("EmptyBG.png"));

    public DrawAspect() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    public void onEquip() {
        ++AbstractDungeon.player.masterHandSize;
    }
    public void onUnequip() {
        --AbstractDungeon.player.masterHandSize;
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
