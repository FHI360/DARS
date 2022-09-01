/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.dao;

import com.darsdx.business.DxDataElement;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface DxDataElementDao 
{
    public void deleteDataElement(DxDataElement de) throws Exception;
    public void saveOrUpdateDataElement(DxDataElement de) throws Exception;
    public DxDataElement getDataElement(String dataElementId) throws Exception;
    public DxDataElement getDataElementByName(String dataElementName) throws Exception;
    public DxDataElement getDataElementByNameAndDhisInstance(String dataElementName, String dhisInstance) throws Exception;
    public List getAllDataElements() throws Exception;
    public List getDataElementByDhisInstance(String dhisInstance) throws Exception;
    public DxDataElement getDataElementByIdAndDhisInstance(String dataElementId, String dhisInstance) throws Exception;
}
