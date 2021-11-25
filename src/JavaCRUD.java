import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import static javax.swing.JOptionPane.*;


public class JavaCRUD {
    private JPanel Main;
    private JTextField textPname;
    private JTextField textPrice;
    private JTextField textQty;
    private JButton saveButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JTextField textPid;
    private JButton searchButton;
 

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Java CRUD");
        frame.setContentPane(new JavaCRUD().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    Connection con;
    PreparedStatement ps;
    public JavaCRUD()
    {
        Connection();
        saveButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
            String pname,price,qty;

            pname= textPname.getText();
            price=textPrice.getText();
            qty=textQty.getText();

            try
            {
            ps = con.prepareStatement("insert into products(pname,price,qty)values(?,?,?)");
            ps.setString(1,pname);
            ps.setString(2,price);
            ps.setString(3,qty);
            ps.executeUpdate();

            showMessageDialog(null, "Record successfully added...");
            textPname.setText("");
            textPrice.setText("");
            textQty.setText("");
            textPname.requestFocus();
            }
            catch (SQLException e1)
            {
                e1.printStackTrace();
            }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            try{
                String id = textPid.getText();
                ps = con.prepareStatement("select pname,price,qty from products where pid = ? ");
                ps.setString(1,id);
                ResultSet rs=ps.executeQuery();
            if (rs.next()==true)
                {
                String pname= rs.getString(1);
                String price= rs.getString(2);
                String qty= rs.getString(3);

                textPname.setText(pname);
                textPrice.setText(price);
                textQty.setText(qty);
                }
            else{
                textPname.setText("");
                textPrice.setText("");
                textQty.setText("");
                showMessageDialog(null,"No data is available on this Product ID");
                }
            }
            catch (SQLException e1){
                e1.printStackTrace();
            }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pid,pname,price,qty;

                pname= textPname.getText();
                price=textPrice.getText();
                qty=textQty.getText();
                pid=textPid.getText();
            try {
                ps = con.prepareStatement("update products set pname=?, price=?, qty=? where pid=?");
                ps.setString(1,pname);
                ps.setString(2,price);
                ps.setString(3,qty);
                ps.setString(4,pid);

                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Successfully Updated");

                textPname.setText("");
                textPrice.setText("");
                textQty.setText("");
                textPname.requestFocus();
                textPid.setText("");
            }
            catch (SQLException e1){
                e1.printStackTrace();
            }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            String dlt;
            dlt=textPid.getText();

            try {
                ps= con.prepareStatement("delete from products where pid = ?");
                ps.setString(1,dlt);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null,"Record has been successfully deleted");

                textPname.setText("");
                textPrice.setText("");
                textQty.setText("");
                textPname.requestFocus();
                textPid.setText("");
            	}
            catch (SQLException e1){
                e1.printStackTrace();
            }

            }
        });
    }
    public void Connection()
    {
        try
            {
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection("jdbc:mysql://localhost/javaproducts","root","");
            System.out.println("Connection Successful");
            }
        catch (ClassNotFoundException e)
            {
            e.printStackTrace();
            }
        catch (SQLException e)
            {
            e.printStackTrace();
            }
    }


    }

