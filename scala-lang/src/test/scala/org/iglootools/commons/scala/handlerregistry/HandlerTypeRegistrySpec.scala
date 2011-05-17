package org.iglootools.commons.scala.handlerregistry

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.Spec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import java.util.Locale


trait H {
  def supportedType: Class[_]
}
class StringHandler extends H {
  def supportedType: Class[_] = classOf[String]
}
class IntHandler extends H {
  def supportedType: Class[_] = classOf[Int]
}

@RunWith(classOf[JUnitRunner])
class HandlerTypeRegistrySpec extends Spec with ShouldMatchers{

  describe("Handler Type Registry") {
    val stringHandler = new StringHandler
    val intHandler = new IntHandler
    val typeRegistry = new HandlerTypeRegistry[H](List(stringHandler, intHandler), { h => h.supportedType} )
    it("should find handler") {
      typeRegistry.handlerFor(classOf[String]) should be === stringHandler
    }

    it("should not find handler") {
      evaluating { typeRegistry.handlerFor(classOf[Double]) } should produce [NoHandlerFoundException[Class[_]]]

    }
  }
}