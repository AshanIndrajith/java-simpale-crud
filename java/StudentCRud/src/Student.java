import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;



public class Student {
    private JButton saveButton;
    private JButton updateButton;
    private JButton searchButton;
    private JButton deleteButton;
    private JTextField textname;
    private JTextField textssalary;
    private JTextField textmobile;
    private JTable table_1;
    private JTextField textid;
    private JPanel main;



    public static void main(String[] args) {
        JFrame frame = new JFrame("Student");
        frame.setContentPane(new Student().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    Connection con=null;
    PreparedStatement pst;


    public void Connect(){

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection("jdbc:mysql://localhost/rbcompany","root","");
            System.out.println("connction is sucess");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void table_loaded(){

        try {
            pst=con.prepareStatement("select * from registation");
            ResultSet rs=pst.executeQuery();
            table_1.setModel(DbUtils.resultSetToTableModel(rs));


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }








    public Student() {
        Connect();
    saveButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            String empname,salary,mobile;

            empname=textname.getText();
            salary=textssalary.getText();
            mobile=textmobile.getText();

            try {
                pst=con.prepareStatement("insert into registation (empname,salary,mobile)values (?,?,?)");
                pst.setString(1,empname);
                pst.setString(2,salary);
                pst.setString(3,mobile);

                JOptionPane.showMessageDialog(null,"recode added");
                table_loaded();

                textname.setText("");
                textssalary.setText("");
                textmobile.setText("");
                textname.requestFocus();

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }


        }
    });
}
}
