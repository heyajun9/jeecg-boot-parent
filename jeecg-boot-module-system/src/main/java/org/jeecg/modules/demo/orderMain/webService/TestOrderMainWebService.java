package org.jeecg.modules.demo.orderMain.webService;

import org.jeecg.common.api.vo.Result;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;
import org.jeecg.modules.demo.orderMain.entity.TestOrderMain;
import org.jeecg.modules.demo.orderMain.vo.TestOrderMainPage;

 /**
 * @Description: 测试订单主表
 * @Author: jeecg-boot
 * @Date:   2019-11-29
 * @Version: V1.0
 */
@WebService(name="testOrderMainWebService",targetNamespace = "http://webService.orderMain.org.jeecg.modules.demo")
public interface TestOrderMainWebService {
    @WebMethod
    public Result<TestOrderMain> add(@WebParam(name="testOrderMain") TestOrderMainPage testOrderMainPage);
    @WebMethod
    public Result<List<TestOrderMain>> addList(@WebParam(name="testOrderMain") List<TestOrderMainPage> testOrderMainPage);
    @WebMethod
    public Result<TestOrderMain> edit(@WebParam(name="testOrderMain") TestOrderMainPage testOrderMainPage);
    @WebMethod
    public Result<List<TestOrderMain>> editList(@WebParam(name="testOrderMain") List<TestOrderMainPage> testOrderMainPage);
    @WebMethod
    public Result<TestOrderMain> delete(@WebParam(name="testOrderMain") TestOrderMainPage testOrderMainPage);
}
