/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.dao;

import com.darsdx.business.DataTransferManager;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface DataTransferManagerDao 
{
    public void saveDataTransferManager(DataTransferManager dtm) throws Exception;
    public void updateDataTransferManager(DataTransferManager dtm) throws Exception;
    public void deleteDataTransferManager(DataTransferManager dtm) throws Exception;
    public DataTransferManager getDataTransferManager(String id) throws Exception;
    public List getAllDataTransferManagers() throws Exception;
    public DataTransferManager getDataTransferManagerByName(String name) throws Exception;
}
