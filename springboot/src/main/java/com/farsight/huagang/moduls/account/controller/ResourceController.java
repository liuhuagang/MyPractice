/**
 * 
 */
package com.farsight.huagang.moduls.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.farsight.huagang.moduls.account.entity.Resource;
import com.farsight.huagang.moduls.account.entity.User;
import com.farsight.huagang.moduls.account.service.ResourceService;
import com.farsight.huagang.moduls.common.vo.Result;
import com.farsight.huagang.moduls.common.vo.SearchVo;
import com.github.pagehelper.PageInfo;

/**
 * @author  liu
 *	@date  2020年4月8日
 *	@Description   
 *	@version  
 */
@RestController
@RequestMapping("/api")
public class ResourceController {
	@Autowired
	private ResourceService resourceService;

	@PostMapping(value = "/resource", consumes = "application/json")
	@ResponseBody
	public Result register(@RequestBody Resource resource) {

		return resourceService.addResource(resource);
	}
	
	

	@PostMapping(value = "/resources", consumes = "application/json")
	public PageInfo<Resource> getResourcesBySearchVo(@RequestBody SearchVo searchVo) {
		return resourceService.getResourcesBySearchVo(searchVo);
	}


	@RequestMapping("/resource/{resourceId}")
	public Resource getResourceById(@PathVariable int resourceId) {
		return resourceService.getResourceById(resourceId);
	}

	@PutMapping(value = "/resource", consumes = "application/json")
	public Result updateResource(@RequestBody Resource resource) {
		return resourceService.updateResource(resource);
	}

	@DeleteMapping("/resource/{resourceId}")
	public Result deleteResource(@PathVariable int resourceId) {
		return resourceService.deleteResource(resourceId);
	}
}
