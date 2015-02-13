/*
 * Author: Nick Campbell (Ledzeppelinbd)
 * 
 * Version:1.4 The final version, the one version..... TO RULE THEM ALL
 * 
 * Date:2.10.15
 * 
 * Description:
 * 
 * 
 * 
 */
package edu.pitt.bank;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import edu.pitt.utilities.DbUtilities;
import edu.pitt.utilities.MySqlUtilities;
import edu.pitt.utilities.ErrorLogger;

// TODO: Auto-generated Javadoc
/**
 * The Class Test.
 */
public class Test {

	
	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		Security s = new Security();
		Customer c = s.validateLogin("nmarcus", 8125);
		s.listUserGroups(c.getCustomerID());
	
		//ErrorLogger.log("Shit went down");
		/*
		 * to test security class
		 */
		//Security s = new Security();
		//Customer c= s.validateLogin("nmarcus", 8125);
		//System.out.println(c.getLastName());
		
		//Bank newBank = new Bank();
		
		//Customer custy = new Customer("01b9f986-5d41-11e3-94ef-97beef767f1d");
		
		//Transaction tranny = new Transaction("00ae9c2a-5d43-11e3-94ef-97beef767f1d", "open", 500, 5000);
		//Customer cust = new Customer("Kirk", "James", "1111111", "JK1701", 1701, "The Enterprise","Federation", "PA",17011);
		
		//String loginName = "nmarcus";
		//int pin = 8125;
		
		//String sql = "SELECT lastName, loginName, pin FROM customer WHERE loginName = '"+ loginName +"' AND pin =" +pin;
		//System.out.println(sql);
		
//HASTABLEEXAMPLE
			
		Map <String,Account> betterAccountList = new Hashtable<String,Account>();
		
		DbUtilities db = new MySqlUtilities();
		String sql = "SELECT * FROM account;";
		try {
			ResultSet rs = db.getResultSet(sql);
			while(rs.next()){
			//	System.out.println(rs.getString("accountID"));
				//String dbLastName = (rs.getString("lastName"));
				//String dbLoginName = rs.getString("loginName");
				//int dbPin = rs.getInt("pin");
				
				Account a = new Account (rs.getString("accountID"));
				betterAccountList.put(rs.getString("accountID"),a);
				
				//System.out.println("Making sure DB utilities works"+dbLastName + "\t\t" + dbLoginName + "\t\t" + dbPin);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ErrorLogger.log("Invalid");	 			// use this for all error handling so it logs
			ErrorLogger.log(e.getMessage());		// use this for all error handling so it logs, remember to also log the query along with the errors
		}

		System.out.println(" put an account ID in here  ");	
		
	}	
}
