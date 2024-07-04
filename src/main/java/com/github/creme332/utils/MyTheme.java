package com.github.creme332.utils;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.InputStream;

import com.formdev.flatlaf.FlatDarculaLaf;

public class MyTheme extends FlatDarculaLaf {
    public static boolean setup() {
        installFonts();
        // look for properties file in theme folder (relative to resource)
        registerCustomDefaultsSource("theme");
        return setup(new MyTheme());
    }

    @Override
    public String getName() {
        return "TizcTheme";
    }

    private static void installFonts() {
        InputStream inputStream;
        String[] fontStyle = { "Regular" };

        for (String style : fontStyle) {
            try {
                String path = String.format("/font/Poppins-%s.ttf", style);
                inputStream = MyTheme.class.getResourceAsStream(path);
                GraphicsEnvironment.getLocalGraphicsEnvironment()
                        .registerFont(Font.createFont(Font.TRUETYPE_FONT, inputStream));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}