package backend.core.json;

import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import javax.json.JsonValue.ValueType;

public class JSONCollector 
{
	public static ArrayList<JsonObject> collectDataFrom(JsonValue json)
	{
		ArrayList<JsonObject> array=new ArrayList<JsonObject>();
		collectDataFrom(json,array);
		return array;
	}
	
	private static void collectDataFrom(JsonValue json,ArrayList<JsonObject> array)
	{
		
		ValueType valueType=json.getValueType();
		switch(valueType)
		{
		case ARRAY:
			JsonArray converted=(JsonArray) json;
			for(JsonValue value : converted)
				collectDataFrom(value,array);
			break;
			
		case OBJECT:
			JsonObject collected=collectObject(json);
			array.add(collected);
			break;

		default:
			//TODO Not tested yet
			break;
		
		}
	}
	
	private static JsonObject collectObject(JsonValue json)
	{
		ArrayList<JsonDataRow> collectedData=new ArrayList<JsonDataRow>();
		collectDataFrom("",json,collectedData);
		
		JsonObjectBuilder builder=Json.createObjectBuilder();
		
		for(JsonDataRow row : collectedData)
			builder.add(row.name, row.value);
		
		return builder.build();
		
	}
	
	private static void collectDataFrom(String pathName,JsonValue jsonNode,ArrayList<JsonDataRow> collecting)
	{
		ValueType valueType=jsonNode.getValueType();

		switch(valueType)
		{
		case ARRAY:
			JsonArray convertedArray;
			convertedArray=(JsonArray) jsonNode;
			for(JsonValue element : convertedArray)
				collectDataFrom(pathName,element,collecting);
			break;
		case OBJECT:
			JsonObject convertedObject;
			convertedObject=(JsonObject) jsonNode;
			Set<String> keys=convertedObject.keySet();
			for(String key : keys)
			{
				collectDataFrom(pathName+"."+key,convertedObject.get(key),collecting);
			}
			break;
		/*case NUMBER:
			collecting.add(new JsonDataRow(pathName,jsonNode.toString()));
			break;
		case STRING:
			break;
			
		case TRUE:
			collecting.add(new JsonDataRow(pathName,"true"));
			break;
		case FALSE:
			collecting.add(new JsonDataRow(pathName,"false"));
			break;
		case NULL:
			collecting.add(new JsonDataRow(pathName,"null"));
			break;*/
		default:

			collecting.add(new JsonDataRow(pathName,jsonNode.toString()));
			break;
		}
	}
	
	private static class JsonDataRow
	{
		public JsonDataRow(String name, String value) {
			this.name = name;
			this.value = value;
		}
		
		@Override
		public boolean equals(Object o)
		{
			//if(!(o instanceof JsonDataRow))
			//	return false;
			JsonDataRow oCon=(JsonDataRow) o;
			return name.equals(oCon.name);
		}
		
		private String name;
		private String value;
	}
}
