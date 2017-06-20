package com.brewery.app.presentation.base;

/**
 * This interface provide a handle service error method common for all views
 */
public interface GenericView {
    boolean handleServiceErrors(final Throwable error);
}