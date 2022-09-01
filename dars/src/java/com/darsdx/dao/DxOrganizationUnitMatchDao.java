/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.dao;

import com.darsdx.business.DxOrganizationUnitMatch;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface DxOrganizationUnitMatchDao 
{
    public void saveOrganizationUnit(DxOrganizationUnitMatch oum) throws Exception;
    public void updateOrganizationUnit(DxOrganizationUnitMatch oum) throws Exception;
    public void deleteOrganizationUnit(DxOrganizationUnitMatch oum) throws Exception;
    public List getDxOrganizationUnitMatchByMatchDescription(String instanceA,String instanceB) throws Exception;
    public List getAllDxOrganizationUnitMatch() throws Exception;
    public DxOrganizationUnitMatch getDxOrganizationUnitMatchByIdAndMatchDescription(String orgUnitId,String instanceA,String instanceB) throws Exception;
    public List getDxOrganizationUnitMatchIdsByMatchDescription(String instanceA,String instanceB) throws Exception;
    public DxOrganizationUnitMatch getDxOrganizationUnitMatchByIdAndMatchDescription(int recordId) throws Exception;
    public DxOrganizationUnitMatch getDxOrganizationUnitMatchByOuNamesAndInstances(String consumerOrgunitName,String producerOrgunitName,String consumerInstance,String producerInstance) throws Exception;
    public List getDistinctProducerInstances() throws Exception;
    public List getDistinctConsumerInstances() throws Exception;
    public List getDistinctConsumerInstances(String producerInstanceId) throws Exception;
    public List getDxOrganizationUnitMatchByDhisInstances(String producerInstance,String consumerInstance) throws Exception;
    public DxOrganizationUnitMatch getDxOrganizationUnitMatchByProducerOuNamesAndInstances(String producerOrgunitName,String producerInstance,String consumerInstance) throws Exception;
    public DxOrganizationUnitMatch getDxOrganizationUnitMatchByProducerOrgUnitIdAndProducerInstance(String orgUnitId,String producerInstanceId) throws Exception;
    public DxOrganizationUnitMatch getDxOrganizationUnitMatchByConsumerOrgUnitIdAndConsumerInstance(String orgUnitId,String consumerInstanceId) throws Exception;
    public DxOrganizationUnitMatch getDxOrganizationUnitMatchByConsumerOrgUnitIdAndProducerInstance(String orgUnitId,String producerInstanceId) throws Exception;
}
