/*
 * Author: Nick Campbell (Ledzeppelinbd)
 * 
 * Version:1.4 The final version, the one version..... TO RULE THEM ALL
 * 
 * Date:2.10.15
 * 
 * Description:This class is responsible for creating a new bank and organizing the data within.  
 * Such as finding an account or a customer.
 * 
 * 
 * 
 */


package edu.pitt.bank;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.pitt.utilities.DbUtilities;
import edu.pitt.utilities.MySqlUtilities;
import edu.pitt.utilities.ErrorLogger;

// TODO: Auto-generated Javadoc
/**
 * The Class Bank.
 */
public class Bank {

	/** The account list. */
	private  ArrayList<Account> accountList = new ArrayList<Account>();
	
	/** The customer list. */
	private ArrayList<Customer> customerList = new ArrayList<Customer>();
	
	
	
	
	/**
	 * Instantiates a new bank, it does so by loading the accounts and setting owners to the various accounts.
	 */
	public Bank(){
		loadAccounts();
		setAccountOwners();
		
	}
	
	
/**
	 * This method will search through the account list for a corresponding account ID and then return the account
	 *
	 * @param accountID the account id
	 * @return the account
	 */
	private Account findAccount(String accountID){   
	Account matchFound = null;
	for (int i=0; i<accountList.size(); i++ ){
		if (accountList.get(i).getAccountID()==accountID){
			 matchFound = accountList.get(i);
		}else{
			matchFound = null;
		}
	}
	return matchFound;

}



/**
 * This method will search through the customer list for a corresponding customer id then return the customer
 *
 * @param customerID the customer id
 * @return the customer
 */
private Customer findCustomer(String customerID){//confusion this is not how u do this.. what do i import?
	Customer matchFound = null;
	for (int i=0; i<customerList.size(); i++ ){
		if (customerList.get(i).getCustomerID()==customerID){
			 matchFound = customerList.get(i);
		}else{
			matchFound = null;
		}
	}
	return matchFound;

}
	
/**
 * This method loads the customer and their accounts.  It then adds them to two array lists 
 * in order to assign who owns what account.
 * 
 */
private void setAccountOwners() {
	String sql = "SELECT * FROM account JOIN customer_account ON accountID = fk_accountID JOIN customer ON fk_customerID = customerID;"; 
	
	
	
	//System.out.println(sql);
	DbUtilities db = new MySqlUtilities();
	try {
		ResultSet rs = db.getResultSet(sql);
		while(rs.next()){
			Customer newCustomer = new Customer (rs.getString("customerID"));
			customerList.add(newCustomer);
			Account newAccount = new Account(rs.getString("AccountID"));
			accountList.add(newAccount);
			
			//add account as well
			
		}
	} catch (SQLException e) {
		e.printStackTrace();
		ErrorLogger.log("Invalid");	 			// use this for all error handling so it logs
		ErrorLogger.log(e.getMessage());		// use this for all error handling so it logs, remember to also log the query along with the errors
	}
	
	
	//System.out.println("Bank Account List"+accountList);
	//System.out.println("Bank Customer List" + customerList);
	}				
	

	
	/**
	 * This method loads all of the accounts in the database and places them in an array list 
	 */
	private  void loadAccounts(){
		String sql = "SELECT accountID FROM account "; 
		
		//System.out.println(sql);
		DbUtilities db = new MySqlUtilities();
		try {
			ResultSet rs = db.getResultSet(sql);
			while(rs.next()){
				
				Account newAccount = new Account ( rs.getString("accountID"));
				accountList.add(newAccount);				
				
				
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			ErrorLogger.log("Invalid");	 			// use this for all error handling so it logs
			ErrorLogger.log(e.getMessage());		// use this for all error handling so it logs, remember to also log the query along with the errors
		}
		
		
		//System.out.println("Bank Account List"+accountList);
	}				

	
	
}
	
	
	
	
	
	

