/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.business;

/**
 *
 * @author smomoh
 */
public class MatchDescriptor 
{
    private DhisInstance producerInstance;
    private DhisInstance consumerInstance;

    public DhisInstance getConsumerInstance() {
        return consumerInstance;
    }

    public void setConsumerInstance(DhisInstance consumerInstance) {
        this.consumerInstance = consumerInstance;
    }

    public DhisInstance getProducerInstance() {
        return producerInstance;
    }

    public void setProducerInstance(DhisInstance producerInstance) {
        this.producerInstance = producerInstance;
    }
    
}
