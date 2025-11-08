package org.netbeans.lib.awtextra;

import java.awt.Rectangle;

/**
 * Minimal replacement for NetBeans' AbsoluteConstraints used by the GUI builder.
 * This stores x,y,width,height and can produce a Rectangle.
 */
public class AbsoluteConstraints {
    public int x;
    public int y;
    public int width;
    public int height;

    public AbsoluteConstraints(int x, int y) {
        this(x, y, -1, -1);
    }

    public AbsoluteConstraints(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    @Override
    public String toString() {
        return "AbsoluteConstraints[" + x + "," + y + "," + width + "," + height + "]";
    }
}
