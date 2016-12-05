package com.linyun.airline.admin.authority.companyfunction.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.loader.annotation.IocBean;

import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.linyun.airline.admin.authority.companyfunction.form.TCompanyModForm;
import com.linyun.airline.admin.authority.function.entity.TFunctionEntity;
import com.linyun.airline.common.enums.CompanyTypeEnum;
import com.linyun.airline.entities.TCompanyEntity;
import com.linyun.airline.entities.TCompanyFunctionMapEntity;
import com.linyun.airline.entities.TUserEntity;
import com.linyun.airline.forms.TCompanyUpdateForm;
import com.uxuexi.core.common.util.EnumUtil;
import com.uxuexi.core.common.util.MapUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * @author   崔建斌
 * @Date	 2016年11月18日 	 
 */
@IocBean(name = "companyAuthorityService")
public class CompanyAuthorityService extends BaseService<TCompanyEntity> {

	/**
	 * 根据请求路径查询功能
	 * @param requestPath
	 * @return
	 */
	public TFunctionEntity findFuctionByRequestPath(String requestPath) {
		return dbDao.fetch(TFunctionEntity.class, Cnd.where("url", "LIKE", "%" + requestPath + "%"));
	}

	/**修改公司功能关系*/
	public void updateCompanyFunctionMap(TCompanyModForm form) {
		String functionIds = form.getFunctionIds();
		//根据公司查询关系
		Long comId = form.getId();
		List<TCompanyFunctionMapEntity> before = dbDao.query(TCompanyFunctionMapEntity.class,
				Cnd.where("comId", "=", comId), null);
		//欲更新为
		List<TCompanyFunctionMapEntity> after = new ArrayList<TCompanyFunctionMapEntity>();
		if (!Util.isEmpty(functionIds)) {
			Iterable<String> funcIds = Splitter.on(",").split(functionIds);
			for (String fid : funcIds) {
				TCompanyFunctionMapEntity each = new TCompanyFunctionMapEntity();
				each.setComId(comId);
				each.setFunId(Long.valueOf(fid));
				after.add(each);
			}
		}
		dbDao.updateRelations(before, after);
	}

	public Map<String, Object> findCompany(long id) {
		Map<String, Object> obj = new HashMap<String, Object>();
		//全部功能
		List<TFunctionEntity> allFunc = dbDao.query(TFunctionEntity.class, null, null);
		//根据公司查询关系
		List<TCompanyFunctionMapEntity> relation = dbDao.query(TCompanyFunctionMapEntity.class,
				Cnd.where("comId", "=", id), null);
		//如果公司有功能
		if (!Util.isEmpty(relation)) {
			//公司功能集合
			List<TFunctionEntity> companyFuncs = Lists.transform(relation,
					new Function<TCompanyFunctionMapEntity, TFunctionEntity>() {
						@Override
						public TFunctionEntity apply(TCompanyFunctionMapEntity arg) {
							TFunctionEntity func = new TFunctionEntity();
							func.setId(arg.getFunId());
							return func;
						}
					});
			for (TFunctionEntity f : allFunc) {
				if (companyFuncs.contains(f)) {
					f.setChecked("true");
				}
			}
		}
		obj.put("list", allFunc);
		obj.put("company", dbDao.fetch(TCompanyEntity.class, id));
		return obj;
	}

	/**
	 * 
	 * 获取上游公司和代理商的数量
	 * <p>
	 * TODO
	 *
	 * @param sqlManagerm
	 * @return 返回上游公司、代理商、总数 封装到map中
	 */
	public Map<String, Object> getUpCompanyAndAgentCount(SqlManager sqlManagerm) {
		Map<String, Object> map = MapUtil.map();
		long upconpany = getCompanyCount(CompanyTypeEnum.UPCOMPANY.intKey());
		long agent = getCompanyCount(CompanyTypeEnum.AGENT.intKey());
		map.put("upconpany", upconpany);
		map.put("agent", agent);
		map.put("totalcompany", upconpany + agent);
		return map;
	}

	/**
	 * 
	 * 获取相应公司的数量
	 * <p>
	 * TODO
	 *
	 * @param comType 公司类型id
	 * @return 返回相应数量
	 */
	private long getCompanyCount(int comType) {
		String sqlString = EntityUtil.entityCndSql(TCompanyEntity.class);
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		cnd.and("comType", "=", comType);
		cnd.and("deletestatus", "=", 0);
		sql.setCondition(cnd);
		return Daos.queryCount(nutDao, sql.toString());
	}

	/**
	 * 为修改页面准备信息
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param id
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Map<String, Object> getCompanyPageInfo(long id) {
		Map<String, Object> obj = new HashMap<String, Object>();
		TCompanyEntity companyEntity = this.fetch(id);
		//准备数据
		obj.put("company", companyEntity);
		//准备用户名
		obj.put("telephone", dbDao.fetch(TUserEntity.class, companyEntity.getAdminId()).getTelephone());
		//准备下拉框
		obj.put("companyTypeEnum", EnumUtil.enum2(CompanyTypeEnum.class));
		return obj;
	}

	public Object updateCompany(TCompanyUpdateForm updateForm) {
		//修改管理员用户名
		TUserEntity userEntity = dbDao.fetch(TUserEntity.class, updateForm.getAdminId());
		userEntity.setTelephone(updateForm.getTelephone());
		userEntity.setUserName(updateForm.getComName() + "系统管理员");
		dbDao.update(userEntity);
		//修改公司信息
		updateForm.setLastupdatetime(new Date());
		return this.update(updateForm);
	}
}