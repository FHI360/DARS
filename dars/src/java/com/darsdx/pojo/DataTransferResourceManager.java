/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.pojo;

import com.darsdx.business.DatabaseConnection;
import com.darsdx.business.DhisInstance;
import com.darsdx.business.DxOrganizationUnitMatch;
import com.darsdx.dao.DaoUtil;

/**
 *
 * @author smomoh
 */
public class DataTransferResourceManager 
{
    public String getSourceOrSinkName(String id)
    {
        String name=null;
        try
        {
            DatabaseConnection dbconn=DaoUtil.getDatabaseConnectionDaoInstance().getDatabaseConnection(id);
            if(dbconn !=null)
            {
                name=dbconn.getConnectionName();
            }
            else
            {
                DhisInstance dinst=DaoUtil.getDhisInstanceDaoInstance().getDhisInstanceById(id);
                if(dinst !=null)
                {
                    name=dinst.getDhisName();
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return name;
    }
    public String getDECatMappingName(String id)
    {
        String name=null;
        try
        {
            if(id !=null && id.trim().equalsIgnoreCase("notrequired"))
            {
                name="Mapping not required";
            }
            else
            {
                DxOrganizationUnitMatch oum=DaoUtil.getDxOrganizationUnitMatchDaoInstance().getDxOrganizationUnitMatchByIdAndMatchDescription(id, name, id);
                if(oum !=null)
                {
                    name=oum.getMatchDescription();
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return name;
    }
    public String getOrgUnitMappingName(String id)
    {
        String name=null;
        try
        {
            if(id !=null && id.trim().equalsIgnoreCase("notrequired"))
            {
                name="Mapping not required";
            }
            else
            {
                DatabaseConnection dbconn=DaoUtil.getDatabaseConnectionDaoInstance().getDatabaseConnection(id);
                if(dbconn !=null)
                {
                    name=dbconn.getConnectionName();
                }
                else
                {
                    DhisInstance dinst=DaoUtil.getDhisInstanceDaoInstance().getDhisInstanceById(id);
                    if(dinst !=null)
                    {
                        name=dinst.getDhisName();
                    }
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return name;
    }
}
