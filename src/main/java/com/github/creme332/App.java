package com.github.creme332;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.github.creme332.controller.Controller;

public class App {
    public static void main(String[] args) {
        FlatIntelliJLaf.setup();
        new Controller();
    }
}