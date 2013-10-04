package org.google.api.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.google.api.model.ModelObject;
import org.google.api.ui.controller.KeywordFileUploadException;

public class CSVInputFileParser {
	private static final Log logger = LogFactory.getLog(CSVInputFileParser.class);
	private static String []columnHeaders = new String[]{"Keyword", "Avg. Monthly Searches", "Avg. CPC"};
	public static Collection<ModelObject> parseCSVFile(File file) throws IOException{
		Collection<ModelObject> models = new CopyOnWriteArrayList<ModelObject>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		/*List<String[]> data = CsvFiles.getCsvDataArray(reader, true);
		for(String [] dataSet : data){
			for(String str : dataSet)
				System.out.print(str);
			System.out.println();
		}*/
		logger.info("Opened reader for "+file.getAbsolutePath());
		int counter=0;
		String line = null;
		logger.info("Starting reading file");
		int keywordIndex = -1;
		int monthlySearchIndex = -1;
		int cpcIndex = -1;
		
		while((line = reader.readLine())!=null){
			if(line.contains(String.valueOf(Character.forDigit(-1, 10))))
				line = line.replaceAll(Character.forDigit(-1, 10)+"", "");	
			String []elements = line.trim().split("\t");
			if(counter==0){
				//String []elements = new String(Charset.forName("UTF-16").encode(line).array()).trim().split("\t");				
				for(int k=0;k<elements.length;k++){
					if(elements[k].equals(columnHeaders[0]))
						keywordIndex = k;
					if(elements[k].equals(columnHeaders[1]))
						monthlySearchIndex = k;
					if(elements[k].equals(columnHeaders[2]))
						cpcIndex = k;					
				}
				counter++;
				if(keywordIndex==-1 || monthlySearchIndex==-1 || cpcIndex==-1){
					reader.close();
					throw new KeywordFileUploadException(MessageFormat.format(KeywordFileUploadException.MSNG_REQD_ATTRIB_IN_UPLD_FILE, (Object [])columnHeaders));
				}
				continue;
			}
			
			//String []elements = new String(line.getBytes(Charset.forName("UTF8"))).trim().split("\t");
			if(elements.length<=0)
				continue;
			ModelObject obj = new ModelObject();
			
			try {
				obj.setKeyword(elements[keywordIndex]);
				obj.setGlobalMonthlySearches(Integer.parseInt(elements[monthlySearchIndex]));
				obj.setCpc(Double.parseDouble(elements[cpcIndex]));
			} catch (Exception e) {
				e.printStackTrace();
				counter++;
				continue;
			}
			models.add(obj);
			counter++;			
		}
		logger.info("Read "+counter+" lines");
		reader.close();
		return models;
	}
	public static void main(String args[]) throws Exception{
		File file = new File("C:/data/download-2cceb922-ba63-4ad4-8945-c1eedf9fb30a.csv");
		
		//System.out.println(FileUtils.readLines(file));
		System.out.println(parseCSVFile(file));
	}
}
