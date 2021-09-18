package questTheSpire.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.powers.watcher.DevotionPower;
import com.megacrit.cardcrawl.powers.watcher.MantraPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import questTheSpire.QuestTheSpire;
import questTheSpire.characters.MasteryCards;
import questTheSpire.util.TextureLoader;

import java.util.Iterator;
import java.util.stream.Collectors;

import static questTheSpire.QuestTheSpire.*;
import static questTheSpire.characters.MasteryCards.Enums.COLOR_MASTERY;

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
        int artifactPerk;
        int armorPerk;
        int thornPerk;

        strPerk = activeCharacterFile.getStr();
        dexPerk = activeCharacterFile.getDex();
        focusPerk = activeCharacterFile.getFoc();
        mantraPerk = activeCharacterFile.getMan();
        devotionPerk = activeCharacterFile.getDev();
        regenPerk = activeCharacterFile.getReg();
        artifactPerk = activeCharacterFile.getArt();
        armorPerk = activeCharacterFile.getArmor();
        thornPerk = activeCharacterFile.getThorns();

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
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
