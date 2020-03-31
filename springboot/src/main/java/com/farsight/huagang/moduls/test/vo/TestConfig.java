/**
 * 
 */
package com.farsight.huagang.moduls.test.vo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author  liu
 *	@date  2020年3月31日
 *	@Description   
 *	@version  
 */
@Component
@PropertySource("classpath:config/test.properties")
@ConfigurationProperties(prefix = "com.huagang2")
public class TestConfig {

	private int name;

	public int getName() {
		return name;
	}

	public void setName(int name) {
		this.name = name;
	}

}
