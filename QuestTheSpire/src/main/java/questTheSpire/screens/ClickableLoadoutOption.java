package questTheSpire.screens;

import basemod.IUIElement;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;

import static questTheSpire.screens.CharacterLoadout.PERK_IMAGE;

public abstract class ClickableLoadoutOption {
    private String label;
    private float x;
    private float y;
    private Button lb;
    private Button rb;
    private boolean updateNeeded;
    private boolean canUp, canDown;

    public ClickableLoadoutOption (float x, float y) {
        this.x = x;
        this.y = y;
        label = makeLabel();
        lb = new Button(false, 0, 0);
        rb = new Button(true, 0, 0);
        lb.move(x-getAssembledWidth()/2f, y);
        rb.move(x+getAssembledWidth()/2f, y);
        canUp = canUpgrade();
        canDown = canDowngrade();
    }

    public abstract void onClickArrow(boolean increase);

    public abstract String makeLabel();

    public abstract int getUpgradeCost();

    public abstract int getDowngradeRefund();

    public abstract boolean canUpgrade();

    public abstract boolean canDowngrade();

    public abstract int amountPerLevel();

    public void update() {
        lb.update();
        rb.update();
        if (updateNeeded) {
            updateNeeded = false;
            label = makeLabel();
            canUp = canUpgrade();
            canDown = canDowngrade();
            lb.move(x-getAssembledWidth()/2f, y);
            rb.move(x+getAssembledWidth()/2f, y);
        }
    }

    public void render(SpriteBatch sb) {
        lb.render(sb);
        FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, label, x, y, Settings.GOLD_COLOR, Settings.scale);
        rb.render(sb);
    }

    public float getAssembledWidth() {
        return lb.w + rb.w + FontHelper.getWidth(FontHelper.cardTitleFont, label, Settings.scale);
    }

    private class Button implements IUIElement {
        private final Texture arrow;
        private float x;
        private float y;
        private final int w;
        private final int h;
        private final Hitbox hitbox;
        private final boolean rightButton;
        private boolean enabled;

        public Button(boolean rightButton, float x, float y) {
            this.rightButton = rightButton;
            this.arrow = rightButton ? ImageMaster.CF_LEFT_ARROW : ImageMaster.CF_RIGHT_ARROW;
            this.x = x;
            this.y = y;
            this.w = (int)(Settings.scale * (float)this.arrow.getWidth() / 2.0F);
            this.h = (int)(Settings.scale * (float)this.arrow.getHeight() / 2.0F);
            this.hitbox = new Hitbox(x, y, (float)this.w, (float)this.h);
        }

        public void move(float newX, float newY) {
            this.x = (int)(newX - (float)this.w / 2.0F);
            this.y = (int)(newY - (float)this.h / 2.0F);
            this.hitbox.move(newX, newY);
        }

        public void render(SpriteBatch sb) {
            if (enabled) {
                float halfW = (float)this.arrow.getWidth() / 2.0F;
                float halfH = (float)this.arrow.getHeight() / 2.0F;
                if (this.hitbox.hovered) {
                    sb.setColor(Color.WHITE);
                    if (rightButton) {
                        sb.draw(PERK_IMAGE, x + 2*this.w - PERK_IMAGE.getRegionWidth()/2F, y + halfH/2f - PERK_IMAGE.getRegionHeight()/2F, PERK_IMAGE.getRegionWidth()/2F, PERK_IMAGE.getRegionHeight()/2F, PERK_IMAGE.getRegionWidth(), PERK_IMAGE.getRegionHeight(), Settings.scale/2f, Settings.scale/2f, 0.0F);
                        FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, String.valueOf(getUpgradeCost()), x+3*this.w, y, Settings.GOLD_COLOR, Settings.scale);
                    } else {
                        sb.draw(PERK_IMAGE, x - this.w - PERK_IMAGE.getRegionWidth()/2F, y + halfH/2f - PERK_IMAGE.getRegionHeight()/2F, PERK_IMAGE.getRegionWidth()/2F, PERK_IMAGE.getRegionHeight()/2F, PERK_IMAGE.getRegionWidth(), PERK_IMAGE.getRegionHeight(), Settings.scale/2f, Settings.scale/2f, 0.0F);
                        FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, String.valueOf(getDowngradeRefund()), x-2*this.w, y, Settings.GOLD_COLOR, Settings.scale);
                    }
                } else {
                    sb.setColor(Color.LIGHT_GRAY);
                }
                sb.draw(this.arrow, this.x - halfW/2f, this.y - halfH/2f, halfW, halfH, (float)this.arrow.getWidth(), (float)this.arrow.getHeight(), Settings.scale, Settings.scale, 0.0F, 0, 0, this.arrow.getWidth(), this.arrow.getHeight(), true, false);
                this.hitbox.render(sb);
            }
        }

        public void update() {
            enabled = rightButton ? canUp : canDown;
            this.hitbox.update();
            if (this.hitbox.hovered && InputHelper.justClickedLeft && enabled) {
                CardCrawlGame.sound.play("UI_CLICK_1");
                onClickArrow(rightButton);
                updateNeeded = true;
            }
        }

        public int renderLayer() {
            return 0;
        }

        public int updateOrder() {
            return 0;
        }
    }
}
