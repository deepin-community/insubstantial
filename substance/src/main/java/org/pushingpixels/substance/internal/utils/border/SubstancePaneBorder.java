/*
 * Copyright (c) 2005-2010 Substance Kirill Grouchnikov. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 *  o Redistributions of source code must retain the above copyright notice, 
 *    this list of conditions and the following disclaimer. 
 *     
 *  o Redistributions in binary form must reproduce the above copyright notice, 
 *    this list of conditions and the following disclaimer in the documentation 
 *    and/or other materials provided with the distribution. 
 *     
 *  o Neither the name of Substance Kirill Grouchnikov nor the names of 
 *    its contributors may be used to endorse or promote products derived 
 *    from this software without specific prior written permission. 
 *     
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, 
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR 
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR 
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; 
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, 
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
 */
package org.pushingpixels.substance.internal.utils.border;

import java.awt.*;

import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.border.AbstractBorder;
import javax.swing.plaf.UIResource;

import org.pushingpixels.substance.api.*;
import org.pushingpixels.substance.internal.utils.SubstanceCoreUtilities;

/**
 * Root pane and internal frame border in <b>Substance</b> look and feel. This
 * class is <b>for internal use only</b>.
 * 
 * @author Kirill Grouchnikov
 */
public class SubstancePaneBorder extends AbstractBorder implements UIResource {
	/**
	 * Default border thickness.
	 */
	private static final int BORDER_THICKNESS = 4;
    private static final int BORDER_ROUNDNESS = 12;

	/**
	 * Default insets.
	 */
	private static final Insets INSETS = new Insets(
			SubstancePaneBorder.BORDER_THICKNESS,
			SubstancePaneBorder.BORDER_THICKNESS,
			SubstancePaneBorder.BORDER_THICKNESS,
			SubstancePaneBorder.BORDER_THICKNESS);

    public static DecorationAreaType getRootPaneType(JRootPane rootPane) {
        DecorationAreaType type = SubstanceLookAndFeel.getDecorationType(rootPane);
        if ((type == null) || (type == DecorationAreaType.NONE)) {
            if (SubstanceCoreUtilities.isPaintRootPaneActivated(rootPane)) {
                if (SubstanceCoreUtilities.isSecondaryWindow(rootPane)) {
                    type = DecorationAreaType.SECONDARY_TITLE_PANE;
                } else  {
                    type = DecorationAreaType.PRIMARY_TITLE_PANE;
                }
            } else {
                if (SubstanceCoreUtilities.isSecondaryWindow(rootPane)) {
                    type = DecorationAreaType.SECONDARY_TITLE_PANE_INACTIVE;
                } else  {
                    type = DecorationAreaType.PRIMARY_TITLE_PANE_INACTIVE;
                }
            }
        } else if (type == DecorationAreaType.PRIMARY_TITLE_PANE) {
            if (!SubstanceCoreUtilities.isPaintRootPaneActivated(rootPane)) {
                type =DecorationAreaType.PRIMARY_TITLE_PANE_INACTIVE;
            }
        } else if (type == DecorationAreaType.SECONDARY_TITLE_PANE) {
            if (!SubstanceCoreUtilities.isPaintRootPaneActivated(rootPane)) {
                type =DecorationAreaType.SECONDARY_TITLE_PANE_INACTIVE;
            }
        }
        return type;
    }

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.swing.border.Border#paintBorder(java.awt.Component,
	 * java.awt.Graphics, int, int, int, int)
	 */
	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
        if (SubstanceCoreUtilities.isRoundedCorners(c)) {
            paintRoundedBorder(c, g, x, y, w, h);
        } else {
            paintSquareBorder(c, g, x, y, w, h);
        }
    }

    public void paintSquareBorder(Component c, Graphics g, int x, int y, int w, int h) {
		SubstanceSkin skin = SubstanceCoreUtilities.getSkin(c);
		if (skin == null)
			return;


		SubstanceColorScheme scheme = skin
				.getBackgroundColorScheme(getRootPaneType(SwingUtilities.getRootPane(c)));
		Component titlePaneComp = SubstanceLookAndFeel
				.getTitlePaneComponent(SwingUtilities.windowForComponent(c));
		SubstanceColorScheme borderScheme = skin.getColorScheme(titlePaneComp,
				ColorSchemeAssociationKind.BORDER, ComponentState.ENABLED);

		Graphics2D graphics = (Graphics2D) g;

		// bottom and right in ultra dark
		graphics.setColor(borderScheme.getUltraDarkColor());
		graphics.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
		graphics.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
		// top and left
		graphics.setColor(borderScheme.getDarkColor());
		graphics.drawLine(x, y, x + w - 2, y);
		graphics.drawLine(x, y, x, y + h - 2);
		// inner bottom and right
		graphics.setColor(scheme.getMidColor());
		graphics.drawLine(x + 1, y + h - 2, x + w - 2, y + h - 2);
		graphics.drawLine(x + w - 2, y + 1, x + w - 2, y + h - 2);
		// inner top and left
		graphics.setColor(scheme.getMidColor());
		graphics.drawLine(x + 1, y + 1, x + w - 3, y + 1);
		graphics.drawLine(x + 1, y + 1, x + 1, y + h - 3);
		// inner 2 and 3
		graphics.setColor(scheme.getLightColor());
		graphics.drawRect(x + 2, y + 2, w - 5, h - 5);
		graphics.drawRect(x + 3, y + 3, w - 7, h - 7);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.border.Border#getBorderInsets(java.awt.Component)
	 */
	@Override
	public Insets getBorderInsets(Component c) {
		return SubstancePaneBorder.INSETS;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.swing.border.AbstractBorder#getBorderInsets(java.awt.Component,
	 * java.awt.Insets)
	 */
	@Override
	public Insets getBorderInsets(Component c, Insets newInsets) {
		newInsets.top = SubstancePaneBorder.INSETS.top;
		newInsets.left = SubstancePaneBorder.INSETS.left;
		newInsets.bottom = SubstancePaneBorder.INSETS.bottom;
		newInsets.right = SubstancePaneBorder.INSETS.right;
		return newInsets;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.border.Border#isBorderOpaque()
	 */
	@Override
	public boolean isBorderOpaque() {
		return false;
	}

    public void paintRoundedBorder(Component c, Graphics g, int x, int y, int w, int h) {
        SubstanceColorScheme scheme = getColorScheme(c);
        if (scheme == null) return;
        SubstanceColorScheme borderScheme = getBorderColorScheme(c);

        Graphics2D graphics = (Graphics2D) g;

        int xl = x + BORDER_THICKNESS + 2;
        int xr = x + w - BORDER_THICKNESS - 3;
        int yt = y + BORDER_THICKNESS + 2;
        int yb = y + h - BORDER_THICKNESS - 3;

        Object rh = graphics.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // bottom and right in ultra dark
        graphics.setColor(borderScheme.getUltraDarkColor());
        graphics.drawLine(xl, y + h - 1, xr, y + h - 1); // bottom
        graphics.drawLine(x + w - 1, yt, x + w - 1, yb); // right
        // se
        graphics.fillOval(x+w- BORDER_ROUNDNESS,y+h- BORDER_ROUNDNESS, BORDER_ROUNDNESS, BORDER_ROUNDNESS);


        // top and left
        graphics.setColor(borderScheme.getDarkColor());
        graphics.drawLine(xl, y, xr, y);
        graphics.drawLine(x, yt, x, yb);
        // nw, ne, sw
        graphics.fillOval(0        ,0        , BORDER_ROUNDNESS, BORDER_ROUNDNESS);
        graphics.fillOval(0        ,y+h- BORDER_ROUNDNESS, BORDER_ROUNDNESS, BORDER_ROUNDNESS);
        graphics.fillOval(x+w- BORDER_ROUNDNESS,0        , BORDER_ROUNDNESS, BORDER_ROUNDNESS);



        // inner bottom and right
        graphics.setColor(scheme.getMidColor());
        graphics.drawLine(xl, y + h - 2, xr, y + h - 2);
        graphics.drawLine(x + w - 2, yt, x + w - 2, yb);
        graphics.drawLine(xl, y + 1, xr, y + 1);
        graphics.drawLine(x + 1, yt, x + 1, yb);

        graphics.fillOval(1,                            1,                            BORDER_ROUNDNESS, BORDER_ROUNDNESS);
        graphics.fillOval(1,                            y + h - BORDER_ROUNDNESS - 1, BORDER_ROUNDNESS, BORDER_ROUNDNESS);
        graphics.fillOval(x + w - BORDER_ROUNDNESS - 1, 1,                            BORDER_ROUNDNESS, BORDER_ROUNDNESS);
        graphics.fillOval(x + w - BORDER_ROUNDNESS - 1, y + h - BORDER_ROUNDNESS - 1, BORDER_ROUNDNESS, BORDER_ROUNDNESS);



        graphics.setColor(scheme.getLightColor());
        graphics.drawLine(xl,        y + 2,     xr,        y + 2);
        graphics.drawLine(x + 2,     yt,        x + 2,     yb);
        graphics.drawLine(xl,        y + h - 3, xr,        y + h - 3);
        graphics.drawLine(x + w - 3, yt,        x + w - 3, yb);
        graphics.drawLine(xl,        y + 3,     xr,        y + 3);
        graphics.drawLine(x + 3,     yt,        x + 3,     yb);
        graphics.drawLine(xl,        y + h - 4, xr,        y + h - 4);
        graphics.drawLine(x + w - 4, yt,        x + w - 4, yb);

        graphics.fillOval(2,                           2,                            BORDER_ROUNDNESS, BORDER_ROUNDNESS);
        graphics.fillOval(2,                           y + h - BORDER_ROUNDNESS - 2, BORDER_ROUNDNESS, BORDER_ROUNDNESS);
        graphics.fillOval(x + w - BORDER_ROUNDNESS -2, 2,                            BORDER_ROUNDNESS, BORDER_ROUNDNESS);
        graphics.fillOval(x + w - BORDER_ROUNDNESS -2, y + h - BORDER_ROUNDNESS - 2, BORDER_ROUNDNESS, BORDER_ROUNDNESS);


        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, rh);
    }

    private SubstanceColorScheme getColorScheme(Component c) {
        JRootPane rp = c instanceof JRootPane
                ? (JRootPane) c
                : SwingUtilities.getRootPane(c);

        SubstanceSkin skin = SubstanceCoreUtilities.getSkin(c);
        if (skin == null) return null;

        DecorationAreaType type = getRootPaneType(rp);
        return skin.getBackgroundColorScheme(type);
    }

    private SubstanceColorScheme getBorderColorScheme(Component c) {
        JRootPane rp = c instanceof JRootPane
                ? (JRootPane) c
                : SwingUtilities.getRootPane(c);

        SubstanceSkin skin = SubstanceCoreUtilities.getSkin(c);

        if (skin == null) return null;


        Component titlePaneComp = SubstanceLookAndFeel
                .getTitlePaneComponent(SwingUtilities.windowForComponent(c));

        return skin.getColorScheme(getRootPaneType(rp),
                ColorSchemeAssociationKind.BORDER, ComponentState.ENABLED);
    }

}
