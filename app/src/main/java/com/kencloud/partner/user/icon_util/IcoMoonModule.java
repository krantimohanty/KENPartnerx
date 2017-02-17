package com.kencloud.partner.user.icon_util;

import com.joanzapata.iconify.Icon;
import com.joanzapata.iconify.IconFontDescriptor;


public class IcoMoonModule implements IconFontDescriptor {
    @Override
    public String ttfFileName() {
        return "fonts/icomoon.ttf";
    }

    @Override
    public Icon[] characters() {
        return IcoMoonIcons.values();
    }
}
