package com.gxyj.test.commons.factory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.gxyj.test.commons.dao.CdoAndSmc;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.engine.impl.SqlMapClientImpl;

public class SqlMapClientTemplateEx implements ISqlMapClientTemplate {

	private SqlMapClientFactory sqlMapClientFactory;

	public <T> T insert(String id, T pojo) {
		CdoAndSmc r = this.sqlMapClientFactory.getCdoAndSmc(pojo, true, true);
		if (r != null) {
			try {
				r.getSqlMapClient().insert(id, r.getCutDbObj());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	public <T> int delete(String id, T obj) {
		CdoAndSmc r = this.sqlMapClientFactory.getCdoAndSmc(obj, true, false);

		if (r != null) {
			try {
				return r.getSqlMapClient().delete(id, r.getCutDbObj());
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return 0;
	}

	public <T, U> T queryForObject(String id, U obj) {
		CdoAndSmc r = this.sqlMapClientFactory.getCdoAndSmc(obj, false, false);

		if (r != null) {
			try {
				return (T) r.getSqlMapClient().queryForObject(id, r.getCutDbObj());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	public <T, U> List<T> queryForList(String id, U obj) {
		if (obj != null) {
			Object o;
			List subret;
			List ret = new ArrayList();

			if (obj instanceof List) {
				List list = (List) obj;

				for (Iterator localIterator = list.iterator(); localIterator.hasNext();) {
					o = localIterator.next();
					subret = queryForList(id, o);

					if (subret != null)
						ret.addAll(subret);
				}
			} else {
				Object localObject1;
				if (obj instanceof Object[]) {
					Object[] list = (Object[]) obj;

					Object[] arrayOfObject1 = list;
					localObject1 = 0;
					
					/*for (int ss = arrayOfObject1.length; localObject1 < ss; ++localObject1) {
						o = arrayOfObject1[localObject1];
						List subret = queryForList(id, o);

						if (subret != null)
							ret.addAll(subret);
					}*/
				} else if (obj instanceof Collection) {
					Collection coll = (Collection) obj;

					for (localObject1 = coll.iterator(); ((Iterator) localObject1).hasNext();) {
						o = ((Iterator) localObject1).next();
						subret = queryForList(id, o);

						if (subret != null)
							ret.addAll(subret);
					}
				} else {
					CdoAndSmc r = this.sqlMapClientFactory.getCdoAndSmc(obj, false, false);

					if (r != null) {
						try {
							ret = r.getSqlMapClient().queryForList(id, r.getCutDbObj());
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}

			}

			return ret;
		}

		return ((List<T>) null);
	}

	public <U> int update(String id, U obj) {
		CdoAndSmc r = this.sqlMapClientFactory.getCdoAndSmc(obj, false, true);

		if (r != null) {
			try {
				return r.getSqlMapClient().update(id, r.getCutDbObj());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return 0;
	}

	public <T> T queryForObject(String id) {
		return null;
	}

	public int update(String id) {
		return 0;
	}

	public <T> List<T> queryForList(String id) {
		return null;
	}

	public int delete(String id) {
		return 0;
	}

	public void setSqlMapClientFactory(SqlMapClientFactory sqlMapClientFactory) {
		this.sqlMapClientFactory = sqlMapClientFactory;
	}

	public <S> void batch(String statementId, List<S> list) {
		
	}

}
