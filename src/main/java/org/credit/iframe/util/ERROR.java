package org.credit.iframe.util;

public enum ERROR {
	NOTFOUND("{0} not found !");
	
	private String val;

	private ERROR(String val) {
		this.val = val;
	}
	
	public String displayError() {
		return val;
	}
}
