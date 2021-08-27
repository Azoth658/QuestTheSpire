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

public abstract class ClickableLoadoutOption {
    private String name;
    private String label;
    private float x;
    private float y;
    private Button lb;
    private Button rb;
    private static float LABEL_Y_OFFSET = -3f;
    private boolean updateNeeded;

    public ClickableLoadoutOption (String name, float x, float y) {
        this.name = name;
        this.x = x;
        this.y = y;
        label = makeLabel(name);
        lb = new Button(false, 0, 0);
        rb = new Button(true, 0, 0);
        lb.move(x, y);
        rb.move(x+FontHelper.getWidth(FontHelper.cardTitleFont, label, Settings.scale)+2*lb.w, y);
    }

    public abstract void onClickArrow(boolean increase);

    public abstract String makeLabel(String name);

    public void update() {
        lb.update();
        rb.update();
        if (updateNeeded) {
            updateNeeded = false;
            label = makeLabel(name);
            lb.move(x, y);
            rb.move(x+FontHelper.getWidth(FontHelper.cardTitleFont, label, Settings.scale)+2*lb.w, y);
        }
    }

    public void render(SpriteBatch sb) {
        lb.render(sb);
        FontHelper.renderSmartText(sb, FontHelper.cardTitleFont, label, x + lb.w/2f, y + LABEL_Y_OFFSET, 99999.0F, 30.0F * Settings.scale, Settings.GOLD_COLOR);
        rb.render(sb);
    }

    public float getAssembledWidth() {
        return 3*lb.w/2f + 3*rb.w/2f + FontHelper.getWidth(FontHelper.cardTitleFont, label, Settings.scale);
    }

    private class Button implements IUIElement {
        private final Texture arrow;
        private float x;
        private float y;
        private final int w;
        private final int h;
        private final Hitbox hitbox;
        private final boolean rightButton;

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
            this.hitbox.move(newX - (float)this.w / 2.0F, newY - (float)this.h / 2.0F);
        }

        public void render(SpriteBatch sb) {
            if (this.hitbox.hovered) {
                sb.setColor(Color.WHITE);
            } else {
                sb.setColor(Color.LIGHT_GRAY);
            }

            float halfW = (float)this.arrow.getWidth() / 2.0F;
            float halfH = (float)this.arrow.getHeight() / 2.0F;
            sb.draw(this.arrow, this.x - halfW, this.y - halfH, halfW, halfH, (float)this.arrow.getWidth(), (float)this.arrow.getHeight(), Settings.scale, Settings.scale, 0.0F, 0, 0, this.arrow.getWidth(), this.arrow.getHeight(), true, false);
            this.hitbox.render(sb);
        }

        public void update() {
            this.hitbox.update();
            if (this.hitbox.hovered && InputHelper.justClickedLeft) {
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
