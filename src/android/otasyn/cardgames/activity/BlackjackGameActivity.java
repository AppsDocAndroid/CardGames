package android.otasyn.cardgames.activity;

import android.graphics.Typeface;
import android.otasyn.cardgames.scene.CardGameScene;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;

public class BlackjackGameActivity extends CardGameActivity {

    private Font boldFont;

    @Override
    protected void onCreateCardGameResources() {
        boldFont = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256,
                Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
        boldFont.load();
    }

    @Override
    protected void onCreateCardGameScene(final CardGameScene scene) {

    }
}
