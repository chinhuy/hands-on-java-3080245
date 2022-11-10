package bank;

import java.util.Scanner;

import javax.security.auth.login.LoginException;

import bank.exceptions.AmountException;

public class Menu {
  private Scanner scanner;
  public static void main(String[] args) {
    System.out.println("Welcome to Globe Bank Internation!");
    Menu menu = new Menu();
    menu.scanner = new Scanner(System.in);

    Customer customer = menu.authenticateUser();
    if(customer != null){
      Account account = DataSource.getAccount(customer.getAccount_id());
      menu.showMenu(customer, account);
    }


    menu.scanner.close();
  }
  private void showMenu(Customer customer, Account account) {
    int selection = 0;
    while(selection != 4 && customer.isAuthenticated()){
      System.out.println("======================");
      System.out.println("Please select one of the following options:");
      System.out.println("1. Deposit");
      System.out.println("2. Withdraw");
      System.out.println("3. Check balance");
      System.out.println("4. Exit");
      System.out.println("======================");

      selection = scanner.nextInt();
      double amount;
      switch(selection){
        case 1:
          System.out.println("How much would you like to deposit?");
          amount = scanner.nextDouble();
          try {
            account.deposit(amount);
          } catch (AmountException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println("Please try again.");
          }
          break;
        case 2:
          System.out.println("How much would you like to withdraw?");
          amount = scanner.nextDouble();
          try {
            account.withdraw(amount);
          } catch (AmountException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
            System.out.println("Please try again.");
          }
          break;
        case 3:
          System.out.println("Currrent balance:" + account.getBalance());
          break;
        case 4:
          Authenticator.logout(customer);
          System.out.println("Thanks for banking at Globe Bank Internation");
          break;
        default:
          System.out.println("Invalid action!");
          break;
      }
    }
  }
  private Customer authenticateUser(){
    System.out.println("Please enter your username:");
    String username = scanner.next();

    System.out.println("Please enter your password:");
    String password = scanner.next();

    Customer customer = null;
    try {
      customer = Authenticator.login(username, password);
    } catch (LoginException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return customer;
  }
}
