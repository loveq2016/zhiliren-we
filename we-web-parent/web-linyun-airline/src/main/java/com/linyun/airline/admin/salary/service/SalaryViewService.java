/**
 * AirlinePolicyService.java
 * com.linyun.airline.admin.airlinepolicy.service
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.salary.service;

import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.Daos;
import org.nutz.mvc.annotation.Param;

import com.google.common.collect.Maps;
import com.linyun.airline.entities.TSalaryEntity;
import com.linyun.airline.forms.TSalaryFindForm;
import com.uxuexi.core.common.util.MapUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.page.OffsetPager;
import com.uxuexi.core.web.base.service.BaseService;
import com.uxuexi.core.web.form.DataTablesParamForm;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author  孙斌
 * @Date	 2017年3月8日 	 
 */
public class SalaryViewService extends BaseService<TSalaryEntity> {

	public Map<String, Object> listPage4Datatables(@Param("..") final TSalaryFindForm findForm,
			DataTablesParamForm sqlParamForm) {

		String sqlString = sqlManager.get("salary_list");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		Integer year = findForm.getYear();
		Integer month = findForm.getMonth();
		String drawer = findForm.getDrawer();
		if (!Util.isEmpty(year)) {
			/*cnd.and("companyId", "=", companyId);*/
			cnd.and("years", "=", year);
			//sql.setCondition(cnd);
		}
		if (!Util.isEmpty(month)) {

			/*cnd.and("companyId", "=", companyId);*/
			cnd.and("months", "=", month);
			//sql.setCondition(cnd);
		}

		if (!Util.isEmpty(drawer)) {

			/*cnd.and("companyId", "=", companyId);*/
			cnd.and("drawer", "=", drawer);
			//sql.setCondition(cnd);
		}
		cnd.orderBy("updateTime", "DESC");

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

	public Map<String, Object> selectCondition() {
		Map<String, Object> maps = Maps.newHashMap();
		String drawerString = sqlManager.get("salary_drawer");
		Sql drawerSql = Sqls.create(drawerString);
		Cnd cnd = Cnd.NEW();
		List<Record> drawerList = dbDao.query(drawerSql, cnd, null);
		String yearsString = sqlManager.get("salary_years");
		Sql yearsSql = Sqls.create(yearsString);
		List<Record> yearsList = dbDao.query(yearsSql, cnd, null);
		String monthsString = sqlManager.get("salary_months");
		Sql monthsSql = Sqls.create(monthsString);
		List<Record> monthsList = dbDao.query(monthsSql, cnd, null);
		Map<String, Object> re = MapUtil.map();
		re.put("drawerList", drawerList);
		re.put("yearsList", yearsList);
		re.put("monthsList", monthsList);
		return re;

	}

}