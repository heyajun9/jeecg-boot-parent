package org.jeecg.modules.demo.orderMain.mapper;

import java.util.List;
import org.jeecg.modules.demo.orderMain.entity.TestOrderProduct;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 订单产品明细
 * @Author: jeecg-boot
 * @Date:   2019-11-29
 * @Version: V1.0
 */
public interface TestOrderProductMapper extends BaseMapper<TestOrderProduct> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<TestOrderProduct> selectByMainId(@Param("mainId") String mainId);
}
