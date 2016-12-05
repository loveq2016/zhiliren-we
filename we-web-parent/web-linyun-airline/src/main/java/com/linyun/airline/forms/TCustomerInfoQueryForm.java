/**
 * TCustomerInfoQueryForm.java
 * com.linyun.airline.forms
 * Copyright (c) 2016, 北京科技有限公司版权所有.
 */

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   彭辉
 * @Date	 2016年11月20日 	 
 */
package com.linyun.airline.forms;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.form.DataTablesParamForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TCustomerInfoQueryForm extends DataTablesParamForm {

	/**客户 电话 负责人*/
	private String name;

	/**是否签约（未签约、已签约、禁止合作）*/
	private String contract;

	/**是否禁用*/
	private String forbid;

	/**是否禁用*/
	private long id;

	/**负责人姓名*/
	private String agentName;

	/*时间*/
	private Date createTime;

	public Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		//TODO 添加自定义查询条件（可选）
		if (!Util.isEmpty(name)) {
			cnd.and("t.name", "LIKE", "%" + name + "%").or("u.userName", "LIKE", "%" + name + "%")
					.or("t.telephone", "LIKE", "%" + name + "%");
		}
		if (!Util.isEmpty(contract)) {
			cnd.and("t.contract", "=", contract);
		}
		if (!Util.isEmpty(forbid)) {
			cnd.and("t.forbid", "=", forbid);
		}
		if (!Util.isEmpty(createTime)) {
			cnd.orderBy("t.createTime", "DESC");
		}
		if (!Util.isEmpty(id)) {
			cnd.orderBy("t.id", "DESC");
		}
		return cnd;
	}

	/**
	 * (non-Javadoc)
	 * @see com.uxuexi.core.web.form.SQLParamForm#sql(org.nutz.dao.SqlManager)
	 */
	@Override
	public Sql sql(SqlManager sqlManager) {
		String sqlString = sqlManager.get("customer_list_info");
		//String sqlString = EntityUtil.entityCndSql(TCustomerInfoEntity.class);
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;

	}

}