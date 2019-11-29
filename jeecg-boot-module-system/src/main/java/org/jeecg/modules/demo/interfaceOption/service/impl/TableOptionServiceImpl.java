package org.jeecg.modules.demo.interfaceOption.service.impl;

import org.jeecg.modules.demo.interfaceOption.entity.TableOption;
import org.jeecg.modules.demo.interfaceOption.mapper.TableOptionMapper;
import org.jeecg.modules.demo.interfaceOption.service.ITableOptionService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 字段对应表
 * @Author: jeecg-boot
 * @Date:   2019-11-28
 * @Version: V1.0
 */
@Service
public class TableOptionServiceImpl extends ServiceImpl<TableOptionMapper, TableOption> implements ITableOptionService {
	
	@Autowired
	private TableOptionMapper tableOptionMapper;
	
	@Override
	public List<TableOption> selectByMainId(String mainId) {
		return tableOptionMapper.selectByMainId(mainId);
	}
}
