package questTheSpire.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.RegenPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.watcher.DevotionPower;
import com.megacrit.cardcrawl.powers.watcher.MantraPower;
import questTheSpire.QuestTheSpire;
import questTheSpire.util.TextureLoader;

import static questTheSpire.QuestTheSpire.*;

public class PerkPoints extends CustomRelic {

    public static final String ID = QuestTheSpire.makeID("PerkPoints");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("perkPoints.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("perkPoints.png"));

    public PerkPoints() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }



    @Override
    public void atBattleStart() {
        int strPerk;
        int dexPerk;
        int focusPerk;
        int mantraPerk;
        int devotionPerk;
        int regenPerk;

        strPerk = activeCharacterFile.getStr();
        dexPerk = activeCharacterFile.getDex();
        focusPerk = activeCharacterFile.getFoc();
        mantraPerk = activeCharacterFile.getMan();
        devotionPerk = activeCharacterFile.getDev();
        regenPerk = activeCharacterFile.getReg();

        if (strPerk > 0) {
            this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, strPerk), strPerk));
        }
        if (dexPerk > 0) {
            this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, dexPerk), dexPerk));
        }
        if (focusPerk > 0) {
            this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FocusPower(AbstractDungeon.player, focusPerk), focusPerk));
        }
        if (mantraPerk > 0) {
            this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new MantraPower(AbstractDungeon.player, mantraPerk), mantraPerk));
        }
        if (devotionPerk > 0) {
            this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DevotionPower(AbstractDungeon.player, devotionPerk), devotionPerk));
        }
        if (regenPerk > 0) {
            this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new RegenPower(AbstractDungeon.player, regenPerk), regenPerk));
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
