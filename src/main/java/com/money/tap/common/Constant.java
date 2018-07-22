package com.money.tap.common;

public interface Constant {

	public static final String HIGH = "high"; // price
	public static final String CURRENCY = "currency"; // state currency symbol
	public static final String AVREAGE = "avg"; // average price
	public static final String LOW = "low"; // low pricce
	public static final String DURATION = "duration"; // time duration
	public static final String CLOSE = "close"; // closing price
	public static final String VOLUME = "volume"; // volume of
	public static final String WEIGHTED_PRICE = "weighted_price"; // price
	public static final String LATEST_TRADE = "latest_trade"; // latest trade timing

	public static final double HIGH_PRICE_RANG[] = { 4000.00000000000, 10000.0000000000000 };
	public static final double LOW_PRICE_RANG[] = { 0.00000000000, 3999.9999999999999 };
	public static final float VOLUME_RANGE[] = { 0.000000f, 3000.000000f };
	
	
	public static final String APPLICATION_JSON = "application/json";

}
