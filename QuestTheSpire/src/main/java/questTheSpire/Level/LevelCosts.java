package questTheSpire.Level;

import static questTheSpire.QuestTheSpire.*;

public class LevelCosts {
    public static int levelcost(int LevelRequirement) {
        switch(Level) {
            case 1:
                return 500;
            case 2:
                return 750;
            case 3:
                return 1000;
            case 4:
                return 1250;
            case 5:
                return 1500;
            case 6:
                return 2000;
            case 7:
                return 2250;
            case 8:
                return 2500;
            case 9:
                return 2750;
            case 10:
                return 3000;
            case 11:
                return 3250;
            case 12:
                return 3500;
            case 13:
                return 3750;
            case 14:
                return 4000;
            case 15:
                return 4250;
            case 16:
                return 4500;
            case 17:
                return 4750;
            case 18:
                return 5000;
            case 19:
                return 5250;
            case 20:
                return PrestigeCost;
            default:
                return LevelRequirement + 250;
        }
    }
}
