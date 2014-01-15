package android.otasyn.cardgames.entity;

import android.otasyn.cardgames.sprite.CardSprite;
import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

import java.util.List;

public class HandHighlight extends Entity {

    public HandHighlight(List<CardSprite> hand, final float padding, final Color color,
                         final VertexBufferObjectManager vertexBufferObjectManager) {
        drawHighlights(hand, padding, color, vertexBufferObjectManager);
    }

    public void drawHighlights(List<CardSprite> hand, final float padding, final Color color,
                               final VertexBufferObjectManager vertexBufferObjectManager) {
        for (CardSprite card : hand) {
            float x = card.getX() - padding;
            float y = card.getY() - padding;
            float width = card.getWidth() + (padding * 2);
            float height = card.getHeight() + (padding * 2);

            Rectangle highlight = new Rectangle(x, y, width, height, vertexBufferObjectManager);
            highlight.setColor(color);
            highlight.setAlpha(color.getAlpha());
            this.attachChild(highlight);
        }
    }
}
