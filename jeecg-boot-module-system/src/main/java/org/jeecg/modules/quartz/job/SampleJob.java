package org.jeecg.modules.quartz.job;

import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.aspect.annotation.InterfaceLog;
import org.jeecg.common.aspect.annotation.TaskLog;
import org.jeecg.common.util.DateUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import lombok.extern.slf4j.Slf4j;

/**
 * 示例不带参定时任务
 * 
 * @Author Scott
 */
@Slf4j
public class SampleJob implements Job {

	@Override
	@TaskLog(value = "定时任务demo")
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

		log.info(String.format(" Jeecg-Boot 普通定时任务 SampleJob !  时间:" + DateUtils.getTimestamp()));
	}
}