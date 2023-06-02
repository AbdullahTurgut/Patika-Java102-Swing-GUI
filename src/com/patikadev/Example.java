package com.patikadev;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Example extends JFrame {


    private JPanel wrapper;
    private JPanel wtop;
    private JPanel wbottom;
    private JTextField fld_username;
    private JPasswordField fld_password;
    private JButton btn_login;

    public Example() {
        // Grafikleri biraz daha değiştirmek için
        // Burda for döngüsü ile bilgisayarımızda yüklü olan temaları UIManager ile göreceğiz
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
//            System.out.println(info.getName() + " => " + info.getClassName());
//            Metal-Nimbus-CDE/Motif-Windows-Windows Classic // burdan Nimbus seçicez
            if ("Nimbus".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (UnsupportedLookAndFeelException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        this.add(wrapper); // setContentPane(wrapper); aynı kullanım
        setSize(450, 300); // pencere boyutu
        setTitle("Uygulama Adı");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // close butonuna basınca programı durdurur
        setResizable(false); // ekran genişletip küçültmeyi kapattık

        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2;
        setLocation(x, y); // uygulamanın ekranımızın ortasında başlamasını sağlayacak
        setVisible(true); // bu şekilde gözükücek Main classdan

        // Butonumuza bir actionListener yani basıldığında yapacağımız etkileşimi oluşturacağız
        btn_login.addActionListener(e -> {
            // functional interface olduğu için lambda expressionlar kullanılabilir
            if (fld_username.getText().length() == 0 || fld_password.getText().length() == 0) {
                JOptionPane.showMessageDialog(null,"Boş Bırakılamaz ! ",
                                "Hata",
                                JOptionPane.INFORMATION_MESSAGE); // kullanıcıya mesaj pop-up
            }
//                System.out.println(fld_username.getText()); // kullanıcı adı yazılan texti alırız
        });
    }
}
