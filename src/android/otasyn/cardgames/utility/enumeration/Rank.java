package android.otasyn.cardgames.utility.enumeration;

public enum Rank {
    NONE(0,0),
    ACE(11,1),
    TWO(2,2),
    THREE(3,3),
    FOUR(4,4),
    FIVE(5,5),
    SIX(6,6),
    SEVEN(7,7),
    EIGHT(8,8),
    NINE(9,9),
    TEN(10,10),
    JACK(10,10),
    QUEEN(10,10),
    KING(10,10);

    private final int maxBlackjackValue;
    private final int minBlackjackValue;

    private Rank(final int maxBlackjackValue, final int minBlackjackValue) {
        this.maxBlackjackValue = maxBlackjackValue;
        this.minBlackjackValue = minBlackjackValue;
    }

    public int getMaxBlackjackValue() {
        return maxBlackjackValue;
    }

    public int getMinBlackjackValue() {
        return minBlackjackValue;
    }
}
