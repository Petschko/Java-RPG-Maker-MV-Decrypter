package org.petschko.lib.update;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.security.PrivilegedActionException;

/**
 * Author: Peter Dragicevic [peter@petschko.org]
 * Authors-Website: https://petschko.org/
 * Date: 05.05.2019
 * Time: 17:02
 * Update: -
 * Version: 0.0.1
 *
 * Notes: Contains the UpdateException Class
 */
public class UpdateException extends Exception {
	private Version currentVersion;
	private Version newestVersion = null;

	/**
	 * Constructs a new exception with {@code null} as its detail message.
	 * The cause is not initialized, and may subsequently be initialized by a
	 * call to {@link #initCause}.
	 *
	 * @param currentVersion - Current Version
	 */
	public UpdateException(@NotNull Version currentVersion) {
		super();
		this.setCurrentVersion(currentVersion);
	}

	/**
	 * Constructs a new exception with {@code null} as its detail message.
	 * The cause is not initialized, and may subsequently be initialized by a
	 * call to {@link #initCause}.
	 *
	 * @param currentVersion - Current Version
	 * @param newestVersion - Newest Version or null for none
	 */
	public UpdateException(@NotNull Version currentVersion, @Nullable Version newestVersion) {
		super();
		this.setCurrentVersion(currentVersion);
		this.setNewestVersion(newestVersion);
	}

	/**
	 * Constructs a new exception with the specified detail message.  The
	 * cause is not initialized, and may subsequently be initialized by
	 * a call to {@link #initCause}.
	 *
	 * @param message the detail message. The detail message is saved for
	 * later retrieval by the {@link #getMessage()} method.
	 * @param currentVersion - Current Version
	 */
	public UpdateException(String message, @NotNull Version currentVersion) {
		super(message);
		this.setCurrentVersion(currentVersion);
	}

	/**
	 * Constructs a new exception with the specified detail message.  The
	 * cause is not initialized, and may subsequently be initialized by
	 * a call to {@link #initCause}.
	 *
	 * @param message the detail message. The detail message is saved for
	 * later retrieval by the {@link #getMessage()} method.
	 * @param currentVersion - Current Version
	 * @param newestVersion - Newest Version or null for none
	 */
	public UpdateException(String message, @NotNull Version currentVersion, @Nullable Version newestVersion) {
		super(message);
		this.setCurrentVersion(currentVersion);
		this.setNewestVersion(newestVersion);
	}

	/**
	 * Constructs a new exception with the specified detail message and
	 * cause.  <p>Note that the detail message associated with
	 * {@code cause} is <i>not</i> automatically incorporated in
	 * this exception's detail message.
	 *
	 * @param message the detail message (which is saved for later retrieval
	 * by the {@link #getMessage()} method).
	 * @param currentVersion - Current Version
	 * @param cause the cause (which is saved for later retrieval by the
	 * {@link #getCause()} method).  (A <tt>null</tt> value is
	 * permitted, and indicates that the cause is nonexistent or
	 * unknown.)
	 */
	public UpdateException(String message, @NotNull Version currentVersion, Throwable cause) {
		super(message, cause);
		this.setCurrentVersion(currentVersion);
	}

	/**
	 * Constructs a new exception with the specified detail message and
	 * cause.  <p>Note that the detail message associated with
	 * {@code cause} is <i>not</i> automatically incorporated in
	 * this exception's detail message.
	 *
	 * @param message the detail message (which is saved for later retrieval
	 * by the {@link #getMessage()} method).
	 * @param currentVersion - Current Version
	 * @param newestVersion - Newest Version or null for none
	 * @param cause the cause (which is saved for later retrieval by the
	 * {@link #getCause()} method).  (A <tt>null</tt> value is
	 * permitted, and indicates that the cause is nonexistent or
	 * unknown.)
	 */
	public UpdateException(String message, @NotNull Version currentVersion, @Nullable Version newestVersion, Throwable cause) {
		super(message, cause);
		this.setCurrentVersion(currentVersion);
		this.setNewestVersion(newestVersion);
	}

	/**
	 * Constructs a new exception with the specified cause and a detail
	 * message of <tt>(cause==null ? null : cause.toString())</tt> (which
	 * typically contains the class and detail message of <tt>cause</tt>).
	 * This constructor is useful for exceptions that are little more than
	 * wrappers for other throwables (for example, {@link
	 * PrivilegedActionException}).
	 *
	 * @param currentVersion - Current Version
	 * @param cause the cause (which is saved for later retrieval by the
	 * {@link #getCause()} method).  (A <tt>null</tt> value is
	 * permitted, and indicates that the cause is nonexistent or
	 * unknown.)
	 */
	public UpdateException(@NotNull Version currentVersion, Throwable cause) {
		super(cause);
		this.setCurrentVersion(currentVersion);
	}

	/**
	 * Constructs a new exception with the specified cause and a detail
	 * message of <tt>(cause==null ? null : cause.toString())</tt> (which
	 * typically contains the class and detail message of <tt>cause</tt>).
	 * This constructor is useful for exceptions that are little more than
	 * wrappers for other throwables (for example, {@link
	 * PrivilegedActionException}).
	 *
	 * @param currentVersion - Current Version
	 * @param newestVersion - Newest Version or null for none
	 * @param cause the cause (which is saved for later retrieval by the
	 * {@link #getCause()} method).  (A <tt>null</tt> value is
	 * permitted, and indicates that the cause is nonexistent or
	 * unknown.)
	 */
	public UpdateException(@NotNull Version currentVersion, @Nullable Version newestVersion, Throwable cause) {
		super(cause);
		this.setCurrentVersion(currentVersion);
		this.setNewestVersion(newestVersion);
	}

	/**
	 * Constructs a new exception with the specified detail message,
	 * cause, suppression enabled or disabled, and writable stack
	 * trace enabled or disabled.
	 *
	 * @param message the detail message.
	 * @param currentVersion - Current Version
	 * @param cause the cause.  (A {@code null} value is permitted,
	 * and indicates that the cause is nonexistent or unknown.)
	 * @param enableSuppression whether or not suppression is enabled
	 * or disabled
	 * @param writableStackTrace whether or not the stack trace should
	 * be writable
	 */
	protected UpdateException(String message, @NotNull Version currentVersion, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.setCurrentVersion(currentVersion);
	}

	/**
	 * Constructs a new exception with the specified detail message,
	 * cause, suppression enabled or disabled, and writable stack
	 * trace enabled or disabled.
	 *
	 * @param message the detail message.
	 * @param currentVersion - Current Version
	 * @param newestVersion - Newest Version or null for none
	 * @param cause the cause.  (A {@code null} value is permitted,
	 * and indicates that the cause is nonexistent or unknown.)
	 * @param enableSuppression whether or not suppression is enabled
	 * or disabled
	 * @param writableStackTrace whether or not the stack trace should
	 * be writable
	 */
	protected UpdateException(String message, @NotNull Version currentVersion, @Nullable Version newestVersion, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.setCurrentVersion(currentVersion);
		this.setNewestVersion(newestVersion);
	}

	/**
	 * Get the current Version
	 *
	 * @return - Current Version
	 */
	public @NotNull Version getCurrentVersion() {
		return currentVersion;
	}

	/**
	 * Get the current Version as String
	 *
	 * @return - Current Version as String
	 */
	public String getCurrentVersionAsString() {
		return currentVersion.getVersion();
	}

	/**
	 * Set the current Version
	 *
	 * @param currentVersion - Current Version
	 */
	private void setCurrentVersion(@NotNull Version currentVersion) {
		this.currentVersion = currentVersion;
	}

	/**
	 * Get the newest Version
	 *
	 * @return - Newest Version or null
	 */
	public @Nullable Version getNewestVersion() {
		return newestVersion;
	}

	/**
	 * Get the newest Version as String
	 *
	 * @return - Newest Version as String
	 */
	public String getNewestVersionAsString() {
		if(this.newestVersion == null)
			return "";

		return newestVersion.getVersion();
	}

	/**
	 * Set the newest Version
	 *
	 * @param newestVersion - Newest Version or null
	 */
	private void setNewestVersion(@Nullable Version newestVersion) {
		this.newestVersion = newestVersion;
	}
}
