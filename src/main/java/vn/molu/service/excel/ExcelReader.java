package vn.molu.service.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import vn.molu.domain.admin.C2AdminUserAuto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader {
    private Workbook workbook;

    public ExcelReader(MultipartFile file) throws IOException {

        if (file.getOriginalFilename().toLowerCase().endsWith(".xlsx")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (file.getOriginalFilename().toLowerCase().endsWith(".xls")) {
            workbook = new HSSFWorkbook(file.getInputStream());
        } else {
            throw new IllegalArgumentException("Unsupported file format. Only .xlsx and .xls files are supported.");
        }

    }

    public List<C2AdminUserAuto> readAddUserExcelFile() {
        List<C2AdminUserAuto> rs = new ArrayList<>();
        Sheet sheet = workbook.getSheetAt(0);
        int startRow = 1;

        for (int rowIndex = startRow; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                C2AdminUserAuto c2AdminUserAuto = new C2AdminUserAuto();
                for (int cellIndex = 0; cellIndex <= row.getLastCellNum(); cellIndex++) {
                    c2AdminUserAuto.setUser_name(row.getCell(0).toString());
                    c2AdminUserAuto.setFull_name(row.getCell(1).toString());
                    c2AdminUserAuto.setPhone(row.getCell(2).toString());
                    c2AdminUserAuto.setBirthday(row.getCell(3).toString());
                    c2AdminUserAuto.setCmnd(row.getCell(4).toString());
                    c2AdminUserAuto.setNgaycap(row.getCell(5).toString());
                    c2AdminUserAuto.setNoicap(row.getCell(6).toString());
                    c2AdminUserAuto.setToThu(row.getCell(7).toString());
                    c2AdminUserAuto.setShop_code(row.getCell(8).toString());
                    c2AdminUserAuto.setGranted_ip(row.getCell(9).toString());
                    c2AdminUserAuto.setAccess_schedule(row.getCell(10).toString());
                    c2AdminUserAuto.setStatus(Integer.parseInt(row.getCell(11).toString()));
                    c2AdminUserAuto.setType(Integer.parseInt(row.getCell(12).toString()));
                    c2AdminUserAuto.setCity(row.getCell(13).toString());
                    c2AdminUserAuto.setDistrict(row.getCell(14).toString());
                    c2AdminUserAuto.setProgram(row.getCell(15).toString());
                }
                rs.add(c2AdminUserAuto);
            }
        }
        return rs;
    }

    public List<String> readInactivityExcelFile(){
        List<String> rs = new ArrayList<>();
        Sheet sheet = workbook.getSheetAt(0);
        int startRow = 1;

        for (int rowIndex = startRow; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                String temp = "";
                for (int cellIndex = 0; cellIndex <= row.getLastCellNum(); cellIndex++) {
                    temp = row.getCell(0).toString();
                }
                rs.add(temp);
            }
        }
        return rs;
    }

    public void close() throws IOException {
        workbook.close();
    }
}
