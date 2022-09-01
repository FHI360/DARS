/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.operationsmanagement;

import com.darsdx.business.BusinessRule;
import com.darsdx.business.DxCategoryCombination;
import com.darsdx.business.DxDataElement;
import com.darsdx.dao.DaoUtil;

/**
 *
 * @author smomoh
 */
public class BusinessRuleOperationsManager 
{
  public BusinessRule validateBusinessRule(BusinessRule br)
  {
      if(br !=null)
      {
          try
          {
            int validationScore=0;
            DaoUtil util=new DaoUtil();
            DxDataElement pde=util.getDxDataElementDaoInstance().getDataElementByNameAndDhisInstance(br.getProducerDeName(),br.getProducerInstanceId());
            if(pde !=null)
            {
                br.setProducerDeId(pde.getDataElementId());
                br.setPdevalidated(1);
                validationScore++;
            }
            else
            br.setPdevalidated(0);
            DxCategoryCombination pcc=util.getDxCategoryCombinationDaoInstance().getCategoryCombinationByNameAndDhisInstance(br.getProducerCatComboName(), br.getProducerInstanceId());
            if(pcc !=null)
            {
                br.setProducerCatComboId(pcc.getCatComboId());
                br.setPccvalidated(1);
                validationScore++;
            }
            else
            br.setPccvalidated(0);
            DxDataElement cde=util.getDxDataElementDaoInstance().getDataElementByNameAndDhisInstance(br.getConsumerCatComboName(),br.getConsumerInstanceId());
            if(cde !=null)
            {
                br.setConsumerDeId(cde.getDataElementId());
                br.setCdevalidated(1);
                validationScore++;
            }
            else
            br.setCdevalidated(0);
            DxCategoryCombination ccc=util.getDxCategoryCombinationDaoInstance().getCategoryCombinationByNameAndDhisInstance(br.getConsumerCatComboName(), br.getConsumerInstanceId());
            if(ccc !=null)
            {
                br.setConsumerCatComboId(ccc.getCatComboId());
                br.setCccvalidated(1);
                validationScore++;
            }
            else
            br.setCccvalidated(0);
            br.setValidationScore(validationScore);
          }
          catch(Exception ex)
          {
              ex.printStackTrace();
          }
      }
      return br;
  }
  public void validateAndSaveBusinessRule(BusinessRule br)
  {
      if(br !=null)
      {
          DaoUtil util=new DaoUtil();
          try
          {
               br=validateBusinessRule(br);
               BusinessRule br2=util.getBusinessRuleDaoInstance().getBusinessRuleByConsumerAndProducerParameters(br.getProducerDeName(), br.getProducerCatComboName(), br.getConsumerDeName(), br.getConsumerCatComboName());
               if(br2 !=null)
               {
                   br.setRecordId(br2.getRecordId());
                   util.getBusinessRuleDaoInstance().updateBusinessRule(br);
               }
               else
               util.getBusinessRuleDaoInstance().saveBusinessRule(br);
          }
          catch(Exception ex)
          {
              ex.printStackTrace();
          }
      }
  }
}
