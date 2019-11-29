package org.jeecg.modules.demo.interfaceOption.webService;

import org.jeecg.common.api.vo.Result;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;
import org.jeecg.modules.demo.interfaceOption.entity.InterfaceOption;
import org.jeecg.modules.demo.interfaceOption.vo.InterfaceOptionPage;

 /**
 * @Description: 接口配置
 * @Author: jeecg-boot
 * @Date:   2019-11-28
 * @Version: V1.0
 */
@WebService(name="interfaceOptionWebService",targetNamespace = "http://webService.interfaceOption.org.jeecg.modules.demo")
public interface InterfaceOptionWebService {
    @WebMethod
    public Result<InterfaceOption> add(@WebParam(name="interfaceOption") InterfaceOptionPage interfaceOptionPage);
    @WebMethod
    public Result<List<InterfaceOption>> addList(@WebParam(name="interfaceOption") List<InterfaceOptionPage> interfaceOptionPage);
    @WebMethod
    public Result<InterfaceOption> edit(@WebParam(name="interfaceOption") InterfaceOptionPage interfaceOptionPage);
    @WebMethod
    public Result<List<InterfaceOption>> editList(@WebParam(name="interfaceOption") List<InterfaceOptionPage> interfaceOptionPage);
    @WebMethod
    public Result<InterfaceOption> delete(@WebParam(name="interfaceOption") InterfaceOptionPage interfaceOptionPage);
}
