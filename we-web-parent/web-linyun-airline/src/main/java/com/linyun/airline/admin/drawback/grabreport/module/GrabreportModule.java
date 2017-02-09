package com.linyun.airline.admin.drawback.grabreport.module;

import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.linyun.airline.admin.drawback.grabreport.form.TGrabReportAddForm;
import com.linyun.airline.admin.drawback.grabreport.form.TGrabReportSqlForm;
import com.linyun.airline.admin.drawback.grabreport.form.TGrabReportUpdateForm;
import com.linyun.airline.admin.drawback.grabreport.service.GrabreportViewService;
import com.uxuexi.core.web.base.page.Pagination;
import com.uxuexi.core.web.chain.support.JsonResult;

@IocBean
@At("/admin/grabreport")
public class GrabreportModule {

	@Inject
	private GrabreportViewService grabreportViewService;

	/**
	 * 分页查询
	 */
	@At
	@Ok("jsp")
	public Pagination list(@Param("..") final TGrabReportSqlForm sqlParamForm, @Param("..") final Pager pager) {
		return grabreportViewService.listPage(sqlParamForm, pager);
	}

	/**
	 * 跳转到'添加操作'的录入数据页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object add() {
		return null;
	}

	/**
	 * 添加
	 */
	@At
	@POST
	public Object add(@Param("..") TGrabReportAddForm addForm) {
		return grabreportViewService.add(addForm);
	}

	/**
	 * 跳转到'修改操作'的录入数据页面,实际就是[按照主键查询单个实体]
	 */
	@At
	@GET
	@Ok("jsp")
	public Object update(@Param("id") final long id) {
		return grabreportViewService.fetch(id);
	}

	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object update(@Param("..") TGrabReportUpdateForm updateForm) {
		return grabreportViewService.update(updateForm);
	}

	/**
	 * 删除记录
	 */
	@At
	public Object delete(@Param("id") final long id) {
		grabreportViewService.deleteById(id);
		return JsonResult.success("删除成功");
	}

	/**
	 * 批量删除记录
	 */
	@At
	public Object batchDelete(@Param("ids") final Long[] ids) {
		grabreportViewService.batchDelete(ids);
		return JsonResult.success("删除成功");
	}

}