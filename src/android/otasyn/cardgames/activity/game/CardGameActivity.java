package android.otasyn.cardgames.activity.game;

import android.os.Handler;
import android.os.Message;
import android.otasyn.cardgames.R;
import android.otasyn.cardgames.communication.asynctask.LatestActionTask;
import android.otasyn.cardgames.communication.dto.Game;
import android.otasyn.cardgames.communication.dto.GameAction;
import android.otasyn.cardgames.communication.dto.SimpleUser;
import android.otasyn.cardgames.scene.CardGameScene;
import android.otasyn.cardgames.sprite.CardSprite;
import android.otasyn.cardgames.utility.TextureUtility;
import android.otasyn.cardgames.utility.enumeration.Card;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.IResolutionPolicy;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.debug.Debug;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public abstract class CardGameActivity extends SimpleBaseGameActivity {

    public static final String GAME_INFO = "game_info";
    public static final String CURRENT_USER = "current_user";

    protected static int DEFAULT_PORTRAIT_CAMERA_WIDTH = 720;
    protected static int DEFAULT_PORTRAIT_CAMERA_HEIGHT = 1280;
    protected static int DEFAULT_LANDSCAPE_CAMERA_WIDTH = 1280;
    protected static int DEFAULT_LANDSCAPE_CAMERA_HEIGHT = 720;

    private int cameraWidth;
    private int cameraHeight;

    private Game game;
    private SimpleUser currentUser;
    private GameAction latestAction;
    private Timer timer;
    private Handler latestActionHandler;
    private boolean alsoUpdateOnCurrentUserTurn;

    private ScreenOrientation screenOrientation;
    private IResolutionPolicy resolutionPolicy;

    private ITextureRegion backgroundTextureRegion;
    private ITextureRegion[] gameMenuButtonRegion;
    private Map<Card,ITextureRegion> cardTextureRegions;
    private List<CardSprite> cardSprites = new ArrayList<CardSprite>();
    private ITextureRegion redBack;

    private ButtonSprite gameMenuButton;

    private CardGameScene cardGameScene;

    protected int getCameraWidth() {
        return cameraWidth;
    }

    protected void setCameraWidth(final int cameraWidth) {
        this.cameraWidth = cameraWidth;
    }

    protected int getCameraHeight() {
        return cameraHeight;
    }

    protected void setCameraHeight(final int cameraHeight) {
        this.cameraHeight = cameraHeight;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(final Game game) {
        this.game = game;
    }

    public SimpleUser getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(final SimpleUser currentUser) {
        this.currentUser = currentUser;
    }

    public GameAction getLatestAction() {
        return latestAction;
    }

    public void setLatestAction(final GameAction latestAction) {
        this.latestAction = latestAction;
    }

    protected boolean isCurrentUserTurn() {
        return getCurrentUser() != null && getLatestAction() != null
                && getCurrentUser().equals(getLatestAction().getNextActionPlayer());
    }

    protected void setScreenOrientation(final ScreenOrientation screenOrientation) {
        this.screenOrientation = screenOrientation;
    }

    protected void setResolutionPolicy(final IResolutionPolicy resolutionPolicy) {
        this.resolutionPolicy = resolutionPolicy;
    }

    public boolean isAlsoUpdateOnCurrentUserTurn() {
        return alsoUpdateOnCurrentUserTurn;
    }

    public void setAlsoUpdateOnCurrentUserTurn(final boolean alsoUpdateOnCurrentUserTurn) {
        this.alsoUpdateOnCurrentUserTurn = alsoUpdateOnCurrentUserTurn;
    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
    }

    @Override
    public EngineOptions onCreateEngineOptions() {
        onBeforeCreateEngineOptions();
        if (screenOrientation == null) {
            setScreenOrientation(ScreenOrientation.PORTRAIT_FIXED);
        }
        if (resolutionPolicy == null) {
            switch (screenOrientation) {
                case PORTRAIT_FIXED:
                case PORTRAIT_SENSOR:
                    setCameraWidth(DEFAULT_PORTRAIT_CAMERA_WIDTH);
                    setCameraHeight(DEFAULT_PORTRAIT_CAMERA_HEIGHT);
                    break;
                case LANDSCAPE_FIXED:
                case LANDSCAPE_SENSOR:
                default:
                    setCameraWidth(DEFAULT_LANDSCAPE_CAMERA_WIDTH);
                    setCameraHeight(DEFAULT_LANDSCAPE_CAMERA_HEIGHT);
            }
            setResolutionPolicy(new RatioResolutionPolicy(getCameraWidth(), getCameraHeight()));
        }

        setTheme(R.style.GameBoardTheme);

        updateInfo();

        latestActionHandler = new LatestActionHandler();
        timer = new Timer();
        timer.scheduleAtFixedRate(new LatestActionTimerTask(), 5000, 5000);

        return new EngineOptions(true, screenOrientation, resolutionPolicy,
                                 new Camera(0, 0, getCameraWidth(), getCameraHeight()));
    }

    protected void onBeforeCreateEngineOptions() { }

    private void updateInfo() {
        game = getIntent().getParcelableExtra(GAME_INFO);
        currentUser = getIntent().getParcelableExtra(CURRENT_USER);
        updateLatestAction();
    }

    private void updateLatestAction() {
        Debug.d("CardGames", "updateLatestAction()");
        GameAction newLatestAction;
        try {
            updateLatestAction((new LatestActionTask()).execute(game).get());
        } catch (Exception e) {
            Debug.d("CardGames", "updateLatestAction() failed.", e);
        }

        if (this.latestAction == null) {
            this.latestAction = new GameAction();
            this.latestAction.setActionNumber(-1);
            this.latestAction.setGame(getGame());
        }
    }

    protected void updateLatestAction(final GameAction newLatestAction) {
        if (newLatestAction != null
                && (this.latestAction == null
                || this.latestAction.getActionNumber() != newLatestAction.getActionNumber())) {
            this.latestAction = newLatestAction;
            onLatestActionUpdated(this.latestAction);
        }
    }

    protected void onLatestActionUpdated(final GameAction latestAction) { }

    private class LatestActionTimerTask extends TimerTask {
        @Override
        public void run() {
            if (isAlsoUpdateOnCurrentUserTurn() || !isCurrentUserTurn()) {
                latestActionHandler.sendEmptyMessage(0);
            }
        }
    }

    private class LatestActionHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            updateLatestAction();
        }
    }

    @Override
    final protected void onCreateResources() {
        this.backgroundTextureRegion = TextureUtility.loadBackground(this, getCameraWidth(), getCameraHeight());
        gameMenuButtonRegion = TextureUtility.loadGameMenuButton(this);
        onCreateCardGameResources();
        if (this.cardTextureRegions == null) {
            setCardTextureRegions(TextureUtility.loadCards92x128(this));
        }
    }

    protected abstract void onCreateCardGameResources();

    public Map<Card, ITextureRegion> getCardTextureRegions() {
        return cardTextureRegions;
    }

    public void setCardTextureRegions(final Map<Card, ITextureRegion> cardTextureRegions) {
        this.cardTextureRegions = cardTextureRegions;
    }

    public ITextureRegion getRedBack() {
        return redBack;
    }

    public CardGameScene getCardGameScene() {
        return cardGameScene;
    }

    protected void setGameMenuButtonVisibility(final boolean visible) {
        this.gameMenuButton.setVisible(visible);
    }

    @Override
    final protected Scene onCreateScene() {
        cardGameScene = new CardGameScene();

        final Sprite bgSprite = new Sprite(0, 0, this.backgroundTextureRegion, this.getVertexBufferObjectManager());
        cardGameScene.setBackground(new SpriteBackground(bgSprite));

        float x = 10;
        float y = getCameraHeight() - (gameMenuButtonRegion[TextureUtility.BUTTON_STATE_DOWN].getHeight() + 10);
        gameMenuButton = new ButtonSprite(
                x, y,
                gameMenuButtonRegion[TextureUtility.BUTTON_STATE_UP],
                gameMenuButtonRegion[TextureUtility.BUTTON_STATE_DOWN],
                this.getVertexBufferObjectManager());
        gameMenuButton.setOnClickListener(new GameMenuClickListener());
        cardGameScene.attachChild(gameMenuButton);
        cardGameScene.registerTouchArea(gameMenuButton);

        redBack = getCardTextureRegions().get(Card.BACK_RED);

        onCreateCardGameScene(cardGameScene);

        cardGameScene.setTouchAreaBindingOnActionDownEnabled(true);

        return cardGameScene;
    }

    protected abstract void onCreateCardGameScene(final CardGameScene scene);

    public void clearCardSprites() {
        CardGameActivity.this.runOnUpdateThread(new Runnable() {
            @Override
            public void run() {
                for (CardSprite cardSprite : cardSprites) {
                    cardSprite.detachSelf();
                    cardSprite.dispose();
                }
                cardSprites.clear();
                afterCardSpritesCleared();
            }
        });
    }

    protected void afterCardSpritesCleared() { }

    public CardSprite getCardSprite(final Card card) {
        CardSprite cardSprite = new CardSprite(card, 0, 0, getCardTextureRegions().get(card).deepCopy(), redBack, getVertexBufferObjectManager());
        cardSprites.add(cardSprite);
        getCardGameScene().attachCardSprite(cardSprite);
        cardSprite.setVisible(false);
        cardSprite.showBack();
        return cardSprite;
    }

    private class GameMenuClickListener implements ButtonSprite.OnClickListener {
        @Override
        public void onClick(final ButtonSprite pButtonSprite,
                            final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
            onGameMenuClick(pButtonSprite, pTouchAreaLocalX, pTouchAreaLocalY);
        }
    }

    protected void onGameMenuClick(ButtonSprite pButtonSprite,float pTouchAreaLocalX, float pTouchAreaLocalY) { }
}
