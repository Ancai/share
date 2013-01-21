//package com.vrv.dsms.jj.struts.action;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import javax.servlet.http.HttpSession;
//
//import net.sf.json.JSONArray;
//
//import org.apache.commons.lang.StringUtils;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Controller;
//
//import com.vrv.dsms.jj.annotation.OperateLogAnn;
//import com.vrv.dsms.jj.base.Base;
//import com.vrv.dsms.jj.base.BaseAction;
//import com.vrv.dsms.jj.base.SystemStatics;
//import com.vrv.dsms.jj.base.SystemStatics.StatisticsField;
//import com.vrv.dsms.jj.domain.Card;
//import com.vrv.dsms.jj.domain.CardType;
//import com.vrv.dsms.jj.domain.DataUser;
//import com.vrv.dsms.jj.domain.Education;
//import com.vrv.dsms.jj.domain.Level;
//import com.vrv.dsms.jj.domain.Operator;
//import com.vrv.dsms.jj.domain.Realm;
//import com.vrv.dsms.jj.domain.SysTable;
//import com.vrv.dsms.jj.enums.HibInterEnum;
//import com.vrv.dsms.jj.enums.OperateEnum;
//import com.vrv.dsms.jj.utils.BatchNoUtil;
//import com.vrv.dsms.jj.utils.CharacterCodeUtil;
//import com.vrv.dsms.jj.utils.DateTypeConverterUtil;
//import com.vrv.dsms.jj.utils.HQLBuilderUtil;
//import com.vrv.dsms.jj.utils.OperateLogUtil;
//
///** 
// *         说      明：数据处理人员管理Action
// *
// * @author 作      者：lac
// *		  E-mail: ancai0729@163.com 
// * @version V1.0
// *         创建时间：2012-4-25 上午11:32:52 
// */
//@OperateLogAnn(hibClass=Operator.class)
//@Controller
//@Scope("prototype")
//public class OperatorAction extends BaseAction {
//	private static final long serialVersionUID = -8985037491285500500L;
//	public static final String OPERATORSESSION = "operatorSession";
//	/*数据处理人员对象*/
//	private Operator operator;
//	/*数据处理人员集合*/
//	private List<Operator> operatorList;
//	/*证件对象*/
//	private Card card;
//	/*领域ID*/
//	private String parentRealms;
//	/*子领域ID*/
//	private String childrenRealms;
//	/*数据中心主管人(或负责人)IDs*/
//	private String dataUserFZs;
//	/**统计中文*/
//	private String statisticsZh ;
//	/**
//	 *属性方法
//	 * 
//	 */
//	public Operator getOperator() {
//		return operator;
//	}
//
//	public void setOperator(Operator operator) {
//		this.operator = operator;
//	}
//
//	public List<Operator> getOperatorList() {
//		return operatorList;
//	}
//
//	public void setOperatorList(List<Operator> operatorList) {
//		this.operatorList = operatorList;
//	}
//	
//	
//	public Card getCard() {
//		return card;
//	}
//
//	public void setCard(Card card) {
//		this.card = card;
//	}
//	public String getParentRealms() {
//		return parentRealms;
//	}
//
//	public void setParentRealms(String parentRealms) {
//		this.parentRealms = parentRealms;
//	}
//
//	public String getChildrenRealms() {
//		return childrenRealms;
//	}
//
//	public void setChildrenRealms(String childrenRealms) {
//		this.childrenRealms = childrenRealms;
//	}
//
//	public String getDataUserFZs() {
//		return dataUserFZs;
//	}
//
//	public void setDataUserFZs(String dataUserFZs) {
//		this.dataUserFZs = dataUserFZs;
//	}
//	
//	public String getStatisticsZh() {
//		return statisticsZh;
//	}
//
//	/**
//	 * 详细信息UI
//	 * 
//	 */
//	@OperateLogAnn(type=OperateEnum.SHOW,hibInter=HibInterEnum.ONLOAD)
//	public String showUI() {
//		this.msg = RESULT_FAIL;
//		try {
//			if (operator != null && null != operator.getId()) {
//				operator = operatorService.queryById(operator.getId());
//				this.msg = RESULT_SUCCESS ;
//			}
//		} catch (Exception e) {
//			this.msg = RESULT_ERROR ;
//			log.error("数据处理人员管理 报错com.vrv.dsms.jj.struts.action.OperatorAction.showUI", e);
//		}finally{
//			OperateLogUtil.getMap().put("hibInter", HibInterEnum.NONE);
//		}
//		return RETURN_SHOWUI;
//		
//	}
//	/**
//	 * 录入UI
//	 * 
//	 */
//	@SuppressWarnings("unchecked")
//	public String addUI() {
//		//获取对应列 配置信息
//		HttpSession httpSession = this.getSession( false );
//		if( httpSession.getAttribute( OPERATORSESSION ) == null){
//			List<SysTable> sysTableList = (List<SysTable>) this.getSession(false).getServletContext().getAttribute(SystemStatics.APPLICATION_SYSTABLELISTMEMORY);
//			for(SysTable sysTable : sysTableList){
//				if(sysTable.getNameEn().equals( Operator.class.getSimpleName() )){
//					httpSession.setAttribute( OPERATORSESSION , sysTable);
//					break;
//				}
//			}
//		}
//		return RETURN_ADDUI;
//	}
//	
//	
//	
//	/**
//	 * 录入
//	 * 
//	 */
//	@OperateLogAnn(type=OperateEnum.ADD,hibInter=HibInterEnum.ONSAVE)
//	public void add() {
//		Long id = 0l;
//		this.msg = RESULT_FAIL;
//		try {
//			Set<Card> cards = new HashSet<Card>();
//		    cards.add(new Card(cardTypeService.queryById(Long.valueOf(getRequest().getParameter("cardType1"))), getRequest().getParameter("cardNO1")));
//		    if (StringUtils.isNotBlank(getRequest().getParameter("cardType2")) 
//		    		&& StringUtils.isNotBlank(getRequest().getParameter("cardNO2"))) {
//		    	cards.add(new Card(cardTypeService.queryById(Long.valueOf(getRequest().getParameter("cardType2"))), getRequest().getParameter("cardNO2")));
//			}
//		    if (StringUtils.isNotBlank(getRequest().getParameter("cardType3")) 
//		    		&& StringUtils.isNotBlank(getRequest().getParameter("cardNO3"))) {
//		    	cards.add(new Card(cardTypeService.queryById(Long.valueOf(getRequest().getParameter("cardType3"))), getRequest().getParameter("cardNO3")));
//			}
//			String parentRealmParam = getRequest().getParameter("parentRealm");
//			String childrenRealmParam = getRequest().getParameter("childrenRealm");
//			String datauserParam = getRequest().getParameter("dataUserFZs");
//			operatorService.saveOperator(operator, parentRealmParam, childrenRealmParam, datauserParam, 
//					cards, this.getUser());
//			id = operator.getId();
//			this.msg = RESULT_SUCCESS;
//		} catch (Exception e) {
//			this.msg = RESULT_ERROR;
//			log.error("数据处理人员-添加时出错了！com.vrv.dsms.jj.struts.action.OperatorAction.add",e);
//		}finally{
//			print("{result: '"+ this.msg +"', thisBatchNo: '"+id+"'}");
//		}
//	}
//	
//	/**
//	 * 修改UI
//	 * 
//	 */
//	public String editUI() {
//		operator = operatorService.queryById(operator.getId());
//		return RETURN_EDITUI;
//	}
//	
//	/**
//	 * 修改
//	 * 
//	 */
//	@OperateLogAnn(type=OperateEnum.EDIT,hibInter=HibInterEnum.ONFLUSHDIRTY)
//	public void edit(){
//		this.msg = RESULT_FAIL ;
//		try {
//			Set<Card> cards = new HashSet<Card>();
//			Card card1 = cardService.queryById(Long.valueOf(getRequest().getParameter("card1_id")));
//			card1.setCardType(cardTypeService.queryById(Long.valueOf(getRequest().getParameter("cardType1"))));
//			card1.setCardNo(getRequest().getParameter("cardNO1"));
//			cards.add(card1);
//		    if (StringUtils.isNotBlank(getRequest().getParameter("cardType2")) 
//		    		&& StringUtils.isNotBlank(getRequest().getParameter("cardNO2"))) {
//		    	Card card2 = null;
//		    	if (StringUtils.isNotBlank(getRequest().getParameter("card2_id"))) {
//		    		card2 = cardService.queryById(Long.valueOf(getRequest().getParameter("card2_id")));
//				} else {
//					card2 = new Card();
//				}
//				card2.setCardType(cardTypeService.queryById(Long.valueOf(getRequest().getParameter("cardType2"))));
//				card2.setCardNo(getRequest().getParameter("cardNO2"));
//				cards.add(card2);
//			}
//		    if (StringUtils.isNotBlank(getRequest().getParameter("cardType3")) 
//		    		&& StringUtils.isNotBlank(getRequest().getParameter("cardNO3"))) {
//		    	Card card3 = null;
//		    	if (StringUtils.isNotBlank(getRequest().getParameter("card3_id"))) {
//		    		card3 = cardService.queryById(Long.valueOf(getRequest().getParameter("card3_id")));
//				} else {
//					card3 = new Card();
//				}
//				card3.setCardType(cardTypeService.queryById(Long.valueOf(getRequest().getParameter("cardType3"))));
//				card3.setCardNo(getRequest().getParameter("cardNO3"));
//				cards.add(card3);
//			}
//			String parentRealmParam = getRequest().getParameter("parentRealm");
//			String childrenRealmParam = getRequest().getParameter("childrenRealm");
//			String datauserParam = getRequest().getParameter("dataUserFZs");
//			operatorService.updateOperator(operator, parentRealmParam, childrenRealmParam, datauserParam, 
//					cards, this.getUser());
//			this.msg = RESULT_SUCCESS ;
//		}catch(Exception e) {
//			this.msg = RESULT_ERROR;
//			log.error("数据处理人员-修改时出错了！com.vrv.dsms.jj.struts.action.OperatorAction.edit",e);
//		}finally{
//			print( this.msg );
//		}
//	}
//	/**
//	 * 数据处理人员列表
//	 *
//	 */
//	public String listUI() {
//		//1.组装hql
//		if (null != getRequest().getParameter("via") && 
//				"statistics".equals(getRequest().getParameter("via"))) {
//			//由统计页面的分类字段 ，链接到列表页面
//			this.hqlBuilderUtil = operatorService.getHqlBuilderForStatistics(field.substring(0, field.lastIndexOf("_")), startTime, endTime,
//					CharacterCodeUtil.convertGetURL(getRequest().getParameter("value")), getUser());
//		} else {
//			this.hqlBuilderUtil = getHqlBuilder(Operator.class,this.operator,this.card,parentRealms,childrenRealms,this.dataUserFZs);
//		} 
//		//2.分页查询
//		pageView = operatorService.getPageView(this.hqlBuilderUtil, getPageNum(), getPageSize());
//		//3.存放显示、隐藏列及其顺序到Session中
//		saveColumnsToSesssion(OPERATORSESSION, Operator.class);
//		
//		return RETURN_LISTUI;
//	}
//
//	/**
//	 * 删除
//	 * 
//	 */
//	@OperateLogAnn(hibInter=HibInterEnum.ONDELETE,type=OperateEnum.DELETE)
//	public void delete() {
//		try {
//			if (null != getIds() && 0 < getIds().length) {
//				operatorService.deleteByIdsWithCaseCade(getIds());
//				this.msg = RESULT_SUCCESS;
//			}else{
//				this.msg = RESULT_FAIL;
//			}
//		} catch (Exception e) {
//			this.msg = RESULT_ERROR;
//			log.error("数据处理人员-删除时出错了！com.vrv.dsms.jj.struts.action.OperatorAction.delete()",e);
//		}finally {
//			print(this.msg);
//		}
//	}
//	
//	/**
//	 * 统计
//	 */
//	@OperateLogAnn(type=OperateEnum.STATISTICS,remarkTitle="条件字段",remarkField="statisticsZh")
//	public void statistics() {
//		String result = "";
//		this.msg = RESULT_FAIL;
//		try {
//			field = (null == field) ? "SEX" : field;
//			statisticsZh = StatisticsField.valueOf( field ).getNameZh();
//			if (null != startTime && null != endTime) {
//				result= JSONArray.fromObject(operatorService.statisticsData(field, startTime, endTime, getUser())).toString();
//				this.msg = RESULT_SUCCESS;
//			}
//		} catch (Exception e) {
//			this.msg = RESULT_ERROR;
//			result = RESULT_ERROR;
//			log.error("处理人员中-加载统计数据时报错！com.vrv.dsms.jj.struts.action.OperatorAction.statistics",e);
//		} finally {
//			print( result );
//		}
//	}
//
//	/**
//	 * 导出
//	 * @return
//	 */
//	@OperateLogAnn(type=OperateEnum.EXPORT,remarkField="fileName",remarkTitle="文件名")
//	public String export() {
//		this.msg = RESULT_FAIL ;
//		try {
//			//1.被导出字段：英文、中文名
//			this.exportNameEn= getRequest().getParameterValues("exportNameEn");
//			this.exportNameZh= getRequest().getParameterValues("exportNameZh");
//			if(exportNameEn!=null && exportNameZh!=null && exportNameEn.length>0 && exportNameZh.length>0){
//				//2.构建被导出的数据
//				this.hqlBuilderUtil = getHqlBuilder(Operator.class, this.operator, this.card,this.parentRealms,this.childrenRealms,this.dataUserFZs);
//				operatorList = operatorService.queryByHQLBuilder(this.hqlBuilderUtil);
//				//3.设置导出路径
//				this.exportPath = this.getSession(false).getServletContext().getAttribute("exportRootPath")+ "/" +this.getUser().getAccount();
//				if (!(new File(this.exportPath).exists())) {
//					new File(this.exportPath).mkdirs();
//				}
//				
//				//导出文件名、sheet名、head名
//				this.fileName = "数据处理人员";
//				this.sheetName = this.fileName;
//				this.headName = this.fileName + "("+ DateTypeConverterUtil.converterUtil2String(new Date(), "yyyy-MM-dd HH:mm:ss")+")";
//				
//				//5.调用Service导出数据
//				operatorService.export(exportNameEn, exportNameZh, operatorList, exportPath, fileName, sheetName, headName);
//				
//				//6.设定下载时的文件名
//				this.fileName = this.fileName + ".xls";
//				this.msg = RESULT_SUCCESS;
//				return RETURN_DOWNLOADUI;
//			}else{
//				return RESULT_FAIL;
//			}
//		} catch (Exception e) {
//			this.msg = RESULT_ERROR ;
//			log.error("数据处理人员-导出时报错com.vrv.dsms.jj.struts.action.OperatorAction.export", e);
//			return RESULT_ERROR;
//		}
//	}
//	/**
//	 * 处理人员导入
//	 */
//	@OperateLogAnn(type=OperateEnum.IMPORT,remarkTitle="文件名",remarkField="uploadFileName")
//	public void importFile(){
//		String result = "" ;
//		this.msg = RESULT_FAIL ;
//		try {
//			//1.判断当前文件是否允许上传
//			String filterResult = this.filterType();
//			//2.如果当前文件不允许上传
//			if(filterResult!=null){
//				//返回错误提示
//				result= filterResult;
// 			}else{
//				//4.调用service导入方法 并返回导入提示
//				result = operatorService.importFile(upload, this.getUser());
//				//5.返回导入提示
//				if( "success".equals( result )){
//					this.msg = RESULT_SUCCESS ;
//				}
// 			}
//		} catch (Throwable e) {
//			result=RESULT_ERROR;
//			log.error("处理人员-导入时报错com.vrv.dsms.jj.struts.action.OperatorAction.importFile",e);
//		}finally{
//			StringBuffer sb = new StringBuffer();
//			sb.append("<script type='text/javascript'>parent.setMessage('"+result+"');");
//			sb.append("</script>");
//			print(sb.toString());
//		}
//	}
//	/**
//	 * 领域下拉框的数据
//	 * 
//	 */
//	public void realmJson() {
//		List<Base> jsonList = new ArrayList<Base>();
//		List<Realm> realmList = realmService.queryTop();
//		for (Realm realm : realmList) {
//			jsonList.add(new Base(realm.getId(),realm.getName()));
//		}
//		print(JSONArray.fromObject(jsonList).toString());
//	}
//	
//	/**
//	 * 子领域下拉框的数据
//	 * 
//	 */
//	public void childRealmJson() {
//		List<Base> jsonList = new ArrayList<Base>();
//		List<Realm> realmList = null;
//		try {
//			if (StringUtils.isNotBlank(getRequest().getParameter("parentRealm"))) {
//				realmList= realmService.queryByHQLBuilder(
//						new HQLBuilderUtil(Realm.class).addWhereClause(" this.parent.id in (" + getRequest().getParameter("parentRealm")+")"));
//				for (Realm realm : realmList) {
//					jsonList.add(new Base(realm.getId(),realm.getName()));
//				}
//			}
//			print(JSONArray.fromObject(jsonList).toString());
//		} catch (Exception e) {
//			log.error("获取子领域下拉框数据时出错！", e);
//			print("[]");
//		}
//		
//	}
//	
//	/**
//	 * 子领域json(用于查询条件)
//	 */
//	public void childRealmJson2(){
//		List<Base> jsonList = new ArrayList<Base>();
//		List<Realm> realmList = null;
//		try {
//			realmList= realmService.queryByHQLBuilder(
//					new HQLBuilderUtil(Realm.class).addWhereClause(" this.parent.id is not null"));
//			for (Realm realm : realmList) {
//				jsonList.add(new Base(realm.getId(),realm.getName()));
//			}
//			print(JSONArray.fromObject(jsonList).toString());
//		}catch (Exception e) {
//			this.msg = RESULT_ERROR;
//			log.error("查询条件中获取子领域下拉框数据时出错",e);
//			print( this.msg );
//		}
//	}
//	
//	/**
//	 * 级别下拉框的数据
//	 * 
//	 */
//	public void levelJson() {
//		List<Base> jsonList = new ArrayList<Base>();
//		List<Level> levelmList = levelService.queryAll();
//		for (Level level : levelmList) {
//			jsonList.add(new Base(level.getId(),level.getName()));
//		}
//		print(JSONArray.fromObject(jsonList).toString());
//	}
//	
//	/**
//	 * 学历下拉框的数据
//	 * 
//	 */
//	public void educationJson() {
//		List<Base> jsonList = new ArrayList<Base>();
//		List<Education> educationList = educationService.queryAll();
//		for (Education education : educationList) {
//			jsonList.add(new Base(education.getId(),education.getName()));
//		}
//		print(JSONArray.fromObject(jsonList).toString());
//	}
//	
//	/**
//	 * 数据中心主管人下拉框
//	 * 
//	 */
//	public void dataUserJson() {
//		List<Base> jsonList = new ArrayList<Base>();
//		List<DataUser> dataUserList = dataUserService.queryByDataUserRoleMark(DATAUSER_FZ);
//		for (DataUser dataUser : dataUserList) {
//			jsonList.add(new Base(dataUser.getId(),dataUser.getName()));
//		}
//		print(JSONArray.fromObject(jsonList).toString());
//	}
//	
//	/**
//	 * 证件类型下拉框
//	 * 
//	 */
//	public void cardTypeJson() {
//		List<Base> jsonList = new ArrayList<Base>();
//		List<CardType> dataUserList = cardTypeService.queryAll();
//		for (CardType cardType : dataUserList) {
//			jsonList.add(new Base(cardType.getId(),cardType.getName()));
//		}
//		print(JSONArray.fromObject(jsonList).toString());
//	}
//	
//	/**
//	 * "代号" 的唯一性验证
//	 * 
//	 */
//	public void checkByJobNo() {
//		//false 不通过  true 通过
//		this.msg = "false";
//		try {
//			if (null != operator && null != operator.getJobNo()) {
//				Operator tempOperator = operatorService.queryByJobNo(operator.getJobNo());
//				if (null == tempOperator) {
//					msg = "true";
//				} else if (tempOperator.getId().equals(operator.getId())) {
//					msg = "true";
//				}
//			}
//		} catch (Exception e) {
//		}finally {
//			print(msg);
//		}
//	}
//	
//	/**
//	 * "代号" 的唯一性验证
//	 * 
//	 */
//	public void checkByJobNo2() {
//		//false 不通过  true 通过
//		this.msg = "false";
//		try {
//			if (StringUtils.isNotBlank(getRequest().getParameter("param1"))) {
//				Operator tempOperator = operatorService.queryByJobNo(getRequest().getParameter("param1"));
//				if (null == tempOperator) {
//					msg = "true";
//				}  
//			}
//		} catch (Exception e) {
//		}finally {
//			print(msg);
//		}
//	}
//	/**
//	 * 组装Hql语句(供查询和导出使用)
//	 * @param clazz 当前要查询或导出的实体
//	 * @param tempOperator 当前要查询或导出的实体条件
//	 * @param tempCard 当前要查询或导出的实体条件-证件
//	 * @param tempParentRealmIds  当前要查询或导出的实体条件-领域IDs
//	 * @param tempChildrenRealmIds 当前要查询或导出的实体条件-子领域IDs
//	 * @param tempDataUserFZs 当前要查询或导出的实体条件-数据中心主管人(或负责人)IDs
//	 * @return HQLBuilderUtil
//	 */
//	private HQLBuilderUtil getHqlBuilder(Class<Operator> clazz,
//			Operator tempOperator, Card tempCard, String tempParentRealmIds,
//			String tempChildrenRealmIds,String tempDataUserFZs) {
//		HQLBuilderUtil hqlBuilderUtil = new HQLBuilderUtil(clazz);
////		hqlBuilderUtil.addWhereClause(" this.user.id = ? ", SessionUtil.getInstance().getUserFromSession(getRequest()).getId());
//		hqlBuilderUtil.addSelectClause(" this ");
//		if(!isSysOrGly(this.getUser())){
//			hqlBuilderUtil.addJoinClause(" this.user.roles tempRole ");
//			hqlBuilderUtil.addWhereClause(" tempRole.mark  like ? ", BatchNoUtil.getInstance().getRoleTypeMark(this.getUser())+"%");
//		}
//		if(tempOperator != null){
//			//姓名
//			if(StringUtils.isNotBlank(tempOperator.getName())){
//				hqlBuilderUtil.addWhereClause(" this.name like ? ", "%"+StringUtils.trim(tempOperator.getName())+"%");
//			}
//			//性别
//			if(tempOperator.getSex() != null){
//				hqlBuilderUtil.addWhereClause(" this.sex = ? ", tempOperator.getSex());
//			}
//			//代号
//			if (StringUtils.isNotBlank(tempOperator.getJobNo())) {
//				hqlBuilderUtil.addWhereClause(" this.jobNo like ? ", "%"+StringUtils.trim(tempOperator.getJobNo())+"%");
//			}
//			//专业特长
//			if (StringUtils.isNotBlank(tempOperator.getSpecialty())) {
//				hqlBuilderUtil.addWhereClause(" this.specialty like ? ", "%"+StringUtils.trim(tempOperator.getSpecialty())+"%");
//			}
//			//工作定位
//			if (StringUtils.isNotBlank(tempOperator.getWorkSpecialization())) {
//				hqlBuilderUtil.addWhereClause(" this.workSpecialization like ? ", "%"+StringUtils.trim(tempOperator.getWorkSpecialization())+"%");
//			}
//			//级别
//			if(tempOperator.getLevel() != null && tempOperator.getLevel().getId() != null){
//				hqlBuilderUtil.addWhereClause(" this.level.id = ? ", tempOperator.getLevel().getId());
//			}
//			//学历
//			if(tempOperator.getEducation() != null && tempOperator.getEducation().getId() != null){
//				hqlBuilderUtil.addWhereClause(" this.education.id = ? ", tempOperator.getEducation().getId());
//			}
//			//学校
//			if(StringUtils.isNotBlank(tempOperator.getSchool())){
//				hqlBuilderUtil.addWhereClause(" this.school like ? ", "%"+StringUtils.trim(tempOperator.getSchool())+"%");
//			}
//			//工作经历
//			if(StringUtils.isNotBlank(tempOperator.getWorkExperience())){
//				hqlBuilderUtil.addWhereClause(" this.workExperience like ? ", "%"+StringUtils.trim(tempOperator.getWorkExperience())+"%");
//			}
//			//个人涉外情况
//			if(StringUtils.isNotBlank(tempOperator.getPersonalForeignSituation())){
//				hqlBuilderUtil.addWhereClause(" this.personalForeignSituation like ? ", "%"+StringUtils.trim(tempOperator.getPersonalForeignSituation())+"%");
//			}
//			//测试情况一
//			if(StringUtils.isNotBlank(tempOperator.getTestResult1())){
//				hqlBuilderUtil.addWhereClause(" this.testResult1 like ? ", "%"+StringUtils.trim(tempOperator.getTestResult1())+"%");
//			}
//			//备案日期
//			if(tempOperator.getRecordTime() != null){
//				hqlBuilderUtil.addWhereClause(" this.recordTime = ? ", tempOperator.getRecordTime());
//			}
//			//审批日期
//			if(tempOperator.getApproveTime() != null){
//				hqlBuilderUtil.addWhereClause(" this.approveTime = ? ", tempOperator.getApproveTime());
//			}
//			//级别变更
//			if(StringUtils.isNotBlank(tempOperator.getGradeChange())){
//				hqlBuilderUtil.addWhereClause(" this.gradeChange like ? ", "%"+StringUtils.trim(tempOperator.getGradeChange())+"%");
//			}
//			//退出时间 
//			if(tempOperator.getExitTime() != null){
//				hqlBuilderUtil.addWhereClause(" this.exitTime = ? ", tempOperator.getExitTime());
//			}
//			//退出原因
//			if(StringUtils.isNotBlank(tempOperator.getExitCause())){
//				hqlBuilderUtil.addWhereClause(" this.exitCause like ? ", "%"+StringUtils.trim(tempOperator.getExitCause())+"%");
//			}
//			//退出方式
//			if(StringUtils.isNotBlank(tempOperator.getExitWay())){
//				hqlBuilderUtil.addWhereClause(" this.exitWay like ? ", "%"+StringUtils.trim(tempOperator.getExitWay())+"%");
//			}
//			//家庭成员情况
//			if(StringUtils.isNotBlank(tempOperator.getFamilyCase())){
//				hqlBuilderUtil.addWhereClause(" this.familyCase like ? ", "%"+StringUtils.trim(tempOperator.getExitWay())+"%");
//			}
//			//备注
//			if(StringUtils.isNotBlank(tempOperator.getRemark())){
//				hqlBuilderUtil.addWhereClause(" this.remark like ? ", "%"+StringUtils.trim(tempOperator.getExitWay())+"%");
//			}
//			
//		}
//		
//		//单独处理多对多关系的复杂关系
//		if(tempCard != null || StringUtils.isNotBlank(tempParentRealmIds) || StringUtils.isNotBlank(tempChildrenRealmIds) || StringUtils.isNotBlank(tempDataUserFZs)){
////			hqlBuilderUtil.addSelectClause(" this ");
//			//证件
//			if(tempCard != null ){
//				if((tempCard.getCardType() != null && tempCard.getCardType().getId() != null)||(StringUtils.isNotBlank(tempCard.getCardNo()))){
//					hqlBuilderUtil.addJoinClause(" this.cards tempCard ");
//				}
//				if(tempCard.getCardType() != null && tempCard.getCardType().getId() != null){
//					hqlBuilderUtil.addWhereClause(" tempCard.cardType.id = ? ", tempCard.getCardType().getId());
//				}
//				if(StringUtils.isNotBlank(tempCard.getCardNo())){
//					hqlBuilderUtil.addWhereClause(" tempCard.cardNo like ? ", "%"+StringUtils.trim(tempCard.getCardNo())+"%");
//				}
//			}
//			//领域 
//			if(StringUtils.isNotBlank(tempParentRealmIds)){
//				hqlBuilderUtil.addJoinClause(" this.parentRealms tempParentRealm ");
//				hqlBuilderUtil.addWhereClause(" tempParentRealm.id = ? ", Long.parseLong(StringUtils.trim(tempParentRealmIds)));
//			}
//			//子领域
//			if(StringUtils.isNotBlank(tempChildrenRealmIds)){
//				hqlBuilderUtil.addJoinClause(" this.childrenRealms tempChildrenRealm ");
//				hqlBuilderUtil.addWhereClause(" tempChildrenRealm.id = ? ", Long.parseLong(StringUtils.trim(tempChildrenRealmIds)));
//			}
//			//数据中心主管人
//			if(StringUtils.isNotBlank(tempDataUserFZs)){
//				hqlBuilderUtil.addJoinClause(" this.dataUserFZs tempDataUserFZ ");
//				hqlBuilderUtil.addWhereClause(" tempDataUserFZ.id = ? ", Long.parseLong(StringUtils.trim(tempDataUserFZs)));
//			}
//		}
//		
//		hqlBuilderUtil.addOrderByProperty(" this.id ", false);
//		hqlBuilderUtil.addOrderByProperty(" this.approveTime ", false);
//		return hqlBuilderUtil;
//	}
//}
