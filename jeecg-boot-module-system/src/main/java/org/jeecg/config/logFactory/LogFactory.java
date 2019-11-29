package org.jeecg.config.logFactory;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.system.entity.SysLog;
import org.jeecg.modules.system.service.ISysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Admin
 * @create 2019-11-28 17:21
 * @desc 日志工厂
 **/
public class LogFactory {

    public static SysLog createSysLog(String taskName,String method,String params,String url,long time,String responseBody){
        SysLog sysLog=new SysLog();
        sysLog.setLogContent(taskName);
        sysLog.setLogType(CommonConstant.LOG_TYPE_2);
        sysLog.setMethod(method);//定时任务调用的方法
        sysLog.setOperateType(CommonConstant.OPERATE_TYPE_1);
        sysLog.setRequestParam(params);
        sysLog.setRequestUrl(url);
        sysLog.setUserid("admin");
        sysLog.setUsername("管理员");

        sysLog.setResponseBody(responseBody);
        //耗时
        sysLog.setCostTime(time);
        sysLog.setCreateTime(new Date());
        return sysLog;
    }

}
