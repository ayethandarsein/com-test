/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panu.competitor.information.exception;

/**
 *
 * @author HNDK
 */
public class CompetitorException extends Exception{
    
  
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String errorCode = null;

    /**
     * constructor 
     */
    public CompetitorException(){
        
    }
    /**
     * 
     * @param errorCode
     */
    public CompetitorException(String errorCode){
        this.errorCode = errorCode;
    }
    /**
     * 
     * @param errorCode
     * @param exp
     */
    public CompetitorException(String errorCode, Exception exp) {
        super(exp);
        this.errorCode = errorCode;
    }
    /**
     * 
     * @param exp
     */
    public CompetitorException(Exception exp) {
        super(exp);
    }
    /**
     * 
     * @return
     */
    public String getErrorCode() {
        return errorCode;
    }
    /**
     * 
     * @param errorCode
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
    
    
}
