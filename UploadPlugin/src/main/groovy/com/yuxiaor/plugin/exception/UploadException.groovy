package com.yuxiaor.plugin.exception

import org.gradle.api.GradleException

/**
 * UploadException
 */
class UploadException extends GradleException {
    UploadException() {
    }

    UploadException(String message) {
        super(message)
    }

    UploadException(String message, Throwable cause) {
        super(message, cause)
    }
}