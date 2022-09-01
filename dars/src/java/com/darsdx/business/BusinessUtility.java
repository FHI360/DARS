/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.business;

import com.darsdx.dao.DaoUtil;
import java.io.Serializable;

/**
 *
 * @author smomoh
 */
public class BusinessUtility implements Serializable
{
    DaoUtil util=new DaoUtil();
    public DxOrganizationUnit getDxOrganizationUnit(String orgUnitId,String instanceId)
    {
        DxOrganizationUnit dou=null;
        try
        {
            dou=util.getDxOrganizationUnitDaoInstance().getOrganizationUnitByIdAndInstance(orgUnitId, instanceId);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return dou;
    }
    public DhisInstance getDhisInstance(String instanceId)
    {
        DhisInstance dInstance=null;
        try
        {
            dInstance=util.getDhisInstanceDaoInstance().getDhisInstanceById(instanceId);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return dInstance;
    }
}
