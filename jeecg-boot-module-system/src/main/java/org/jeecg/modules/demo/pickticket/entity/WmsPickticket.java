package org.jeecg.modules.demo.pickticket.entity;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: 出库单
 * @Author: jeecg-boot
 * @Date:   2019-11-22
 * @Version: V1.0
 */
@Data
@TableName("wms_pickticket")
public class WmsPickticket implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
	private java.lang.String id;
	/**创建人*/
	private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date createTime;
	/**更新人*/
	private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date updateTime;
	/**所属部门*/
	private java.lang.String sysOrgCode;
	/**出库单号*/
	private java.lang.String pickticketCode;
	/**单据类型*/
	private java.lang.String orderType;
	/**业务类型*/
	private java.lang.String serviceType;
	/**单据日期*/
	private java.lang.String pickticketDate;
	/**到货日期*/
	private java.lang.String arriveDate;
	/**收货人*/
	private java.lang.String arrivor;
	/**收货地址*/
	private java.lang.String address;
	/**联系方式*/
	private java.lang.String phone;
}
