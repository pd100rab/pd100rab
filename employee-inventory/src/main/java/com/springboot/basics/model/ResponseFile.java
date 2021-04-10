package com.springboot.basics.model;

public class ResponseFile {
	private String name;
	private String url;
	private String status;
	private int countOk;
	private int countKo;

	public ResponseFile(final String name, final String url, final String status, final int countOk,
			final int countKo) {
		super();
		this.name = name;
		this.url = url;
		this.status = status;
		this.countOk = countOk;
		this.countKo = countKo;
	}

	public ResponseFile() {
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public int getCountOk() {
		return this.countOk;
	}

	public void setCountOk(final int countOk) {
		this.countOk = countOk;
	}

	public int getCountKo() {
		return this.countKo;
	}

	public void setCountKo(final int countKo) {
		this.countKo = countKo;
	}

}
