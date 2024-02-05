/*                                                                      *\
** powerquants was derived from squants:                                **
** Scala Quantities and Units of Measure Library and DSL                **
**                                                                      **
** (c) 2013-2015, Gary Keorkunian                                       **
** (c) 2024, Sebastian Peter (ie3)                                      **
\*                                                                      */

package edu.ie3.quants

import org.scalacheck.Gen

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
trait QuantityChecks {

  type TestData = Int
  val posNum: Gen[TestData] = Gen.posNum[TestData]
  val tol = 1e-13
  implicit val tolTime: Time = Seconds(tol)
}
