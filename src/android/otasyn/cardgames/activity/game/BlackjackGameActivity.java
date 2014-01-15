/**
 * Patrick John Haskins
 * Zachary Evans
 * CS7020 - Term Project
 */
package android.otasyn.cardgames.activity.game;

import android.graphics.Typeface;
import android.otasyn.cardgames.communication.dto.GameAction;
import android.otasyn.cardgames.communication.dto.gamestate.BlackjackState;
import android.otasyn.cardgames.communication.dto.gamestate.blackjack.PlayerHand;
import android.otasyn.cardgames.communication.dto.gamestate.blackjack.PlayerHands;
import android.otasyn.cardgames.entity.PositionBox;
import android.otasyn.cardgames.scene.CardGameScene;
import android.otasyn.cardgames.sprite.CardSprite;
import android.otasyn.cardgames.utility.TextureUtility;
import android.otasyn.cardgames.utility.enumeration.Card;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;

import java.util.Queue;

public class BlackjackGameActivity extends CardGameActivity {

    private final static float POSITION_BOX_WIDTH = 125;
    private final static float POSITION_BOX_HEIGHT = 200;

    private final static float DECK_PADDING = 15;

    private final float CARD_SPACING = 12;

    private Sprite deckSprite;
    private float deckX;
    private float deckY;
    private float deckWidth;
    private float deckHeight;

    private Font boldFont;
    private Font deckSizeFont;

    private Text deckSizeText;

    private PositionBox[] positionBoxes;

    @Override
    protected void onBeforeCreateEngineOptions() {
        setScreenOrientation(ScreenOrientation.LANDSCAPE_FIXED);
        setAlsoUpdateOnCurrentUserTurn(true);
    }

    @Override
    protected void onCreateCardGameResources() {
        setCardTextureRegions(TextureUtility.loadCards92x128(this));

        boldFont = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256,
                Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
        boldFont.load();

        deckSizeFont = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256,
                Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
        deckSizeFont.load();
    }

    @Override
    protected void onCreateCardGameScene(final CardGameScene scene) {
        createAndDrawPositionBoxes(scene);

        deckX = (getCameraWidth() / 2f) - 200f;
        deckY = 25f;
        deckWidth = getRedBack().getWidth();
        deckHeight = getRedBack().getHeight();

        displayAll();
    }

    private void createAndDrawPositionBoxes(final CardGameScene scene) {
        float arcCenterX = getCameraWidth() / 2f;
        float arcCenterY = -500f;
        float arcRadius = 850f;
        double spacingAngle = .082d * Math.PI;
        double currentAngle = 0;

        positionBoxes = new PositionBox[getGame().getPlayers().size()];
        for (int n = 0; n < getGame().getPlayers().size(); n++) {
            if (n == 0) {
                currentAngle -= spacingAngle * ((getGame().getPlayers().size() - 1) / 2d);
            } else {
                currentAngle += spacingAngle;
            }

            float pbX = arcCenterX + (arcRadius * (float) Math.sin(currentAngle));
            float pbY = arcCenterY + (arcRadius * (float) Math.cos(currentAngle));

            positionBoxes[n] = createPositionBox(pbX, pbY, 0);
            scene.attachChild(positionBoxes[n]);
        }
    }

    private PositionBox createPositionBox(final float x, final float y, final float angle) {
        return new PositionBox(x, y, POSITION_BOX_WIDTH, POSITION_BOX_HEIGHT, angle, getVertexBufferObjectManager());
    }

    @Override
    protected void onLatestActionUpdated(final GameAction latestAction) {
        if (isGameLoaded() && isGameRunning()) {
            Debug.d("CardGames", "onLatestActionUpdated");
            displayAll();
        }
    }

    private BlackjackState getGameState() {
        return (BlackjackState) getLatestAction().getGameState();
    }

    private void displayAll() {
        clearCardSprites();
        displayDeck();
        displayDealerHand();
        displayPlayersHands();
    }

    private void displayDeck() {
        if (deckSprite == null) {
            if (getRedBack() != null) {
                Rectangle deckPositionBox = new Rectangle(deckX - DECK_PADDING, deckY - DECK_PADDING,
                                                          deckWidth + (2 * DECK_PADDING),
                                                          deckHeight + (2 * DECK_PADDING),
                                                          getVertexBufferObjectManager());
                deckPositionBox.setColor(Color.BLACK);
                getCardGameScene().attachChild(deckPositionBox);

                deckSprite = new Sprite(deckX, deckY, getRedBack(), this.getVertexBufferObjectManager());
                getCardGameScene().attachChild(deckSprite);
            }
        }
        try {
            Queue<Card> deck = getGameState().getDeck();
            if (deck.size() > 0) {
                Debug.d("CardGames", "Deck is visible.");
                deckSprite.setVisible(true);
            } else {
                Debug.d("CardGames", "Deck is not visible.");
                deckSprite.setVisible(false);
            }
            displayDeckSize(deck);
        } catch (NullPointerException e) {
            Debug.d("CardGames", e);
            displayDeckSize(null);
        }
    }

    private void displayDeckSize(final Queue<Card> deck) {
        if (deckSizeText == null) {
            deckSizeText = new Text(deckX, deckY + ((deckHeight - deckSizeFont.getLineHeight()) / 2), this.deckSizeFont,
                    "", 3, new TextOptions(HorizontalAlign.CENTER), getVertexBufferObjectManager());
            getCardGameScene().attachChild(deckSizeText);
        }
        deckSizeText.setText(deck != null ? String.valueOf(deck.size()) : "");
        deckSizeText.setPosition(deckX + ((deckWidth - deckSizeText.getWidth()) / 2), deckSizeText.getY());
    }

    private void displayPlayersHands() {
        for (int n = 0; n < getGame().getTurnOrder().size(); n++) {
            PlayerHands playerHands = getGameState().getPlayersHands().get(getGame().getTurnOrder().get(n));
            displayPlayerHands(n, playerHands);
        }
    }

    private void displayPlayerHands(final int turnNumber, final PlayerHands playerHands) {
        PositionBox box = positionBoxes[turnNumber];
        float boxCenterX = box.getX();
        float boxTopY = box.getY();
        float boxWidth = box.getWidth();
        float boxHeight = box.getHeight();

        float cardWidth = getRedBack().getWidth();
        float cardHeight = getRedBack().getHeight();

        float startY;
        if (playerHands.getHands().size() > 2) {
            startY = boxTopY + (boxHeight / 2);
        } else {
            startY = boxTopY + ((boxHeight - cardHeight) / 2);
        }
        for (int n = 0; n < playerHands.getHands().size(); n+=2) {
            boolean twoColumns = playerHands.getHands().size() > 1;
            if (twoColumns) {
                displayHand(playerHands.getHands().get(n), boxCenterX - (boxWidth / 2), startY, CARD_SPACING);
                displayHand(playerHands.getHands().get(n+1), boxCenterX, startY, CARD_SPACING);
            } else {
                displayHand(playerHands.getHands().get(n),
                            boxCenterX - ((cardWidth + CARD_SPACING) / 2), startY, CARD_SPACING);
            }
            startY -= cardHeight + 20;
        }
    }

    private void displayHand(PlayerHand playerHand, final float startX, final float startY, final float space) {
        for (int c = 0; c < playerHand.getHand().size(); c++) {
            Card card = playerHand.getHand().get(c);
            CardSprite cardSprite = getCardSprite(card);

            cardSprite.setX(startX + (c * space));
            cardSprite.setY(startY + (c * space));
            cardSprite.showFace();
            cardSprite.setVisible(true);
            getCardGameScene().moveCardSpriteToFront(cardSprite);
        }
    }

    private void displayDealerHand() {
        float cardX = deckX + deckWidth + 20;
        float cardY = deckY;
        if (getGameState().getDealerHand().getFaceDownCard() != null) {
            CardSprite faceDownCardSprite = getCardSprite(getGameState().getDealerHand().getFaceDownCard());
            faceDownCardSprite.setX(cardX);
            faceDownCardSprite.setY(cardY);
            faceDownCardSprite.showBack();
            faceDownCardSprite.setVisible(true);
            getCardGameScene().moveCardSpriteToFront(faceDownCardSprite);
            cardX += CARD_SPACING;
        }
        for (Card card : getGameState().getDealerHand().getFaceUpCards()) {
            CardSprite faceUpCardSprite = getCardSprite(card);
            faceUpCardSprite.setX(cardX);
            faceUpCardSprite.setY(cardY);
            faceUpCardSprite.showFace();
            faceUpCardSprite.setVisible(true);
            getCardGameScene().moveCardSpriteToFront(faceUpCardSprite);
            cardX += CARD_SPACING;
        }
    }
}
