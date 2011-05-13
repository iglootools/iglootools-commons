package org.iglootools.commons.scala.handlerregistry

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.Spec
import org.scalatest.matchers.ShouldMatchers

trait Handler {
  def key: String
}

class AHandler extends Handler {
  def key = "A"
}
class BHandler extends Handler {
  def key = "B"
}

@RunWith(classOf[JUnitRunner])
class HandlerRegistrySpec extends Spec with ShouldMatchers{

  describe("Handler Registry") {
    val aHandler = new AHandler
    val bHandler = new BHandler

    val registry = new HandlerRegistry[String,Handler](List(aHandler, bHandler), { h => h.key} )
    it("should find handler") {
      registry.handlerFor("A") should be === aHandler
    }

    it("should not find handler") {
      evaluating { registry.handlerFor("C") } should produce [NoHandlerFoundException[String]]

    }
  }
}