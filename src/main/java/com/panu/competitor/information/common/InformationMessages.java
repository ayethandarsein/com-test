/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panu.competitor.information.common;

import javax.faces.application.FacesMessage;
import org.primefaces.context.RequestContext;

/**
 *
 * @author HNDK
 */
public class InformationMessages {
   
    public InformationMessages(){
    }
    /**
     * showMessageDialog
     *
     * The purpose of this method is to show information message to user
     *
     * @param messageType
     * @param messageHeader
     * @param messageBody
     */
    public void showMessageDialog(FacesMessage.Severity messageType, String messageHeader, String messageBody) {
        FacesMessage message = new FacesMessage(messageType, messageHeader, messageBody);
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    } 
    /**
     * showCustomMessageDialog
     *
     * The purpose of this method is to show custom information message to user
     *
     * @param dialogWidgetVar
     */
    public void showCustomMessageDialog(String dialogWidgetVar){
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('"+dialogWidgetVar+"').show();");
    }
}
