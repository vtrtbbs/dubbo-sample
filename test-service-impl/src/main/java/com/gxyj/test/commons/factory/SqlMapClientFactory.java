package com.gxyj.test.commons.factory;

import java.util.List;

import com.gxyj.test.commons.dao.CdoAndSmc;
import com.gxyj.test.commons.dao.CutDbObj;
import com.ibatis.sqlmap.client.SqlMapClient;

public class SqlMapClientFactory {
	private String tableNamePre = "";
	private List<SqlMapClient> sqlMapClientList_r;
	private List<SqlMapClient> sqlMapClientList_w;
	private CutDbFilter filter;

	public void setFilter(CutDbFilter filter) {
		this.filter = filter;
	}

	public void setTableNamePre(String tableNamePre) {
		this.tableNamePre = tableNamePre;
	}

	private <T extends CutDbObj> String getTableName(T o) {
		String postfix = filterTableName(o);
		return this.tableNamePre + postfix;
	}

	private <T extends CutDbObj> String filterTableName(T t) {
		return filterTableNameByKey(t.getCutDbObjId());
	}

	private String filterTableNameByKey(Object o) {
		return this.filter.getTableNamePostfix(o);
	}

	private SqlMapClient[] filterSqlMapClientsByKey(Object o) {
		int index = this.filter.getSqlMapClientIndex(o);

		if (index >= 0) {
			SqlMapClient[] sqlMapClients = new SqlMapClient[2];
			if (index < this.sqlMapClientList_r.size()) {
				sqlMapClients[0] = ((SqlMapClient) this.sqlMapClientList_r.get(index));
			}

			if (index < this.sqlMapClientList_w.size()) {
				sqlMapClients[1] = ((SqlMapClient) this.sqlMapClientList_w.get(index));
			}
			return sqlMapClients;
		}

		return null;
	}

	private <T extends CutDbObj> SqlMapClient[] getSqlMapClients(T t) {
		SqlMapClient[] sqlMapClients = filterSqlMapClientsByKey(t.getCutDbObjId());
		return sqlMapClients;
	}

	private SqlMapClient getSqlMapClient_w(SqlMapClient[] sqlMapClients) {
		if ((sqlMapClients != null) && (sqlMapClients.length >= 1)) {
			if (sqlMapClients.length == 1) {
				return sqlMapClients[0];
			}
			return sqlMapClients[1];
		}
		return null;
	}

	private SqlMapClient getSqlMapClient_r(SqlMapClient[] sqlMapClients) {
		if ((sqlMapClients != null) && (sqlMapClients.length >= 1)) {
			return sqlMapClients[0];
		}
		return null;
	}

	public <T> CdoAndSmc getCdoAndSmc(T t, boolean write, boolean force)
	  {
	    if (t != null)
	    {
	      CutDbObj cut = null;

	      if (t instanceof CutDbObj) {
	        cut = (CutDbObj)t;
	      } /*else if (!(force)) {
	        Object o = t;
	        cut = new t(this, o);
	      }*/

	      if (cut != null) {
	        cut.setTableName(getTableName(cut));

	        SqlMapClient[] sqlMapClients = getSqlMapClients(cut);

	        if (sqlMapClients != null)
	        {
	          CdoAndSmc ret = new CdoAndSmc();
	          ret.setCutDbObj(cut);

	          if (write)
	            ret.setSqlMapClient(getSqlMapClient_w(sqlMapClients));
	          else {
	            ret.setSqlMapClient(getSqlMapClient_r(sqlMapClients));
	          }

	          return ret;
	        }
	      }

	    }

	    return null;
	  }

	public void setSqlMapClientList_r(List<SqlMapClient> sqlMapClientList_r) {
		this.sqlMapClientList_r = sqlMapClientList_r;
	}

	public void setSqlMapClientList_w(List<SqlMapClient> sqlMapClientList_w) {
		this.sqlMapClientList_w = sqlMapClientList_w;
	}

	public void setSqlMapClientList(List<SqlMapClient> sqlMapClientList) {
		setSqlMapClientList_w(sqlMapClientList);
		setSqlMapClientList_r(sqlMapClientList);
	}

}
