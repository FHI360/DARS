/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author smomoh
 */
public class XmlProcessor 
{
    public static List splitXmlToString(List list,String splitParam)
    {
        List deParamList=new ArrayList();
        for(int i=0; i<list.size(); i++)
        {
            //System.err.println("list.get(i).toString() is "+list.get(i).toString());
            String[] deArray=list.get(i).toString().split(splitParam);
            if(deArray !=null)
            {
                for(int j=0; j<deArray.length; j++)
                {
                    deParamList.add(deArray[j]);
                }
            }
        }
        return deParamList;
    }
}
