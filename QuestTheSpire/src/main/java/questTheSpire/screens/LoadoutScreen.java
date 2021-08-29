package questTheSpire.screens;

import basemod.BaseMod;
import basemod.IUIElement;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import com.megacrit.cardcrawl.screens.mainMenu.MenuCancelButton;
import questTheSpire.QuestTheSpire;
import questTheSpire.patches.MainMenuPatches;

import java.util.ArrayList;
import java.util.Comparator;

public class LoadoutScreen {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(QuestTheSpire.makeID("LoadoutScreen"));
    public static final String[] TEXT = uiStrings.TEXT;
    private int optionsPerIndex = 4;
    private int selectIndex = 0;
    private int maxSelectIndex;
    private int optionsIndex;
    private final LeftOptionsButton leftArrow;
    private final RightOptionsButton rightArrow;
    private final ArrayList<CharacterLoadout> allOptions;
    public MenuCancelButton cancelButton;
    public ArrayList<CharacterLoadout> options;
    private boolean anySelected;
    public Texture bgCharImg;
    private Color bgCharColor;
    private static final float BG_Y_OFFSET_TARGET = 0.0F;
    private float bg_y_offset;

    public LoadoutScreen() {
        this.leftArrow = new LeftOptionsButton("images/ui/popupArrow.png", (int)(425.0F * Settings.scale), (int)((float)(Settings.isFourByThree ? 244 : 180) * Settings.scale));
        this.rightArrow = new RightOptionsButton("images/ui/popupArrow.png", (int)(1425.0F * Settings.scale), (int)((float)(Settings.isFourByThree ? 244 : 180) * Settings.scale));
        this.updateOptionsIndex();
        this.allOptions = new ArrayList<>();
        this.cancelButton = new MenuCancelButton();
        this.options = new ArrayList<>();
        this.anySelected = false;
        this.bgCharImg = null;
        this.bgCharColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        this.bg_y_offset = 0.0F;
    }

    public void initialize() {
        this.options.add(new CharacterLoadout(CharacterSelectScreen.TEXT[2], CardCrawlGame.characterManager.recreateCharacter(AbstractPlayer.PlayerClass.IRONCLAD), ImageMaster.CHAR_SELECT_IRONCLAD, ImageMaster.CHAR_SELECT_BG_IRONCLAD));
        this.options.add(new CharacterLoadout(CharacterSelectScreen.TEXT[3], CardCrawlGame.characterManager.recreateCharacter(AbstractPlayer.PlayerClass.THE_SILENT), ImageMaster.CHAR_SELECT_SILENT, ImageMaster.CHAR_SELECT_BG_SILENT));
        this.options.add(new CharacterLoadout(CharacterSelectScreen.TEXT[4], CardCrawlGame.characterManager.recreateCharacter(AbstractPlayer.PlayerClass.DEFECT), ImageMaster.CHAR_SELECT_DEFECT, ImageMaster.CHAR_SELECT_BG_DEFECT));
        this.options.add(new CharacterLoadout(CharacterSelectScreen.TEXT[14], CardCrawlGame.characterManager.recreateCharacter(AbstractPlayer.PlayerClass.WATCHER), ImageMaster.CHAR_SELECT_WATCHER, ImageMaster.CHAR_SELECT_BG_WATCHER));
        FontHelper.cardTitleFont.getData().setScale(1.0F);
        this.allOptions.clear();
        this.options.addAll(makeCustomCharacterOptions());
        this.allOptions.addAll(options);

        if (this.allOptions.size() == this.optionsPerIndex + 1) {
            ++this.optionsPerIndex;
        }

        this.selectIndex = 0;
        this.updateOptionsIndex();
        this.maxSelectIndex = (int)Math.ceil((float)this.allOptions.size() / (float)this.optionsPerIndex) - 1;
        this.options = new ArrayList<>(this.allOptions.subList(0, Math.min(this.optionsPerIndex, this.allOptions.size())));
        this.positionButtons();
    }

    private ArrayList<CharacterLoadout> makeCustomCharacterOptions() {
        ArrayList<CharacterLoadout> options = new ArrayList<>();

        for (AbstractPlayer character : BaseMod.getModdedCharacters()) {
            CharacterLoadout option = new CharacterLoadout(character.getLocalizedCharacterName(), CardCrawlGame.characterManager.recreateCharacter(character.chosenClass), ImageMaster.loadImage(BaseMod.playerSelectButtonMap.get(character.chosenClass)), ImageMaster.loadImage(BaseMod.playerPortraitMap.get(character.chosenClass)));
            options.add(option);
        }

        options.sort(Comparator.comparing((o) -> o.name));
        return options;
    }

    public void open() {
        this.cancelButton.show(CharacterSelectScreen.TEXT[5]);
        CardCrawlGame.mainMenuScreen.screen = MainMenuPatches.Enums.LOADOUT_VIEW; //This is how we tell it what screen is open
        CardCrawlGame.mainMenuScreen.darken();
    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.bgCharColor);
        if (this.bgCharImg != null) {
            if (Settings.isSixteenByTen) {
                sb.draw(this.bgCharImg, (float)Settings.WIDTH / 2.0F - 960.0F, (float)Settings.HEIGHT / 2.0F - 600.0F, 960.0F, 600.0F, 1920.0F, 1200.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 1920, 1200, false, false);
            } else if (Settings.isFourByThree) {
                sb.draw(this.bgCharImg, (float)Settings.WIDTH / 2.0F - 960.0F, (float)Settings.HEIGHT / 2.0F - 600.0F + this.bg_y_offset, 960.0F, 600.0F, 1920.0F, 1200.0F, Settings.yScale, Settings.yScale, 0.0F, 0, 0, 1920, 1200, false, false);
            } else if (Settings.isLetterbox) {
                sb.draw(this.bgCharImg, (float)Settings.WIDTH / 2.0F - 960.0F, (float)Settings.HEIGHT / 2.0F - 600.0F + this.bg_y_offset, 960.0F, 600.0F, 1920.0F, 1200.0F, Settings.xScale, Settings.xScale, 0.0F, 0, 0, 1920, 1200, false, false);
            } else {
                sb.draw(this.bgCharImg, (float)Settings.WIDTH / 2.0F - 960.0F, (float)Settings.HEIGHT / 2.0F - 600.0F + this.bg_y_offset, 960.0F, 600.0F, 1920.0F, 1200.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 1920, 1200, false, false);
            }
        }

        this.cancelButton.render(sb);
        boolean anythingSelected = false;
        for (CharacterLoadout o : options) {
            o.render(sb);
            if (o.selected) {
                anythingSelected = true;
            }
        }

        if (!anythingSelected) {
            FontHelper.renderFontCentered(sb, FontHelper.losePowerFont, CharacterSelectScreen.TEXT[0], (float)Settings.WIDTH / 2.0F, 340.0F * Settings.yScale, Settings.CREAM_COLOR, 1.2F);
        }
        if (this.selectIndex < this.maxSelectIndex) {
            this.rightArrow.render(sb);
        }

        if (this.selectIndex != 0) {
            this.leftArrow.render(sb);
        }

    }

    public void update() {
        this.anySelected = false;

        for (CharacterLoadout o : this.options) {
            o.update();
            if (o.selected) {
                this.anySelected = true;
            }
        }

        this.updateButtons();

        if (this.anySelected) {
            this.bgCharColor.a = MathHelper.fadeLerpSnap(this.bgCharColor.a, 0.3F);
            this.bg_y_offset = MathHelper.fadeLerpSnap(this.bg_y_offset, -0.0F);
        } else {
            this.bgCharColor.a = MathHelper.fadeLerpSnap(this.bgCharColor.a, 0.0F);
        }

        CardCrawlGame.mainMenuScreen.superDarken = this.anySelected;
        if (this.selectIndex < this.maxSelectIndex) {
            this.rightArrow.update();
        }

        if (this.selectIndex != 0) {
            this.leftArrow.update();
        }

    }

    public void updateButtons() {
        this.cancelButton.update();
        if (this.cancelButton.hb.clicked || InputHelper.pressedEscape) {
            CardCrawlGame.mainMenuScreen.superDarken = false;
            InputHelper.pressedEscape = false;
            this.cancelButton.hb.clicked = false;
            this.cancelButton.hide();
            for (CharacterLoadout o : this.options) {
                o.selected = false;
            }
            this.bgCharColor.a = 0.0F;
            this.anySelected = false;
            CardCrawlGame.mainMenuScreen.screen = MainMenuScreen.CurScreen.MAIN_MENU;
            CardCrawlGame.mainMenuScreen.lighten();
        }
    }

    private void setCurrentOptions(boolean rightArrowClicked) {
        if (rightArrowClicked && this.selectIndex < this.maxSelectIndex) {
            ++this.selectIndex;
        } else if (!rightArrowClicked && this.selectIndex > 0) {
            --this.selectIndex;
        }

        this.updateOptionsIndex();
        int endIndex = this.optionsIndex + this.optionsPerIndex;
        this.options = new ArrayList<>(this.allOptions.subList(this.optionsIndex, Math.min(this.allOptions.size(), endIndex)));
        this.options.forEach((o) -> o.selected = false);
        this.positionButtons();
    }

    private void updateOptionsIndex() {
        this.optionsIndex = this.optionsPerIndex * this.selectIndex;
    }

    private void positionButtons() {
        int count = this.options.size();
        float offsetX = (float)Settings.WIDTH / 2.0F - (float)this.optionsPerIndex / 2.0F * 220.0F * Settings.scale + 110.0F * Settings.scale;

        for(int i = 0; i < count; ++i) {
            (this.options.get(i)).hb.move(offsetX + (float)i * 220.0F * Settings.scale, (Settings.isFourByThree ? 254.0F : 190.0F) * Settings.scale);
        }

        this.leftArrow.move(offsetX - 220.0F * Settings.scale, (Settings.isFourByThree ? 254.0F : 190.0F) * Settings.scale);
        this.rightArrow.move(offsetX + (float)count * 220.0F * Settings.scale, (Settings.isFourByThree ? 254.0F : 190.0F) * Settings.scale);
    }

    public void deselectOtherOptions(CharacterLoadout characterLoadout) {
        for (CharacterLoadout o : this.options) {
            if (o != characterLoadout) {
                o.selected = false;
            }
        }

    }

    public void justSelected() {
        this.bg_y_offset = 0.0F;
    }

    private class RightOptionsButton implements IUIElement {
        private final Texture arrow;
        private int x;
        private int y;
        private final int w;
        private final int h;
        private final Hitbox hitbox;

        public RightOptionsButton(String imgUrl, int x, int y) {
            this.arrow = ImageMaster.loadImage(imgUrl);
            this.x = x;
            this.y = y;
            this.w = (int)(Settings.scale * (float)this.arrow.getWidth() / 2.0F);
            this.h = (int)(Settings.scale * (float)this.arrow.getHeight() / 2.0F);
            this.hitbox = new Hitbox((float)x, (float)y, (float)this.w, (float)this.h);
        }

        public void move(float newX, float newY) {
            this.x = (int)(newX - (float)this.w / 2.0F);
            this.y = (int)(newY - (float)this.h / 2.0F);
            this.hitbox.move(newX, newY);
        }

        public void render(SpriteBatch sb) {
            if (this.hitbox.hovered) {
                sb.setColor(Color.WHITE);
            } else {
                sb.setColor(Color.LIGHT_GRAY);
            }

            float halfW = (float)this.arrow.getWidth() / 2.0F;
            float halfH = (float)this.arrow.getHeight() / 2.0F;
            sb.draw(this.arrow, (float)this.x - 10.0F * Settings.xScale - halfW + halfW * 0.5F * Settings.scale, (float)this.y + 10.0F * Settings.yScale - halfH + halfH * 0.5F * Settings.scale, halfW, halfH, (float)this.arrow.getWidth(), (float)this.arrow.getHeight(), 0.75F * Settings.scale, 0.75F * Settings.scale, 0.0F, 0, 0, this.arrow.getWidth(), this.arrow.getHeight(), true, false);
            this.hitbox.render(sb);
        }

        public void update() {
            this.hitbox.update();
            if (this.hitbox.hovered && InputHelper.justClickedLeft) {
                CardCrawlGame.sound.play("UI_CLICK_1");
                LoadoutScreen.this.setCurrentOptions(true);
            }
        }

        public int renderLayer() {
            return 0;
        }

        public int updateOrder() {
            return 0;
        }
    }

    private class LeftOptionsButton implements IUIElement {
        private final Texture arrow;
        private int x;
        private int y;
        private final int w;
        private final int h;
        private final Hitbox hitbox;

        public LeftOptionsButton(String imgUrl, int x, int y) {
            this.arrow = ImageMaster.loadImage(imgUrl);
            this.x = x;
            this.y = y;
            this.w = (int)(Settings.scale * (float)this.arrow.getWidth() / 2.0F);
            this.h = (int)(Settings.scale * (float)this.arrow.getHeight() / 2.0F);
            this.hitbox = new Hitbox((float)x, (float)y, (float)this.w, (float)this.h);
        }

        public void move(float newX, float newY) {
            this.x = (int)(newX - (float)this.w / 2.0F);
            this.y = (int)(newY - (float)this.h / 2.0F);
            this.hitbox.move(newX, newY);
        }

        public void render(SpriteBatch sb) {
            if (this.hitbox.hovered) {
                sb.setColor(Color.WHITE);
            } else {
                sb.setColor(Color.LIGHT_GRAY);
            }

            float halfW = (float)this.arrow.getWidth() / 2.0F;
            float halfH = (float)this.arrow.getHeight() / 2.0F;
            sb.draw(this.arrow, (float)this.x + 10.0F * Settings.xScale - halfW + halfW * 0.5F * Settings.scale, (float)this.y + 10.0F * Settings.yScale - halfH + halfH * 0.5F * Settings.scale, halfW, halfH, (float)this.arrow.getWidth(), (float)this.arrow.getHeight(), 0.75F * Settings.scale, 0.75F * Settings.scale, 0.0F, 0, 0, this.arrow.getWidth(), this.arrow.getHeight(), false, false);
            this.hitbox.render(sb);
        }

        public void update() {
            this.hitbox.update();
            if (this.hitbox.hovered && InputHelper.justClickedLeft) {
                CardCrawlGame.sound.play("UI_CLICK_1");
                LoadoutScreen.this.setCurrentOptions(false);
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
