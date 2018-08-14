package com.gxyj.test.commons.service;

public abstract class SimpleDataService<T> extends DataService<T, T> {
	
	protected T convert(T t) {
		return t;
	}
}
