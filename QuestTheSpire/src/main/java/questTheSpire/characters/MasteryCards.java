package questTheSpire.characters;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.CardLibrary;

public class MasteryCards {
    public static class Enums {
        @SpireEnum
        public static AbstractPlayer.PlayerClass MASTERY;
        @SpireEnum(name = "MASTERY") // These two HAVE to have the same absolutely identical name.
        public static AbstractCard.CardColor COLOR_MASTERY;
        @SpireEnum(name = "MASTERY") @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_MASTERY;
    }
}
