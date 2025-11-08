package org.netbeans.lib.awtextra;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;

/**
 * Minimal replacement for NetBeans' AbsoluteLayout used by the GUI builder.
 * It supports AbsoluteConstraints and will set component bounds accordingly.
 */
public class AbsoluteLayout implements LayoutManager2 {

    private final Map<Component, AbsoluteConstraints> map = new HashMap<>();

    @Override
    public void addLayoutComponent(Component comp, Object constraints) {
        if (constraints instanceof AbsoluteConstraints) {
            map.put(comp, (AbsoluteConstraints) constraints);
        }
    }

    @Override
    public Dimension maximumLayoutSize(Container target) {
        return preferredLayoutSize(target);
    }

    @Override
    public float getLayoutAlignmentX(Container target) {
        return 0.5f;
    }

    @Override
    public float getLayoutAlignmentY(Container target) {
        return 0.5f;
    }

    @Override
    public void invalidateLayout(Container target) {
        // nothing
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {
        // deprecated variant - ignore
    }

    @Override
    public void removeLayoutComponent(Component comp) {
        map.remove(comp);
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        int mx = 0, my = 0;
        for (Component c : parent.getComponents()) {
            AbsoluteConstraints ac = map.get(c);
            if (ac != null) {
                Rectangle r = ac.getBounds();
                int w = r.width >= 0 ? r.width : c.getPreferredSize().width;
                int h = r.height >= 0 ? r.height : c.getPreferredSize().height;
                mx = Math.max(mx, r.x + w);
                my = Math.max(my, r.y + h);
            } else {
                Dimension d = c.getPreferredSize();
                mx = Math.max(mx, d.width);
                my = Math.max(my, d.height);
            }
        }
        return new Dimension(mx, my);
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return preferredLayoutSize(parent);
    }

    @Override
    public void layoutContainer(Container parent) {
        for (Component c : parent.getComponents()) {
            AbsoluteConstraints ac = map.get(c);
            if (ac != null) {
                Rectangle r = ac.getBounds();
                int w = r.width >= 0 ? r.width : c.getPreferredSize().width;
                int h = r.height >= 0 ? r.height : c.getPreferredSize().height;
                c.setBounds(r.x, r.y, w, h);
            }
        }
    }
}
