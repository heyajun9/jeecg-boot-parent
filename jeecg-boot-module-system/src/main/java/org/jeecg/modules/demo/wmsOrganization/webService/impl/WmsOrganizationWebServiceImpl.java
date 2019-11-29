package org.jeecg.modules.demo.wmsOrganization.webService.impl;

import org.jeecg.common.api.vo.Result;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.jeecg.modules.demo.wmsOrganization.entity.WmsOrganization;
import org.jeecg.modules.demo.wmsOrganization.service.IWmsOrganizationService;
import org.jeecg.modules.demo.wmsOrganization.webService.WmsOrganizationWebService;

 /**
 * @Description: 组织单位表
 * @Author: jeecg-boot
 * @Date:   2019-11-29
 * @Version: V1.0
 */
 @WebService(serviceName = "wmsOrganizationWebService", // 与接口中指定的name一致
         targetNamespace = "http://webService.wmsOrganization.org.jeecg.modules.demo", // 与接口中的命名空间一致,一般是接口的包名倒
         endpointInterface = "org.jeecg.modules.demo.wmsOrganization.webService.WmsOrganizationWebService"// 接口地址
 )
 @Component
 @Slf4j
public class WmsOrganizationWebServiceImpl implements WmsOrganizationWebService{

    @Autowired
    private IWmsOrganizationService wmsOrganizationService;

    @Override
    public Result<WmsOrganization> add(@WebParam(name="wmsOrganization") WmsOrganization wmsOrganization){
          log.info("WmsOrganizationWebService is start!");
          Result<WmsOrganization> result = new Result<WmsOrganization>();
          try {
              if(wmsOrganization.getId()==null){
                  wmsOrganization.setId(UUID.randomUUID().toString());
              }
              wmsOrganizationService.save(wmsOrganization);
              result.success("操作成功");
          }catch(Exception e){
              log.error(e.getMessage(),e);
              result.error500("操作失败");
          }
           return result;
    }
    @Override
    public Result<List<WmsOrganization>> addList(@WebParam(name="wmsOrganization") List<WmsOrganization> wmsOrganization){
             log.info("WmsOrganizationWebService is start!");
             Result<List<WmsOrganization>> result = new Result<List<WmsOrganization>>();
             try {
             for (WmsOrganization wmsOrganization1 : wmsOrganization) {
                 if(wmsOrganization1.getId()==null){
                     wmsOrganization1.setId(UUID.randomUUID().toString());
                 }
                 wmsOrganizationService.save(wmsOrganization1);
                 result.success("操作成功");
                 }
             }catch(Exception e){
                 log.error(e.getMessage(),e);
                 result.error500("操作失败");
             }
              return result;
    }
    @Override
    public Result<WmsOrganization> edit(@WebParam(name="wmsOrganization") WmsOrganization wmsOrganization){
       log.info("WmsOrganizationWebService is start!");
                Result<WmsOrganization> result = new Result<WmsOrganization>();
                try {
                    wmsOrganizationService.updateById(wmsOrganization);
                    result.success("操作成功");
                }catch(Exception e){
                    log.error(e.getMessage(),e);
                    result.error500("操作失败");
                }
                 return result;
    }
    @Override
    public Result<List<WmsOrganization>> editList(@WebParam(name="wmsOrganization") List<WmsOrganization> wmsOrganization){
         log.info("WmsOrganizationWebService is start!");
                     Result<List<WmsOrganization>> result = new Result<List<WmsOrganization>>();
                     try {
                     for (WmsOrganization wmsOrganization1 : wmsOrganization) {
                         wmsOrganizationService.updateById(wmsOrganization1);
                         result.success("操作成功");
                         }
                     }catch(Exception e){
                         log.error(e.getMessage(),e);
                         result.error500("操作失败");
                     }
                      return result;
    }
    @Override
    public Result<WmsOrganization> delete(@WebParam(name="wmsOrganization") WmsOrganization wmsOrganization){
           log.info("WmsOrganizationWebService is start!");
           Result<WmsOrganization> result = new Result<WmsOrganization>();
           try {
               wmsOrganizationService.removeById(wmsOrganization.getId());
               result.success("删除成功");
           }catch(Exception e){
               log.error(e.getMessage(),e);
               result.error500("删除失败");
           }
            return result;
    }
}
