package com.kencloud.partner.user.FontAwesome;

import com.joanzapata.iconify.Icon;
import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

public class FontAwesomeModule implements IconFontDescriptor {

    @Override
    public String ttfFileName() {
        return "fonts/android-iconify-fontawesome.ttf";
    }

    @Override
    public Icon[] characters() {
        return FontAwesomeIcons.values();
    }
}
