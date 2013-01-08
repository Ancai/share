/**
 * 
 */
package com.share.dao.impl;
import org.springframework.stereotype.Repository;

import com.share.dao.ISiteDao;
import com.share.model.ISite;

/**
 * Dao层实现类：我的网址
 *
 * @author user email：ancai0729@gmail.com
 * @since 2012-10-23 上午10:22:10
 * @version 1.0
 */
@Repository("iSiteDao")
public class ISiteDaoImpl extends BaseDaoImpl<ISite, Long> implements ISiteDao {

}
