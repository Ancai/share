/***
 * 利用struts2 标签    给复选框   回显定位
 */

//package com.vrv.ccp.action;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.annotation.Resource;
//
//import com.vrv.ccp.base.BaseAction;
//import com.vrv.ccp.bean.CheckItem;
//import com.vrv.ccp.bean.Json;
//import com.vrv.ccp.bean.PolicyAssign;
//import com.vrv.ccp.bean.Schedule;
//import com.vrv.ccp.domain.Policy;
//import com.vrv.ccp.model.ICheckTemplate;
//import com.vrv.ccp.service.CCPRuleService;
//import com.vrv.ccp.service.ICheckTemplateService;
//import com.vrv.ccp.utils.ActionUtil;
//import com.vrv.ccp.utils.CCPUtil;
//import com.vrv.ccp.utils.CoreUtil;
//
///** 
// *         说      明：“我要检查”模板
// *
// * @author 作      者：lac
// *		  E-mail: liangancai@vrvmail.com.cn 
// * @version V1.0
// *         创建时间：2013-1-29 下午02:09:53 
// */
//public class ICheckAction extends BaseAction {
//	private static final long serialVersionUID = 1L;
//	@Resource private ICheckTemplateService iCheckTemplateService;
//	@Resource private CCPRuleService ccpRuleService;
//	
//	/** 检查项 **/
//	private CheckItem checkItem;
//	/** CCP模型："我要检查"模块的模板 **/
//	private ICheckTemplate template;
//	/** 策略对象 **/
//	private Policy policy;
//	/** 执行计划 **/
//	private Schedule schedule;
//	/** 分配方式 **/
//	private PolicyAssign policyAssign;
//	
//	/**复选框值：无线设备**/
//    private String[] keysWireless;
//    /**复选框值：上网痕迹 》 检查位置**/
//    private String[] keysLocation;
//    /**复选框值：文件内容 》 文件后缀名**/
//    private String[] keysExtName;
//    /**复选框值：文件内容 》 盘符**/
//    private String[] keysDisk;
//	
//	/**
//	 * UI:检查结果》我要检查
//	 */
//	public String iCheckUI() {
//		try {
//			if (CoreUtil.notNull(template) && CoreUtil.notNull(template.getId())) {
//				template = iCheckTemplateService.getById(template);
//				checkItem = template.getCheckItem();
//				policyAssign = template.getPolicyAssign();
//				if (CoreUtil.notNull(checkItem.getNetDevice().getCheckType())) {
//					this.keysWireless = checkItem.getNetDevice().getCheckType().split(",");
//				}
//				if (CoreUtil.notNull(checkItem.getNetRecord().getCheckType())) {
//					this.keysLocation = checkItem.getNetRecord().getCheckType().split(",");
//				}
//				if (CoreUtil.notNull(checkItem.getFileCheck().getExtName())) {
//					this.keysExtName = checkItem.getFileCheck().getExtName().split(";");
//				}
//				if (CoreUtil.notNull(checkItem.getFileCheck().getCheckFolder())) {
//					this.keysDisk = checkItem.getFileCheck().getCheckFolder().split(";");
//				}
//				
//			}
//			 
//			getRequest().setAttribute("wirelessList", ccpRuleService.ruleInfo(CCPRuleService.KEY_WIRELESS_DEVICE));
//			getRequest().setAttribute("traceList", ccpRuleService.ruleInfo(CCPRuleService.KEY_NET_TRACE));
//			getRequest().setAttribute("extNameList", ccpRuleService.ruleInfo(CCPRuleService.KEY_EXT_NAME));
//			getRequest().setAttribute("diskList", ccpRuleService.ruleInfo(CCPRuleService.KEY_FILE_DISK));
//		} catch (Exception e) {
//			// TODO: handle exception
//			log.error("检查结果》我要检查,装载Action对象中的数据时报错！", e);
//		}
//		
//		return "iCheckUI";
//	}
//	
//	/**
//	 * 保存模板
//	 */
//	public void saveTemplate() {
//		try {
//			checkItem.getNetDevice().setCheckType(CCPUtil.array2Str(keysWireless)); //无线设备
//			checkItem.getNetRecord().setCheckType(CCPUtil.array2Str(keysLocation)); //上网痕迹》检查位置
//			checkItem.getFileCheck().setExtName(CCPUtil.array2Str(keysExtName, ";")); //文件内容》扩展名
//			checkItem.getFileCheck().setCheckFolder(CCPUtil.array2Str(keysDisk, ";"));//文件内容》盘符
//			template.setCheckItem(checkItem);
//			template.setPolicy(policy);
//			template.setSchedule(schedule);
//			template.setPolicyAssign(policyAssign);
//			template.setCreateUser(getNowUser().getAccount()+ "@" +getRequest().getRemoteAddr());
//			iCheckTemplateService.save(template);
//			this.msg = RESULT_SUCCESS;
//		} catch (Exception e) {
//			// TODO: handle exception
//			log.error("保存'我要检查'模板时报错！", e);
//			this.msg = RESULT_ERROR;
//		} finally {
//			print(this.msg);
//		}
//	}
//	
//	/**
//	 * 删除模板
//	 */
//	public void delTemplate() {
//		try {
//			iCheckTemplateService.delete(template);
//			this.msg = RESULT_SUCCESS;
//		} catch (Exception e) {
//			// TODO: handle exception
//			log.error("删除'我要检查'模板时报错！", e);
//			this.msg = RESULT_ERROR;
//		} finally {
//			print(this.msg);
//		}
//	}
//	
//	/**
//	 * 模板下拉框列表
//	 */
//	public void templateList() {
//		try {
//			List<Json> jsonList = new ArrayList<Json>();
//			List<ICheckTemplate> templateList = iCheckTemplateService.list();
//			for (ICheckTemplate iCheckTemplate : templateList) {
//				jsonList.add(new Json(iCheckTemplate.getId().toString(), iCheckTemplate.getName()));
//			}
//			this.msg = ActionUtil.jsonArray(jsonList);
//		} catch (Exception e) {
//			// TODO: handle exception
//			log.error("获取'我要检查'模板下拉框列表时报错！", e);
//			this.msg = RESULT_ERROR;
//		} finally {
//			print(this.msg);
//		}
//	}
//	
//	//属性方法
//	public CheckItem getCheckItem() {
//		return checkItem;
//	}
//	public void setCheckItem(CheckItem checkItem) {
//		this.checkItem = checkItem;
//	}
//
//	public ICheckTemplate getTemplate() {
//		return template;
//	}
//	public void setTemplate(ICheckTemplate template) {
//		this.template = template;
//	}
//
//	public Policy getPolicy() {
//		return policy;
//	}
//	public void setPolicy(Policy policy) {
//		this.policy = policy;
//	}
//
//	public Schedule getSchedule() {
//		return schedule;
//	}
//	public void setSchedule(Schedule schedule) {
//		this.schedule = schedule;
//	}
//
//	public PolicyAssign getPolicyAssign() {
//		return policyAssign;
//	}
//	public void setPolicyAssign(PolicyAssign policyAssign) {
//		this.policyAssign = policyAssign;
//	}
//	
//	public String[] getKeysWireless() {
//		return keysWireless;
//	}
//	public void setKeysWireless(String[] keysWireless) {
//		this.keysWireless = keysWireless;
//	}
//
//	public String[] getKeysLocation() {
//		return keysLocation;
//	}
//	public void setKeysLocation(String[] keysLocation) {
//		this.keysLocation = keysLocation;
//	}
//
//	public String[] getKeysExtName() {
//		return keysExtName;
//	}
//	public void setKeysExtName(String[] keysExtName) {
//		this.keysExtName = keysExtName;
//	}
//
//	public String[] getKeysDisk() {
//		return keysDisk;
//	}
//	public void setKeysDisk(String[] keysDisk) {
//		this.keysDisk = keysDisk;
//	}
//}
