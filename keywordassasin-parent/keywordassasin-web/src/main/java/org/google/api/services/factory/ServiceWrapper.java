package org.google.api.services.factory;

import java.util.Map;

public interface ServiceWrapper {
	public enum REQ_TOKEN {
		EMAIL, LANG_CODE, LOC_CODE;
	}
	public Map<?, ?> serviceCall(Map<String, Object> paraMap) throws Exception;
}
