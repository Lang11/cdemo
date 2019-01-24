package com.test.employ;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.TimeUnit;

/**
 * @program: client
 * @description:
 * @author: daniel
 * @create: 2019-01-24 10:16
 **/
public class CacheUtil {
	final static LoadingCache<String,String> cache = CacheBuilder.newBuilder()
			.maximumSize(10)
			.expireAfterAccess(1, TimeUnit.SECONDS)
			.expireAfterWrite(1, TimeUnit.SECONDS)
			.build(
					new CacheLoader<String, String>() {
						public String load(String key) throws Exception {
							return "cacheloading...";
						}
					});

	public static void put(String key, String value) {
		cache.put(key,value);
	}

	public static String get(String key) {
		return cache.getIfPresent(key);
	}
}
