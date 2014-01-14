package android.otasyn.cardgames.communication.dto.moves;

public class BlackjackMovesAvailable implements MovesAvailable {

    private boolean ready;
    private boolean bet;
    private boolean insurance;
    private boolean surrender;
    private boolean hit;
    private boolean stand;
    private boolean doubleDown;
    private boolean split;

    public boolean isReady() {
        return ready;
    }

    public void setReady(final boolean ready) {
        this.ready = ready;
    }

    public boolean isBet() {
        return bet;
    }

    public void setBet(final boolean bet) {
        this.bet = bet;
    }

    public boolean isInsurance() {
        return insurance;
    }

    public void setInsurance(final boolean insurance) {
        this.insurance = insurance;
    }

    public boolean isSurrender() {
        return surrender;
    }

    public void setSurrender(final boolean surrender) {
        this.surrender = surrender;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(final boolean hit) {
        this.hit = hit;
    }

    public boolean isStand() {
        return stand;
    }

    public void setStand(final boolean stand) {
        this.stand = stand;
    }

    public boolean isDoubleDown() {
        return doubleDown;
    }

    public void setDoubleDown(final boolean doubleDown) {
        this.doubleDown = doubleDown;
    }

    public boolean isSplit() {
        return split;
    }

    public void setSplit(final boolean split) {
        this.split = split;
    }
}
