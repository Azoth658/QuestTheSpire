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

public class FairyBlessing extends CustomRelic {

    public static final String ID = QuestTheSpire.makeID("FairyBlessing");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("fairyBlessing.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("EmptyBG.png"));

    public FairyBlessing() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    // Flash at the start of Battle.
    @Override
    public void atBattleStart() {
        this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new RegenPower(AbstractDungeon.player, 4), 4));
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
