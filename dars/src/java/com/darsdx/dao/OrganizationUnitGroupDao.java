/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.dao;

import com.darsdx.business.OrganisationUnitGroup;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface OrganizationUnitGroupDao 
{
    public void saveOrganizationUnitGroup(OrganisationUnitGroup ou) throws Exception;
    public void updateOrganizationUnitGroup(OrganisationUnitGroup ou) throws Exception;
    public void deleteOrganizationUnitGroup(OrganisationUnitGroup ou) throws Exception;
    public OrganisationUnitGroup getOrganizationUnitGroup(String orgunitId) throws Exception;
    public OrganisationUnitGroup getOrganizationUnitGroupByIdAndInstance(String orgunitId,String instance) throws Exception;
    public OrganisationUnitGroup getOrganizationUnitGroupByName(String orgunitGroupName) throws Exception;
    public OrganisationUnitGroup getOrganizationUnitGroupByNameAndInstance(String orgunitGroupName,String instance) throws Exception;
    public List getOrganizationUnitGroups(String dhisInstance) throws Exception;
}
