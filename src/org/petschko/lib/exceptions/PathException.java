package org.petschko.lib.exceptions;

import java.security.PrivilegedActionException;

/**
 * Author: Peter Dragicevic [peter@petschko.org]
 * Authors-Website: https://petschko.org/
 * Date: 04.05.2019
 * Time: 21:03
 * Update: -
 * Version: 0.0.1
 *
 * Notes: PathException (Replacement for Oracle-Path-Exception)
 */
public class PathException extends Exception {
	private String path;

	/**
	 * Constructs a new throwable with {@code null} as its detail message.
	 * The cause is not initialized, and may subsequently be initialized by a
	 * call to {@link #initCause}.
	 *
	 * <p>The {@link #fillInStackTrace()} method is called to initialize
	 * the stack trace data in the newly created throwable.
	 *
	 * @param path - Path of the Exception
	 */
	public PathException(String path) {
		super();
		this.path = path;
	}

	/**
	 * Constructs a new throwable with the specified detail message.  The
	 * cause is not initialized, and may subsequently be initialized by
	 * a call to {@link #initCause}.
	 *
	 * <p>The {@link #fillInStackTrace()} method is called to initialize
	 * the stack trace data in the newly created throwable.
	 *
	 * @param message the detail message. The detail message is saved for
	 * later retrieval by the {@link #getMessage()} method.
	 * @param path - Path of the Exception
	 */
	public PathException(String message, String path) {
		super(message);
		this.path = path;
	}

	/**
	 * Constructs a new throwable with the specified detail message and
	 * cause.  <p>Note that the detail message associated with
	 * {@code cause} is <i>not</i> automatically incorporated in
	 * this throwable's detail message.
	 *
	 * <p>The {@link #fillInStackTrace()} method is called to initialize
	 * the stack trace data in the newly created throwable.
	 *
	 * @param message the detail message (which is saved for later retrieval
	 * by the {@link #getMessage()} method).
	 * @param path - Path of the Exception
	 * @param cause the cause (which is saved for later retrieval by the
	 * {@link #getCause()} method).  (A {@code null} value is
	 * permitted, and indicates that the cause is nonexistent or
	 * unknown.)
	 */
	public PathException(String message, String path, Throwable cause) {
		super(message, cause);
		this.path = path;
	}

	/**
	 * Constructs a new throwable with the specified cause and a detail
	 * message of {@code (cause==null ? null : cause.toString())} (which
	 * typically contains the class and detail message of {@code cause}).
	 * This constructor is useful for throwables that are little more than
	 * wrappers for other throwables (for example, {@link
	 * PrivilegedActionException}).
	 *
	 * <p>The {@link #fillInStackTrace()} method is called to initialize
	 * the stack trace data in the newly created throwable.
	 *
	 * @param path - Path of the Exception
	 * @param cause the cause (which is saved for later retrieval by the
	 * {@link #getCause()} method).  (A {@code null} value is
	 * permitted, and indicates that the cause is nonexistent or
	 * unknown.)
	 */
	public PathException(String path, Throwable cause) {
		super(cause);
		this.path = path;
	}

	/**
	 * Constructs a new throwable with the specified detail message,
	 * cause, {@linkplain #addSuppressed suppression} enabled or
	 * disabled, and writable stack trace enabled or disabled.  If
	 * suppression is disabled, {@link #getSuppressed} for this object
	 * will return a zero-length array and calls to {@link
	 * #addSuppressed} that would otherwise append an exception to the
	 * suppressed list will have no effect.  If the writable stack
	 * trace is false, this constructor will not call {@link
	 * #fillInStackTrace()}, a {@code null} will be written to the
	 * {@code stackTrace} field, and subsequent calls to {@code
	 * fillInStackTrace} and {@link
	 * #setStackTrace(StackTraceElement[])} will not set the stack
	 * trace.  If the writable stack trace is false, {@link
	 * #getStackTrace} will return a zero length array.
	 *
	 * <p>Note that the other constructors of {@code Throwable} treat
	 * suppression as being enabled and the stack trace as being
	 * writable.  Subclasses of {@code Throwable} should document any
	 * conditions under which suppression is disabled and document
	 * conditions under which the stack trace is not writable.
	 * Disabling of suppression should only occur in exceptional
	 * circumstances where special requirements exist, such as a
	 * virtual machine reusing exception objects under low-memory
	 * situations.  Circumstances where a given exception object is
	 * repeatedly caught and rethrown, such as to implement control
	 * flow between two sub-systems, is another situation where
	 * immutable throwable objects would be appropriate.
	 *
	 * @param message the detail message.
	 * @param path - Path of the Exception
	 * @param cause the cause.  (A {@code null} value is permitted,
	 * and indicates that the cause is nonexistent or unknown.)
	 * @param enableSuppression whether or not suppression is enabled or disabled
	 * @param writableStackTrace whether or not the stack trace should be
	 * writable
	 * @see OutOfMemoryError
	 * @see NullPointerException
	 * @see ArithmeticException
	 */
	protected PathException(String message, String path, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.path = path;
	}

	/**
	 * Gets the Path
	 *
	 * @return - Path of the Exception
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Returns the detail message string of this throwable.
	 *
	 * @return the detail message string of this {@code Throwable} instance
	 * (which may be {@code null}).
	 */
	@Override
	public String getMessage() {
		return super.getMessage() + " | Path: " + this.path;
	}
}
