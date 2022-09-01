/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.dao;

import com.darsdx.business.BusinessRule;
import java.util.List;

/**
 *
 * @author smomoh
 */
public interface BusinessRuleDao 
{
    public void saveBusinessRule(BusinessRule bizrule) throws Exception;
    public void updateBusinessRule(BusinessRule bizrule) throws Exception;
    public void deleteBusinessRule(BusinessRule bizrule) throws Exception;
    public String getUniqueId() throws Exception;
    public List getAllBusinessRules() throws Exception;
    public List getDistinctProducerInstances() throws Exception;
    public List getDistinctConsumerInstances() throws Exception;
    public List getDistinctConsumerInstances(String producerInstanceId) throws Exception;
    public List getBusinessRules(String producerInstance,String consumerInstance) throws Exception;
    public BusinessRule getBusinessRule(BusinessRule br) throws Exception;
    public BusinessRule getBusinessRuleByProducerParameters(String producerInstance,String producerDe,String producerCatCombo) throws Exception;
    public List getBusinessRuleByConsumerParameters(String consumerInstance,String consumerDe,String consumerCatCombo) throws Exception;
    public BusinessRule getBusinessRule(int recordId) throws Exception;
    public BusinessRule getBusinessRuleByConsumerAndProducerParameters(String producerDe,String producerCatCombo,String consumerDe,String consumerCatCombo) throws Exception;
    public List getDistinctBusinessRuleInstances() throws Exception;
    public BusinessRule getBusinessRuleByProducerCatComboAndProducerInstance(String producerDataElementId,String producerCatComboName,String dhisInstance) throws Exception;
    public BusinessRule getBusinessRuleByConsumerDataElementIdAndConsumerCatComboIdAndConsumerInstanceId(String consumerDataElementId,String consumerCatComboId,String consumerInstanceId) throws Exception;
}
