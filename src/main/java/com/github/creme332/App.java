package com.github.creme332;

import com.github.creme332.controller.Controller;
import com.github.creme332.view.MyTheme;

public class App {
    public static void main(String[] args) {
        MyTheme.setup();
        new Controller();
    }
}