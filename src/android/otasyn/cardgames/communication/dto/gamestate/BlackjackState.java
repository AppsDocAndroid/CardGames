package android.otasyn.cardgames.communication.dto.gamestate;

import android.otasyn.cardgames.communication.dto.GamePlayer;
import android.otasyn.cardgames.communication.dto.gamestate.blackjack.DealerHand;
import android.otasyn.cardgames.communication.dto.gamestate.blackjack.PlayerHands;
import android.otasyn.cardgames.utility.enumeration.Card;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class BlackjackState extends GameState {

    private DealerHand dealerHand;
    private Map<GamePlayer, PlayerHands> playersHands;
    private Map<GamePlayer, Integer> playersBanks;
    private Map<GamePlayer, Boolean> playersReadyForNext;
    private boolean handFinished;
    private LinkedList<Card> discardPile;

    public DealerHand getDealerHand() {
        if (dealerHand == null) {
            dealerHand = new DealerHand();
        }
        return dealerHand;
    }

    public void setDealerHand(final DealerHand dealerHand) {
        this.dealerHand = dealerHand;
    }

    public java.util.Map<GamePlayer, PlayerHands> getPlayersHands() {
        if (playersHands == null) {
            playersHands = new HashMap<GamePlayer, PlayerHands>();
        }
        return playersHands;
    }

    public void setPlayersHands(final Map<GamePlayer, PlayerHands> playersHands) {
        this.playersHands = playersHands;
    }

    public java.util.Map<GamePlayer, Integer> getPlayersBanks() {
        if (playersBanks == null) {
            playersBanks = new HashMap<GamePlayer, Integer>();
        }
        return playersBanks;
    }

    public void setPlayersBanks(final Map<GamePlayer, Integer> playersBanks) {
        this.playersBanks = playersBanks;
    }

    public java.util.Map<GamePlayer, Boolean> getPlayersReadyForNext() {
        if (playersReadyForNext == null) {
            playersReadyForNext = new HashMap<GamePlayer, Boolean>();
        }
        return playersReadyForNext;
    }

    public void setPlayersReadyForNext(final Map<GamePlayer, Boolean> playersReadyForNext) {
        this.playersReadyForNext = playersReadyForNext;
    }

    public boolean isHandFinished() {
        return handFinished;
    }

    public void setHandFinished(final boolean handFinished) {
        this.handFinished = handFinished;
    }

    public java.util.LinkedList<Card> getDiscardPile() {
        if (discardPile == null) {
            discardPile = new LinkedList<Card>();
        }
        return discardPile;
    }

    public void setDiscardPile(final LinkedList<Card> discardPile) {
        this.discardPile = discardPile;
    }
}
