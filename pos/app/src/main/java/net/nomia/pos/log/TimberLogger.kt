package net.nomia.pos.log

import net.nomia.utils.logger.Loggable
import timber.log.Timber

class TimberLogger : Loggable {

    override fun d(tag: String, message: String) {
        Timber.tag(tag).d(message)
    }

    override fun i(tag: String, message: String) {
        Timber.tag(tag).i(message)
    }

    override fun t(tag: String, message: String) {
        Timber.tag(tag).d(message)
    }

    override fun w(tag: String, message: String) {
        Timber.tag(tag).w(message)
    }

    override fun e(tag: String, throwable: Throwable) {
        Timber.tag(tag).e(throwable)
    }

    override fun e(tag: String, throwable: Throwable, message: String) {
        Timber.tag(tag).e(throwable, message)
    }
}
