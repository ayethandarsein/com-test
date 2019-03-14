/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panu.competitor.information.spring.service;


import com.panu.competitor.information.exception.CompetitorException;
import com.panu.competitor.information.pojo.entity.Attachment;
import java.util.List;

/**
 * Interface Class for Attachment Service
 *
 * @author HNDK
 */
public interface IAttachmentService {

    /**
     * The purpose of this method is Add New Attachment.
     *
     * @param attachemnt
     *
     */
    public void addAttachment(Attachment attachemnt);

    /**
     * The purpose of this method is to get attachment by .
     *
     * @param id
     * @return
     *
     */
    public List<Attachment> getAttachmentByCompetitorInfoDetialID(int id);

    /**
     *
     * The purpose of this method is remove attachment list by attachmentid.
     *
     * @param attachmentIdList
     * @throws com.panu.competitor.information.exception.CompetitorException
     *
     */
    public void removeAttachmentIdList(List<Integer> attachmentIdList) throws CompetitorException;
    
    public void updateAttachment(Attachment attachment);
}
