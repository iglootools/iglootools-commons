package org.iglootools.commons.scala.handlerregistry

object EventHandler {
  def handler[A, B](callback: A => B) = new EventHandler(callback)

  implicit def handlerToPartialFunction[A, B](handler: EventHandler[A, B])(implicit m: Manifest[A]) =
    new PartialFunction[AnyRef, B] {
      def isDefinedAt(event: AnyRef) = m.erasure.isInstance(event)
      def apply(event: AnyRef) = handler.handleEvent(event.asInstanceOf[A])
    }
}

class EventHandler[Event, +Result](callback: Event => Result) {
  def handleEvent(event: Event) = callback(event)
}