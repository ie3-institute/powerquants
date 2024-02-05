/*                                                                      *\
** powerquants was derived from squants:                                **
** Scala Quantities and Units of Measure Library and DSL                **
**                                                                      **
** (c) 2013-2015, Gary Keorkunian                                       **
** (c) 2024, Sebastian Peter (ie3)                                      **
\*                                                                      */

package edu.ie3.quants

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class DimensionSpec extends AnyFlatSpec with Matchers {

  "Dimensions" should "have a stable hashCode" in {
    space.Length.hashCode().toHexString should be("3298d547")
  }
}
