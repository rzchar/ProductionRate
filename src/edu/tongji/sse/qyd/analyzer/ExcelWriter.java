package edu.tongji.sse.qyd.analyzer;

import edu.tongji.sse.qyd.util.DatePeriod;
import edu.tongji.sse.qyd.util.Path;
import edu.tongji.sse.qyd.resultStructure.AnalyzeResult;
import edu.tongji.sse.qyd.resultStructure.cost.CostTypeSet;
import edu.tongji.sse.qyd.resultStructure.effort.EffortTypeSet;
import edu.tongji.sse.qyd.resultStructure.info.InfoSet;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.FillPatternType;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by qyd on 2018/5/29.
 */
public class ExcelWriter {
    HSSFWorkbook workbook;
    HSSFSheet sheetADD;
    HSSFSheet sheetDEL;
    HSSFSheet sheetCHG;
    File outputFile;
    HSSFCellStyle cellStyle;

    public ExcelWriter(String fileName) {
        this.outputFile = new File(Path.getOutputPath() + File.separator + fileName);
        if (outputFile.exists()) {
            outputFile.delete();
        }

        try {
            outputFile.createNewFile();
        } catch (IOException e) {
            workbook = null;
            e.printStackTrace();
            return;
        }

        workbook = new HSSFWorkbook();
        this.cellStyle = workbook.createCellStyle();
        this.cellStyle.setFillBackgroundColor(HSSFColor.LIGHT_GREEN.index);
        this.cellStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
        this.cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        this.sheetADD = workbook.createSheet("add");
        this.sheetDEL = workbook.createSheet("del");
        this.sheetCHG = workbook.createSheet("chg");

        HSSFRow rowADD = sheetADD.createRow(0);
        HSSFRow rowDEL = sheetDEL.createRow(0);
        HSSFRow rowCHG = sheetCHG.createRow(0);
        AnalyzeResult resultForHeader = new AnalyzeResult(new CostTypeSet(), new EffortTypeSet());
        rowADD.createCell(0).setCellValue("time");
        rowDEL.createCell(0).setCellValue("time");
        rowCHG.createCell(0).setCellValue("time");
        int offset = 1;
        for (int i = 0; i < resultForHeader.getCostTypeSet().costTypeList.length; i++) {
            rowADD.createCell(i + offset).setCellValue(resultForHeader.getCostTypeSet().costTypeList[i].getTypeString());
            rowDEL.createCell(i + offset).setCellValue(resultForHeader.getCostTypeSet().costTypeList[i].getTypeString());
            rowCHG.createCell(i + offset).setCellValue(resultForHeader.getCostTypeSet().costTypeList[i].getTypeString());
            rowADD.getCell(i+ offset).setCellStyle(this.cellStyle);
            rowDEL.getCell(i+ offset).setCellStyle(this.cellStyle);
            rowCHG.getCell(i+ offset).setCellStyle(this.cellStyle);
        }
        offset += resultForHeader.getCostTypeSet().costTypeList.length;
        for (int i = 0; i < resultForHeader.getEffortTypeSet().effortTypeList.length; i++) {
            rowADD.createCell(i + offset).setCellValue(resultForHeader.getEffortTypeSet().effortTypeList[i].getTypeString());
            rowDEL.createCell(i + offset).setCellValue(resultForHeader.getEffortTypeSet().effortTypeList[i].getTypeString());
            rowCHG.createCell(i + offset).setCellValue(resultForHeader.getEffortTypeSet().effortTypeList[i].getTypeString());
        }
        offset += resultForHeader.getEffortTypeSet().effortTypeList.length;
        for (int i = 0; i < InfoSet.getHeader().length; i++) {
            rowADD.createCell(i + offset).setCellValue(InfoSet.getHeader()[i]);
            rowDEL.createCell(i + offset).setCellValue(InfoSet.getHeader()[i]);
            rowCHG.createCell(i + offset).setCellValue(InfoSet.getHeader()[i]);
            rowADD.getCell(i+ offset).setCellStyle(this.cellStyle);
            rowDEL.getCell(i+ offset).setCellStyle(this.cellStyle);
            rowCHG.getCell(i+ offset).setCellStyle(this.cellStyle);
        }

        try {
            workbook.write(outputFile);
        } catch (IOException e) {
            workbook = null;
            e.printStackTrace();
        }
    }

    private void sumTheProvide(List<AnalyzeResult> results, AnalyzeResult sumResult) {

        for (AnalyzeResult result : results) {
            for (int i = 0; i < sumResult.getCostTypeSet().costTypeList.length; i++) {
                double added = result.getCostTypeSet().costTypeList[i].getAddLineSum();
                double deleted = result.getCostTypeSet().costTypeList[i].getDeleteLineSum();
                double changed = result.getCostTypeSet().costTypeList[i].getChangeLineSum();
                sumResult.getCostTypeSet().costTypeList[i].addAddLine(added);
                sumResult.getCostTypeSet().costTypeList[i].addDeleteLine(deleted);
                sumResult.getCostTypeSet().costTypeList[i].addChangeLine(changed);
            }

            for (int i = 0; i < sumResult.getEffortTypeSet().effortTypeList.length; i++) {
                double added = result.getEffortTypeSet().effortTypeList[i].getAddLineSum();
                double deleted = result.getEffortTypeSet().effortTypeList[i].getDeleteLineSum();
                double changed = result.getEffortTypeSet().effortTypeList[i].getChangeLineSum();
                sumResult.getEffortTypeSet().effortTypeList[i].addAddLine(added);
                sumResult.getEffortTypeSet().effortTypeList[i].addDeleteLine(deleted);
                sumResult.getEffortTypeSet().effortTypeList[i].addChangeLine(changed);
            }

            InfoSet sumInfoSet  = sumResult.getInfoSet();
            sumInfoSet.addCommitterAmount(result.getInfoSet().getCommitterAmount());
        }
    }

    private void writeResultToSheet(AnalyzeResult sumResult, DatePeriod datePeriod) {
        HSSFRow rowADD = sheetADD.createRow(sheetADD.getLastRowNum() + 1);
        HSSFRow rowDEL = sheetDEL.createRow(sheetDEL.getLastRowNum() + 1);
        HSSFRow rowCHG = sheetCHG.createRow(sheetCHG.getLastRowNum() + 1);

        rowADD.createCell(0).setCellValue(datePeriod.getSinceUntilFileName("",""));
        rowDEL.createCell(0).setCellValue(datePeriod.getSinceUntilFileName("",""));
        rowCHG.createCell(0).setCellValue(datePeriod.getSinceUntilFileName("",""));

        int offset = 1;
        for (int i = 0; i < sumResult.getCostTypeSet().costTypeList.length; i++) {
            rowADD.createCell(i + offset).setCellValue(sumResult.getCostTypeSet().costTypeList[i].getAddLineSum());
            rowDEL.createCell(i + offset).setCellValue(sumResult.getCostTypeSet().costTypeList[i].getAddLineSum());
            rowCHG.createCell(i + offset).setCellValue(sumResult.getCostTypeSet().costTypeList[i].getAddLineSum());
            rowADD.getCell(i+ offset).setCellStyle(this.cellStyle);
            rowDEL.getCell(i+ offset).setCellStyle(this.cellStyle);
            rowCHG.getCell(i+ offset).setCellStyle(this.cellStyle);
        }
        offset += sumResult.getCostTypeSet().costTypeList.length;
        for (int i = 0; i < sumResult.getEffortTypeSet().effortTypeList.length; i++) {
            rowADD.createCell(i + offset).setCellValue(sumResult.getEffortTypeSet().effortTypeList[i].getAddLineSum());
            rowDEL.createCell(i + offset).setCellValue(sumResult.getEffortTypeSet().effortTypeList[i].getAddLineSum());
            rowCHG.createCell(i + offset).setCellValue(sumResult.getEffortTypeSet().effortTypeList[i].getAddLineSum());
        }

        offset += sumResult.getEffortTypeSet().effortTypeList.length;
        for (int i = 0; i < sumResult.getInfoSet().getInfomation().length; i++) {
            rowADD.createCell(i + offset).setCellValue(sumResult.getInfoSet().getInfomation()[i]);
            rowDEL.createCell(i + offset).setCellValue(sumResult.getInfoSet().getInfomation()[i]);
            rowCHG.createCell(i + offset).setCellValue(sumResult.getInfoSet().getInfomation()[i]);
            rowADD.getCell(i+ offset).setCellStyle(this.cellStyle);
            rowDEL.getCell(i+ offset).setCellStyle(this.cellStyle);
            rowCHG.getCell(i+ offset).setCellStyle(this.cellStyle);
        }


        try {
            workbook.write(this.outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean addRow(List<AnalyzeResult> results, DatePeriod datePeriod) {
        if (workbook == null) {
            return false;
        }
        AnalyzeResult sumResult = new AnalyzeResult(new CostTypeSet(), new EffortTypeSet());

        sumTheProvide(results, sumResult);
        writeResultToSheet(sumResult, datePeriod);

        return true;
    }

    public boolean close(){

        try {
            workbook.write(this.outputFile);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        workbook = null;
        return true;
    }

    public static void main(String[] args){

        ExcelWriter excelWriter = new ExcelWriter("text.xls");
        excelWriter.close();

    }
}
