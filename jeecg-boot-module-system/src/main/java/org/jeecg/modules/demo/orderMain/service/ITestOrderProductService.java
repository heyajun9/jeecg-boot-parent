package org.jeecg.modules.demo.orderMain.service;

import org.jeecg.modules.demo.orderMain.entity.TestOrderProduct;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 订单产品明细
 * @Author: jeecg-boot
 * @Date:   2019-11-29
 * @Version: V1.0
 */
public interface ITestOrderProductService extends IService<TestOrderProduct> {

	public List<TestOrderProduct> selectByMainId(String mainId);
}
