package org.jeecg.modules.system.aspect;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.aspect.annotation.InterfaceLog;
import org.jeecg.common.aspect.annotation.TaskLog;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.IPUtils;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.modules.system.entity.SysLog;
import org.jeecg.modules.system.service.ISysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;


/**
 * 系统日志，切面处理类
 * 
 * @Author scott
 * @email jeecgos@163.com
 * @Date 2018年1月14日
 */
@Aspect
@Component
public class AutoLogAspect {
	@Autowired
	private ISysLogService sysLogService;
	
	@Pointcut("@annotation(org.jeecg.common.aspect.annotation.AutoLog)")
	public void logPointCut() {

	}

	@Pointcut("@annotation(org.jeecg.common.aspect.annotation.InterfaceLog)")
	public void logInterfacePointCut() {

	}
	@Pointcut("@annotation(org.jeecg.common.aspect.annotation.TaskLog)")
	public void logTaskPointCut() {

	}

	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long beginTime = System.currentTimeMillis();
		//执行方法
		Object result = point.proceed();
		//执行时长(毫秒)
		long time = System.currentTimeMillis() - beginTime;

		//保存日志
		saveSysLog(point, time);

		return result;
	}

	private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();

		SysLog sysLog = new SysLog();
		AutoLog syslog = method.getAnnotation(AutoLog.class);
		if(syslog != null){
			//注解上的描述,操作日志内容
			sysLog.setLogContent(syslog.value());
			sysLog.setLogType(syslog.logType());
			
		}

		//请求的方法名
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		sysLog.setMethod(className + "." + methodName + "()");
		
		
		//设置操作类型
		if (sysLog.getLogType() == CommonConstant.LOG_TYPE_2) {
			sysLog.setOperateType(getOperateType(methodName, syslog.operateType()));
		}

		//请求的参数
		Object[] args = joinPoint.getArgs();
		try{
			String params = JSONObject.toJSONString(args);
			sysLog.setRequestParam(params);
		}catch (Exception e){

		}

		//获取request
		HttpServletRequest request = SpringContextUtils.getHttpServletRequest();
		//设置IP地址
        sysLog.setIp(IPUtils.getIpAddr(request));

		//获取登录用户信息
		LoginUser sysUser = (LoginUser)SecurityUtils.getSubject().getPrincipal();
		if(sysUser!=null){
			sysLog.setUserid(sysUser.getUsername());
			sysLog.setUsername(sysUser.getRealname());

		}
		//耗时
		sysLog.setCostTime(time);
		sysLog.setCreateTime(new Date());
		//保存系统日志
		sysLogService.save(sysLog);
	}
	/**
	 * 获取操作类型
	 */
	private int getOperateType(String methodName,int operateType) {
		if (operateType > 0) {
			return operateType;
		}
        if (methodName.startsWith("list")) {
        	return CommonConstant.OPERATE_TYPE_1;
		}
        if (methodName.startsWith("add")) {
        	return CommonConstant.OPERATE_TYPE_2;
		}
        if (methodName.startsWith("edit")) {
        	return CommonConstant.OPERATE_TYPE_3;
		}
        if (methodName.startsWith("delete")) {
        	return CommonConstant.OPERATE_TYPE_4;
		}
        if (methodName.startsWith("import")) {
        	return CommonConstant.OPERATE_TYPE_5;
		}
        if (methodName.startsWith("export")) {
        	return CommonConstant.OPERATE_TYPE_6;
		}
		return CommonConstant.OPERATE_TYPE_1;
	}
	//正常return返回
	@AfterReturning(value="logInterfacePointCut()",returning = "rtv")
	public void recordInterfaceLogReturn(JoinPoint joinPoint, Object rtv) throws Throwable {
		long beginTime = System.currentTimeMillis();

		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();

		SysLog sysLog = new SysLog();
		InterfaceLog syslog = method.getAnnotation(InterfaceLog.class);
		if(syslog != null){
			//注解上的描述,操作日志内容
			sysLog.setLogContent(syslog.value());
			sysLog.setLogType(syslog.logType());

		}

		//请求的方法名
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		sysLog.setMethod(className + "." + methodName + "()");


		//设置操作类型
//		if (sysLog.getLogType() == CommonConstant.LOG_TYPE_2) {
		if (sysLog.getLogType()!=null) {
			sysLog.setOperateType(getOperateType(methodName, syslog.operateType()));
		}

		//请求的参数
		Object[] args = joinPoint.getArgs();
		try{
			String params = JSONObject.toJSONString(args);
			sysLog.setRequestParam(params);
		}catch (Exception e){

		}

		//获取request
		HttpServletRequest request = SpringContextUtils.getHttpServletRequest();
		//设置IP地址
		sysLog.setIp(IPUtils.getIpAddr(request));

		//获取登录用户信息
		LoginUser sysUser = (LoginUser)SecurityUtils.getSubject().getPrincipal();
		if(sysUser!=null){
			sysLog.setUserid(sysUser.getUsername());
			sysLog.setUsername(sysUser.getRealname());

		}
//		System.out.println(JSONObject.toJSON(rtv));
		sysLog.setResponseBody(JSONObject.toJSONString(rtv));
		//执行时长(毫秒)
		long time = System.currentTimeMillis() - beginTime;
		//耗时
		sysLog.setCostTime(time);
		sysLog.setCreateTime(new Date());
		//保存系统日志
		sysLogService.save(sysLog);
	}

	//异常返回
	@AfterThrowing(value="logInterfacePointCut()",throwing = "throwing")
	public void recordInterfaceLogError(JoinPoint joinPoint,Throwable throwing) throws Throwable{
		long beginTime = System.currentTimeMillis();

		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();

		SysLog sysLog = new SysLog();
		InterfaceLog syslog = method.getAnnotation(InterfaceLog.class);
		if(syslog != null){
			//注解上的描述,操作日志内容
			sysLog.setLogContent(syslog.value());
			sysLog.setLogType(syslog.logType());

		}

		//请求的方法名
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		sysLog.setMethod(className + "." + methodName + "()");


		//设置操作类型
//		if (sysLog.getLogType() == CommonConstant.LOG_TYPE_2) {
		if (sysLog.getLogType()!=null) {
			sysLog.setOperateType(getOperateType(methodName, syslog.operateType()));
		}

		//请求的参数
		Object[] args = joinPoint.getArgs();
		try{
			String params = JSONObject.toJSONString(args);
			sysLog.setRequestParam(params);
		}catch (Exception e){

		}

		//获取request
		HttpServletRequest request = SpringContextUtils.getHttpServletRequest();
		//设置IP地址
		sysLog.setIp(IPUtils.getIpAddr(request));

		//获取登录用户信息
		LoginUser sysUser = (LoginUser)SecurityUtils.getSubject().getPrincipal();
		if(sysUser!=null){
			sysLog.setUserid(sysUser.getUsername());
			sysLog.setUsername(sysUser.getRealname());

		}
		sysLog.setResponseBody(throwing.getMessage());
		//执行时长(毫秒)
		long time = System.currentTimeMillis() - beginTime;
		//耗时
		sysLog.setCostTime(time);
		sysLog.setCreateTime(new Date());
		//保存系统日志
		sysLogService.save(sysLog);
	}
	//定时任务
    @Around("logTaskPointCut()")
    public Object aroundTask(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;

        //保存日志
        saveTaskLog(point, time);

        return result;
    }

    private void saveTaskLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        SysLog sysLog = new SysLog();
        TaskLog syslog = method.getAnnotation(TaskLog.class);
        if(syslog != null){
            //注解上的描述,操作日志内容
            sysLog.setLogContent(syslog.value());
            sysLog.setLogType(syslog.logType());

        }

        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName + "()");


        //设置操作类型
        if (sysLog.getLogType() != null) {
            sysLog.setOperateType(getOperateType(methodName, syslog.operateType()));
        }

        //请求的参数
        Object[] args = joinPoint.getArgs();
        try{
            String params = JSONObject.toJSONString(args);
            sysLog.setRequestParam(params);
        }catch (Exception e){

        }

//        //获取request
//        HttpServletRequest request = SpringContextUtils.getHttpServletRequest();
//        //设置IP地址
//        sysLog.setIp(IPUtils.getIpAddr(request));

        //获取登录用户信息
            sysLog.setUserid("admin");
            sysLog.setUsername("管理员");
//
        //耗时
        sysLog.setCostTime(time);
        sysLog.setCreateTime(new Date());
        //保存系统日志
        sysLogService.save(sysLog);
    }

}
