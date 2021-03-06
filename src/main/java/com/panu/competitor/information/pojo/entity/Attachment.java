package com.panu.competitor.information.pojo.entity;
// Generated Sep 26, 2018 10:20:56 AM by Hibernate Tools 4.3.1

import com.panu.competitor.information.common.ConstantCommon;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * Attachment generated by hbm2java
 */
@Entity
@Table(name = "Attachment")
@NamedQueries({
    @NamedQuery(name = "Attachment.findAttachmentById", query = "SELECT t FROM Attachment t WHERE t.competitorInfoDetailId =:competitorInfoDetailId AND t.isDelete=" + ConstantCommon.STATUS_ISDELETE)
})
public class Attachment implements java.io.Serializable {

    private int attachmentId;

    private String attachedFileName;
    private int competitorInfoDetailId;
    private byte[] uploadedFile;
    private int attachedBy;
    private String attachedFileType;
    private boolean isDelete;
    private Integer createdUserId;
    private Date createdDate;
    private Integer updatedUserId;
    private Date updatedDate;

    public Attachment() {
    }

    public Attachment(int attachmentId, String attachedFileName, int competitorInfoDetailId, byte[] uploadedFile, int attachedBy, String attachedFileType, boolean isDelete, Date createdDate) {
        this.attachmentId = attachmentId;
        this.attachedFileName = attachedFileName;
        this.competitorInfoDetailId = competitorInfoDetailId;
        this.uploadedFile = uploadedFile;
        this.attachedBy = attachedBy;
        this.attachedFileType = attachedFileType;
        this.isDelete = isDelete;
        this.createdDate = createdDate;
    }

    public Attachment(int attachmentId, String attachedFileName, int competitorInfoDetailId, byte[] uploadedFile, int attachedBy, String attachedFileType, boolean isDelete, Integer createdUserId, Date createdDate, Integer updatedUserId, Date updatedDate) {
        this.attachmentId = attachmentId;
        this.attachedFileName = attachedFileName;
        this.competitorInfoDetailId = competitorInfoDetailId;
        this.uploadedFile = uploadedFile;
        this.attachedBy = attachedBy;
        this.attachedFileType = attachedFileType;
        this.isDelete = isDelete;
        this.createdUserId = createdUserId;
        this.createdDate = createdDate;
        this.updatedUserId = updatedUserId;
        this.updatedDate = updatedDate;
    }

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AttachmentId", unique = true, nullable = false)
    public int getAttachmentId() {
        return this.attachmentId;
    }

    public void setAttachmentId(int attachmentId) {
        this.attachmentId = attachmentId;
    }

    @Column(name = "AttachedFileName", nullable = false, length = 50)
    public String getAttachedFileName() {
        return this.attachedFileName;
    }

    public void setAttachedFileName(String attachedFileName) {
        this.attachedFileName = attachedFileName;
    }

    @Column(name = "CompetitorInfoDetailId", nullable = false)
    public int getCompetitorInfoDetailId() {
        return competitorInfoDetailId;
    }

    public void setCompetitorInfoDetailId(int competitorInfoDetailId) {
        this.competitorInfoDetailId = competitorInfoDetailId;
    }

    @Column(name = "UploadedFile", nullable = false)
    public byte[] getUploadedFile() {
        return this.uploadedFile;
    }

    public void setUploadedFile(byte[] uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    @Column(name = "AttachedBy", nullable = false)
    public int getAttachedBy() {
        return this.attachedBy;
    }

    public void setAttachedBy(int attachedBy) {
        this.attachedBy = attachedBy;
    }

    @Column(name = "AttachedFileType", nullable = false, length = 50)
    public String getAttachedFileType() {
        return this.attachedFileType;
    }

    public void setAttachedFileType(String attachedFileType) {
        this.attachedFileType = attachedFileType;
    }

    @Column(name = "IsDelete", nullable = false)
    public boolean isIsDelete() {
        return this.isDelete;
    }

    public void setIsDelete(boolean isDelete) {
        this.isDelete = isDelete;
    }

    @Column(name = "CreatedUserId")
    public Integer getCreatedUserId() {
        return this.createdUserId;
    }

    public void setCreatedUserId(Integer createdUserId) {
        this.createdUserId = createdUserId;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CreatedDate", nullable = false, length = 23)
    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "UpdatedUserId")
    public Integer getUpdatedUserId() {
        return this.updatedUserId;
    }

    public void setUpdatedUserId(Integer updatedUserId) {
        this.updatedUserId = updatedUserId;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UpdatedDate", length = 23)
    public Date getUpdatedDate() {
        return this.updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

}
