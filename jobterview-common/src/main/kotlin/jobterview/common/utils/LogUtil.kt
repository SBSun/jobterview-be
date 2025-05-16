package jobterview.common.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory

object LogUtil {

    private val log: Logger = LoggerFactory.getLogger("GlobalLogger")

    fun trace(message: String, throwable: Throwable? = null) {
        if (throwable != null) {
            log.trace(message, throwable)
        } else {
            log.trace(message)
        }
    }

    fun debug(message: String, throwable: Throwable? = null) {
        if (throwable != null) {
            log.debug(message, throwable)
        } else {
            log.debug(message)
        }
    }

    fun info(message: String, throwable: Throwable? = null) {
        if (throwable != null) {
            log.info(message, throwable)
        } else {
            log.info(message)
        }
    }

    fun warn(message: String, throwable: Throwable? = null) {
        if (throwable != null) {
            log.warn(message, throwable)
        } else {
            log.warn(message)
        }
    }

    fun error(message: String, throwable: Throwable? = null) {
        if (throwable != null) {
            log.error(message, throwable)
        } else {
            log.error(message)
        }
    }
}