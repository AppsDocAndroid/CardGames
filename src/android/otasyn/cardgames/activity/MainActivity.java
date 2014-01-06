/**
 * Patrick John Haskins
 * Zachary Evans
 * CS7020 - Term Project
 */
package android.otasyn.cardgames.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.otasyn.cardgames.communication.dto.SimpleUser;
import android.otasyn.cardgames.communication.utility.AccountUtility;
import android.otasyn.cardgames.scene.GameMenuScene;
import android.otasyn.cardgames.utility.TextureUtility;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.HorizontalAlign;

public class MainActivity extends GameMenuActivity {

    public static void launch(final Context context) {
        Intent mainIntent = new Intent(context, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(mainIntent);
    }

    private ITextureRegion logoTextureRegion;

    private ITextureRegion[] signInButtonTextureRegions;
    private ITextureRegion[] signOutButtonTextureRegions;
    private ITextureRegion[] registerButtonTextureRegions;
    private ITextureRegion[] demosButtonTextureRegions;
    private ITextureRegion[] friendsButtonTextureRegions;
    private ITextureRegion[] gamesButtonTextureRegions;

    private GameMenuScene scene;
    private ButtonSprite signInButton;
    private ButtonSprite signOutButton;
    private ButtonSprite registerButton;
    private ButtonSprite demosButton;
    private ButtonSprite friendsButton;
    private ButtonSprite gamesButton;

    private float startY = 500;

    private SimpleUser currentUser;

    private Font boldFont;
    private Font normalFont;

    private Text userText;
    private Text emailText;

    @Override
    protected void onCreateGameMenuResources() {
        logoTextureRegion = TextureUtility.loadLogo(this);

        signInButtonTextureRegions = TextureUtility.loadSignInButton(this);
        signOutButtonTextureRegions = TextureUtility.loadSignOutButton(this);
        registerButtonTextureRegions = TextureUtility.loadRegisterButton(this);
        demosButtonTextureRegions = TextureUtility.loadDemosButton(this);
        friendsButtonTextureRegions = TextureUtility.loadFriendsButton(this);
        gamesButtonTextureRegions = TextureUtility.loadGamesButton(this);

        boldFont = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256,
                Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
        boldFont.load();
        normalFont = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256,
                Typeface.create(Typeface.DEFAULT, Typeface.NORMAL), 32);
        normalFont.load();
    }

    @Override
    protected void onCreateGameMenuScene(final GameMenuScene scene) {
        this.scene = scene;
        float buttonPadding = 20;

        userText = new Text(20, 40, this.boldFont, "", 256,
                new TextOptions(HorizontalAlign.LEFT), getVertexBufferObjectManager());
        scene.attachChild(userText);

        emailText = new Text(20, userText.getY() + userText.getHeight(), this.normalFont, "", 256,
                new TextOptions(HorizontalAlign.LEFT), getVertexBufferObjectManager());
        scene.attachChild(emailText);

        float logoWidth = 256;
        float logoHeight = 256;
        float logoX = (CAMERA_WIDTH - logoWidth) / 2;
        float logoY = 150;
        final Sprite logo = new Sprite(logoX, logoY, logoWidth, logoHeight, logoTextureRegion,
                                       this.getVertexBufferObjectManager());
        scene.attachChild(logo);

        //
        // Both Logged In and Not Logged In
        //

        float start = startY;
        demosButton = new ButtonSprite(
                buttonPadding, start,
                demosButtonTextureRegions[TextureUtility.BUTTON_STATE_UP],
                demosButtonTextureRegions[TextureUtility.BUTTON_STATE_DOWN],
                demosButtonTextureRegions[TextureUtility.BUTTON_STATE_DISABLED],
                this.getVertexBufferObjectManager());
        demosButton.setOnClickListener(new DemosClickListener());
        scene.attachChild(demosButton);
        scene.registerTouchArea(demosButton);

        //
        // Not Logged In
        //

        start += (demosButtonTextureRegions[TextureUtility.BUTTON_STATE_UP].getHeight() + buttonPadding);
        signInButton = new ButtonSprite(
                buttonPadding, start,
                signInButtonTextureRegions[TextureUtility.BUTTON_STATE_UP],
                signInButtonTextureRegions[TextureUtility.BUTTON_STATE_DOWN],
                signInButtonTextureRegions[TextureUtility.BUTTON_STATE_DISABLED],
                this.getVertexBufferObjectManager());
        signInButton.setOnClickListener(new SignInClickListener());
        scene.attachChild(signInButton);
        scene.registerTouchArea(signInButton);

        start += (signInButtonTextureRegions[TextureUtility.BUTTON_STATE_UP].getHeight() + buttonPadding);
        registerButton = new ButtonSprite(
                buttonPadding, start,
                registerButtonTextureRegions[TextureUtility.BUTTON_STATE_UP],
                registerButtonTextureRegions[TextureUtility.BUTTON_STATE_DOWN],
                registerButtonTextureRegions[TextureUtility.BUTTON_STATE_DISABLED],
                this.getVertexBufferObjectManager());
        registerButton.setOnClickListener(new RegisterClickListener());
        scene.attachChild(registerButton);
        scene.registerTouchArea(registerButton);

        //
        // Logged In
        //

        start = (startY + demosButtonTextureRegions[TextureUtility.BUTTON_STATE_UP].getHeight() + buttonPadding);
        gamesButton = new ButtonSprite(
                buttonPadding, start,
                gamesButtonTextureRegions[TextureUtility.BUTTON_STATE_UP],
                gamesButtonTextureRegions[TextureUtility.BUTTON_STATE_DOWN],
                gamesButtonTextureRegions[TextureUtility.BUTTON_STATE_DISABLED],
                this.getVertexBufferObjectManager());
        gamesButton.setOnClickListener(new GamesClickListener());
        scene.attachChild(gamesButton);
        scene.registerTouchArea(gamesButton);

        start += (gamesButtonTextureRegions[TextureUtility.BUTTON_STATE_UP].getHeight() + buttonPadding);
        friendsButton = new ButtonSprite(
                buttonPadding, start,
                friendsButtonTextureRegions[TextureUtility.BUTTON_STATE_UP],
                friendsButtonTextureRegions[TextureUtility.BUTTON_STATE_DOWN],
                friendsButtonTextureRegions[TextureUtility.BUTTON_STATE_DISABLED],
                this.getVertexBufferObjectManager());
        friendsButton.setOnClickListener(new FriendsClickListener());
        scene.attachChild(friendsButton);
        scene.registerTouchArea(friendsButton);

        start += (friendsButtonTextureRegions[TextureUtility.BUTTON_STATE_UP].getHeight() + buttonPadding);
        signOutButton = new ButtonSprite(
                buttonPadding, start,
                signOutButtonTextureRegions[TextureUtility.BUTTON_STATE_UP],
                signOutButtonTextureRegions[TextureUtility.BUTTON_STATE_DOWN],
                signOutButtonTextureRegions[TextureUtility.BUTTON_STATE_DISABLED],
                this.getVertexBufferObjectManager());
        signOutButton.setOnClickListener(new SignOutClickListener());
        scene.attachChild(signOutButton);
        scene.registerTouchArea(signOutButton);

        currentUser = AccountUtility.currentUser();
        if (currentUser == null) {
            showLoggedOutButton();
        } else {
            showLoggedInButtons();
        }
    }

    private void showLoggedInButtons() {
        demosButton.setVisible(true);

        gamesButton.setVisible(true);
        friendsButton.setVisible(true);
        signOutButton.setVisible(true);

        signInButton.setVisible(false);
        registerButton.setVisible(false);

        if (currentUser != null) {
            userText.setText(currentUser.getFirstname() + " " + currentUser.getLastname());
            emailText.setText(currentUser.getEmail());
        } else {
            showLoggedOutButton();
        }
    }

    private void showLoggedOutButton() {
        demosButton.setVisible(true);

        gamesButton.setVisible(false);
        friendsButton.setVisible(false);
        signOutButton.setVisible(false);

        signInButton.setVisible(true);
        registerButton.setVisible(true);

        userText.setText("");
        emailText.setText("");
    }

    private class SignInClickListener implements ButtonSprite.OnClickListener {
        @Override
        public void onClick(final ButtonSprite pButtonSprite,
                            final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
            LoginActivity.launch(MainActivity.this);
        }
    }

    private class SignOutClickListener implements ButtonSprite.OnClickListener {
        @Override
        public void onClick(final ButtonSprite pButtonSprite,
                            final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
            AccountUtility.logout();
            showLoggedOutButton();
        }
    }

    private class RegisterClickListener implements ButtonSprite.OnClickListener {
        @Override
        public void onClick(final ButtonSprite pButtonSprite,
                            final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
            RegisterActivity.launch(MainActivity.this);
        }
    }

    private class GamesClickListener implements ButtonSprite.OnClickListener {
        @Override
        public void onClick(final ButtonSprite pButtonSprite,
                            final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
            GamesListActivity.launch(MainActivity.this);
        }
    }

    private class FriendsClickListener implements ButtonSprite.OnClickListener {
        @Override
        public void onClick(final ButtonSprite pButtonSprite,
                            final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
            FriendsListActivity.launch(MainActivity.this);
        }
    }

    private class DemosClickListener implements ButtonSprite.OnClickListener {
        @Override
        public void onClick(final ButtonSprite pButtonSprite,
                            final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
            DemosActivity.launch(MainActivity.this);
        }
    }
}
