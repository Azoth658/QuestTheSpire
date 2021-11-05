package questTheSpire.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import questTheSpire.QuestTheSpireMod;
import questTheSpire.util.TextureLoader;

import static questTheSpire.QuestTheSpireMod.makeRelicOutlinePath;
import static questTheSpire.QuestTheSpireMod.makeRelicPath;

public class GreedAspect extends CustomRelic {

    public static final String ID = QuestTheSpireMod.makeID("GreedAspect");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("EmptyBG.png"));

    public GreedAspect() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    public void onTrigger(){
        flash();
        AbstractDungeon.player.gainGold(100);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
