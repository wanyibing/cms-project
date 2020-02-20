package com.wanyibing.Utils;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.digest.DigestUtils;

public class CmsUtils {
/**
 *  
 */
	public static String encry(String src,String salt) {
		
		byte[] md5 = DigestUtils.md5(salt+src+salt);
		
		String enPwd;
		try {
			enPwd = new String(md5,"UTF-8");
			return enPwd;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return salt+src+salt;
		}
		
		
		
	}
	
}
