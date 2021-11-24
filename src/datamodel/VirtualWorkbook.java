package datamodel;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class VirtualWorkbook {

    private String fileLocation;

    private XSSFWorkbook workbookRead;
    private XSSFWorkbook workbookWrite;

    private ArrayList<String> columnsInBlogData;
    private ArrayList<String> columnsInLinkTargets;
    private ArrayList<String> columnsInArticles;

    public final String _SHEET_BLOGGER_ = "BloggerData";
    public final String _SHEET_LINK_TARGET_ = "LinkTargets";
    public final String _SHEET_ARTICLES_ = "Articles";


    public VirtualWorkbook (String fileLocation)   {
        this.fileLocation = fileLocation;
        this.workbookRead = this.getWorkbookFromFile(fileLocation);
    }                                               // IS WORKING

    private XSSFWorkbook getWorkbookFromFile (String fileLocation){
        try {
            File fileExcell = new File(fileLocation);
            if (fileExcell.exists()) {
                FileInputStream fileStream = new FileInputStream(fileExcell);
                XSSFWorkbook storedWB = new XSSFWorkbook(fileStream);
                XSSFWorkbook createdWB = new XSSFWorkbook();

                int nbr_Sheets = storedWB.getNumberOfSheets();
                String storedSheetName;


                for (int i= 0; i< nbr_Sheets; i++) {
                    XSSFSheet storedSheet = storedWB.getSheetAt(i);
                    storedSheetName = storedSheet.getSheetName();

                    XSSFSheet createdSheet = createdWB.createSheet( storedSheetName );

                    if (storedSheet.getSheetName().equalsIgnoreCase(_SHEET_BLOGGER_) ) {

                        this.setColumnsInBlogData( this.arrayFromFirstRowOfColumns( storedSheet.getRow(0) ) );

                        for ( int j=0; j< this.numberOfRowsIntSheet(storedSheet); j++)    {

                            XSSFRow storedRow = storedSheet.getRow(j+1);
                            XSSFRow createdRow = createdSheet.createRow(j);

                            for (int k = 0; k< this.numberOfColumnsViableVariables(storedSheet); k++) {

                                XSSFCell storedCell = storedRow.getCell( k, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);

                                String currentCellValue = this.convertCellValueToString( storedCell );
                                XSSFCell createdCell = createdRow.createCell(k);
                                createdCell.setCellValue(currentCellValue);
                            }
                        }
                    }

                    if (storedSheet.getSheetName().equalsIgnoreCase(_SHEET_LINK_TARGET_) ) {

                        this.setColumnsInLinkTargets( this.arrayFromFirstRowOfColumns( storedSheet.getRow(0) ) );

                        for ( int j=0; j< this.numberOfRowsIntSheet(storedSheet); j++)    {

                            XSSFRow storedRow = storedSheet.getRow(j+1);
                            XSSFRow createdRow = createdSheet.createRow(j);

                            for (int k = 0; k< this.numberOfColumnsViableVariables(storedSheet); k++) {

                                XSSFCell storedCell = storedRow.getCell( k, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);

                                String currentCellValue = this.convertCellValueToString( storedCell );
                                XSSFCell createdCell = createdRow.createCell(k);
                                createdCell.setCellValue(currentCellValue);
                            }
                        }
                    }
                    if (storedSheet.getSheetName().equalsIgnoreCase(_SHEET_ARTICLES_) ) {

                        this.setColumnsInArticles( this.arrayFromFirstRowOfColumns( storedSheet.getRow(0) ) );

                        for ( int j=0; j< this.numberOfRowsIntSheet(storedSheet); j++)    {

                            XSSFRow storedRow = storedSheet.getRow(j+1);
                            XSSFRow createdRow = createdSheet.createRow(j);

                            for (int k = 0; k< this.numberOfColumnsViableVariables(storedSheet); k++) {

                                XSSFCell storedCell = storedRow.getCell( k, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);

                                String currentCellValue = this.convertCellValueToString( storedCell );
                                XSSFCell createdCell = createdRow.createCell(k);
                                createdCell.setCellValue(currentCellValue);
                            }
                        }
                    }
                }
                storedWB.close();

                return createdWB;
            } else {
                System.out.println("(Invalid) File does not exists! or Row Number Out of Bounds!");
                return null;
            }
        } catch (Exception e)   {
           e.printStackTrace();
        }
        return null;
    }                                // IS WORKING

    private ArrayList<String> arrayFromFirstRowOfColumns (XSSFRow row){
        ArrayList<String> arrayStr = new ArrayList<>();
        for (int i=0; i<this.numberOfColumnsViableVariables( row.getSheet() ); i++) {
            XSSFCell cell = row.getCell(i);
            String stringCell = this.convertCellValueToString(cell);
            arrayStr.add( stringCell );
        }
        return arrayStr;
    }                           // IS WORKING

    private ArrayList<String> arrayFromFirstRowOfColumns (XSSFSheet sheet){

        ArrayList<String> arrayStr = new ArrayList<>();

        if (sheet != null)  {
            XSSFRow row = sheet.getRow(0);
            for (int i=0; i<this.numberOfColumnsViableVariables( row.getSheet() ); i++) {
                XSSFCell cell = row.getCell(i);
                String stringCell = this.convertCellValueToString(cell);
                arrayStr.add( stringCell );
            }
        }
        return arrayStr;
    }                       // IS WORKING

    private int numberOfColumnsViableVariables(XSSFSheet sheet)  {
        XSSFRow rowWithCollumnNames = sheet.getRow(0);
        int colCount = 0;
        boolean cellHasContent = true;

        while (cellHasContent)  {
           //String str = this.convertCellValueToString( rowWithCollumnNames.getCell(colCount, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL));
            XSSFCell collumnCell = rowWithCollumnNames.getCell(colCount);
            if (collumnCell ==null || collumnCell.getCellTypeEnum() == CellType.BLANK) {
          //  if (str == null || str.trim().length() == 0)  {
                cellHasContent =false;
                break;
            }
            colCount++;
        }
        return colCount;
    }                                 // IS WORKING

    private String convertCellValueToString (XSSFCell cell) {
        String stringOfCell = null;
        if ( cell != null ){
            if(cell.getCellTypeEnum() == CellType.BLANK)
                stringOfCell = "";
            else if (cell.getCellTypeEnum() == CellType.NUMERIC)
                stringOfCell = "" + cell.getNumericCellValue();
            else if (cell.getCellTypeEnum() == CellType.STRING)
                stringOfCell = cell.getStringCellValue();
            return stringOfCell;
        }
        else
            return "";
    }                                     // IS WORKING

    private boolean rowIsEmpty (XSSFRow row)    {
        int test=0;
        boolean cellsEmpty = true;
        //System.out.println("ROW = "+ row.getRowNum());
        if (row != null){
            for (int i = 0; i < this.numberOfColumnsViableVariables(row.getSheet()); i++) {

                XSSFCell cell = row.getCell(i);
                if (cell != null) {
                    if ((cell.getCellTypeEnum() != CellType.BLANK) ||
                            (!this.convertCellValueToString(cell).trim().isEmpty()) ||
                            (this.convertCellValueToString(cell).trim().length() != 0)) {
                        cellsEmpty = false;
                        break;
                    }
                }
                test++;
                //System.out.println("EMPT: "+ test +"   ");
            }
        }
        else
            cellsEmpty = true;
        //System.out.println(" ");
        return cellsEmpty;
    }                                                 // IS WORKING

    private int numberOfRowsIntSheet (XSSFSheet sheet)  {
        boolean doesRowHaveContent = true;
        boolean doesCellHaveContent;
        int i =1;
        int rowCount = 0;

        while ( doesRowHaveContent )   {

            XSSFRow row = sheet.getRow(i);
            doesRowHaveContent = false; // we assume this next row is EMPTY
            doesCellHaveContent = false;
            int maxColNum = this.numberOfColumnsViableVariables(sheet);
            int j=0;
            while ( !this.rowIsEmpty(row)) {

                XSSFCell cell = row.getCell(j);
                String cellContent = new String();

                if (cell== null || cell.getCellTypeEnum() ==  CellType.BLANK){
                    // if cell is blank
                    // do nothing, move on
                }
                else {
                    // if cell has any content
                    if (cell.getCellTypeEnum() == CellType.STRING)
                        cellContent = cell.getStringCellValue();
                    else if (cell.getCellTypeEnum() == CellType.NUMERIC)
                        cellContent = "" + cell.getNumericCellValue();

                    if ( !cellContent.trim().isEmpty() || cellContent.trim().length() != 0)     {
                        rowCount ++;
                        doesCellHaveContent = true;
                        doesRowHaveContent = true;  // if at least one CELL on row has content, then Row has content
                        break;
                    }
                }
                j++;
            }
        i++;
        }
        return rowCount;
    }                                          // IS WORKING

    public XSSFRow getFirstRowBlogData (XSSFRow row)  {

        for (int i=0; i< this.columnsInBlogData.size(); i++) {
            row.createCell(i).setCellValue( this.columnsInBlogData.get( i ) );
        }
        return row;
    }                                            // IS WORKING

    public XSSFRow getFirstRowLinkTarget (XSSFRow row)  {

        for (int i=0; i< this.columnsInLinkTargets.size(); i++) {
            row.createCell(i).setCellValue( this.columnsInLinkTargets.get( i ) );
        }
        return row;
    }                                           // IS WORKING

    public XSSFRow getFirstRowArticle (XSSFRow row)  {

        for (int i=0; i< this.columnsInArticles.size(); i++) {
            row.createCell(i).setCellValue( this.columnsInArticles.get( i ) );
        }
        return row;
    }                                               // IS WORKING

    // --------------------------------------- DISPLAY and FORMATTING --------------------------------------------------

    public void saveWorkbookToFile (String fileLocationSave, XSSFWorkbook workbook  )  {

        // ------------- SAVE -------------------
        try {
            if (fileLocationSave != null && workbook != null)  {
                FileOutputStream outStream = new FileOutputStream( fileLocationSave );
                this.workbookWrite = workbook;
                this.workbookWrite.write( outStream );
                this.workbookWrite.close();
            }
        }
        catch (Exception e)       {
            e.printStackTrace();
        }
    }              // IS WORKING


    // ---------------------------------------- SETTERS and GETTERS ----------------------------------------------------
    public void setFileLocation (String fileLocation)   {
        this.fileLocation = fileLocation;
    }             // IS WORKING

    public String getFileLocation ()    {
        return this.fileLocation;
    }                                      // IS WORKING

    public ArrayList<String> getColumnsInBlogData() {
        return columnsInBlogData;
    }                        // IS WORKING

    public ArrayList<String> getColumnsInLinkTargets() {
        return columnsInLinkTargets;
    }                                            // IS WORKING

    public ArrayList<String> getColumnsInArticles() {
        return columnsInArticles;
    }                       // IS WORKING

    public XSSFWorkbook getWorkbookRead()   {return this.workbookRead;}                                 // IS WORKING


    private void setColumnsInBlogData(ArrayList<String> columnsInBlogData) {
        this.columnsInBlogData = columnsInBlogData;
    }                         // IS WORKING

    private void setColumnsInLinkTargets(ArrayList<String> columnsInLinkTargets) {
        this.columnsInLinkTargets = columnsInLinkTargets;
    }                  // IS WORKING

    private void setColumnsInArticles (ArrayList<String> columnsInArticles) {
        this.columnsInArticles = columnsInArticles;
    }                      // IS WORKING



}



/*
     HINTS

    1. EXCELL Cell Type = TEXT
        In the locally stored xlsx file, in every sheet, select all Cells - change their Type to Text (from General).

    2. EMPTY CELLs and ROWs return NULL
        If a cell (from local file location) has not value. Then row.getCell(i) returns NULL. And any methon called on that will fail.
        Sama with Row. A row with all cells blank will return null. ( sheet.getRow())
        USE (if(cell!=null) if(row!=null) or throwing exception NullPointerException.


        public void countEmptyCellsInSheet (XSSFSheet sheet)    {
        for (int i=1; i<15; i++){
            XSSFRow row = sheet.getRow(i);
            if (row!= null)
                this.rowIsEmpty(row);
                if (this.rowIsEmpty(row))   {
                    System.out.println("HEY! Row"+ i +" is empty.");
                }
        }
    }   // TEST - to be deleted
*/