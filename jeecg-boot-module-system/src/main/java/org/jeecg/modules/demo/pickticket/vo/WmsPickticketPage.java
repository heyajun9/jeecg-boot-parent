package org.jeecg.modules.demo.pickticket.vo;

import java.util.List;
import org.jeecg.modules.demo.pickticket.entity.WmsPickticket;
import org.jeecg.modules.demo.pickticket.entity.WmsPickticketDetail;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelEntity;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

/**
 * @Description: 出库单
 * @Author: jeecg-boot
 * @Date:   2019-11-22
 * @Version: V1.0
 */
@Data
public class WmsPickticketPage {
	
	/**主键*/
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
	/**出库单号*/
	@Excel(name = "出库单号", width = 15)
	private java.lang.String pickticketCode;
	/**单据类型*/
	@Excel(name = "单据类型", width = 15)
	private java.lang.String orderType;
	/**业务类型*/
	@Excel(name = "业务类型", width = 15)
	private java.lang.String serviceType;
	/**单据日期*/
	@Excel(name = "单据日期", width = 15)
	private java.lang.String pickticketDate;
	/**到货日期*/
	@Excel(name = "到货日期", width = 15)
	private java.lang.String arriveDate;
	/**收货人*/
	@Excel(name = "收货人", width = 15)
	private java.lang.String arrivor;
	/**收货地址*/
	@Excel(name = "收货地址", width = 15)
	private java.lang.String address;
	/**联系方式*/
	@Excel(name = "联系方式", width = 15)
	private java.lang.String phone;
	
	@ExcelCollection(name="出库单明细")
	private List<WmsPickticketDetail> wmsPickticketDetailList;	
	
}
