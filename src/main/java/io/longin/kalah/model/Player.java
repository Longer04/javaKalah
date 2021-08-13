package io.longin.kalah.model;

public enum Player {

    PLAYER_ONE(7),
    PLAYER_TWO(14);

    private final int basePitIndex;

    Player(final int basePitIndex) {
        this.basePitIndex = basePitIndex;
    }

    public int getBasePitIndex() {
        return basePitIndex;
    }

}
