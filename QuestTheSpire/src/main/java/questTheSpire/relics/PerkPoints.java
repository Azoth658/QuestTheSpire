package questTheSpire.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.powers.watcher.DevotionPower;
import com.megacrit.cardcrawl.powers.watcher.MantraPower;
import questTheSpire.QuestTheSpireMod;
import questTheSpire.powers.RetainBlockPower;
import questTheSpire.util.CharacterSaveFile;
import questTheSpire.util.TextureLoader;

import static questTheSpire.QuestTheSpireMod.*;

public class PerkPoints extends CustomRelic {

    public static final String ID = QuestTheSpireMod.makeID("PerkPoints");

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
        int artifactPerk;
        int armorPerk;
        int thornPerk;
        int retainBlockPerk;

        strPerk = activeCharacterFile.getData(CharacterSaveFile.SaveDataEnum.STRENGTH);
        dexPerk = activeCharacterFile.getData(CharacterSaveFile.SaveDataEnum.DEXTERITY);
        focusPerk = activeCharacterFile.getData(CharacterSaveFile.SaveDataEnum.FOCUS);
        mantraPerk = activeCharacterFile.getData(CharacterSaveFile.SaveDataEnum.MANTRA);
        devotionPerk = activeCharacterFile.getData(CharacterSaveFile.SaveDataEnum.DEVOTION);
        regenPerk = activeCharacterFile.getData(CharacterSaveFile.SaveDataEnum.REGEN);
        artifactPerk = activeCharacterFile.getData(CharacterSaveFile.SaveDataEnum.ARTIFACT);
        armorPerk = activeCharacterFile.getData(CharacterSaveFile.SaveDataEnum.PLATED_ARMOR);
        thornPerk = activeCharacterFile.getData(CharacterSaveFile.SaveDataEnum.THORNS);
        retainBlockPerk = activeCharacterFile.getData(CharacterSaveFile.SaveDataEnum.RETAIN_BLOCK);

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
        if (artifactPerk > 0) {
            this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ArtifactPower(AbstractDungeon.player, artifactPerk), artifactPerk));
        }
        if (armorPerk > 0) {
            this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new PlatedArmorPower(AbstractDungeon.player, armorPerk), armorPerk));
        }
        if (thornPerk > 0) {
            this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ThornsPower(AbstractDungeon.player, thornPerk), thornPerk));
        }
        if (retainBlockPerk > 0) {
            this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new RetainBlockPower(AbstractDungeon.player, retainBlockPerk), retainBlockPerk));
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
