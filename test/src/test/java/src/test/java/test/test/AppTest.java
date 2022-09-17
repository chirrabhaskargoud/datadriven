package src.test.java.test.test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AppTest {
    
	public static void main(String args[]) {
		
		String path="/Users/chirra.bhaskar/eclipse-workspace/test/src/test/java/src/test/java/test/test/book1.xlsx";
		Object a[][]=getDataFromDataprovider(path,"Sheet1","Scenario","tc1");
		System.out.println(a);
		int length=Array.getLength(a);
		List<HashMap<String, String>> map = loadTestData(a);		
	}
	
 public static Object[][] getDataFromDataprovider(String path, String sheetName, String columnName, String columnValue){
	 	 String filepath=path;		 
		 String tcId = columnValue; 
		 	 
		 Object[][] data=null;
		 Xls_Reader testData;
		 try
		 {
		 testData = new Xls_Reader(filepath);
		 int startIndex = testData.getCellRowNum(sheetName, columnName, tcId);
		 
		 List listrowValues = new ArrayList<Object>();
		 String colNames="";
         String colValues="";
         String tcIdCell;
         String sheetColValue;
         String previousTcID=null;
		 int rowCount=0;
		 for(int i=startIndex-1;i<=testData.getRowCount(sheetName);i++)
		 {
			 tcIdCell = testData.getCellData(sheetName, 0, i);
			 if(rowCount!=0 &&  !(tcIdCell.equals(previousTcID)) && !previousTcID.equals(columnName))
		    	 break;
			 
			 sheetColValue = testData.getCellData(sheetName, 1, i).trim();
		    
		    for(int j=1;!(testData.getCellData(sheetName, j, i).isEmpty());j++){
		     if(rowCount==0)
		      	colNames = colNames+"^"+testData.getCellData(sheetName, j, i);
			 else
			 	colValues = colValues+"^"+testData.getCellData(sheetName, j, i);
		    }
		    if(!(colValues.isEmpty()))
		    {
		    	listrowValues.add(colValues);
		    	rowCount++;
		    	colValues="";
		    }
		    if(rowCount==0 && !(colNames.isEmpty()))
		    	rowCount++;	
		    previousTcID = tcIdCell;
		    
		 }
		 rowCount = rowCount-1;
		 data = new Object[rowCount][2];
		 for(int i=0;i<rowCount;i++)
		 { 
			 data[i][0]= colNames;
			 data[i][1]= listrowValues.get(i);
		 }
		 }
		 catch( Exception e )
		 {
		 System.err.println( e );
		 }
		 return data;
    }
 
 public static List<HashMap<String, String>> loadTestData(Object[][] data){
	 HashMap<String, String> TestContext= null;
	 int length=Array.getLength(data);
	 List<HashMap<String, String>> list=new ArrayList<HashMap<String, String>>();
	 for (int i = 0; i < length; i++) {
		 TestContext= new HashMap<String, String>();
         // Loop through all elements of current row
         System.out.print(data[i][0] + " "+data[i][1]);
         String ColumnNames=data[i][0].toString();
         String ColumnValues=data[i][1].toString();
         
    	 String[] Keys = ColumnNames.split("\\^");
    	 String[] Values = ColumnValues.split("\\^");
    	 for(int x=1;x<Keys.length;x++){
    		 TestContext.put(Keys[x].trim(), Values[x].trim());
    	 }
    	 list.add(TestContext);
	 }
	 return list;	 
 }
}
