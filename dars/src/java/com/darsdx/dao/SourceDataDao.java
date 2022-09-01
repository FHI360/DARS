/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.dao;

import com.darsdx.business.SourceData;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface SourceDataDao 
{
    public void saveSourceData(SourceData sd) throws Exception;
    public void updateSourceData(SourceData sd) throws Exception;
    public void deleteSourceData(SourceData sd) throws Exception;
    public List getSourceData(String instanceId) throws Exception;
    public List getAllSourceData() throws Exception;
    public SourceData getSourceDataById(int id) throws Exception;
    public SourceData getSourceData(String organizationUnit,String dataElement,String category,String startDate) throws Exception;
    public int getNumberOfSourceDataSaved() throws Exception;
    public List getDistinctDataElements(String instance) throws Exception;
    public List getSourceDataByDataElementAndInstance(String dataElement,String instance) throws Exception;
    public List getDistinctDhisInstances() throws Exception;
    public List getSourceDataByDataElementCategoryAndInstance(String dataElement,String categoryCombo,String instance) throws Exception;
}
