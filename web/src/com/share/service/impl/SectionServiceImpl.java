/**
 * 
 */
package com.share.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.share.dao.SectionDao;
import com.share.model.Section;
import com.share.service.SectionService;

/**
 * Service层实现类：栏目/板块/分类
 *
 * @author user email：ancai0729@gmail.com
 * @since 2012-8-22 下午3:43:25
 * @version 1.0
 */
@Service("sectionService")
public class SectionServiceImpl extends BaseServiceImpl<Section, Integer>
		implements SectionService {
	@SuppressWarnings("unused")
	private SectionDao sectionDao;
	
	@Resource
	public void setBaseDao(SectionDao sectionDao) {
		super.setBaseDao(sectionDao);
		this.sectionDao = sectionDao;
	}
}
