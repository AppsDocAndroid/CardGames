package android.otasyn.cardgames.entity;

import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Line;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

public class PositionBox extends Entity {

    private final static float DEFAULT_ANGLE = 0;
    private final static Color DEFAULT_COLOR = Color.BLACK;
    private final static float DEFAULT_LINE_WIDTH = 5;

    private final float x;
    private final float y;
    private final float width;
    private final float height;
    private final Color color;
    private final float lineWidth;
    private final VertexBufferObjectManager vertexBufferObjectManager;

    /**
     * Coordinates represent the top center of the box.
     *
     * Angle defaults to 0.
     * Line Width default to 5.
     * @param x Top center X
     * @param y Top center Y
     * @param width Box width
     * @param height Box height
     * @param vertexBufferObjectManager Should be provided by the GameActivity
     */
    public PositionBox(final float x, final float y, final float width, final float height,
                       final VertexBufferObjectManager vertexBufferObjectManager) {
        this(x, y, width, height, DEFAULT_ANGLE, DEFAULT_COLOR, DEFAULT_LINE_WIDTH, vertexBufferObjectManager);
    }

    /**
     * Coordinates represent the top center of the box.
     *
     * Angle defaults to 0.
     * Line Width default to 5.
     * @param x Top center X.
     * @param y Top center Y.
     * @param width Box width.
     * @param height Box height.
     * @param angle Tilt angle of the box in degrees.  0 for a horizontal top.
     * @param vertexBufferObjectManager Should be provided by the GameActivity
     */
    public PositionBox(final float x, final float y, final float width, final float height,
                       final float angle, final VertexBufferObjectManager vertexBufferObjectManager) {
        this(x, y, width, height, angle, DEFAULT_COLOR, DEFAULT_LINE_WIDTH, vertexBufferObjectManager);
    }

    /**
     * Coordinates represent the top center of the box.
     * @param x Top center X.
     * @param y Top center Y.
     * @param width Box width.
     * @param height Box height.
     * @param angle Tilt angle of the box in degrees.  0 for a horizontal top.
     * @param lineWidth Line width of the box borders.
     * @param vertexBufferObjectManager Should be provided by the GameActivity
     */
    public PositionBox(final float x, final float y, final float width, final float height,
                       final float angle, final Color color, final float lineWidth,
                       final VertexBufferObjectManager vertexBufferObjectManager) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.lineWidth = lineWidth;
        this.vertexBufferObjectManager = vertexBufferObjectManager;

        drawLines();
        setRotationCenter(x, y);
        setRotation(angle);
    }

    private void drawLines() {
        float leftX = x - (width / 2f);
        float rightX = x + (width / 2f);
        float bottomY = y + height;

        final Line top = new Line(leftX, y, rightX, y, lineWidth, vertexBufferObjectManager);
        top.setColor(color);
        this.attachChild(top);

        final Line right = new Line(rightX, y, rightX, bottomY, lineWidth, vertexBufferObjectManager);
        right.setColor(color);
        this.attachChild(right);

        final Line bottom = new Line(rightX, y + height, leftX, y + height, lineWidth, vertexBufferObjectManager);
        bottom.setColor(color);
        this.attachChild(bottom);

        final Line left = new Line(leftX, bottomY, leftX, y, lineWidth, vertexBufferObjectManager);
        left.setColor(color);
        this.attachChild(left);
    }
}
