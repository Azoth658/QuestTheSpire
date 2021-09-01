package questTheSpire.screens;

import basemod.IUIElement;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import questTheSpire.util.CharacterSaveFile;

import java.io.IOException;

import static questTheSpire.screens.CharacterLoadout.PERK_IMAGE;

public class ClickableUIContainers {

    public abstract static class ClickableContainer {
        public boolean updateNeeded;
        public abstract void update();
        public abstract void render(SpriteBatch sb);
    }

    @Deprecated
    public abstract static class OldClickableLoadoutOption extends ClickableContainer {
        CharacterLoadout loadout;
        private String label;
        private float x;
        private float y;
        private Button lb;
        private Button rb;
        private boolean canUp, canDown;

        public OldClickableLoadoutOption(CharacterLoadout loadout, float x, float y) {
            this.x = x;
            this.y = y;
            this.loadout = loadout;
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
                    loadout.setAllButtonsNeedUpdate();
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

    public abstract static class ClickableText extends ClickableContainer {
        private String label;
        private Hitbox hb;
        private float x, y;
        private static final float PADDING = 5f * Settings.scale;

        public ClickableText(String message, float x, float y) {
            this.label = message;
            this.x = x;
            this.y = y;
            hb = new Hitbox(x, y);
            scaleHitbox();
        }

        public abstract void onClick();

        protected void scaleHitbox() {
            hb.resize(FontHelper.getWidth(FontHelper.cardTitleFont, label, Settings.scale)+PADDING*2, FontHelper.getHeight(FontHelper.cardTitleFont, label, Settings.scale)+PADDING*2);
            hb.move(x, y);
        }

        @Override
        public void update() {
            hb.update();
            if (hb.hovered && InputHelper.justClickedLeft) {
                CardCrawlGame.sound.play("UI_CLICK_1");
                onClick();
            }
        }

        @Override
        public void render(SpriteBatch sb) {
            FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, label, x, y, hb.hovered ? Settings.GREEN_TEXT_COLOR : Settings.GOLD_COLOR, Settings.scale);
            hb.render(sb);
        }
    }

    public abstract static class PlusMinusLoadoutOption extends ClickableContainer {
        CharacterLoadout loadout;
        private String label;
        private float x;
        private float y;
        private boolean canUp, canDown, hasUp, hasDown;
        private int amount;
        private int upgrades;
        private int points;
        private final Button lb;
        private final Button rb;
        private final SpireConfig config;
        private final String varPath;
        private final String baseText;

        public PlusMinusLoadoutOption(CharacterLoadout loadout, CharacterSaveFile file, String variablePath, String labelText, float x, float y) {
            this.x = x;
            this.y = y;
            this.loadout = loadout;
            this.config = file.getWrappedConfig();
            this.varPath = variablePath;
            this.baseText = labelText;
            lb = new Button(false, 0, 0);
            rb = new Button(true, 0, 0);
            readValues();
        }

        protected void readValues() {
            amount = config.getInt(varPath);
            upgrades = amount/amountPerUpgrade();
            points = config.getInt(CharacterSaveFile.CURRENT_PERK_POINTS);
            makeLabel();
            canUp = canUpgrade();
            canDown = canDowngrade();
            hasUp = hasUpgrade();
            hasDown = hasDowngrade();
            lb.move(x-getAssembledWidth()/2f, y);
            rb.move(x+getAssembledWidth()/2f, y);
        }

        protected void saveValues() {
            config.setInt(varPath, amount);
            config.setInt(CharacterSaveFile.CURRENT_PERK_POINTS, points);
            try {
                config.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        protected void makeLabel() {
            label = baseText+": "+amount;
        }

        public int getUpgradeCost(int currentUpgrades) {
            return 1;
        }

        public int getDowngradeRefund(int currentUpgrades) {
            return 1;
        }

        public int amountPerUpgrade() {
            return 1;
        }

        public int maxUpgrades() {
            return -1;
        }

        public int minUpgrades() {
            return 0;
        }

        protected boolean hasUpgrade() {
            return (maxUpgrades() == -1 || upgrades < maxUpgrades());
        }

        protected boolean hasDowngrade() {
            return upgrades > minUpgrades();
        }

        protected boolean canUpgrade() {
            return points >= getUpgradeCost(upgrades) && hasUpgrade();
        }

        protected boolean canDowngrade() {
            return hasDowngrade();
        }

        protected void onClickArrow(boolean upgrade) {
            if (upgrade) {
                upgrade();
            } else {
                downgrade();
            }
        }

        protected void upgrade() {
            points -= getUpgradeCost(upgrades);
            amount += amountPerUpgrade();
            upgrades++;
            saveValues();
        }

        protected void downgrade() {
            points += getDowngradeRefund(upgrades);
            amount -= amountPerUpgrade();
            upgrades--;
            saveValues();
        }

        public void update() {
            lb.update();
            rb.update();
            if (updateNeeded) {
                updateNeeded = false;
                readValues();
            }
        }

        public void render(SpriteBatch sb) {
            lb.render(sb);
            FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, label, x, y, Settings.GOLD_COLOR, Settings.scale);
            rb.render(sb);
        }

        private float getAssembledWidth() {
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
            private boolean clickable;

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
                    if (clickable) {
                        if (this.hitbox.hovered) {
                            sb.setColor(Color.WHITE);
                        } else {
                            sb.setColor(Color.LIGHT_GRAY);
                        }
                    } else {
                        sb.setColor(Color.GRAY);
                    }
                    if (this.hitbox.hovered) {
                        if (rightButton) {
                            sb.draw(PERK_IMAGE, x + 2*this.w - PERK_IMAGE.getRegionWidth()/2F, y + halfH/2f - PERK_IMAGE.getRegionHeight()/2F, PERK_IMAGE.getRegionWidth()/2F, PERK_IMAGE.getRegionHeight()/2F, PERK_IMAGE.getRegionWidth(), PERK_IMAGE.getRegionHeight(), Settings.scale/2f, Settings.scale/2f, 0.0F);
                            FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, String.valueOf(getUpgradeCost(amount)), x+3*this.w, y, Settings.GOLD_COLOR, Settings.scale);
                        } else {
                            sb.draw(PERK_IMAGE, x - this.w - PERK_IMAGE.getRegionWidth()/2F, y + halfH/2f - PERK_IMAGE.getRegionHeight()/2F, PERK_IMAGE.getRegionWidth()/2F, PERK_IMAGE.getRegionHeight()/2F, PERK_IMAGE.getRegionWidth(), PERK_IMAGE.getRegionHeight(), Settings.scale/2f, Settings.scale/2f, 0.0F);
                            FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, String.valueOf(getDowngradeRefund(amount)), x-2*this.w, y, Settings.GOLD_COLOR, Settings.scale);
                        }
                    }
                    sb.draw(this.arrow, this.x - halfW/2f, this.y - halfH/2f, halfW, halfH, (float)this.arrow.getWidth(), (float)this.arrow.getHeight(), Settings.scale, Settings.scale, 0.0F, 0, 0, this.arrow.getWidth(), this.arrow.getHeight(), true, false);
                    this.hitbox.render(sb);
                }
            }

            public void update() {
                enabled = rightButton ? hasUp : hasDown;
                clickable = rightButton ? canUp : canDown;
                this.hitbox.update();
                if (this.hitbox.hovered && InputHelper.justClickedLeft && clickable) {
                    CardCrawlGame.sound.play("UI_CLICK_1");
                    onClickArrow(rightButton);
                    loadout.setAllButtonsNeedUpdate();
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
}
