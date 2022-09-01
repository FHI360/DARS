/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.operationsmanagement;

import com.darsdx.business.DxCategoryCombination;
import com.darsdx.business.DxDataElement;
import com.darsdx.business.DxOrganizationUnit;
import com.darsdx.business.DxOrganizationUnitMatch;
import com.darsdx.dao.DaoUtil;

/**
 *
 * @author smomoh
 */
public class OrganizationUnitOperationsManager 
{
    public DxOrganizationUnitMatch validateOrganizationUnitMatch(DxOrganizationUnitMatch oum)
  {
      if(oum !=null)
      {
          try
          {
            String matchDescription=oum.getProducerInstanceId()+"-->"+oum.getConsumerInstanceId();
            int validationScore=0;
            DaoUtil util=new DaoUtil();
            DxOrganizationUnit cou=util.getDxOrganizationUnitDaoInstance().getOrganizationUnitByNameAndInstance(oum.getConsumerOrgUnitName(),oum.getConsumerInstanceId());
            if(cou !=null)
            {
                oum.setConsumerOrgUnitId(cou.getOrgunitId());
                oum.setConsumerouvalidated(1);
                validationScore++;
            }
            else
            oum.setConsumerouvalidated(0);
            DxOrganizationUnit pou=util.getDxOrganizationUnitDaoInstance().getOrganizationUnitByNameAndInstance(oum.getConsumerOrgUnitName(),oum.getConsumerInstanceId());
            if(pou !=null)
            {
                oum.setProducerOrgUnitId(pou.getOrgunitId());
                oum.setProducerouvalidated(1);
                validationScore++;
            }
            else
            oum.setProducerouvalidated(0);
            oum.setTotalValidationScore(validationScore);
            oum.setMatchDescription(matchDescription);
          }
          catch(Exception ex)
          {
              ex.printStackTrace();
          }
      }
      return oum;
  }
  public void validateAndSaveOrganizationUnitMatch(DxOrganizationUnitMatch oum)
  {
      if(oum !=null)
      {
          DaoUtil util=new DaoUtil();
          try
          {
               oum=validateOrganizationUnitMatch(oum);
               DxOrganizationUnitMatch oum2=util.getDxOrganizationUnitMatchDaoInstance().getDxOrganizationUnitMatchByOuNamesAndInstances(oum.getConsumerOrgUnitName(),oum.getProducerOrgUnitName(),oum.getConsumerInstanceId(),oum.getProducerInstanceId());
               if(oum2 !=null)
               {
                   oum.setRecordId(oum2.getRecordId());
                   util.getDxOrganizationUnitMatchDaoInstance().updateOrganizationUnit(oum);
               }
               else
               util.getDxOrganizationUnitMatchDaoInstance().saveOrganizationUnit(oum);
          }
          catch(Exception ex)
          {
              ex.printStackTrace();
          }
      }
  }
}
