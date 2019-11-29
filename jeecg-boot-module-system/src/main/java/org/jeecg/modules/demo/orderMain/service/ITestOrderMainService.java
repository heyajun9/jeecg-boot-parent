package org.jeecg.modules.demo.orderMain.service;

import org.jeecg.modules.demo.orderMain.entity.TestOrderProduct;
import org.jeecg.modules.demo.orderMain.entity.TestOrderMain;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 测试订单主表
 * @Author: jeecg-boot
 * @Date:   2019-11-29
 * @Version: V1.0
 */
public interface ITestOrderMainService extends IService<TestOrderMain> {

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(TestOrderMain testOrderMain,List<TestOrderProduct> testOrderProductList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(TestOrderMain testOrderMain,List<TestOrderProduct> testOrderProductList);
	
	/**
	 * 删除一对多
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);
	
}
