package com.money.tap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.money.tap.common.Cache;
import com.money.tap.service.PriceGenerator;

@RestController
@RequestMapping(value = "/bitcoin")
public class BitCoinsPriceController {

	@Autowired
	private PriceGenerator priceGenerator;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String getBitCoinList() {
		if (!Cache.prices.isEmpty()) {
			return Cache.prices.get("bit.coin.data");

		} else {
			try {
				this.priceGenerator.generate();
				return Cache.prices.get("bit.coin.data");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
