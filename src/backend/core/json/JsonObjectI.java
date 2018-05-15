package backend.core.json;

import javax.json.JsonValue;

public interface JsonObjectI 
{
	public JsonValue toJson();
	
	public void setJson(JsonValue value);
}
