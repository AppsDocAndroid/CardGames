/**
 * Patrick John Haskins
 * Zachary Evans
 * CS7020 - Term Project
 */
package android.otasyn.cardgames.utility;

import android.otasyn.cardgames.utility.enumeration.Background;
import android.otasyn.cardgames.utility.enumeration.Card;
import android.otasyn.cardgames.utility.enumeration.CardFile;
import android.otasyn.cardgames.utility.enumeration.CardLocation;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePack;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackLoader;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackTextureRegionLibrary;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.exception.TexturePackParseException;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.debug.Debug;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class TextureUtility {

    public static final int BUTTON_STATE_UP = 0;
    public static final int BUTTON_STATE_DOWN = 1;
    public static final int BUTTON_STATE_DISABLED = 2;

    public static ITextureRegion loadLogo(final BaseGameActivity baseGameActivity) {
        try {
            ITexture logoTexture = new BitmapTexture(baseGameActivity.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return baseGameActivity.getAssets().open("gfx/logo/logo-128.png");
                }
            });

            logoTexture.load();
            return TextureRegionFactory.extractFromTexture(logoTexture);
        } catch (IOException e) {
            Debug.e(e);
            return null;
        }
    }

    public static ITextureRegion loadBackground(final BaseGameActivity baseGameActivity,
                                                final int width, final int height) {
        return loadBackground(baseGameActivity, width, height, Background.BG_GREEN_1A9C48);
    }

    public static ITextureRegion loadBackground(final BaseGameActivity baseGameActivity,
                                                final int width, final int height,
                                                final Background bg) {
        BitmapTextureAtlas backgroundTexture = new BitmapTextureAtlas(baseGameActivity.getTextureManager(), 100, 100,
                TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA);
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(Background.BG_BASE_PATH);
        BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                backgroundTexture, baseGameActivity, bg.getLocation(), 0, 0);
        backgroundTexture.load();
        ITextureRegion backgroundTextureRegion = TextureRegionFactory.extractFromTexture(backgroundTexture);

        backgroundTextureRegion.setTextureWidth(width);
        backgroundTextureRegion.setTextureHeight(height);

        return backgroundTextureRegion;
    }

    public static Map<Card,ITextureRegion> loadCards46x64(final BaseGameActivity baseGameActivity) {
        Map<Card,ITextureRegion> cards = new HashMap<Card, ITextureRegion>(Card.values().length);
        try {
            TexturePackLoader texturePackLoader = new TexturePackLoader(
                    baseGameActivity.getTextureManager(), CardLocation.LOCATION_46x64.getLocation());

            final TexturePack texturePack = texturePackLoader.loadFromAsset(
                    baseGameActivity.getAssets(), CardFile.FILE_46x64.getFile());
            texturePack.loadTexture();
            TexturePackTextureRegionLibrary texturePackLibrary = texturePack.getTexturePackTextureRegionLibrary();

            cards.put(Card.CLUBS_ACE, texturePackLibrary.get(Card.CLUBS_ACE.getId_46x64()));
            cards.put(Card.CLUBS_TWO, texturePackLibrary.get(Card.CLUBS_TWO.getId_46x64()));
            cards.put(Card.CLUBS_THREE, texturePackLibrary.get(Card.CLUBS_THREE.getId_46x64()));
            cards.put(Card.CLUBS_FOUR, texturePackLibrary.get(Card.CLUBS_FOUR.getId_46x64()));
            cards.put(Card.CLUBS_FIVE, texturePackLibrary.get(Card.CLUBS_FIVE.getId_46x64()));
            cards.put(Card.CLUBS_SIX, texturePackLibrary.get(Card.CLUBS_SIX.getId_46x64()));
            cards.put(Card.CLUBS_SEVEN, texturePackLibrary.get(Card.CLUBS_SEVEN.getId_46x64()));
            cards.put(Card.CLUBS_EIGHT, texturePackLibrary.get(Card.CLUBS_EIGHT.getId_46x64()));
            cards.put(Card.CLUBS_NINE, texturePackLibrary.get(Card.CLUBS_NINE.getId_46x64()));
            cards.put(Card.CLUBS_TEN, texturePackLibrary.get(Card.CLUBS_TEN.getId_46x64()));
            cards.put(Card.CLUBS_JACK, texturePackLibrary.get(Card.CLUBS_JACK.getId_46x64()));
            cards.put(Card.CLUBS_QUEEN, texturePackLibrary.get(Card.CLUBS_QUEEN.getId_46x64()));
            cards.put(Card.CLUBS_KING, texturePackLibrary.get(Card.CLUBS_KING.getId_46x64()));

            cards.put(Card.DIAMONDS_ACE, texturePackLibrary.get(Card.DIAMONDS_ACE.getId_46x64()));
            cards.put(Card.DIAMONDS_TWO, texturePackLibrary.get(Card.DIAMONDS_TWO.getId_46x64()));
            cards.put(Card.DIAMONDS_THREE, texturePackLibrary.get(Card.DIAMONDS_THREE.getId_46x64()));
            cards.put(Card.DIAMONDS_FOUR, texturePackLibrary.get(Card.DIAMONDS_FOUR.getId_46x64()));
            cards.put(Card.DIAMONDS_FIVE, texturePackLibrary.get(Card.DIAMONDS_FIVE.getId_46x64()));
            cards.put(Card.DIAMONDS_SIX, texturePackLibrary.get(Card.DIAMONDS_SIX.getId_46x64()));
            cards.put(Card.DIAMONDS_SEVEN, texturePackLibrary.get(Card.DIAMONDS_SEVEN.getId_46x64()));
            cards.put(Card.DIAMONDS_EIGHT, texturePackLibrary.get(Card.DIAMONDS_EIGHT.getId_46x64()));
            cards.put(Card.DIAMONDS_NINE, texturePackLibrary.get(Card.DIAMONDS_NINE.getId_46x64()));
            cards.put(Card.DIAMONDS_TEN, texturePackLibrary.get(Card.DIAMONDS_TEN.getId_46x64()));
            cards.put(Card.DIAMONDS_JACK, texturePackLibrary.get(Card.DIAMONDS_JACK.getId_46x64()));
            cards.put(Card.DIAMONDS_QUEEN, texturePackLibrary.get(Card.DIAMONDS_QUEEN.getId_46x64()));
            cards.put(Card.DIAMONDS_KING, texturePackLibrary.get(Card.DIAMONDS_KING.getId_46x64()));

            cards.put(Card.HEARTS_ACE, texturePackLibrary.get(Card.HEARTS_ACE.getId_46x64()));
            cards.put(Card.HEARTS_TWO, texturePackLibrary.get(Card.HEARTS_TWO.getId_46x64()));
            cards.put(Card.HEARTS_THREE, texturePackLibrary.get(Card.HEARTS_THREE.getId_46x64()));
            cards.put(Card.HEARTS_FOUR, texturePackLibrary.get(Card.HEARTS_FOUR.getId_46x64()));
            cards.put(Card.HEARTS_FIVE, texturePackLibrary.get(Card.HEARTS_FIVE.getId_46x64()));
            cards.put(Card.HEARTS_SIX, texturePackLibrary.get(Card.HEARTS_SIX.getId_46x64()));
            cards.put(Card.HEARTS_SEVEN, texturePackLibrary.get(Card.HEARTS_SEVEN.getId_46x64()));
            cards.put(Card.HEARTS_EIGHT, texturePackLibrary.get(Card.HEARTS_EIGHT.getId_46x64()));
            cards.put(Card.HEARTS_NINE, texturePackLibrary.get(Card.HEARTS_NINE.getId_46x64()));
            cards.put(Card.HEARTS_TEN, texturePackLibrary.get(Card.HEARTS_TEN.getId_46x64()));
            cards.put(Card.HEARTS_JACK, texturePackLibrary.get(Card.HEARTS_JACK.getId_46x64()));
            cards.put(Card.HEARTS_QUEEN, texturePackLibrary.get(Card.HEARTS_QUEEN.getId_46x64()));
            cards.put(Card.HEARTS_KING, texturePackLibrary.get(Card.HEARTS_KING.getId_46x64()));

            cards.put(Card.SPADES_ACE, texturePackLibrary.get(Card.SPADES_ACE.getId_46x64()));
            cards.put(Card.SPADES_TWO, texturePackLibrary.get(Card.SPADES_TWO.getId_46x64()));
            cards.put(Card.SPADES_THREE, texturePackLibrary.get(Card.SPADES_THREE.getId_46x64()));
            cards.put(Card.SPADES_FOUR, texturePackLibrary.get(Card.SPADES_FOUR.getId_46x64()));
            cards.put(Card.SPADES_FIVE, texturePackLibrary.get(Card.SPADES_FIVE.getId_46x64()));
            cards.put(Card.SPADES_SIX, texturePackLibrary.get(Card.SPADES_SIX.getId_46x64()));
            cards.put(Card.SPADES_SEVEN, texturePackLibrary.get(Card.SPADES_SEVEN.getId_46x64()));
            cards.put(Card.SPADES_EIGHT, texturePackLibrary.get(Card.SPADES_EIGHT.getId_46x64()));
            cards.put(Card.SPADES_NINE, texturePackLibrary.get(Card.SPADES_NINE.getId_46x64()));
            cards.put(Card.SPADES_TEN, texturePackLibrary.get(Card.SPADES_TEN.getId_46x64()));
            cards.put(Card.SPADES_JACK, texturePackLibrary.get(Card.SPADES_JACK.getId_46x64()));
            cards.put(Card.SPADES_QUEEN, texturePackLibrary.get(Card.SPADES_QUEEN.getId_46x64()));
            cards.put(Card.SPADES_KING, texturePackLibrary.get(Card.SPADES_KING.getId_46x64()));

            cards.put(Card.BACK_BLUE, texturePackLibrary.get(Card.BACK_BLUE.getId_46x64()));
            cards.put(Card.BACK_RED, texturePackLibrary.get(Card.BACK_RED.getId_46x64()));
        } catch (final TexturePackParseException e) {
            Debug.e(e);
        }

        return cards;
    }

    public static Map<Card,ITextureRegion> loadCards92x128(final BaseGameActivity baseGameActivity) {
        Map<Card,ITextureRegion> cards = new HashMap<Card, ITextureRegion>(Card.values().length);
        try {
            TexturePackLoader texturePackLoader = new TexturePackLoader(
                    baseGameActivity.getTextureManager(), CardLocation.LOCATION_92x128.getLocation());

            final TexturePack texturePack = texturePackLoader.loadFromAsset(
                    baseGameActivity.getAssets(), CardFile.FILE_92x128.getFile());
            texturePack.loadTexture();
            TexturePackTextureRegionLibrary texturePackLibrary = texturePack.getTexturePackTextureRegionLibrary();

            cards.put(Card.CLUBS_ACE, texturePackLibrary.get(Card.CLUBS_ACE.getId_92x128()));
            cards.put(Card.CLUBS_TWO, texturePackLibrary.get(Card.CLUBS_TWO.getId_92x128()));
            cards.put(Card.CLUBS_THREE, texturePackLibrary.get(Card.CLUBS_THREE.getId_92x128()));
            cards.put(Card.CLUBS_FOUR, texturePackLibrary.get(Card.CLUBS_FOUR.getId_92x128()));
            cards.put(Card.CLUBS_FIVE, texturePackLibrary.get(Card.CLUBS_FIVE.getId_92x128()));
            cards.put(Card.CLUBS_SIX, texturePackLibrary.get(Card.CLUBS_SIX.getId_92x128()));
            cards.put(Card.CLUBS_SEVEN, texturePackLibrary.get(Card.CLUBS_SEVEN.getId_92x128()));
            cards.put(Card.CLUBS_EIGHT, texturePackLibrary.get(Card.CLUBS_EIGHT.getId_92x128()));
            cards.put(Card.CLUBS_NINE, texturePackLibrary.get(Card.CLUBS_NINE.getId_92x128()));
            cards.put(Card.CLUBS_TEN, texturePackLibrary.get(Card.CLUBS_TEN.getId_92x128()));
            cards.put(Card.CLUBS_JACK, texturePackLibrary.get(Card.CLUBS_JACK.getId_92x128()));
            cards.put(Card.CLUBS_QUEEN, texturePackLibrary.get(Card.CLUBS_QUEEN.getId_92x128()));
            cards.put(Card.CLUBS_KING, texturePackLibrary.get(Card.CLUBS_KING.getId_92x128()));

            cards.put(Card.DIAMONDS_ACE, texturePackLibrary.get(Card.DIAMONDS_ACE.getId_92x128()));
            cards.put(Card.DIAMONDS_TWO, texturePackLibrary.get(Card.DIAMONDS_TWO.getId_92x128()));
            cards.put(Card.DIAMONDS_THREE, texturePackLibrary.get(Card.DIAMONDS_THREE.getId_92x128()));
            cards.put(Card.DIAMONDS_FOUR, texturePackLibrary.get(Card.DIAMONDS_FOUR.getId_92x128()));
            cards.put(Card.DIAMONDS_FIVE, texturePackLibrary.get(Card.DIAMONDS_FIVE.getId_92x128()));
            cards.put(Card.DIAMONDS_SIX, texturePackLibrary.get(Card.DIAMONDS_SIX.getId_92x128()));
            cards.put(Card.DIAMONDS_SEVEN, texturePackLibrary.get(Card.DIAMONDS_SEVEN.getId_92x128()));
            cards.put(Card.DIAMONDS_EIGHT, texturePackLibrary.get(Card.DIAMONDS_EIGHT.getId_92x128()));
            cards.put(Card.DIAMONDS_NINE, texturePackLibrary.get(Card.DIAMONDS_NINE.getId_92x128()));
            cards.put(Card.DIAMONDS_TEN, texturePackLibrary.get(Card.DIAMONDS_TEN.getId_92x128()));
            cards.put(Card.DIAMONDS_JACK, texturePackLibrary.get(Card.DIAMONDS_JACK.getId_92x128()));
            cards.put(Card.DIAMONDS_QUEEN, texturePackLibrary.get(Card.DIAMONDS_QUEEN.getId_92x128()));
            cards.put(Card.DIAMONDS_KING, texturePackLibrary.get(Card.DIAMONDS_KING.getId_92x128()));

            cards.put(Card.HEARTS_ACE, texturePackLibrary.get(Card.HEARTS_ACE.getId_92x128()));
            cards.put(Card.HEARTS_TWO, texturePackLibrary.get(Card.HEARTS_TWO.getId_92x128()));
            cards.put(Card.HEARTS_THREE, texturePackLibrary.get(Card.HEARTS_THREE.getId_92x128()));
            cards.put(Card.HEARTS_FOUR, texturePackLibrary.get(Card.HEARTS_FOUR.getId_92x128()));
            cards.put(Card.HEARTS_FIVE, texturePackLibrary.get(Card.HEARTS_FIVE.getId_92x128()));
            cards.put(Card.HEARTS_SIX, texturePackLibrary.get(Card.HEARTS_SIX.getId_92x128()));
            cards.put(Card.HEARTS_SEVEN, texturePackLibrary.get(Card.HEARTS_SEVEN.getId_92x128()));
            cards.put(Card.HEARTS_EIGHT, texturePackLibrary.get(Card.HEARTS_EIGHT.getId_92x128()));
            cards.put(Card.HEARTS_NINE, texturePackLibrary.get(Card.HEARTS_NINE.getId_92x128()));
            cards.put(Card.HEARTS_TEN, texturePackLibrary.get(Card.HEARTS_TEN.getId_92x128()));
            cards.put(Card.HEARTS_JACK, texturePackLibrary.get(Card.HEARTS_JACK.getId_92x128()));
            cards.put(Card.HEARTS_QUEEN, texturePackLibrary.get(Card.HEARTS_QUEEN.getId_92x128()));
            cards.put(Card.HEARTS_KING, texturePackLibrary.get(Card.HEARTS_KING.getId_92x128()));

            cards.put(Card.SPADES_ACE, texturePackLibrary.get(Card.SPADES_ACE.getId_92x128()));
            cards.put(Card.SPADES_TWO, texturePackLibrary.get(Card.SPADES_TWO.getId_92x128()));
            cards.put(Card.SPADES_THREE, texturePackLibrary.get(Card.SPADES_THREE.getId_92x128()));
            cards.put(Card.SPADES_FOUR, texturePackLibrary.get(Card.SPADES_FOUR.getId_92x128()));
            cards.put(Card.SPADES_FIVE, texturePackLibrary.get(Card.SPADES_FIVE.getId_92x128()));
            cards.put(Card.SPADES_SIX, texturePackLibrary.get(Card.SPADES_SIX.getId_92x128()));
            cards.put(Card.SPADES_SEVEN, texturePackLibrary.get(Card.SPADES_SEVEN.getId_92x128()));
            cards.put(Card.SPADES_EIGHT, texturePackLibrary.get(Card.SPADES_EIGHT.getId_92x128()));
            cards.put(Card.SPADES_NINE, texturePackLibrary.get(Card.SPADES_NINE.getId_92x128()));
            cards.put(Card.SPADES_TEN, texturePackLibrary.get(Card.SPADES_TEN.getId_92x128()));
            cards.put(Card.SPADES_JACK, texturePackLibrary.get(Card.SPADES_JACK.getId_92x128()));
            cards.put(Card.SPADES_QUEEN, texturePackLibrary.get(Card.SPADES_QUEEN.getId_92x128()));
            cards.put(Card.SPADES_KING, texturePackLibrary.get(Card.SPADES_KING.getId_92x128()));

            cards.put(Card.BACK_BLUE, texturePackLibrary.get(Card.BACK_BLUE.getId_92x128()));
            cards.put(Card.BACK_RED, texturePackLibrary.get(Card.BACK_RED.getId_92x128()));
        } catch (final TexturePackParseException e) {
            Debug.e(e);
        }

        return cards;
    }

    public static Map<Card,ITextureRegion> loadCards184x256(final BaseGameActivity baseGameActivity) {
        Map<Card,ITextureRegion> cards = new HashMap<Card, ITextureRegion>(Card.values().length);
        try {
            TexturePackLoader texturePackLoader = new TexturePackLoader(baseGameActivity.getTextureManager(),
                    CardLocation.LOCATION_184x256.getLocation());
            final TexturePack clubsTexturePack = texturePackLoader.loadFromAsset(
                    baseGameActivity.getAssets(), CardFile.FILE_184x256_CLUBS.getFile());
            clubsTexturePack.loadTexture();
            TexturePackTextureRegionLibrary clubsTexturePackLibrary =
                    clubsTexturePack.getTexturePackTextureRegionLibrary();

            cards.put(Card.CLUBS_ACE, clubsTexturePackLibrary.get(Card.CLUBS_ACE.getId_184x256()));
            cards.put(Card.CLUBS_TWO, clubsTexturePackLibrary.get(Card.CLUBS_TWO.getId_184x256()));
            cards.put(Card.CLUBS_THREE, clubsTexturePackLibrary.get(Card.CLUBS_THREE.getId_184x256()));
            cards.put(Card.CLUBS_FOUR, clubsTexturePackLibrary.get(Card.CLUBS_FOUR.getId_184x256()));
            cards.put(Card.CLUBS_FIVE, clubsTexturePackLibrary.get(Card.CLUBS_FIVE.getId_184x256()));
            cards.put(Card.CLUBS_SIX, clubsTexturePackLibrary.get(Card.CLUBS_SIX.getId_184x256()));
            cards.put(Card.CLUBS_SEVEN, clubsTexturePackLibrary.get(Card.CLUBS_SEVEN.getId_184x256()));
            cards.put(Card.CLUBS_EIGHT, clubsTexturePackLibrary.get(Card.CLUBS_EIGHT.getId_184x256()));
            cards.put(Card.CLUBS_NINE, clubsTexturePackLibrary.get(Card.CLUBS_NINE.getId_184x256()));
            cards.put(Card.CLUBS_TEN, clubsTexturePackLibrary.get(Card.CLUBS_TEN.getId_184x256()));
            cards.put(Card.CLUBS_JACK, clubsTexturePackLibrary.get(Card.CLUBS_JACK.getId_184x256()));
            cards.put(Card.CLUBS_QUEEN, clubsTexturePackLibrary.get(Card.CLUBS_QUEEN.getId_184x256()));
            cards.put(Card.CLUBS_KING, clubsTexturePackLibrary.get(Card.CLUBS_KING.getId_184x256()));

            final TexturePack diamondsTexturePack = texturePackLoader.loadFromAsset(
                    baseGameActivity.getAssets(),  CardFile.FILE_184x256_DIAMONDS.getFile());
            diamondsTexturePack.loadTexture();
            TexturePackTextureRegionLibrary diamondsTexturePackLibrary =
                    diamondsTexturePack.getTexturePackTextureRegionLibrary();

            cards.put(Card.DIAMONDS_ACE, diamondsTexturePackLibrary.get(Card.DIAMONDS_ACE.getId_184x256()));
            cards.put(Card.DIAMONDS_TWO, diamondsTexturePackLibrary.get(Card.DIAMONDS_TWO.getId_184x256()));
            cards.put(Card.DIAMONDS_THREE, diamondsTexturePackLibrary.get(Card.DIAMONDS_THREE.getId_184x256()));
            cards.put(Card.DIAMONDS_FOUR, diamondsTexturePackLibrary.get(Card.DIAMONDS_FOUR.getId_184x256()));
            cards.put(Card.DIAMONDS_FIVE, diamondsTexturePackLibrary.get(Card.DIAMONDS_FIVE.getId_184x256()));
            cards.put(Card.DIAMONDS_SIX, diamondsTexturePackLibrary.get(Card.DIAMONDS_SIX.getId_184x256()));
            cards.put(Card.DIAMONDS_SEVEN, diamondsTexturePackLibrary.get(Card.DIAMONDS_SEVEN.getId_184x256()));
            cards.put(Card.DIAMONDS_EIGHT, diamondsTexturePackLibrary.get(Card.DIAMONDS_EIGHT.getId_184x256()));
            cards.put(Card.DIAMONDS_NINE, diamondsTexturePackLibrary.get(Card.DIAMONDS_NINE.getId_184x256()));
            cards.put(Card.DIAMONDS_TEN, diamondsTexturePackLibrary.get(Card.DIAMONDS_TEN.getId_184x256()));
            cards.put(Card.DIAMONDS_JACK, diamondsTexturePackLibrary.get(Card.DIAMONDS_JACK.getId_184x256()));
            cards.put(Card.DIAMONDS_QUEEN, diamondsTexturePackLibrary.get(Card.DIAMONDS_QUEEN.getId_184x256()));
            cards.put(Card.DIAMONDS_KING, diamondsTexturePackLibrary.get(Card.DIAMONDS_KING.getId_184x256()));

            final TexturePack heartsTexturePack = texturePackLoader.loadFromAsset(
                    baseGameActivity.getAssets(),  CardFile.FILE_184x256_HEARTS.getFile());
            heartsTexturePack.loadTexture();
            TexturePackTextureRegionLibrary heartsTexturePackLibrary =
                    heartsTexturePack.getTexturePackTextureRegionLibrary();

            cards.put(Card.HEARTS_ACE, heartsTexturePackLibrary.get(Card.HEARTS_ACE.getId_184x256()));
            cards.put(Card.HEARTS_TWO, heartsTexturePackLibrary.get(Card.HEARTS_TWO.getId_184x256()));
            cards.put(Card.HEARTS_THREE, heartsTexturePackLibrary.get(Card.HEARTS_THREE.getId_184x256()));
            cards.put(Card.HEARTS_FOUR, heartsTexturePackLibrary.get(Card.HEARTS_FOUR.getId_184x256()));
            cards.put(Card.HEARTS_FIVE, heartsTexturePackLibrary.get(Card.HEARTS_FIVE.getId_184x256()));
            cards.put(Card.HEARTS_SIX, heartsTexturePackLibrary.get(Card.HEARTS_SIX.getId_184x256()));
            cards.put(Card.HEARTS_SEVEN, heartsTexturePackLibrary.get(Card.HEARTS_SEVEN.getId_184x256()));
            cards.put(Card.HEARTS_EIGHT, heartsTexturePackLibrary.get(Card.HEARTS_EIGHT.getId_184x256()));
            cards.put(Card.HEARTS_NINE, heartsTexturePackLibrary.get(Card.HEARTS_NINE.getId_184x256()));
            cards.put(Card.HEARTS_TEN, heartsTexturePackLibrary.get(Card.HEARTS_TEN.getId_184x256()));
            cards.put(Card.HEARTS_JACK, heartsTexturePackLibrary.get(Card.HEARTS_JACK.getId_184x256()));
            cards.put(Card.HEARTS_QUEEN, heartsTexturePackLibrary.get(Card.HEARTS_QUEEN.getId_184x256()));
            cards.put(Card.HEARTS_KING, heartsTexturePackLibrary.get(Card.HEARTS_KING.getId_184x256()));

            final TexturePack spadesTexturePack = texturePackLoader.loadFromAsset(
                    baseGameActivity.getAssets(),  CardFile.FILE_184x256_SPADES.getFile());
            spadesTexturePack.loadTexture();
            TexturePackTextureRegionLibrary spadesTexturePackLibrary =
                    spadesTexturePack.getTexturePackTextureRegionLibrary();

            cards.put(Card.SPADES_ACE, spadesTexturePackLibrary.get(Card.SPADES_ACE.getId_184x256()));
            cards.put(Card.SPADES_TWO, spadesTexturePackLibrary.get(Card.SPADES_TWO.getId_184x256()));
            cards.put(Card.SPADES_THREE, spadesTexturePackLibrary.get(Card.SPADES_THREE.getId_184x256()));
            cards.put(Card.SPADES_FOUR, spadesTexturePackLibrary.get(Card.SPADES_FOUR.getId_184x256()));
            cards.put(Card.SPADES_FIVE, spadesTexturePackLibrary.get(Card.SPADES_FIVE.getId_184x256()));
            cards.put(Card.SPADES_SIX, spadesTexturePackLibrary.get(Card.SPADES_SIX.getId_184x256()));
            cards.put(Card.SPADES_SEVEN, spadesTexturePackLibrary.get(Card.SPADES_SEVEN.getId_184x256()));
            cards.put(Card.SPADES_EIGHT, spadesTexturePackLibrary.get(Card.SPADES_EIGHT.getId_184x256()));
            cards.put(Card.SPADES_NINE, spadesTexturePackLibrary.get(Card.SPADES_NINE.getId_184x256()));
            cards.put(Card.SPADES_TEN, spadesTexturePackLibrary.get(Card.SPADES_TEN.getId_184x256()));
            cards.put(Card.SPADES_JACK, spadesTexturePackLibrary.get(Card.SPADES_JACK.getId_184x256()));
            cards.put(Card.SPADES_QUEEN, spadesTexturePackLibrary.get(Card.SPADES_QUEEN.getId_184x256()));
            cards.put(Card.SPADES_KING, spadesTexturePackLibrary.get(Card.SPADES_KING.getId_184x256()));

            final TexturePack backsTexturePack = texturePackLoader.loadFromAsset(
                    baseGameActivity.getAssets(),  CardFile.FILE_184x256_BACKS.getFile());
            backsTexturePack.loadTexture();
            TexturePackTextureRegionLibrary backsTexturePackLibrary =
                    backsTexturePack.getTexturePackTextureRegionLibrary();

            cards.put(Card.BACK_BLUE, backsTexturePackLibrary.get(Card.BACK_BLUE.getId_184x256()));
            cards.put(Card.BACK_RED, backsTexturePackLibrary.get(Card.BACK_RED.getId_184x256()));
        } catch (final TexturePackParseException e) {
            Debug.e(e);
        }

        return cards;
    }

    public static Map<Card,ITextureRegion> loadCardsFullSize(final BaseGameActivity baseGameActivity) {
        Map<Card,ITextureRegion> cards = new HashMap<Card, ITextureRegion>(Card.values().length);
        try {
            TexturePackLoader texturePackLoader = new TexturePackLoader(baseGameActivity.getTextureManager(),
                    CardLocation.LOCATION_FULL.getLocation());
            final TexturePack clubsNumberedTexturePack = texturePackLoader.loadFromAsset(
                    baseGameActivity.getAssets(), CardFile.FILE_FULL_CLUBS_NUMBERED.getFile());
            clubsNumberedTexturePack.loadTexture();
            TexturePackTextureRegionLibrary clubsNumberedTexturePackLibrary =
                    clubsNumberedTexturePack.getTexturePackTextureRegionLibrary();

            cards.put(Card.CLUBS_TWO, clubsNumberedTexturePackLibrary.get(Card.CLUBS_TWO.getId_fullSize()));
            cards.put(Card.CLUBS_THREE, clubsNumberedTexturePackLibrary.get(Card.CLUBS_THREE.getId_fullSize()));
            cards.put(Card.CLUBS_FOUR, clubsNumberedTexturePackLibrary.get(Card.CLUBS_FOUR.getId_fullSize()));
            cards.put(Card.CLUBS_FIVE, clubsNumberedTexturePackLibrary.get(Card.CLUBS_FIVE.getId_fullSize()));
            cards.put(Card.CLUBS_SIX, clubsNumberedTexturePackLibrary.get(Card.CLUBS_SIX.getId_fullSize()));
            cards.put(Card.CLUBS_SEVEN, clubsNumberedTexturePackLibrary.get(Card.CLUBS_SEVEN.getId_fullSize()));
            cards.put(Card.CLUBS_EIGHT, clubsNumberedTexturePackLibrary.get(Card.CLUBS_EIGHT.getId_fullSize()));
            cards.put(Card.CLUBS_NINE, clubsNumberedTexturePackLibrary.get(Card.CLUBS_NINE.getId_fullSize()));
            cards.put(Card.CLUBS_TEN, clubsNumberedTexturePackLibrary.get(Card.CLUBS_TEN.getId_fullSize()));

            final TexturePack clubsLetteredTexturePack = texturePackLoader.loadFromAsset(
                    baseGameActivity.getAssets(), CardFile.FILE_FULL_CLUBS_LETTERED.getFile());
            clubsLetteredTexturePack.loadTexture();
            TexturePackTextureRegionLibrary clubsLetteredTexturePackLibrary =
                    clubsLetteredTexturePack.getTexturePackTextureRegionLibrary();

            cards.put(Card.CLUBS_ACE, clubsLetteredTexturePackLibrary.get(Card.CLUBS_ACE.getId_fullSize()));
            cards.put(Card.CLUBS_JACK, clubsLetteredTexturePackLibrary.get(Card.CLUBS_JACK.getId_fullSize()));
            cards.put(Card.CLUBS_QUEEN, clubsLetteredTexturePackLibrary.get(Card.CLUBS_QUEEN.getId_fullSize()));
            cards.put(Card.CLUBS_KING, clubsLetteredTexturePackLibrary.get(Card.CLUBS_KING.getId_fullSize()));

            final TexturePack diamondsNumberedTexturePack = texturePackLoader.loadFromAsset(
                    baseGameActivity.getAssets(), CardFile.FILE_FULL_DIAMONDS_NUMBERED.getFile());
            diamondsNumberedTexturePack.loadTexture();
            TexturePackTextureRegionLibrary diamondsNumberedTexturePackLibrary =
                    diamondsNumberedTexturePack.getTexturePackTextureRegionLibrary();

            cards.put(Card.DIAMONDS_TWO, diamondsNumberedTexturePackLibrary.get(Card.DIAMONDS_TWO.getId_fullSize()));
            cards.put(Card.DIAMONDS_THREE, diamondsNumberedTexturePackLibrary.get(Card.DIAMONDS_THREE.getId_fullSize()));
            cards.put(Card.DIAMONDS_FOUR, diamondsNumberedTexturePackLibrary.get(Card.DIAMONDS_FOUR.getId_fullSize()));
            cards.put(Card.DIAMONDS_FIVE, diamondsNumberedTexturePackLibrary.get(Card.DIAMONDS_FIVE.getId_fullSize()));
            cards.put(Card.DIAMONDS_SIX, diamondsNumberedTexturePackLibrary.get(Card.DIAMONDS_SIX.getId_fullSize()));
            cards.put(Card.DIAMONDS_SEVEN, diamondsNumberedTexturePackLibrary.get(Card.DIAMONDS_SEVEN.getId_fullSize()));
            cards.put(Card.DIAMONDS_EIGHT, diamondsNumberedTexturePackLibrary.get(Card.DIAMONDS_EIGHT.getId_fullSize()));
            cards.put(Card.DIAMONDS_NINE, diamondsNumberedTexturePackLibrary.get(Card.DIAMONDS_NINE.getId_fullSize()));
            cards.put(Card.DIAMONDS_TEN, diamondsNumberedTexturePackLibrary.get(Card.DIAMONDS_TEN.getId_fullSize()));

            final TexturePack diamondsLetteredTexturePack = texturePackLoader.loadFromAsset(
                    baseGameActivity.getAssets(), CardFile.FILE_FULL_DIAMONDS_LETTERED.getFile());
            diamondsLetteredTexturePack.loadTexture();
            TexturePackTextureRegionLibrary diamondsLetteredTexturePackLibrary =
                    diamondsLetteredTexturePack.getTexturePackTextureRegionLibrary();

            cards.put(Card.DIAMONDS_ACE, diamondsLetteredTexturePackLibrary.get(Card.DIAMONDS_ACE.getId_fullSize()));
            cards.put(Card.DIAMONDS_JACK, diamondsLetteredTexturePackLibrary.get(Card.DIAMONDS_JACK.getId_fullSize()));
            cards.put(Card.DIAMONDS_QUEEN, diamondsLetteredTexturePackLibrary.get(Card.DIAMONDS_QUEEN.getId_fullSize()));
            cards.put(Card.DIAMONDS_KING, diamondsLetteredTexturePackLibrary.get(Card.DIAMONDS_KING.getId_fullSize()));

            final TexturePack heartsNumberedTexturePack = texturePackLoader.loadFromAsset(
                    baseGameActivity.getAssets(), CardFile.FILE_FULL_HEARTS_NUMBERED.getFile());
            heartsNumberedTexturePack.loadTexture();
            TexturePackTextureRegionLibrary heartsNumberedTexturePackLibrary =
                    heartsNumberedTexturePack.getTexturePackTextureRegionLibrary();

            cards.put(Card.HEARTS_TWO, heartsNumberedTexturePackLibrary.get(Card.HEARTS_TWO.getId_fullSize()));
            cards.put(Card.HEARTS_THREE, heartsNumberedTexturePackLibrary.get(Card.HEARTS_THREE.getId_fullSize()));
            cards.put(Card.HEARTS_FOUR, heartsNumberedTexturePackLibrary.get(Card.HEARTS_FOUR.getId_fullSize()));
            cards.put(Card.HEARTS_FIVE, heartsNumberedTexturePackLibrary.get(Card.HEARTS_FIVE.getId_fullSize()));
            cards.put(Card.HEARTS_SIX, heartsNumberedTexturePackLibrary.get(Card.HEARTS_SIX.getId_fullSize()));
            cards.put(Card.HEARTS_SEVEN, heartsNumberedTexturePackLibrary.get(Card.HEARTS_SEVEN.getId_fullSize()));
            cards.put(Card.HEARTS_EIGHT, heartsNumberedTexturePackLibrary.get(Card.HEARTS_EIGHT.getId_fullSize()));
            cards.put(Card.HEARTS_NINE, heartsNumberedTexturePackLibrary.get(Card.HEARTS_NINE.getId_fullSize()));
            cards.put(Card.HEARTS_TEN, heartsNumberedTexturePackLibrary.get(Card.HEARTS_TEN.getId_fullSize()));

            final TexturePack heartsLetteredTexturePack = texturePackLoader.loadFromAsset(
                    baseGameActivity.getAssets(), CardFile.FILE_FULL_HEARTS_LETTERED.getFile());
            heartsLetteredTexturePack.loadTexture();
            TexturePackTextureRegionLibrary heartsLetteredTexturePackLibrary =
                    heartsLetteredTexturePack.getTexturePackTextureRegionLibrary();

            cards.put(Card.HEARTS_ACE, heartsLetteredTexturePackLibrary.get(Card.HEARTS_ACE.getId_fullSize()));
            cards.put(Card.HEARTS_JACK, heartsLetteredTexturePackLibrary.get(Card.HEARTS_JACK.getId_fullSize()));
            cards.put(Card.HEARTS_QUEEN, heartsLetteredTexturePackLibrary.get(Card.HEARTS_QUEEN.getId_fullSize()));
            cards.put(Card.HEARTS_KING, heartsLetteredTexturePackLibrary.get(Card.HEARTS_KING.getId_fullSize()));

            final TexturePack spadesNumberedTexturePack = texturePackLoader.loadFromAsset(
                    baseGameActivity.getAssets(), CardFile.FILE_FULL_SPADES_NUMBERED.getFile());
            spadesNumberedTexturePack.loadTexture();
            TexturePackTextureRegionLibrary spadesNumberedTexturePackLibrary =
                    spadesNumberedTexturePack.getTexturePackTextureRegionLibrary();

            cards.put(Card.SPADES_TWO, spadesNumberedTexturePackLibrary.get(Card.SPADES_TWO.getId_fullSize()));
            cards.put(Card.SPADES_THREE, spadesNumberedTexturePackLibrary.get(Card.SPADES_THREE.getId_fullSize()));
            cards.put(Card.SPADES_FOUR, spadesNumberedTexturePackLibrary.get(Card.SPADES_FOUR.getId_fullSize()));
            cards.put(Card.SPADES_FIVE, spadesNumberedTexturePackLibrary.get(Card.SPADES_FIVE.getId_fullSize()));
            cards.put(Card.SPADES_SIX, spadesNumberedTexturePackLibrary.get(Card.SPADES_SIX.getId_fullSize()));
            cards.put(Card.SPADES_SEVEN, spadesNumberedTexturePackLibrary.get(Card.SPADES_SEVEN.getId_fullSize()));
            cards.put(Card.SPADES_EIGHT, spadesNumberedTexturePackLibrary.get(Card.SPADES_EIGHT.getId_fullSize()));
            cards.put(Card.SPADES_NINE, spadesNumberedTexturePackLibrary.get(Card.SPADES_NINE.getId_fullSize()));
            cards.put(Card.SPADES_TEN, spadesNumberedTexturePackLibrary.get(Card.SPADES_TEN.getId_fullSize()));

            final TexturePack spadesLetteredTexturePack = texturePackLoader.loadFromAsset(
                    baseGameActivity.getAssets(), CardFile.FILE_FULL_SPADES_LETTERED.getFile());
            spadesLetteredTexturePack.loadTexture();
            TexturePackTextureRegionLibrary spadesLetteredTexturePackLibrary =
                    spadesLetteredTexturePack.getTexturePackTextureRegionLibrary();

            cards.put(Card.SPADES_ACE, spadesLetteredTexturePackLibrary.get(Card.SPADES_ACE.getId_fullSize()));
            cards.put(Card.SPADES_JACK, spadesLetteredTexturePackLibrary.get(Card.SPADES_JACK.getId_fullSize()));
            cards.put(Card.SPADES_QUEEN, spadesLetteredTexturePackLibrary.get(Card.SPADES_QUEEN.getId_fullSize()));
            cards.put(Card.SPADES_KING, spadesLetteredTexturePackLibrary.get(Card.SPADES_KING.getId_fullSize()));

            final TexturePack backsTexturePack = texturePackLoader.loadFromAsset(
                    baseGameActivity.getAssets(), CardFile.FILE_FULL_BACKS.getFile());
            backsTexturePack.loadTexture();
            TexturePackTextureRegionLibrary backsTexturePackLibrary =
                    backsTexturePack.getTexturePackTextureRegionLibrary();

            cards.put(Card.BACK_BLUE, backsTexturePackLibrary.get(Card.BACK_BLUE.getId_fullSize()));
            cards.put(Card.BACK_RED, backsTexturePackLibrary.get(Card.BACK_RED.getId_fullSize()));
        } catch (final TexturePackParseException e) {
            Debug.e(e);
        }

        return cards;
    }

    public static ITextureRegion[] loadSignInButton(final BaseGameActivity baseGameActivity) {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/buttons/");
        // Width and height have to be powers of 2 or this won't work.
        BuildableBitmapTextureAtlas buttonTextureAtlas = new BuildableBitmapTextureAtlas(
                baseGameActivity.getTextureManager(), 1024, 1024, TextureOptions.NEAREST);

        ITextureRegion signInUpButtonRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                buttonTextureAtlas, baseGameActivity, "sign-in-up.png");
        ITextureRegion signInDownButtonRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                buttonTextureAtlas, baseGameActivity, "sign-in-down.png");
        ITextureRegion signInDisabledButtonRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                buttonTextureAtlas, baseGameActivity, "sign-in-disabled.png");

        try {
            buttonTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
            buttonTextureAtlas.load();

            ITextureRegion[] textureRegions = new ITextureRegion[3];
            textureRegions[BUTTON_STATE_UP] = signInUpButtonRegion;
            textureRegions[BUTTON_STATE_DOWN] = signInDownButtonRegion;
            textureRegions[BUTTON_STATE_DISABLED] = signInDisabledButtonRegion;
            return textureRegions;
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            Debug.e(e);
        }
        return null;
    }

    public static ITextureRegion[] loadSignOutButton(final BaseGameActivity baseGameActivity) {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/buttons/");
        // Width and height have to be powers of 2 or this won't work.
        BuildableBitmapTextureAtlas buttonTextureAtlas = new BuildableBitmapTextureAtlas(
                baseGameActivity.getTextureManager(), 1024, 1024, TextureOptions.NEAREST);

        ITextureRegion signOutUpButtonRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                buttonTextureAtlas, baseGameActivity, "sign-out-up.png");
        ITextureRegion signOutDownButtonRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                buttonTextureAtlas, baseGameActivity, "sign-out-down.png");
        ITextureRegion signOutDisabledButtonRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                buttonTextureAtlas, baseGameActivity, "sign-out-disabled.png");

        try {
            buttonTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
            buttonTextureAtlas.load();

            ITextureRegion[] textureRegions = new ITextureRegion[3];
            textureRegions[BUTTON_STATE_UP] = signOutUpButtonRegion;
            textureRegions[BUTTON_STATE_DOWN] = signOutDownButtonRegion;
            textureRegions[BUTTON_STATE_DISABLED] = signOutDisabledButtonRegion;
            return textureRegions;
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            Debug.e(e);
        }
        return null;
    }

    public static ITextureRegion[] loadRegisterButton(final BaseGameActivity baseGameActivity) {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/buttons/");
        // Width and height have to be powers of 2 or this won't work.
        BuildableBitmapTextureAtlas buttonTextureAtlas = new BuildableBitmapTextureAtlas(
                baseGameActivity.getTextureManager(), 1024, 1024, TextureOptions.NEAREST);

        ITextureRegion registerUpButtonRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                buttonTextureAtlas, baseGameActivity, "register-up.png");
        ITextureRegion registerDownButtonRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                buttonTextureAtlas, baseGameActivity, "register-down.png");
        ITextureRegion registerDisabledButtonRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                buttonTextureAtlas, baseGameActivity, "register-disabled.png");

        try {
            buttonTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
            buttonTextureAtlas.load();

            ITextureRegion[] textureRegions = new ITextureRegion[3];
            textureRegions[BUTTON_STATE_UP] = registerUpButtonRegion;
            textureRegions[BUTTON_STATE_DOWN] = registerDownButtonRegion;
            textureRegions[BUTTON_STATE_DISABLED] = registerDisabledButtonRegion;
            return textureRegions;
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            Debug.e(e);
        }
        return null;
    }

    public static ITextureRegion[] loadDemosButton(final BaseGameActivity baseGameActivity) {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/buttons/");
        // Width and height have to be powers of 2 or this won't work.
        BuildableBitmapTextureAtlas buttonTextureAtlas = new BuildableBitmapTextureAtlas(
                baseGameActivity.getTextureManager(), 1024, 1024, TextureOptions.NEAREST);

        ITextureRegion demosUpButtonRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                buttonTextureAtlas, baseGameActivity, "demos-up.png");
        ITextureRegion demosDownButtonRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                buttonTextureAtlas, baseGameActivity, "demos-down.png");
        ITextureRegion demosDisabledButtonRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                buttonTextureAtlas, baseGameActivity, "demos-disabled.png");

        try {
            buttonTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
            buttonTextureAtlas.load();

            ITextureRegion[] textureRegions = new ITextureRegion[3];
            textureRegions[BUTTON_STATE_UP] = demosUpButtonRegion;
            textureRegions[BUTTON_STATE_DOWN] = demosDownButtonRegion;
            textureRegions[BUTTON_STATE_DISABLED] = demosDisabledButtonRegion;
            return textureRegions;
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            Debug.e(e);
        }
        return null;
    }

    public static ITextureRegion[] loadFriendsButton(final BaseGameActivity baseGameActivity) {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/buttons/");
        // Width and height have to be powers of 2 or this won't work.
        BuildableBitmapTextureAtlas buttonTextureAtlas = new BuildableBitmapTextureAtlas(
                baseGameActivity.getTextureManager(), 1024, 1024, TextureOptions.NEAREST);

        ITextureRegion friendsUpButtonRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                buttonTextureAtlas, baseGameActivity, "friends-up.png");
        ITextureRegion friendsDownButtonRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                buttonTextureAtlas, baseGameActivity, "friends-down.png");
        ITextureRegion friendsDisabledButtonRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                buttonTextureAtlas, baseGameActivity, "friends-disabled.png");

        try {
            buttonTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
            buttonTextureAtlas.load();

            ITextureRegion[] textureRegions = new ITextureRegion[3];
            textureRegions[BUTTON_STATE_UP] = friendsUpButtonRegion;
            textureRegions[BUTTON_STATE_DOWN] = friendsDownButtonRegion;
            textureRegions[BUTTON_STATE_DISABLED] = friendsDisabledButtonRegion;
            return textureRegions;
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            Debug.e(e);
        }
        return null;
    }

    public static ITextureRegion[] loadGamesButton(final BaseGameActivity baseGameActivity) {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/buttons/");
        // Width and height have to be powers of 2 or this won't work.
        BuildableBitmapTextureAtlas buttonTextureAtlas = new BuildableBitmapTextureAtlas(
                baseGameActivity.getTextureManager(), 1024, 1024, TextureOptions.NEAREST);

        ITextureRegion gamesUpButtonRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                buttonTextureAtlas, baseGameActivity, "games-up.png");
        ITextureRegion gamesDownButtonRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                buttonTextureAtlas, baseGameActivity, "games-down.png");
        ITextureRegion gamesDisabledButtonRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                buttonTextureAtlas, baseGameActivity, "games-disabled.png");

        try {
            buttonTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
            buttonTextureAtlas.load();

            ITextureRegion[] textureRegions = new ITextureRegion[3];
            textureRegions[BUTTON_STATE_UP] = gamesUpButtonRegion;
            textureRegions[BUTTON_STATE_DOWN] = gamesDownButtonRegion;
            textureRegions[BUTTON_STATE_DISABLED] = gamesDisabledButtonRegion;
            return textureRegions;
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            Debug.e(e);
        }
        return null;
    }

    public static ITextureRegion[] loadGameMenuButton(final BaseGameActivity baseGameActivity) {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/buttons/");
        // Width and height have to be powers of 2 or this won't work.
        BuildableBitmapTextureAtlas buttonTextureAtlas = new BuildableBitmapTextureAtlas(
                baseGameActivity.getTextureManager(), 128, 256, TextureOptions.NEAREST);

        ITextureRegion gameMenuUpButtonRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                buttonTextureAtlas, baseGameActivity, "game-menu-up.png");
        ITextureRegion gameMenuDownButtonRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                buttonTextureAtlas, baseGameActivity, "game-menu-down.png");

        try {
            buttonTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
            buttonTextureAtlas.load();

            ITextureRegion[] textureRegions = new ITextureRegion[3];
            textureRegions[BUTTON_STATE_UP] = gameMenuUpButtonRegion;
            textureRegions[BUTTON_STATE_DOWN] = gameMenuDownButtonRegion;
            return textureRegions;
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            Debug.e(e);
        }
        return null;
    }
}
