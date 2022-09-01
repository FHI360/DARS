/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.dao;

import com.darsdx.business.FinalFlatTable;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface FinalFlatTableDao 
{
    public void saveFinalFlatTable(FinalFlatTable fft) throws Exception;
    public void updateFinalFlatTable(FinalFlatTable fft) throws Exception;
    public void deleteFinalFlatTable(FinalFlatTable fft) throws Exception;
    public FinalFlatTable getFinalFlatTable(String consumerOrganizationUnitId,String consumerDataElementId,String consumerCategoryId,String attributeOptionCombo,String startDate,String consumerInstanceId) throws Exception;
    public List getDistinctOrgUnitIdsFromFinalFlatTable() throws Exception;
    public List getNonDuplicateRecords(String orgUnitId,String instance) throws Exception;
    public List getAllRecords() throws Exception;
    public List getNonDuplicateRecords(String orgUnitId) throws Exception;
    public FinalFlatTable getFinalFlatTableByNames(String consumerOrganizationUnitName,String consumerDataElementName,String consumerCategoryName,String startDate,String consumerInstanceId) throws Exception;
    public List getDistinctCatComboNamesFromFinalFlatTable(boolean nullIdsOnly) throws Exception;
    public List getDistinctDataElementNamesFromFinalFlatTable(boolean nullIdsOnly) throws Exception;
    public List getDistinctOrgUnitNamesFromFinalFlatTable(String consumerInstance,boolean nullIdsOnly) throws Exception;
    public List getFinalFlatTableByOrgUnitNameAndDhisInstance(String orgUnitName,String instance) throws Exception;
    public List getFinalFlatTableByDataElementNameAndDhisInstance(String deName,String instance) throws Exception;
    public List getFinalFlatTableByCatComboNameAndDhisInstance(String ccName,String instance) throws Exception;
    
}
