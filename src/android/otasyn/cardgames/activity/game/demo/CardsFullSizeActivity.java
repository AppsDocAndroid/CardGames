/**
 * Patrick John Haskins
 * Zachary Evans
 * CS7020 - Term Project
 */
package android.otasyn.cardgames.activity.game.demo;

import android.otasyn.cardgames.scene.CardGameScene;
import android.otasyn.cardgames.utility.TextureUtility;

public class CardsFullSizeActivity extends CardDemoActivity {

    @Override
    protected void onCreateCardGameResources() {
        setCardTextureRegions(TextureUtility.loadCardsFullSize(this));
    }

    @Override
    protected void onCreateCardGameScene(final CardGameScene scene) {
        loadCardSprites(scene, 40, 3, 80, 60);
    }
}
