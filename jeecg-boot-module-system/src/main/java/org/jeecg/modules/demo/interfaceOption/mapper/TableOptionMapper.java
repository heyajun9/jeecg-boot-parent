package org.jeecg.modules.demo.interfaceOption.mapper;

import java.util.List;
import org.jeecg.modules.demo.interfaceOption.entity.TableOption;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 字段对应表
 * @Author: jeecg-boot
 * @Date:   2019-11-28
 * @Version: V1.0
 */
public interface TableOptionMapper extends BaseMapper<TableOption> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<TableOption> selectByMainId(@Param("mainId") String mainId);
}
