package com.gxyj.test.commons.handler;

public interface GenericWrap<T> {
	
	public  void init();

	public  void wrapBeforeDbOp(T paramT);

	public  void wrapAfterDbOp(T paramT);
}
