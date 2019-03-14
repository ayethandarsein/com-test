package com.panu.competitor.information.pojo.entity;
// Generated Oct 1, 2018 2:56:01 PM by Hibernate Tools 4.3.1


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * AppSetting generated by hbm2java
 */

@Entity
@Table(name="AppSetting")
public class AppSetting  implements java.io.Serializable {


     private int appId;
     private String appName;
     private String appValue;
     private String description;
     private boolean isDelete;
     private int createdUserId;
     private Date createdDate;
     private Integer updatedUserId;
     private Date updatedDate;

    public AppSetting() {
    }

	
    public AppSetting(int appId, String appName, String appValue, boolean isDelete, int createdUserId, Date createdDate) {
        this.appId = appId;
        this.appName = appName;
        this.appValue = appValue;
        this.isDelete = isDelete;
        this.createdUserId = createdUserId;
        this.createdDate = createdDate;
    }
    public AppSetting(int appId, String appName, String appValue, String description, boolean isDelete, int createdUserId, Date createdDate, Integer updatedUserId, Date updatedDate) {
       this.appId = appId;
       this.appName = appName;
       this.appValue = appValue;
       this.description = description;
       this.isDelete = isDelete;
       this.createdUserId = createdUserId;
       this.createdDate = createdDate;
       this.updatedUserId = updatedUserId;
       this.updatedDate = updatedDate;
    }
   
     @Id 

     @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="AppId", unique=true, nullable=false)
    public int getAppId() {
        return this.appId;
    }
    
    public void setAppId(int appId) {
        this.appId = appId;
    }
    
    @Column(name="AppName", nullable=false, length=20)
    public String getAppName() {
        return this.appName;
    }
    
    public void setAppName(String appName) {
        this.appName = appName;
    }

    
    @Column(name="AppValue", nullable=false, length=50)
    public String getAppValue() {
        return this.appValue;
    }
    
    public void setAppValue(String appValue) {
        this.appValue = appValue;
    }

    
    @Column(name="Description", length=50)
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    
    @Column(name="IsDelete", nullable=false)
    public boolean isIsDelete() {
        return this.isDelete;
    }
    
    public void setIsDelete(boolean isDelete) {
        this.isDelete = isDelete;
    }

    
    @Column(name="CreatedUserId", nullable=false)
    public int getCreatedUserId() {
        return this.createdUserId;
    }
    
    public void setCreatedUserId(int createdUserId) {
        this.createdUserId = createdUserId;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CreatedDate", nullable=false, length=23)
    public Date getCreatedDate() {
        return this.createdDate;
    }
    
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    
    @Column(name="UpdatedUserId")
    public Integer getUpdatedUserId() {
        return this.updatedUserId;
    }
    
    public void setUpdatedUserId(Integer updatedUserId) {
        this.updatedUserId = updatedUserId;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="UpdatedDate", length=23)
    public Date getUpdatedDate() {
        return this.updatedDate;
    }
    
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }




}

