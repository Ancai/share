/**
 * 
 */
package com.share.dao.impl;

import org.springframework.stereotype.Repository;

import com.share.dao.SectionDao;
import com.share.model.Section;

/**
 * Dao层实现类：栏目/板块/分类
 *
 * @author Ancai email：ancai0729@gmail.com
 * @since 2012-8-21 下午9:04:24
 * @version 1.0
 */
@Repository("sectionDao")
public class SectionDaoImp extends BaseDaoImpl<Section, Integer> implements SectionDao {

}
