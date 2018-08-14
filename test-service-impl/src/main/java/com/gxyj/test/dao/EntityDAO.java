package com.gxyj.test.dao;

import java.io.Serializable;

public interface EntityDAO<T, PK extends Serializable> {
	
	/** 
     * 插入一个实体（在数据库INSERT一条记录） 
     * @param entity 实体对象 
     */  
    void insert(T entity);  
      
    /** 
     * 修改一个实体对象（UPDATE一条记录） 
     * @param entity 实体对象 
     * @return 修改的对象个数，正常情况=1 
     */  
    int update(T entity);  
      
         
    /** 
     * 按主键删除记录 
     * @param primaryKey 主键对象 
     * @return 删除的对象个数，正常情况=1 
     */  
    int delete(PK primaryKey);  
  
      
    /** 
     * 按主键取记录 
     * @param primaryKey 主键值 
     * @return 记录实体对象，如果没有符合主键条件的记录，则返回null 
     */  
    T get(PK primaryKey);  
  
    /** 
     * 按主键取记录 
     * @param primaryKey 主键值 
     * @return 记录实体对象，如果没有符合主键条件的记录，则 throw DataAccessException 
     */  
    T load(PK primaryKey) ;  
  
    
}
