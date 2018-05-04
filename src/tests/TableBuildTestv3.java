package tests;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import backend.html.TableBuilder;
import exceptions.BuildException;

class TableBuildTestv3 {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
	}

	private void printHTMLToConsole(List<String> html)
	{
		for(String line : html)
			System.out.println(line);
	}
	
	@Test
	void testCase1() {
		TableBuilder table=new TableBuilder();
		
		//printHTMLToConsole(table.getHTMLLines());
	}

	@Test
	void testCase2() throws BuildException {
		TableBuilder table=new TableBuilder();
		table.build();
		
		//printHTMLToConsole(table.getHTMLLines());
	}
	
	@Test
	void testCase3() throws BuildException {
		TableBuilder table=new TableBuilder();
		
		String[] headers= {"Header 1","Header 2","Header 3"};
		table.setHeaders(headers).build();
		
		//printHTMLToConsole(table.getHTMLLines());
	}
	
	@Test
	void testCase4() throws BuildException {
		TableBuilder table=new TableBuilder();
		
		String[] headers= {};
		table.setHeaders(null).build();
		
		printHTMLToConsole(table.getHTMLLines());
	}
	
	@Test
	void testCase5() throws BuildException {
		TableBuilder table=new TableBuilder();
		
		String[] headers= {"Header 1","Header 2","Header 3"};
		String[] data= {"Data 1","Data 2","Data 3"};
		table.setHeaders(headers).addDataRow(data).addDataRow(data).addDataRow(data).build();
		
		//printHTMLToConsole(table.getHTMLLines());
	}
	
	@Test
	void testCase6() throws BuildException {
		TableBuilder table=new TableBuilder();
		
		String[] headers= {"Header 1","Header 2","Header 3"};
		String[] data1= {"Data 1","Data 3"};
		String[] data= {"Data 1","Data 2","Data 3"};
		table.setHeaders(headers).addDataRow(data1).addDataRow(data1).addDataRow(data).build();
		
		//printHTMLToConsole(table.getHTMLLines());
	}
	
	@Test
	void testCase7() throws BuildException {
		TableBuilder table=new TableBuilder();
		
		String[] headers= {};
		String[] data1= {"Data 1","Data 3"};
		String[] data= {"Data 1","Data 2","Data 3"};
		table.setHeaders(headers).addDataRow(data1).addDataRow(data1).addDataRow(data).build();
		
		//printHTMLToConsole(table.getHTMLLines());
	}
	
	@Test
	void testCas8() throws BuildException {
		TableBuilder table=new TableBuilder();
		
		String[] headers= {};
		String[] data1= {"Data 1","Data 3"};
		String[] data= {"Data 1","Data 2","Data 3"};
		table.setHeaders(headers).addDataRow(data).addDataRow(data1).addDataRow(data1).build();
		
		//printHTMLToConsole(table.getHTMLLines());
	}
	
	@Test
	void testCas9() throws BuildException {
		TableBuilder table=new TableBuilder();
		
		String[] headers= {"Header1","Header2"};
		String[] data1= {"Data 1","Data 3"};
		String[] data= {"Data 1","Data 2","Data 3"};
		table.setHeaders(headers).addDataRow(data1).addDataRow(data).addDataRow(data1).build();
		
		//printHTMLToConsole(table.getHTMLLines());
	}
	
	@Test
	void testCas10() throws BuildException {
		TableBuilder table=new TableBuilder();
		
		String[] headers= {"Header1","Header2"};
		String[] data1= {"Data 1","Data 3"};
		String[] data= {"Data 1","Data 2","Data 3"};
		String[][] dataToSet= {{"Static data1","Static data2"},{"Static data1","Static data2","Static data3"},{"Static data1","Static data2"}};
		
		table.setHeaders(headers).setDataRows(dataToSet).addDataRow(data1).addDataRow(data).addDataRow(data1).build();
		
		//printHTMLToConsole(table.getHTMLLines());
	}
	
	@Test
	void testCas11() throws BuildException {
		TableBuilder table=new TableBuilder();
		
		String[] headers= {"Header1","Header2","Header3"};
		String[] data1= {"Data 1","Data 3"};
		String[] data= {"Data 1","Data 2","Data 3"};
		String[][] dataToSet= {{"Static data1","Static data2"},{"Static data1","Static data2","Static data3"},{"Static data1","Static data2"}};
		
		table.setHeaders(headers).setDataRows(dataToSet).addDataRow(data1).addDataRow(data).addDataRow(data1).build();
		
		//printHTMLToConsole(table.getHTMLLines());
	}

}
