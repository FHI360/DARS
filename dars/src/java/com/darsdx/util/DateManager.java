/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darsdx.util;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author smomoh
 */
public class DateManager implements Serializable
{
    public static String DB_DATE_FORMAT="yyyy-MM-dd";
    public static String DD_MM_YYYY_SLASH="dd/MM/yyyy";
    public static String MM_DD_YYYY_SLASH="MM/dd/yyyy";
    public static String TIMEFORMAT="HH:mm:ss";
    
    public static String getDefaultStartDateForReports()
    {
        String startDate="07/01/2018";
        return startDate;
    }
    public static String getDefaultEndDateForReports()
    {
        String endDate=DateManager.getMthDayYearFromMySqlDate(DateManager.getCurrentDate());
        try
        {
            if(DateManager.compareDates(endDate, getDefaultStartDateForReports()))
            endDate=getDefaultStartDateForReports();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return endDate;
    }
    public static boolean isValidDate(String mysqlDate)
    {
        AppUtility appUtil=new AppUtility();
        boolean validDate=false;
        if(mysqlDate !=null && mysqlDate.indexOf("-") !=-1)
        {
            String[] dateArray=mysqlDate.split("-");
            if(dateArray.length==3)
            {
                if(dateArray[0].length()==4 && appUtil.isNumberGreaterThanZero(dateArray[0]))
                {
                    if(dateArray[1].length()<3 && dateArray[2].length()<3)
                    {
                        if(appUtil.isNumberGreaterThanZero(dateArray[1]) && appUtil.isNumberGreaterThanZero(dateArray[2]))
                        {
                            int day=Integer.parseInt(dateArray[2]);
                            int mth=Integer.parseInt(dateArray[1]);
                            if(mth<13)
                            {
                                if(mth==2 && day<30)
                                {
                                    validDate=true;
                                }
                                else if(mth==4 || mth==6 || mth==9 || mth==11)
                                {
                                    if(day <31)
                                    validDate=true;
                                }
                                else if(mth==1 || mth==3 || mth==5 || mth==7 || mth==8 || mth==10 || mth==12)
                                {
                                    if(day <32)
                                    validDate=true;
                                }
                            }
                        }
                    }
                }
            }
            
        }
        return validDate;
    }
    public static String convertDateToString(Date date,String format)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
    public static Date getDateInstance(String datestr)
    {
        Date date=new Date();
        try
        {
            if(isValidDate(datestr))
            date=new SimpleDateFormat("yyyy-MM-dd").parse((datestr));
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return date;
    }
    public static Date getCurrentDateAsDateObject()
    {
        String dateFormat="yyyy-MM-dd";
        SimpleDateFormat sdf=new SimpleDateFormat(dateFormat);
        String currentDate=sdf.format(new java.util.Date());
        return getDateInstance(currentDate);
    }
    public static String getCurrentDate()
    {
        String dateFormat="yyyy-MM-dd";
        SimpleDateFormat sdf=new SimpleDateFormat(dateFormat);
        String currentDate=sdf.format(new java.util.Date());
        return currentDate;
    }
    public static String getCurrentDateAndTime(String format)
    {
        SimpleDateFormat sdf=new SimpleDateFormat(format);
        String currentDate=sdf.format(new java.util.Date());
        return currentDate;
    }
    public String getPreviousDate(int year,int mth,int day,int diff)
    {
        String dateFormat="yyyy/MM/dd  hh:mm:ss";
      SimpleDateFormat sdf=new SimpleDateFormat(dateFormat);
	Calendar c=new GregorianCalendar(year,mth,day);
	c.add(Calendar.MONTH,diff);
        String previousMonth=sdf.format(c.getTime());
        return previousMonth;
    }
    public String getCurrentMthAndDate()
    {
        String currentDate=getCurrentDate();
        String[] dateArray=currentDate.split("-");
        String month=getFullMonthAsString(Integer.parseInt(dateArray[1]));
        String today=month+" "+dateArray[2]+", "+dateArray[0];
        return today;
    }
    public static String getMthDayYearFromMySqlDate(String mysqlDate)
    {
        String displayDate=null;
        if(mysqlDate !=null && mysqlDate.indexOf("-") !=-1)
        {
            String[] dateArray=mysqlDate.split("-");
            displayDate=dateArray[1]+"/"+dateArray[2]+"/"+dateArray[0];
        }
        return displayDate;
    }
    public String monthDayYear(String mysqlDate)
    {
        String displayDate=null;
        if(mysqlDate !=null && mysqlDate.indexOf("-") !=-1)
        {
            String[] dateArray=mysqlDate.split("-");
            displayDate=dateArray[1]+"/"+dateArray[2]+"/"+dateArray[0];
        }
        return displayDate;
    }
    public String convertMysqlDateTomonthDayYear(String mysqlDate)
    {
        String displayDate=null;
        if(mysqlDate !=null && mysqlDate.indexOf("-") !=-1)
        {
            String[] dateArray=mysqlDate.split("-");
            displayDate=dateArray[1]+"/"+dateArray[2]+"/"+dateArray[0];
        }
        return displayDate;
    }
    public static String processMthDayYearToMysqlFormat(String date)
    {
        String[] dateArray=null;
        String mysqlDate=date;
        if(date !=null && !date.equalsIgnoreCase("All"))
        {
            if(date.indexOf("/") !=-1)
            {
                dateArray=date.split("/");
                if(dateArray !=null && dateArray.length>2)
                mysqlDate=getMySqlDate(dateArray[1],dateArray[0],dateArray[2]);
            }
        }
        return mysqlDate;
    }
    public long monthsBetween(Calendar startDate, Calendar endDate) {
        Calendar date = (Calendar) startDate.clone();
        long monthsBetween = 0;
        while (date.before(endDate)) {
            date.add(Calendar.MONTH, 1);
            monthsBetween++;
        }
        return monthsBetween;
    }
    public static String getMySqlDate(String day,String month,String year)
    {
        String mysqlDate=year+"-"+month+"-"+day;
        return mysqlDate;
    }
    public String getServiceDate(String mysqlDate)
    {
        String[] dateParts=mysqlDate.split("-");
        String month=getMonthAsString(Integer.parseInt(dateParts[1]));
        String displayDate=month+" "+dateParts[0];
        return displayDate;
    }
    public String getDayMthYearFromMysqlDate(String mysqlDate)
    {
        String displayDate=null;
        if(mysqlDate !=null && mysqlDate.indexOf("-") !=-1)
        {
            String[] dateParts=mysqlDate.split("-");
            displayDate=dateParts[2]+"/"+dateParts[1]+"/"+dateParts[0];
        }
        return displayDate;
    }
    public String getFullMonthAsString(int mth)
    {
        String month=null;
        if(mth==1 || mth==01)
        month="January";
        else if(mth==2 || mth==02)
        month="February";
        else if(mth==3 || mth==03)
        month="March";
        else if(mth==4 || mth==04)
        month="April";
        else if(mth==5 || mth==05)
        month="May";
        else if(mth==6 || mth==06)
        month="June";
        else if(mth==7 || mth==07)
        month="July";
        else if(mth==8)
        month="August";
        else if(mth==9)
        month="September";
        else if(mth==10)
        month="October";
        else if(mth==11)
        month="November";
        else if(mth==12)
        month="December";
        return month;
    }
    public String getMonthAsString(int mth)
    {
        String month=null;
        if(mth==1 || mth==01)
        month="Jan";
        else if(mth==2 || mth==02)
        month="Feb";
        else if(mth==3 || mth==03)
        month="Mar";
        else if(mth==4 || mth==04)
        month="Apr";
        else if(mth==5 || mth==05)
        month="May";
        else if(mth==6 || mth==06)
        month="Jun";
        else if(mth==7 || mth==07)
        month="Jul";
        else if(mth==8)
        month="Aug";
        else if(mth==9)
        month="Sep";
        else if(mth==10)
        month="Oct";
        else if(mth==11)
        month="Nov";
        else if(mth==12)
        month="Dec";
        return month;
    }
    public String getMonthAsNumber(String mth)
    {
        String month=null;
        if(mth !=null)
        {
            if(mth.equalsIgnoreCase("Jan") || mth.equalsIgnoreCase("January"))
            month="01";
            else if(mth.equalsIgnoreCase("Feb") || mth.equalsIgnoreCase("February"))
            month="02";
            else if(mth.equalsIgnoreCase("Mar") || mth.equalsIgnoreCase("March"))
            month="03";
            else if(mth.equalsIgnoreCase("Apr") || mth.equalsIgnoreCase("April"))
            month="04";
            else if(mth.equalsIgnoreCase("May"))
            month="05";
            else if(mth.equalsIgnoreCase("Jun") || mth.equalsIgnoreCase("June"))
            month="06";
            else if(mth.equalsIgnoreCase("Jul") || mth.equalsIgnoreCase("July"))
            month="07";
            else if(mth.equalsIgnoreCase("Aug") || mth.equalsIgnoreCase("August"))
            month="08";
            else if(mth.equalsIgnoreCase("Sep") || mth.equalsIgnoreCase("September"))
            month="09";
            else if(mth.equalsIgnoreCase("Oct") || mth.equalsIgnoreCase("October"))
            month="10";
            else if(mth.equalsIgnoreCase("Nov") || mth.equalsIgnoreCase("November"))
            month="11";
            else if(mth.equalsIgnoreCase("Dec") || mth.equalsIgnoreCase("December"))
            month="12";
        }
        return month;
    }
    public List generatDate(int startYear)
    {
        List listOfDates=new ArrayList();
        for(int i=startYear; i<=2020; i++)
        {
            listOfDates.add(i);
        }
        return listOfDates;
    }
    public List generateMonths(int startMonth)
    {
        List listOfMonths=new ArrayList();
        String[] months={"January","February","March","April","May","June","July","August","September","October","November","December"};
        for(int i=startMonth-1; i<months.length; i++)
        {
            listOfMonths.add(months[i]);
        }
        return listOfMonths;
    }
    public static List generateMonths()
    {
        Month month=null;
        String[] monthArray={"01","Jan","January","02","Feb","February","03","Mar","March","04","Apr","April","05","May","May","06","Jun","June","07","Jul","July","08","Aug","August","09","Sep","September","10","Oct","October","11","Nov","November","12","Dec","December"};
        List monthList=new ArrayList();
        for(int i=0; i<monthArray.length; i+=3)
        {
            month=new Month();
            month.setValue(monthArray[i]);
            month.setShortName(monthArray[i+1]);
            month.setName(monthArray[i+2]);
            monthList.add(month);
        }
        return monthList;
    }
    public List generateYears(int firstYr,int lastYr)
    {
         List yearList=new ArrayList();
         for(int i=firstYr; i<lastYr+1; i++)
         {
             yearList.add(i);
         }
         return yearList;
    }
public static boolean compareDates(String firstDate, String secondDate)
{
    String[] firstDateArray;
    String[] secondDateArray;
    boolean dateFlag=false;
    if(firstDate !=null && secondDate !=null)
    {
        firstDateArray=firstDate.split("-");
        secondDateArray=secondDate.split("-");
        if(firstDateArray.length !=3 || secondDateArray.length !=3)
        return false;
        if(firstDate.equalsIgnoreCase(secondDate))
        dateFlag=true;
        else
        {
            int year1=Integer.parseInt(firstDateArray[0]);
            int mth1=Integer.parseInt(firstDateArray[1]);
            int day1=Integer.parseInt(firstDateArray[2]);

            int year2=Integer.parseInt(secondDateArray[0]);
            int mth2=Integer.parseInt(secondDateArray[1]);
            int day2=Integer.parseInt(secondDateArray[2]);
            dateFlag=compareDate(year1, mth1, day1,year2, mth2, day2);
        }
    }
    return dateFlag;
}
    public static boolean compareDate(int year1,int mth1,int day1,int year2,int mth2,int day2)
    {
        Calendar c1=new GregorianCalendar(year1,mth1,day1);
        Calendar c2=new GregorianCalendar(year2,mth2,day2);
        if(c1.before(c2))
        return true;
        return false;
    }
    public String getLastDayOfMonth(String mth)
    {
        String lastDay="31";
        if(mth.equalsIgnoreCase("02"))
        lastDay="28";
        else if(mth.equalsIgnoreCase("04") || mth.equalsIgnoreCase("06") || mth.equalsIgnoreCase("09") || mth.equalsIgnoreCase("11"))
        lastDay="30";
        return lastDay;
    }
    public int getLastDayOfMonth(int mth)
    {
        int lastDay=31;
        if(mth==2)
        lastDay=28;
        else if(mth==4|| mth==6 || mth==9 || mth==11)
        lastDay=30;
        return lastDay;
    }
    public List getDateLabels(String[] dateParams)
    {
        List dateList=new ArrayList();
        Calendar cal=Calendar.getInstance();
        for(int i=0; i<dateParams.length; i++)
        {
           String[] dateArray=dateParams[i].split("-");
           cal.set(Integer.parseInt(dateArray[0]), Integer.parseInt(dateArray[1])-1, Integer.parseInt(dateArray[2]));
           dateList.add(cal.getDisplayName(cal.MONTH, cal.SHORT, Locale.ENGLISH)+" "+dateArray[0]);
        }
        return dateList;
    }
    public String getStartDate(int startMth,int startYear)
    {
        String startDate=startYear+"-"+startMth+"-"+"01";
        return startDate;
    }
    public String getEndDate(int endMth,int endYear)
    {
        Calendar cal=Calendar.getInstance();
        cal.set(endYear, endMth-1,1);
        int lastDayOfMth=cal.getActualMaximum(Calendar.DATE);
        String endDate=endYear+"-"+endMth+"-"+lastDayOfMth;
        return endDate;
    }
    public String getStartDate(String[] params)
    {
        String startDate=null;
        if(params !=null && params.length >1)
        {
            if(!validateDateParamenters(params))
            return null;
            startDate=params[1]+"-"+params[0]+"-"+"01";
        }
        return startDate;
    }
    public String getEndDate(String[] params)
    {
        String endDate=null;
        if(params !=null && params.length >3)
        {
            if(!validateDateParamenters(params))
            return null;
            Calendar cal=Calendar.getInstance();
            cal.set(Integer.parseInt(params[3]), Integer.parseInt(params[2])-1,1);
            int lastDayOfMth=cal.getActualMaximum(Calendar.DATE);
            endDate=params[3]+"-"+params[2]+"-"+lastDayOfMth;
        }
        return endDate;
    }
    public boolean validateDateParamenters(String[] params)
    {
        boolean gooddateParams=true;
        for(int i=0; i<4; i++)
        {
            if(params[i]==null || params[i].equalsIgnoreCase("All"))
            gooddateParams=false;
        }
        return gooddateParams;
    }
    public List generatetMonthlyDates(int year)
    {
        List list=new ArrayList();
        for(int i=1; i<13; i++)
        {
            if(i==1)
            list.add("Jan-"+year);
            else if(i==2)
            list.add("Feb-"+year);
            if(i==3)
            list.add("Mar-"+year);
            else if(i==4)
            list.add("Apr-"+year);
            if(i==5)
            list.add("May-"+year);
            else if(i==6)
            list.add("Jun-"+year);
            if(i==7)
            list.add("Jul-"+year);
            else if(i==8)
            list.add("Aug-"+year);
            if(i==9)
            list.add("Sep-"+year);
            else if(i==10)
            list.add("Oct-"+year);
            if(i==11)
            list.add("Nov-"+year);
            else if(i==2)
            list.add("Dec-"+year);
        }
        return list;
    }
    public static List generateMonthAndYear(int startYear,int endYear)
    {
        List monthAndYearList=new ArrayList();     
        for(int i=startYear; i<(endYear+1); i++)
        {
            monthAndYearList.addAll(generateMonthAndYear(i));
        }
        return monthAndYearList;
    }
    public static List generateMonthAndYear(int year)
    {
        List monthAndYearList=new ArrayList();
        List monthList=generateMonths();
        Month month=null;
        
        for(int i=0; i<monthList.size(); i++)
        {
            month=(Month)monthList.get(i);
            month.setYear(year);
            monthAndYearList.add(month);
        }
        return monthAndYearList;
    }
    public List generateQuarterlyDates(int year)
    {
        List list=new ArrayList();
        for(int i=1; i<13; i+=3)
        {
            Month mth=new Month();
            String shortName=null;
            String value=null;
            if(i==1)
            {
                shortName="Jan-Mar";
                value="01";
            }
            else if(i==4)
            {
                shortName="Apr-Jun";
                value="04";
            }
            else if(i==7)
            {
                shortName="Jul-Sep";
                value="07";
            }
            else if(i==10)
            {
                shortName="Oct-Dec";
                value="10";
            }
            mth.setShortName(shortName);
            mth.setValue(value);
            mth.setYear(year);
            list.add(mth);
            
        }
        return list;
    }
    public List generateSemiAnnualDates(int year)
    {
        List list=new ArrayList();
        for(int i=1; i<13; i+=6)
        {
            Month mth=new Month();
            String shortName=null;
            String value=null;
            if(i==1)
            {
                shortName="Jan-Jun";
                value="16";
            }
            else if(i==7)
            {
                shortName="Jul-Dec ";
                value="712";
            }
            
            mth.setShortName(shortName);
            mth.setValue(value);
            mth.setYear(year);
            list.add(mth);
            
        }
        return list;
    }
    public List generateAnnualDates(int startyear, int endYear)
    {
        List list=new ArrayList();
        for(int i=startyear; i<endYear+1; i++)
        {
            list.add(i);
        }
        return list;
    }
    public List generateDailyDates(int startyear, int endYear)
    {
        List list=new ArrayList();
        String day="";
        String mth="";
        for(int i=endYear; i>startyear-1; i--)
        {
            for(int j=1; j<13; j++)
            {
               int noOfDays=getLastDayOfMonth(j);
               for(int k=1; k<noOfDays+1; k++)
               {
                   if(j<10)
                   mth="0"+j;
                   else
                   mth=""+j;
                   if(k<10)
                   day="0"+k;
                   else
                   day=""+k;
                   list.add(i+mth+day); 
                   
               }
            }
            
        }
        return list;
    }
    public List generateWeeklyDates(int startyear, int endYear)
    {
        List list=new ArrayList();
        for(int i=endYear; i>startyear-1; i--)
        {
            for(int j=1; j<53; j++)
            {
               if(j<10)
               list.add(i+"W0"+j); 
               else
               list.add(i+"W"+j); 
            }
            
        }
        return list;
    }
    public List generateMonthlyDates(int startyear, int endYear)
    {
        List list=new ArrayList();
        for(int i=startyear; i<endYear+1; i++)
        {
            for(int j=1; j<13; j++)
            {
               if(j<10)
               list.add(i+"0"+j);
               else
               list.add(i+""+j);
            }
            
        }
        return list;
    }
}
