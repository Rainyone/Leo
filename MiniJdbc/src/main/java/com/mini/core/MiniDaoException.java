package com.mini.core;
/**
 * @author sxjun
 * 2016-2-4
 */
public class MiniDaoException extends RuntimeException {
	
	private static final long serialVersionUID = 342820722361408621L;
	
	public MiniDaoException(String message) {
		super(message);
	}
	
	public MiniDaoException(Throwable cause) {
		super(cause);
	}
	
	public MiniDaoException(String message, Throwable cause) {
		super(message, cause);
	}
}
