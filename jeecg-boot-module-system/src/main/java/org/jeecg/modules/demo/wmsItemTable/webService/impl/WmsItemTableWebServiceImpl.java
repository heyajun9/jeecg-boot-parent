package org.jeecg.modules.demo.wmsItemTable.webService.impl;

import org.jeecg.common.api.vo.Result;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.jeecg.modules.demo.wmsItemTable.entity.WmsItemTable;
import org.jeecg.modules.demo.wmsItemTable.service.IWmsItemTableService;
import org.jeecg.modules.demo.wmsItemTable.webService.WmsItemTableWebService;

 /**
 * @Description: wms_item_table
 * @Author: jeecg-boot
 * @Date:   2019-11-29
 * @Version: V1.0
 */
 @WebService(serviceName = "wmsItemTableWebService", // 与接口中指定的name一致
         targetNamespace = "http://webService.wmsItemTable.org.jeecg.modules.demo", // 与接口中的命名空间一致,一般是接口的包名倒
         endpointInterface = "org.jeecg.modules.demo.wmsItemTable.webService.WmsItemTableWebService"// 接口地址
 )
 @Component
 @Slf4j
public class WmsItemTableWebServiceImpl implements WmsItemTableWebService{

    @Autowired
    private IWmsItemTableService wmsItemTableService;

    @Override
    public Result<WmsItemTable> add(@WebParam(name="wmsItemTable") WmsItemTable wmsItemTable){
          log.info("WmsItemTableWebService is start!");
          Result<WmsItemTable> result = new Result<WmsItemTable>();
          try {
              if(wmsItemTable.getId()==null){
                  wmsItemTable.setId(UUID.randomUUID().toString());
              }
              wmsItemTableService.save(wmsItemTable);
              result.success("操作成功");
          }catch(Exception e){
              log.error(e.getMessage(),e);
              result.error500("操作失败");
          }
           return result;
    }
    @Override
    public Result<List<WmsItemTable>> addList(@WebParam(name="wmsItemTable") List<WmsItemTable> wmsItemTable){
             log.info("WmsItemTableWebService is start!");
             Result<List<WmsItemTable>> result = new Result<List<WmsItemTable>>();
             try {
             for (WmsItemTable wmsItemTable1 : wmsItemTable) {
                 if(wmsItemTable1.getId()==null){
                     wmsItemTable1.setId(UUID.randomUUID().toString());
                 }
                 wmsItemTableService.save(wmsItemTable1);
                 result.success("操作成功");
                 }
             }catch(Exception e){
                 log.error(e.getMessage(),e);
                 result.error500("操作失败");
             }
              return result;
    }
    @Override
    public Result<WmsItemTable> edit(@WebParam(name="wmsItemTable") WmsItemTable wmsItemTable){
       log.info("WmsItemTableWebService is start!");
                Result<WmsItemTable> result = new Result<WmsItemTable>();
                try {
                    wmsItemTableService.updateById(wmsItemTable);
                    result.success("操作成功");
                }catch(Exception e){
                    log.error(e.getMessage(),e);
                    result.error500("操作失败");
                }
                 return result;
    }
    @Override
    public Result<List<WmsItemTable>> editList(@WebParam(name="wmsItemTable") List<WmsItemTable> wmsItemTable){
         log.info("WmsItemTableWebService is start!");
                     Result<List<WmsItemTable>> result = new Result<List<WmsItemTable>>();
                     try {
                     for (WmsItemTable wmsItemTable1 : wmsItemTable) {
                         wmsItemTableService.updateById(wmsItemTable1);
                         result.success("操作成功");
                         }
                     }catch(Exception e){
                         log.error(e.getMessage(),e);
                         result.error500("操作失败");
                     }
                      return result;
    }
    @Override
    public Result<WmsItemTable> delete(@WebParam(name="wmsItemTable") WmsItemTable wmsItemTable){
           log.info("WmsItemTableWebService is start!");
           Result<WmsItemTable> result = new Result<WmsItemTable>();
           try {
               wmsItemTableService.removeById(wmsItemTable.getId());
               result.success("删除成功");
           }catch(Exception e){
               log.error(e.getMessage(),e);
               result.error500("删除失败");
           }
            return result;
    }
}
