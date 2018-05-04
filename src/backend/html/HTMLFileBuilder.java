package backend.html;

import java.util.ArrayList;
import java.util.List;

public class HTMLFileBuilder 
{
	//schematic variables
	private ArrayList<String> head;
	private ArrayList<String> body;
	
	private boolean builded;
	
	public HTMLFileBuilder()
	{
		head=new ArrayList<String>();
		body=new ArrayList<String>();
		
		htmlCode=new ArrayList<String>();
		
		builded=true;
	}
	
	public HTMLFileBuilder setStyleFromFile(String file)
	{
		head.add("<link rel=\"stylesheet\" href=\""+file+"\">");
		
		builded=false;
		return this;
	}
	
	public HTMLFileBuilder addLinesInBody(List<String> lines)
	{
		for(String line : lines)
			body.add(line);
		
		builded=false;
		return this;
	}
	
	public void build()
	{
		if(builded)
			return;
		
		htmlCode.add("<!DOCTYPE html>");
		htmlCode.add("<html>");
		
		if(head.size()!=0)
		{
			htmlCode.add("<head>");
			for(String headLine : head)
				htmlCode.add(headLine);
			htmlCode.add("</head>");
		}
		if(body.size()!=0)
		{
			htmlCode.add("<body>");
			for(String bodyLine : body)
				htmlCode.add(bodyLine);
			htmlCode.add("</body>");
		}
		
		htmlCode.add("</html>");
		
		builded=true;
	}
	
	public List<String> getHTML()
	{
		return htmlCode;
	}

	
	//build variables (for debug only)
	
	private ArrayList<String> htmlCode;
}
