package org.jeecg.modules.demo.interfaceOption.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import java.util.Date;

/**
 * @Description: 字段对应表
 * @Author: jeecg-boot
 * @Date:   2019-11-28
 * @Version: V1.0
 */
@Data
@TableName("table_option")
public class TableOption implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**id*/
	@TableId(type = IdType.ID_WORKER_STR)
	private java.lang.String id;
	/**上游字段*/
	@Excel(name = "上游字段", width = 15)
	private java.lang.String upColumnName;
	/**下游子段*/
	@Excel(name = "下游子段", width = 15)
	private java.lang.String downCloumnName;
	/**父字段*/
	@Excel(name = "父字段", width = 15)
	private java.lang.String parentColumnName;
	/**标识*/
	@Excel(name = "标识", width = 15)
	private java.lang.String flag;
	/**子字段*/
	@Excel(name = "子字段", width = 15)
	private java.lang.String childColumnName;
	/**默认值*/
	@Excel(name = "默认值", width = 15)
	private java.lang.String defaultValue;
	/**字段类型*/
	@Excel(name = "字段类型", width = 15)
	private java.lang.String typeName;
	/**字段长度*/
	@Excel(name = "字段长度", width = 15)
	private java.lang.String coolumnSize;
	/**是否为空*/
	@Excel(name = "是否为空", width = 15)
	private java.lang.String isEmpty;
	/**表名*/
	@Excel(name = "表名", width = 15)
	private java.lang.String tableName;
	/**备注*/
	@Excel(name = "备注", width = 15)
	private java.lang.String remark;
	/**外键*/
	private java.lang.String interfaceFkId;
	/**创建人*/
	@Excel(name = "创建人", width = 15)
	private java.lang.String createBy;
	/**创建日期*/
	@Excel(name = "创建日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private java.util.Date createTime;
	/**更新人*/
	@Excel(name = "更新人", width = 15)
	private java.lang.String updateBy;
	/**更新日期*/
	@Excel(name = "更新日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private java.util.Date updateTime;
}
