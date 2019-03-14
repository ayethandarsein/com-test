/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panu.competitor.information.common;

import com.panu.competitor.information.pojo.entity.Attachment;
import com.panu.competitor.information.pojo.entity.PlantInformationDetail;
import java.util.Date;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Htet Nanda Kyaw
 */
public class AttachmentHandler {

    private CurrentLoggedInUser cu = new CurrentLoggedInUser();
    private Attachment attachment;

    public AttachmentHandler() {
    }

    public Attachment setAttachmentData(UploadedFile uploadfile, String fileType) {
        attachment = new Attachment();
        String filename = uploadfile.getFileName();
        byte[] file = new byte[uploadfile.getContents().length];
        System.arraycopy(uploadfile.getContents(), 0, file, 0, uploadfile.getContents().length);
        attachment.setAttachedFileName(filename);
        attachment.setUploadedFile(file);
        attachment.setIsDelete(false);
        attachment.setAttachedBy(cu.getLogInUserId());
        attachment.setAttachedFileType(fileType);
        attachment.setCreatedUserId(cu.getLogInUserId());
        attachment.setCreatedDate(new Date());
        return attachment;
    }
}
