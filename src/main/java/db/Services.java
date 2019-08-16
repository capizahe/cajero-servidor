package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Services {

    Connection conection = null;
    Statement statement = null;

    private Connection connect() {
        try {
            conection = DriverManager.getConnection("jdbc:sqlite:database.db.txt");
            return conection;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return conection;
    }

    public void withDrawals(String account, Long value) {
        try {
            this.connect();
            statement = conection.createStatement();
            String query = "Update Cuenta SET actual_money=actual_money - " + value + " WHERE account_number = ?";
            PreparedStatement pstm = conection.prepareStatement(query);
            pstm.setString(1, account);
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("No se pudo ejecutar sentencia " + e.getMessage());
        } finally {
            try {
                conection.close();
                statement.close();
            } catch (SQLException e) {
                /* handle exception */
            }

        }
    }

    public void consingMoney(String account, Long value) {
        try {
            this.connect();
            statement = conection.createStatement();
            String query = "Update Cuenta SET actual_money = actual_money + " + value + " WHERE account_number = ?";
            PreparedStatement pstm = conection.prepareStatement(query);
            pstm.setString(1, account);
            pstm.executeUpdate();

        } catch (SQLException e) {
            System.out.println("No se pudo ejecutar sentencia " + e.getMessage());
        } finally {
            try {
                conection.close();
                statement.close();
            } catch (SQLException e) {
                /* handle exception */
            }
        }
    }

    public Long getMoney(String account) {
        Long value = null;
        try {

            this.connect();
            statement = conection.createStatement();
            String query = "SELECT actual_money FROM Cuenta WHERE account_number=?";
            PreparedStatement pstm = conection.prepareStatement(query);
            pstm.setString(1, account);
            ResultSet rs = pstm.executeQuery();
            value = rs.getLong("actual_money");
            System.out.println("el valor es ----------------------------- " + value);
        } catch (SQLException e) {
            /* handle exception */ } finally {
            try {
                conection.close();
                statement.close();
            } catch (SQLException e) {
            }
        }
        return value;
    }

    public String getInfo(String account) {

        String info = "";
        try {
            this.connect();
            statement = conection.createStatement();
            String query = "SELECT actual_money FROM Cuenta WHERE account_number=" + account;
            ResultSet rs = statement.executeQuery(query);
            info += rs.getLong("actual_money") + "";
            rs.close();
        } catch (SQLException e) {

        } finally {
            try {
                conection.close();
                statement.close();
            } catch (SQLException e) {
                /* handle exception */
            }
        }
        return info;
    }

}
