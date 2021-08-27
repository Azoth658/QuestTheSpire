package questTheSpire.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import questTheSpire.QuestTheSpire;
import questTheSpire.patches.MainMenuPatches;
import questTheSpire.util.CharacterSaveFile;

import java.util.ArrayList;

public class CharacterLoadout {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(QuestTheSpire.makeID("LoadoutScreen"));
    public static final String[] TEXT = uiStrings.TEXT;
    private Texture buttonImg;
    private Texture portraitImg;
    private String portraitUrl;
    public AbstractPlayer c;
    public boolean selected = false;
    public boolean locked = false;
    public Hitbox hb;
    private static final float HB_W = 150.0F * Settings.scale;
    private static final int BUTTON_W = 220;
    public static final String ASSETS_DIR = "images/ui/charSelect/";
    private static final Color BLACK_OUTLINE_COLOR = new Color(0.0F, 0.0F, 0.0F, 0.5F);
    private Color glowColor = new Color(1.0F, 0.8F, 0.2F, 0.0F);
    private static final int ICON_W = 64;
    private static final float DEST_INFO_X = 200.0F * Settings.scale;
    private static final float START_INFO_X = -800.0F * Settings.xScale;
    private float infoX;
    private float infoY;
    public String name;
    private String levelInfo;
    private static final float NAME_Y = Settings.HEIGHT - 100F * Settings.scale;
    private String hp;
    private int gold;
    private String flavorText;
    private CharSelectInfo charInfo;
    //private int unlocksRemaining;
    //private int maxAscensionLevel;
    private final ArrayList<ClickableLoadoutOption> customizationOptions = new ArrayList<>();
    private static final float Y_OFFSET_PER_OPTION = -50f * Settings.scale;
    private static final float X_COLUMN_1 = Settings.WIDTH/4f;
    private static final float X_COLUMN_2 = Settings.WIDTH/2f;
    private static final float X_COLUMN_3 = 3*Settings.WIDTH/4f;
    private static final float Y_COLUMN = Settings.HEIGHT - 200f*Settings.scale;
    private CharacterSaveFile file;

    public CharacterLoadout(String optionName, AbstractPlayer c, Texture buttonImg, Texture portraitImg) {
        this.infoX = START_INFO_X;
        this.infoY = (float) Settings.HEIGHT / 2.0F;
        this.name = "";
        this.name = optionName;
        this.hb = new Hitbox(HB_W, HB_W);
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
        this.buttonImg = ImageMaster.CHAR_SELECT_LOCKED;
        this.locked = true;
        this.c = c;
    }

    private void getFile() {
        file = new CharacterSaveFile(c.chosenClass);
        levelInfo = TEXT[0] + file.getLevel() + (file.getPrestigeLevel() > 0 ? TEXT[1] + file.getPrestigeLevel() : "");
    }

    private void setupCustomizationOptions() {
        float sx = X_COLUMN_1, sy = Settings.HEIGHT - 200f*Settings.scale;
        customizationOptions.add(new ClickableLoadoutOption("Health", sx, sy) {
            int amount = 0;
            @Override
            public void onClickArrow(boolean increase) {
                if (increase) {
                    amount++;
                } else {
                    amount--;
                }
            }

            @Override
            public String makeLabel(String name) {
                return name+": "+amount;
            }
        });
        sy += Y_OFFSET_PER_OPTION;
        customizationOptions.add(new ClickableLoadoutOption("Strength", sx, sy) {
            int amount = 0;
            @Override
            public void onClickArrow(boolean increase) {
                if (increase) {
                    amount++;
                } else {
                    amount--;
                }
            }

            @Override
            public String makeLabel(String name) {
                return name+": "+amount;
            }
        });
        sy += Y_OFFSET_PER_OPTION;
        customizationOptions.add(new ClickableLoadoutOption("?", sx, sy) {
            int amount = 0;
            @Override
            public void onClickArrow(boolean increase) {
                if (increase) {
                    amount++;
                } else {
                    amount--;
                }
            }

            @Override
            public String makeLabel(String name) {
                return name+": "+amount;
            }
        });
        sy += Y_OFFSET_PER_OPTION;

        //Second Column
        sx = X_COLUMN_2;
        sy = Y_COLUMN;

        //Third Column
        sx = X_COLUMN_3;
        sy = Y_COLUMN;
    }

    /*public void saveChosenAscensionLevel(int level) {
        Prefs pref = this.c.getPrefs();
        pref.putInteger("LAST_ASCENSION_LEVEL", level);
        pref.flush();
    }

    public void incrementAscensionLevel(int level) {
        if (level <= this.maxAscensionLevel) {
            this.saveChosenAscensionLevel(level);
            CardCrawlGame.mainMenuScreen.charSelectScreen.ascensionLevel = level;
            CardCrawlGame.mainMenuScreen.charSelectScreen.ascLevelInfoString = CharacterSelectScreen.A_TEXT[level - 1];
        }
    }

    public void decrementAscensionLevel(int level) {
        if (level != 0) {
            this.saveChosenAscensionLevel(level);
            CardCrawlGame.mainMenuScreen.charSelectScreen.ascensionLevel = level;
            CardCrawlGame.mainMenuScreen.charSelectScreen.ascLevelInfoString = CharacterSelectScreen.A_TEXT[level - 1];
        }
    }*/

    public void update() {
        this.updateHitbox();
        this.updateInfoPosition();
        this.updateCustomizationOptions();
    }

    private void updateCustomizationOptions() {
        if (selected) {
            for (ClickableLoadoutOption o : customizationOptions) {
                o.update();
            }
        }
    }

    private void updateHitbox() {
        this.hb.update();
        if (this.hb.justHovered) {
            CardCrawlGame.sound.playA("UI_HOVER", -0.3F);
        }

        if (this.hb.hovered && this.locked) {
            if (this.c.chosenClass == AbstractPlayer.PlayerClass.THE_SILENT) {
                TipHelper.renderGenericTip((float) InputHelper.mX + 70.0F * Settings.xScale, (float)InputHelper.mY - 10.0F * Settings.scale, TEXT[0], TEXT[1]);
            } else if (this.c.chosenClass == AbstractPlayer.PlayerClass.DEFECT) {
                TipHelper.renderGenericTip((float)InputHelper.mX + 70.0F * Settings.xScale, (float)InputHelper.mY - 10.0F * Settings.scale, TEXT[0], TEXT[3]);
            } else if (this.c.chosenClass == AbstractPlayer.PlayerClass.WATCHER) {
                TipHelper.renderGenericTip((float)InputHelper.mX + 70.0F * Settings.xScale, (float)InputHelper.mY - 10.0F * Settings.scale, TEXT[0], TEXT[10]);
            }
        }

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
        this.renderCustomizationOptions(sb);
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

        sb.draw(ImageMaster.CHAR_OPT_HIGHLIGHT, this.hb.cX - BUTTON_W/2F, this.hb.cY - BUTTON_W/2F, BUTTON_W/2F, BUTTON_W/2F, BUTTON_W, BUTTON_W, Settings.scale, Settings.scale, 0.0F, 0, 0, BUTTON_W, BUTTON_W, false, false);
        if (!this.selected && !this.hb.hovered) {
            sb.setColor(Color.LIGHT_GRAY);
        } else {
            sb.setColor(Color.WHITE);
        }

        sb.draw(this.buttonImg, this.hb.cX - BUTTON_W/2F, this.hb.cY - BUTTON_W/2F, BUTTON_W/2F, BUTTON_W/2F, BUTTON_W, BUTTON_W, Settings.scale, Settings.scale, 0.0F, 0, 0, BUTTON_W, BUTTON_W, false, false);
    }

    private void renderInfo(SpriteBatch sb) {
        if (!this.name.equals("") && selected) {
            FontHelper.renderFontCentered(sb, FontHelper.bannerNameFont, this.name, X_COLUMN_2, NAME_Y, Settings.GOLD_COLOR, Settings.scale);
            FontHelper.renderFontCentered(sb, FontHelper.bannerNameFont, this.levelInfo, X_COLUMN_2, NAME_Y+Y_OFFSET_PER_OPTION, Settings.BLUE_RELIC_COLOR, Settings.scale);
        }
    }

    /*private void renderRelics(SpriteBatch sb) {
        if (this.charInfo.relics.size() == 1) {
            float var10002;
            String relicString;
            float var10003;
            float var10004;
            if (!Settings.isMobile) {
                sb.setColor(Settings.QUARTER_TRANSPARENT_BLACK_COLOR);
                var10002 = this.infoX - 64.0F;
                sb.draw(RelicLibrary.getRelic((String)this.charInfo.relics.get(0)).outlineImg, var10002, this.infoY - 60.0F * Settings.scale - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128, false, false);
                sb.setColor(Color.WHITE);
                var10002 = this.infoX - 64.0F;
                sb.draw(RelicLibrary.getRelic((String)this.charInfo.relics.get(0)).img, var10002, this.infoY - 60.0F * Settings.scale - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128, false, false);
                var10003 = this.infoX + 44.0F * Settings.scale;
                var10004 = this.infoY - 40.0F * Settings.scale;
                FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, RelicLibrary.getRelic((String)this.charInfo.relics.get(0)).name, var10003, var10004, 10000.0F, 10000.0F, Settings.GOLD_COLOR);
                relicString = RelicLibrary.getRelic((String)this.charInfo.relics.get(0)).description;
                if (this.charInfo.name.equals(TEXT[7])) {
                    relicString = TEXT[8];
                }

                FontHelper.renderSmartText(sb, FontHelper.tipBodyFont, relicString, this.infoX + 44.0F * Settings.scale, this.infoY - 66.0F * Settings.scale, 10000.0F, 10000.0F, Settings.CREAM_COLOR);
            } else {
                sb.setColor(Settings.QUARTER_TRANSPARENT_BLACK_COLOR);
                var10002 = this.infoX - 64.0F;
                var10003 = this.infoY + 30.0F * Settings.scale - 64.0F;
                float var10008 = Settings.scale * 1.4F;
                float var10009 = Settings.scale * 1.4F;
                sb.draw(RelicLibrary.getRelic((String)this.charInfo.relics.get(0)).outlineImg, var10002, var10003, 64.0F, 64.0F, 128.0F, 128.0F, var10008, var10009, 0.0F, 0, 0, 128, 128, false, false);
                sb.setColor(Color.WHITE);
                var10002 = this.infoX - 64.0F;
                var10003 = this.infoY + 30.0F * Settings.scale - 64.0F;
                var10008 = Settings.scale * 1.4F;
                var10009 = Settings.scale * 1.4F;
                sb.draw(RelicLibrary.getRelic((String)this.charInfo.relics.get(0)).img, var10002, var10003, 64.0F, 64.0F, 128.0F, 128.0F, var10008, var10009, 0.0F, 0, 0, 128, 128, false, false);
                var10003 = this.infoX + 60.0F * Settings.scale;
                var10004 = this.infoY + 60.0F * Settings.scale;
                FontHelper.renderSmartText(sb, FontHelper.topPanelInfoFont, RelicLibrary.getRelic((String)this.charInfo.relics.get(0)).name, var10003, var10004, 10000.0F, 10000.0F, Settings.GOLD_COLOR);
                relicString = RelicLibrary.getRelic((String)this.charInfo.relics.get(0)).description;
                if (this.charInfo.name.equals(TEXT[7])) {
                    relicString = TEXT[8];
                }

                if (this.selected) {
                    FontHelper.renderSmartText(sb, FontHelper.topPanelInfoFont, relicString, this.infoX + 60.0F * Settings.scale, this.infoY + 24.0F * Settings.scale, 10000.0F, 10000.0F, Settings.CREAM_COLOR);
                }
            }
        } else {
            for(int i = 0; i < this.charInfo.relics.size(); ++i) {
                AbstractRelic r = RelicLibrary.getRelic((String)this.charInfo.relics.get(i));
                r.updateDescription(this.charInfo.player.chosenClass);
                Hitbox relicHitbox = new Hitbox(80.0F * Settings.scale * (0.01F + (1.0F - 0.019F * (float)this.charInfo.relics.size())), 80.0F * Settings.scale);
                relicHitbox.move(this.infoX + (float)i * 72.0F * Settings.scale * (0.01F + (1.0F - 0.019F * (float)this.charInfo.relics.size())), this.infoY - 60.0F * Settings.scale);
                relicHitbox.render(sb);
                relicHitbox.update();
                if (relicHitbox.hovered) {
                    if ((float)InputHelper.mX < 1400.0F * Settings.scale) {
                        TipHelper.queuePowerTips((float)InputHelper.mX + 60.0F * Settings.scale, (float)InputHelper.mY - 50.0F * Settings.scale, r.tips);
                    } else {
                        TipHelper.queuePowerTips((float)InputHelper.mX - 350.0F * Settings.scale, (float)InputHelper.mY - 50.0F * Settings.scale, r.tips);
                    }
                }

                sb.setColor(new Color(0.0F, 0.0F, 0.0F, 0.25F));
                sb.draw(r.outlineImg, this.infoX - 64.0F + (float)i * 72.0F * Settings.scale * (0.01F + (1.0F - 0.019F * (float)this.charInfo.relics.size())), this.infoY - 60.0F * Settings.scale - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, Settings.scale * (0.01F + (1.0F - 0.019F * (float)this.charInfo.relics.size())), Settings.scale * (0.01F + (1.0F - 0.019F * (float)this.charInfo.relics.size())), 0.0F, 0, 0, 128, 128, false, false);
                sb.setColor(Color.WHITE);
                sb.draw(r.img, this.infoX - 64.0F + (float)i * 72.0F * Settings.scale * (0.01F + (1.0F - 0.019F * (float)this.charInfo.relics.size())), this.infoY - 60.0F * Settings.scale - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, Settings.scale * (0.01F + (1.0F - 0.019F * (float)this.charInfo.relics.size())), Settings.scale * (0.01F + (1.0F - 0.019F * (float)this.charInfo.relics.size())), 0.0F, 0, 0, 128, 128, false, false);
            }
        }

    }*/

    private LoadoutScreen getLoadoutScreen() {
        return MainMenuPatches.LoadoutScreenField.loadoutScreen.get(CardCrawlGame.mainMenuScreen);
    }
}