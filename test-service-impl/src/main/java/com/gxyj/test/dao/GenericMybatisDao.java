package com.gxyj.test.dao;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.gxyj.test.commons.dao.GenericsUtils;
import com.gxyj.test.commons.dao.Page;
import com.gxyj.test.commons.dao.PageCondition;


@Repository
public abstract class GenericMybatisDao<T, PK extends Serializable>  implements BaseGenericDao<T, PK> {
	private static final Logger loger = LoggerFactory.getLogger(GenericMybatisDao.class);
	
	private static final String SQLID_INSERT       = "insert";
	private static final String SQLID_INSERT_BATCH = "insertBatch";
	private static final String SQLID_UPDATE       = "update";
	private static final String SQLID_UPDATE_PARAM = "updateParam";
	private static final String SQLID_DELETE       = "delete";
	private static final String SQLID_DELETE_PARAM = "deleteParam";
	private static final String SQLID_TRUNCATE     = "truncate";
	private static final String SQLID_SELECT       = "select";
	private static final String SQLID_SELECT_PK    = "selectPk";
	private static final String SQLID_SELECT_PARAM = "selectParam";
	private static final String SQLID_COUNT        = "count";
	private static final String SQLID_COUNT_PARAM  = "countParam";		

	public String getSqlmapNamespace() {
		Class<T> clazz = (Class)GenericsUtils.getSuperClassGenricType(this.getClass());
		String simpleName = clazz.getSimpleName();
		return simpleName;
	}	

	@Resource(name = "writeSqlSessionTemplate")
	private SqlSessionTemplate writeSqlSessionTemplate;

	@Resource(name = "readSqlSessionTemplate")
	private SqlSessionTemplate readSqlSessionTemplate;	
	

	@Override
	public void insert(T entity) {
		writeSqlSessionTemplate.insert(getSqlmapNamespace() + "." + SQLID_INSERT,entity);  
	}

	@Override
	public int update(T entity) {
		 return writeSqlSessionTemplate.update(getSqlmapNamespace() + "." + SQLID_UPDATE, entity);
	}

	@Override
	public int delete(PK primaryKey) {
		int rows = writeSqlSessionTemplate.delete(getSqlmapNamespace() + "." + SQLID_DELETE, primaryKey);        
        return rows;  
	}	

	@Override
	public T get(PK primaryKey) {
		return readSqlSessionTemplate.selectOne(getSqlmapNamespace() + "." + SQLID_SELECT_PK, primaryKey);
	}

	@Override
	public int truncate() {
		int rows = 0;
        rows = writeSqlSessionTemplate.delete(getSqlmapNamespace() + "." + SQLID_TRUNCATE);
        return rows;  
	}

	@Override
	public int count() {
		int result = 0;
		result = readSqlSessionTemplate.selectOne(getSqlmapNamespace() + "." + SQLID_COUNT);
		return result;
	}	

	@Override
	public List<T> select() {
		 return readSqlSessionTemplate.selectList(getSqlmapNamespace() + "." + SQLID_SELECT); 
	}

	@Override
	public int  batchInsert(List<T> list) {
		return writeSqlSessionTemplate.insert(getSqlmapNamespace() + "." + SQLID_INSERT_BATCH,list);  
	}

	@Override
	public int batchUpdate(List<T> list) {
		int rows = 0;       
        for (T t : list) {  
             rows = rows + writeSqlSessionTemplate.update(getSqlmapNamespace() + "." + SQLID_UPDATE, t);  
        }        
        return rows;  
	}

	@Override
	public int batchDelete(List<PK> list) {
		return 0;
	}
	
	public Page<T> pagedQuery(PageCondition condition) {
		return pagedQuery(condition,SQLID_COUNT_PARAM,SQLID_SELECT_PARAM);
	}
	
	public  <T> Page<T> pagedQuery(PageCondition condition, String countSqlId,String pageQuerySqlId) {
		int curPage = condition.getCurPage();
		if (curPage < 1) {
			curPage = 1;
		}
		Integer totalCount = (Integer)readSqlSessionTemplate.selectOne(getSqlmapNamespace() + "." + countSqlId, condition);
		if ((totalCount == null) || (totalCount.intValue() == 0)) {
			return new Page<T>();
		}
		int totalPageCount = 0;
		int fetchNum = condition.getFetchNum();
		if (fetchNum > 0) {
			totalPageCount = (totalCount.intValue() - 1) / fetchNum + 1;
			if (totalPageCount < curPage) {
				curPage = totalPageCount;
				condition.setCurPage(curPage);
			}
		}
		List<T> list = readSqlSessionTemplate.selectList(getSqlmapNamespace() + "." + pageQuerySqlId, condition);
		return new Page<T>(condition, totalCount.intValue(), list);
	}
	
	public void printLog(String sqlId,Object param,boolean isWrite) {
		Configuration config = readSqlSessionTemplate.getConfiguration();
		if (isWrite) {
			config = writeSqlSessionTemplate.getConfiguration();
		}
		MappedStatement mappedStatement = config.getMappedStatement(sqlId);		
        //param为传入到sql语句中的参数  
        BoundSql boundSql = mappedStatement.getBoundSql(param);  
        //得到sql语句  
        String sql = boundSql.getSql().trim();  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        System.out.println("info-sql: "+sdf.format(new Date()) + "  "+sql);  
	}
	
	
	

}
