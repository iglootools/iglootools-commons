package org.iglootools.commons

package object scala {

  /**
   * A kestrel
   * @see http://debasishg.blogspot.com/2009/09/side-effects-with-kestrel-in-scala.html
   */
  def returning[T](t: T)(ops: T => Unit): T = {
    ops(t)
    t
  }
}