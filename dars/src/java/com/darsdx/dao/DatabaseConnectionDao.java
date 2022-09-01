/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.dao;

import com.darsdx.business.DatabaseConnection;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface DatabaseConnectionDao 
{
    public void saveDatabaseConnection(DatabaseConnection dbconn) throws Exception;
    public void updateDatabaseConnection(DatabaseConnection dbconn) throws Exception;
    public void deleteDatabaseConnection(DatabaseConnection dbconn) throws Exception;
    public List getAllDatabaseConnections() throws Exception;
    public DatabaseConnection getDatabaseConnection(String id) throws Exception;
    public DatabaseConnection getDatabaseConnectionByName(String connectionName) throws Exception;
}
