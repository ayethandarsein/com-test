/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panu.competitor.information.dao;
import com.panu.competitor.information.pojo.entity.Attachment;
import javax.inject.Named;
/**
 *
 * @author ThetPyaeSoneHtoo
 */
@Named
public class AttachmentDAO extends BaseDAO<Attachment>{

    public AttachmentDAO() {
        super(Attachment.class);
    }

    
}
