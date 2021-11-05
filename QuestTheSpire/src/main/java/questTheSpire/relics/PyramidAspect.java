package questTheSpire.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.RetainCardPower;
import questTheSpire.QuestTheSpireMod;
import questTheSpire.util.TextureLoader;

import static questTheSpire.QuestTheSpireMod.makeRelicOutlinePath;
import static questTheSpire.QuestTheSpireMod.makeRelicPath;

public class PyramidAspect extends CustomRelic {

    public static final String ID = QuestTheSpireMod.makeID("PyramidAspect");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("EmptyBG.png"));

    public PyramidAspect() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    public void atBattleStart() {
        this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new RetainCardPower(AbstractDungeon.player, 1), 1));
        }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
