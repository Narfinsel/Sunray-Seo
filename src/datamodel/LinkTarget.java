package datamodel;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;

import java.util.ArrayList;

public class LinkTarget {

    private int ltCrt;
    private String ltBloggerName;
    private String ltArticleTitle;
    private String ltPageLinkTargetTheirs;
    private String ltDA;
    private String ltPA;
    private EnumLtStatus ltStatus;
    private String ltEmailThread;

// ------------------------------- METHODS --------------------------------------------------

    public LinkTarget (int ltCrt, String ltBloggerName, String ltArticleTitle, String ltPageLinkTargetTheirs, String ltDA, String ltPA, EnumLtStatus ltStatus, String ltEmailThread) {
        this.ltCrt = ltCrt;
        this.ltBloggerName = ltBloggerName;
        this.ltArticleTitle = ltArticleTitle;
        this.ltPageLinkTargetTheirs = ltPageLinkTargetTheirs;
        this.ltDA = ltDA;
        this.ltPA = ltPA;
        this.ltStatus = ltStatus;
        this.ltEmailThread = ltEmailThread;
    }

    public LinkTarget (XSSFRow row, ArrayList<String> arrayOfColsLT) {
        if (row != null) {
            int current_ltNrCrt                     = this.convertCellValueToInt ( row.getCell(this.stringToColNumber( _GLOBAL_constants._COL_LT_Nr_Crt_ , arrayOfColsLT)) );
            String current_ltBloggerName            = row.getCell(this.stringToColNumber( _GLOBAL_constants._COL_LT_Blog_Name_ , arrayOfColsLT)).getStringCellValue();
            String current_ltArticleTitle           = row.getCell(this.stringToColNumber( _GLOBAL_constants._COL_LT_Article_Title , arrayOfColsLT)).getStringCellValue();
            String current_ltPageLinkTargetTheirs   = row.getCell(this.stringToColNumber( _GLOBAL_constants._COL_LT_Page_Target_Theirs_ , arrayOfColsLT)).getStringCellValue();
            String current_ltDA                     = this.da_pa_Correct_String_Format ( row.getCell(this.stringToColNumber( _GLOBAL_constants._COL_LT_DA_ , arrayOfColsLT)).getStringCellValue());
            String current_ltPA                     = this.da_pa_Correct_String_Format (row.getCell(this.stringToColNumber( _GLOBAL_constants._COL_LT_PA_ , arrayOfColsLT)).getStringCellValue());
            EnumLtStatus current_ltStatus           = EnumLtStatus.stringToEnum ( row.getCell(this.stringToColNumber ( _GLOBAL_constants._COL_LT_Status_ , arrayOfColsLT)).getStringCellValue());
            String current_ltEmailThread            = row.getCell(this.stringToColNumber( _GLOBAL_constants._COL_LT_Email_Thread, arrayOfColsLT)).getStringCellValue();

            this.ltCrt = current_ltNrCrt;
            this.ltBloggerName = current_ltBloggerName;
            this.ltArticleTitle = current_ltArticleTitle;
            this.ltPageLinkTargetTheirs = current_ltPageLinkTargetTheirs;
            this.ltDA = current_ltDA;
            this.ltPA = current_ltPA;
            this.ltStatus = current_ltStatus;
            this.ltEmailThread = current_ltEmailThread;
        }
    }

    public void displayLinkTarget ()    {

        System.out.println("LT ----- " + this.ltCrt + " | "+ this.ltBloggerName + " | "+ this.ltArticleTitle + " | " +
                this.ltPageLinkTargetTheirs + " | "+ this.ltDA + " | "+ this.ltPA + " | "+ this.ltStatus + " | " + this.ltEmailThread);
    }

    private int stringToColNumber (String columnName, ArrayList<String> arrayList) {
        int colNum = -1;
        for (int i=0; i < arrayList.size(); i++)
            if ( arrayList.get(i).trim().equalsIgnoreCase( columnName ))
                colNum = i;
        return colNum;
    }

    private int convertCellValueToInt (XSSFCell cell) {
        int intOfCell = 0;
        if ( cell != null ){
            if(cell.getCellTypeEnum() == CellType.BLANK)
                intOfCell = 0;
            else if (cell.getCellTypeEnum() == CellType.NUMERIC)
                intOfCell =  (int) cell.getNumericCellValue();
            else if (cell.getCellTypeEnum() == CellType.STRING)
                intOfCell = Integer.parseInt( cell.getStringCellValue() );
        }
        return intOfCell;
    }

    @Override
    public String toString() {
        String strCombination = this.getLtArticleTitle() + "  >>  " +
                                this.getLtBloggerName() + "  |  " +
                                this.getLtPageLinkTargetTheirs();
        return strCombination;
    }

    // ----------------------------------- SAVING LIST AND BLOG TO FILE ------------------------------------------------

    public static XSSFRow convertLinkTargetToXSSFRow (XSSFRow row, LinkTarget linkTarget)   {

        if ( linkTarget != null)   {
            row.createCell(0).setCellValue(  convertToString( linkTarget.getLtCrt())  );
            row.createCell(1).setCellValue(  linkTarget.ltBloggerName );
            row.createCell(2).setCellValue(  linkTarget.ltArticleTitle );
            row.createCell(3).setCellValue(  linkTarget.ltPageLinkTargetTheirs );
            row.createCell(4).setCellValue(  linkTarget.ltDA );
            row.createCell(5).setCellValue(  linkTarget.ltPA );
            row.createCell(6).setCellValue(  linkTarget.ltStatus.getLtStatus() );
            row.createCell(7).setCellValue(  linkTarget.ltEmailThread );

        }

        return row;
    }

    private static String convertToString (int num)    {

        String str = String.valueOf( num );
        return str;
    }                                               // IS WORKING !!!

    private static String convertToString (double num)    {

        String str = String.valueOf( num );
        return str;
    }                                            // IS WORKING !!!

    private String da_pa_Correct_String_Format (String str)   {

        if ( str != null && str.length() > 2)
            if ( str.charAt(str.length()-2)=='.' &&
                 str.charAt(str.length()-1)=='0' )  {

                 str = str.substring( 0, str.indexOf('.'));
        }
        return str;
    }                                        // IS WORKING !!!

// ----------------------------------------- GETTERS and SETTERS -------------------------------------------------------

    public int getLtCrt () {
        return ltCrt;
    }

    public String getLtBloggerName () {
        return ltBloggerName;
    }

    public String getLtArticleTitle() {
        return ltArticleTitle;
    }

    public String getLtPageLinkTargetTheirs () {
        return ltPageLinkTargetTheirs;
    }

    public String getLtDA () {
        return ltDA;
    }

    public String getLtPA () {
        return ltPA;
    }

    public EnumLtStatus getLtStatus () {
        return ltStatus;
    }

    public String getLtEmailThread () {
        return ltEmailThread;
    }



    public void setLtCrt (int ltCrt) {
        this.ltCrt = ltCrt;
    }

    public void setLtBloggerName (String ltBloggerName) {
        this.ltBloggerName = ltBloggerName;
    }

    public void setLtArticleTitle(String ltArticleTitle) {
        this.ltArticleTitle = ltArticleTitle;
    }

    public void setLtPageLinkTargetTheirs (String ltPageLinkTargetTheirs) {
        this.ltPageLinkTargetTheirs = ltPageLinkTargetTheirs;
    }

    public void setLtDA (String ltDA) {
        this.ltDA = ltDA;
    }

    public void setLtPA (String ltPA) {
        this.ltPA = ltPA;
    }

    public void setLtStatus (EnumLtStatus ltStatus) {
        this.ltStatus = ltStatus;
    }

    public void setLtEmailThread (String ltEmailThread) {
        this.ltEmailThread = ltEmailThread;
    }

}
