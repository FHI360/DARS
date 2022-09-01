/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.dao;

import com.darsdx.business.ImisDatavalue;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface ImisDatavalueDao 
{
    public void saveImisDatavalue(ImisDatavalue dv) throws Exception;
    public void updateImisDatavalue(ImisDatavalue dv) throws Exception;
    public void deleteImisDatavalue(ImisDatavalue dv) throws Exception;
    public void saveOrUpdateImisDatavalue(ImisDatavalue dv) throws Exception;
    public List getAllImisDatavalues() throws Exception;
    public ImisDatavalue getImisDatavalue(String recordId) throws Exception;
    public List getDistinctPeriodsFromImisDatavalues() throws Exception;
}
