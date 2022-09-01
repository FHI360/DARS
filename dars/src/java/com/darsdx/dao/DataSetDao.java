/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.dao;

import com.darsdx.business.DataSet;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface DataSetDao 
{
    public void saveDataSet(DataSet dst) throws Exception;
    public void updateDataSet(DataSet dst) throws Exception;
    public void deleteDataSet(DataSet dst) throws Exception;
    public DataSet getDataSet(String dataSetId) throws Exception;
    public List getAllDataSets() throws Exception;
    public List getDataSetsByDhisId(String dhisId) throws Exception;
    public DataSet getDataSet(String dataSetId,String dhisId) throws Exception;
}
