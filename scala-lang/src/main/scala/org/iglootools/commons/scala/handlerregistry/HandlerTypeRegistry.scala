package org.iglootools.commons.scala.handlerregistry

class HandlerTypeRegistry[H](handlers: List[H], getType: (H) => Class[_]) extends HandlerRegistry[Class[_],H](handlers, getType) {

  def handlerFor[K](implicit mf: Manifest[K]): H = {
    handlerFor(mf.erasure)
  }
}