package android.otasyn.cardgames.communication.dto;

import android.otasyn.cardgames.communication.dto.gamestate.GameState;
import android.otasyn.cardgames.communication.dto.moves.MovesAvailable;

import java.io.Serializable;
import java.util.Date;

public class GameAction implements Serializable {

    private Game game;
    private int actionNumber;
    private GamePlayer nextActionPlayer;
    private GameState gameState;
    private Date actionDate;
    private MovesAvailable movesAvailable;

    public Game getGame() {
        return game;
    }

    public void setGame(final Game game) {
        this.game = game;
    }

    public int getActionNumber() {
        return actionNumber;
    }

    public void setActionNumber(final int actionNumber) {
        this.actionNumber = actionNumber;
    }

    public GamePlayer getNextActionPlayer() {
        return nextActionPlayer;
    }

    public void setNextActionPlayer(final GamePlayer nextActionPlayer) {
        this.nextActionPlayer = nextActionPlayer;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(final GameState gameState) {
        this.gameState = gameState;
    }

    public java.util.Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(final Date actionDate) {
        this.actionDate = actionDate;
    }

    public MovesAvailable getMovesAvailable() {
        return movesAvailable;
    }

    public void setMovesAvailable(final MovesAvailable movesAvailable) {
        this.movesAvailable = movesAvailable;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameAction that = (GameAction) o;

        if (actionNumber != that.actionNumber) return false;
        if (!game.equals(that.game)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = game.hashCode();
        result = 31 * result + actionNumber;
        return result;
    }

    @Override
    public String toString() {
        return "GameAction{" +
                "game=" + game +
                ", actionNumber=" + actionNumber +
                ", nextActionPlayer=" + nextActionPlayer +
                ", actionDate=" + actionDate +
                '}';
    }
}
