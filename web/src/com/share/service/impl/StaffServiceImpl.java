/**
 * 
 */
package com.share.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.share.model.Staff;
import com.share.service.StaffService;

/**
 * Service层实现类：Staff(operamasks-ui的试验)
 *
 * @author user email：ancai0729@gmail.com
 * @since 2012-11-29 下午2:53:06
 * @version 1.0
 */
@Service("staffService")
public class StaffServiceImpl implements StaffService {

	private static final Staff[] staffs = { new Staff(1, "小李", "xiaoli@163.com", "13754158965", 1, "广东省", "梅林关"), new Staff(2, "虹虹", "xiaohong@qq.com", "13754038965", 0, "广东省", "汕头市"), new Staff(3, "李莫", "limo@163.com", "13759876910", 0, "广东省", "梅州市"), new Staff(4, "江小小", "jxx@gmail.com", "13754159632", 0, "广东省", "揭阳市"), new Staff(5, "王强", "wq@apusic.com", "13941258966", 1, "广东省", "普宁市"), new Staff(6, "陈彬", "cb@163.com", "13754159999", 1, "广东省", "深圳市"), new Staff(7, "王丽", "lili@163.com", "13754178110", 0, "广东省", "广州市"), new Staff(8, "张旭", "zx@kingdee.com", "13754159968", 1, "", "北京市"), new Staff(9, "赵冰", "zb@sina.com", "13511158586", 1, "", "天津市"), new Staff(10, "孙小小", "sxx@163.com", "13754157452", 0, "", "香港"), new Staff(11, "诸明", "zm@163.com", "13754151025", 1, "", "澳门"), new Staff(12, "陈康永", "cky@qq.com", "13754116465", 1, "湖南省", "长沙"), new Staff(13, "王晓冰", "wxb@sohu.com", "13754151230", 0, "四川", "成都"), new Staff(14, "陈大卫", "dw@apusic.com", "13756358973", 1, "云南", "昆明"), new Staff(15, "蔡小翠", "cxc@163.com", "13754154563", 0, "福建", "福州"), new Staff(16, "林凡", "linfan@163.com", "13754151203", 1, "吉林", "长春"), new Staff(17, "慕容", "murong@163.com", "13754158987", 0, "山西", "太原") };

	@Override
	public List<Staff> query(int start, int limit) {
		// TODO Auto-generated method stub
		return Arrays.asList(staffs).subList(start, start + limit > staffs.length ? staffs.length : start + limit);
	}

	@Override
	public int getTotal() {
		// TODO Auto-generated method stub
		return staffs.length;
	}

}
