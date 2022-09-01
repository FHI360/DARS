/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.dao;

import com.darsdx.business.DataElement;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface DataElementDao 
{
    public void saveDataElement(DataElement de) throws Exception;
    public void updateDataElement(DataElement de) throws Exception;
    public void deleteDataElement(DataElement de) throws Exception;
    public DataElement getDataElement(String dataElementId) throws Exception;
    public List getAllDataElements() throws Exception;
    public List getDataElementsByDhisId(String dhisId) throws Exception;
    public DataElement getDataElement(String dhisId,String dataElementId) throws Exception;
}
