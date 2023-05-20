import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Employee {
    private JPanel main;
    private JTextField textid;
    private JTextField textname;
    private JButton saveButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton searchButton;
    private JTable table1;
    private JTextField textsid;


    Connection con=null;
    PreparedStatement pst;





    public void Connect(){

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection("jdbc:mysql://localhost/rbcompany","root","");
            System.out.println("suceess");

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void Load(){

        try {
            pst=con.prepareStatement("select * from student");
            ResultSet rs=pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }





    public static void main(String[] args) {
        JFrame frame = new JFrame("Employee");
        frame.setContentPane(new Employee().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public Employee() {
        Connect();
        Load();
    saveButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            String stuid,name;

            stuid=textid.getText();
            name=textname.getText();

            try {
                pst=con.prepareStatement("insert into student(id,name) values (?,?)");
                pst.setString(1,stuid);
                pst.setString(2,name);
                pst.executeUpdate();

                JOptionPane.showMessageDialog(null,"updated");

                Load();
                textid.setText("");
                textname.setText("");
                textid.requestFocus();




            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }


        }
    });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String sid=textsid.getText();

                try {
                    pst=con.prepareStatement("select id,name from student where id=?");
                    pst.setString(1,sid);
                    ResultSet rs=pst.executeQuery();

                    if(rs.next()==true){

                        String ssid=rs.getString(1);
                        String name=rs.getString(2);

                        textid.setText(ssid);
                        textname.setText(name);




                    }else{

                        textid.setText("");
                        textname.setText("");

                        JOptionPane.showMessageDialog(null,"invalid");

                    }



                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String id,name;



                id=textid.getText();
                name=textname.getText();

                try {
                    pst=con.prepareStatement("update student set id=?,name=? where id=?");
                    pst.setString(1,id);
                    pst.setString(2,name);
                    pst.setString(3,id);
                    pst.executeUpdate();


                    JOptionPane.showMessageDialog(null,"updated");
                    Load();
                    textid.setText("");
                    textname.setText("");
                    textid.requestFocus();



                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String sid=textsid.getText();
                try {
                    pst=con.prepareStatement("delete from student");
                    pst.executeUpdate();


                    JOptionPane.showMessageDialog(null,"deleted");
                    Load();
                    textid.setText("");
                    textname.setText("");
                    textid.requestFocus();



                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });
    }
}
