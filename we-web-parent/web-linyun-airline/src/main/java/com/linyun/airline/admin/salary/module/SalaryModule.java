/**
 * AirlinePolicyModule.java
 * com.linyun.airline.admin.airlinepolicy.module
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.salary.module;

import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.linyun.airline.admin.salary.service.SalaryViewService;
import com.linyun.airline.forms.TSalaryFindForm;
import com.linyun.airline.forms.TSalaryForm;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   孙斌
 * @Date	 2017年3月8日 	 
 */
@IocBean
@At("/admin/salary")
@Filters
public class SalaryModule {
	@Inject
	private SalaryViewService salaryViewService;

	/**
	 * 跳转到list页面
	 * @param sqlForm
	 * @param pager
	 */
	@At
	@Ok("jsp")
	public Object list(@Param("..") final Pager pager) {

		return salaryViewService.selectCondition();
	}

	/**
	 * 列表查询
	 */
	@At
	public Object listData(@Param("..") final TSalaryFindForm findForm, @Param("..") final TSalaryForm sqlForm) {

		return salaryViewService.listPage4Datatables(findForm, sqlForm);
	}

}