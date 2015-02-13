/*
 * Author: Nick Campbell (Ledzeppelinbd)
 * 
 * Version:1.4 The final version, the one version..... TO RULE THEM ALL
 * 
 * Date:2.10.15
 * 
 * Description:This class creates and defines what a customer is.  It also has methods for adding a customer to the database or searching for a customer.
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
 * The Class Customer.
 */
public class Customer {
	
	/** The customer id. */
	private String customerID;
	
	/** The first name. */
	private String firstName;
	
	/** The last name. */
	private String lastName;
	
	/** The ssn. */
	private String ssn;
	
	/** The street address. */
	private String streetAddress;
	
	/** The city. */
	private String city;
	
	/** The state. */
	private String state;
	
	/** The zip. */
	private int zip;
	
	/** The login name. */
	private String loginName;
	
	/** The pin. */
	private int pin;
	
	
	
	
	/**
	 * Instantiates a new customer.These Constructors create Users/Customers by defining what a Customer is. It then takes the data that has been 
	 * loaded from MySql queries and assigns it to variables usable by Java
	 *
	 * @param customerID the customer id
	 */
	public Customer(String customerID){
		String sql = "SELECT * FROM customer "; 
		sql += "WHERE customerID = '" + customerID + "';";
		//System.out.println(sql);
		DbUtilities db = new MySqlUtilities();
		try {
			ResultSet rs = db.getResultSet(sql);
			while(rs.next()){
				this.customerID = rs.getString("customerID");
				this.firstName = rs.getString("firstName");
				this.lastName = rs.getString("lastName");
				this.ssn = rs.getString("ssn");
				this.streetAddress = rs.getString("streetAddress");
				this.city = rs.getString("city");
				this.state = rs.getString("state");
				this.zip = rs.getInt("zip");
				this.loginName = rs.getString("loginName");
				this.pin = rs.getInt("pin");
			//	System.out.println (rs);



			}
		} catch (SQLException e) {
			e.printStackTrace();
			ErrorLogger.log("Invalid");	 			// use this for all error handling so it logs
			ErrorLogger.log(e.getMessage());		// use this for all error handling so it logs, remember to also log the query along with the errors
		}
		
		
	
	}
	
		/**
	 * Instantiates a new customer.
	 * Creates a Customer and fills in the corresponding variables then runs a database query that places the Customer in the Customer table in the Database
	 *
	 * @param lastName the last name
	 * @param firstName the first name
	 * @param ssn the ssn
	 * @param loginName the login name
	 * @param pin the pin
	 * @param streetAddress the street address
	 * @param City the city
	 * @param state the state
	 * @param zip the zip
	 */
	public Customer(String lastName, String firstName, String ssn, String loginName, int pin,String streetAddress, String City, String state, int zip){
		this.customerID = UUID.randomUUID().toString();
		this.firstName =firstName;
		this.lastName =lastName;
		this.ssn =ssn;
		this.streetAddress =streetAddress ; 
		this.city =	city;  
		this.state =state; 
		this.zip =zip; 
		this.loginName = loginName;
		this.pin = pin;
		
	
		String sql = "INSERT INTO nsc18_bank1017.customer ";
		sql += "(customerID, lastName, firstName, ssn,streetAddress,city,state,zip,loginName,pin) ";
		sql += " VALUES ";
		sql +="('" +  this.customerID +"', ";
		sql +="'" +this.lastName+"',";
		sql +="'" +this.firstName+"',";
		sql += "'" +this.ssn + "', ";
		sql +="'" +this.streetAddress + "', ";
		sql +="'" +this.city + "', ";
		sql +="'" +this.state + "', ";
		sql +="'" +this.zip + "', ";
		sql +="'" +this.loginName + "', ";
		sql +=this.pin + ");";
		
		//System.out.println(sql);

		
		DbUtilities db = new MySqlUtilities();
		db.executeQuery(sql);
	}
	
	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Sets the last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * Gets the street address.
	 *
	 * @return the street address
	 */
	public String getStreetAddress() {
		return streetAddress;
	}
	
	/**
	 * Sets the street address.
	 *
	 * @param streetAddress the new street address
	 */
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	
	/**
	 * Gets the city.
	 *
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	
	/**
	 * Sets the city.
	 *
	 * @param city the new city
	 */
	public void setCity(String city) {
		this.city = city;
	}
	
	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	
	/**
	 * Sets the state.
	 *
	 * @param state the new state
	 */
	public void setState(String state) {
		this.state = state;
	}
	
	/**
	 * Gets the zip.
	 *
	 * @return the zip
	 */
	public int getZip() {
		return zip;
	}
	
	/**
	 * Sets the zip.
	 *
	 * @param zip the new zip
	 */
	public void setZip(int zip) {
		this.zip = zip;
	}
	
	/**
	 * Gets the login name.
	 *
	 * @return the login name
	 */
	public String getLoginName() {
		return loginName;
	}
	
	/**
	 * Sets the login name.
	 *
	 * @param loginName the new login name
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	/**
	 * Gets the pin.
	 *
	 * @return the pin
	 */
	public int getPin() {
		return pin;
	}
	
	/**
	 * Sets the pin.
	 *
	 * @param pin the new pin
	 */
	public void setPin(int pin) {
		this.pin = pin;
	}

	/**
	 * Gets the customer id.
	 *
	 * @return the customer id
	 */
	public String getCustomerID() {
		return customerID;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return this.customerID;
	}

	
}
