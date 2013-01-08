/**
 * 
 */
package com.share.service;

import java.util.List;

import com.share.model.Staff;

/**
 * Service层接口：Staff(operamasks-ui的试验)
 *
 * @author user email：ancai0729@gmail.com
 * @since 2012-11-29 下午2:48:15
 * @version 1.0
 */
public interface StaffService {
	
	/**
	 * 查询员工
	 * 
	 * @param start 起始位置
	 * @param limit 每页大小
	 * @return List<Staff>
	 */
	public List<Staff> query(int start, int limit);

	/**
	 * 员工总数
	 * 
	 * @return int
	 */
	public int getTotal();
}
