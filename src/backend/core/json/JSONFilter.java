package backend.core.json;

import java.text.ParseException;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonString;
import javax.json.JsonValue;

public class JSONFilter 
{
	
	public static JsonValue filterJson(JsonValue json,JSONSchematic schematic) throws ParseException
	{
		return filterJson(json,schematic,1);
	}
	
	private static JsonValue filterJson(JsonValue json,JSONSchematic schematic,int level) throws ParseException
	{
		JsonValue.ValueType valueType=schematic.getValueType();
		if(json.getValueType()==JsonValue.ValueType.NULL)
		{
			return JsonValue.NULL;
		}
		if(valueType!=json.getValueType())
			throw new ParseException("JSONFilter encountered parse difference when parsing "+valueType+" to "+json.getValueType()+" with name "+schematic.getReferenceName(),level);
		
		JsonValue out=null;
		
		switch(valueType)
		{
		case ARRAY:
			out=filterJsonArray((JsonArray) json,schematic,level);
			break;
		case NUMBER:
			out=filterJsonNumber((JsonNumber) json,schematic,level);
			break;
		case OBJECT:
			out=filterJsonObject((JsonObject) json,schematic,level);
			break;
		case STRING:
			out=filterJsonString((JsonString) json,schematic,level);
			break;
		default:
			break;
		}
		
		return out;
	}
	
	private static JsonArray filterJsonArray(JsonArray jsonArray,JSONSchematic schematic,int level) throws ParseException
	{
		JsonValue.ValueType valueType=schematic.getValueType();
		if(valueType!=jsonArray.getValueType())
			throw new ParseException("JSONFilter encountered parse difference when parsing "+valueType+" with name "+schematic.getReferenceName(),level);
		
		JSONSchematic arrayElement=schematic.getArrayChild();
		JsonArrayBuilder builder=Json.createArrayBuilder();
		
		for(JsonValue arrayValue : jsonArray)
			builder.add(filterJson(arrayValue,arrayElement,level+1));
		
		return builder.build();
	}
	
	private static JsonObject filterJsonObject(JsonObject jsonObject,JSONSchematic schematic,int level) throws ParseException
	{
		JsonValue.ValueType valueType=schematic.getValueType();
		if(valueType!=jsonObject.getValueType())
			throw new ParseException("JSONFilter encountered parse difference when parsing "+valueType+" with name "+schematic.getReferenceName(),level);
		
		ArrayList<JSONSchematic> objectElements=schematic.getChildren();
		JsonObjectBuilder builder=Json.createObjectBuilder();
		
		for(JSONSchematic objectElement : objectElements)
			builder.add(objectElement.getReferenceName(),filterJson(jsonObject.get(objectElement.getReferenceName()),objectElement,level+1));
		
		return builder.build();
	}
	
	private static JsonString filterJsonString(JsonString jsonString,JSONSchematic schematic,int level) throws ParseException
	{
		JsonValue.ValueType valueType=schematic.getValueType();
		if(valueType!=jsonString.getValueType())
			throw new ParseException("JSONFilter encountered parse difference when parsing "+valueType+" with name "+schematic.getReferenceName(),level);
		
		return jsonString;
	}
	
	private static JsonNumber filterJsonNumber(JsonNumber jsonNumber,JSONSchematic schematic,int level) throws ParseException
	{
		JsonValue.ValueType valueType=schematic.getValueType();
		if(valueType!=jsonNumber.getValueType())
			throw new ParseException("JSONFilter encountered parse difference when parsing "+valueType+" with name "+schematic.getReferenceName(),level);
		
		return jsonNumber;
	}
}
