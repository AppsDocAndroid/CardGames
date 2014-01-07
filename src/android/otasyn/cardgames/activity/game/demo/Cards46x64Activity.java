/**
 * Patrick John Haskins
 * Zachary Evans
 * CS7020 - Term Project
 */
package android.otasyn.cardgames.activity.game.demo;

import android.otasyn.cardgames.scene.CardGameScene;
import android.otasyn.cardgames.utility.TextureUtility;

public class Cards46x64Activity extends CardDemoActivity {

    @Override
    protected void onCreateCardGameResources() {
        setCardTextureRegions(TextureUtility.loadCards46x64(this));
    }

    @Override
    protected void onCreateCardGameScene(final CardGameScene scene) {
        loadCardSprites(scene, 10, 3, 20, 15);
    }
}
