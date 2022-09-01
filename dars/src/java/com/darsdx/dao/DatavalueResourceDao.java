/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.dao;

import com.darsdx.business.DatavalueResource;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface DatavalueResourceDao 
{
    public DatavalueResource getDatavalueResource(String dhisId) throws Exception;
    public void saveDatavalueResource(DatavalueResource dvr) throws Exception;
    public void updateDatavalueResource(DatavalueResource dvr) throws Exception;
    public void deleteDatavalueResource(DatavalueResource dvr) throws Exception;
    public List getAllDatavalueResources() throws Exception;
    public void saveOrUpdateDatavalueResource(DatavalueResource dvr) throws Exception;
}
