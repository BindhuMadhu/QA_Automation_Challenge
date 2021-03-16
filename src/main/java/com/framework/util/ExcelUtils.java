package com.framework.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

public class ExcelUtils {

      private static String file;
      private int columnNumber = 1;
      private String sheetPath = "";

    public  ExcelUtils(String file) {
        this.file = file;
    }

    public Workbook getBook() {
        return getBook(file);
    }

    public Workbook getBook(String file) {
        Workbook book = null;
        
        try {
            if (file.endsWith(".xls")) {
                book = new HSSFWorkbook(new FileInputStream(new File(file)));
            } else if (file.endsWith("xlsx")) {
                book = new XSSFWorkbook(new FileInputStream(new File(file)));
            } else {
                throw new RuntimeException("invalid file format");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return book;
    }

    public Object[][] getDataArray(String sheetName) {
        Sheet sheet = getBook().getSheet(sheetName);
        int number_of_rows = sheet.getPhysicalNumberOfRows();
        int cols_number = sheet.getRow(0).getPhysicalNumberOfCells();
        Iterator<Row> rows = sheet.rowIterator();
        String array[][] = new String[number_of_rows - 1][cols_number];
        int count = 0;
        rows.next();
        while (rows.hasNext()) {
            Row row = rows.next();
            Iterator<Cell> cells = row.cellIterator();
            int col_count = 0;
            while (cells.hasNext()) {
                Cell cell = cells.next();
                array[count][col_count] = getStringCellValue(cell);
                col_count++;
            }
            count++;
        }
        return array;
    }

    public String getStringCellValue(Cell cell) {
        cell.setCellType(Cell.CELL_TYPE_STRING);
        return cell.getStringCellValue();
    }

    public Object[][] getDataArray(String sheetName, int col) {
        Sheet sheet = getBook().getSheet(sheetName);
        int number_of_rows = sheet.getPhysicalNumberOfRows();
        Iterator<Row> rows = sheet.rowIterator();
        String[][] array = new String[number_of_rows - 1][1];
        int count = 0;
        rows.next();
        while (rows.hasNext()) {
            Row row = rows.next();
            Cell cell = row.getCell(col);
            array[count][0] = getStringCellValue(cell);
            count++;
        }
        return array;
    }

    public String getTestData(String key){
        String value = "";
        boolean found = false;

        int numberOfSheets = getBook().getNumberOfSheets();
        for(int i=0;i<numberOfSheets;i++){
            Sheet sheet = getBook().getSheetAt(i);
            Iterator<Row> itr = sheet.rowIterator();
            while(itr.hasNext()){
                Row row = itr.next();
                if(row.getCell(0) == null){
                    continue;
                }
                if(row.getCell(0).getStringCellValue().equalsIgnoreCase(key)){
                	value = getCellStringValue(row.getCell(columnNumber));
                    found = true;
                    break;
                }
            }
            if(found){
                break;
            }
        }
        return value;
    }

    public String getCellStringValue(Cell cell){
    	String value = "";
    	if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
    		value = String.valueOf(cell.getNumericCellValue());
    	}
    	else if(cell.getCellType() == Cell.CELL_TYPE_STRING){
    		value = cell.getStringCellValue();
    	}
    	else{
    		
    	}
    	return value;
    }

}
