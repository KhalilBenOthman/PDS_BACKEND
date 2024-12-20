package com.spsrh.absService.exception;

public class InsufficientLeaveBalanceException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InsufficientLeaveBalanceException(String message) {
        super(message);
    }
}
