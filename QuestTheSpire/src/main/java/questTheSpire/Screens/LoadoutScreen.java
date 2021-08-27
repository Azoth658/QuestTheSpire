package questTheSpire.Screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ibm.jvm.j9.dump.extract.Main;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.metrics.BotDataUploader;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import com.megacrit.cardcrawl.screens.mainMenu.MenuCancelButton;
import com.megacrit.cardcrawl.ui.buttons.ConfirmButton;
import com.megacrit.cardcrawl.ui.panels.SeedPanel;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import questTheSpire.patches.MainMenuPatches;

import java.util.ArrayList;
import java.util.Iterator;

public class LoadoutScreen {
        private static final UIStrings uiStrings;
        public static final String[] TEXT;
        private static final UIStrings uiStrings2;
        public static final String[] A_TEXT;
        public ConfirmButton confirmButton;
        public MenuCancelButton cancelButton;
        public ArrayList<CharacterOption> options;
        private boolean anySelected;
        public Texture bgCharImg;
        private Color bgCharColor;
        private float bg_y_offset;
        public boolean isAscensionMode;
        private boolean isAscensionModeUnlocked;
        public int ascensionLevel;
        public String ascLevelInfoString;

        private final float imageScale;
        private Hitbox hpLeftHb;
        private Hitbox hpRightHb;

        public LoadoutScreen() {
            this.confirmButton = new ConfirmButton(TEXT[1]);
            this.cancelButton = new MenuCancelButton();
            this.options = new ArrayList();
            this.anySelected = false;
            this.bgCharImg = null;
            this.bgCharColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
            this.bg_y_offset = 0.0F;
            this.isAscensionMode = false;
            this.isAscensionModeUnlocked = false;
            this.ascensionLevel = 0;
            this.ascLevelInfoString = "";
            this.imageScale = Settings.isMobile ? Settings.scale * 1.2F : Settings.scale;

            initialize();
        }

        public void initialize() {
            this.hpLeftHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
            this.hpRightHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
        }


        public void open() {
            this.cancelButton.show(TEXT[5]);
            CardCrawlGame.mainMenuScreen.screen = MainMenuPatches.Enums.LOADOUT_VIEW; //This is how we tell it what screen is open
            CardCrawlGame.mainMenuScreen.darken();
        }

        public void update() {
            this.updateButtons();
            if (InputHelper.justReleasedClickLeft && !this.anySelected) {
                this.confirmButton.isDisabled = true;
                this.confirmButton.hide();
            }

            CardCrawlGame.mainMenuScreen.superDarken = this.anySelected;
        }

        public void updateButtons() {
            this.cancelButton.update();
            if (this.cancelButton.hb.clicked || InputHelper.pressedEscape) {
                CardCrawlGame.mainMenuScreen.superDarken = false;
                InputHelper.pressedEscape = false;
                this.cancelButton.hb.clicked = false;
                this.cancelButton.hide();
                CardCrawlGame.mainMenuScreen.screen = MainMenuScreen.CurScreen.MAIN_MENU;
                CardCrawlGame.mainMenuScreen.lighten();
                this.bgCharColor.a = 0.0F;
                this.anySelected = false;
            }
        }

        public void render(SpriteBatch sb) {
            this.cancelButton.render(sb);
            boolean anythingSelected = false;
            sb.draw(ImageMaster.CF_LEFT_ARROW, this.hpLeftHb.cX - 24.0F, this.hpLeftHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);
            sb.draw(ImageMaster.CF_RIGHT_ARROW, this.hpRightHb.cX - 24.0F, this.hpRightHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);
            this.hpLeftHb.render(sb);
            this.hpRightHb.render(sb);
        }

        static {
            //TODO grab the correct text once we rewrite the file. We can hardcode stuff for now
            uiStrings = CardCrawlGame.languagePack.getUIString("CharacterSelectScreen");
            TEXT = uiStrings.TEXT;
            uiStrings2 = CardCrawlGame.languagePack.getUIString("AscensionModeDescriptions");
            A_TEXT = uiStrings2.TEXT;
        }

}
