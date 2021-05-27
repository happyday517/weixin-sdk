package com.github.h97.weixin.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Code2Session extends BaseResult {

	private String openid;
	@JsonProperty("session_key")
	private String sessionKey;
	private String unionid;

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

}
