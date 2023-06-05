package com.patikadev.View;

import com.patikadev.Helper.*;
import com.patikadev.Model.Operator;
import com.patikadev.Model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class OperatorGUI extends JFrame {

    private JPanel wrapper;
    private JTabbedPane tab_operator;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JButton btn_logout;
    private JPanel pnl_user_list;
    private JScrollPane scrl_user_list;
    private JTable tbl_user_list;
    private JPanel pnl_user_form;
    private JTextField fld_user_name;
    private JTextField fld_user_username;
    private JTextField fld_user_password;
    private JComboBox cmb_user_type;
    private JButton btn_user_add;

    //Tablomuz için bir modele ihtiyacımız var
    private DefaultTableModel mdl_user_list;
    //listelerimizi tutmak için bir array'e ihtiyacımız var
    private Object[] row_user_list;

    private final Operator operator;

    public OperatorGUI(Operator operator) {
        this.operator = operator;

        add(wrapper);
        setSize(1000, 500);
        int x = Helper.screenCenterPoint("x", getSize());
        int y = Helper.screenCenterPoint("y", getSize());
        setLocation(x, y); // uygulamanın ekranımızın ortasında başlamasını sağlayacak
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        lbl_welcome.setText("Hoşgeldin " + operator.getName() + " !"); // giriş yapan için Hoşgeldin dinamik yapıda

        // ModelUserList
        mdl_user_list = new DefaultTableModel();
        Object[] col_user_list = {"ID", "Ad Soyad", "Kullanıcı Adı", "Şifre", "Üyelik Tipi"};// modelimizin sütun kısmı için
        mdl_user_list.setColumnIdentifiers(col_user_list); // Obje veri tipinde bir array alır bu method
        row_user_list = new Object[col_user_list.length];

        // modelimizin satırına verileri almak için örnek bir yapı
//        Object[] firstRow = {"1","Alcor Goetia","alcor","12345","operator"};
//        mdl_user_list.addRow(firstRow); // bu şekilde tablomuza örnek bir satır eklemiş olduk

        // User veritabanımızdan listeyi almak için
        loadUserModel();

        // bu modelimizi artık tableUserList içerisinde atabiliriz
        tbl_user_list.setModel(mdl_user_list);
        tbl_user_list.getTableHeader().setReorderingAllowed(false); // sütun yer değiştirebilme özelliğini kapattık

        // Butona tıklama ile yapılacak
        btn_user_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_user_name) ||
                    Helper.isFieldEmpty(fld_user_username) ||
                    Helper.isFieldEmpty(fld_user_password)) {
                Helper.showMsg("fill");
            } else {
                // burda dolduran kısımları alabiliriz
                String name = fld_user_name.getText();
                String username = fld_user_username.getText();
                String password = fld_user_password.getText();
                String type = cmb_user_type.getSelectedItem().toString();
                if (User.add(name, username, password, type)) {
                    Helper.showMsg("done");

                    loadUserModel();
                }
            }

        });
    }

    private void loadUserModel() {
        // Burda tabloyu ekleme işleminden sonra güncellemek için önce
        DefaultTableModel clearModel = (DefaultTableModel) tbl_user_list.getModel();
        clearModel.setRowCount(0); // tablodaki tüm değerleri 0 lar
        // ardından tabloyu tekrar getirmek bu problemi çözücek
        for (User obj : User.getList()) {
            int i = 0;
            row_user_list[i++] = obj.getId();
            row_user_list[i++] = obj.getName();
            row_user_list[i++] = obj.getUsername();
            row_user_list[i++] = obj.getPassword();
            row_user_list[i++] = obj.getType();
            // oluşturduğumuz bu satır modelini modelimize ekleyebiliriz
            mdl_user_list.addRow(row_user_list);
        }
    }

    public static void main(String[] args) {
        Helper.setLayout();

        Operator op = new Operator();
        // veri tabanı öncesi manuel
        op.setId(1);
        op.setName("Abdullah Turgut");
        op.setPassword("1234");
        op.setType("operator");
        op.setUsername("abdullah");


        OperatorGUI opGUI = new OperatorGUI(op);
    }
}
