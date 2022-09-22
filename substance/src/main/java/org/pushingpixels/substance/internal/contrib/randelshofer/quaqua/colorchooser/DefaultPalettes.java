/*
 * @(#)ColorPalettes.java  1.0  September 18, 2005
 *
 * Copyright (c) 2005 Werner Randelshofer
 * Staldenmattweg 2, Immensee, CH-6405, Switzerland.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Werner Randelshofer. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Werner Randelshofer.
 */

package org.pushingpixels.substance.internal.contrib.randelshofer.quaqua.colorchooser;

import java.awt.*;
/**
 * This class provides some well known color palettes as array constants.
 *
 * @author  Werner Randelshofer
 * @version 1.0 September 18, 2005 Created.
 */
public class DefaultPalettes {
    public final static Color[] CRAYONS = {
    };
    
    public final static Color[] APPLE_COLORS = {
};
        
    public final static Color[] WINDOWS_BASIC_COLORS = {
    };
    
    public final static Color[] WEB_SAFE_COLORS = {
    };
    
    
    public final static Color[] MS_OFFICE_COLORS = {
    };

    /**
     * Prevent instance creation.
     */
    private DefaultPalettes() {
    }
    /*
    public static void main(String[] args) {
        for (int i=0; i < WINDOWS_BASIC_COLORS.length; i+=2) {
            System.out.println("new Color(0x"
            +Integer.toHexString(((Color)WINDOWS_BASIC_COLORS[i+1]).getRGB()).substring(2)
            +"), //"+WINDOWS_BASIC_COLORS[i]);
        }
    }*/
}
