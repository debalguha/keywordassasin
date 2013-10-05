package org.google.api.services.factory;

import java.util.Map;


public abstract class AbstractServiceWraper implements ServiceWrapper {
	public abstract void initWrapper() throws Exception;
	public abstract boolean isInitialized();
	@Override
	public Map<?, ?> serviceCall(Map<String, Object> paraMap) throws Exception {
		if(!isInitialized())
			initWrapper();
		return handleParameterInternal(paraMap);
	}
	public abstract Map<?, ?> handleParameterInternal(Map<String, Object> paraMap) throws Exception;
}
