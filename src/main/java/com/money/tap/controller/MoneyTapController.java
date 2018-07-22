package com.money.tap.controller;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="bitcoin/")
public class MoneyTapController {
	
	private static final Logger log = LoggerFactory.getLogger(MoneyTapController.class);
	AtomicInteger i = new AtomicInteger();

	@RequestMapping(value = "/data.json", method = RequestMethod.POST)
	void hello(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			String json = getRequestPostStr(request);
			log.info("Post Number "+i+" Data: "+json);
		} finally {
			i.incrementAndGet();
		}

	}
	
	public static String getRequestPostStr(HttpServletRequest request) throws IOException {
		byte buffer[] = getRequestPostBytes(request);
		String charEncoding = request.getCharacterEncoding();
		if (charEncoding == null) {
			charEncoding = "UTF-8";
		}
		return new String(buffer, charEncoding);
	}

	public static byte[] getRequestPostBytes(HttpServletRequest request) throws IOException {
		int contentLength = request.getContentLength();
		if (contentLength < 0) {
			return null;
		}
		byte buffer[] = new byte[contentLength];
		for (int i = 0; i < contentLength;) {
			int readlen = request.getInputStream().read(buffer, i, contentLength - i);
			if (readlen == -1) {
				break;
			}
			i += readlen;
		}
		return buffer;
	}

}
