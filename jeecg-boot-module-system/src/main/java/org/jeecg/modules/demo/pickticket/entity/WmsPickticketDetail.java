package org.jeecg.modules.demo.pickticket.entity;

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
 * @Description: 出库单明细
 * @Author: jeecg-boot
 * @Date:   2019-11-22
 * @Version: V1.0
 */
@Data
@TableName("wms_pickticket_detail")
public class WmsPickticketDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
	private java.lang.String id;
	/**创建人*/
	@Excel(name = "创建人", width = 15)
	private java.lang.String createBy;
	/**创建日期*/
	@Excel(name = "创建日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date createTime;
	/**更新人*/
	@Excel(name = "更新人", width = 15)
	private java.lang.String updateBy;
	/**更新日期*/
	@Excel(name = "更新日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
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
	/**物料类型*/
	@Excel(name = "物料类型", width = 15)
	private java.lang.String itemType;
	/**物料标签*/
	@Excel(name = "物料标签", width = 15)
	private java.lang.String itemBarcode;
	/**包装*/
	@Excel(name = "包装", width = 15)
	private java.lang.String itemPackage;
	/**数量*/
	@Excel(name = "数量", width = 15)
	private java.lang.String num;
	/**单位*/
	@Excel(name = "单位", width = 15)
	private java.lang.String unit;
	/**备注*/
	@Excel(name = "备注", width = 15)
	private java.lang.String note;
	/**出库单id*/
	private java.lang.String wmsPickticketId;
}
