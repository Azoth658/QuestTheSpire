//package questTheSpire.patches;
//
//import basemod.ReflectionHacks;
//import com.badlogic.gdx.graphics.Color;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.evacipated.cardcrawl.modthespire.lib.SpireInstrumentPatch;
//import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
//import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
//import com.megacrit.cardcrawl.core.Settings;
//import com.megacrit.cardcrawl.helpers.FontHelper;
//import com.megacrit.cardcrawl.helpers.Hitbox;
//import com.megacrit.cardcrawl.helpers.ImageMaster;
//import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
//import javassist.CannotCompileException;
//import javassist.expr.ExprEditor;
//import javassist.expr.MethodCall;
//
//public class YeetAscensionText {
//    public static final float X_OFFSET = -350f * Settings.scale; //General offset for all pieces
//    public static final float Y_OFFSET = 0f * Settings.scale; //General offset for all pieces
//    public static final float SEED_TRIM = 50f * Settings.scale; //Cut off part of the seed hitbox if you want
//    public static final float SCRUNCH_AMOUNT = 100f * Settings.scale; //Bring Ascension and Levels closer together
//
//    public static boolean validDrawTarget(Texture t) {
//        return t == ImageMaster.OPTION_TOGGLE || t == ImageMaster.OPTION_TOGGLE_ON;
//    }
//
//    public static float scrunch(float x, boolean right) {
//        return x + X_OFFSET + (right ? SCRUNCH_AMOUNT/2f : -SCRUNCH_AMOUNT/2f);
//    }
//
//    public static float scrunchByColor(float x, Color c) {
//        return x + X_OFFSET + (c != Settings.BLUE_TEXT_COLOR ? SCRUNCH_AMOUNT/2f : -SCRUNCH_AMOUNT/2f);
//    }
//
//    @SpirePatch2(clz = CharacterSelectScreen.class, method = "initialize")
//    public static class YeetMainHitbox {
//        @SpirePostfixPatch
//        public static void patch(CharacterSelectScreen __instance) {
//            Hitbox whyAreYouPrivate = ReflectionHacks.getPrivate(__instance, CharacterSelectScreen.class, "ascensionModeHb");
//            whyAreYouPrivate.move(whyAreYouPrivate.cX+X_OFFSET+SCRUNCH_AMOUNT/2f, whyAreYouPrivate.cY+Y_OFFSET);
//            Hitbox stupidSeedHitbox = ReflectionHacks.getPrivate(__instance, CharacterSelectScreen.class, "seedHb");
//            stupidSeedHitbox.move(stupidSeedHitbox.cX-SEED_TRIM, stupidSeedHitbox.cY);
//            //stupidSeedHitbox.resize(stupidSeedHitbox.width-SEED_TRIM, stupidSeedHitbox.height);
//        }
//    }
//
//    @SpirePatch2(clz = CharacterSelectScreen.class, method = "update")
//    public static class YeetLeftAndRightHitbox {
//        @SpireInstrumentPatch
//        public static ExprEditor patch() {
//            return new ExprEditor() {
//                @Override
//                //Method call is basically the equivalent of a methodcallmatcher of an insert patch, checks the edit method against every method call in the function you#re patching
//                public void edit(MethodCall m) throws CannotCompileException {
//                    //If the method is from the class Hitbox and the method is called move
//                    if (m.getClassName().equals(Hitbox.class.getName()) && m.getMethodName().equals("move")) {
//                        m.replace("{" +
//                                //$1 refers to the first input parameter of the method, in this case the float x, $2 is the second param, y
//                                //TODO You need need to edit the reference location
//                                "$1 = questTheSpire.patches.YeetAscensionText.scrunch($1,false);" +
//                                "$2 = $2 + questTheSpire.patches.YeetAscensionText.Y_OFFSET;" +
//                                //Call the method as normal
//                                "$proceed($$);" +
//                                "}");
//                    }
//                }
//            };
//        }
//    }
//
//    @SpirePatch2(clz = CharacterSelectScreen.class, method = "renderAscensionMode")
//    public static class YeetText {
//        @SpireInstrumentPatch
//        public static ExprEditor patch1() {
//            return new ExprEditor() {
//                @Override
//                //Method call is basically the equivalent of a methodcallmatcher of an insert patch, checks the edit method against every method call in the function you#re patching
//                public void edit(MethodCall m) throws CannotCompileException {
//                    if (m.getClassName().equals(FontHelper.class.getName()) && m.getMethodName().equals("renderFontCentered")) {
//                        m.replace("{" +
//                                //Same dice but x and y are now 4 and 5
//                                //TODO edit reference location
//                                "$4 = questTheSpire.patches.YeetAscensionText.scrunchByColor($4,$6);" +
//                                "$5 = $5 + questTheSpire.patches.YeetAscensionText.Y_OFFSET;" +
//                                "$proceed($$);" +
//                                "}");
//                    }
//                }
//            };
//        }
//        @SpireInstrumentPatch
//        public static ExprEditor patch2() {
//            return new ExprEditor() {
//                @Override
//                //Method call is basically the equivalent of a methodcallmatcher of an insert patch, checks the edit method against every method call in the function you#re patching
//                public void edit(MethodCall m) throws CannotCompileException {
//                    if (m.getClassName().equals(SpriteBatch.class.getName()) && m.getMethodName().equals("draw")) {
//                        m.replace("{" +
//                                //Only do the offset if its the tickbox image pieces
//                                //TODO edit reference location
//                                "if(questTheSpire.patches.YeetAscensionText.validDrawTarget($1)) {" +
//                                "$2 = questTheSpire.patches.YeetAscensionText.scrunch($2,true);" +
//                                "$3 = $3 + questTheSpire.patches.YeetAscensionText.Y_OFFSET;" +
//                                "}" +
//                                //Call the method as normal
//                                "$proceed($$);" +
//                                "}");
//                    }
//                }
//            };
//        }
//    }
//}
