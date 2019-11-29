package org.jeecg.modules.demo.pickticket.mapper;

import java.util.List;
import org.jeecg.modules.demo.pickticket.entity.WmsPickticketDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 出库单明细
 * @Author: jeecg-boot
 * @Date:   2019-11-22
 * @Version: V1.0
 */
public interface WmsPickticketDetailMapper extends BaseMapper<WmsPickticketDetail> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<WmsPickticketDetail> selectByMainId(@Param("mainId") String mainId);
}
