/*
 * Author: Nick Campbell (Ledzeppelinbd)
 * 
 * Version:1.4 The final version, the one version..... TO RULE THEM ALL
 * 
 * Date:2.10.15
 * 
 * Description:  Creates and finds transactions that have been run for a particular user.
 * 
 * 
 * 
 */
package edu.pitt.bank;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

import edu.pitt.utilities.DbUtilities;
import edu.pitt.utilities.MySqlUtilities;
import edu.pitt.utilities.ErrorLogger;

// TODO: Auto-generated Javadoc
/**
 * The Class Transaction.
 */
public class Transaction {
	
	/** The transaction id. */
	private String transactionID;
	
	/** The account id. */
	private String accountID;
	
	/** The type. */
	private String type;
	
	/** The amount. */
	private double amount;
	
	/** The balance. */
	private double balance;
	
	/** The transaction date. */
	private Date transactionDate; 
	
	/**
	 * Instantiates a new transaction.Displays/fetches and sets the information of a particular transaction based on it's ID
	 * Then assigns the values gathered from sql to variables interpretable by java
	 *
	 * @param transactionID
	 *            the transaction id
	 */
	public Transaction(String transactionID){  
		String sql = "SELECT * FROM nsc18_bank1017.transaction "; 
		sql += "WHERE transactionID = '" + transactionID + "'";
		//System.out.println(sql);
		DbUtilities db = new MySqlUtilities();
		try {
			ResultSet rs = db.getResultSet(sql);
			while(rs.next()){
				this.transactionID = rs.getString("transactionID");
				this.accountID = rs.getString("accountID");
				this.type = rs.getString("type");//// not in transaction table
				this.amount = rs.getDouble("amount");
				this.balance = rs.getDouble("balance");// not in transaction table
				this.transactionDate = new Date();
				//System.out.println (rs);

			}
		} catch (SQLException e) {
			e.printStackTrace();
			ErrorLogger.log("Invalid");	 			// use this for all error handling so it logs
			ErrorLogger.log(e.getMessage());		// use this for all error handling so it logs, remember to also log the query along with the errors
		}
	}
	
	/**
	 * Instantiates a new transaction. Creates a transaction and displays the information of the transaction
	 * Assigns values to an sql string in order to place them into the database and create a new transaction
	 *
	 * @param accountID
	 *            the account id
	 * @param type
	 *            the type
	 * @param amount
	 *            the amount
	 * @param balance
	 *            the balance
	 */
	
	    
	public Transaction(String accountID,String type,double amount, double balance){	
		this.transactionID = UUID.randomUUID().toString();
		this.type = type; //not in transaction table
		this.amount = amount;
		this.accountID = accountID;
		this.balance = balance;	//not in transaction table
		
		String sql = "INSERT INTO nsc18_bank1017.transaction ";
		sql += "(transactionID, accountID, amount, transactionDate, type, balance) ";		
		sql += " VALUES ";
		sql += "('" + this.transactionID + "', ";
		sql += "'" + this.accountID + "', ";
		sql += amount + ", ";
		sql += "CURDATE()"+ ", ";
		sql += "'" + this.type + "', ";
		sql += balance + ");";
		
		//System.out.println(sql);
		
		DbUtilities db = new MySqlUtilities();
		db.executeQuery(sql);
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
	 * Gets the amount.
	 *
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * Sets the amount.
	 *
	 * @param amount the new amount
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * Gets the balance.
	 *
	 * @return the balance
	 */
	public double getBalance() {
		return balance;
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
	 * Gets the transaction date.
	 *
	 * @return the transaction date
	 */
	public Date getTransactionDate() {
		return transactionDate;
	}

	/**
	 * Sets the transaction date.
	 *
	 * @param transactionDate the new transaction date
	 */
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
}
