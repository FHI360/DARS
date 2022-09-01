/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.dao;

import com.darsdx.business.OrganizationUnit;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface OrganizationUnitDao 
{
    public void saveOrganizationUnit(OrganizationUnit ou) throws Exception;
    public void updateOrganizationUnit(OrganizationUnit ou) throws Exception;
    public void deleteOrganizationUnit(OrganizationUnit ou) throws Exception;
    public OrganizationUnit getOrganizationUnit(String orgunitId) throws Exception;
    public List getAllOrganizationUnits() throws Exception;
    public List getOrganizationUnitsByOuLevel(int oulevel) throws Exception;
    public List getOrganizationUnitsByParentId(String dhisId,String parentId) throws Exception;
    public List getOrganizationUnitsByOuLevelAndParentId(String parentId,int oulevel) throws Exception;
    public List getFacilityParentListByLevel2OuId(String level2OuId) throws Exception;
    public List getOrganizationUnitsByOuLevel(String dhisId,int oulevel) throws Exception;
    public List getOrganizationUnitsByOuLevelAndPrefix(String prefix,String dhisId,int oulevel) throws Exception;
    public OrganizationUnit getOrganizationUnit(String dhisId,String orgunitId) throws Exception;
    public List getDistinctOrganizationUnitIdsByOuLevel(String dhisId,int oulevel) throws Exception;
    public List getOrganizationUnitsByDhisId(String dhisId) throws Exception;
    public List getOrganizationUnitsByOuPath(String dhisId,String parentId) throws Exception;
    public List getOrganizationUnitAndParents(String dhisId,String orgunitId) throws Exception;
    public List getParentOrganizationUnitsOnly(String dhisId,String orgunitId) throws Exception;
}
