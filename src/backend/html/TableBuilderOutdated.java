package backend.html;

import java.util.ArrayList;
import java.util.List;

import exceptions.BuildException;

public class TableBuilderOutdated 
{	
	private int nColumns;
	private int nRows;
	private String[] headers;	
	private String[][] dataOnRows;
	
	private boolean builded;
	private ArrayList<String> buildedHTMLLines;
	
	private ArrayList<String[]> buildingData;
	
	public TableBuilderOutdated()
	{
		//Default empty table
		headers=new String[0];
		dataOnRows=new String[0][0];
		buildingData=new ArrayList<String[]>();
		
		builded=true;
		buildedHTMLLines=new ArrayList<String>();
	}
	
	public TableBuilderOutdated setHeaders(String[] headers)
	{
		this.headers=headers;
		
		builded=false;
		return this;
	}
	
	public TableBuilderOutdated addDataRow(String[] dataRow)
	{
		buildingData.add(dataRow);
		
		builded=false;
		return this;
	}
	
	public TableBuilderOutdated setDataRows(String[][] dataRows)
	{
		this.dataOnRows=dataRows;
		
		builded=false;
		return this;
	}
	
	public TableBuilderOutdated setStyle()
	{
		//TODO
		
		builded=false;
		return this;
	}
	
	public void build() throws BuildException
	{
		if(builded)
			return;
		
		//if added row by row
		if(dataOnRows.length==0)
		{
			if(buildingData.size()!=0)
			{
				dataOnRows=new String[buildingData.size()][];
				for(int i=0;i<buildingData.size();i++)
				{
					if(headers.length!=0 && buildingData.get(i).length>headers.length)
						throw new BuildException("Header size of "+headers.length+" is less than " + (i+1)+"-th row with size of " +buildingData.get(i).length);
					else
						dataOnRows[i]=buildingData.get(i);
				}
			}
		}
		
				
		nColumns=headers.length>0 ? headers.length : dataOnRows[0].length;
		

		for(int i=0;i<dataOnRows.length;i++)
			if(dataOnRows[i].length>nColumns)
				throw new BuildException("TODO");
		
		nRows=dataOnRows.length ;
		
		//creating html lines
		buildedHTMLLines.add("<table>");
		if(headers.length!=0)
		{
			buildedHTMLLines.add("<tr>");
			for(String header : headers)
				buildedHTMLLines.add("<th>"+header+"</th>");
			buildedHTMLLines.add("</tr>");
		}
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
	
	public List<String> getHTMLLines()
	{
		return buildedHTMLLines;
	}
	
	
	private static void printHTMLToConsole(List<String> html)
	{
		for(String line : html)
			System.out.println(line);
	}
	
	public static void main(String[] args) throws BuildException {
			TableBuilderOutdated table=new TableBuilderOutdated();
			
			String[] headers= {};
			String[] data1= {"Data 1","Data 3"};
			String[] data= {"Data 1","Data 2","Data 3"};
			table.setHeaders(headers).addDataRow(data1).addDataRow(data1).addDataRow(data).build();
			
			printHTMLToConsole(table.getHTMLLines());
		
	}
	
}
