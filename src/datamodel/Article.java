package datamodel;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import java.util.ArrayList;

public class Article {

    private int artNbrCrt;
    private String artArticleTitle;
    private String artPageLinkForArticle;

// --------------------------------- CONSTRUCTORS -----------------------------------------


    public Article (int artNbrCrt, String artArticleTitle, String artPageLinkForArticle) {
        this.artNbrCrt              = artNbrCrt;
        this.artArticleTitle        = artArticleTitle;
        this.artPageLinkForArticle  = artPageLinkForArticle;
    }             // IS WORKING

    public Article (XSSFRow row, ArrayList<String> arrayOfColsArticles) {

        if (row != null) {
            int current_artNbrCrt               = this.convertCellValueToInt( row.getCell (this.stringToColNumber( _GLOBAL_constants._COL_ART_Nr_Crt_ , arrayOfColsArticles ) ) );
            String current_artArticleTitle      = row.getCell(this.stringToColNumber( _GLOBAL_constants._COL_ART_Art_Tit_ , arrayOfColsArticles)).getStringCellValue();
            String current_blogWebSiteURL       = row.getCell(this.stringToColNumber( _GLOBAL_constants._COL_ART_Link_ , arrayOfColsArticles)).getStringCellValue();

            this.artNbrCrt              = current_artNbrCrt;
            this.artArticleTitle        = current_artArticleTitle;
            this.artPageLinkForArticle  = current_blogWebSiteURL;
        }
    }                              // IS WORKING


// ------------------------------ UTILITARY METHODS ---------------------------------------
    private int stringToColNumber   (String columnName, ArrayList<String> arrayList) {
        int colNum = -1;
        for (int i=0; i < arrayList.size(); i++)
            if ( arrayList.get(i).trim().equalsIgnoreCase( columnName ))
                colNum = i;
        return colNum;
    }                 // IS WORKING

    private static String convertToString (int num)    {

        String str = String.valueOf( num );
        return str;
    }

    private String da_pa_Correct_String_Format (String str)   {

        if ( str != null && str.length() > 2)
            if ( str.charAt(str.length()-2)=='.' &&
                    str.charAt(str.length()-1)=='0' )  {

                str = str.substring( 0, str.indexOf('.'));
            }
        return str;
    }                                        // IS WORKING !!!

    private int convertCellValueToInt (XSSFCell cell) {
        int intOfCell = 0;
        if ( cell != null ){
            if(cell.getCellTypeEnum() == CellType.BLANK)
                intOfCell = 0;
            else if (cell.getCellTypeEnum() == CellType.NUMERIC)
                intOfCell = (int) cell.getNumericCellValue();
            else if (cell.getCellTypeEnum() == CellType.STRING) {
                try {
                    String str = cell.getStringCellValue();
                    double d = Double.parseDouble( str );
                    intOfCell = (int) d;

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        return intOfCell;
    }                                                // IS WORKING

    public static XSSFRow convertArticleToXSSFRow (XSSFRow row, Article article)   {

        if ( article != null)   {
            row.createCell(0).setCellValue( convertToString( article.getArtNbrCrt() ));
            row.createCell(1).setCellValue( article.getArtArticleTitle() );
            row.createCell(2).setCellValue( article.getArtPageLinkForArticle() );
        }

        return row;
    }                   // IS WORKING

    @Override
    public String toString() {
        return this.artArticleTitle;
    }                                              // IS WORKING

    public void displayArticleData () {

        System.out.println(this.artNbrCrt +"  -  "+ this.artArticleTitle + " --- " + this.artPageLinkForArticle);
    }                                                                // IS WORKING

    // ----------------------------- SETTERS and GETTERS --------------------------------------


    public int getArtNbrCrt() {
        return artNbrCrt;
    }                                                        // IS WORKING

    public String getArtArticleTitle() {
        return artArticleTitle;
    }                                                               // IS WORKING

    public String getArtPageLinkForArticle() {
        return artPageLinkForArticle;
    }                             // IS WORKING



    public void setArtNbrCrt(int artNbrCrt) {
        this.artNbrCrt = artNbrCrt;
    }                                                          // IS WORKING

    public void setArtArticleTitle(String artArticleTitle) {
        this.artArticleTitle = artArticleTitle;
    }                                           // IS WORKING

    public void setArtPageLinkForArticle(String artPageLinkForArticle) {
        this.artPageLinkForArticle = artPageLinkForArticle;
    }                               // IS WORKING


}
