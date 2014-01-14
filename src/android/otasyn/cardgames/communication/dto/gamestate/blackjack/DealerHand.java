package android.otasyn.cardgames.communication.dto.gamestate.blackjack;

import android.otasyn.cardgames.utility.enumeration.Card;

import java.util.ArrayList;
import java.util.List;

public class DealerHand {

    private Card faceDownCard;
    private List<Card> faceUpCards;

    public Card getFaceDownCard() {
        return faceDownCard;
    }

    public void setFaceDownCard(final Card faceDownCard) {
        this.faceDownCard = faceDownCard;
    }

    public List<Card> getFaceUpCards() {
        if (faceUpCards == null) {
            faceUpCards = new ArrayList<Card>();
        }
        return faceUpCards;
    }

    public void setFaceUpCards(final List<Card> faceUpCards) {
        this.faceUpCards = faceUpCards;
    }
}
