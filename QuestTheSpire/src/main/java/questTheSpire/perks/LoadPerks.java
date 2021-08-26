package questTheSpire.perks;

import basemod.interfaces.PostInitializeSubscriber;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class LoadPerks implements PostInitializeSubscriber {
    public boolean perkloaded;
    public int hpPerk;
    public int goldPerk;
    public int strPerk;
    public int dexPerk;
    public int focusPerk;

    @Override
    public void receivePostInitialize() {
        AbstractDungeon.player.gainGold(2000);
    }
}
