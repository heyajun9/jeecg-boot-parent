package org.jeecg.modules.demo.wmsItemTable.entity;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;

/**
 * @Description: wms_item_table
 * @Author: jeecg-boot
 * @Date:   2019-11-29
 * @Version: V1.0
 */
@Data
@TableName("wms_item_table")
public class WmsItemTable implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
	private java.lang.String id;
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
	/**所属部门*/
	@Excel(name = "所属部门", width = 15)
	private java.lang.String sysOrgCode;
	/**物料编码*/
	@Excel(name = "物料编码", width = 15)
	private java.lang.String itemCode;
	/**物料名称*/
	@Excel(name = "物料名称", width = 15)
	private java.lang.String itemName;
	/**物料描述*/
	@Excel(name = "物料描述", width = 15)
	private java.lang.String itemNote;
	/**物料条码*/
	@Excel(name = "物料条码", width = 15)
	private java.lang.String itemBarcode;
	/**RR*/
	@Excel(name = "RR", width = 15)
	private java.lang.String itemRr;
	/**业务类型*/
	@Excel(name = "业务类型", width = 15)
	private java.lang.String serviceType;
}
