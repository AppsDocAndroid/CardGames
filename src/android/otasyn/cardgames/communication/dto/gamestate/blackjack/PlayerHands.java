package android.otasyn.cardgames.communication.dto.gamestate.blackjack;

import java.util.LinkedList;
import java.util.List;

public class PlayerHands {

    private List<PlayerHand> hands;
    private int handTurn;
    private Integer insurance;
    private boolean surrendered;

    public List<PlayerHand> getHands() {
        if (hands == null) {
            hands = new LinkedList<PlayerHand>();
        }
        return hands;
    }

    public void setHands(final List<PlayerHand> hands) {
        this.hands = hands;
    }

    public int getHandTurn() {
        return handTurn;
    }

    public void setHandTurn(final int handTurn) {
        this.handTurn = handTurn;
    }

    public Integer getInsurance() {
        return insurance;
    }

    public void setInsurance(final Integer insurance) {
        this.insurance = insurance;
    }

    public boolean isSurrendered() {
        return surrendered;
    }

    public void setSurrendered(final boolean surrendered) {
        this.surrendered = surrendered;
    }
}
