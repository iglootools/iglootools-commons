package org.iglootools.commons.scala.handlerregistry

class NoHandlerFoundException[K](val key: K) extends RuntimeException("No handler found for: %s".format(key)) {
}