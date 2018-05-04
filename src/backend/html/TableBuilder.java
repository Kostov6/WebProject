package backend.html;

import java.util.ArrayList;
import java.util.List;

import exceptions.BuildException;

public class TableBuilder {

	//schematic variables
	private String[] schematicHeaders;
	private String[][] schematicDataSet;
	private ArrayList<String[]> schematicDataArray;

	private boolean builded;
	
	public TableBuilder()
	{
		//Initializing list
		schematicDataArray=new ArrayList<String[]>();
		buildedHTMLLines=new ArrayList<String>();
		
		builded=true;
	}
	
	public TableBuilder setHeaders(String[] headers)
	{
		schematicHeaders=headers;
		
		builded=false;
		return this;
	}
	
	public TableBuilder setDataRows(String[][] dataRows)
	{
		schematicDataSet=dataRows;
		
		builded=false;
		return this;
	}
	
	/*
	public TableBuilder setStyle(String fileName)
	{
		style=fileName;
		
		builded=false;
		return this;
	}
	*/
	
	public TableBuilder addDataRow(String[] dataRow)
	{
		schematicDataArray.add(dataRow);
		
		builded=false;
		return this;
	}

	public void build() throws BuildException
	{
		if(builded)
			return;
		
		if(!isNullOrEmpty(schematicHeaders))
		{
			nColumns=schematicHeaders.length;
			headers=schematicHeaders;
		}
		else
		{
			if(!isNullOrEmpty(schematicDataSet))
				nColumns=dataOnRows[0].length;
			else if(schematicDataArray.size()!=0)
				nColumns=schematicDataArray.get(0).length;
			
			headers=null;
		}
		
		if(!isNullOrEmpty(schematicDataSet))
			dataOnRows=schematicDataSet;
		else if(schematicDataArray.size()!=0)
		{
			dataOnRows=new String[schematicDataArray.size()][];
			for(int i=0;i<schematicDataArray.size();i++)
				dataOnRows[i]=schematicDataArray.get(i);
		}
		
		//check for validation
		if(dataOnRows!=null)
			for(String[] row : dataOnRows)
				if(row.length>nColumns)
					throw new BuildException("There is a column with size "+ row.length+". The column limit is "+nColumns);
		
		nRows=0;
		if(dataOnRows!=null)
			nRows=dataOnRows.length;
		
		//creating html lines
		buildedHTMLLines.add("<table>");
		if(headers!=null)
		{
			buildedHTMLLines.add("<tr>");
			for(String header : headers)
				buildedHTMLLines.add("<th>"+header+"</th>");
			buildedHTMLLines.add("</tr>");
		}
		if(dataOnRows!=null)
			for(String[] row : dataOnRows)
			{
				buildedHTMLLines.add("<tr>");
				for(String rowElement : row)
					buildedHTMLLines.add("<td>"+rowElement+"</td>");
				
				buildedHTMLLines.add("</tr>");
				
			}
		buildedHTMLLines.add("</table>");
		
		builded=true;		
		
	}
	
	private boolean isNullOrEmpty(String[] s)
	{
		return s==null || s.length==0;
	}
	
	private boolean isNullOrEmpty(String[][] s)
	{
		return s==null || s.length==0;
	}
	
	public List<String> getHTMLLines()
	{
		return buildedHTMLLines;
	}
	
	//build variables(for debug only)
	
	private int nColumns;
	private int nRows;
	
	private String style;
	
	private String[] headers;	
	private String[][] dataOnRows;
	private ArrayList<String> buildedHTMLLines;
}

