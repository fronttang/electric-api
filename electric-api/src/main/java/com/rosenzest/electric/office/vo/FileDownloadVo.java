package com.rosenzest.electric.office.vo;

import java.util.Map;

import lombok.Data;

@Data
public class FileDownloadVo {

	/**
	 * 文档下载地址，该地址需确保外网可访问且排除访问时防火墙的限制
	 */
	private String url;

	/**
	 * 文档校验和 (checksum)
	 */
	private String digest;

	/**
	 * 文档校验和算法 md5 或者 sha1
	 */
	private String digest_type;

	/**
	 * 请求文档下载地址所需要的额外请求头，例如某些云存储商会要求额外的签名头等
	 */
	private Map<String, String> headers;
}
