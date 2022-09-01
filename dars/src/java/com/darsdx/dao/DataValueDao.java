/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.dao;

import com.darsdx.business.DataValue;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface DataValueDao 
{
    public void saveDataValue(DataValue dv) throws Exception;
    public void updateDataValue(DataValue dv) throws Exception;
    public void deleteDataValue(DataValue dv) throws Exception;
    public void saveOrUpdateDataValue(DataValue dv) throws Exception;
    public List getAllDataValues() throws Exception;
    public DataValue getDataValue(int recordId) throws Exception;
    public DataValue getDataValue(String datasetId,String dataElementId,String orgUnitId,String categoryId,String period) throws Exception;
    public List getDataValuesForReport() throws Exception;
    public List getDataValuesByParentId(String parentId) throws Exception;
    public List getDistinctPeriodsFromDataValues() throws Exception;
    public List getDataValuesByDhisIdPeriodAndPrefix(String dhisId,String period,String prefix) throws Exception;
    public List getDataValuesByDhisId(String dhisId) throws Exception;
    public List getDistinctOrganizationUnitsByDataElementAndPeriodFromDataValues(String dataElementId,String period) throws Exception;
    public List getDistinctDataElementsByPeriodsFromDataValues(String period) throws Exception;
    public List getDatavaluesByOrganizationUnitsDataElementAndPeriod(String orgunitId,String dataElementId,String period) throws Exception;
}
