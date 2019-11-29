package org.jeecg.modules.demo.interfaceOption.webService.impl;

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

import org.jeecg.modules.demo.interfaceOption.webService.InterfaceOptionWebService;
import org.jeecg.modules.demo.interfaceOption.entity.InterfaceOption;
import org.jeecg.modules.demo.interfaceOption.vo.InterfaceOptionPage;
import org.jeecg.modules.demo.interfaceOption.service.IInterfaceOptionService;
import org.jeecg.modules.demo.interfaceOption.entity.TableOption;
import org.jeecg.modules.demo.interfaceOption.service.ITableOptionService;

 /**
 * @Description: 接口配置
 * @Author: jeecg-boot
 * @Date:   2019-11-28
 * @Version: V1.0
 */
 @WebService(serviceName = "interfaceOptionWebService", // 与接口中指定的name一致
         targetNamespace = "http://webService.interfaceOption.org.jeecg.modules.demo", // 与接口中的命名空间一致,一般是接口的包名倒
         endpointInterface = "org.jeecg.modules.demo.interfaceOption.webService.InterfaceOptionWebService"// 接口地址
 )
 @Component
 @Slf4j
public class InterfaceOptionWebServiceImpl implements InterfaceOptionWebService{

    @Autowired
    private IInterfaceOptionService interfaceOptionService;

    @Override
    public Result<InterfaceOption> add(@WebParam(name="interfaceOption") InterfaceOptionPage interfaceOptionPage){
          log.info("InterfaceOptionWebService is start!");
          Result<InterfaceOption> result = new Result<InterfaceOption>();
          try {
             	InterfaceOption interfaceOption = new InterfaceOption();
             	BeanUtils.copyProperties(interfaceOptionPage, interfaceOption);
              interfaceOptionService.saveMain(interfaceOption, interfaceOptionPage.getTableOptionList());
              result.success("操作成功");
          }catch(Exception e){
              log.error(e.getMessage(),e);
              result.error500("操作失败");
          }
           return result;
    }
    @Override
    public Result<List<InterfaceOption>> addList(@WebParam(name="interfaceOption") List<InterfaceOptionPage> interfaceOptionPage){
             log.info("InterfaceOptionWebService is start!");
            Result<List<InterfaceOption>> result = new Result<List<InterfaceOption>>();
                		try {
                		for(InterfaceOptionPage interfaceOptionPage1: interfaceOptionPage ){
                			InterfaceOption interfaceOption = new InterfaceOption();
                			BeanUtils.copyProperties(interfaceOptionPage1, interfaceOption);

                			interfaceOptionService.saveMain(interfaceOption, interfaceOptionPage1.getTableOptionList());
                			result.success("添加成功！");
                			}
                		} catch (Exception e) {
                			log.error(e.getMessage(),e);
                			result.error500("操作失败");
                		}
                		return result;
    }
    @Override
    public Result<InterfaceOption> edit(@WebParam(name="interfaceOption") InterfaceOptionPage interfaceOptionPage){
       log.info("InterfaceOptionWebService is start!");
            	Result<InterfaceOption> result = new Result<InterfaceOption>();
            		InterfaceOption interfaceOption = new InterfaceOption();
            		BeanUtils.copyProperties(interfaceOptionPage, interfaceOption);
            		InterfaceOption interfaceOptionEntity = interfaceOptionService.getById(interfaceOption.getId());
            		if(interfaceOptionEntity==null) {
            			result.error500("未找到对应实体");
            		}else {
            			boolean ok = interfaceOptionService.updateById(interfaceOption);
            			interfaceOptionService.updateMain(interfaceOption, interfaceOptionPage.getTableOptionList());
            			result.success("修改成功!");
            		}

            		return result;
    }
    @Override
    public Result<List<InterfaceOption>> editList(@WebParam(name="interfaceOption") List<InterfaceOptionPage> interfaceOptionPage){
         log.info("InterfaceOptionWebService is start!");
         Result<List<InterfaceOption>> result = new Result<List<InterfaceOption>>();
         try {
          for(InterfaceOptionPage interfaceOptionPage1: interfaceOptionPage ){
          	InterfaceOption interfaceOption = new InterfaceOption();
          		BeanUtils.copyProperties(interfaceOptionPage1, interfaceOption);
                InterfaceOption interfaceOptionEntity = interfaceOptionService.getById(interfaceOption.getId());
                if(interfaceOptionEntity==null) {
                	result.error500("未找到对应实体");
                }else {
                	boolean ok = interfaceOptionService.updateById(interfaceOption);
                	interfaceOptionService.updateMain(interfaceOption, interfaceOptionPage1.getTableOptionList());
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
    public Result<InterfaceOption> delete(@WebParam(name="interfaceOption") InterfaceOptionPage interfaceOptionPage){
           log.info("InterfaceOptionWebService is start!");
           Result<InterfaceOption> result = new Result<InterfaceOption>();
           try {
               interfaceOptionService.delMain(interfaceOptionPage.getId());
               result.success("删除成功!");
           }catch (Exception e) {
           	log.error("删除失败",e.getMessage());
           	result.error500("删除失败!");
           }
           return result;
    }
}
