/**
 * TPayOrderEntity.java
 * com.linyun.airline.admin.receivePayment.entities
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.receivePayment.entities;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年3月18日 	 
 */
@Data
@Table("t_pay_order")
public class TPayOrderEntity {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column 
	@Comment("payid")
	private Integer payid;

	@Column
	@Comment("orderid")
	private Integer orderid;

	@Column
	@Comment("orderpaystatus")
	private Integer orderpaystatus;
}