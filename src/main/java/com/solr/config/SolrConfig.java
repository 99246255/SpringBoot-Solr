/**
 * Project Name:chenxun-solr
 * File Name:SolrConfig.java
 * Package Name:com.chenxun.solr.config
 * Date:2016年8月20日下午3:11:32
 * Copyright (c) 2016, www midaigroup com Technology Co., Ltd. All Rights Reserved.
 *
 */

package com.solr.config;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

@Configuration
@EnableSolrRepositories(basePackages = { "com.solr" }, multicoreSupport = true)
public class SolrConfig {

	@Value("${spring.data.solr.host}")
	private String url;

	@Bean
	public SolrClient solrClient() {
		return new HttpSolrClient(url);
	}

	@Bean
	public SolrTemplate solrTemplate() throws Exception {
		SolrTemplate solrTemplate =  new SolrTemplate(solrClient());
//		solrTemplate.setSolrConverter(solrConverter);
		return solrTemplate;
	}
	



}
