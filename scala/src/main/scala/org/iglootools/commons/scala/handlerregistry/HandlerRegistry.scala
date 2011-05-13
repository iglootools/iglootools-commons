package org.iglootools.commons.scala.handlerregistry

class HandlerRegistry[K,H](val handlers: List[H], val getKey: (H) => K) {
  val byKey: Map[K, H] = Map(handlers map {h => (getKey(h),h)} : _*)

  def handlerFor(key: K): H = {
    byKey.get(key).getOrElse(throw new NoHandlerFoundException(key))
  }

}