package com.github.h97.weixin;

import java.util.Map;
import java.util.concurrent.Callable;

import org.nutz.http.Request;
import org.nutz.http.Sender;
import org.nutz.http.Request.METHOD;
import org.nutz.lang.Encoding;
import org.nutz.lang.Lang;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.h97.weixin.pojo.Code2Session;

public class Weixin {

	private static final ObjectMapper objectMapper = new ObjectMapper().setSerializationInclusion(Include.NON_NULL);

	private final Config config;

	public Weixin(Config config) {
		super();
		this.config = config;
	}

	public Code2Session code2Session(String code) {
		return json(
				String.format("https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code",
						config.getAppid(), config.getSecret(), code),
				Code2Session.class);
	}

	private <T> T json(String url, Class<T> cls) {
		return execute(() -> objectMapper.readValue(get(url), cls));
	}

	private String get(String url) {
		return Sender.create(Request.create(url, METHOD.GET, (Map<String, Object>) null, null)).send().getContent(Encoding.UTF8);
	}

	private <T> T execute(Callable<T> callable) {
		try {
			return callable.call();
		} catch (Throwable t) {
			throw Lang.wrapThrow(t);
		}
	}
}
