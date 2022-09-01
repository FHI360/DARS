/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.business;

import java.io.Serializable;

/**
 *
 * @author smomoh
 */
public class Task implements Serializable
{
    private String taskId;
    private String taskName;
    private String taskStatus;
    private String startTime;
    private String endTime;
    private String duration;
    private int taskValue;
    private int serialNumber;
    private boolean active;
    private boolean idle;
    private boolean complete;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) 
    {
        if(taskStatus !=null)
        {
            if(taskStatus.equalsIgnoreCase("idle"))
            setIdle(true);
            if(taskStatus.equalsIgnoreCase("active"))
            setActive(true);
            if(taskStatus.equalsIgnoreCase("complete"))
            setComplete(true);
            if(taskStatus.equalsIgnoreCase("error"))
            setComplete(false);
        }
        this.taskStatus = taskStatus;
    }

    public int getTaskValue() {
        return taskValue;
    }

    public void setTaskValue(int taskValue) {
        this.taskValue = taskValue;
    }

    public boolean isActive() {
        return active;
    }

    private void setActive(boolean active) {
        this.active = active;
    }

    public boolean isComplete() {
        return complete;
    }

    private void setComplete(boolean complete) {
        this.complete = complete;
    }

    public boolean isIdle() {
        return idle;
    }

    private void setIdle(boolean idle) {
        this.idle = idle;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    
}
