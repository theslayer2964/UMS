package vn.molu.common.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;
//Class Config được sử dụng để đọc và trả về đường dẫn đến vị trí lưu trữ tệp PDF từ tệp cấu hình config.properties
public class Config  {
    private transient final Logger log = Logger.getLogger(getClass().toString());
	/*
		getPDFsLocation():
		Đọc và trả về đường dẫn đến vị trí lưu trữ tệp PDF từ tệp cấu hình config.properties
		Ví dụ:
			Input: ""
			Output: pdf.location=/path/to/pdf/files/
	 */
    public String getPDFsLocation() throws IOException {
    	String pdfLocation = null;
    	InputStream inputStream = null;
		try {
			Properties prop = new Properties();
			String propFileName = "config.properties";
 
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
 
			// get the property value and print it out
			pdfLocation = prop.getProperty("pdf.location");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			inputStream.close();
		}
		return pdfLocation;
	}
    
    public static void main(String[] args){
    	try {
			System.out.println(new Config().getPDFsLocation());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}