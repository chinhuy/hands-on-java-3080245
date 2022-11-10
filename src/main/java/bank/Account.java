package bank;

import bank.exceptions.AmountException;

public class Account {
  private int id;
  private String type;
  private double balance;


  public Account(int id, String type, double balance) {
    this.id = id;
    this.type = type;
    this.balance = balance;
  }
  
  public void deposit(double amount) throws AmountException{
    if(amount < 1){
      throw new AmountException("The minimum deposit us 1.00");
    }
    this.balance += amount;
    DataSource.updateAccountBalance(id, this.balance);
  }
  public void withdraw(double amount) throws AmountException{
    if(amount < 0){
      throw new AmountException("The withdraw amount must be greater then 0.");
    }
    if (amount > getBalance()){
      throw new AmountException("You do not have sufficient funds for this withdraw.");
    }
    this.balance -= amount;
    DataSource.updateAccountBalance(id, this.balance);
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public double getBalance() {
    return this.balance;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }

}
