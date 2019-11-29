package org.jeecg.modules.demo.interfaceOption.service.impl;

import org.jeecg.modules.demo.interfaceOption.entity.InterfaceOption;
import org.jeecg.modules.demo.interfaceOption.entity.TableOption;
import org.jeecg.modules.demo.interfaceOption.mapper.TableOptionMapper;
import org.jeecg.modules.demo.interfaceOption.mapper.InterfaceOptionMapper;
import org.jeecg.modules.demo.interfaceOption.service.IInterfaceOptionService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 接口配置
 * @Author: jeecg-boot
 * @Date:   2019-11-28
 * @Version: V1.0
 */
@Service
public class InterfaceOptionServiceImpl extends ServiceImpl<InterfaceOptionMapper, InterfaceOption> implements IInterfaceOptionService {

	@Autowired
	private InterfaceOptionMapper interfaceOptionMapper;
	@Autowired
	private TableOptionMapper tableOptionMapper;
	
	@Override
	@Transactional
	public void saveMain(InterfaceOption interfaceOption, List<TableOption> tableOptionList) {
		interfaceOptionMapper.insert(interfaceOption);
		if(tableOptionList!=null && tableOptionList.size()>0) {
			for(TableOption entity:tableOptionList) {
				//外键设置
				entity.setInterfaceFkId(interfaceOption.getId());
				tableOptionMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(InterfaceOption interfaceOption,List<TableOption> tableOptionList) {
		interfaceOptionMapper.updateById(interfaceOption);
		
		//1.先删除子表数据
		tableOptionMapper.deleteByMainId(interfaceOption.getId());
		
		//2.子表数据重新插入
		if(tableOptionList!=null && tableOptionList.size()>0) {
			for(TableOption entity:tableOptionList) {
				//外键设置
				entity.setInterfaceFkId(interfaceOption.getId());
				tableOptionMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		tableOptionMapper.deleteByMainId(id);
		interfaceOptionMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			tableOptionMapper.deleteByMainId(id.toString());
			interfaceOptionMapper.deleteById(id);
		}
	}
	
}
