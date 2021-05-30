package com.github.h97.weixin;

import java.util.Map;
import java.util.concurrent.Callable;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.util.encoders.Base64;
import org.nutz.http.Request;
import org.nutz.http.Request.METHOD;
import org.nutz.http.Sender;
import org.nutz.json.Json;
import org.nutz.lang.Encoding;
import org.nutz.lang.Lang;
import org.nutz.lang.Streams;
import org.nutz.mapl.Mapl;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.h97.weixin.pojo.Code2Session;
import com.github.h97.weixin.pojo.EncryptedData;

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

	public Object decrypt(EncryptedData data) {
		SecretKeySpec key = new SecretKeySpec(Base64.decode(data.getSessionKey()), "AES");
		IvParameterSpec iv = new IvParameterSpec(Base64.decode(data.getIv()));
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, key, iv);
			byte[] outputBytes = cipher.doFinal(Base64.decode(data.getEncryptedData()));
			Object decrypt = Json.fromJson(Streams.utf8r(Streams.wrap(outputBytes)));
			if (data.isCheck() && !config.getAppid().equals(Mapl.cell(decrypt, "watermark.appid")))
				throw new WeixinException("check failed");
			return decrypt;
		} catch (Throwable t) {
			throw new WeixinException(t);
		}
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
