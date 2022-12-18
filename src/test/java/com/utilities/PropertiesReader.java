package com.utilities;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertiesReader {
	
	static FileInputStream oFile;
	
	public static String getPropertyValue(String sFileName,String sKey) {
		String sFile = "./src/test/resources/datas/"+sFileName+".properties";
		String sValue = null;
		try {
			oFile = new FileInputStream(sFile);
			Properties  oProp = new Properties();
			oProp.load(oFile);
			sValue= oProp.getProperty(sKey);
			return sValue;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sValue;
	}

}
