/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.dao;

import com.darsdx.business.IntermediateFlatTable;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface IntermediateFlatTableDao 
{
    public void saveIntermediateFlatTable(IntermediateFlatTable ift) throws Exception;
    public void updateIntermediateFlatTable(IntermediateFlatTable ift) throws Exception;
    public void deleteIntermediateFlatTable(IntermediateFlatTable ift) throws Exception;
    public List getDistinctConsumerOrgUnitIdsFromIntermediateFlatTable(String instance) throws Exception;
    public List aggregateRecordsWithSameOrgUnitDataElementAndCategory(String consumerOrganizationUnitId) throws Exception;
    public List getIntermediateDhisFlatTableByOrgUnitName(String consumerOrganizationUnitName) throws Exception;
    public List getIntermediateDhisFlatTableByConsumerOrganizationUnitId(String consumerOrganizationUnitId) throws Exception;
    public List getDistinctConsumerOrgUnitIdsFromIntermediateFlatTable() throws Exception;
    public List getAllRecords() throws Exception;
    public IntermediateFlatTable getIntermediateFlatTable(IntermediateFlatTable ift) throws Exception;
    public List getDistinctConsumerOrgUnitNamesFromIntermediateFlatTable(String producerInstanceId,String consumerInstanceId) throws Exception;
    public List aggregateRecordsWithSameOrgUnitDataElementAndCategory(String consumerOrganizationUnitName,String producerInstanceId,String consumerInstanceId) throws Exception;
    public List getIntermediateFlatTableByOrgUnitNameAndInstance(String consumerOrganizationUnitName,String producerInstanceId,String consumerInstanceId) throws Exception;
}
