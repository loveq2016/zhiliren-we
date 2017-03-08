package com.linyun.airline.admin.operationsArea.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.linyun.airline.admin.customer.service.CustomerViewService;
import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.admin.operationsArea.form.TMessageAddForm;
import com.linyun.airline.admin.operationsArea.form.TMessageUpdateForm;
import com.linyun.airline.common.enums.MessageIsRemindEnum;
import com.linyun.airline.common.enums.MessageLevelEnum;
import com.linyun.airline.common.enums.MessageRemindEnum;
import com.linyun.airline.common.enums.MessageSourceEnum;
import com.linyun.airline.common.enums.MessageStatusEnum;
import com.linyun.airline.common.enums.MessageTypeEnum;
import com.linyun.airline.common.enums.MessageUserEnum;
import com.linyun.airline.entities.TCheckboxStatusEntity;
import com.linyun.airline.entities.TCompanyEntity;
import com.linyun.airline.entities.TMessageEntity;
import com.linyun.airline.entities.TUserEntity;
import com.linyun.airline.entities.TUserMsgEntity;
import com.uxuexi.core.common.util.DateTimeUtil;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.JsonUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class OperationsAreaViewService extends BaseService<TMessageEntity> {
	private static final Log log = Logs.get();

	//询单消息状态
	private static final int SEARCHORDERS = MessageTypeEnum.SEARCHMSG.intKey();
	//订单消息状态
	private static final int BOOKORDERS = MessageTypeEnum.BOOKMSG.intKey();
	private static final int FIRBOOK = MessageTypeEnum.FIRBOOKMSG.intKey();
	private static final int SECBOOK = MessageTypeEnum.SECBOOKMSG.intKey();
	private static final int THRBOOK = MessageTypeEnum.THRBOOKMSG.intKey();
	private static final int ALLBOOK = MessageTypeEnum.ALLBOOKMSG.intKey();
	private static final int LASTBOOK = MessageTypeEnum.LASTBOOKMSG.intKey();
	private static final int BOOKMSG = MessageTypeEnum.BOOKMSG.intKey();
	//TODO 任务消息状态

	//消息提醒模式
	private static final int MOUTH = MessageRemindEnum.MOUTH.intKey();
	private static final int WEEK = MessageRemindEnum.WEEK.intKey();
	private static final int DAY = MessageRemindEnum.DAY.intKey();
	private static final int HOUR = MessageRemindEnum.HOUR.intKey();
	private static final int FIFTEENM = MessageRemindEnum.FIFTEENM.intKey();
	private static final int THIRTYM = MessageRemindEnum.THIRTYM.intKey();
	private static final int TIMED = MessageRemindEnum.TIMED.intKey();

	/**
	 * 注入容器中的Service对象
	 */
	@Inject
	private CustomerViewService customerViewService;

	/**
	 * 
	 * 添加自定义事件
	 * <p>
	 *
	 * @param addForm
	 * @return 
	 */
	public Object addCustomEvent(TMessageAddForm addForm, HttpSession session) {
		//当前用户id
		TUserEntity loginUser = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		long id = loginUser.getId();

		//消息类型
		addForm.setMsgType(MessageTypeEnum.CUSTOMMSG.intKey());
		//消息状态默认为 （1：表示未删除）
		addForm.setMsgStatus(1);
		//消息优先级  MSGLEVEL1.intKey()表示等级最低
		addForm.setPriorityLevel(MessageLevelEnum.MSGLEVEL1.intKey());
		//消息是否提醒
		addForm.setIsRemind(Long.valueOf(MessageIsRemindEnum.YES.intKey()));
		//消息提醒模式
		addForm.setReminderMode(Long.valueOf(MessageRemindEnum.TIMED.intKey()));

		//格式化日期
		String generateTimeStr = addForm.getGenerateTimeString();
		if (!Util.isEmpty(generateTimeStr)) {
			addForm.setGenerateTime(DateUtil.string2Date(generateTimeStr, "yyyy-MM-dd hh:mm:ss"));
		}
		//添加消息数据
		TMessageEntity tMessageEntity = this.add(addForm);

		//操作关系表
		TUserMsgEntity tUserMsgEntity = new TUserMsgEntity();
		//消息ID
		tUserMsgEntity.setMsgId(Long.valueOf(tMessageEntity.getId()));
		//用户ID
		tUserMsgEntity.setUserId(id);
		//用户类型
		tUserMsgEntity.setUserType(Long.valueOf(MessageUserEnum.PERSONAL.intKey()));
		//来源方ID   
		tUserMsgEntity.setFromId(id);
		//来源方类型  自定义事件
		tUserMsgEntity.setMsgSource(Long.valueOf(MessageSourceEnum.PERSONALMSG.intKey()));
		//是否阅读  默认为未读状态
		tUserMsgEntity.setIsRead(Long.valueOf(MessageStatusEnum.UNREAD.intKey()));
		//自定义事件的时间
		tUserMsgEntity.setSendTime(addForm.getGenerateTime());
		TUserMsgEntity entity = dbDao.insert(tUserMsgEntity);

		return JsonUtil.toJson(entity);
	}

	/**
	 * 
	 * 到更新自定义事件
	 * <p>
	 *
	 * @param id
	 * @return 
	 */
	public Object toUpdateCustomEvent(Long id) {
		Map<String, Object> obj = new HashMap<String, Object>();
		//查询自定义消息
		TMessageEntity tMessage = dbDao.fetch(TMessageEntity.class, id);
		obj.put("message", tMessage);
		return obj;
	}

	/**
	 * 
	 * 更新自定义事件
	 * <p>
	 * @param messageUpdateForm
	 * @return 
	 */
	public Object updateCustom(TMessageUpdateForm messageUpdateForm) {
		TMessageEntity tMessage = dbDao.fetch(TMessageEntity.class, messageUpdateForm.getId());
		tMessage.setMsgContent(messageUpdateForm.getMsgContent());
		tMessage.setGenerateTime(DateUtil.string2Date(messageUpdateForm.getGenerateTimeString(), "yyyy-MM-dd hh:mm:ss"));
		messageUpdateForm.setMsgType(MessageTypeEnum.CUSTOMMSG.intKey());
		messageUpdateForm.setPriorityLevel(MessageLevelEnum.MSGLEVEL1.intKey());
		//消息是否提醒
		messageUpdateForm.setIsRemind(Long.valueOf(MessageIsRemindEnum.YES.intKey()));
		//消息提醒模式
		messageUpdateForm.setReminderMode(Long.valueOf(MessageRemindEnum.TIMED.intKey()));
		return dbDao.update(tMessage);
	}

	public Object deleteCustom(TMessageUpdateForm messageUpdateForm) {
		TMessageEntity tMessage = dbDao.fetch(TMessageEntity.class, messageUpdateForm.getId());
		tMessage.setMsgStatus(0L);
		return dbDao.update(tMessage);
	}

	/**
	 * 
	 * 查询自定义事件
	 * <p>
	 *
	 * @param addForm
	 * @return 
	 */
	public Object getCustomEvent(HttpSession session, String start, String end) {
		//当前用户id
		TUserEntity loginUser = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		long id = loginUser.getId();
		int msgstatus = 1;
		Sql sql = Sqls.create(sqlManager.get("msg_user_company"));
		if (!Util.isEmpty(id)) {
			sql.params().set("userId", id);
		}
		if (!Util.isEmpty(start)) {
			sql.params().set("start", start);
		}
		if (!Util.isEmpty(end)) {
			sql.params().set("end", end);
		}
		sql.params().set("msgStatus", msgstatus);
		Cnd cnd = Cnd.NEW();
		cnd.orderBy("m.priorityLevel", "desc");
		sql.setCondition(cnd);
		sql.setCallback(Sqls.callback.records());
		List<Record> rList = dbDao.query(sql, cnd, null);
		String jsonStr = JsonUtil.toJson(rList);
		jsonStr = jsonStr.replaceAll("generatetime", "start");
		jsonStr = jsonStr.replaceAll("msgcontent", "title");

		return jsonStr;
	}

	/**
	 * 查询 任务事件
	 */
	public Object getTaskEvents(HttpSession session) {

		//当前用户id
		TUserEntity loginUser = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		long id = loginUser.getId();

		//DOTO 当前没有代理商任务， 所以查询自定义事件
		Sql sql = Sqls.create(sqlManager.get("msg_user_company_task"));
		if (!Util.isEmpty(id)) {
			sql.params().set("userId", id);
		}
		sql.params().set("msgSource", 1);
		/*
		 * 当前时间+30分钟
		long millis = DateTimeUtil.millis();
		millis += 30 * 60 * 1000;
		DateTime dateTime = DateUtil.dateTime(new Date(millis));*/

		//sql.params().set("now", DateTimeUtil.now());
		sql.setCallback(Sqls.callback.records());
		List<Record> records = dbDao.query(sql, null, null); //查询自定义的结果

		int size = records.size();
		for (Record record : records) {
			record.set("num", size);
		}
		return JsonUtil.toJson(records);
	}

	/**
	 ***********************账期******************
	 */
	public Object getPayTypeTerm(HttpSession session) {

		//当前用户id
		TUserEntity loginUser = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		long loginUserId = loginUser.getId();
		int msgType = MessageTypeEnum.PROCESSMSG.intKey(); //消息类型
		Sql sql = Sqls.create(sqlManager.get("operationsArea_taskList_customerInfo"));
		if (!Util.isEmpty(loginUserId)) {
			sql.params().set("userId", loginUserId);
		}
		sql.params().set("msgType", msgType);
		sql.setCallback(Sqls.callback.records());
		List<Record> records = dbDao.query(sql, null, null); //查询自定义的结果

		//根据提醒模式，筛选结果集    1 月  2周 3天 4小时 5分 6定时提醒
		List<Record> recordsByCondition = new ArrayList<Record>();
		for (Record record : records) {
			String reminderMode = record.getString("reminderMode"); //当前消息的提醒模式
			String generateDate = record.getString("generatetime"); //当前消息的时间
			Date nowDate = DateUtil.nowDate();
			if (String.valueOf(MOUTH).equals(reminderMode)) {
				//每自然月1号提醒
				int day = DateUtil.getDay(nowDate);
				if (day == 1) {
					recordsByCondition.add(record);
				}
			}
			if (String.valueOf(WEEK).equals(reminderMode)) {
				//每自然周一提醒
				Date firstWeekDay = DateUtil.getFirstWeekDay(nowDate);
				long millisBetween = DateUtil.millisBetween(firstWeekDay, nowDate);
				if (millisBetween == 0) {
					recordsByCondition.add(record);
				}
			}
		}
		int size = recordsByCondition.size();
		for (Record record : recordsByCondition) {
			record.set("num", size);
		}
		return JsonUtil.toJson(recordsByCondition);
	}

	/**
	 * 
	 * 任务栏 询单、订单、任务提醒事件
	 * <p>
	 * 
	 *
	 * @param orderType  订单状态 
	 * 							queryOrders		询单： 查询
	 * 				 值可能为：	bookOrders		订单： 一订、二订、三订、全款、尾款
	 *                   	    taskNotice    任务： 
	 * 
	 * @param session    获取当前登陆用户
	 * @return 
	 */
	public Object getOrderMsgs(String orderType, HttpSession session) {

		//当前用户id
		TUserEntity loginUser = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		long loginUserId = loginUser.getId();

		//查询当前公司下 会计id
		TCompanyEntity companyEntity = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Sql accountSql = Sqls.create(sqlManager.get("customer_search_accounter"));
		accountSql.setParam("jobName", "会计");
		accountSql.setParam("compId", companyEntity.getId());
		List<Record> accountingIds = dbDao.query(accountSql, null, null);
		//消息展示方id
		ArrayList<Long> sendUserIds = Lists.newArrayList();
		if (!Util.isEmpty(accountingIds)) {
			for (Record record : accountingIds) {
				long accountingId = Long.parseLong(record.getString("userId"));
				sendUserIds.add(accountingId);
			}
		}
		sendUserIds.add(loginUserId); //TODO

		//消息类型(默认为关闭状态)
		String msgType = "0";
		switch (orderType) {
		case "queryOrders":
			//询单
			msgType = String.valueOf(SEARCHORDERS);
			break;
		case "bookOrders":
			//订单
			msgType = String.valueOf(BOOKMSG) + "," + String.valueOf(FIRBOOK) + "," + String.valueOf(SECBOOK) + ","
					+ String.valueOf(THRBOOK) + "," + String.valueOf(ALLBOOK) + "," + String.valueOf(LASTBOOK);
			break;
		case "taskNotice":
			//任务 TODO
			break;
		}

		/***********************************任务栏 消息列表******************************************/
		Sql sql = Sqls.create(sqlManager.get("operationsArea_order_msg"));
		Cnd cnd = Cnd.NEW();
		cnd.and("um.userId", "=", loginUserId);
		cnd.and("m.msgType", "in", msgType);
		List<Record> records = dbDao.query(sql, cnd, null);
		List<Record> recordsByCondition = new ArrayList<Record>();

		for (Record record : records) {
			String reminderMode = record.getString("reminderMode"); //当前消息的提醒模式
			String generateDate = record.getString("generatetime"); //当前消息的时间
			Date nowDate = DateUtil.nowDate();
			if (String.valueOf(MOUTH).equals(reminderMode)) {
				//每自然月1号提醒
				int day = DateUtil.getDay(nowDate);
				if (day == 1) {
					recordsByCondition.add(record);
				}
			}
			if (String.valueOf(WEEK).equals(reminderMode)) {
				//每自然周一提醒
				Date firstWeekDay = DateUtil.getFirstWeekDay(nowDate);
				long millisBetween = DateUtil.millisBetween(firstWeekDay, nowDate);
				if (millisBetween == 0) {
					recordsByCondition.add(record);
				}
			}
			if (String.valueOf(DAY).equals(reminderMode)) {
				//每1天提醒 TODO
				recordsByCondition.add(record);
			}
			if (String.valueOf(HOUR).equals(reminderMode)) {
				//每1小时提醒 TODO
				recordsByCondition.add(record);
			}
			if (String.valueOf(THIRTYM).equals(reminderMode)) {
				//每30分钟提醒 TODO
				recordsByCondition.add(record);
			}
			if (String.valueOf(FIFTEENM).equals(reminderMode)) {
				//每15分钟提醒 TODO
				recordsByCondition.add(record);
			}
			if (String.valueOf(TIMED).equals(reminderMode)) {
				//自定义提醒
				String generatetime = record.getString("generatetime");
				long generateMillis = DateTimeUtil.string2DateTime(generatetime, "").getMillis();
				long nowMillis = DateTimeUtil.now().getMillis();
				long a = generateMillis - nowMillis;
				if (a < 0) {
					recordsByCondition.add(record);
				}
			}
		}

		int size = recordsByCondition.size();
		for (Record record : recordsByCondition) {
			record.set("num", size);
		}
		return JsonUtil.toJson(recordsByCondition);
	}

	/**
	 * 自定义界面设置
	 */
	@Aop("txDb")
	public Object setCheckBox(HttpSession session, String checkboxname) {
		TUserEntity loginUser = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		long userId = loginUser.getId();

		Map<String, Object> obj = new HashMap<String, Object>();
		Iterable<String> checkS = Splitter.on(",").split(checkboxname);
		TCheckboxStatusEntity checkEntity = new TCheckboxStatusEntity();

		//查询用户是否存在
		if (!Util.isEmpty(userId)) {
			checkEntity = dbDao.fetch(TCheckboxStatusEntity.class, Cnd.where("userId", "=", userId));
		}
		long task = 0;
		long maxC = 0;
		long minC = 0;
		//设置状态值
		if (!Util.isEmpty(checkS)) {
			for (String s : checkS) {
				if ("task".equals(s)) {
					task = 1;
				}
				if ("maxC".equals(s)) {
					maxC = 1;
				}
				if ("minC".equals(s)) {
					minC = 1;
				}
			}
		}
		checkEntity.setTaskShow(Long.valueOf(task)); //任务栏被勾选
		checkEntity.setMaxCShow(Long.valueOf(maxC)); //大日历被勾选
		checkEntity.setMinCShow(Long.valueOf(minC)); //小日历被勾选
		//判断id是否存在， 存在则更新
		if (!Util.isEmpty(userId)) {
			dbDao.update(checkEntity);
		} else {
			dbDao.insert(checkEntity);
		}
		obj.put("success", "保存成功");
		return obj;
	}

	/**
	 * 自定义界面获取
	 */
	public Object getCheckBox(HttpSession session) {
		TUserEntity loginUser = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		long userId = loginUser.getId();
		TCheckboxStatusEntity checkbox = dbDao.fetch(TCheckboxStatusEntity.class, Cnd.where("userId", "=", userId));
		if (Util.isEmpty(checkbox)) {
			TCheckboxStatusEntity check = new TCheckboxStatusEntity();
			check.setUserId(userId);
			check.setTaskShow(Long.valueOf(1));
			check.setMaxCShow(Long.valueOf(1));
			check.setMinCShow(Long.valueOf(1));
			dbDao.insert(check);
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		TCheckboxStatusEntity checkBoxEntity = dbDao.fetch(TCheckboxStatusEntity.class,
				Cnd.where("userId", "=", userId));
		obj.put("checkBox", checkBoxEntity);
		return obj;
	}

	/**
	 * 
	 * 查询指定月 每天的自定义事件数
	 * <p>
	 * TODO(以后查询 飞机票相关的事件)
	 *
	 * @param id
	 * @param timeStr   格式"2016-10","2016-12","2017-11"
	 * @return 
	 */
	public Object getMinCalList(HttpSession session, String timeStr) {
		//当前用户id
		TUserEntity loginUser = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		long id = loginUser.getId();

		Sql sql = Sqls.create(sqlManager.get("msg_type_list"));
		Date date1 = DateUtil.string2Date(timeStr);
		Date date2 = DateUtil.addMonth(date1, 1);
		Date date3 = DateUtil.addMonth(date1, 2);
		if (!Util.isEmpty(id)) {
			sql.params().set("userId", id);
		}
		sql.params().set("MincalTimes1", date1);
		sql.params().set("MincalTimes2", date2);
		sql.params().set("MincalTimes3", date3);
		sql.params().set("msgStatus", 1);
		sql.params().set("userid", id);
		sql.setCallback(Sqls.callback.records());

		Set<String> set = new HashSet<String>();

		List<Record> rList = dbDao.query(sql, null, null);
		for (Record record : rList) {
			set.add(record.getString("gtime"));
		}
		List<Map<String, Object>> resultlist = new ArrayList<Map<String, Object>>();
		for (String datestr : set) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("gtime", datestr);
			String msgcontent = "";
			for (Record r : rList) {
				if (datestr.equals(r.getString("gtime"))) {
					msgcontent += r.getString("msgcontent") + "<br/>";
				}
			}
			map.put("msgcontent", msgcontent);
			resultlist.add(map);
		}
		return JsonUtil.toJson(resultlist);
	}

	/**
	 * 
	 * 查询当天的自定义事件
	 * <p>
	 * TODO(以后查询 飞机票相关的事件)
	 *
	 * @param id
	 * @param timeStr   格式"2016-10"
	 * @return 
	 */
	public Object getMinCal(HttpSession session, String timeStr) {
		//当前用户id
		TUserEntity loginUser = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		long id = loginUser.getId();

		Sql sql = Sqls.create(sqlManager.get("msg_type"));
		Date date1 = DateUtil.string2Date(timeStr);
		if (!Util.isEmpty(id)) {
			sql.params().set("userId", id);
		}
		sql.params().set("MincalTimes1", date1);
		sql.params().set("msgStatus", 1);
		List<Record> rList = dbDao.query(sql, null, null);

		Set<String> set = new HashSet<String>();
		for (Record record : rList) {
			set.add(record.getString("gtime"));
		}
		List<Map<String, Object>> resultlist = new ArrayList<Map<String, Object>>();
		for (String datestr : set) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("gtime", datestr);
			String msgcontent = "";
			for (Record r : rList) {
				if (datestr.equals(r.getString("gtime"))) {
					msgcontent += r.getString("msgcontent") + "<br/>";
				}
			}
			map.put("msgcontent", msgcontent);
			resultlist.add(map);
		}
		return JsonUtil.toJson(resultlist);
	}
}