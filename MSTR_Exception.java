package com.closeup.mstr.exceptions;

public class MSTR_Exception extends Exception{

	private static final long serialVersionUID = 7132661921935591279L;
	
    public MSTR_Exception(String message, Throwable cause){
        super(message, cause);
        System.out.println(message);
    }

}
