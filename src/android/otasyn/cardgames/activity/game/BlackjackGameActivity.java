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
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;

public class BlackjackGameActivity extends CardGameActivity {

    private final static float POSITION_BOX_WIDTH = 125;
    private final static float POSITION_BOX_HEIGHT = 200;

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
}
