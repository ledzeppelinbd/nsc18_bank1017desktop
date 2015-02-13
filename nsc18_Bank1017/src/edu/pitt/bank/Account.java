/*
 * Author: Nick Campbell (Ledzeppelinbd)
 * 
 * Version:1.4 The final version, the one version..... TO RULE THEM ALL
 * 
 * Date:2.10.15
 * 
 * Description:This class will create or select accounts from within the MySql nsc18_1017bank. 
 * Uses a number of different variables to tie into MySql statements and tell teh Database what to do through querys.
 * 
 * 
 * 
 */
package edu.pitt.bank;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import edu.pitt.utilities.DbUtilities;
import edu.pitt.utilities.MySqlUtilities;
import edu.pitt.utilities.ErrorLogger;

// TODO: Auto-generated Javadoc

/**
 * The Class Account.
 */
public class Account {
	
	/** The account id. */
	private String accountID;
	
	/** The type. */
	private String type;
	
	/** The balance. */
	private double balance;
	
	/** The interest rate. */
	private double interestRate;
	
	/** The penalty. */
	private double penalty;
	
	/** The status. */
	private String status;
	
	/** The date open. */
	private Date dateOpen;
	
	/** The transaction list. */
	private ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
	
	/** The account owners. */
	private ArrayList<Customer> accountOwners = new ArrayList<Customer>();
	
	/**
	 * Pulls a user's account from the database and sets the variables from the database to the variables defined within the class.
	 *
	 * @param accountID
	 *            the account id
	 */
	public Account(String accountID){					//This pulls a user's account information from our database and assigns the data to the Java variables.
		String sql = "SELECT * FROM account "; 
		sql += "WHERE accountID = '" + accountID + "'";
		DbUtilities db = new MySqlUtilities();
		try {
			ResultSet rs = db.getResultSet(sql);
			while(rs.next()){
				this.accountID = rs.getString("accountID");
				this.type = rs.getString("type");
				this.balance = rs.getDouble("balance");
				this.interestRate = rs.getDouble("interestRate");
				this.penalty = rs.getDouble("penalty");
				this.status = rs.getString("status");
				this.dateOpen = new Date();
				//System.out.println (rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			ErrorLogger.log("Invalid");	 			// use this for all error handling so it logs
			ErrorLogger.log(e.getMessage());		// use this for all error handling so it logs, remember to also log the query along with the errors
		}
		
		
		
		String sqlTransaction ="SELECT * FROM transaction ";
		sqlTransaction +=" Where accountID ='"+accountID+"'";
		
		ResultSet transaction;
		
		try{
			transaction=db.getResultSet(sqlTransaction);
				if(transaction != null){
					while(transaction.next()){
						createTransaction(transaction.getString("transactionId"));
					}
				}
		}catch (SQLException e) {
			e.printStackTrace();
			ErrorLogger.log("Invalid");	 			// use this for all error handling so it logs
			ErrorLogger.log(e.getMessage());		// use this for all error handling so it logs, remember to also log the query along with the errors
		}
		
		
		
		
	}
	
	/**
	 * Instantiates a new account into the database by pulling the variables and setting them into an insert statement that 
	 * the database will take and use to create a new account. 
	 * 
	 * 
	 * p.s.  there might be like 500 Captain James T Kirk's in my database from when i was testing this...........  
	 * I tried to delete them all, but Damnit Captain he cant be killed..
	 *
	 * @param accountType
	 *            the account type
	 * @param initialBalance
	 *            the initial balance
	 */
	public Account(String accountType, double initialBalance){ 	//this creates a new account on the database and assignes values to the variables, then inserts it into the database.
		this.accountID = UUID.randomUUID().toString();
		this.type = accountType;
		this.balance = initialBalance;
		this.interestRate = 0;
		this.penalty = 0;
		this.status = "active";
		this.dateOpen = new Date();
		
		String sql = "INSERT INTO nsc18_bank1017.account ";
		sql += "(accountID,type,balance,interestRate,penalty,status,dateOpen) ";
		sql += " VALUES ";
		sql += "('" + this.accountID + "', ";
		sql += "'" + this.type + "', ";
		sql += this.balance + ", ";
		sql += this.interestRate + ", ";
		sql += this.penalty + ", ";
		sql += "'" + this.status + "', ";
		sql += "CURDATE());";
		
		DbUtilities db = new MySqlUtilities();
		db.executeQuery(sql);
		
	}
	
	
	
	/*
	 * This method simply adds passed accountOwners to the existing accountOwner array
	 */
	
	/**
	 * Adds the account owner.
	 *
	 * @param accountOwner the account owner
	 */
	public void addAccountOwner(Customer accountOwner){				//what exactly were we supposed to do with this..?
		this.accountOwners.add(accountOwner);
	}
	
	
	
	
	/**
	 * Withdraw changes the balance within the database by subtracting the amount. Done by creating a transaction then telling the database
	 * to update the account's balance
	 *
	 * @param amount
	 *            the amount
	 */
	public void withdraw(double amount){		//takes the variable amount and subtracts it from the total an account had.  Then saves the info to the database
		this.balance -= amount;
		createTransaction(this.accountID,  "withdraw", this.balance, amount);		//this.type, amount,   taken out as per UML diagram
		updateDatabaseAccountBalance();
	}
	
	
	/**
	 * Deposit changes the balance within the database by adding an amount and then creating a transaction.  It then finally 
	 * tells the database to update itself.
	 *
	 * @param amount
	 *            the amount
	 */
	public void deposit(double amount){			//takes the variable amount and adds it to the total an account had.  Then saves it to the database
		this.balance += amount;
		createTransaction(this.accountID,  "deposit", this.balance, amount);		
		updateDatabaseAccountBalance();
	}
	
	/**
	 * Update database account balance.
	 */
	private void updateDatabaseAccountBalance(){	//saves deposits and withdrawals to the database for that specific user
		String sql = "UPDATE nsc18_bank1017.account SET balance = " + this.balance + " ";
		sql += "WHERE accountID = '" + this.accountID + "';";
		
		DbUtilities db = new MySqlUtilities();
		db.executeQuery(sql);
	}
	
	/**
	 * Creates the transaction taken from the ID.
	 * Then adds it to the transaction List Array
	 *
	 * @param transactionID
	 *            the transaction id
	 * @return the transaction
	 */
	private Transaction createTransaction(String transactionID){	//Adds a transaction to the account 
		Transaction t = new Transaction(transactionID);
		transactionList.add(t);
		return t;
	}
	
	/**
	 * Creates the transaction taken from the variables accountID type amount and balance
	 * then adds this to the array transactionList.
	 *
	 * @param accountID
	 *            the account id
	 * @param type
	 *            the type
	 * @param amount
	 *            the amount
	 * @param balance
	 *            the balance
	 * @return the transaction
	 */
	private Transaction createTransaction( String accountID, String type, double amount, double balance){	//adds a transaction to the user's account by importing their id, 
																												//account type, the amount being transferred and the balance
		Transaction t = new Transaction(accountID, type, balance, amount);									//type, amount,  taken out as per UML diagram
		transactionList.add(t);
		return t;
	}
	
	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets the interest rate.
	 *
	 * @return the interest rate
	 */
	public double getInterestRate() {
		return interestRate;
	}

	/**
	 * Sets the interest rate.
	 *
	 * @param interestRate the new interest rate
	 */
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	/**
	 * Gets the penalty.
	 *
	 * @return the penalty
	 */
	public double getPenalty() {
		return penalty;
	}

	/**
	 * Sets the penalty.
	 *
	 * @param penalty the new penalty
	 */
	public void setPenalty(double penalty) {
		this.penalty = penalty;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the transaction list.
	 *
	 * @return the transaction list
	 */
	public ArrayList<Transaction> getTransactionList() {
		return transactionList;
	}

	/**
	 * Sets the transaction list.
	 *
	 * @param transactionList the new transaction list
	 */
	public void setTransactionList(ArrayList<Transaction> transactionList) {
		this.transactionList = transactionList;
	}

	/**
	 * Gets the account owners.
	 *
	 * @return the account owners
	 */
	public ArrayList<Customer> getAccountOwners() {
		return accountOwners;
	}

	/**
	 * Sets the account owners.
	 *
	 * @param accountOwners the new account owners
	 */
	public void setAccountOwners(ArrayList<Customer> accountOwners) {
		this.accountOwners = accountOwners;
	}

	/**
	 * Sets the balance.
	 *
	 * @param balance the new balance
	 */
	public void setBalance(double balance) {
		this.balance = balance;
	}

	/**
	 * Gets the account id.
	 *
	 * @return the account id
	 */
	public String getAccountID(){
		return this.accountID;
	}
	
	/**
	 * Gets the balance.
	 *
	 * @return the balance
	 */
	public double getBalance(){
		return this.balance;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override		// anything in java with @ is a directive, in this case the override makes sure the toString exists in the parent method
	public String toString(){
		return this.accountID;
	}
	
}
