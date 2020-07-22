/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MySQL_DB;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Hiwa O.Hassan
 */
class MysqlDatabase {

    Statement stmt;
    ResultSet result;

    public void connectionDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/PAMED_Algo", "root", "");
            stmt = (Statement) con.createStatement();
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, exc.getMessage());
        }
    }

    public String customerData() {
        String customerInfo = "";
        try {
//            result = stmt.executeQuery("select * from tt");
//            while (result.next()) {
//                customerInfo = customerInfo
//                        + result.getString("id")
//                        + "&" + result.getString("name")
//                   + "&" + result.getString("age");
//                // Here "&"s are added to the return string. 
//                // This is help to split the string in Android application
//            }

            //  Statement stmt=(Statement)con.createStatement();
            //  String name="Jerome Dcruz";
            //  String contactno="9773523568";
            //  String insert="INSERT INTO tt VALUES('"+name+"','"+contactno+"');";
            // stmt.executeUpdate(insert);
            int id = 10;
            String name = "Hiwa Omer";
            int age = 39;

            String insert = "INSERT INTO tt VALUES(" + id + ",'" + name + "'," + age + ");";

            stmt.executeUpdate(insert);
        } catch (Exception exc) {
            System.out.println(exc.getMessage());
        }
        customerInfo = "hiwa";
        return customerInfo;
    }

    public void Insert() {
        try {
            int id = 10;
            String name1 = "Hiwa Omer";
            int age = 39;
            Class.forName("com.mysql.jdbc.Driver");
            // Connection con = DriverManager.getConnection("jdbc:mysql:\\localhost\basicinfo", "root", "root");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/PAMED_Algo", "root", "");

            Statement stmt = (Statement) con.createStatement();

            String name = "Jerome Dcruz";
            String contactno = "9773523568";

            String insert = "INSERT INTO info VALUES('" + name + "','" + contactno + "');";
            stmt.executeUpdate(insert);

            String insert1 = "INSERT INTO tt VALUES(" + id + ",'" + name1 + "'," + age + ");";
            stmt.executeUpdate(insert1);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", 1);

        }
    }

    public void update() {
        try {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/PAMED_Algo", "root", "");

        String query = "update tt set name = 'awat' where id = 1";
        PreparedStatement preparedStmt = con.prepareStatement(query);
       // preparedStmt.setInt(1, 6000);
        //preparedStmt.setString(2, "Fred");

        preparedStmt.executeUpdate();

        con.close();
                } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", 1);

        }
    }

    public static void main(String args[]) {
        MysqlDatabase ob = new MysqlDatabase();
      //  ob.Insert();
        ob.update();
        //System.out.println(ob.customerData());
    }
}
