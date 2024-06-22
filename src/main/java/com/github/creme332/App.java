package com.github.creme332;

import com.github.creme332.controller.Controller;
import com.github.creme332.model.MaterialCondition;
import com.github.creme332.view.MyTheme;

public class App {
    public static void main(String[] args) {
        // MyTheme.setup();
        // new Controller();
        MaterialCondition a = MaterialCondition.NEW;
        System.out.println(a);
        System.out.println(MaterialCondition.fromString("Used -  Acceptable"));
    }
}