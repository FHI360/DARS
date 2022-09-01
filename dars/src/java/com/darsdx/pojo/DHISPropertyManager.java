/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.pojo;

import java.io.Serializable;

/**
 *
 * @author smomoh
 */
public class DHISPropertyManager implements Serializable
{
    private static String homePage="https://who-dev.baosystems.com/api/dataElements.xml";
    private static String xmlExt=".xml";
    private static String csvExt=".csv";
    private static String jsonExt=".json";
    //private static String apiUrl="/api/dataElements.xml";
    private static String dataElementUrl="/dataElements";
    private static String categoryCominationtUrl="/categoryOptionCombos";
    private static String organizationUnitUrl="/organisationUnits";
    private static String organizationUnitFieldsUrl="fields=id,name,level,parent,path,shortName";
    private static String organizationUnitGroupUrl="/organisationUnitGroups";
    private static String datasetUrl="/dataSets";
    private static String datavaluesUrl="/dataValueSets";
    private static String firstQueryParamSeperator="?";
    private static String additionalQueryParamSeperator="&";
    private static String seperator="/";
    private static String paging="paging=false";
    private static String links="links=false";
    private static String analytics="analytics";
    private static String dataValueSet="dataValueSet";
    private static String dataValueSets="dataValueSets";
    private static String dataset="dataSet=";
    private static String orgUnit="orgUnit";
    private static String orgUnitGroup="orgUnitGroup=";
    private static String startDate="startDate=";
    private static String endDate="endDate=";
    private static String dimension="dimension";
    
    private static String peDimension="pe";
    private static String deDimension="dx";
    private static String ouDimension="ou";
    private static String level="LEVEL";
    private static String nigDhis2AnalyticsQuery="https://dhis2nigeria.org.ng/dhis/api/analytics/dataValueSet.xml?dimension=dx:Jc1WjNKrObY;KWJ3cSuyzs4;m5gTF1jKQjl.W50DgZrPrUk;m5gTF1jKQjl.ItGQGDRFD4t;r6WOvUlcQm6.W50DgZrPrUk;r6WOvUlcQm6.ItGQGDRFD4t&dimension=ou:Nko8QFDmYmq;LEVEL-5&dimension=pe:201712&paging=false&links=false";
    private static String dataValueAnalyticsQuery="analytics/dataValueSet.xml?dimension=";
    private static String dataValueDatasetQuery="dataValueSets.xml?dataSet=";
    private static String dataValueFieldsQuery="fields=dataElement,period,orgUnit,categoryOptionCombo,attributeOptionCombo,value,storedBy,created,lastUpdated,followUp";
    //&dimension=ou:Nko8QFDmYmq;LEVEL-5&dimension=pe:201710;201711;201712
    //https://dhis2nigeria.org.ng/dhis/api/25/analytics/dataValueSet.xml?dimension=dx:Jc1WjNKrObY;KWJ3cSuyzs4;m5gTF1jKQjl.W50DgZrPrUk;m5gTF1jKQjl.ItGQGDRFD4t;r6WOvUlcQm6.W50DgZrPrUk;r6WOvUlcQm6.ItGQGDRFD4t;GEd2F6skCpT.W50DgZrPrUk;GEd2F6skCpT.ItGQGDRFD4t;k2Cpuz9BQtD.W50DgZrPrUk;k2Cpuz9BQtD.ItGQGDRFD4t;G7iWnz9RMy9.W50DgZrPrUk;G7iWnz9RMy9.ItGQGDRFD4t;f0YCEriaGQC.Y2SrnZVI87k;DyMyl4NeDxz.Y2SrnZVI87k;K9K1G0XX4ax.W50DgZrPrUk;K9K1G0XX4ax.ItGQGDRFD4t;HdtaLx63988.W50DgZrPrUk;HdtaLx63988.ItGQGDRFD4t;nrql7kywMLM.W50DgZrPrUk;nrql7kywMLM.ItGQGDRFD4t;ouzURM9c1FI.W50DgZrPrUk;ouzURM9c1FI.ItGQGDRFD4t;nDKVffS3ijX.W50DgZrPrUk;nDKVffS3ijX.ItGQGDRFD4t;M1kGBHgqGvv.W50DgZrPrUk;M1kGBHgqGvv.ItGQGDRFD4t;wGPpop3rz7i;mz0LIaWBAz9.k5pHlvkYR3T;TbBdCUZsgzA.Y2SrnZVI87k;Ay7qXXFJ2z8.Y2SrnZVI87k;zvtnRJJ3Mxr.Y2SrnZVI87k;kF4KJ4ucXYT.Y2SrnZVI87k;bGcL2xrAMSe.Y2SrnZVI87k;OZH9GfZqZ7q.Y2SrnZVI87k;PV88LZCbiSF.Y2SrnZVI87k;pUZ0BKgsAXp.Y2SrnZVI87k;fP1E4qRJ57a.Y2SrnZVI87k;w6nOgEFHWMG.Y2SrnZVI87k;CvX3NUIe50P.Knva5F6vpNi;CvX3NUIe50P.Jf7HweYFAFM;ZFgiCO6nyAP.Y2SrnZVI87k;u2Dzr2whr7g.Y2SrnZVI87k;tSCqxLc7dzh.Y2SrnZVI87k&dimension=ou:Nko8QFDmYmq;LEVEL-5&dimension=pe:201710;201711;201712
    //analytics/dataValueSet.xml
    //url<-"https://who-dev.baosystems.com/api/dataValueSets.xml?dataSet=qNtxTrp56wV&startDate=2017-01-01&endDate=2017-12-31&orgUnit=S1PsGJvdud6&orgUnit=UJIF3OmraBX&orgUnit=OaX5jg5snSa&orgUnit=fdMXKhBXIT4"

    public static String getCategoryCominationtUrl() {
        return categoryCominationtUrl;
    }

    public static String getDataElementUrl() {
        return dataElementUrl;
    }

    public static String getDatavaluesUrl() {
        return datavaluesUrl;
    }

    public static String getHomePage() {
        return homePage;
    }

    public static String getOrganizationUnitUrl() {
        return organizationUnitUrl;
    }

    public static String getOrganizationUnitGroupUrl() {
        return organizationUnitGroupUrl;
    }

    public static String getOrganizationUnitFieldsUrl() {
        return organizationUnitFieldsUrl;
    }

    public static void setOrganizationUnitFieldsUrl(String organizationUnitFieldsUrl) {
        DHISPropertyManager.organizationUnitFieldsUrl = organizationUnitFieldsUrl;
    }

    public static String getSeperator() {
        return seperator;
    }

    public static String getLinks() {
        return links;
    }

    public static void setLinks(String links) {
        DHISPropertyManager.links = links;
    }

    public static String getPaging() {
        return paging;
    }

    public static void setPaging(String paging) {
        DHISPropertyManager.paging = paging;
    }

    public static String getAdditionalQueryParamSeperator() {
        return additionalQueryParamSeperator;
    }

    public static void setAdditionalQueryParamSeperator(String additionalQueryParamSeperator) {
        DHISPropertyManager.additionalQueryParamSeperator = additionalQueryParamSeperator;
    }

    public static String getFirstQueryParamSeperator() {
        return firstQueryParamSeperator;
    }

    public static void setFirstQueryParamSeperator(String firstQueryParamSeperator) {
        DHISPropertyManager.firstQueryParamSeperator = firstQueryParamSeperator;
    }

    public static String getAnalytics() {
        return analytics;
    }

    public static void setAnalytics(String analytics) {
        DHISPropertyManager.analytics = analytics;
    }

    public static String getDataValueSet() {
        return dataValueSet;
    }

    public static void setDataValueSet(String dataValueSet) {
        DHISPropertyManager.dataValueSet = dataValueSet;
    }

    public static String getDataValueSets() {
        return dataValueSets;
    }

    public static void setDataValueSets(String dataValueSets) {
        DHISPropertyManager.dataValueSets = dataValueSets;
    }

    public static String getDeDimension() {
        return deDimension;
    }

    public static void setDeDimension(String deDimension) {
        DHISPropertyManager.deDimension = deDimension;
    }

    public static String getDimension() {
        return dimension;
    }

    public static void setDimension(String dimension) {
        DHISPropertyManager.dimension = dimension;
    }

    public static String getEndDate() {
        return endDate;
    }

    public static void setEndDate(String endDate) {
        DHISPropertyManager.endDate = endDate;
    }

    public static String getLevel() {
        return level;
    }

    public static void setLevel(String level) {
        DHISPropertyManager.level = level;
    }

    public static String getOrgUnit() {
        return orgUnit;
    }

    public static void setOrgUnit(String orgUnit) {
        DHISPropertyManager.orgUnit = orgUnit;
    }

    public static String getOuDimension() {
        return ouDimension;
    }

    public static void setOuDimension(String ouDimension) {
        DHISPropertyManager.ouDimension = ouDimension;
    }

    public static String getPeDimension() {
        return peDimension;
    }

    public static void setPeDimension(String peDimension) {
        DHISPropertyManager.peDimension = peDimension;
    }

    public static String getStartDate() {
        return startDate;
    }

    public static void setStartDate(String startDate) {
        DHISPropertyManager.startDate = startDate;
    }

    public static String getDataValueAnalyticsQuery() {
        return dataValueAnalyticsQuery;
    }

    public static void setDataValueAnalyticsQuery(String dataValueAnalyticsQuery) {
        DHISPropertyManager.dataValueAnalyticsQuery = dataValueAnalyticsQuery;
    }

    public static String getDataValueDatasetQuery() {
        return dataValueDatasetQuery;
    }

    public static void setDataValueDatasetQuery(String dataValueDatasetQuery) {
        DHISPropertyManager.dataValueDatasetQuery = dataValueDatasetQuery;
    }

    public static String getDataValueFieldsQuery() {
        return dataValueFieldsQuery;
    }

    public static void setDataValueFieldsQuery(String dataValueFieldsQuery) {
        DHISPropertyManager.dataValueFieldsQuery = dataValueFieldsQuery;
    }

    public static String getNigDhis2AnalyticsQuery() {
        return nigDhis2AnalyticsQuery;
    }

    public static void setNigDhis2AnalyticsQuery(String nigDhis2AnalyticsQuery) {
        DHISPropertyManager.nigDhis2AnalyticsQuery = nigDhis2AnalyticsQuery;
    }

    public static String getDatasetUrl() {
        return datasetUrl;
    }

    public static void setDatasetUrl(String datasetUrl) {
        DHISPropertyManager.datasetUrl = datasetUrl;
    }

    public static String getCsvExt() {
        return csvExt;
    }

    public static void setCsvExt(String csvExt) {
        DHISPropertyManager.csvExt = csvExt;
    }

    public static String getJsonExt() {
        return jsonExt;
    }

    public static void setJsonExt(String jsonExt) {
        DHISPropertyManager.jsonExt = jsonExt;
    }

    public static String getXmlExt() {
        return xmlExt;
    }

    public static void setXmlExt(String xmlExt) {
        DHISPropertyManager.xmlExt = xmlExt;
    }

    public static String getOrgUnitGroup() {
        return orgUnitGroup;
    }

    public static String getDataset() {
        return dataset;
    }

    public static void setDataset(String dataset) {
        DHISPropertyManager.dataset = dataset;
    }
    
}
