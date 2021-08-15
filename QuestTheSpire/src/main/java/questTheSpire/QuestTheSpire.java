package questTheSpire;

import basemod.*;
import basemod.eventUtil.AddEventParams;
import basemod.eventUtil.EventUtils;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.Ironclad;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.relics.Sozu;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import questTheSpire.cards.AbstractDefaultCard;
import questTheSpire.events.*;
import questTheSpire.relics.FairyBlessing;
import questTheSpire.util.IDCheckDontTouchPls;
import questTheSpire.util.TextureLoader;
import questTheSpire.variables.DefaultCustomVariable;
import questTheSpire.variables.DefaultSecondMagicNumber;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import static com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.IRONCLAD;

@SpireInitializer
public class QuestTheSpire implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        PostDeathSubscriber,
        PostInitializeSubscriber {

    public static final Logger logger = LogManager.getLogger(QuestTheSpire.class.getName());
    private static String modID;

    // Mod-settings settings. This is if you want an on/off savable button
    public static Properties questTheSpireDefaultSettings = new Properties();
    public static final String ENABLE_EVENT_SETTINGS = "enableEvents";
    public static boolean enableEvents = true; // The boolean we'll be setting on/off (true/false)
    public static final String ENABLE_CARD_SETTINGS = "enableCards";
    public static boolean enableCards = true; // The boolean we'll be setting on/off (true/false)

    //This is for the in-game mod settings panel.
    private static final String MODNAME = "Quest The Spire";
    private static final String AUTHOR = "Azoth658"; // And pretty soon - You!
    private static final String DESCRIPTION = "An attempt at offering a Rogue Lite experience.";

    //Permanent progression fields
    public static Properties questTheSpireStats = new Properties();
    public static Properties questTheSpireCharacterStats = new Properties();
    public static int reincarnations = 0;
    public static int monsterkills = 0;
    public static int elitekills = 0;
    public static int bosskills = 0;
    public static int heartkills = 0;
    public static int eventsvisited = 0;

    public static int Experience = 0; // current run experience
    public static int LevelRequirement = 0; // requirement to hit next level / prestige
    public static int PrestigeCost = 10000; // The cost for each prestige level
    public static int OverFlowExperience; // total level experience
    public static int Level = 1;
    public static int PrestigeLevel = 0;

    public static int perkpoints = 0;
    public static int maxhppoints = 0;
    public static int strpoints = 0;
    public static int dexpoints = 0;
    public static int focuspoints = 0;
    public static int goldpoints = 0;
    public static int potionpoints = 0;

    public static int aspectsmax = 0;
    public static int aspectscurrent = 0;


    // =============== INPUT TEXTURE LOCATION =================
    
    //Mod Badge - A small icon that appears in the mod settings menu next to your mod.
    public static final String BADGE_IMAGE = "questTheSpireResources/images/Badge.png";
    
    // =============== MAKE IMAGE PATHS =================
    
    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }

    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }
    
    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }
    
    public static String makeOrbPath(String resourcePath) {
        return getModID() + "Resources/images/orbs/" + resourcePath;
    }
    
    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }
    
    public static String makeEventPath(String resourcePath) {
        return getModID() + "Resources/images/events/" + resourcePath;
    }
    
    // =============== /MAKE IMAGE PATHS/ =================
    
    // =============== /INPUT TEXTURE LOCATION/ =================
    
    
    // =============== SUBSCRIBE, INITIALIZE =================
    
    public QuestTheSpire() {
        logger.info("Subscribe to BaseMod hooks");

        BaseMod.subscribe(this);
        setModID("questTheSpire");

        logger.info("Done subscribing");
        logger.info("Adding mod settings");

        // This loads the mod settings.
        // The actual mod Button is added below in receivePostInitialize()

        questTheSpireDefaultSettings.setProperty(ENABLE_EVENT_SETTINGS, "FALSE"); // This is the default setting. It's actually set...
        questTheSpireDefaultSettings.setProperty(ENABLE_CARD_SETTINGS, "FALSE");
            try {
                SpireConfig config = new SpireConfig("QuestTheSpire", "QuestTheSpireConfig", questTheSpireDefaultSettings);
                config.load(); // Load the setting and set the boolean to equal it
                enableEvents = config.getBool(ENABLE_EVENT_SETTINGS);
                enableCards = config.getBool(ENABLE_CARD_SETTINGS);
            } catch (Exception e) {
                e.printStackTrace();
            }

        logger.info("Done adding mod settings");


        //Load Global Progression
        logger.info("Loading global progression");

        try {
            SpireConfig config = new SpireConfig("QuestTheSpire", "QuestTheSpireStats", questTheSpireStats);
            config.load();
            reincarnations = config.getInt("reincarnations");
            monsterkills = config.getInt("monsterkills");
            elitekills = config.getInt("elitekills");
            bosskills = config.getInt("bosskills");
            heartkills = config.getInt("heartkills");
            eventsvisited = config.getInt("eventsvisited");
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.info("Done loading global progression");
    }

    // ====== NO EDIT AREA ======
    // DON'T TOUCH THIS STUFF. IT IS HERE FOR STANDARDIZATION BETWEEN MODS AND TO ENSURE GOOD CODE PRACTICES.
    // IF YOU MODIFY THIS I WILL HUNT YOU DOWN AND DOWNVOTE YOUR MOD ON WORKSHOP
    
    public static void setModID(String ID) { // DON'T EDIT
        Gson coolG = new Gson(); // EY DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i hate u Gdx.files
        InputStream in = QuestTheSpire.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THIS ETHER
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // OR THIS, DON'T EDIT IT
        logger.info("You are attempting to set your mod ID as: " + ID); // NO WHY
        if (ID.equals(EXCEPTION_STRINGS.DEFAULTID)) { // DO *NOT* CHANGE THIS ESPECIALLY, TO EDIT YOUR MOD ID, SCROLL UP JUST A LITTLE, IT'S JUST ABOVE
            throw new RuntimeException(EXCEPTION_STRINGS.EXCEPTION); // THIS ALSO DON'T EDIT
        } else if (ID.equals(EXCEPTION_STRINGS.DEVID)) { // NO
            modID = EXCEPTION_STRINGS.DEFAULTID; // DON'T
        } else { // NO EDIT AREA
            modID = ID; // DON'T WRITE OR CHANGE THINGS HERE NOT EVEN A LITTLE
        } // NO
        logger.info("Success! ID is " + modID); // WHY WOULD U WANT IT NOT TO LOG?? DON'T EDIT THIS.
    } // NO
    
    public static String getModID() { // NO
        return modID; // DOUBLE NO
    } // NU-UH
    
    private static void pathCheck() { // ALSO NO
        Gson coolG = new Gson(); // NOPE DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i still hate u btw Gdx.files
        InputStream in = QuestTheSpire.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THISSSSS
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // NAH, NO EDIT
        String packageName = QuestTheSpire.class.getPackage().getName(); // STILL NO EDIT ZONE
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources"); // PLEASE DON'T EDIT THINGS HERE, THANKS
        if (!modID.equals(EXCEPTION_STRINGS.DEVID)) { // LEAVE THIS EDIT-LESS
            if (!packageName.equals(getModID())) { // NOT HERE ETHER
                throw new RuntimeException(EXCEPTION_STRINGS.PACKAGE_EXCEPTION + getModID()); // THIS IS A NO-NO
            } // WHY WOULD U EDIT THIS
            if (!resourcePathExists.exists()) { // DON'T CHANGE THIS
                throw new RuntimeException(EXCEPTION_STRINGS.RESOURCE_FOLDER_EXCEPTION + getModID() + "Resources"); // NOT THIS
            }// NO
        }// NO
    }// NO
    
    // ====== YOU CAN EDIT AGAIN ======
    
    
    public static void initialize() {
        logger.info("========================= Initializing Default Mod. Hi. =========================");
        QuestTheSpire defaultmod = new QuestTheSpire();
        logger.info("========================= /Default Mod Initialized. Hello World./ =========================");
    }


    // =============== POST-INITIALIZE =================
    
    @Override
    public void receivePostInitialize() {
        logger.info("Loading badge image and mod options");
        
        // Load the Mod Badge
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);
        
        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();
        
        // Create the on/off button:
        ModLabeledToggleButton enableNormalsButton = new ModLabeledToggleButton("Check to disable mod events. Requires restart to activate.",
                350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, // Position (trial and error it), color, font
                enableEvents, // Boolean it uses
                settingsPanel, // The mod panel in which this button will be in
                (label) -> {}, // thing??????? idk
                (button) -> { // The actual button:
            
            enableEvents = button.enabled; // The boolean true/false will be whether the button is enabled or not
            try {
                // And based on that boolean, set the settings and save them
                SpireConfig config = new SpireConfig("QuestTheSpire", "QuestTheSpireConfig", questTheSpireDefaultSettings);
                config.setBool(ENABLE_EVENT_SETTINGS, enableEvents);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        ModLabeledToggleButton enableNormalsButton2 = new ModLabeledToggleButton("Check to disable mod cards. Requires restart to activate.",
                350.0f, 650.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, // Position (trial and error it), color, font
                enableCards, // Boolean it uses
                settingsPanel, // The mod panel in which this button will be in
                (label) -> {},
                (button) -> { // The actual button:

                    enableCards = button.enabled; // The boolean true/false will be whether the button is enabled or not
                    try {
                        // And based on that boolean, set the settings and save them
                        SpireConfig config = new SpireConfig("QuestTheSpire", "QuestTheSpireConfig", questTheSpireDefaultSettings);
                        config.setBool(ENABLE_CARD_SETTINGS, enableCards);
                        config.save();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        
        settingsPanel.addUIElement(enableNormalsButton); // Add the button to the settings panel. Button is a go.
        settingsPanel.addUIElement(enableNormalsButton2); // Add the button to the settings panel. Button is a go.
        
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        
        // =============== EVENTS =================

        BaseMod.addEvent(wanderingMerchant.ID, wanderingMerchant.class, Exordium.ID);
        BaseMod.addEvent(druidEvent.ID, druidEvent.class, Exordium.ID);
        BaseMod.addEvent(fairyEvent.ID, fairyEvent.class, TheCity.ID);
        BaseMod.addEvent(tavernEvent.ID, tavernEvent.class);
        BaseMod.addEvent(new AddEventParams.Builder(dragonHoard.ID, dragonHoard.class).eventType(EventUtils.EventType.NORMAL).bonusCondition(() -> AbstractDungeon.player.gold > 1000).create());
        BaseMod.addEvent(new AddEventParams.Builder(alchemyLab.ID, alchemyLab.class).eventType(EventUtils.EventType.NORMAL).bonusCondition(() -> AbstractDungeon.player.potionSlots > 0 && !AbstractDungeon.player.hasRelic(Sozu.ID)).create());


        // Add the events for Azoth's Reliquariam
        if(Loader.isModLoaded("Azoth")) {
            //BaseMod.addEvent(new AddEventParams.Builder(wanderingMerchant.ID, wanderingMerchant.class).eventType(EventUtils.EventType.NORMAL).playerClass(AbstractPlayer.PlayerClass.THE_SILENT).bonusCondition(() -> AbstractDungeon.player.hasRelic(SnakeRing.ID)).create());

        }

        // =============== /EVENTS/ =================
        logger.info("Done loading badge Image and mod options");




        //LOAD CHARACTER SPECIFIC STATS
        try {
            for (AbstractPlayer.PlayerClass pc : AbstractPlayer.PlayerClass.values()) {

            SpireConfig config = new SpireConfig("QuestTheSpire", pc.toString()+"_QuestTheSpire_Stats", questTheSpireCharacterStats);
            config.load();
            Level = config.getInt("Level");
            OverFlowExperience = config.getInt("OverFlowExperience");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    
    // =============== / POST-INITIALIZE/ =================
    
    // ================ ADD POTIONS ===================
    
   public void receiveEditPotions() {
       /*  logger.info("Beginning to edit potions");
        
        // Class Specific Potion. If you want your potion to not be class-specific,
        // just remove the player class at the end (in this case the "TheDefaultEnum.THE_DEFAULT".
        // Remember, you can press ctrl+P inside parentheses like addPotions)
        BaseMod.addPotion(PlaceholderPotion.class, PLACEHOLDER_POTION_LIQUID, PLACEHOLDER_POTION_HYBRID, PLACEHOLDER_POTION_SPOTS, PlaceholderPotion.POTION_ID, TheDefault.Enums.THE_DEFAULT);
        
        logger.info("Done editing potions");
     */
    }

    
    // ================ /ADD POTIONS/ ===================
    
    
    // ================ ADD RELICS ===================


    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");

        // This adds a character specific relic. Only when you play with the mentioned color, will you get this relic.
        //BaseMod.addRelicToCustomPool(new PlaceholderRelic(), TheDefault.Enums.COLOR_GRAY);

        BaseMod.addRelic(new FairyBlessing(), RelicType.SHARED);
        UnlockTracker.markRelicAsSeen(FairyBlessing.ID);

        logger.info("Done adding relics!");

    }
    
    // ================ /ADD RELICS/ ===================




    // ================ ADD CARDS ===================
    
    @Override
    public void receiveEditCards() {


        logger.info("Adding variables");

        //Ignore this
        pathCheck();
        // Add the Custom Dynamic Variables
        logger.info("Add variables");
        // Add the Custom Dynamic variables
        BaseMod.addDynamicVariable(new DefaultCustomVariable());
        BaseMod.addDynamicVariable(new DefaultSecondMagicNumber());
        
        logger.info("Adding cards");
        // Add the cards

        new AutoAdd("QuestTheSpire") // ${project.artifactId}
            .packageFilter(AbstractDefaultCard.class) // filters to any class in the same package as AbstractDefaultCard, nested packages included
            .setDefaultSeen(true)
            .cards();

        // .setDefaultSeen(true) unlocks the cards in the library before finding them.

        logger.info("Done adding cards!");
    }

    
    // ================ /ADD CARDS/ ===================



    
    // ================ LOAD THE TEXT ===================
    
    @Override
    public void receiveEditStrings() {
        logger.info("You seeing this?");
        logger.info("Beginning to edit strings for mod with ID: " + getModID());
        
        // CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class,
                getModID() + "Resources/localization/eng/DefaultMod-Card-Strings.json");
        
        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                getModID() + "Resources/localization/eng/DefaultMod-Power-Strings.json");
        
        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                getModID() + "Resources/localization/eng/DefaultMod-Relic-Strings.json");
        
        // Event Strings
        BaseMod.loadCustomStringsFile(EventStrings.class,
                getModID() + "Resources/localization/eng/DefaultMod-Event-Strings.json");
        
        // PotionStrings
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                getModID() + "Resources/localization/eng/DefaultMod-Potion-Strings.json");
        
        // CharacterStrings
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                getModID() + "Resources/localization/eng/DefaultMod-Character-Strings.json");
        
        // OrbStrings
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                getModID() + "Resources/localization/eng/DefaultMod-Orb-Strings.json");
        
        logger.info("Done edittting strings");
    }
    
    // ================ /LOAD THE TEXT/ ===================
    
    // ================ LOAD THE KEYWORDS ===================

    @Override
    public void receiveEditKeywords() {
        // Keywords on cards are supposed to be Capitalized, while in Keyword-String.json they're lowercase
        //
        // Multiword keywords on cards are done With_Underscores
        //
        // If you're using multiword keywords, the first element in your NAMES array in your keywords-strings.json has to be the same as the PROPER_NAME.
        // That is, in Card-Strings.json you would have #yA_Long_Keyword (#y highlights the keyword in yellow).
        // In Keyword-Strings.json you would have PROPER_NAME as A Long Keyword and the first element in NAMES be a long keyword, and the second element be a_long_keyword
        
        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/eng/DefaultMod-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);
        
        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(getModID().toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
                //  getModID().toLowerCase() makes your keyword mod specific (it won't show up in other cards that use that word)
            }
        }
    }
    
    // ================ /LOAD THE KEYWORDS/ ===================    



    // this adds "ModName:" before the ID of any card/relic/power etc.
    // in order to avoid conflicts if any other mod uses the same ID.
    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }


    // When the player dies store number of restarts for meta events
    @Override
    public void receivePostDeath() {
        reincarnations++;
        try {
            SpireConfig config = new SpireConfig("QuestTheSpire", "QuestTheSpireStats", questTheSpireStats);
            config.setInt("reincarnations", reincarnations);
            config.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
                AbstractPlayer.PlayerClass pc = AbstractDungeon.player.chosenClass;
                SpireConfig config = new SpireConfig("QuestTheSpire", pc.toString() + "_QuestTheSpire_Stats", questTheSpireCharacterStats);
                config.setInt("Level", Level);
                config.setInt("OverFlowExperience", OverFlowExperience);
                config.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
