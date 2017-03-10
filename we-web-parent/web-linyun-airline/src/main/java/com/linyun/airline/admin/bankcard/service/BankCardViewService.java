/**
 * BankCardViewService.java
 * com.linyun.airline.admin.bankcard.service
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.bankcard.service;

import static com.uxuexi.core.common.util.ExceptionUtil.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.Param;

import com.google.common.collect.Maps;
import com.linyun.airline.common.enums.BankCardStatusEnum;
import com.linyun.airline.entities.DictInfoEntity;
import com.linyun.airline.entities.TBankCardEntity;
import com.linyun.airline.entities.TCompanyEntity;
import com.linyun.airline.forms.TBankCardForm;
import com.uxuexi.core.common.util.BeanUtil;
import com.uxuexi.core.common.util.MapUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.dao.IDbDao;
import com.uxuexi.core.web.base.page.OffsetPager;
import com.uxuexi.core.web.base.service.BaseService;
import com.uxuexi.core.web.chain.support.JsonResult;
import com.uxuexi.core.web.form.DataTablesParamForm;
import com.uxuexi.core.web.util.FormUtil;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   孙斌
 * @Date	 2017年3月2日 	 
 */
@IocBean
public class BankCardViewService extends BaseService<TBankCardEntity> {

	/**
	 * 注入容器中的dbDao对象，用于数据库查询、持久操作
	 */
	@Inject
	private IDbDao dbDao;

	public Map<String, Object> list() {
		Map<String, Object> maps = Maps.newHashMap();
		//查询有哪些银行
		List<DictInfoEntity> bankList = dbDao.query(DictInfoEntity.class, Cnd.where("typeCode", "=", "YH"), null);
		//查询有哪些银行卡类型
		List<DictInfoEntity> bankCardTypeList = dbDao.query(DictInfoEntity.class, Cnd.where("typeCode", "=", "YHKLX"),
				null);
		//查询有哪些币种
		List<DictInfoEntity> moneyTypeList = dbDao.query(DictInfoEntity.class, Cnd.where("typeCode", "=", "BZ"), null);
		maps.put("bankList", bankList);
		maps.put("bankCardTypeList", bankCardTypeList);
		maps.put("moneyTypeList", moneyTypeList);
		return maps;
	}

	public Object updateBankCardinfo(int userId, HttpSession session) {

		//按id查询
		Map<String, Object> obj = this.list();
		TBankCardEntity bankCardInfo = dbDao.fetch(TBankCardEntity.class, userId);
		obj.put("bankCardInfo", bankCardInfo);
		return obj;

	}

	/**
	 * (non-Javadoc)
	 * @param session 
	 * @see com.uxuexi.core.web.base.service.BaseService#listPage4Datatables(com.uxuexi.core.web.form.DataTablesParamForm)
	 */

	public Map<String, Object> listPage4Datatables(DataTablesParamForm sqlParamForm, HttpSession session) {

		checkNull(sqlParamForm, "sqlParamForm不能为空");

		String sqlString = sqlManager.get("bankcardmanager_find_money");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		/*Long companyId = 23l;*/
		TCompanyEntity company = (TCompanyEntity) session.getAttribute("user_company");
		Long companyId = company.getId();
		cnd.and("companyId", "=", companyId);
		cnd.and("status", "=", BankCardStatusEnum.ENABLE.intKey());
		sql.setCondition(cnd);
		Pager pager = new OffsetPager(sqlParamForm.getStart(), sqlParamForm.getLength());
		pager.setRecordCount((int) Daos.queryCount(nutDao, sql.toString()));

		sql.setPager(pager);

		sql.setCallback(Sqls.callback.records());
		nutDao.execute(sql);

		@SuppressWarnings("unchecked")
		List<Record> list = (List<Record>) sql.getResult();

		Map<String, Object> re = MapUtil.map();
		re.put("data", list);
		re.put("draw", sqlParamForm.getDraw());
		re.put("recordsTotal", pager.getPageSize());
		re.put("recordsFiltered", pager.getRecordCount());
		return re;

	}

	/*保存银行卡*/
	public Object addBankCard(@Param("..") TBankCardAddForm addForm, final HttpSession session) {
		/**
		 * 获取公司的id
		 * 
		 */
		TCompanyEntity company = (TCompanyEntity) session.getAttribute("user_company");
		Long companyId = company.getId();
		//Long companyId = 23l;
		addForm.setCreateTime(new Date());
		addForm.setUpdateTime(new Date());
		addForm.setCompanyId(companyId);
		addForm.setStatus(BankCardStatusEnum.ENABLE.intKey());
		FormUtil.add(dbDao, addForm, TBankCardEntity.class);
		return null;
	}

	public Object updateData(TBankCardForm updateForm, HttpSession session) {

		Map<String, Object> obj = Maps.newHashMap();
		if (!Util.isEmpty(updateForm)) {
			updateForm.setStatus(BankCardStatusEnum.ENABLE.intKey());
			updateForm.setUpdateTime(new Date());
		}
		TBankCardEntity bankCard = new TBankCardEntity();
		BeanUtil.copyProperties(updateForm, bankCard);
		updateIgnoreNull(bankCard);//更新银行卡表中的数据
		return obj;
	}

	public Object deleteUserInfo(int bankCardId) {

		TBankCardEntity bankCard = dbDao.fetch(TBankCardEntity.class, bankCardId);
		//校验,如果银行卡状态是处于启用,那么更新银行卡中用户的状态为禁用
		if (!Util.isEmpty(bankCard)) {
			//更新用户表中状态
			nutDao.update(TBankCardEntity.class, Chain.make("status", BankCardStatusEnum.DISENABLE.intKey()),

			Cnd.where("id", "=", bankCardId));//禁用

			return JsonResult.success("删除成功");
		}
		return JsonResult.error("删除失败");

	}

	//校验银行卡号
	public Object checkBankCardNumExist(String cardNum, String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		int count = 0;
		if (Util.isEmpty(id)) {
			//添加时校验
			count = nutDao.count(TBankCardEntity.class, Cnd.where("cardNum", "=", cardNum));
		} else {
			//更新时校验
			count = nutDao.count(TBankCardEntity.class, Cnd.where("cardNum", "=", cardNum).and("id", "!=", id));
		}
		map.put("valid", count <= 0);
		return map;
	}

}
