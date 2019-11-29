package org.jeecg.modules.demo.interfaceOption.controller;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.aspect.annotation.InterfaceLog;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.interfaceOption.entity.TableOption;
import org.jeecg.modules.demo.interfaceOption.entity.InterfaceOption;
import org.jeecg.modules.demo.interfaceOption.vo.InterfaceOptionPage;
import org.jeecg.modules.demo.interfaceOption.service.IInterfaceOptionService;
import org.jeecg.modules.demo.interfaceOption.service.ITableOptionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson.JSON;

 /**
 * @Description: 接口配置
 * @Author: jeecg-boot
 * @Date:   2019-11-28
 * @Version: V1.0
 */
@RestController
@RequestMapping("/interfaceOption/interfaceOption")
@Slf4j
public class InterfaceOptionController {
	@Autowired
	private IInterfaceOptionService interfaceOptionService;
	@Autowired
	private ITableOptionService tableOptionService;
	
	/**
	  * 分页列表查询
	 * @param interfaceOption
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<IPage<InterfaceOption>> queryPageList(InterfaceOption interfaceOption,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<InterfaceOption>> result = new Result<IPage<InterfaceOption>>();
		QueryWrapper<InterfaceOption> queryWrapper = QueryGenerator.initQueryWrapper(interfaceOption, req.getParameterMap());
		Page<InterfaceOption> page = new Page<InterfaceOption>(pageNo, pageSize);
		IPage<InterfaceOption> pageList = interfaceOptionService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	  *   添加
	 * @param interfaceOptionPage
	 * @return
	 */
	@InterfaceLog(value = "接口配置-添加")
	@PostMapping(value = "/add")
	public Result<InterfaceOption> add(@RequestBody InterfaceOptionPage interfaceOptionPage) {
		Result<InterfaceOption> result = new Result<InterfaceOption>();
		try {
			InterfaceOption interfaceOption = new InterfaceOption();
			BeanUtils.copyProperties(interfaceOptionPage, interfaceOption);
			
			interfaceOptionService.saveMain(interfaceOption, interfaceOptionPage.getTableOptionList());
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}

	/**
    	  *   批量添加
    	 * @param interfaceOptionPage
    	 * @return
    	 */
    	@InterfaceLog(value = "接口配置-添加")
    	@PostMapping(value = "/addList")
    	public Result<InterfaceOption> addList(@RequestBody List<InterfaceOptionPage> interfaceOptionPages) {
    		Result<InterfaceOption> result = new Result<InterfaceOption>();
    		try {
    		for(InterfaceOptionPage interfaceOptionPage: interfaceOptionPages ){
    			InterfaceOption interfaceOption = new InterfaceOption();
    			BeanUtils.copyProperties(interfaceOptionPage, interfaceOption);

    			interfaceOptionService.saveMain(interfaceOption, interfaceOptionPage.getTableOptionList());
    			result.success("添加成功！");
    			}
    		} catch (Exception e) {
    			log.error(e.getMessage(),e);
    			result.error500("操作失败");
    		}
    		return result;
    	}
	
	/**
	  *  编辑
	 * @param interfaceOptionPage
	 * @return
	 */
	@InterfaceLog(value = "接口配置-编辑")
	@PutMapping(value = "/edit")
	public Result<InterfaceOption> edit(@RequestBody InterfaceOptionPage interfaceOptionPage) {
		Result<InterfaceOption> result = new Result<InterfaceOption>();
		InterfaceOption interfaceOption = new InterfaceOption();
		BeanUtils.copyProperties(interfaceOptionPage, interfaceOption);
		InterfaceOption interfaceOptionEntity = interfaceOptionService.getById(interfaceOption.getId());
		if(interfaceOptionEntity==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = interfaceOptionService.updateById(interfaceOption);
			interfaceOptionService.updateMain(interfaceOption, interfaceOptionPage.getTableOptionList());
			result.success("修改成功!");
		}
		
		return result;
	}
	
	/**
	  *   通过id删除
	 * @param id
	 * @return
	 */
	@InterfaceLog(value = "接口配置-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		try {
			interfaceOptionService.delMain(id);
		} catch (Exception e) {
			log.error("删除失败",e.getMessage());
			return Result.error("删除失败!");
		}
		return Result.ok("删除成功!");
	}
	
	/**
	  *  批量删除
	 * @param ids
	 * @return
	 */
	@InterfaceLog(value = "接口配置-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<InterfaceOption> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<InterfaceOption> result = new Result<InterfaceOption>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.interfaceOptionService.delBatchMain(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryById")
	public Result<InterfaceOption> queryById(@RequestParam(name="id",required=true) String id) {
		Result<InterfaceOption> result = new Result<InterfaceOption>();
		InterfaceOption interfaceOption = interfaceOptionService.getById(id);
		if(interfaceOption==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(interfaceOption);
			result.setSuccess(true);
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryTableOptionByMainId")
	public Result<List<TableOption>> queryTableOptionListByMainId(@RequestParam(name="id",required=true) String id) {
		Result<List<TableOption>> result = new Result<List<TableOption>>();
		List<TableOption> tableOptionList = tableOptionService.selectByMainId(id);
		result.setResult(tableOptionList);
		result.setSuccess(true);
		return result;
	}

  /**
      * 导出excel
   *
   * @param request
   * @param response
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, InterfaceOption interfaceOption) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<InterfaceOption> queryWrapper = QueryGenerator.initQueryWrapper(interfaceOption, request.getParameterMap());
      List<InterfaceOption> queryList = interfaceOptionService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<InterfaceOption> interfaceOptionList = new ArrayList<InterfaceOption>();
      if(oConvertUtils.isEmpty(selections)) {
    	  interfaceOptionList = queryList;
      }else {
    	  List<String> selectionList = Arrays.asList(selections.split(","));
    	  interfaceOptionList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }
	  // Step.2 组装pageList
      List<InterfaceOptionPage> pageList = new ArrayList<InterfaceOptionPage>();
      for (InterfaceOption main : interfaceOptionList) {
          InterfaceOptionPage vo = new InterfaceOptionPage();
          BeanUtils.copyProperties(main, vo);
          List<TableOption> tableOptionList = tableOptionService.selectByMainId(main.getId());
          vo.setTableOptionList(tableOptionList);
          pageList.add(vo);
      }
      // Step.3 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "接口配置列表");
      mv.addObject(NormalExcelConstants.CLASS, InterfaceOptionPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("接口配置列表数据", "导出人:Jeecg", "导出信息"));
      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      return mv;
  }

  /**
      * 通过excel导入数据
   *
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
  public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
          MultipartFile file = entity.getValue();// 获取上传文件对象
          ImportParams params = new ImportParams();
          params.setTitleRows(2);
          params.setHeadRows(1);
          params.setNeedSave(true);
          try {
              List<InterfaceOptionPage> list = ExcelImportUtil.importExcel(file.getInputStream(), InterfaceOptionPage.class, params);
              for (InterfaceOptionPage page : list) {
                  InterfaceOption po = new InterfaceOption();
                  BeanUtils.copyProperties(page, po);
                  interfaceOptionService.saveMain(po, page.getTableOptionList());
              }
              return Result.ok("文件导入成功！数据行数:" + list.size());
          } catch (Exception e) {
              log.error(e.getMessage(),e);
              return Result.error("文件导入失败:"+e.getMessage());
          } finally {
              try {
                  file.getInputStream().close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      return Result.ok("文件导入失败！");
  }

}
