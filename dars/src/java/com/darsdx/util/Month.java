/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.darsdx.util;

import java.io.Serializable;

/**
 *
 * @author smomoh
 */
public class Month implements Serializable
{
    private String name;
    private String shortName;
    private String value;
    private int year;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
    
}
