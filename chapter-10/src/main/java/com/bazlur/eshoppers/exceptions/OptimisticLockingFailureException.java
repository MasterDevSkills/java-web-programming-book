package com.bazlur.eshoppers.exceptions;

public class OptimisticLockingFailureException extends RuntimeException {
	public OptimisticLockingFailureException(String msg) {
		super(msg);
	}
}
