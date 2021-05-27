package com.github.h97.weixin.pojo;

public class BaseResult {

	private int errcode;
	private String errmsg;

	public boolean isOk() {
		return errcode == 0;
	}

	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

}
