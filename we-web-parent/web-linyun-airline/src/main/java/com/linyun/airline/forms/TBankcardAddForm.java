package com.linyun.airline.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.AddForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TBankcardAddForm extends AddForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**银行卡名称*/
	private String cardName;
		
	/**卡号*/
	private String cardNum;
		
	/**银行卡类型*/
	private String bankCardType;
		
	/**银行名称*/
	private String bankName;
		
	/**币种*/
	private String currency;
		
	/**创建时间*/
	private Date createTime;
		
	/**修改时间*/
	private Date updateTime;
		
	/**状态*/
	private Integer status;
		
	/**备注*/
	private String remark;
		
}