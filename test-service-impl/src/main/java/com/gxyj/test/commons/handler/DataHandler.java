package com.gxyj.test.commons.handler;

import java.util.List;

public interface DataHandler<T> {

	public <S> T select(S paramS);

	public <S> List<T> list(List<S> paramList);
}
