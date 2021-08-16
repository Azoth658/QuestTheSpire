package questTheSpire.Screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.buttons.UnlockConfirmButton;
import com.megacrit.cardcrawl.unlock.AbstractUnlock;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.ConeEffect;
import com.megacrit.cardcrawl.vfx.RoomShineEffect;
import com.megacrit.cardcrawl.vfx.RoomShineEffect2;

import java.util.ArrayList;
import java.util.Iterator;

public class LevelUpScreen {
    public ArrayList<AbstractUnlock> unlockBundle;
    private ArrayList<ConeEffect> cones = new ArrayList();
    private static final int CONE_AMT = 30;
    private float shinyTimer = 0.0F;
    private static final float SHINY_INTERVAL = 0.2F;
    public UnlockConfirmButton button = new UnlockConfirmButton();
    public long id;
    private static final UIStrings uiStrings;
    public static final String[] TEXT;

    public LevelUpScreen() {
    }

    public void open(boolean isVictory) {
        AbstractDungeon.screen = AbstractDungeon.CurrentScreen.NEOW_UNLOCK;
        this.button.show();
        this.id = CardCrawlGame.sound.play("UNLOCK_SCREEN");
        this.cones.clear();

        int i;
        for(i = 0; i < 30; ++i) {
            this.cones.add(new ConeEffect());
        }

        switch(((AbstractUnlock)this.unlockBundle.get(0)).type) {
            case CARD:
                for(i = 0; i < this.unlockBundle.size(); ++i) {
                    UnlockTracker.unlockCard(((AbstractUnlock)this.unlockBundle.get(i)).card.cardID);
                    AbstractDungeon.dynamicBanner.appearInstantly(TEXT[0]);
                    ((AbstractUnlock)this.unlockBundle.get(i)).card.targetDrawScale = 1.0F;
                    ((AbstractUnlock)this.unlockBundle.get(i)).card.drawScale = 0.01F;
                    ((AbstractUnlock)this.unlockBundle.get(i)).card.current_x = (float) Settings.WIDTH * 0.25F * (float)(i + 1);
                    ((AbstractUnlock)this.unlockBundle.get(i)).card.current_y = (float)Settings.HEIGHT / 2.0F;
                    ((AbstractUnlock)this.unlockBundle.get(i)).card.target_x = (float)Settings.WIDTH * 0.25F * (float)(i + 1);
                    ((AbstractUnlock)this.unlockBundle.get(i)).card.target_y = (float)Settings.HEIGHT / 2.0F - 30.0F * Settings.scale;
                }

                return;
            case RELIC:
                for(i = 0; i < this.unlockBundle.size(); ++i) {
                    UnlockTracker.hardUnlockOverride(((AbstractUnlock)this.unlockBundle.get(i)).relic.relicId);
                    UnlockTracker.markRelicAsSeen(((AbstractUnlock)this.unlockBundle.get(i)).relic.relicId);
                    ((AbstractUnlock)this.unlockBundle.get(i)).relic.loadLargeImg();
                    AbstractDungeon.dynamicBanner.appearInstantly(TEXT[1]);
                    ((AbstractUnlock)this.unlockBundle.get(i)).relic.currentX = (float)Settings.WIDTH * 0.25F * (float)(i + 1);
                    ((AbstractUnlock)this.unlockBundle.get(i)).relic.currentY = (float)Settings.HEIGHT / 2.0F;
                    ((AbstractUnlock)this.unlockBundle.get(i)).relic.hb.move(((AbstractUnlock)this.unlockBundle.get(i)).relic.currentX, ((AbstractUnlock)this.unlockBundle.get(i)).relic.currentY);
                }

                return;
            case CHARACTER:
                ((AbstractUnlock)this.unlockBundle.get(0)).onUnlockScreenOpen();
                AbstractDungeon.dynamicBanner.appearInstantly(TEXT[2]);
            case MISC:
        }

    }

    public void reOpen() {
        AbstractDungeon.screen = AbstractDungeon.CurrentScreen.NEOW_UNLOCK;
        this.button.show();
        this.id = CardCrawlGame.sound.play("UNLOCK_SCREEN");
        this.cones.clear();

        int i;
        for(i = 0; i < 30; ++i) {
            this.cones.add(new ConeEffect());
        }


        //TODO Change this section from unlocks to level up perk point increase
        switch(((AbstractUnlock)this.unlockBundle.get(0)).type) {
            case CARD:
                for(i = 0; i < this.unlockBundle.size(); ++i) {
                    UnlockTracker.unlockCard(((AbstractUnlock)this.unlockBundle.get(i)).card.cardID);
                    AbstractDungeon.dynamicBanner.appearInstantly(TEXT[0]);
                    ((AbstractUnlock)this.unlockBundle.get(i)).card.targetDrawScale = 1.0F;
                    ((AbstractUnlock)this.unlockBundle.get(i)).card.drawScale = 0.01F;
                    ((AbstractUnlock)this.unlockBundle.get(i)).card.current_x = (float)Settings.WIDTH * 0.25F * (float)(i + 1);
                    ((AbstractUnlock)this.unlockBundle.get(i)).card.current_y = (float)Settings.HEIGHT / 2.0F;
                    ((AbstractUnlock)this.unlockBundle.get(i)).card.target_x = (float)Settings.WIDTH * 0.25F * (float)(i + 1);
                    ((AbstractUnlock)this.unlockBundle.get(i)).card.target_y = (float)Settings.HEIGHT / 2.0F - 30.0F * Settings.scale;
                }

                return;
            case RELIC:
                for(i = 0; i < this.unlockBundle.size(); ++i) {
                    AbstractDungeon.dynamicBanner.appearInstantly(TEXT[1]);
                    ((AbstractUnlock)this.unlockBundle.get(i)).relic.currentX = (float)Settings.WIDTH * 0.25F * (float)(i + 1);
                    ((AbstractUnlock)this.unlockBundle.get(i)).relic.currentY = (float)Settings.HEIGHT / 2.0F;
                    ((AbstractUnlock)this.unlockBundle.get(i)).relic.hb.move(((AbstractUnlock)this.unlockBundle.get(i)).relic.currentX, ((AbstractUnlock)this.unlockBundle.get(i)).relic.currentY);
                }

                return;
            case CHARACTER:
                ((AbstractUnlock)this.unlockBundle.get(0)).onUnlockScreenOpen();
                AbstractDungeon.dynamicBanner.appearInstantly(TEXT[2]);
            case MISC:
        }
        //TODO Change above from unlocks

    }

    public void update() {
        this.shinyTimer -= Gdx.graphics.getDeltaTime();
        if (this.shinyTimer < 0.0F) {
            this.shinyTimer = 0.2F;
            AbstractDungeon.topLevelEffects.add(new RoomShineEffect());
            AbstractDungeon.topLevelEffects.add(new RoomShineEffect());
            AbstractDungeon.topLevelEffects.add(new RoomShineEffect2());
        }

        //TODO Change this from unlocks to level up effects
        int i;
        label28:
        switch(((AbstractUnlock)this.unlockBundle.get(0)).type) {
            case CARD:
                this.updateConeEffect();
                i = 0;

                while(true) {
                    if (i >= this.unlockBundle.size()) {
                        break label28;
                    }

                    ((AbstractUnlock)this.unlockBundle.get(i)).card.update();
                    ((AbstractUnlock)this.unlockBundle.get(i)).card.updateHoverLogic();
                    ((AbstractUnlock)this.unlockBundle.get(i)).card.targetDrawScale = 1.0F;
                    ++i;
                }
            case RELIC:
                this.updateConeEffect();
                i = 0;

                while(true) {
                    if (i >= this.unlockBundle.size()) {
                        break label28;
                    }

                    ((AbstractUnlock)this.unlockBundle.get(i)).relic.update();
                    ++i;
                }
            case CHARACTER:
                this.updateConeEffect();
                ((AbstractUnlock)this.unlockBundle.get(0)).player.update();
        }

        this.button.update();
    }

    private void updateConeEffect() {
        Iterator e = this.cones.iterator();

        while(e.hasNext()) {
            ConeEffect d = (ConeEffect)e.next();
            d.update();
            if (d.isDone) {
                e.remove();
            }
        }

        if (this.cones.size() < 30) {
            this.cones.add(new ConeEffect());
        }

    }

    public void render(SpriteBatch sb) {
        sb.setColor(new Color(0.05F, 0.15F, 0.18F, 1.0F));
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, (float)Settings.WIDTH, (float)Settings.HEIGHT);
        sb.setBlendFunction(770, 1);
        Iterator var2 = this.cones.iterator();

        while(var2.hasNext()) {
            ConeEffect e = (ConeEffect)var2.next();
            e.render(sb);
        }


        //TODO Change from unlock bundles to level up rewards
        sb.setBlendFunction(770, 771);
        int i;
        switch(((AbstractUnlock)this.unlockBundle.get(0)).type) {
            case CARD:
                for(i = 0; i < this.unlockBundle.size(); ++i) {
                    ((AbstractUnlock)this.unlockBundle.get(i)).card.renderHoverShadow(sb);
                    ((AbstractUnlock)this.unlockBundle.get(i)).card.render(sb);
                    ((AbstractUnlock)this.unlockBundle.get(i)).card.renderCardTip(sb);
                }

                sb.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F));
                sb.draw(ImageMaster.UNLOCK_TEXT_BG, (float)Settings.WIDTH / 2.0F - 500.0F, (float)Settings.HEIGHT / 2.0F - 330.0F * Settings.scale - 130.0F, 500.0F, 130.0F, 1000.0F, 260.0F, Settings.scale * 1.2F, Settings.scale * 0.8F, 0.0F, 0, 0, 1000, 260, false, false);
                FontHelper.renderFontCentered(sb, FontHelper.panelNameFont, TEXT[3], (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F - 330.0F * Settings.scale, Settings.CREAM_COLOR);
                break;
            case RELIC:
                for(i = 0; i < this.unlockBundle.size(); ++i) {
                    if (RelicLibrary.redList.contains(((AbstractUnlock)this.unlockBundle.get(i)).relic)) {
                        ((AbstractUnlock)this.unlockBundle.get(i)).relic.render(sb, false, Settings.RED_RELIC_COLOR);
                    } else if (RelicLibrary.greenList.contains(((AbstractUnlock)this.unlockBundle.get(i)).relic)) {
                        ((AbstractUnlock)this.unlockBundle.get(i)).relic.render(sb, false, Settings.GREEN_RELIC_COLOR);
                    } else if (RelicLibrary.blueList.contains(((AbstractUnlock)this.unlockBundle.get(i)).relic)) {
                        ((AbstractUnlock)this.unlockBundle.get(i)).relic.render(sb, false, Settings.BLUE_RELIC_COLOR);
                    } else if (RelicLibrary.whiteList.contains(((AbstractUnlock)this.unlockBundle.get(i)).relic)) {
                        ((AbstractUnlock)this.unlockBundle.get(i)).relic.render(sb, false, Settings.PURPLE_RELIC_COLOR);
                    } else {
                        ((AbstractUnlock)this.unlockBundle.get(i)).relic.render(sb, false, Settings.TWO_THIRDS_TRANSPARENT_BLACK_COLOR);
                    }

                    sb.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F));
                    sb.draw(ImageMaster.UNLOCK_TEXT_BG, (float)Settings.WIDTH / 2.0F - 500.0F, (float)Settings.HEIGHT / 2.0F - 330.0F * Settings.scale - 130.0F, 500.0F, 130.0F, 1000.0F, 260.0F, Settings.scale * 1.2F, Settings.scale * 0.8F, 0.0F, 0, 0, 1000, 260, false, false);
                    FontHelper.renderFontCentered(sb, FontHelper.losePowerFont, ((AbstractUnlock)this.unlockBundle.get(i)).relic.name, (float)Settings.WIDTH * 0.25F * (float)(i + 1), (float)Settings.HEIGHT / 2.0F - 150.0F * Settings.scale, Settings.GOLD_COLOR, 1.2F);
                }

                FontHelper.renderFontCentered(sb, FontHelper.panelNameFont, TEXT[3], (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F - 330.0F * Settings.scale, Settings.CREAM_COLOR);
                break;
            case CHARACTER:
                ((AbstractUnlock)this.unlockBundle.get(0)).render(sb);
                ((AbstractUnlock)this.unlockBundle.get(0)).player.renderPlayerImage(sb);
        }

        this.button.render(sb);
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("UnlockScreen");
        TEXT = uiStrings.TEXT;
    }
}
