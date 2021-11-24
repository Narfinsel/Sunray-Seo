package datamodel;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;
import java.util.LinkedList;

public class BloggerData {
// ---------------------------------------------------------------------------------------------------------------------
    private int blogNbrCrt;
    private String blogBlogName;
    private String blogWebSiteURL;
    private String blogSiteDescription;
    private String blogSiteValuesInfo;
    private String blogTagcrowd;
    private String blogOwner;
    private String blogEmailContact;
    private String blogWhenToSend;
    private String blogStrategiesIntel;
    private String blogCommentsOnSite;

// ---------------------------------------------------------------------------------------------------------------------


    public BloggerData(int blogNbrCrt, String blogBlogName, String blogWebSiteURL,
                       String blogSiteDescription, String blogSiteValuesInfo,
                       String blogTagcrowd, String blogOwner,
                       String blogEmailContact, String blogWhenToSend,
                       String blogStrategiesIntel, String blogCommentsOnSite) {

        this.blogNbrCrt = blogNbrCrt;
        this.blogBlogName = blogBlogName;
        this.blogWebSiteURL = blogWebSiteURL;
        this.blogSiteDescription = blogSiteDescription;
        this.blogSiteValuesInfo = blogSiteValuesInfo;
        this.blogTagcrowd = blogTagcrowd;
        this.blogOwner = blogOwner;
        this.blogEmailContact = blogEmailContact;
        this.blogWhenToSend = blogWhenToSend;
        this.blogStrategiesIntel = blogStrategiesIntel;
        this.blogCommentsOnSite = blogCommentsOnSite;

    }                        // IS WORKING

    public BloggerData (XSSFRow row, ArrayList<String> arrayOfColsBlog) {

        if (row != null) {
            int current_blogBlogNrCrt           = this.convertCellValueToInt( row.getCell(this.stringToColNumber( _GLOBAL_constants._COL_BLOG_Nr_Crt_ , arrayOfColsBlog)) );
            String current_blogBlogName         = row.getCell(this.stringToColNumber( _GLOBAL_constants._COL_BLOG_Blog_Name_ , arrayOfColsBlog)).getStringCellValue();
            String current_blogWebSiteURL       = row.getCell(this.stringToColNumber( _GLOBAL_constants._COL_BLOG_Website_URL_ , arrayOfColsBlog)).getStringCellValue();
            String current_blogSiteDescription  = row.getCell(this.stringToColNumber( _GLOBAL_constants._COL_BLOG_Site_Description_ , arrayOfColsBlog)).getStringCellValue();
            String current_blogSiteValuesInfo   = row.getCell(this.stringToColNumber( _GLOBAL_constants._COL_BLOG_Values_Information_ , arrayOfColsBlog)).getStringCellValue();
            String current_blogTagcrowd         = row.getCell(this.stringToColNumber( _GLOBAL_constants._COL_BLOG_Tagcrowd_ , arrayOfColsBlog)).getStringCellValue();
            String current_blogOwner            = row.getCell(this.stringToColNumber( _GLOBAL_constants._COL_BLOG_Owner_ , arrayOfColsBlog)).getStringCellValue();
            String current_blogEmailContact     = row.getCell(this.stringToColNumber( _GLOBAL_constants._COL_BLOG_Email_Contact_ , arrayOfColsBlog)).getStringCellValue();
            String current_blogWhenToSend       = row.getCell(this.stringToColNumber( _GLOBAL_constants._COL_BLOG_When_to_Send_ , arrayOfColsBlog)).getStringCellValue();
            String current_blogStrategiesIntel  = row.getCell(this.stringToColNumber( _GLOBAL_constants._COL_BLOG_Strategies_and_Intel , arrayOfColsBlog)).getStringCellValue();
            String current_blogCommentsOnSite   = row.getCell(this.stringToColNumber( _GLOBAL_constants._COL_BLOG_Comments_on_Site , arrayOfColsBlog)).getStringCellValue();

            this.blogNbrCrt = current_blogBlogNrCrt;
            this.blogBlogName = current_blogBlogName;
            this.blogWebSiteURL = current_blogWebSiteURL;
            this.blogSiteDescription = current_blogSiteDescription;
            this.blogSiteValuesInfo = current_blogSiteValuesInfo;
            this.blogTagcrowd = current_blogTagcrowd;
            this.blogOwner = current_blogOwner;
            this.blogEmailContact = current_blogEmailContact;
            this.blogWhenToSend = current_blogWhenToSend;
            this.blogStrategiesIntel = current_blogStrategiesIntel;
            this.blogCommentsOnSite = current_blogCommentsOnSite;
        }
    }                              // IS WORKING

    private int convertCellValueToInt (XSSFCell cell) {
        int intOfCell = 0;
        if ( cell != null ){
            if(cell.getCellTypeEnum() == CellType.BLANK)
                intOfCell = 0;
            else if (cell.getCellTypeEnum() == CellType.NUMERIC)
                intOfCell =  (int) cell.getNumericCellValue();
            else if (cell.getCellTypeEnum() == CellType.STRING)
                //intOfCell = Integer.parseInt( cell.getStringCellValue() );
                try {
                    String str = cell.getStringCellValue();
                    double d = Double.parseDouble( str );
                    intOfCell = (int) d;

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
        }
        return intOfCell;
    }                                                // IS WORKING

    private int stringToColNumber   (String columnName, ArrayList<String> arrayList) {
        int colNum = -1;
        for (int i=0; i < arrayList.size(); i++)
            if ( arrayList.get(i).trim().equalsIgnoreCase( columnName ))
                colNum = i;
        return colNum;
    }                 // IS WORKING

    @Override
    public String toString() {
        return this.getBlogBlogName();
    }                                            // IS WORKING

    // ----------------------------------- SAVING LIST AND BLOG TO FILE ------------------------------------------------

    public static XSSFRow convertBlogToXSSFRow (XSSFRow row, BloggerData bloggerData)   {

        if ( bloggerData != null)   {
            row.createCell(0).setCellValue( convertToString( bloggerData.getBlogNbrCrt() ));
            row.createCell(1).setCellValue(  bloggerData.getBlogBlogName() );
            row.createCell(2).setCellValue(  bloggerData.getBlogWebSiteURL() );
            row.createCell(3).setCellValue(  bloggerData.getBlogSiteDescription() );
            row.createCell(4).setCellValue(  bloggerData.getBlogSiteValuesInfo() );
            row.createCell(5).setCellValue(  bloggerData.getBlogTagcrowd() );
            row.createCell(6).setCellValue(  bloggerData.getBlogOwner() );
            row.createCell(7).setCellValue(  bloggerData.getBlogEmailContact() );
            row.createCell(8).setCellValue(  bloggerData.getBlogWhenToSend() );
            row.createCell(9).setCellValue(  bloggerData.getBlogStrategiesIntel() );
            row.createCell(10).setCellValue(  bloggerData.getBlogCommentsOnSite() );
        }

        return row;
    }

    private static String convertToString (int num)    {

        String str = String.valueOf( num );
        return str;
    }


    // --------------------------------------- DISPLAY and FORMATTING --------------------------------------------------

    public void displayBloggerData () {
        System.out.println("<< " + blogNbrCrt +" >>  " + blogBlogName + " |" + " |" + blogWebSiteURL + " |" + blogSiteDescription + " |" + blogSiteValuesInfo + " |" + blogTagcrowd + " |" +
                blogOwner + " |" + blogEmailContact + " |" +    blogWhenToSend + " |" + blogStrategiesIntel + " |" + blogCommentsOnSite);
    }                                                                // IS WORKING

    // ----------------------------------------- GETTERS and SETTERS ---------------------------------------------------

    public int getBlogNbrCrt() {
        return blogNbrCrt;
    }

    public String getBlogBlogName() {
        return blogBlogName;
    }

    public String getBlogWebSiteURL() {
        return blogWebSiteURL;
    }

    public String getBlogSiteDescription() {
        return blogSiteDescription;
    }

    public String getBlogSiteValuesInfo() {
        return blogSiteValuesInfo;
    }

    public String getBlogTagcrowd() {
        return blogTagcrowd;
    }

    public String getBlogOwner() {
        return blogOwner;
    }

    public String getBlogEmailContact() {
        return blogEmailContact;
    }

    public String getBlogWhenToSend() {
        return blogWhenToSend;
    }

    public String getBlogStrategiesIntel() {
        return blogStrategiesIntel;
    }

    public String getBlogCommentsOnSite() {
        return blogCommentsOnSite;
    }



    public void setBlogNbrCrt(int blogNbrCrt) {
        this.blogNbrCrt = blogNbrCrt;
    }

    public void setBlogBlogName(String blogBlogName) {
        this.blogBlogName = blogBlogName;
    }

    public void setBlogWebSiteURL(String blogWebSiteURL) {
        this.blogWebSiteURL = blogWebSiteURL;
    }

    public void setBlogSiteDescription(String blogSiteDescription) {
        this.blogSiteDescription = blogSiteDescription;
    }

    public void setBlogSiteValuesInfo(String blogSiteValuesInfo) {
        this.blogSiteValuesInfo = blogSiteValuesInfo;
    }

    public void setBlogTagcrowd(String blogTagcrowd) {
        this.blogTagcrowd = blogTagcrowd;
    }

    public void setBlogOwner(String blogOwner) {
        this.blogOwner = blogOwner;
    }

    public void setBlogEmailContact(String blogEmailContact) {
        this.blogEmailContact = blogEmailContact;
    }

    public void setBlogWhenToSend(String blogWhenToSend) {
        this.blogWhenToSend = blogWhenToSend;
    }

    public void setBlogStrategiesIntel(String blogStrategiesIntel) {
        this.blogStrategiesIntel = blogStrategiesIntel;
    }

    public void setBlogCommentsOnSite(String blogCommentsOnSite) {
        this.blogCommentsOnSite = blogCommentsOnSite;
    }

}

