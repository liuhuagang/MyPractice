package com.farsight.huagang.moduls.account.util;

import org.apache.shiro.crypto.hash.SimpleHash;

public class MD5Util {

	/**
	 * 
	 * @Description   MD5加密，加密次数为1024次
	 * @param password 需要加密的字符串
	 * @param salt  盐值 一般用用户名即可
	 * @return
	 */
	public static String getPasswordByMD5(String password, String salt) {
		String simpleHash = new SimpleHash("MD5", password, salt, 1024).toString();
		return simpleHash;
	}
}
