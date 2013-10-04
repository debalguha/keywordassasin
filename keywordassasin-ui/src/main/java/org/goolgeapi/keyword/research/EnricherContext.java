package org.goolgeapi.keyword.research;

import java.util.HashMap;
import java.util.Map;

public class EnricherContext {
	private Map<String, Object> params;
	public EnricherContext(){
		params = new HashMap<String, Object>();
	}
	public void addParam(String name, Object value){
		params.put(name, value);
	}
	public Object getParamValue(String name){
		return params.get(name);
	}
}
