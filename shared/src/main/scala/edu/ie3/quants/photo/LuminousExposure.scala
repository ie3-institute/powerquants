/*                                                                      *\
** powerquants was derived from squants:                                **
** Scala Quantities and Units of Measure Library and DSL                **
**                                                                      **
** (c) 2013-2015, Gary Keorkunian                                       **
** (c) 2024, Sebastian Peter (ie3)                                      **
\*                                                                      */

package edu.ie3.quants.photo

import edu.ie3.quants.{ PrimaryUnit, SiUnit, UnitConverter, UnitOfMeasure }
import edu.ie3.quants._
import edu.ie3.quants.time.TimeIntegral

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value value in [[edu.ie3.quants.photo.LuxSeconds]]
 */
final class LuminousExposure private (val value: Double, val unit: LuminousExposureUnit)
  extends Quantity[LuminousExposure]
  with TimeIntegral[Illuminance] {

  def dimension = LuminousExposure

  protected def timeDerived = Lux(toLuxSeconds)
  protected[quants] def time = Seconds(1)

  def toLuxSeconds = to(LuxSeconds)
}

object LuminousExposure extends Dimension[LuminousExposure] {
  private[photo] def apply[A](n: A, unit: LuminousExposureUnit)(implicit num: Numeric[A]) = new LuminousExposure(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "LuminousExposure"
  def primaryUnit = LuxSeconds
  def siUnit = LuxSeconds
  def units = Set(LuxSeconds)
}

trait LuminousExposureUnit extends UnitOfMeasure[LuminousExposure] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = LuminousExposure(n, this)
}

object LuxSeconds extends LuminousExposureUnit with PrimaryUnit with SiUnit {
  val symbol = "lx⋅s"
}

object LuminousExposureConversions {
  lazy val luxSecond = LuxSeconds(1)

  implicit class LuminousExposureConversions[A](n: A)(implicit num: Numeric[A]) {
    def luxSeconds = LuxSeconds(n)
  }

  implicit object LuminousExposureNumeric extends AbstractQuantityNumeric[LuminousExposure](LuminousExposure.primaryUnit)
}