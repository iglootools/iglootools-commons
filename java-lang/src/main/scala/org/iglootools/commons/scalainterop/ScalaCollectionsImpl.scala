package org.iglootools.commons.scalainterop;
import scalaj.collection.Imports._

object ScalaCollectionsImpl {
  def toScalaSet[T](javaSet: java.util.Set[T]) = {
    Set() ++ javaSet.asScala
  }
}