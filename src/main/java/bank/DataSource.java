package bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataSource {
  public static Connection connect() {
    String dbFile = "jdbc:sqlite:resources/bank.db";
    Connection connection = null;
    try {
      connection = DriverManager.getConnection(dbFile);
      System.out.println("We are connected db!");
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return connection;
  }

  public static Customer getCustomer(String username){
    String sql = "select * from customers where username = ?";
    Customer customer = null;
    try (Connection connection = connect();
      PreparedStatement statement = connection.prepareStatement(sql)
    ) {
      statement.setString(1, username);
      try (ResultSet resultSet = statement.executeQuery()) {
        customer = new Customer(
          resultSet.getInt("id"), 
          resultSet.getString("name"),
          resultSet.getString("username"),
          resultSet.getString("password"),
          resultSet.getInt("account_id"));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return customer;
  }

  public static Account getAccount(int accountId){
    String sql = "select * from accounts where id = ?";
    Account account = null;
    try (Connection connection = connect();
      PreparedStatement statement = connection.prepareStatement(sql);
    ) {
      statement.setInt(1, accountId);
      try (ResultSet rs = statement.executeQuery()) {
        account = new Account(
          rs.getInt("id"),
          rs.getString("type"),
          rs.getDouble("balance"));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return account;
  }

  public static void updateAccountBalance(int accountId, double balance){
    String sql = "update accounts set balance = ? where id = ?";
    try (Connection connection = connect();
        PreparedStatement stm = connection.prepareStatement(sql);
    ) {
      stm.setDouble(1, balance);
      stm.setInt(2, accountId);
      stm.executeUpdate();
    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
    }
  }

  // public static void main(String[] args) {
  //   Customer customer = getCustomer("ojamblinbx@ycombinator.com");
  //   System.out.println(customer.getAccount_id());
  //   Account account = getAccount(customer.getAccount_id());
  //   System.out.println(account.getBalance());
    
  // }
}
