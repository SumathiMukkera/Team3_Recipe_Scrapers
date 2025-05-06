package com.Utilities;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.annotations.DataProvider;

public class DataProviderClass {
	
	   @DataProvider(name = "excelData")
	    public Object[][] excelDataProvider() throws IOException, org.apache.poi.openxml4j.exceptions.InvalidFormatException {
	        ExcelDataReader reader = new ExcelDataReader();
	        String filepath = configReader.getexcelfilepath();
	        String sheetname = configReader.getSheetName();
	        List<Map<String, String>> list = reader.getData(filepath , sheetname);
	        
	        Object[][] data = new Object[list.size()][1];
	        for (int i = 0; i < list.size(); i++) {
	            data[i][0] = list.get(i);
	        }
	        return data;
	    }

}
