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
package edu.pitt.utilities;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.DefaultTableModel;

// TODO: Auto-generated Javadoc
/**
 * The Interface DbUtilities.
 */
public interface DbUtilities {
    
    /**
     * Gets the result set.
     *
     * @param sql the sql
     * @return the result set
     * @throws SQLException the SQL exception
     */
    public ResultSet getResultSet(String sql) throws SQLException;
    
    /**
     * Execute query.
     *
     * @param sql the sql
     * @return true, if successful
     */
    public boolean executeQuery(String sql);
    
    /**
     * Gets the data table.
     *
     * @param sql the sql
     * @return the data table
     * @throws SQLException the SQL exception
     */
    public DefaultTableModel getDataTable(String sql) throws SQLException;
    
    /**
     * Connection close.
     *
     * @throws SQLException the SQL exception
     */
    public void connectionClose() throws SQLException;

    
    /**
     * Gets the data table.
     *
     * @param sqlQuery the sql query
     * @param customColumnNames the custom column names
     * @return the data table
     * @throws SQLException the SQL exception
     */
    public DefaultTableModel getDataTable(String sqlQuery, String[] customColumnNames) throws SQLException;

}
