package com.money.tap.service;

import java.util.concurrent.ThreadLocalRandom;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.money.tap.common.Cache;
import com.money.tap.common.Constant;
import com.money.tap.common.Global;

@Component
public class PriceGenerator implements Job {

	private static final Logger log = LoggerFactory.getLogger(PriceGenerator.class);

	/**
	 * Worker thread execute the Job
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			log.info("<<------------------Scheduler Task Start-------------->>");
			/**
			 * scheduler will generate bit coins price and store in local cache
			 * and post data to web hooks
			 */
			generate();

			log.info("<<------------------Scheduler Task End---------------->>");
		} catch (Exception e) {
			log.error("Error in price generator Worker thread class: ", e);
		}
	}

	
	public void generate() throws JSONException {
		JSONArray json = new JSONArray();
		for (Global.Currency s: Global.Currency.values()) {
			JSONObject curr = new JSONObject();
			curr.put(Constant.CURRENCY, s);
			double high = getHighPrice(s.getValue());
			curr.put(Constant.HIGH, high);
			double low = getLowPrice(s.getValue());
			curr.put(Constant.LOW, low);
			curr.put(Constant.LATEST_TRADE, getLatestTrade());
			double avg = getAverage(high, low);
			curr.put(Constant.AVREAGE, avg);
			curr.put(Constant.VOLUME, getVolume());
			curr.put(Constant.CLOSE, getClose());
			curr.put(Constant.WEIGHTED_PRICE, avg);
			json.put(curr);
		}
		
		//store new data into local cache
		Cache.prices.put("bit.coin.data", json.toString());
		try {
			//post same data which can be used as web hooks
			postBitCoinPrice(Global.WEB_URL, json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Double getHighPrice(String currency) {
		if (currency.equals(Global.Currency.USD.getValue())) {
			return ThreadLocalRandom.current().nextDouble(Constant.HIGH_PRICE_RANG[0], Constant.HIGH_PRICE_RANG[1] + 1);
		}

		if (currency.equals(Global.Currency.EUR.getValue())) {
			return ThreadLocalRandom.current().nextDouble(Constant.HIGH_PRICE_RANG[0], Constant.HIGH_PRICE_RANG[1] + 1);
		}
		if (currency.equals(Global.Currency.JPY.getValue())) {
			return ThreadLocalRandom.current().nextDouble(Constant.HIGH_PRICE_RANG[0], Constant.HIGH_PRICE_RANG[1] + 1);
		}
		if (currency.equals(Global.Currency.BRL.getValue())) {
			return ThreadLocalRandom.current().nextDouble(Constant.HIGH_PRICE_RANG[0], Constant.HIGH_PRICE_RANG[1] + 1);
		}
		if (currency.equals(Global.Currency.AUD.getValue())) {
			return ThreadLocalRandom.current().nextDouble(Constant.HIGH_PRICE_RANG[0], Constant.HIGH_PRICE_RANG[1] + 1);
		}
		return null;
	}

	public Double getLowPrice(String currency) {
		if (currency.equals(Global.Currency.USD.getValue())) {
			return ThreadLocalRandom.current().nextDouble(Constant.LOW_PRICE_RANG[0], Constant.LOW_PRICE_RANG[1] + 1);
		}

		if (currency.equals(Global.Currency.EUR.getValue())) {
			return ThreadLocalRandom.current().nextDouble(Constant.LOW_PRICE_RANG[0], Constant.LOW_PRICE_RANG[1] + 1);
		}
		if (currency.equals(Global.Currency.JPY.getValue())) {
			return ThreadLocalRandom.current().nextDouble(Constant.LOW_PRICE_RANG[0], Constant.LOW_PRICE_RANG[1] + 1);
		}
		if (currency.equals(Global.Currency.BRL.getValue())) {
			return ThreadLocalRandom.current().nextDouble(Constant.LOW_PRICE_RANG[0], Constant.LOW_PRICE_RANG[1] + 1);
		}
		if (currency.equals(Global.Currency.AUD.getValue())) {
			return ThreadLocalRandom.current().nextDouble(Constant.LOW_PRICE_RANG[0], Constant.LOW_PRICE_RANG[1] + 1);
		}
		return null;
	}

	public long getLatestTrade() {
		return System.currentTimeMillis();
	}

	public double getVolume() {
		return ThreadLocalRandom.current().nextDouble(Constant.VOLUME_RANGE[0], Constant.VOLUME_RANGE[0] + 1);
	}

	public double getAverage(double high, double low) {
		return (high+low)/2;
	}

	public double getClose() {
		return ThreadLocalRandom.current().nextDouble();
	}

	public void postBitCoinPrice(String url, String params) throws Exception {
		try (CloseableHttpClient httpclient = HttpClientBuilder.create().build()) {
			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeader(Global.HeaderType.CONTENT_TYPE, Constant.APPLICATION_JSON);

			StringEntity entity = new StringEntity(params, "UTF-8");
			entity.setContentEncoding("UTF-8");
			httpPost.setEntity(entity);
			HttpResponse res = httpclient.execute(httpPost);
			//HttpEntity resEntity = res.getEntity();
			if (res.getStatusLine().getStatusCode() == 200) {
				//String str = EntityUtils.toString(resEntity, "UTF-8").toString();
				log.info("Data Post Successfully");
			}
		} catch (Exception ex) {
			log.error("Exception ", ex);
		}
	}

}
