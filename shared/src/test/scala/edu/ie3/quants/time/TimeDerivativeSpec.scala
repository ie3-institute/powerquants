/*                                                                      *\
** powerquants was derived from squants:                                **
** Scala Quantities and Units of Measure Library and DSL                **
**                                                                      **
** (c) 2013-2015, Gary Keorkunian                                       **
** (c) 2024, Sebastian Peter (ie3)                                      **
\*                                                                      */

package edu.ie3.quants.time

import edu.ie3.quants.CustomMatchers
import edu.ie3.quants.motion.{MetersPerSecond, UsMilesPerHour}
import edu.ie3.quants.space.{Meters, UsMiles}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import edu.ie3.quants.motion.Velocity
import edu.ie3.quants.space.Length

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class TimeDerivativeSpec extends AnyFlatSpec with Matchers with CustomMatchers {

  behavior of "Time Derivatives and Integrals as implemented in Distance and Velocity"

  it should "satisfy Derivative = Integral / Time" in {
    implicit val tolerance: Velocity = UsMilesPerHour(0.0000000000001)
    UsMilesPerHour(55) should beApproximately(UsMiles(55) / Hours(1))
  }

  it should "satisfy Integral = Derivative * Time" in {
    implicit val tolerance: Length = UsMiles(0.0000000000001)
    UsMiles(110) should beApproximately(UsMilesPerHour(55) * Hours(2))
    UsMiles(110) should beApproximately(Hours(2) * UsMilesPerHour(55))
  }

  it should "satisfy Time = Integral / Derivative" in {
    implicit val tolerance: Time = Hours(0.0000000000001)
    Hours(2) should beApproximately(UsMiles(110) / UsMilesPerHour(55))
  }

  it should "satisfy Derivative = Integral * Frequency" in {
    implicit val tolerance: Velocity = UsMilesPerHour(0.0000000000001)
    UsMilesPerHour(55) should beApproximately(UsMiles(55) * 1 / Hours(1))
  }

  it should "satisfy Frequency = Derivative / Integral (Time value in hours)" in {
    implicit val tolerance: Frequency = Hertz(0.0000000000001)
    Hertz(0.01) should beApproximately(UsMilesPerHour(72) / UsMiles(2))
  }

  it should "satisfy Frequency = Derivative / Integral (Time value in Seconds)" in {
    implicit val tolerance: Frequency = Hertz(0.0000000000001)
    Hertz(55) should beApproximately(MetersPerSecond(110) / Meters(2))
  }
}
