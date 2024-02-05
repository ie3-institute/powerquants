/*                                                                      *\
** powerquants was derived from squants:                                **
** Scala Quantities and Units of Measure Library and DSL                **
**                                                                      **
** (c) 2013-2015, Gary Keorkunian                                       **
** (c) 2024, Sebastian Peter (ie3)                                      **
\*                                                                      */

package edu.ie3.quants.radio

import edu.ie3.quants.{ PrimaryUnit, SiUnit, UnitOfMeasure }
import edu.ie3.quants._
import edu.ie3.quants.energy.Watts
import edu.ie3.quants.space.{ SquareMeters, SquaredRadians }

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value Double
 */
final class Radiance private (val value: Double, val unit: RadianceUnit)
  extends Quantity[Radiance] {

  def dimension = Radiance

  def *(that: Area): RadiantIntensity = WattsPerSteradian(this.toWattsPerSteradianPerSquareMeter * that.toSquareMeters)
  def /(that: RadiantIntensity): Area = SquareMeters(this.toWattsPerSteradianPerSquareMeter / that.toWattsPerSteradian)

  def toWattsPerSteradianPerSquareMeter = to(WattsPerSteradianPerSquareMeter)
}

object Radiance extends Dimension[Radiance] {
  private[radio] def apply[A](n: A, unit: RadianceUnit)(implicit num: Numeric[A]) = new Radiance(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "Radiance"
  def primaryUnit = WattsPerSteradianPerSquareMeter
  def siUnit = WattsPerSteradianPerSquareMeter
  def units = Set(WattsPerSteradianPerSquareMeter)
}

trait RadianceUnit extends UnitOfMeasure[Radiance] {
  def apply[A](n: A)(implicit num: Numeric[A]) = Radiance(n, this)
}

object WattsPerSteradianPerSquareMeter extends RadianceUnit with PrimaryUnit with SiUnit {
  val symbol = Watts.symbol + "/" + SquaredRadians.symbol + "/" + SquareMeters.symbol
}

object RadianceConversions {
  lazy val wattPerSteradianPerSquareMeter = WattsPerSteradianPerSquareMeter(1)

  implicit class RadianceConversions[A](n: A)(implicit num: Numeric[A]) {
    def wattsPerSteradianPerSquareMeter = WattsPerSteradianPerSquareMeter(n)
  }

  implicit object RadianceNumeric extends AbstractQuantityNumeric[Radiance](Radiance.primaryUnit)
}

