package org.jeecg.modules.demo.interfac.webService.impl;

import org.jeecg.common.api.vo.Result;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.jeecg.modules.demo.interfac.entity.Interfac;
import org.jeecg.modules.demo.interfac.service.IInterfacService;
import org.jeecg.modules.demo.interfac.webService.InterfacWebService;

 /**
 * @Description: 测试
 * @Author: jeecg-boot
 * @Date:   2019-11-29
 * @Version: V1.0
 */
 @WebService(serviceName = "interfacWebService", // 与接口中指定的name一致
         targetNamespace = "http://webService.interfac.org.jeecg.modules.demo", // 与接口中的命名空间一致,一般是接口的包名倒
         endpointInterface = "org.jeecg.modules.demo.interfac.webService.InterfacWebService"// 接口地址
 )
 @Component
 @Slf4j
public class InterfacWebServiceImpl implements InterfacWebService{

    @Autowired
    private IInterfacService interfacService;

    @Override
    public Result<Interfac> add(@WebParam(name="interfac") Interfac interfac){
          log.info("InterfacWebService is start!");
          Result<Interfac> result = new Result<Interfac>();
          try {
              if(interfac.getId()==null){
                  interfac.setId(UUID.randomUUID().toString());
              }
              interfacService.save(interfac);
              result.success("操作成功");
          }catch(Exception e){
              log.error(e.getMessage(),e);
              result.error500("操作失败");
          }
           return result;
    }
    @Override
    public Result<List<Interfac>> addList(@WebParam(name="interfac") List<Interfac> interfac){
             log.info("InterfacWebService is start!");
             Result<List<Interfac>> result = new Result<List<Interfac>>();
             try {
             for (Interfac interfac1 : interfac) {
                 if(interfac1.getId()==null){
                     interfac1.setId(UUID.randomUUID().toString());
                 }
                 interfacService.save(interfac1);
                 result.success("操作成功");
                 }
             }catch(Exception e){
                 log.error(e.getMessage(),e);
                 result.error500("操作失败");
             }
              return result;
    }
    @Override
    public Result<Interfac> edit(@WebParam(name="interfac") Interfac interfac){
       log.info("InterfacWebService is start!");
                Result<Interfac> result = new Result<Interfac>();
                try {
                    interfacService.updateById(interfac);
                    result.success("操作成功");
                }catch(Exception e){
                    log.error(e.getMessage(),e);
                    result.error500("操作失败");
                }
                 return result;
    }
    @Override
    public Result<List<Interfac>> editList(@WebParam(name="interfac") List<Interfac> interfac){
         log.info("InterfacWebService is start!");
                     Result<List<Interfac>> result = new Result<List<Interfac>>();
                     try {
                     for (Interfac interfac1 : interfac) {
                         interfacService.updateById(interfac1);
                         result.success("操作成功");
                         }
                     }catch(Exception e){
                         log.error(e.getMessage(),e);
                         result.error500("操作失败");
                     }
                      return result;
    }
    @Override
    public Result<Interfac> delete(@WebParam(name="interfac") Interfac interfac){
           log.info("InterfacWebService is start!");
           Result<Interfac> result = new Result<Interfac>();
           try {
               interfacService.removeById(interfac.getId());
               result.success("删除成功");
           }catch(Exception e){
               log.error(e.getMessage(),e);
               result.error500("删除失败");
           }
            return result;
    }
}
