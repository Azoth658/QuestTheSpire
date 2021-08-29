package questTheSpire.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.EmptyRoom;

public class CurrentRoomHack {
    public static boolean roomHack = false;
    private static final AbstractRoom EMPTY_ROOM = new EmptyRoom();

    //Stop adding relics from crashing
    @SpirePatch(clz = AbstractDungeon.class, method = "getCurrRoom")
    public static class GimmeRoom {
        @SpirePrefixPatch
        public static SpireReturn<?> withoutCrashingPls() {
            if (roomHack) {
                return SpireReturn.Return(EMPTY_ROOM);
            }
            return SpireReturn.Continue();
        }
    }
}
