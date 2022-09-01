/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.dao;

import com.darsdx.business.DhisInstance;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface DhisInstanceDao 
{
    public void saveDhisInstance(DhisInstance dhisInstance) throws Exception;
    public void updateDhisInstance(DhisInstance dhisInstance) throws Exception;
    public void deleteDhisInstance(DhisInstance dhisInstance) throws Exception;
    public DhisInstance getDhisInstanceById(String id) throws Exception;
    public List getAllDhisInstances() throws Exception;
    public DhisInstance[] getDhisInstanceComboItems();
    public DhisInstance getDhisInstanceByInstanceName(String instanceName) throws Exception;
    public List filterInstance(List instanceList,String dhisInstanceToExclude) throws Exception;
    public List getInstance(List instanceCodes) throws Exception;
    public List getDhisInstanceByConnectionType(String connectionType) throws Exception;
}
