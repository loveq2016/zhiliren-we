package com.linyun.airline.admin.operationsArea.form;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.ModForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TMessageUpdateForm extends ModForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**标题*/
	private String msgTitle;

	/**内容*/
	private String msgContent;

	/**消息类型*/
	private Integer msgType;

	/**生成日期*/
	private Date generateTime;
	private String generateTimeString;
	/**优先级*/
	private Integer priorityLevel;

}