//package com.vrv.ccp.aop;
//
//import java.sql.Timestamp;
//import java.text.SimpleDateFormat;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.annotation.Resource;
//
//import org.apache.commons.lang.StringUtils;
//import com.vrv.ccp.base.SystemStatics;
//import com.vrv.ccp.bean.DeviceBean;
//import com.vrv.ccp.bean.Encryption;
//import com.vrv.ccp.dao.EncryptionDao;
//import com.vrv.ccp.domain.Policy;
//import com.vrv.ccp.enums.EncryptModel;
//import com.vrv.ccp.utils.ComEdpCryptUtil;
//import com.vrv.ccp.utils.CoreUtil;
//import com.vrv.ccp.utils.DateTypeConverterUtil;
//
///** 
// *         工厂类：专门处理加解密
// *
// * @author 作      者：lac
// *		  E-mail: liangancai@vrvmail.com.cn 
// * @version V1.0
// *         创建时间：2013-2-4 下午01:51:26 
// */
//public class EncryptFactory {
//	/** 加密表、字段  **/
//	private static Map<String, Encryption> encInfoMap;
//	/** 加密的密钥 **/
//	private static String encKey;
//	@Resource private EncryptionDao encDao;
//	
//	/**
//	 * 加密
//	 * 
//	 * @param modelEnum 
//	 * @param entity 实体对象
//	 * @return T 
//	 */
//	@SuppressWarnings("unchecked")
//	public <T> T encrypt(EncryptModel modelEnum, T entity) {
//		T t = null;
//		switch (modelEnum) {
//			case Policy:
//				t = (T) encryptPolicy(entity);
//				break;
//			case Device:
//				break;
//		}
//		
//		return t;
//	}
//	
//	/**
//	 * 解密
//	 * 
//	 * @param model
//	 * @param entity 实体对象
//	 * @return T
//	 */
//	@SuppressWarnings("unchecked")
//	public <T> T decrypt(EncryptModel modelEnum, T entity) {
//		T t = null;
//		switch (modelEnum) {
//			case Policy:
//				t = (T) dencryptPolicy(entity);
//				break;
//			case Device:
//				t = (T) dencryptDevice(entity);
//				break;
//		}
//		
//		return t;
//	}
//	
//	private Map<String, Encryption> getEncInfoMap() {
//		if (CoreUtil.isNull(encInfoMap)) {
//			encInfoMap = new HashMap<String, Encryption>();
//			List<Encryption> list = encDao.getEncInfo();
//			for (Encryption bean : list) {
//				encInfoMap.put(bean.getTableName(), bean);
//			}
//		}
//		
//		return encInfoMap;
//	}
//	
//	private String getEncKey() {
//		if (CoreUtil.isNull(encKey)) {
//			encKey = encDao.getEncKey();
//		}
//		
//		return encKey;
//	}
//	
//	/**
//	 * 加密策略
//	 * 
//	 * @param entity
//	 * @return Policy
//	 */
//	private Policy encryptPolicy(Object entity) {
//		Policy policy = (Policy)entity;
//		String key = "policy_list";
//		if (getEncInfoMap().containsKey(key) && getEncInfoMap().containsKey("policy_list")) {
//			Encryption bean = getEncInfoMap().get(key);
//			String[] fields = bean.getTableFields().split(SystemStatics.DEFAULT_SPLIT);
//			for (String field : fields) {
//				if("Policy_Name".equalsIgnoreCase(field)){
//					policy.setName(ComEdpCryptUtil.encode(policy.getName(), getEncKey()));
//					continue;
//				}
//				if("Policy_Func".equalsIgnoreCase(field)) {
//					policy.setTypeName(ComEdpCryptUtil.encode(policy.getTypeName(), getEncKey()));
//					continue;
//				}
//				if("Policy_FuncNum".equalsIgnoreCase(field)) {
//					policy.setTypeNum(Integer.valueOf(ComEdpCryptUtil.encode(String.valueOf(policy.getTypeNum()), getEncKey())));
//					continue;
//				}
//				if("Policy_Content".equalsIgnoreCase(field)){
//					policy.setContent(ComEdpCryptUtil.encode(policy.getContent(), getEncKey()));
//					continue;
//				}
//				if("Policy_Object".equalsIgnoreCase(field)){
//					policy.setObj(ComEdpCryptUtil.encode(policy.getObj(), getEncKey()));
//					continue;
//				}
//				if("Policy_Create".equalsIgnoreCase(field)){
//					policy.setCreateDate(DateTypeConverterUtil.converterString2SqlDate(ComEdpCryptUtil.encode(String.valueOf(policy.getCreateDate()), getEncKey()), "yyyy-MM-dd hh:mm:ss"));
//					continue;
//				}
//				if("Policy_CreateUser".equalsIgnoreCase(field)){
//					policy.setCreateUser(ComEdpCryptUtil.encode(policy.getCreateUser(), getEncKey()));
//					continue;
//				}
//				if("Policy_Modify".equalsIgnoreCase(field)){
//					policy.setUpdateDate(DateTypeConverterUtil.converterString2SqlDate(ComEdpCryptUtil.encode(String.valueOf(policy.getUpdateDate()), getEncKey()), "yyyy-MM-dd hh:mm:ss"));
//					continue;
//				}
//				if("Policy_Modify_Content".equalsIgnoreCase(field)){
//					policy.setUpdateContent(DateTypeConverterUtil.converterString2SqlDate(ComEdpCryptUtil.encode(String.valueOf(policy.getUpdateContent()), getEncKey()), "yyyy-MM-dd hh:mm:ss"));
//					continue;
//				}
//				if("Policy_ModifyUser".equalsIgnoreCase(field)){
//					policy.setUpdateUser(ComEdpCryptUtil.encode(policy.getUpdateUser(), getEncKey()));
//					continue;
//				}
//				if("OSType".equalsIgnoreCase(field)){
//					policy.setOs(ComEdpCryptUtil.encode(policy.getOs(), getEncKey()));
//					continue;
//				}
//				if("Policy_Start".equalsIgnoreCase(field)){
//					policy.setStartFlag(Integer.valueOf(ComEdpCryptUtil.encode(String.valueOf(policy.getStartFlag()), getEncKey())));
//					continue;
//				}
//				if("Remark".equalsIgnoreCase(field)){
//					policy.setRemark(ComEdpCryptUtil.encode(policy.getRemark(), getEncKey()));
//					continue;
//				}
//				if("Policy_CRC".equalsIgnoreCase(field)){
//					policy.setCrc(Long.valueOf(ComEdpCryptUtil.encode(String.valueOf(policy.getCrc()), getEncKey())));
//					continue;
//				}
//				if("Policy_CRC_Content".equalsIgnoreCase(field)){
//					policy.setContentCRC(Long.valueOf(ComEdpCryptUtil.decode(String.valueOf(policy.getContentCRC()), getEncKey())));
//					continue;
//				}
//				if("Policy_CRC_Object".equalsIgnoreCase(field)){
//					policy.setObjectCRC(Long.valueOf(ComEdpCryptUtil.encode(String.valueOf(policy.getObjectCRC()), getEncKey())));
//					continue;
//				}
//				if("Policy_Changed".equalsIgnoreCase(field)){
//					policy.setChanged(Integer.valueOf(ComEdpCryptUtil.encode(String.valueOf(policy.getChanged()), getEncKey())));
//					continue;
//				}
//				if("PolicyExecuteLevel".equalsIgnoreCase(field)){
//					policy.setExecuteLevel(Integer.valueOf(ComEdpCryptUtil.encode(String.valueOf(policy.getExecuteLevel()), getEncKey())));
//					continue;
//				}
//				if("Reserved1".equalsIgnoreCase(field)){
//					policy.setReserved(Integer.valueOf(ComEdpCryptUtil.encode(String.valueOf(policy.getReserved()), getEncKey())));
//					continue;
//				}
//				if("Reserved2".equalsIgnoreCase(field)){
//					policy.setReserved2(Integer.valueOf(ComEdpCryptUtil.encode(String.valueOf(policy.getReserved2()), getEncKey())));
//					continue;
//				}
//				if("Reserved3".equalsIgnoreCase(field)){
//					policy.setReserved3(Integer.valueOf(ComEdpCryptUtil.encode(String.valueOf(policy.getReserved3()), getEncKey())));
//					continue;
//				}
//				if("Reserved4".equalsIgnoreCase(field)){
//					policy.setReserved4(Double.valueOf(ComEdpCryptUtil.encode(String.valueOf(policy.getReserved4()), getEncKey())));
//					continue;
//				}
//				if("Reserved5".equalsIgnoreCase(field)){
//					policy.setReserved5(ComEdpCryptUtil.encode(policy.getReserved5(), getEncKey()));
//					continue;
//				}
//				if("Reserved6".equalsIgnoreCase(field)){
//					policy.setReserved6(ComEdpCryptUtil.encode(policy.getReserved6(), getEncKey()));
//					continue;
//				}
//				if("Reserved7".equalsIgnoreCase(field)){
//					policy.setReserved7(ComEdpCryptUtil.encode(policy.getReserved7(), getEncKey()));
//					continue;
//				}
//			}
//		}
//		
//		return policy;
//	}
//	
//	/**
//	 * 解密策略
//	 * 
//	 * @param entity
//	 * @return Policy
//	 */
//	private <T> Policy dencryptPolicy(T entity) {
//		Policy policy = (Policy) entity;
//		String key = "Policy_List";
//		if (StringUtils.isNotBlank(getEncKey()) && getEncInfoMap().containsKey(key)) {
//			Encryption bean = getEncInfoMap().get(key);
//			String[] fields = bean.getTableFields().split(SystemStatics.DEFAULT_SPLIT);
//			for (String field : fields) {
//				if("Policy_Name".equalsIgnoreCase(field)){
//					policy.setName(ComEdpCryptUtil.decode(policy.getName(), getEncKey()));
//					continue;
//				}
//				if("Policy_Func".equalsIgnoreCase(field)) {
//					policy.setTypeName(ComEdpCryptUtil.decode(policy.getTypeName(), getEncKey()));
//					continue;
//				}
//				if("Policy_FuncNum".equalsIgnoreCase(field)) {
//					policy.setTypeNum(Integer.valueOf(ComEdpCryptUtil.decode(String.valueOf(policy.getTypeNum()), getEncKey())));
//					continue;
//				}
//				if("Policy_Content".equalsIgnoreCase(field)){
//					policy.setContent(ComEdpCryptUtil.decode(policy.getContent(), getEncKey()));
//					continue;
//				}
//				if("Policy_Object".equalsIgnoreCase(field)){
//					policy.setObj(ComEdpCryptUtil.decode(policy.getObj(), getEncKey()));
//					continue;
//				}
//				if("Policy_Create".equalsIgnoreCase(field)){
//					policy.setCreateDate(DateTypeConverterUtil.converterString2SqlDate(ComEdpCryptUtil.decode(String.valueOf(policy.getCreateDate()), getEncKey()), "yyyy-MM-dd hh:mm:ss"));
//					continue;
//				}
//				if("Policy_CreateUser".equalsIgnoreCase(field)){
//					policy.setCreateUser(ComEdpCryptUtil.decode(policy.getCreateUser(), getEncKey()));
//					continue;
//				}
//				if("Policy_Modify".equalsIgnoreCase(field)){
//					policy.setUpdateDate(DateTypeConverterUtil.converterString2SqlDate(ComEdpCryptUtil.decode(String.valueOf(policy.getUpdateDate()), getEncKey()), "yyyy-MM-dd hh:mm:ss"));
//					continue;
//				}
//				if("Policy_Modify_Content".equalsIgnoreCase(field)){
//					policy.setUpdateContent(DateTypeConverterUtil.converterString2SqlDate(ComEdpCryptUtil.decode(String.valueOf(policy.getUpdateContent()), getEncKey()), "yyyy-MM-dd hh:mm:ss"));
//					continue;
//				}
//				if("Policy_ModifyUser".equalsIgnoreCase(field)){
//					policy.setUpdateUser(ComEdpCryptUtil.decode(policy.getUpdateUser(), getEncKey()));
//					continue;
//				}
//				if("OSType".equalsIgnoreCase(field)){
//					policy.setOs(ComEdpCryptUtil.decode(policy.getOs(), getEncKey()));
//					continue;
//				}
//				if("Policy_Start".equalsIgnoreCase(field)){
//					policy.setStartFlag(Integer.valueOf(ComEdpCryptUtil.decode(String.valueOf(policy.getStartFlag()), getEncKey())));
//					continue;
//				}
//				if("Remark".equalsIgnoreCase(field)){
//					policy.setRemark(ComEdpCryptUtil.decode(policy.getRemark(), getEncKey()));
//					continue;
//				}
//				if("Policy_CRC".equalsIgnoreCase(field)){
//					policy.setCrc(Long.valueOf(ComEdpCryptUtil.decode(String.valueOf(policy.getCrc()), getEncKey())));
//					continue;
//				}
//				if("Policy_CRC_Content".equalsIgnoreCase(field)){
//					policy.setContentCRC(Long.valueOf(ComEdpCryptUtil.decode(String.valueOf(policy.getContentCRC()), getEncKey())));
//					continue;
//				}
//				if("Policy_CRC_Object".equalsIgnoreCase(field)){
//					policy.setObjectCRC(Long.valueOf(ComEdpCryptUtil.decode(String.valueOf(policy.getObjectCRC()), getEncKey())));
//					continue;
//				}
//				if("Policy_Changed".equalsIgnoreCase(field)){
//					policy.setChanged(Integer.valueOf(ComEdpCryptUtil.decode(String.valueOf(policy.getChanged()), getEncKey())));
//					continue;
//				}
//				if("PolicyExecuteLevel".equalsIgnoreCase(field)){
//					policy.setExecuteLevel(Integer.valueOf(ComEdpCryptUtil.decode(String.valueOf(policy.getExecuteLevel()), getEncKey())));
//					continue;
//				}
//				if("Reserved1".equalsIgnoreCase(field)){
//					policy.setReserved(Integer.valueOf(ComEdpCryptUtil.decode(String.valueOf(policy.getReserved()), getEncKey())));
//					continue;
//				}
//				if("Reserved2".equalsIgnoreCase(field)){
//					policy.setReserved2(Integer.valueOf(ComEdpCryptUtil.decode(String.valueOf(policy.getReserved2()), getEncKey())));
//					continue;
//				}
//				if("Reserved3".equalsIgnoreCase(field)){
//					policy.setReserved3(Integer.valueOf(ComEdpCryptUtil.decode(String.valueOf(policy.getReserved3()), getEncKey())));
//					continue;
//				}
//				if("Reserved4".equalsIgnoreCase(field)){
//					policy.setReserved4(Double.valueOf(ComEdpCryptUtil.decode(String.valueOf(policy.getReserved4()), getEncKey())));
//					continue;
//				}
//				if("Reserved5".equalsIgnoreCase(field)){
//					policy.setReserved5(ComEdpCryptUtil.decode(policy.getReserved5(), getEncKey()));
//					continue;
//				}
//				if("Reserved6".equalsIgnoreCase(field)){
//					policy.setReserved6(ComEdpCryptUtil.decode(policy.getReserved6(), getEncKey()));
//					continue;
//				}
//				if("Reserved7".equalsIgnoreCase(field)){
//					policy.setReserved7(ComEdpCryptUtil.decode(policy.getReserved7(), getEncKey()));
//					continue;
//				}
//			}
//		}
//		
//		return policy;
//	}
//
//	/**
//	 * 解密设备
//	 * 
//	 * @param entity
//	 * @return DeviceBean
//	 */
//	private <T> DeviceBean dencryptDevice(T entity) {
//		DeviceBean device = (DeviceBean) entity;
//		String key = "Device";
//		if (StringUtils.isNotBlank(getEncKey()) && getEncInfoMap().containsKey(key)) {
//			Encryption bean = getEncInfoMap().get(key);
//			String[] fields = bean.getTableFields().split(SystemStatics.DEFAULT_SPLIT);
//			String dbEncKey = getEncKey();
//			for (String field : fields) {
//				if("deviceId".equalsIgnoreCase(field)){
//					device.setDeviceId(Long.valueOf(ComEdpCryptUtil.decode(String.valueOf(device.getDeviceId()),dbEncKey)));
//					continue;
//				}
//				if("devOnlyId".equalsIgnoreCase(field)){
//					device.setDevOnlyId(Long.valueOf(ComEdpCryptUtil.decode(String.valueOf(device.getDevOnlyId()),dbEncKey)));
//					continue;
//				}
//				if("ipAddres".equalsIgnoreCase(field)){
//					device.setIpAddres(ComEdpCryptUtil.decode(device.getIpAddres(),dbEncKey));
//					continue;
//				}
//				if("macAddress".equalsIgnoreCase(field)){
//					device.setMacAddress(ComEdpCryptUtil.decode(device.getMacAddress(),dbEncKey));
//					continue;
//				}
//				if("userName".equalsIgnoreCase(field)){
//					device.setUserName(ComEdpCryptUtil.decode(device.getUserName(),dbEncKey));
//					continue;
//				}
//				if("deviceName".equalsIgnoreCase(field)){
//					device.setDeviceName(ComEdpCryptUtil.decode(device.getDeviceName(),dbEncKey));
//					continue;
//				}
//				if("deptName".equalsIgnoreCase(field)){
//					device.setDeptName(ComEdpCryptUtil.decode(device.getDeptName(),dbEncKey));
//					continue;
//				}
//				if("officeName".equalsIgnoreCase(field)){
//					device.setOfficeName(ComEdpCryptUtil.decode(device.getOfficeName(),dbEncKey));
//					continue;
//				}
//				if("roomNumber".equalsIgnoreCase(field)){
//					device.setRoomNumber(ComEdpCryptUtil.decode(device.getRoomNumber(),dbEncKey));
//					continue;
//				}
//				if("tel".equalsIgnoreCase(field)){
//					device.setTel(ComEdpCryptUtil.decode(device.getTel(),dbEncKey));
//					continue;
//				}
//				if("email".equalsIgnoreCase(field)){
//					device.setEmail(ComEdpCryptUtil.decode(device.getEmail(),dbEncKey));
//					continue;
//				}
//				if("registerTime".equalsIgnoreCase(field)){
//					device.setRegisterTime(Timestamp.valueOf(ComEdpCryptUtil.decode(String.valueOf(device.getRegisterTime()),dbEncKey)));
//					continue;
//				}
//				if("diskSerial".equalsIgnoreCase(field)){
//					device.setDiskSerial(ComEdpCryptUtil.decode(device.getDiskSerial(),dbEncKey));
//					continue;
//				}
//				if("amtUUID".equalsIgnoreCase(field)){
//					device.setAmtUUID(ComEdpCryptUtil.decode(device.getAmtUUID(),dbEncKey));
//					continue;
//				}
//				if("diskSize".equalsIgnoreCase(field)){
//					device.setDiskSize(Integer.valueOf(ComEdpCryptUtil.decode(String.valueOf(device.getDiskSize()),dbEncKey)));
//					continue;
//				}
//				if("memory".equalsIgnoreCase(field)){
//					device.setMemory(Integer.valueOf(ComEdpCryptUtil.decode(String.valueOf(device.getMemory()),dbEncKey)));
//					continue;
//				}
//				if("osVersion".equalsIgnoreCase(field)){
//					device.setOsVersion(ComEdpCryptUtil.decode(device.getOsVersion(), dbEncKey));
//				}
//				if("osType".equalsIgnoreCase(field)){
//					device.setOsType(ComEdpCryptUtil.decode(device.getOsType(), dbEncKey));
//				}
//				//将Timestamp类型的时间转成String 放入RegisterTime2中,方便Json将数据传输到页面
//				device.setRegisterTime2(new SimpleDateFormat("yyyy-MM-dd hh:ss:mm").format(device.getRegisterTime()));
//			}
//		}
//		
//		return device;
//	}
//}
