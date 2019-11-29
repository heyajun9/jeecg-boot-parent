package org.jeecg.modules.demo.interfac.webService;

import org.jeecg.common.api.vo.Result;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;
import org.jeecg.modules.demo.interfac.entity.Interfac;

 /**
 * @Description: 测试
 * @Author: jeecg-boot
 * @Date:   2019-11-29
 * @Version: V1.0
 */
@WebService(name="interfacWebService",targetNamespace = "http://webService.interfac.org.jeecg.modules.demo")
public interface InterfacWebService {
    @WebMethod
    public Result<Interfac> add(@WebParam(name="interfac") Interfac interfac);
    @WebMethod
    public Result<List<Interfac>> addList(@WebParam(name="interfac") List<Interfac> interfac);
    @WebMethod
    public Result<Interfac> edit(@WebParam(name="interfac") Interfac interfac);
    @WebMethod
    public Result<List<Interfac>> editList(@WebParam(name="interfac") List<Interfac> interfac);
    @WebMethod
    public Result<Interfac> delete(@WebParam(name="interfac") Interfac interfac);
}
