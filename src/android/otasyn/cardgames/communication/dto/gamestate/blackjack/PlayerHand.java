package android.otasyn.cardgames.communication.dto.gamestate.blackjack;

import android.otasyn.cardgames.utility.enumeration.Card;

import java.util.LinkedList;
import java.util.List;

public class PlayerHand {

    private int bet;
    private int doubleDown;
    private List<Card> hand;

    public int getBet() {
        return bet;
    }

    public void setBet(final int bet) {
        this.bet = bet;
    }

    public int getDoubleDown() {
        return doubleDown;
    }

    public void setDoubleDown(final int doubleDown) {
        this.doubleDown = doubleDown;
    }

    public List<Card> getHand() {
        if (hand == null) {
            hand = new LinkedList<Card>();
        }
        return hand;
    }

    public void setHand(final List<Card> hand) {
        this.hand = hand;
    }
}
