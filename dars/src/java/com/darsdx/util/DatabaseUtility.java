/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.util;

import com.darsdx.dao.DaoUtil;
import java.util.List;

/**
 *
 * @author smomoh
 */
public class DatabaseUtility {
    
    DaoUtil util=new DaoUtil();
    public void updateAssessment()
    {
        
    }
    public boolean tableExists(String tableName)
    {
        tableName=tableName.toUpperCase();
        boolean tableExists=false;
        String query="SELECT TABLENAME FROM SYS.SYSTABLES WHERE UPPER(TABLENAME)='"+tableName+"'";
        List list=util.execSqlQuery(query);
        if(list !=null && !list.isEmpty())
        {
            tableExists=true;
        }
        return tableExists;
    }
    public void dropTable(String tableName)
    {
        try
        {
        if(tableExists(tableName))
        {
            util.updateDatabase("DROP TABLE "+tableName);
            System.err.println(tableName+" dropped");
        }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void dropDhisBusinessRulesTable()
    {
        dropTable("DHISBUSINESSRULE");
    }
    public void dropIntermediateFlatTable()
    {
        dropTable("INTERMEDIATEFLATTABLE");
    }
    public void dropSourceDataTable()
    {
        dropTable("SOURCEDATA");
    }
    public void dropFinalFlatTable()
    {
        dropTable("FINALFLATTABLE");
    }
    
    public boolean createDhisBusinessRulesTable()
    {
        try
        {
            dropDhisBusinessRulesTable();
            if(!tableExists("DHISBUSINESSRULES"))
            {
                String query="CREATE TABLE BUSINESSRULE (RECORDID INTEGER GENERATED ALWAYS AS IDENTITY(start with 1, increment by 1)  NOT NULL  PRIMARY KEY,"
                        + "PRODUCERINSTANCEID VARCHAR(15) NOT NULL, PRODUCERDEID VARCHAR(15) NOT NULL, PRODUCERCATCOMBOID VARCHAR(15) NOT NULL, "
                        + "CONSUMERINSTANCEID VARCHAR(15) NOT NULL, CONSUMERDEID VARCHAR(15) NOT NULL, CONSUMERCATCOMBOID VARCHAR(15) NOT NULL, BUSLOGIC VARCHAR(15), "
                        + "RECORDGRPID VARCHAR(15) DEFAULT 'rgid'  NOT NULL, LASTMODIFIEDDATE DATE DEFAULT '1900-01-01'  NOT NULL, PRODUCERDENAME VARCHAR(500), "
                        + "PRODUCERCATCOMBONAME VARCHAR(500), CONSUMERDENAME VARCHAR(500), CONSUMERCATCOMBONAME VARCHAR(500),ATTRIBUTEOPTIONCOMBOID VARCHAR(11),"
                        + "ATTRIBUTEOPTIONCOMBONAME VARCHAR(200), DATASET VARCHAR(25),CDEVALIDATED NUMERIC(1) DEFAULT 0  NOT NULL, CCCVALIDATED NUMERIC(1) DEFAULT 0  NOT NULL,"
                        + "PDEVALIDATED NUMERIC(1) DEFAULT 0  NOT NULL,PCCVALIDATED NUMERIC(1) DEFAULT 0  NOT NULL, VALIDATIONSCORE NUMERIC(1))";
                        
                /*String query="CREATE TABLE DHISBUSINESSRULES "
                + "(RECORDID INTEGER GENERATED ALWAYS AS IDENTITY(start with 1, increment by 1)  NOT NULL PRIMARY KEY,"
                + " DATAELEMENTAID VARCHAR(25), DATAELEMENTANAME VARCHAR(1000), CATEGORYCOMBOOPTIONAID VARCHAR(25), "
                + "CATEGORYCOMBOOPTIONANAME VARCHAR(100), DATAELEMENTBID VARCHAR(25), DATAELEMENTBNAME VARCHAR(1000), "
                + "CATEGORYCOMBOOPTIONBID VARCHAR(25), CATEGORYCOMBOOPTIONBNAME VARCHAR(100), MATCHDESCRIPTION VARCHAR(100),"
                + "DATASET VARCHAR(100), LOGIC VARCHAR(100))";*/
                util.updateDatabase(query);
                util.updateDatabase("create index index_dbrdeaname on DHISBUSINESSRULES(DATAELEMENTANAME)");
                util.updateDatabase("create index index_dbrcatcomboaname on DHISBUSINESSRULES(CATEGORYCOMBOOPTIONANAME)");
                util.updateDatabase("create index index_dbrdebname on DHISBUSINESSRULES(DATAELEMENTBNAME)");
                util.updateDatabase("create index index_dbrcatcombobname on DHISBUSINESSRULES(CATEGORYCOMBOOPTIONBNAME)");
                util.updateDatabase("create index index_dbrlogic on DHISBUSINESSRULES(LOGIC)");
            }
        }
        catch(Exception ex)
        {
            System.err.println("Error creating DHISBUSINESSRULES table");
            return false;
        }
        return true;
    }//   ;
    public boolean createFinalFlatTable()
    {
        try
        {
            dropFinalFlatTable();
            if(!tableExists("FINALFLATTABLE"))
            {
                String query="CREATE TABLE FINALFLATTABLE "
                + "(RECORDID INTEGER GENERATED ALWAYS AS IDENTITY(start with 1, increment by 1)  NOT NULL PRIMARY KEY,"
                + "PRODUCERINSTANCE VARCHAR(15) NOT NULL,CONSUMERINSTANCE VARCHAR(15) NOT NULL, CONSUMERORGANIZATIONUNITID VARCHAR(15),"
                + "CONSUMERORGANIZATIONUNITNAME VARCHAR(200) NOT NULL, CONSUMERDATAELEMENTID VARCHAR(15),"
                + "CONSUMERDATAELEMENTNAME VARCHAR(200) NOT NULL,CONSUMERCATEGORYID VARCHAR(15), CONSUMERCATEGORYNAME VARCHAR(100) NOT NULL,"
                + "STARTDATE DATE NOT NULL, DATAVALUE DECIMAL(11) DEFAULT 0  NOT NULL, LASTMODIFIEDDATE DATE NOT NULL)";
                util.updateDatabase(query);
                util.updateDatabase("create index index_fftdhisinstance on FINALFLATTABLE(CONSUMERINSTANCE)");
                util.updateDatabase("create index index_fftcouid on FINALFLATTABLE(CONSUMERORGANIZATIONUNITID)");
                util.updateDatabase("create index index_fftcouname on FINALFLATTABLE(CONSUMERORGANIZATIONUNITNAME)");
                util.updateDatabase("create index index_fftcdeid on FINALFLATTABLE(CONSUMERDATAELEMENTID)");
                util.updateDatabase("create index index_fftstartdate on FINALFLATTABLE(STARTDATE)");
                util.updateDatabase("create index index_fftcdename on FINALFLATTABLE(CONSUMERDATAELEMENTNAME)");
                util.updateDatabase("create index index_fftccid on FINALFLATTABLE(CONSUMERCATEGORYID)");
                util.updateDatabase("create index index_fftccname on FINALFLATTABLE(CONSUMERCATEGORYNAME)");
           }
        }
        catch(Exception ex)
        {
            System.err.println("Error creating FINALFLATTABLE table");
            return false;
        }
        return true;
    }// DATASET VARCHAR(100), LOGIC VARCHAR(100), LASTMODIFIEDDATE DATE NOT NULL, PRIMARY KEY (RECORDID));
    public boolean createIntermediateFlatTable()
    {
        try
        {
            dropIntermediateFlatTable();
            if(!tableExists("INTERMEDIATEFLATTABLE"))
            {
                String query="CREATE TABLE INTERMEDIATEFLATTABLE"
                + "(RECORDID INTEGER GENERATED ALWAYS AS IDENTITY(start with 1, increment by 1)  NOT NULL PRIMARY KEY,"
                + "CONSUMERINSTANCE VARCHAR(15) NOT NULL, CONSUMERORGANIZATIONUNITID VARCHAR(15),"
                + "CONSUMERORGANIZATIONUNITNAME VARCHAR(200) NOT NULL, CONSUMERDATAELEMENTID VARCHAR(15),"
                + "CONSUMERDATAELEMENTNAME VARCHAR(200) NOT NULL, CONSUMERCATEGORYID VARCHAR(15) NOT NULL,"
                + "CONSUMERCATEGORYNAME VARCHAR(200) NOT NULL, PRODUCERINSTANCE VARCHAR(15) NOT NULL,"
                + "PRODUCERORGANIZATIONUNIT VARCHAR(200) NOT NULL, PRODUCERDATAELEMENT VARCHAR(200) NOT NULL,"
                + "PRODUCERCATEGORY VARCHAR(200) NOT NULL, STARTDATE DATE NOT NULL,DATAVALUE DECIMAL(11) DEFAULT 0  NOT NULL,"
                + "ORGUNITTYPE VARCHAR(100), ORGUNITGROUP VARCHAR(100),ORGANIZATIONUNITOWNERSHIP VARCHAR(100), FUNDINGBODY VARCHAR(100),"
                + "DATASET VARCHAR(100), LOGIC VARCHAR(100), LASTMODIFIEDDATE DATE NOT NULL)";
                util.updateDatabase(query);
                util.updateDatabase("create index index_iftcinst on INTERMEDIATEFLATTABLE(CONSUMERINSTANCE)");
                util.updateDatabase("create index index_iftcouid on INTERMEDIATEFLATTABLE(CONSUMERORGANIZATIONUNITID)");
                util.updateDatabase("create index index_iftcdeid on INTERMEDIATEFLATTABLE(CONSUMERDATAELEMENTID)");
                util.updateDatabase("create index index_iftccid on INTERMEDIATEFLATTABLE(CONSUMERCATEGORYID)");
                util.updateDatabase("create index index_iftpinst on INTERMEDIATEFLATTABLE(PRODUCERINSTANCE)");
                util.updateDatabase("create index index_iftstdate on INTERMEDIATEFLATTABLE(STARTDATE)");
                //util.updateDatabase("create index index_iftlga on INTERMEDIATEFLATTABLE(LGA)");
                //util.updateDatabase("create index index_iftorgunittype on INTERMEDIATEFLATTABLE(ORGUNITTYPE)");
                //util.updateDatabase("create index index_iftorgunitgrp on INTERMEDIATEFLATTABLE(ORGUNITGROUP)");
            }
        }
        catch(Exception ex)
        {
            System.err.println("Error creating INTERMEDIATEFLATTABLE table");
            return false;
        }
        return true;
    }
    public boolean createSourceDataTable()
    {
        try
        {
            dropSourceDataTable();
            if(!tableExists("SOURCEDATA"))
            {
                String query="CREATE TABLE SOURCEDATA "
                + "(RECORDID INTEGER GENERATED ALWAYS AS IDENTITY(start with 1, increment by 1) NOT NULL PRIMARY KEY,"
                + "ORGANIZATIONUNIT VARCHAR(200) NOT NULL, DATAELEMENT VARCHAR(200) NOT NULL, CATEGORYCOMBO VARCHAR(200) NOT NULL,"
                + " STARTDATE DATE NOT NULL, DATAVALUE DECIMAL(10) DEFAULT 0  NOT NULL, DATASET VARCHAR(100), ORGUNITTYPE VARCHAR(200),"
                + "ORGUNITGROUP VARCHAR(200), ORGANIZATIONUNITOWNERSHIP VARCHAR(200), FUNDINGBODY VARCHAR(100),"
                + " DHISINSTANCE VARCHAR(12) NOT NULL,LASTMODIFIEDDATE DATE DEFAULT '2000-01-01'  NOT NULL)";
                util.updateDatabase(query);
                util.updateDatabase("create index index_sddhinstance on SOURCEDATA(DHISINSTANCE)");
                util.updateDatabase("create index index_sddename on SOURCEDATA(DATAELEMENT)");
                util.updateDatabase("create index index_sdcatcombo on SOURCEDATA(CATEGORYCOMBO)");
                util.updateDatabase("create index index_sdouname on SOURCEDATA(ORGANIZATIONUNIT)");
                util.updateDatabase("create index index_sdstartdate on SOURCEDATA(STARTDATE)");
                util.updateDatabase("create index index_sdoutype on SOURCEDATA(ORGUNITTYPE)");
                util.updateDatabase("create index index_sdougrp on SOURCEDATA(ORGUNITGROUP)");
            }
        }
        catch(Exception ex)
        {
            System.err.println("Error creating SOURCEDATA table");
            return false;
        }
        return true;
    }//

    public boolean createUserTable()
    {
        try
        {
            if(!dbObjectExist("USERS"))
            {
                String usersTableQuery="create table APP.USERS"
                +"(USERNAME VARCHAR(25) not null primary key,PASSWORD VARCHAR(25) not null,"
                +"USERROLE_ID VARCHAR(25) not null,FIRST_NAME VARCHAR(25) not null,"
                +"LAST_NAME VARCHAR(25) not null,STATE VARCHAR(25) not null)";
                util.updateDatabase(usersTableQuery);
            }
        }
        catch(Exception ex)
        {
            System.err.println("Table USERS already exist");
            return false;
        }
        return true;
    }
    public boolean dbObjectExist(String objectName)
    {
        List list=util.createSqlQuery("SELECT t.tablename FROM sys.systables t WHERE t.tablename = '"+objectName+"'");
        if(list !=null && !list.isEmpty())
        return true;
        return false;
    }
    
    
}
