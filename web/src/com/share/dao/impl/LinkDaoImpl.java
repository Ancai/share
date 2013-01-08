/**
 * 
 */
package com.share.dao.impl;

import org.springframework.stereotype.Repository;

import com.share.dao.LinkDao;
import com.share.model.Link;

/**
 * Dao层实现类：链接
 *
 * @author Ancai email：ancai0729@gmail.com
 * @since 2012-8-21 下午9:06:23
 * @version 1.0
 */
@Repository("linkDao")
public class LinkDaoImpl extends BaseDaoImpl<Link, Integer> implements LinkDao {

}
