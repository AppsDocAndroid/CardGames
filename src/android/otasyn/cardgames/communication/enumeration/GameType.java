package android.otasyn.cardgames.communication.enumeration;

public enum GameType {
    UNKNOWN(0, "Unknown", 0, Integer.MAX_VALUE),
    BLACKJACK(3, "Blackjack", 1, 6),
    FIVES(2, "Fives", 2, 2),
    FREESTYLE(1, "Freestyle", 2, 2);

    private final int id;
    private final String name;
    private final int minPlayers;
    private final int maxPlayers;

    private GameType(final int id, final String name, final int minPlayers, final int maxPlayers) {
        this.id = id;
        this.name = name;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public static GameType findGameType(final int id) {
        for (GameType gt : GameType.values()) {
            if (gt.getId() == id) {
                return gt;
            }
        }
        return UNKNOWN;
    }
}
