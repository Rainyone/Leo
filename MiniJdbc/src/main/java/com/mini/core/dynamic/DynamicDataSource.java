package com.mini.core.dynamic;

import java.util.Map;

public class DynamicDataSource extends AbstractRoutingDataSource{
	@Override
	protected Object determineCurrentLookupKey() {
		return DatabaseContextHolder.getCustomerType(); 
	}
}