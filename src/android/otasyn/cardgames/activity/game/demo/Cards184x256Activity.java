/**
 * Patrick John Haskins
 * Zachary Evans
 * CS7020 - Term Project
 */
package android.otasyn.cardgames.activity.game.demo;

import android.otasyn.cardgames.scene.CardGameScene;
import android.otasyn.cardgames.utility.TextureUtility;

public class Cards184x256Activity extends CardDemoActivity {

    @Override
    protected void onCreateCardGameResources() {
        setCardTextureRegions(TextureUtility.loadCards184x256(this));
    }

    @Override
    protected void onCreateCardGameScene(final CardGameScene scene) {
        loadCardSprites(scene, 40, 3, 80, 60);
    }
}
