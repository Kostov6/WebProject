package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import backend.html.HTMLFileBuilder;
import backend.html.TableBuilder;
import exceptions.BuildException;

class HTMLFileBuilderTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@Test
	void test() throws BuildException {
		HTMLFileBuilder htmlFile=new HTMLFileBuilder();
		
		htmlFile.setStyleFromFile("style.css");
		
		TableBuilder table=new TableBuilder();
		String[] headers= {"Header1","Header2","Header3"};
		table.setHeaders(headers)
			.addDataRow(new String[] {"Data 1.1","Data 1.2","Data 1.3"})
			.addDataRow(new String[] {"Data 2.1","Data 2.2","Data 2.3"})
			.addDataRow(new String[] {"Data 3.1","Data 3.2","Data 3.3"})
			.build();
		
		htmlFile.addLinesInBody(table.getHTMLLines());
	
		htmlFile.build();
		
		for(String line : htmlFile.getHTML())
			System.out.println(line);
	}

	
}
