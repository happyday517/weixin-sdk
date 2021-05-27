package com.github.h97.weixin;

import java.util.Properties;

public class Config {

	private String appid;
	private String secret;

	public Config(String appid, String secret) {
		super();
		this.appid = appid;
		this.secret = secret;
	}

	public Config(Properties properties, String prefix) {
		this.appid = properties.getProperty(prefix + "appid");
		this.secret = properties.getProperty(prefix + "secret");
	}

	public Config(Properties properties) {
		this(properties, "weixin.");
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}
}
