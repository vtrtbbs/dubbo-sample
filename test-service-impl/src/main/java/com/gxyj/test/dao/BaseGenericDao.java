package com.gxyj.test.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.dao.DataAccessException;

public  interface BaseGenericDao<T, PK extends Serializable> {

	/**
	 * 插入一个实体（在数据库INSERT一条记录）
	 * 
	 * @param entity
	 *            实体对象
	 */
	void insert(T entity);

	/**
	 * 修改一个实体对象（UPDATE一条记录）
	 * 
	 * @param entity
	 *            实体对象
	 * @return 修改的对象个数，正常情况=1
	 */
	int update(T entity);

	/**
	 * 按主键删除记录
	 * 
	 * @param primaryKey
	 *            主键对象
	 * @return 删除的对象个数，正常情况=1
	 */
	int delete(PK primaryKey);

	/**
	 * 清空表，比delete具有更高的效率，而且是从数据库中物理删除（delete是逻辑删除，被删除的记录依然占有空间）
	 * <p>
	 * <strong>此方法一定要慎用！</strong>
	 * </p>
	 * 
	 * @return
	 */
	int truncate();

	/**
	 * 查询整表总记录数
	 * 
	 * @return 整表总记录数
	 */
	int count();

	/**
	 * 按主键取记录
	 * 
	 * @param primaryKey
	 *            主键值
	 * @return 记录实体对象，如果没有符合主键条件的记录，则返回null
	 */
	T get(PK primaryKey);


	/**
	 * 取全部记录
	 * 
	 * @return 全部记录实体对象的List
	 */
	List<T> select();

	/**
	 * 批量插入
	 * 
	 * @param list
	 */
	int batchInsert(final List<T> list);

	/**
	 * 批量修改
	 * 
	 * @param list
	 */
	int batchUpdate(final List<T> list);

	/**
	 * 批量删除
	 * 
	 * @param list
	 */
	int batchDelete(final List<PK> list);
}
