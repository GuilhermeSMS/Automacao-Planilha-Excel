package com.automacao;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class LeitorExcel {

  
    public List<String> lerCodigosDaPlanilha(String caminhoArquivo) {
        List<String> codigos = new ArrayList<>();
        
       
        try (FileInputStream fis = new FileInputStream(caminhoArquivo);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0); 

            for (Row row : sheet) {
                Cell cell = row.getCell(0); 
                if (cell != null) {
                    String valorCelula = "";
                    if (cell.getCellType() == CellType.STRING) {
                        valorCelula = cell.getStringCellValue();
                    } else if (cell.getCellType() == CellType.NUMERIC) {
                        valorCelula = String.valueOf((long)cell.getNumericCellValue());
                    }
                    if (!valorCelula.isEmpty()) {
                        codigos.add(valorCelula.trim());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro ao ler o arquivo Excel: " + e.getMessage());
        }
        System.out.println("Total de " + codigos.size() + " códigos lidos do Excel.");
        return codigos;
    }
}