package questTheSpire.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import questTheSpire.QuestTheSpire;
import questTheSpire.cards.BlankCard;
import questTheSpire.patches.MainMenuPatches;
import questTheSpire.util.CharacterSaveFile;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static com.megacrit.cardcrawl.core.Settings.scale;
import static questTheSpire.characters.MasteryCards.Enums.COLOR_MASTERY;

public class CharacterLoadout {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(QuestTheSpire.makeID("LoadoutScreen"));
    private static final String[] TEXT = uiStrings.TEXT;
    public static final String LEVEL = TEXT[0];
    public static final String PRESTIGE = TEXT[1];
    public static final String PERK_POINTS = TEXT[2];
    public static final String CORE_BUFFS = TEXT[3];
    public static final String ASPECTS = TEXT[4];
    public static final String EXTRA_STUFF = TEXT[5];
    public static final String MAX_HEALTH = TEXT[6];
    public static final String EXTRA_GOLD = TEXT[7];
    public static final String STRENGTH = TEXT[8];
    public static final String DEXTERITY = TEXT[9];
    public static final String FOCUS = TEXT[10];
    public static final String REGEN = TEXT[11];
    public static final String DEVOTION = TEXT[12];
    public static final String RESET = TEXT[13];
    public static final String ARTIFACT = TEXT[14];
    public static final String COMMON_RELIC = TEXT[15];
    public static final String UNCOMMON_RELIC = TEXT[16];
    public static final String RARE_RELIC = TEXT[17];
    public static final AtlasRegion PERK_IMAGE = ImageMaster.CARD_COLORLESS_ORB;
    public static final AtlasRegion RESET_IMAGE = new TextureAtlas(Gdx.files.internal("powers/powers.atlas")).findRegion("128/" + "retain");
    private Texture buttonImg;
    private Texture portraitImg;
    private String portraitUrl;
    public AbstractPlayer c;
    public boolean selected = false;
    public boolean locked = false;
    public Hitbox hb;
    public Hitbox resetHitbox;
    private static final float HB_W = 150.0F * scale;
    private static final int BUTTON_W = 220;
    public static final String ASSETS_DIR = "images/ui/charSelect/";
    private static final Color BLACK_OUTLINE_COLOR = new Color(0.0F, 0.0F, 0.0F, 0.5F);
    private Color glowColor = new Color(1.0F, 0.8F, 0.2F, 0.0F);
    private static final int ICON_W = 64;
    private static final float DEST_INFO_X = 200.0F * scale;
    private static final float START_INFO_X = -800.0F * Settings.xScale;
    private float infoX;
    private float infoY;
    public String name;
    private String levelInfo;
    private String hp;
    private int gold;
    private String flavorText;
    private CharSelectInfo charInfo;
    //private int unlocksRemaining;
    //private int maxAscensionLevel;
    private final ArrayList<ClickableLoadoutOption> customizationOptions = new ArrayList<>();
    private static final float PERK_X = 100f * scale;
    private static final float PERK_Y = Settings.HEIGHT - 100f * scale;
    private static final float RESET_X = Settings.WIDTH - 100f * scale;
    private static final float RESET_Y = Settings.HEIGHT - 100f * scale;
    private static final float Y_OFFSET_PER_OPTION = -50f * scale;
    private static final float NAME_Y = Settings.HEIGHT - 75f * scale;
    private static final float LEVEL_Y = NAME_Y + Y_OFFSET_PER_OPTION;
    private static final float HEADER_Y = LEVEL_Y + 1.5f*Y_OFFSET_PER_OPTION;
    private static final float COLUMN_1_X = Settings.WIDTH/6f;
    private static final float COLUMN_2_X = Settings.WIDTH/2f;
    private static final float COLUMN_3_X = 5*Settings.WIDTH/6f;
    private static final float COLUMN_Y = HEADER_Y + Y_OFFSET_PER_OPTION;
    private CharacterSaveFile file;
    private final AbstractCard clickableMasteryCard = new BlankCard();

    public CharacterLoadout(String optionName, AbstractPlayer c, Texture buttonImg, Texture portraitImg) {
        this.infoX = START_INFO_X;
        this.infoY = (float) Settings.HEIGHT / 2.0F;
        this.name = "";
        this.name = optionName;
        this.hb = new Hitbox(HB_W, HB_W);
        this.resetHitbox = new Hitbox(RESET_X - PERK_IMAGE.getRegionWidth()/2F, RESET_Y - PERK_IMAGE.getRegionHeight()/2F,PERK_IMAGE.getRegionWidth(), PERK_IMAGE.getRegionHeight());
        this.buttonImg = buttonImg;
        this.portraitImg = portraitImg;
        this.c = c;
        this.charInfo = null;
        this.charInfo = c.getLoadout();
        this.hp = this.charInfo.hp;
        this.gold = this.charInfo.gold;
        this.flavorText = this.charInfo.flavorText;
        getFile();
        setupCustomizationOptions();
    }

    public CharacterLoadout(String optionName, AbstractPlayer c, String buttonUrl, String portraitImg) {
        this.infoX = START_INFO_X;
        this.infoY = (float)Settings.HEIGHT / 2.0F;
        this.name = "";
        this.name = optionName;
        this.hb = new Hitbox(HB_W, HB_W);
        this.resetHitbox = new Hitbox(RESET_X - PERK_IMAGE.getRegionWidth()/2F, RESET_Y - PERK_IMAGE.getRegionHeight()/2F,PERK_IMAGE.getRegionWidth(), PERK_IMAGE.getRegionHeight());
        this.buttonImg = ImageMaster.loadImage("images/ui/charSelect/" + buttonUrl);
        this.portraitUrl = c.getPortraitImageName();
        this.c = c;
        this.charInfo = null;
        this.charInfo = c.getLoadout();
        this.hp = this.charInfo.hp;
        this.gold = this.charInfo.gold;
        this.flavorText = this.charInfo.flavorText;
        getFile();
        setupCustomizationOptions();
    }

    public CharacterLoadout(AbstractPlayer c) {
        this.infoX = START_INFO_X;
        this.infoY = (float)Settings.HEIGHT / 2.0F;
        this.name = "";
        this.hb = new Hitbox(HB_W, HB_W);
        this.resetHitbox = new Hitbox(RESET_X - PERK_IMAGE.getRegionWidth()/2F, RESET_Y - PERK_IMAGE.getRegionHeight()/2F,PERK_IMAGE.getRegionWidth(), PERK_IMAGE.getRegionHeight());
        this.buttonImg = ImageMaster.CHAR_SELECT_LOCKED;
        this.locked = true;
        this.c = c;
    }

    private void getFile() {
        file = new CharacterSaveFile(c.chosenClass);
        levelInfo = LEVEL + file.getLevel() + (file.getPrestigeLevel() > 0 ? " " + PRESTIGE + file.getPrestigeLevel() : "");
    }

    private void setupCustomizationOptions() {
        //First Column
        float cX = COLUMN_1_X, cY = COLUMN_Y;
        //Max HP
        customizationOptions.add(new ClickableLoadoutOption(this, cX, cY) {
            @Override
            public void onClickArrow(boolean increase) {
                if (increase && canUpgrade()) {
                    file.setMaxHP(file.getStartMaxHP() + amountPerLevel());
                    file.setCurrentPerkPoints(file.getCurrentPerkPoints()-getUpgradeCost());
                } else if (canDowngrade()){
                    file.setMaxHP(file.getStartMaxHP() - amountPerLevel());
                    file.setCurrentPerkPoints(file.getCurrentPerkPoints()+getDowngradeRefund());
                }
            }

            @Override
            public String makeLabel() {
                return MAX_HEALTH+": "+file.getStartMaxHP();
            }

            @Override
            public int getUpgradeCost() {
                return 1;
            }

            @Override
            public int getDowngradeRefund() {
                return 1;
            }

            @Override
            public boolean canUpgrade() {
                return file.getCurrentPerkPoints() >= getUpgradeCost();
            }

            @Override
            public boolean canDowngrade() {
                return file.getStartMaxHP() > 0;
            }

            @Override
            public int amountPerLevel() {
                return 5;
            }
        });
        cY += Y_OFFSET_PER_OPTION;
        //Gold
        customizationOptions.add(new ClickableLoadoutOption(this, cX, cY) {
            @Override
            public void onClickArrow(boolean increase) {
                if (increase && canUpgrade()) {
                    file.setStartGold(file.getStartGold() + amountPerLevel());
                    file.setCurrentPerkPoints(file.getCurrentPerkPoints() - getUpgradeCost());
                } else if (canDowngrade()) {
                    file.setStartGold(file.getStartGold() - amountPerLevel());
                    file.setCurrentPerkPoints(file.getCurrentPerkPoints() + getDowngradeRefund());
                }
            }

            @Override
            public String makeLabel() {
                return EXTRA_GOLD+": "+file.getStartGold();
            }

            @Override
            public int getUpgradeCost() {
                return 1;
            }

            @Override
            public int getDowngradeRefund() {
                return 1;
            }

            @Override
            public boolean canUpgrade() {
                return file.getCurrentPerkPoints() >= getUpgradeCost();
            }

            @Override
            public boolean canDowngrade() {
                return file.getStartGold() > 0;
            }

            @Override
            public int amountPerLevel() {
                return 20;
            }
        });
        cY += Y_OFFSET_PER_OPTION;
        //Strength
        customizationOptions.add(new ClickableLoadoutOption(this, cX, cY) {
            @Override
            public void onClickArrow(boolean increase) {
                if (increase && canUpgrade()) {
                    file.setStr(file.getStr() + amountPerLevel());
                    file.setCurrentPerkPoints(file.getCurrentPerkPoints() - getUpgradeCost());
                } else if (canDowngrade()) {
                    file.setStr(file.getStr() - amountPerLevel());
                    file.setCurrentPerkPoints(file.getCurrentPerkPoints() + getDowngradeRefund());
                }
            }

            @Override
            public String makeLabel() {
                return STRENGTH+": "+file.getStr();
            }

            @Override
            public int getUpgradeCost() {
                return 3;
            }

            @Override
            public int getDowngradeRefund() {
                return 3;
            }

            @Override
            public boolean canUpgrade() {
                return file.getCurrentPerkPoints() >= getUpgradeCost();
            }

            @Override
            public boolean canDowngrade() {
                return file.getStr() > 0;
            }

            @Override
            public int amountPerLevel() {
                return 1;
            }
        });
        cY += Y_OFFSET_PER_OPTION;
        //Dex
        customizationOptions.add(new ClickableLoadoutOption(this, cX, cY) {
            @Override
            public void onClickArrow(boolean increase) {
                if (increase && canUpgrade()) {
                    file.setDex(file.getDex() + amountPerLevel());
                    file.setCurrentPerkPoints(file.getCurrentPerkPoints() - getUpgradeCost());
                } else if (canDowngrade()) {
                    file.setDex(file.getDex() - amountPerLevel());
                    file.setCurrentPerkPoints(file.getCurrentPerkPoints() + getDowngradeRefund());
                }
            }

            @Override
            public String makeLabel() {
                return DEXTERITY+": "+file.getDex();
            }

            @Override
            public int getUpgradeCost() {
                return 3;
            }

            @Override
            public int getDowngradeRefund() {
                return 3;
            }

            @Override
            public boolean canUpgrade() {
                return file.getCurrentPerkPoints() >= getUpgradeCost();
            }

            @Override
            public boolean canDowngrade() {
                return file.getDex() > 0;
            }

            @Override
            public int amountPerLevel() {
                return 1;
            }
        });
        cY += Y_OFFSET_PER_OPTION;
        //Focus
        customizationOptions.add(new ClickableLoadoutOption(this, cX, cY) {
            @Override
            public void onClickArrow(boolean increase) {
                if (increase && canUpgrade()) {
                    file.setFoc(file.getFoc() + amountPerLevel());
                    file.setCurrentPerkPoints(file.getCurrentPerkPoints() - getUpgradeCost());
                } else if (canDowngrade()) {
                    file.setFoc(file.getFoc() - amountPerLevel());
                    file.setCurrentPerkPoints(file.getCurrentPerkPoints() + getDowngradeRefund());
                }
            }

            @Override
            public String makeLabel() {
                return FOCUS+": "+file.getFoc();
            }

            @Override
            public int getUpgradeCost() {
                return 4;
            }

            @Override
            public int getDowngradeRefund() {
                return 4;
            }

            @Override
            public boolean canUpgrade() {
                return file.getCurrentPerkPoints() >= getUpgradeCost();
            }

            @Override
            public boolean canDowngrade() {
                return file.getFoc() > 0;
            }

            @Override
            public int amountPerLevel() {
                return 1;
            }
        });
        cY += Y_OFFSET_PER_OPTION;
        //Regen
        customizationOptions.add(new ClickableLoadoutOption(this, cX, cY) {
            @Override
            public void onClickArrow(boolean increase) {
                if (increase && canUpgrade()) {
                    file.setCurrentPerkPoints(file.getCurrentPerkPoints() - getUpgradeCost());
                    file.setReg(file.getReg() + amountPerLevel());
                } else if (canDowngrade()) {
                    file.setCurrentPerkPoints(file.getCurrentPerkPoints() + getDowngradeRefund());
                    file.setReg(file.getReg() - amountPerLevel());
                }
            }

            @Override
            public String makeLabel() {
                return REGEN+": "+file.getReg();
            }

            @Override
            public int getUpgradeCost() {
                return 1 + file.getReg();
            }

            @Override
            public int getDowngradeRefund() {
                return file.getReg();
            }

            @Override
            public boolean canUpgrade() {
                return file.getCurrentPerkPoints() >= getUpgradeCost() && file.getReg() < 4;
            }

            @Override
            public boolean canDowngrade() {
                return file.getReg() > 0;
            }

            @Override
            public int amountPerLevel() {
                return 1;
            }
        });
        cY += Y_OFFSET_PER_OPTION;
        //Artifact
        customizationOptions.add(new ClickableLoadoutOption(this, cX, cY) {
            @Override
            public void onClickArrow(boolean increase) {
                if (increase && canUpgrade()) {
                    file.setCurrentPerkPoints(file.getCurrentPerkPoints() - getUpgradeCost());
                    file.setArt(file.getArt() + amountPerLevel());
                } else if (canDowngrade()) {
                    file.setCurrentPerkPoints(file.getCurrentPerkPoints() + getDowngradeRefund());
                    file.setArt(file.getArt() - amountPerLevel());
                }
            }

            @Override
            public String makeLabel() {
                return ARTIFACT+": "+file.getArt();
            }

            @Override
            public int getUpgradeCost() {
                return 2 + file.getArt();
            }

            @Override
            public int getDowngradeRefund() {
                return file.getArt() + 1;
            }

            @Override
            public boolean canUpgrade() {
                return file.getCurrentPerkPoints() >= getUpgradeCost() && file.getArt() < 5;
            }

            @Override
            public boolean canDowngrade() {
                return file.getArt() > 0;
            }

            @Override
            public int amountPerLevel() {
                return 1;
            }
        });
        cY += Y_OFFSET_PER_OPTION;
        //Devotion
        customizationOptions.add(new ClickableLoadoutOption(this, cX, cY) {
            @Override
            public void onClickArrow(boolean increase) {
                if (increase && canUpgrade()) {
                    file.setDev(file.getDev() + amountPerLevel());
                    file.setCurrentPerkPoints(file.getCurrentPerkPoints() - getUpgradeCost());
                } else if (canDowngrade()) {
                    file.setDev(file.getDev() - amountPerLevel());
                    file.setCurrentPerkPoints(file.getCurrentPerkPoints() + getDowngradeRefund());
                }
            }

            @Override
            public String makeLabel() {
                return DEVOTION+": "+file.getDev();
            }

            @Override
            public int getUpgradeCost() {
                return 3;
            }

            @Override
            public int getDowngradeRefund() {
                return 3;
            }

            @Override
            public boolean canUpgrade() {
                return file.getCurrentPerkPoints() >= getUpgradeCost() && file.getDev() < 3;
            }

            @Override
            public boolean canDowngrade() {
                return file.getDev() > 0;
            }

            @Override
            public int amountPerLevel() {
                return 1;
            }
        });
        cY += Y_OFFSET_PER_OPTION;

        //Second Column
        cX = COLUMN_2_X;
        cY = COLUMN_Y;

        //Third Column
        cX = COLUMN_3_X;
        cY = COLUMN_Y;

        //Common Relic
        customizationOptions.add(new ClickableLoadoutOption(this, cX, cY) {
            @Override
            public void onClickArrow(boolean increase) {
                if (increase && canUpgrade()) {
                    file.setCommonRelic(file.getCommonRelic() + amountPerLevel());
                    file.setCurrentPerkPoints(file.getCurrentPerkPoints()-getUpgradeCost());
                } else if (canDowngrade()){
                    file.setCommonRelic(file.getCommonRelic() - amountPerLevel());
                    file.setCurrentPerkPoints(file.getCurrentPerkPoints()+getDowngradeRefund());
                }
            }

            @Override
            public String makeLabel() {
                return COMMON_RELIC+": "+file.getCommonRelic();
            }

            @Override
            public int getUpgradeCost() {
                return 3;
            }

            @Override
            public int getDowngradeRefund() {
                return 3;
            }

            @Override
            public boolean canUpgrade() {
                return file.getCurrentPerkPoints() >= getUpgradeCost() && file.getCommonRelic() < 5;
            }

            @Override
            public boolean canDowngrade() {
                return file.getCommonRelic() > 0;
            }

            @Override
            public int amountPerLevel() {
                return 1;
            }
        });
        cY += Y_OFFSET_PER_OPTION;
        //Uncommon Relic
        customizationOptions.add(new ClickableLoadoutOption(this, cX, cY) {
            @Override
            public void onClickArrow(boolean increase) {
                if (increase && canUpgrade()) {
                    file.setUncommonRelic(file.getUncommonRelic() + amountPerLevel());
                    file.setCurrentPerkPoints(file.getCurrentPerkPoints() - getUpgradeCost());
                } else if (canDowngrade()) {
                    file.setUncommonRelic(file.getUncommonRelic() - amountPerLevel());
                    file.setCurrentPerkPoints(file.getCurrentPerkPoints() + getDowngradeRefund());
                }
            }

            @Override
            public String makeLabel() {
                return UNCOMMON_RELIC+": "+file.getUncommonRelic();
            }

            @Override
            public int getUpgradeCost() {
                return 4;
            }

            @Override
            public int getDowngradeRefund() {
                return 4;
            }

            @Override
            public boolean canUpgrade() {
                return file.getCurrentPerkPoints() >= getUpgradeCost() && file.getUncommonRelic() < 4;
            }

            @Override
            public boolean canDowngrade() {
                return file.getUncommonRelic() > 0;
            }

            @Override
            public int amountPerLevel() {
                return 1;
            }

        });
        cY += Y_OFFSET_PER_OPTION;
        //Rare Relic
        customizationOptions.add(new ClickableLoadoutOption(this, cX, cY) {
            @Override
            public void onClickArrow(boolean increase) {
                if (increase && canUpgrade()) {
                    file.setRareRelic(file.getRareRelic() + amountPerLevel());
                    file.setCurrentPerkPoints(file.getCurrentPerkPoints() - getUpgradeCost());
                } else if (canDowngrade()) {
                    file.setRareRelic(file.getRareRelic() - amountPerLevel());
                    file.setCurrentPerkPoints(file.getCurrentPerkPoints() + getDowngradeRefund());
                }
            }

            @Override
            public String makeLabel() {
                return RARE_RELIC+": "+file.getRareRelic();
            }

            @Override
            public int getUpgradeCost() {
                return 5;
            }

            @Override
            public int getDowngradeRefund() {
                return 5;
            }

            @Override
            public boolean canUpgrade() {
                return file.getCurrentPerkPoints() >= getUpgradeCost() && file.getCommonRelic() < 3;
            }

            @Override
            public boolean canDowngrade() {
                return file.getRareRelic() > 0;
            }

            @Override
            public int amountPerLevel() {
                return 1;
            }

        });
    }

    public void setAllButtonsNeedUpdate() {
        for (ClickableLoadoutOption o : customizationOptions) {
            o.updateNeeded = true;
        }
    }

    public void update() {
        this.updateMainHitbox();
        this.updateInfoPosition();
        this.updateCustomizationOptions();
        this.updateResetHitbox();
        //this.updateMasteryHitbox();
    }

    private void updateCustomizationOptions() {
        if (selected) {
            for (ClickableLoadoutOption o : customizationOptions) {
                o.update();
            }
        }
    }

    private void updateMainHitbox() {
        this.hb.update();
        if (this.hb.justHovered) {
            CardCrawlGame.sound.playA("UI_HOVER", -0.3F);
        }

        //TODO reimplement hover logic and locked characters?
        /*if (this.hb.hovered && this.locked) {
            if (this.c.chosenClass == AbstractPlayer.PlayerClass.THE_SILENT) {
                TipHelper.renderGenericTip((float) InputHelper.mX + 70.0F * Settings.xScale, (float)InputHelper.mY - 10.0F * Settings.scale, TEXT[0], TEXT[1]);
            } else if (this.c.chosenClass == AbstractPlayer.PlayerClass.DEFECT) {
                TipHelper.renderGenericTip((float)InputHelper.mX + 70.0F * Settings.xScale, (float)InputHelper.mY - 10.0F * Settings.scale, TEXT[0], TEXT[3]);
            } else if (this.c.chosenClass == AbstractPlayer.PlayerClass.WATCHER) {
                TipHelper.renderGenericTip((float)InputHelper.mX + 70.0F * Settings.xScale, (float)InputHelper.mY - 10.0F * Settings.scale, TEXT[0], TEXT[10]);
            }
        }*/

        if (InputHelper.justClickedLeft && !this.locked && this.hb.hovered) {
            CardCrawlGame.sound.playA("UI_CLICK_1", -0.4F);
            this.hb.clickStarted = true;
        }

        if (this.hb.clicked) {
            this.hb.clicked = false;
            if (!this.selected) {
                getLoadoutScreen().deselectOtherOptions(this);
                this.selected = true;
                getLoadoutScreen().justSelected();
                //CardCrawlGame.chosenCharacter = this.c.chosenClass;
                if (this.portraitUrl != null) {
                    getLoadoutScreen().bgCharImg = ImageMaster.loadImage("images/ui/charSelect/" + this.portraitUrl);
                } else {
                    getLoadoutScreen().bgCharImg = this.portraitImg;
                }
            }
        }
    }

    private void updateResetHitbox() {
        //TODO Make the reset button glow or something on hover and click so it it more clear it is working (Done? Added hitbox hovered check to render color)
        if (selected) {
            this.resetHitbox.update();
            if (this.resetHitbox.justHovered) {
                CardCrawlGame.sound.playA("UI_HOVER", -0.3F);
            }

            if (InputHelper.justClickedLeft && this.resetHitbox.hovered) {
                CardCrawlGame.sound.playA("UI_CLICK_1", -0.4F);
                this.resetHitbox.clickStarted = true;
            }

            if (this.resetHitbox.clicked) {
                this.resetHitbox.clicked = false;
                file.setCurrentPerkPoints(file.getMaxPerkPoints());
                file.setMaxHP(0);
                file.setStartGold(0);
                file.setStr(0);
                file.setDex(0);
                file.setFoc(0);
                file.setReg(0);
                file.setArt(0);
                file.setDev(0);
                file.setCommonRelic(0);
                file.setUncommonRelic(0);
                file.setRareRelic(0);
                this.setAllButtonsNeedUpdate();
            }
        }

    }

    private void updateMasteryHitbox() {
        if (selected) {
            this.clickableMasteryCard.hb.update();
            if (this.clickableMasteryCard.hb.justHovered) {
                CardCrawlGame.sound.playA("UI_HOVER", -0.3F);
            }

            if (InputHelper.justClickedLeft && this.clickableMasteryCard.hb.hovered) {
                CardCrawlGame.sound.playA("UI_CLICK_1", -0.4F);
                this.clickableMasteryCard.hb.clickStarted = true;
            }

            if (this.clickableMasteryCard.hb.clicked) {
                    CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                    for (AbstractCard c : CardLibrary.getAllCards().stream().filter(c -> c.color == COLOR_MASTERY).collect(Collectors.toList())) {
                        group.addToBottom(c);
                        }
                    AbstractDungeon.gridSelectScreen.open(group, 1, "TEST", false);

            }
        }

    }

    private void updateInfoPosition() {
        if (this.selected) {
            this.infoX = MathHelper.uiLerpSnap(this.infoX, DEST_INFO_X);
        } else {
            this.infoX = MathHelper.uiLerpSnap(this.infoX, START_INFO_X);
        }

    }

    public void render(SpriteBatch sb) {
        this.renderOptionButton(sb);
        this.renderInfo(sb);
        this.hb.render(sb);
        this.renderResetHitbox(sb);
        this.renderCustomizationOptions(sb);
        //this.renderMasteryCard(sb);
    }

    public void renderMasteryCard(SpriteBatch sb){
        if (selected) {
            clickableMasteryCard.current_x = Settings.WIDTH/2f ;
            clickableMasteryCard.current_y = Settings.HEIGHT/1.7f;
            clickableMasteryCard.render(sb);
            this.clickableMasteryCard.hb.render(sb);
        }
    }

    private void renderResetHitbox(SpriteBatch sb) {
        if (selected) {
            this.resetHitbox.render(sb);
        }
    }

    private void renderCustomizationOptions(SpriteBatch sb) {
        if (selected) {
            for (ClickableLoadoutOption o : customizationOptions) {
                o.render(sb);
            }
        }
    }

    private void renderOptionButton(SpriteBatch sb) {
        if (this.selected) {
            this.glowColor.a = 0.25F + (MathUtils.cosDeg((float)(System.currentTimeMillis() / 4L % 360L)) + 1.25F) / 3.5F;
            sb.setColor(this.glowColor);
        } else {
            sb.setColor(BLACK_OUTLINE_COLOR);
        }

        sb.draw(ImageMaster.CHAR_OPT_HIGHLIGHT, this.hb.cX - BUTTON_W/2F, this.hb.cY - BUTTON_W/2F, BUTTON_W/2F, BUTTON_W/2F, BUTTON_W, BUTTON_W, scale, scale, 0.0F, 0, 0, BUTTON_W, BUTTON_W, false, false);
        if (!this.selected && !this.hb.hovered) {
            sb.setColor(Color.LIGHT_GRAY);
        } else {
            sb.setColor(Color.WHITE);
        }

        sb.draw(this.buttonImg, this.hb.cX - BUTTON_W/2F, this.hb.cY - BUTTON_W/2F, BUTTON_W/2F, BUTTON_W/2F, BUTTON_W, BUTTON_W, scale, scale, 0.0F, 0, 0, BUTTON_W, BUTTON_W, false, false);
    }

    private void renderInfo(SpriteBatch sb) {
        if (!this.name.equals("") && selected) {
            FontHelper.renderFontCentered(sb, FontHelper.bannerNameFont, this.name, COLUMN_2_X, NAME_Y, Settings.GOLD_COLOR, scale);
            FontHelper.renderFontCentered(sb, FontHelper.charTitleFont, this.levelInfo, COLUMN_2_X, LEVEL_Y, Settings.BLUE_RELIC_COLOR, scale);
            FontHelper.renderFontCentered(sb, FontHelper.charTitleFont, CORE_BUFFS, COLUMN_1_X, HEADER_Y, Settings.GOLD_COLOR, scale);
            FontHelper.renderFontCentered(sb, FontHelper.charTitleFont, ASPECTS, COLUMN_2_X, HEADER_Y, Settings.GOLD_COLOR, scale);
            FontHelper.renderFontCentered(sb, FontHelper.charTitleFont, EXTRA_STUFF, COLUMN_3_X, HEADER_Y, Settings.GOLD_COLOR, scale);
            sb.draw(PERK_IMAGE, PERK_X - PERK_IMAGE.getRegionWidth()/2F, PERK_Y - PERK_IMAGE.getRegionHeight()/2F, PERK_IMAGE.getRegionWidth()/2F, PERK_IMAGE.getRegionHeight()/2F, PERK_IMAGE.getRegionWidth(), PERK_IMAGE.getRegionHeight(), scale, scale, 0.0F);
            FontHelper.renderFontCentered(sb, FontHelper.charTitleFont, file.getCurrentPerkPoints()+" / "+file.getMaxPerkPoints(), PERK_X+3* PERK_IMAGE.getRegionWidth()/2f, PERK_Y, Settings.GOLD_COLOR, scale);
            sb.draw(RESET_IMAGE, RESET_X - RESET_IMAGE.getRegionWidth()/2F, RESET_Y - RESET_IMAGE.getRegionHeight()/2F, RESET_IMAGE.getRegionWidth()/2F, RESET_IMAGE.getRegionHeight()/2F, RESET_IMAGE.getRegionWidth(), RESET_IMAGE.getRegionHeight(), (float) PERK_IMAGE.getRegionWidth()/ RESET_IMAGE.getRegionWidth()* scale, (float) PERK_IMAGE.getRegionHeight()/ RESET_IMAGE.getRegionHeight()* scale, 0.0F);
            FontHelper.renderFontRightAligned(sb, FontHelper.charTitleFont, RESET, RESET_X - PERK_IMAGE.getRegionWidth(), RESET_Y, resetHitbox.hovered ? Settings.BLUE_TEXT_COLOR : Settings.GOLD_COLOR);
        }
    }

    private LoadoutScreen getLoadoutScreen() {
        return MainMenuPatches.LoadoutScreenField.loadoutScreen.get(CardCrawlGame.mainMenuScreen);
    }
}