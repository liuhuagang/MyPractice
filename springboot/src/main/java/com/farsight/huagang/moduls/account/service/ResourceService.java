/**
 * 
 */
package com.farsight.huagang.moduls.account.service;

import com.farsight.huagang.moduls.account.entity.Resource;
import com.farsight.huagang.moduls.common.vo.Result;
import com.farsight.huagang.moduls.common.vo.SearchVo;
import com.github.pagehelper.PageInfo;

/**
 * @author  liu
 *	@date  2020年4月8日
 *	@Description   
 *	@version  
 */

public interface ResourceService {
	PageInfo<Resource> getResourcesBySearchVo(SearchVo searchVo);

	Resource getResourceById(int userId);

	Result updateResource(Resource user);

	Result deleteResource(int resourceId);

	/**
	 * @Description  
	 * @param resource
	 * @return
	 */
	Result addResource(Resource resource);
}
