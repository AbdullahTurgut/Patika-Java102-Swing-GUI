package com.patikadev.Helper;

import javax.swing.*;
import java.awt.*;

public class Helper {

    // işimizi kolaylaştıracak methodlar
    public static void setLayout() {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                         UnsupportedLookAndFeelException e) {
                    throw new RuntimeException(e);
                }
                break; // bulduktan sonra aramayı bıraktırmak için
            }
        }
    }

    public static int screenCenterPoint(String axis, Dimension size) {
        int point = 0;
        switch (axis) {
            case "x":
                point = (Toolkit.getDefaultToolkit().getScreenSize().width - size.width) / 2;
                break;
            case "y":
                point = (Toolkit.getDefaultToolkit().getScreenSize().height - size.height) / 2;
                break;
            default:
                point = 0;
        }
        return point;
    }

    // textField ların içi dolu mu boş mu için boolean döndürecek method
    public static boolean isFieldEmpty(JTextField field) {
        return field.getText().trim().isEmpty(); // boş ise
    }

    // JOptionPane ile mesaj gösterme kısmı
    public static void showMsg(String str) {
        optionPaneTR();
        String msg;
        String title;
        switch (str) {
            case "fill":
                msg = "Lütfen tüm alanları doldurunuz !";
                title = "Hata !";
                break;
            case "done":
                msg = "İşlem başarılı !";
                title = "Sonuç";
                break;
            case "error":
                msg = "Bir hata oluştu !";
                title = "Hata!";
                break;
            default:
                msg = str;
                title = "Mesaj";
        }

        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }

    // hata mesajındaki Ok kısmı için
    public static void optionPaneTR() {
        UIManager.put("OptionPane.okButtonText", "Tamam");
    }
}
