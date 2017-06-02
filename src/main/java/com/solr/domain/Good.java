package com.solr.domain;

import org.apache.solr.client.solrj.beans.Field;

import java.util.Date;

public class Good{
    @Field("id")
    private String id;
    @Field("number")
    private String number;
    @Field("name")
    private String name;
    @Field("updateTime")
    private Date updateTime;

    @Override
    public String toString() {
        return "Good [id=" + id + ", number=" + number + ", name=" + name + ", updateTime=" + updateTime + "]";
    }

    public Good(){}

    public Good(String id, String number, String name, Date updateTime) {
        super();
        this.id = id;
        this.number = number;
        this.name = name;
        this.updateTime = updateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Date getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}