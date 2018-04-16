package com.example.util;

import au.com.bytecode.opencsv.CSVWriter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;

public class ExcelUtil {
	
	public static void getExcelXls() {
		Workbook wb = new HSSFWorkbook();
	}
	
	public static void getExcelXlsx() {
		Workbook wb = new XSSFWorkbook();
	}
	//写入excel文件并导出
	public static void writeToExcelFileForExport(List<String[]> data, String file_name, HttpServletResponse response) throws IOException {
	    // 先去掉文件名称中的空格,然后转换编码格式为utf-8,保证不出现乱码,这个文件名称用于浏览器的下载框中自动显示的文件名
	    response.addHeader("Content-Disposition", "attachment;filename=" + new String(file_name.replaceAll(" ", "")));
	    //response.addHeader("Content-Length", "" + file.length());
	    response.setContentType("application/vnd.ms-excel");
		OutputStreamWriter out = new OutputStreamWriter(response.getOutputStream(),"UTF-8");
		CSVWriter writer = new CSVWriter(out);
		writer.writeAll(data);
		writer.close();
		System.out.println(out.getEncoding());
	    out.flush();
	    out.close();
	}
	
	public static void excelExportExcel(List<String> titles, List<List<String>> datas, HttpServletResponse response) throws IOException {
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("new sheet");
		
		Row titleRow = sheet.createRow(0);
		Cell titleCell = null;
		for(int i=0; i<titles.size(); i++) {
			titleCell = titleRow.createCell(i);
			titleCell.setCellValue(titles.get(i));
		}
		
		Row dataRow = null;
		for(int i=0; i<datas.size(); i++) {
			dataRow =  sheet.createRow(i + 1);
			Cell dataCell = null;
			for(int j=0; j<datas.get(i).size(); j++) {
				dataCell = dataRow.createCell(j);
				dataCell.setCellValue(datas.get(i).get(j));
			}
		}
		
		response.setContentType("application/octet-stream");
		response.setHeader("Content-disposition", "attachment;filename=" + System.currentTimeMillis() + ".xls");
		response.flushBuffer();
		wb.write(response.getOutputStream());
	}
}
