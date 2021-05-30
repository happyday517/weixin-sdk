package com.github.h97.weixin;

public class WeixinException extends RuntimeException {

	public WeixinException(String message) {
		super(message);
	}

	public WeixinException(Throwable t) {
		super(t);
	}

	private static final long serialVersionUID = 1L;

}
