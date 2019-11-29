package org.jeecg.modules.demo.wmsItemTable.webService;

import org.jeecg.common.api.vo.Result;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;
import org.jeecg.modules.demo.wmsItemTable.entity.WmsItemTable;

 /**
 * @Description: wms_item_table
 * @Author: jeecg-boot
 * @Date:   2019-11-29
 * @Version: V1.0
 */
@WebService(name="wmsItemTableWebService",targetNamespace = "http://webService.wmsItemTable.org.jeecg.modules.demo")
public interface WmsItemTableWebService {
    @WebMethod
    public Result<WmsItemTable> add(@WebParam(name="wmsItemTable") WmsItemTable wmsItemTable);
    @WebMethod
    public Result<List<WmsItemTable>> addList(@WebParam(name="wmsItemTable") List<WmsItemTable> wmsItemTable);
    @WebMethod
    public Result<WmsItemTable> edit(@WebParam(name="wmsItemTable") WmsItemTable wmsItemTable);
    @WebMethod
    public Result<List<WmsItemTable>> editList(@WebParam(name="wmsItemTable") List<WmsItemTable> wmsItemTable);
    @WebMethod
    public Result<WmsItemTable> delete(@WebParam(name="wmsItemTable") WmsItemTable wmsItemTable);
}
