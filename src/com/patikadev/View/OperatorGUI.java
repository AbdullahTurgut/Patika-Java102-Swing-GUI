package com.patikadev.View;

import com.patikadev.Helper.*;

import javax.swing.*;


public class OperatorGUI extends JFrame {

    private JPanel wrapper;

    public OperatorGUI() {
        add(wrapper);
        setSize(1000, 500);
        int x = Helper.screenCenterPoint("x", getSize());
        int y = Helper.screenCenterPoint("y", getSize());
        setLocation(x, y); // uygulamanın ekranımızın ortasında başlamasını sağlayacak
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);
    }
}
