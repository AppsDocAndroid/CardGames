/**
 * Patrick John Haskins
 * Zachary Evans
 * CS7020 - Term Project
 */
package android.otasyn.cardgames.activity.game.demo;

import android.otasyn.cardgames.scene.CardGameScene;
import android.otasyn.cardgames.utility.TextureUtility;

public class Cards92x128Activity extends CardDemoActivity {

    @Override
    protected void onCreateCardGameResources() {
        setCardTextureRegions(TextureUtility.loadCards92x128(this));
    }

    @Override
    protected void onCreateCardGameScene(final CardGameScene scene) {
        loadCardSprites(scene, 20, 3, 40, 30);
    }
}
