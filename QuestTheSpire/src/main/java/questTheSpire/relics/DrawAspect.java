package questTheSpire.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import questTheSpire.QuestTheSpireMod;
import questTheSpire.util.TextureLoader;

import static questTheSpire.QuestTheSpireMod.makeRelicOutlinePath;
import static questTheSpire.QuestTheSpireMod.makeRelicPath;

public class DrawAspect extends CustomRelic {

    public static final String ID = QuestTheSpireMod.makeID("DrawAspect");

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
