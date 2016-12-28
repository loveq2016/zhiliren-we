package com.linyun.airline.admin.area.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.entity.Record;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.linyun.airline.admin.user.service.UserViewService;
import com.linyun.airline.common.result.Select2Option;
import com.linyun.airline.entities.TAreaEntity;
import com.linyun.airline.forms.TAreaAddForm;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class AreaViewService extends BaseService<TAreaEntity> {

	@Inject
	private UserViewService userViewService;

	//校验名称名称唯一性
	public Object checkAreaNameExist(final String areaName, final Long areaId) {
		Map<String, Object> map = new HashMap<String, Object>();
		int count = 0;
		if (Util.isEmpty(areaId)) {
			//add
			count = nutDao.count(TAreaEntity.class, Cnd.where("areaName", "=", areaName));
		} else {
			//update
			count = nutDao.count(TAreaEntity.class, Cnd.where("areaName", "=", areaName).and("id", "!=", areaId));
		}
		map.put("valid", count <= 0);
		return map;
	}

	//区域
	public Object areaSelect2(String dictAreaName, final String selectedAreaIds) {
		List<Record> dictNameList = userViewService.getAreaNameList(dictAreaName, selectedAreaIds);
		List<Select2Option> result = transform2SelectOptions(dictNameList);
		return result;
	}

	private List<Select2Option> transform2SelectOptions(List<Record> dictNameList) {
		return Lists.transform(dictNameList, new Function<Record, Select2Option>() {
			@Override
			public Select2Option apply(Record record) {
				Select2Option op = new Select2Option();
				op.setId(record.getInt("areaId"));
				op.setText(record.getString("areaName"));
				return op;
			}
		});
	}

	//区域名称保存
	public Object addAreaName(TAreaAddForm addForm) {
		TAreaEntity areaEntity = this.add(addForm);
		return dbDao.insert(areaEntity);
	}

}