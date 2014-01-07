/**
 * Patrick John Haskins
 * Zachary Evans
 * CS7020 - Term Project
 */
package android.otasyn.cardgames.activity.game;

import android.graphics.Typeface;
import android.otasyn.cardgames.entity.PositionBox;
import android.otasyn.cardgames.scene.CardGameScene;
import android.otasyn.cardgames.utility.TextureUtility;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;

public class BlackjackGameActivity extends CardGameActivity {

    private final static float POSITION_BOX_WIDTH = 125;
    private final static float POSITION_BOX_HEIGHT = 200;

    private final static float DECK_PADDING = 15;

    private ButtonSprite deckButton;

    private Font boldFont;

    private PositionBox[] positionBoxes;

    @Override
    protected void onBeforeCreateEngineOptions() {
        setScreenOrientation(ScreenOrientation.LANDSCAPE_FIXED);
    }

    @Override
    protected void onCreateCardGameResources() {
        setCardTextureRegions(TextureUtility.loadCards92x128(this));

        boldFont = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256,
                Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
        boldFont.load();
    }

    @Override
    protected void onCreateCardGameScene(final CardGameScene scene) {
        createAndDrawPositionBoxes(scene);

        displayAll();
    }

    private void createAndDrawPositionBoxes(final CardGameScene scene) {
        float arcCenterX = getCameraWidth() / 2f;
        float arcCenterY = -500f;
        float arcRadius = 850f;
        double spacingAngle = .065d * Math.PI;
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

            /*Rectangle test = new Rectangle(pbX - 10f, pbY - 10f, 20f, 20f, getVertexBufferObjectManager());
            test.setColor(Color.RED);
            scene.attachChild(test);*/
        }
    }

    private PositionBox createPositionBox(final float x, final float y, final float angle) {
        return new PositionBox(x, y, POSITION_BOX_WIDTH, POSITION_BOX_HEIGHT, angle, getVertexBufferObjectManager());
    }

    private void displayAll() {
        displayDeck();
    }

    private void displayDeck() {
        if (deckButton == null) {
            if (getRedBack() != null) {
                float deckX = (getCameraWidth() / 2f) - 200f;
                float deckY = 25f;

                Rectangle deckPositionBox = new Rectangle(deckX - DECK_PADDING, deckY - DECK_PADDING,
                                                          getRedBack().getWidth() + (2 * DECK_PADDING),
                                                          getRedBack().getHeight() + (2 * DECK_PADDING),
                                                          getVertexBufferObjectManager());
                deckPositionBox.setColor(Color.BLACK);
                getCardGameScene().attachChild(deckPositionBox);

                deckButton = new ButtonSprite(
                        deckX, deckY,
                        getRedBack(),
                        this.getVertexBufferObjectManager());
                deckButton.setOnClickListener(new DeckClickListener());
                getCardGameScene().attachChild(deckButton);
                getCardGameScene().registerTouchArea(deckButton);
            }
        }
        if (deckButton != null) {
            if (getLatestAction() != null
                    && getLatestAction().getGameState() != null
                    && getLatestAction().getGameState().getDeck() != null
                    && getLatestAction().getGameState().getDeck().size() > 0) {
                Debug.d("CardGames", "Deck is visible.");
                deckButton.setVisible(true);
            } else {
                Debug.d("CardGames", "Deck is not visible.");
                deckButton.setVisible(false);
            }
        }
    }

    // Listeners

    private class DeckClickListener implements ButtonSprite.OnClickListener {
        @Override
        public void onClick(final ButtonSprite deckButton,
                            final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
            BlackjackGameActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Debug.d("CardGames", "Deck clicked.");
                    if (isCurrentUserTurn()) {

                        // TODO handle deck click
                    }
                }
            });
        }
    }
}
