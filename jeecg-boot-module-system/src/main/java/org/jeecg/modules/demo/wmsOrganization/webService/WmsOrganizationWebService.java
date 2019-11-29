package org.jeecg.modules.demo.wmsOrganization.webService;

import org.jeecg.common.api.vo.Result;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;
import org.jeecg.modules.demo.wmsOrganization.entity.WmsOrganization;

 /**
 * @Description: 组织单位表
 * @Author: jeecg-boot
 * @Date:   2019-11-29
 * @Version: V1.0
 */
@WebService(name="wmsOrganizationWebService",targetNamespace = "http://webService.wmsOrganization.org.jeecg.modules.demo")
public interface WmsOrganizationWebService {
    @WebMethod
    public Result<WmsOrganization> add(@WebParam(name="wmsOrganization") WmsOrganization wmsOrganization);
    @WebMethod
    public Result<List<WmsOrganization>> addList(@WebParam(name="wmsOrganization") List<WmsOrganization> wmsOrganization);
    @WebMethod
    public Result<WmsOrganization> edit(@WebParam(name="wmsOrganization") WmsOrganization wmsOrganization);
    @WebMethod
    public Result<List<WmsOrganization>> editList(@WebParam(name="wmsOrganization") List<WmsOrganization> wmsOrganization);
    @WebMethod
    public Result<WmsOrganization> delete(@WebParam(name="wmsOrganization") WmsOrganization wmsOrganization);
}
