package com.money.tap.common;

import java.util.concurrent.ConcurrentHashMap;

public class Cache {

	/**
	 * Small set of bit coins price data are stored in in-memory cache, for
	 * large set of data we can implement distributed level of cache like
	 * redis/memcached
	 */
	public static ConcurrentHashMap<String, String> prices = new ConcurrentHashMap<String, String>();

}
