package org.jeecg.modules.demo.orderMain.webService.impl;

import org.jeecg.common.api.vo.Result;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;

import org.jeecg.modules.demo.orderMain.webService.TestOrderMainWebService;
import org.jeecg.modules.demo.orderMain.entity.TestOrderMain;
import org.jeecg.modules.demo.orderMain.vo.TestOrderMainPage;
import org.jeecg.modules.demo.orderMain.service.ITestOrderMainService;
import org.jeecg.modules.demo.orderMain.entity.TestOrderProduct;
import org.jeecg.modules.demo.orderMain.service.ITestOrderProductService;

 /**
 * @Description: 测试订单主表
 * @Author: jeecg-boot
 * @Date:   2019-11-29
 * @Version: V1.0
 */
 @WebService(serviceName = "testOrderMainWebService", // 与接口中指定的name一致
         targetNamespace = "http://webService.orderMain.org.jeecg.modules.demo", // 与接口中的命名空间一致,一般是接口的包名倒
         endpointInterface = "org.jeecg.modules.demo.orderMain.webService.TestOrderMainWebService"// 接口地址
 )
 @Component
 @Slf4j
public class TestOrderMainWebServiceImpl implements TestOrderMainWebService{

    @Autowired
    private ITestOrderMainService testOrderMainService;

    @Override
    public Result<TestOrderMain> add(@WebParam(name="testOrderMain") TestOrderMainPage testOrderMainPage){
          log.info("TestOrderMainWebService is start!");
          Result<TestOrderMain> result = new Result<TestOrderMain>();
          try {
             	TestOrderMain testOrderMain = new TestOrderMain();
             	BeanUtils.copyProperties(testOrderMainPage, testOrderMain);
              testOrderMainService.saveMain(testOrderMain, testOrderMainPage.getTestOrderProductList());
              result.success("操作成功");
          }catch(Exception e){
              log.error(e.getMessage(),e);
              result.error500("操作失败");
          }
           return result;
    }
    @Override
    public Result<List<TestOrderMain>> addList(@WebParam(name="testOrderMain") List<TestOrderMainPage> testOrderMainPage){
             log.info("TestOrderMainWebService is start!");
            Result<List<TestOrderMain>> result = new Result<List<TestOrderMain>>();
                		try {
                		for(TestOrderMainPage testOrderMainPage1: testOrderMainPage ){
                			TestOrderMain testOrderMain = new TestOrderMain();
                			BeanUtils.copyProperties(testOrderMainPage1, testOrderMain);

                			testOrderMainService.saveMain(testOrderMain, testOrderMainPage1.getTestOrderProductList());
                			result.success("添加成功！");
                			}
                		} catch (Exception e) {
                			log.error(e.getMessage(),e);
                			result.error500("操作失败");
                		}
                		return result;
    }
    @Override
    public Result<TestOrderMain> edit(@WebParam(name="testOrderMain") TestOrderMainPage testOrderMainPage){
       log.info("TestOrderMainWebService is start!");
            	Result<TestOrderMain> result = new Result<TestOrderMain>();
            		TestOrderMain testOrderMain = new TestOrderMain();
            		BeanUtils.copyProperties(testOrderMainPage, testOrderMain);
            		TestOrderMain testOrderMainEntity = testOrderMainService.getById(testOrderMain.getId());
            		if(testOrderMainEntity==null) {
            			result.error500("未找到对应实体");
            		}else {
            			boolean ok = testOrderMainService.updateById(testOrderMain);
            			testOrderMainService.updateMain(testOrderMain, testOrderMainPage.getTestOrderProductList());
            			result.success("修改成功!");
            		}

            		return result;
    }
    @Override
    public Result<List<TestOrderMain>> editList(@WebParam(name="testOrderMain") List<TestOrderMainPage> testOrderMainPage){
         log.info("TestOrderMainWebService is start!");
         Result<List<TestOrderMain>> result = new Result<List<TestOrderMain>>();
         try {
          for(TestOrderMainPage testOrderMainPage1: testOrderMainPage ){
          	TestOrderMain testOrderMain = new TestOrderMain();
          		BeanUtils.copyProperties(testOrderMainPage1, testOrderMain);
                TestOrderMain testOrderMainEntity = testOrderMainService.getById(testOrderMain.getId());
                if(testOrderMainEntity==null) {
                	result.error500("未找到对应实体");
                }else {
                	boolean ok = testOrderMainService.updateById(testOrderMain);
                	testOrderMainService.updateMain(testOrderMain, testOrderMainPage1.getTestOrderProductList());
                	result.success("修改成功!");
                }
          	}
          } catch (Exception e) {
          	log.error(e.getMessage(),e);
          	result.error500("操作失败");
          }
          return result;
    }
    @Override
    public Result<TestOrderMain> delete(@WebParam(name="testOrderMain") TestOrderMainPage testOrderMainPage){
           log.info("TestOrderMainWebService is start!");
           Result<TestOrderMain> result = new Result<TestOrderMain>();
           try {
               testOrderMainService.delMain(testOrderMainPage.getId());
               result.success("删除成功!");
           }catch (Exception e) {
           	log.error("删除失败",e.getMessage());
           	result.error500("删除失败!");
           }
           return result;
    }
}
