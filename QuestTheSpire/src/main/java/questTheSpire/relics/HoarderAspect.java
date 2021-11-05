package questTheSpire.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import questTheSpire.QuestTheSpireMod;
import questTheSpire.util.CharacterSaveFile;
import questTheSpire.util.TextureLoader;

import java.util.ArrayList;

import static questTheSpire.QuestTheSpireMod.*;

public class HoarderAspect extends CustomRelic {

    public static final String ID = QuestTheSpireMod.makeID("HoarderAspect");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("perkPoints.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("perkPoints.png"));

    public HoarderAspect() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }
    private boolean used = false;

    private void removeRetainTip() {
        ArrayList<String> keywords = new ArrayList<>();
        for (String s : GameDictionary.RETAIN.NAMES) {
            keywords.add(s.toLowerCase());
        }
        this.tips.removeIf(s -> keywords.contains(s.header.toLowerCase()));
    }

    public void onEquip(){
        used = false;
    }

    public void onTrigger(){
        if (!used) {
            flash();
            AbstractDungeon.player.gainGold(activeCharacterFile.getData(CharacterSaveFile.SaveDataEnum.HOARDER_ASPECT));
            used = true;
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
