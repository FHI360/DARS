/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.dao;

import com.darsdx.business.CleanedDatavalue;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface CleanedDatavalueDao 
{
    public void saveCleanedDatavalue(CleanedDatavalue dv) throws Exception;
    public void updateCleanedDatavalue(CleanedDatavalue dv) throws Exception;
    public void deleteCleanedDatavalue(CleanedDatavalue dv) throws Exception;
    public List getAllCleanedDatavalues() throws Exception;
    public CleanedDatavalue getCleanedDatavalue(int recordId) throws Exception;
    public CleanedDatavalue getCleanedDatavalue(String datasetId,String dataElementId,String orgUnitId,String categoryId,String period) throws Exception;
    public List getCleanedDatavaluesForReport() throws Exception;
    public List getDistinctPeriodsFromDatavalues() throws Exception;
    public List getDataFromDatavalues(String orgunitPath,String dataElementId,String periodQuery) throws Exception;
    public List getCleanedDatavaluesByDhisId(String dhisId) throws Exception;
}
