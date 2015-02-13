/*
 * Author: Nick Campbell (Ledzeppelinbd)
 * 
 * Version:1.4 The final version, the one version..... TO RULE THEM ALL
 * 
 * Date:2.10.15
 * 
 * Description:  The security class takes the user's input data and checks to see if it matches what is stored in
 * the database.  Aside from this the class also has the responsibility of listing all permissions that a user has.
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
 * The Class Security.
 */
public class Security {

	
	/** The User id. */
	private String UserID;
	
	
	/**
	 * Validate login.This method checks to see if a Users id and password match what is stored in the database.
	 * It does so by seeing if the id and password match what is stored.
	 *
	 * @param loginName the login name
	 * @param pin the pin
	 * @return the customer
	 */
	public  Customer validateLogin(String loginName, int pin){
		String sql = "SELECT * FROM customer WHERE loginName='"+loginName+"' and pin='"+pin+"';"; 
		//System.out.println(sql);
		DbUtilities db = new MySqlUtilities();
		try {
			ResultSet rs = db.getResultSet(sql);
				
				if(rs.next() ){
				Customer newCustomer = new Customer(rs.getString( "customerID"));
				//System.out.println("checking to see if validateLogin is working: im working!");	
				return newCustomer;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			ErrorLogger.log("Invalid");	 			// use this for all error handling so it logs
			ErrorLogger.log(e.getMessage());		// use this for all error handling so it logs, remember to also log the query along with the errors
		}
		//System.out.println(" checking to see if validateLogin is working: im still working!");
		return null;
			
	}//end of validateLogin
	
	/**
	 * List user groups. Lists all of the groups to which a certain customer belongs, the user ID is passed into this method that then creates two arrays.
	 * One to store the groupID and the next to store the groups themselves.  This is due to me having to run two separate queries and display the data at
	 * the end.
	 *
	 * @param userID the user id
	 * @return the array list
	 */
	public ArrayList<String> listUserGroups(String userID){
		String sql = "SELECT groupID FROM user_permissions WHERE groupOrUserID ='"+userID+"';"; 
		//System.out.println(sql);
		DbUtilities db = new MySqlUtilities();
		ArrayList<String> groupID = new ArrayList<String>();
		ArrayList<String> groupArray = new ArrayList<String>();

		try {
			ResultSet rs = db.getResultSet(sql);
			while(rs.next() ){

				groupID.add(rs.getString("groupID"));
			}
			//System.out.print(groupID);
		} catch (SQLException e) {
			e.printStackTrace();
			ErrorLogger.log("Invalid");	 			// use this for all error handling so it logs
			ErrorLogger.log(e.getMessage());		// use this for all error handling so it logs, remember to also log the query along with the errors
		}

		//try{

		for(int i=0; i<groupID.size();i++)
		{
			String groups = "SELECT groupName FROM groups WHERE groupID='"+groupID.get(i)+"';";
			//System.out.println(groups);

			try{	
				DbUtilities dz = new MySqlUtilities();
				ResultSet rz =dz.getResultSet(groups);

				while(rz.next()){	
					groupArray.add(rz.getString("groupName"));

					for(int x=0; x<groupArray.size();x++){

					}
					

				}
			} catch (SQLException e) {
				e.printStackTrace();
				ErrorLogger.log("Invalid");	 			// use this for all error handling so it logs
				ErrorLogger.log(e.getMessage());		// use this for all error handling so it logs, remember to also log the query along with the errors

			}


		}
		return groupArray;
	}
}//end of class
