package zephyr;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.extractor.XSSFExcelExtractor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by zephyr on 2018/12/3.
 */
public class POIDemo {

    public static void main(String[] args) throws IOException, InvalidFormatException {
        test1();
        //test2();
        //test3();
    }

    private static void test1() throws IOException {
        try (InputStream inp = new FileInputStream("/Users/zephyr/IdeaProjects/poi/src/main/resources/明信片.xlsx");
             XSSFWorkbook wb = new XSSFWorkbook(inp);
             XSSFExcelExtractor extractor = new XSSFExcelExtractor(wb)) {
            extractor.setFormulasNotResults(true);
            extractor.setIncludeSheetNames(false);
            String text = extractor.getText();
            System.out.println(text);
        }
    }

    private static void test2() throws IOException, InvalidFormatException {
        try (OPCPackage pkg = OPCPackage.open("/Users/zephyr/IdeaProjects/poi/src/main/resources/明信片.xlsx")) {
            XSSFWorkbook wb = new XSSFWorkbook(pkg);
            XSSFExcelExtractor extractor = new XSSFExcelExtractor(wb);
            extractor.setFormulasNotResults(true);
            extractor.setIncludeSheetNames(false);
            String text = extractor.getText();
            System.out.println(text);
        }

    }

    private static void test3() throws IOException, InvalidFormatException {
        try (OPCPackage pkg = OPCPackage.open("/Users/zephyr/IdeaProjects/poi/src/main/resources/明信片.xlsx")) {
            XSSFWorkbook wb = new XSSFWorkbook(pkg);
            Sheet sheet = wb.getSheetAt(0);
            for (int r = 1; r < sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                for (int c = 0; c < row.getLastCellNum(); c++) {
                    Cell cell = row.getCell(c);
                    cell.setCellType(CellType.STRING);
                    System.out.println(cell.getStringCellValue());
                }
            }
        }

    }
}
