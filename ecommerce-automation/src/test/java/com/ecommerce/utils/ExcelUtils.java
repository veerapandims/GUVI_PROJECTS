package com.ecommerce.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelUtils {
    public static Object[][] readSheet(String path, String sheetName) {
        try (InputStream fis = new FileInputStream(path);
             Workbook wb = new XSSFWorkbook(fis)) {
            Sheet sheet = wb.getSheet(sheetName);
            if (sheet == null) return new Object[0][];
            List<Object[]> rows = new ArrayList<>();
            Iterator<Row> itr = sheet.iterator();
            Row header = itr.hasNext() ? itr.next() : null;
            while (itr.hasNext()) {
                Row r = itr.next();
                int cols = r.getLastCellNum();
                Object[] vals = new Object[cols];
                for (int i = 0; i < cols; i++) {
                    Cell c = r.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    if (c.getCellType() == CellType.STRING) vals[i] = c.getStringCellValue();
                    else if (c.getCellType() == CellType.NUMERIC) vals[i] = String.valueOf(c.getNumericCellValue());
                    else if (c.getCellType() == CellType.BOOLEAN) vals[i] = String.valueOf(c.getBooleanCellValue());
                    else vals[i] = "";
                }
                rows.add(vals);
            }
            Object[][] out = new Object[rows.size()][];
            return rows.toArray(out);
        } catch (Exception e) {
            e.printStackTrace();
            return new Object[0][];
        }
    }
}