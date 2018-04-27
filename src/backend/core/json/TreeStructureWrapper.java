package backend.core.json;

import java.util.List;

public interface TreeStructureWrapper {
	
	List<Object> getChildren();
	
	Object getParent();
}
