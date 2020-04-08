/**
 * 
 */
package com.farsight.huagang.moduls.account.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.farsight.huagang.moduls.account.dao.ResourceDao;
import com.farsight.huagang.moduls.account.entity.Resource;
import com.farsight.huagang.moduls.account.entity.User;
import com.farsight.huagang.moduls.account.service.ResourceService;
import com.farsight.huagang.moduls.common.vo.Result;
import com.farsight.huagang.moduls.common.vo.Result.ResultStatus;
import com.farsight.huagang.moduls.common.vo.SearchVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @author  liu
 *	@date  2020年4月8日
 *	@Description   
 *	@version  
 */
@Service
public class ResourceServiceImpl implements ResourceService {

	@Autowired
	private ResourceDao resourceDao;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PageInfo<Resource> getResourcesBySearchVo(SearchVo searchVo) {
		searchVo.initSearchVo(searchVo);
		PageHelper.startPage(searchVo.getCurrentPage(), searchVo.getPageSize());
		List<Resource> resources = Optional.ofNullable(resourceDao.getResourcesBySearchVo(searchVo))
				.orElse(Collections.emptyList());
		return new PageInfo(resources);
	}

	@Override
	public Resource getResourceById(int resourceId) {
		return resourceDao.getResourceById(resourceId);
	}

	@Override
	public Result updateResource(Resource resource) {

		Result result = new Result(ResultStatus.SUCCESS.status, "");

		Resource resourceTemp = resourceDao.getResourceByResourceName(resource.getResourceName());
		if (resourceTemp != null) {
			result.setStatus(ResultStatus.FAILED.status);
			result.setMessage("Resource name is repeat.");
			return result;
		}

		resourceDao.updateResource(resource);

		return result;
	}

	@Override
	public Result deleteResource(int resourceId) {
		Result result = new Result(ResultStatus.SUCCESS.status, "");
		try {
			resourceDao.deleteResource(resourceId);
		} catch (Exception e) {
			result.setStatus(ResultStatus.FAILED.status);
			result.setMessage(e.getMessage());
			return result;
		}
		return result;
	}

	@Override
	public Result addResource(Resource resource) {

		Result result = new Result(ResultStatus.SUCCESS.status, "");

		Resource resourceTemp = resourceDao.getResourceByResourceName(resource.getResourceName());
		if (resourceTemp != null) {
			result.setStatus(ResultStatus.FAILED.status);
			result.setMessage("User name is repeat.");
			return result;
		}

		resourceDao.insertResource(resource);

		return result;

	}

}
