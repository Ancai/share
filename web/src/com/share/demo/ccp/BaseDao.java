//package com.vrv.ccp.base;
//
//import java.util.List;
//import java.util.Map;
//
//import com.vrv.ccp.bean.GridData;
//import com.vrv.ccp.utils.HQLBuilderUtil;
//
///** 
// * @author glw
// *		  linwu_gao@163.com
// * @version V1.0
// *		   2011-12-27 上午09:31:08
// * @description	Dao层基本接口
// *
// * @param <T> T是传进来实体类
// */
//public interface BaseDao<T> {
//	/**
//	 * 保存实体entity
//	 * @param entity
//	 */
//	public void save(T entity);
//	
//	/**
//	 * 根据id,删除实体
//	 * @param id ID(String类型的)
//	 */
//	public void delete(String id);
//	
//	/**
//	 * 根据id,删除实体
//	 * @param id ID（Integer类型的）
//	 */
//	public void delete(Integer id);
//	
//	/**
//	 * 更新实体entity
//	 * @param entity
//	 */
//	public void update(T entity);
//	
//	/**
//	 * 保存    或   更新实体entity
//	 * @param entity
//	 */
//	public void saveOrUpdate(T entity);
//	
//	/**
//	 * 根据id,得到该实体类
//	 * @param id ID
//	 * @return entity实体类
//	 */
//	public T queryById(Integer id);
//
//	/**
//	 * 根据id,得到该实体类
//	 * @param id ID
//	 * @return entity实体类
//	 */
//	public T queryById(String id);
//	
//	/**
//	 * 根据给定的id数组ids，查找对应的实体集合
//	 * @param ids IDs
//	 * @return list<entity>
//	 */
//	public List<T> queryByIds(List<Integer> ids);
//
//	/**
//	 * 根据给定的id数组ids，查找对应的实体集合
//	 * @param ids IDs
//	 * @return list<entity>
//	 */
//	public List<T> queryByIds(String[] ids);
//	
//	/**
//	 * 查询所有
//	 * @return list<entity>
//	 */
//	public List<T> queryAll();
//	
//	/**
//	 * 根据给定的id数组ids，删除对应的实体集（注意：该方法不能用于级联操作）
//	 * @param ids IDs
//	 */
//	public void deleteByIds(String[] ids);
//	
//	/**
//	 * 查询分页信息
//	 * 
//	 * @param queryBuilder
//	 *            代表条件（过滤条件与排序条件）和参数列表
//	 * @param pageNum 第多少页
//	 * @param pageSize 每页显示条数
//	 * @return GridData<T> 
//	 */
//	public GridData<T> getPageView(HQLBuilderUtil queryBuilder, int pageNum,int pageSize);
//	
//	/**
//	 * 根据HQL语句查询（导出查询结果时用到）
//	 * @param queryBuilder 代表条件（过滤条件与排序条件）和参数列表
//	 * @return List<T>
//	 */
//	public List<T> queryByHQLBuilder(HQLBuilderUtil queryBuilder);
//	
//	/**
//	 * DB中是否为空
//	 * 
//	 * @return boolean 无：true，有：false
//	 */
//	public boolean haveData();
//	
//	/**
//	 * 根据hql查询
//	 * 
//	 * @param hqlBuilder Hql语句拼接器
//	 * @return List<Object[]>
//	 */
//	public List<Object[]> queryByHql(HQLBuilderUtil hqlBuilder);
//	
//	/**
//	 * 根据hql查询, 获得分页的记录
//	 * 
//	 * @param hqlBuilder Hql语句拼接器
//	 * @param pageNum 第多少页
//	 * @param pageSize 每页显示条数
//	 * @return List<Object[]>
//	 */
//	public List<Object[]> queryByHql(HQLBuilderUtil hqlBuilder, int pageNum, int pageSize);
//	
//	/**
//	 * 根据hql查询, 获得聚合的结果
//	 * 
//	 * @param hqlBuilder Hql语句拼接器
//	 * @return Integer
//	 */
//	public Long countByHql(HQLBuilderUtil hqlBuilder);
//	
//	/**
//	 * 根据SQL语句查询
//	 * 
//	 * @param sql
//	 * @return List<Object[]>
//	 */
//	public List<Object[]> queryBySql(String sql, Object... params);
//	
//	/**
//	 *  根据SQL语句查询
//	 * 
//	 * @param sql
//	 * @param paramMap 参数
//	 * @return List<Object[]>
//	 */
//	public List<Object[]> queryBySql(String sql, Map<String, Object> paramMap);
//	
//	/**
//	 * 查询全部，使用sql
//	 * 
//	 * @param sql 
//	 * @param params
//	 * @return List<T>
//	 */
//	public List<T> list(String sql, Object... params);
//	
//	/**
//	 * 查询全部，使用sql
//	 * 
//	 * @param sql
//	 * @param clazz 关联的模型类型
//	 * @param params
//	 * @return List<T>
//	 */
//	public List<T> list(String sql, Class<T> clazz, Object... params);
//	
//	/**
//	 * 查询对象，根据ID
//	 * 
//	 * @param sql
//	 * @param id
//	 * @return T
//	 */
//	public T getById(String sql, Integer id);
//	
//	/**
//	 * 查询对象，根据ID
//	 * 
//	 * @param sql
//	 * @param clazz 关联的模型类型
//	 * @param id
//	 * @return T
//	 */
//	public T getById(String sql, Class<T> clazz, Integer id);
//}
