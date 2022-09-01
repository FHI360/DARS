/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.dao;

/**
 *
 * @author smomoh
 */
public class DaoUtility 
{
    public static OrganizationUnitDao getOrganizationUnitDaoInstance()
    {
        return new OrganizationUnitDaoImpl();
    }
    public static BusinessRuleDao getBusinessRuleDaoInstance()
    {
        return new BusinessRuleDaoImpl();
    }
    public static DataElementDao getDataElementDaoInstance()
    {
        return new DataElementDaoImpl();
    }
    public static DataValueDao getDatavalueDaoInstance()
    {
        return new DataValueDaoImpl();
    }
    public static CleanedDatavalueDao getCleanedDatavalueDaoInstance()
    {
        return new CleanedDatavalueDaoImpl();
    }
    public static DataSetDao getDataSetDaoInstance()
    {
        return new DataSetDaoImpl();
    }
    public static CategoryOptionComboDao getCategoryOptionComboDaoInstance()
    {
        return new CategoryOptionComboDaoImpl();
    }
    public static DhisInstanceDao getDhisInstanceDaoInstance()
    {
        DhisInstanceDao dinstdao=new DhisInstanceDaoImpl();
        return dinstdao;
    }
    public static ImisDatavalueDao getImisDatavalueDaoInstance()
    {
        return new ImisDatavalueDaoImpl();
    }
}
