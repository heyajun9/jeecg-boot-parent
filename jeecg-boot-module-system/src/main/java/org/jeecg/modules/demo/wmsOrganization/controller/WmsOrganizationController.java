package org.jeecg.modules.demo.wmsOrganization.controller;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.InterfaceLog;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.wmsOrganization.entity.WmsOrganization;
import org.jeecg.modules.demo.wmsOrganization.service.IWmsOrganizationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;

 /**
 * @Description: 组织单位表
 * @Author: jeecg-boot
 * @Date:   2019-11-29
 * @Version: V1.0
 */
@RestController
@RequestMapping("/wmsOrganization/wmsOrganization")
@Slf4j
@Api(tags="wmsOrganization")
public class WmsOrganizationController {
	@Autowired
	private IWmsOrganizationService wmsOrganizationService;
	
	/**
	  * 分页列表查询
	 * @param wmsOrganization
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	@ApiOperation(value = "获取WmsOrganization数据列表", notes = "获取所有WmsOrganization数据列表")
	public Result<IPage<WmsOrganization>> queryPageList(WmsOrganization wmsOrganization,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<WmsOrganization>> result = new Result<IPage<WmsOrganization>>();
		QueryWrapper<WmsOrganization> queryWrapper = QueryGenerator.initQueryWrapper(wmsOrganization, req.getParameterMap());
		Page<WmsOrganization> page = new Page<WmsOrganization>(pageNo, pageSize);
		IPage<WmsOrganization> pageList = wmsOrganizationService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	  *   添加
	 * @param wmsOrganization
	 * @return
	 */
	@InterfaceLog(value = "组织单位表-添加")
	@PostMapping(value = "/add")
	@ApiOperation(value = "添加WmsOrganization", notes = "添加WmsOrganization")
	public Result<WmsOrganization> add(@RequestBody WmsOrganization wmsOrganization) {
		Result<WmsOrganization> result = new Result<WmsOrganization>();
		try {
			wmsOrganizationService.save(wmsOrganization);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}

	/**
    	  *   批量添加
    	 * @param wmsOrganization
    	 * @return
    	 */
    	@InterfaceLog(value = "组织单位表-添加")
    	@PostMapping(value = "/addList")
    	@ApiOperation(value = "批量添加WmsOrganization", notes = "批量添加WmsOrganization")
    	public Result<WmsOrganization> addList(@RequestBody List<WmsOrganization> wmsOrganizations) {
    		Result<WmsOrganization> result = new Result<WmsOrganization>();
    		try {
    		for (WmsOrganization wmsOrganization : wmsOrganizations) {
    			wmsOrganizationService.save(wmsOrganization);
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
	 * @param wmsOrganization
	 * @return
	 */
	@InterfaceLog(value = "组织单位表-编辑")
	@PutMapping(value = "/edit")
	@ApiOperation(value = "编辑WmsOrganization", notes = "编辑WmsOrganization")
	public Result<WmsOrganization> edit(@RequestBody WmsOrganization wmsOrganization) {
		Result<WmsOrganization> result = new Result<WmsOrganization>();
		WmsOrganization wmsOrganizationEntity = wmsOrganizationService.getById(wmsOrganization.getId());
		if(wmsOrganizationEntity==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = wmsOrganizationService.updateById(wmsOrganization);
			//TODO 返回false说明什么？
			if(ok) {
				result.success("修改成功!");
			}
		}
		
		return result;
	}
	
	/**
	  *   通过id删除
	 * @param id
	 * @return
	 */
	@InterfaceLog(value = "组织单位表-通过id删除")
	@DeleteMapping(value = "/delete")
	@ApiOperation(value = "通过ID删除WmsOrganization", notes = "通过ID删除WmsOrganization")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		try {
			wmsOrganizationService.removeById(id);
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
	@InterfaceLog(value = "组织单位表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	@ApiOperation(value = "批量删除WmsOrganization", notes = "批量删除WmsOrganization")
	public Result<WmsOrganization> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<WmsOrganization> result = new Result<WmsOrganization>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.wmsOrganizationService.removeByIds(Arrays.asList(ids.split(",")));
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
	@ApiOperation(value = "通过ID查询WmsOrganization", notes = "通过ID查询WmsOrganization")
	public Result<WmsOrganization> queryById(@RequestParam(name="id",required=true) String id) {
		Result<WmsOrganization> result = new Result<WmsOrganization>();
		WmsOrganization wmsOrganization = wmsOrganizationService.getById(id);
		if(wmsOrganization==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(wmsOrganization);
			result.setSuccess(true);
		}
		return result;
	}

  /**
      * 导出excel
   *
   * @param request
   * @param response
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, WmsOrganization wmsOrganization) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<WmsOrganization> queryWrapper = QueryGenerator.initQueryWrapper(wmsOrganization, request.getParameterMap());
      List<WmsOrganization> pageList = wmsOrganizationService.list(queryWrapper);
      // Step.2 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      // 过滤选中数据
      String selections = request.getParameter("selections");
      if(oConvertUtils.isEmpty(selections)) {
    	  mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      }else {
    	  List<String> selectionList = Arrays.asList(selections.split(","));
    	  List<WmsOrganization> exportList = pageList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
    	  mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
      }
      //导出文件名称
      mv.addObject(NormalExcelConstants.FILE_NAME, "组织单位表列表");
      mv.addObject(NormalExcelConstants.CLASS, WmsOrganization.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("组织单位表列表数据", "导出人:Jeecg", "导出信息"));
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
              List<WmsOrganization> listWmsOrganizations = ExcelImportUtil.importExcel(file.getInputStream(), WmsOrganization.class, params);
              wmsOrganizationService.saveBatch(listWmsOrganizations);
              return Result.ok("文件导入成功！数据行数:" + listWmsOrganizations.size());
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
