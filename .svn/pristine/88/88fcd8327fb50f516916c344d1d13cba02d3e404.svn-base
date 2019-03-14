package com.panu.competitor.information.common;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;


public class MessagesView {

    public MessagesView() {
    }
    
    /**
     * The purpose of this method is to show information message.
     *
     * @param msg
     */
    public static void info(String msg) {
        FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null));
    }

    /**
     * The purpose of this method is to show Warning message.
     *
     * @param msg
     */
    public static void warn(String msg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msg, null));
    }

    /**
     * The purpose of this method is to show Error message.
     *
     * @param msg
     */
    public static void error(String msg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
    }

    /**
     * The purpose of this method is to show Fatal message.
     *
     * @param msg
     */
    public static void fatal(String msg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, msg, null));
    }

    /**
     * The purpose of this method is count time for remove message
     *
     * @param countTime
     * @throws InterruptedException
     */
    public static void RemoveMessage(int countTime) throws InterruptedException {
        FacesContext.getCurrentInstance().wait(countTime);
    }

    /**
     * showCustomMessageDialog
     *
     * The purpose of this method is to show custom information message to user
     *
     * @param dialogWidgetVar
     */
    public void showCustomMessageDialog(String dialogWidgetVar) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('" + dialogWidgetVar + "').show();");
    }
}
