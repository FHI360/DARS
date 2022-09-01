/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.dao;

import com.darsdx.business.DxOrganizationUnit;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface DxOrganizationUnitDao 
{
    public void saveOrganizationUnit(DxOrganizationUnit ou) throws Exception;
    public void updateOrganizationUnit(DxOrganizationUnit ou) throws Exception;
    public void deleteOrganizationUnit(DxOrganizationUnit ou) throws Exception;
    public DxOrganizationUnit getOrganizationUnit(String ourunitId) throws Exception;
    public List getOrganizationUnits(String dhisInstance) throws Exception;
    public List getOrganizationUnitsByParentId(String parentId) throws Exception;
    public DxOrganizationUnit getOrganizationUnitByName(String orgunitName) throws Exception;
    public DxOrganizationUnit[] getOrganizationUnitsNotMatched(String dhisInstance) throws Exception;
    public String[] getOrganizationUnitLevelsAsArray(String dhisInstance) throws Exception;
    public List getOrganizationUnitLevels(String dhisInstance) throws Exception;
    public List getOrganizationUnits(String dhisInstance, int level) throws Exception;
    public DxOrganizationUnit getOrganizationUnitByIdAndInstance(String orgunitId,String instance) throws Exception;
    public DxOrganizationUnit getOrganizationUnitByNameAndInstance(String orgunitName,String instance) throws Exception;
    public List getOrganizationUnitsNotMatched(String instanceA,String instanceB, int instALevel, String activeInstance) throws Exception;
}
