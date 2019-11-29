package org.jeecg.modules.demo.interfaceOption.vo;

import java.util.List;
import org.jeecg.modules.demo.interfaceOption.entity.InterfaceOption;
import org.jeecg.modules.demo.interfaceOption.entity.TableOption;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelEntity;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

/**
 * @Description: 接口配置
 * @Author: jeecg-boot
 * @Date:   2019-11-28
 * @Version: V1.0
 */
@Data
public class InterfaceOptionPage {
	
	/**主键*/
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
	/**接口编号*/
	@Excel(name = "接口编号", width = 15)
	private java.lang.String interfaceCode;
	/**接口名称*/
	@Excel(name = "接口名称", width = 15)
	private java.lang.String interfaceName;
	/**业务类型*/
	@Excel(name = "业务类型", width = 15)
	private java.lang.String interfaceType;
	/**传输类型*/
	@Excel(name = "传输类型", width = 15)
	private java.lang.String transferType;
	/**是否生效*/
	@Excel(name = "是否生效", width = 15)
	private java.lang.String beactive;
	/**访问地址*/
	@Excel(name = "访问地址", width = 15)
	private java.lang.String address;
	/**表名*/
	@Excel(name = "表名", width = 15)
	private java.lang.String tablename;
	/**接口用户名*/
	@Excel(name = "接口用户名", width = 15)
	private java.lang.String interfaceUsername;
	/**接口密码*/
	@Excel(name = "接口密码", width = 15)
	private java.lang.String interfacePassword;
	/**定时任务*/
	@Excel(name = "定时任务", width = 15)
	private java.lang.String taskParam;
	/**报文格式*/
	@Excel(name = "报文格式", width = 15)
	private java.lang.String pomParam;
	/**json格式*/
	@Excel(name = "json格式", width = 15)
	private java.lang.String jsonDetail;
	
	@ExcelCollection(name="字段对应表")
	private List<TableOption> tableOptionList;	
	
}
