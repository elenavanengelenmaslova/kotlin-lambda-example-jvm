package nl.vintik.sample.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory

private fun getLogger(forClass: Class<*>): Logger =
    LoggerFactory.getLogger(forClass)

fun <T : Any> T.logger(): Logger = getLogger(javaClass)
