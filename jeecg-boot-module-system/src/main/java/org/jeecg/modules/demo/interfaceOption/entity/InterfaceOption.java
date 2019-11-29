package org.jeecg.modules.demo.interfaceOption.entity;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: 接口配置
 * @Author: jeecg-boot
 * @Date:   2019-11-28
 * @Version: V1.0
 */
@Data
@TableName("interface_option")
public class InterfaceOption implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
	private java.lang.String id;
	/**创建人*/
	private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private java.util.Date createTime;
	/**更新人*/
	private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private java.util.Date updateTime;
	/**接口编号*/
	private java.lang.String interfaceCode;
	/**接口名称*/
	private java.lang.String interfaceName;
	/**业务类型*/
	private java.lang.String interfaceType;
	/**传输类型*/
	private java.lang.String transferType;
	/**是否生效*/
	private java.lang.String beactive;
	/**访问地址*/
	private java.lang.String address;
	/**表名*/
	private java.lang.String tablename;
	/**接口用户名*/
	private java.lang.String interfaceUsername;
	/**接口密码*/
	private java.lang.String interfacePassword;
	/**定时任务*/
	private java.lang.String taskParam;
	/**报文格式*/
	private java.lang.String pomParam;
	/**json格式*/
	private java.lang.String jsonDetail;
}
