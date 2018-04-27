package backend.core.json;

import java.util.ArrayList;

import javax.json.JsonValue;
import javax.json.JsonValue.ValueType;

public class JSONSchematic 
{
	private ArrayList<JSONSchematic> children;
	private JsonValue.ValueType jsonType;
	private String referenceName;
	
	private JSONSchematic(String referenceName,JsonValue.ValueType jsonType)
	{
		children=new ArrayList<JSONSchematic>();
		this.jsonType=jsonType;
		this.referenceName=referenceName;
	}
	
	public JsonValue.ValueType getValueType()
	{
		return jsonType;
	}
	
	public JSONSchematic getArrayChild()
	{
		return children.get(0);
	}
	
	public ArrayList<JSONSchematic> getChildren() 
	{
		return children;
	}

	public String getReferenceName() 
	{
		return referenceName;
	}

	public static JSONSchematic addArray(String referenceName,JSONSchematic elementSchematic)
	{
		JSONSchematic out=new JSONSchematic(referenceName,ValueType.ARRAY);
		out.children.add(elementSchematic);
		return out;
	}
	
	public static JSONSchematic addArray(JSONSchematic elementSchematic)
	{
		return addArray(null,elementSchematic);
	}
	
	
	public static JSONSchematic addObject(String referenceName,JSONSchematic... objectSchematic)
	{
		JSONSchematic out=new JSONSchematic(referenceName,ValueType.OBJECT);
		
		for(JSONSchematic element : objectSchematic)
			out.children.add(element);
		return out;
	}
	
	public static JSONSchematic addObject(JSONSchematic... objectSchematic)
	{
		return addObject(null,objectSchematic);
	}
	
	public static JSONSchematic addNumber(String referenceName)
	{
		JSONSchematic out=new JSONSchematic(referenceName,ValueType.NUMBER);
		return out;
	}
	
	public static JSONSchematic addNumber()
	{
		JSONSchematic out=new JSONSchematic(null,ValueType.NUMBER);
		return out;
	}
	
	public static JSONSchematic addString(String referenceName)
	{
		JSONSchematic out=new JSONSchematic(referenceName,ValueType.STRING);
		return out;
	}
	
	public static JSONSchematic addString()
	{
		JSONSchematic out=new JSONSchematic(null,ValueType.STRING);
		return out;
	}
	
	public void print()
	{
		recursivePrinting(0,1);
	}
	
	private void recursivePrinting(int spacing,int index)
	{
		for(int i=0;i<spacing;i++)
			System.out.print(" ");
		System.out.print(index+"."+jsonType);
		if(referenceName!=null)
			System.out.print(" "+referenceName);
		System.out.println();
		
		int i=1;
		for(JSONSchematic element : children)
		{
			element.recursivePrinting(spacing+1,i);i++;
		}
	}
	
	public static void main(String[] args) {
		JSONSchematic build=JSONSchematic.addArray(
				JSONSchematic.addArray(
						JSONSchematic.addObject(
								JSONSchematic.addNumber(),
								JSONSchematic.addArray(
										JSONSchematic.addObject(
												JSONSchematic.addNumber(),
												JSONSchematic.addString()
												)
										),
								JSONSchematic.addNumber()
								)
						)
				);
		
		build.print();
		
		System.out.println("--------------------------------------");
		System.out.println();
		
		JSONSchematic repoNames=JSONSchematic.addArray(
				JSONSchematic.addObject(
						JSONSchematic.addNumber("id"),
						JSONSchematic.addString("name")
						)
				);
		
		repoNames.print();
		
	}
}
