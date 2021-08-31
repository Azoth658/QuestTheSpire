package questTheSpire.relics;

import basemod.abstracts.CustomRelic;
import basemod.interfaces.StartActSubscriber;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import questTheSpire.QuestTheSpire;
import questTheSpire.util.TextureLoader;

import static questTheSpire.QuestTheSpire.makeRelicOutlinePath;
import static questTheSpire.QuestTheSpire.makeRelicPath;

public class GreedAspect extends CustomRelic {

    public static final String ID = QuestTheSpire.makeID("GreedAspect");

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
