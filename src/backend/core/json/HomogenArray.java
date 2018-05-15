package backend.core.json;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonValue;
import javax.json.JsonValue.ValueType;

public class HomogenArray 
{
	private JsonArrayBuilder builder;
	private ValueType type;
	
	public HomogenArray()
	{
		builder=Json.createArrayBuilder();
		type=null;
	}
	
	public ValueType getValueType()
	{
		return type;
	}
	
	public boolean add(JsonValue value)
	{
		if(value==null)
			return false;
		
		if(type==null)
			type=value.getValueType();
		else if(!type.equals(value.getValueType()))
			return false;
			
		builder.add(value);
		
		return true;
	}
	
	public JsonArray buildArray()
	{
		return builder.build();
	}
	
	public boolean set(int index,JsonValue value)
	{
		
		if(!value.getValueType().equals(type))
			return false;
		builder.set(index, value);
		
		return true;
	}
}
