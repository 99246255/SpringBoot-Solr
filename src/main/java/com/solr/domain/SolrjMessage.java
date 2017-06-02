package com.solr.domain;

import org.apache.solr.client.solrj.beans.Field;

public class SolrjMessage {
    @Field("id")
    private String id;
    @Field("url")
    private String url;
    @Field("content")
    private String content;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
