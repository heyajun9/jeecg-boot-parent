package org.jeecg.modules.demo.orderMain.job;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.orderMain.entity.TestOrderMain;
import org.jeecg.modules.demo.orderMain.service.ITestOrderMainService;
import org.jeecg.modules.demo.interfaceOption.entity.InterfaceOption;
import org.jeecg.modules.demo.interfaceOption.entity.TableOption;
import org.jeecg.modules.demo.interfaceOption.service.IInterfaceOptionService;
import org.jeecg.modules.demo.interfaceOption.service.ITableOptionService;
import java.util.Date;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.demo.orderMain.entity.TestOrderProduct;
import org.jeecg.modules.demo.orderMain.service.ITestOrderProductService;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jeecg.common.util.*;
import java.util.*;
import org.jeecg.common.aspect.annotation.TaskLog;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.jeecg.common.util.DateUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

 /**
 * @Description: 测试订单主表
 * @Author: jeecg-boot
 * @Date:   2019-11-29
 * @Version: V1.0
 */
@Slf4j
public class TestOrderMainJob implements Job{

    private String parameter;

    public void setParameter(String parameter) {
    	this.parameter = parameter;
    }
		@Autowired
    	private ITestOrderMainService testOrderMainService;
    	@Autowired
    	private ITestOrderProductService testOrderProductService;

	 @Autowired
	 private IInterfaceOptionService iInterfaceOptionService;
     @Autowired
     private ITableOptionService iTableOptionService;
	
    @Override
    @TaskLog(value = "定时任务 TestOrderMainJob")
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

		log.info(String.format("welcome %s! Jeecg-Boot 带参数定时任务 TestOrderMainJob !   时间:" + DateUtils.now(), this.parameter));

         /**
                 * 1、根据类型选择上传单据
                 * 2、根据类型在接口配置中查找上传的URL，json报文格式以及账号密码等信息
                 * 3、需要上传数据封装成接口配置中的json格式，
                 * 4、采用httpclient推送到上游
                 */
          if (parameter != null && parameter.length() > 0 && parameter.contains(";")) {
                    String[] splits = parameter.split(";");
                    for (String pStr : splits) {
                        Map<String, Object> map = new HashMap<>();
                        Map<String, Object> tmap = new HashMap<>();
                        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
                        if (pStr != null && pStr.length() > 0) {
                                          String[] sParam = pStr.split(":");
                                          map.put(sParam[0], sParam[1]);//根据业务类型查找
                                          stringObjectHashMap.put("task_param", sParam[1]);//根据类型查找对应的接口配置，查找响应的url，username，password
                                  }
                Collection<TestOrderMain> results = testOrderMainService.listByMap(map);
                Collection<InterfaceOption> interfaceOptions = iInterfaceOptionService.listByMap(stringObjectHashMap);
                JSONObject jsonObject = new JSONObject();
                JSONArray jsonArray = new JSONArray();
                JSONArray jsonArray1 = new JSONArray();
                String parentColumn = "";
                 InterfaceOption interfaceOption = (InterfaceOption) interfaceOptions.toArray()[0];
                        tmap.put("interface_fk_id", interfaceOption.getId());
                        Collection<TableOption> tableOptions = iTableOptionService.listByMap(tmap);
                        for (TestOrderMain result : results) {
                         Map<String,Object> detailMap=new HashMap<>();
                         detailMap.put(StringUtils.camel2Underline("TestOrderMain")+"_id",result.getId());
                         Collection<TestOrderProduct> testOrderProductList = testOrderProductService.listByMap(detailMap);
                            JSONObject jsonObject1 = new JSONObject();
                        for (TableOption tableOption : tableOptions) {
                            if (tableOption.getParentColumnName() == "" ) {
                                jsonObject.put(tableOption.getUpColumnName(), tableOption.getDefaultValue());
                            }
                            if (tableOption.getParentColumnName() != null) {
                                parentColumn = tableOption.getParentColumnName();
                                if ("1".equals(tableOption.getFlag()) && tableOption.getDownCloumnName() != null) {//代表存在方括号
                                    String downStr = tableOption.getDownCloumnName();
                                    if (downStr.contains(";") && downStr.length() > 0) {
                                        String[] split = downStr.split(";");
                                        for (TestOrderProduct result1 : testOrderProductList) {
                                            JSONObject jsonObject2 = new JSONObject();
                                            for (String s : split) {
                                                String[] s1 = s.split(" ");
                                                String up = s1[0];
                                                String down = s1[1];
                                                 String param=StringUtils.underline2Camel(down);
                                                                                       try {
                                                                                           jsonObject2.put(up, ReflectUtils.getValue(result1,param));
                                                                                       } catch (Exception e) {
                                                                                           e.printStackTrace();
                                                                                       }
                                            }
                                            jsonArray1.add(jsonObject2);
                                        }
                                        jsonObject1.put(tableOption.getUpColumnName(), jsonArray1);
                                    } else {
                                          String param=StringUtils.underline2Camel(tableOption.getDownCloumnName());
                                                                        Object value = null;
                                                                        try {
                                                                            value = ReflectUtils.getValue(result, param);
                                                                        } catch (Exception e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                        jsonObject1.put(tableOption.getUpColumnName(), value);
                                    }
                                }
                              }
                            }
                            jsonArray.add(jsonObject1);
                        }
                        jsonObject.put(parentColumn, jsonArray);
                        log.info("jsonObject:"+jsonObject.toString());
                 String url=interfaceOption.getAddress();
                 String userName=interfaceOption.getInterfaceUsername();
                 String password=interfaceOption.getInterfacePassword();
                 String returnString = HttpClient.httpClientPostJson(url, jsonObject, userName, password);
         }
          }else{
           Map<String, Object> map = new HashMap<>();
                                  Map<String, Object> tmap = new HashMap<>();
                                  HashMap<String, Object> stringObjectHashMap = new HashMap<>();
                          Collection<TestOrderMain> results = testOrderMainService.listByMap(map);
                          Collection<InterfaceOption> interfaceOptions = iInterfaceOptionService.listByMap(stringObjectHashMap);
                          JSONObject jsonObject = new JSONObject();
                          JSONArray jsonArray = new JSONArray();
                          JSONArray jsonArray1 = new JSONArray();
                          String parentColumn = "";
                           InterfaceOption interfaceOption = (InterfaceOption) interfaceOptions.toArray()[0];
                                  tmap.put("interface_fk_id", interfaceOption.getId());
                                  Collection<TableOption> tableOptions = iTableOptionService.listByMap(tmap);
                                  for (TestOrderMain result : results) {
                                   Map<String,Object> detailMap=new HashMap<>();
                                                           detailMap.put(StringUtils.camel2Underline("TestOrderMain")+"_id",result.getId());
                                                           Collection<TestOrderProduct> testOrderProductList = testOrderProductService.listByMap(detailMap);
                                                              JSONObject jsonObject1 = new JSONObject();
                                                          for (TableOption tableOption : tableOptions) {
                                                              if (tableOption.getParentColumnName() == "" && tableOption.getDownCloumnName() == "") {
                                                                  jsonObject.put(tableOption.getUpColumnName(), tableOption.getDefaultValue());
                                                              }
                                                              if (tableOption.getParentColumnName() != null) {
                                                                  parentColumn = tableOption.getParentColumnName();
                                                                  if ("1".equals(tableOption.getFlag()) && tableOption.getDownCloumnName() != null) {//代表存在方括号
                                                                      String downStr = tableOption.getDownCloumnName();
                                                                      if (downStr.contains(";") && downStr.length() > 0) {
                                                                          String[] split = downStr.split(";");
                                                                          for (TestOrderProduct result1 : testOrderProductList) {
                                                                              JSONObject jsonObject2 = new JSONObject();
                                                                              for (String s : split) {
                                                                                  String[] s1 = s.split(" ");
                                                                                  String up = s1[0];
                                                                                  String down = s1[1];
                                                                                   String param=StringUtils.underline2Camel(down);
                                                                                                                         try {
                                                                                                                             jsonObject2.put(up, ReflectUtils.getValue(result1,param));
                                                                                                                         } catch (Exception e) {
                                                                                                                             e.printStackTrace();
                                                                                                                         }
                                                                              }
                                                                              jsonArray1.add(jsonObject2);
                                                                          }
                                                                          jsonObject1.put(tableOption.getUpColumnName(), jsonArray1);
                                                                      } else {
                                                                            String param=StringUtils.underline2Camel(tableOption.getDownCloumnName());
                                                                                                          Object value = null;
                                                                                                          try {
                                                                                                              value = ReflectUtils.getValue(result, param);
                                                                                                          } catch (Exception e) {
                                                                                                              e.printStackTrace();
                                                                                                          }
                                                                                                          jsonObject1.put(tableOption.getUpColumnName(), value);
                                                                      }
                                                                  }
                                                                }
                                                              }
                                                              jsonArray.add(jsonObject1);
                                                          }
                                                          jsonObject.put(parentColumn, jsonArray);
                                                          log.info("jsonObject:"+jsonObject.toString());
                                                   String url=interfaceOption.getAddress();
                                                   String userName=interfaceOption.getInterfaceUsername();
                                                   String password=interfaceOption.getInterfacePassword();
                                                   String returnString = HttpClient.httpClientPostJson(url, jsonObject, userName, password);
                   }

          }



}
