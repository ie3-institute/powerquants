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
import edu.ie3.quants.space.{ Meters, SquaredRadians }

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value Double
 */
final class SpectralIntensity private (val value: Double, val unit: SpectralIntensityUnit)
  extends Quantity[SpectralIntensity] {

  def dimension = SpectralIntensity

  def *(that: Length): RadiantIntensity = WattsPerSteradian(this.toWattsPerSteradianPerMeter * that.toMeters)
  def /(that: RadiantIntensity): Length = Meters(this.toWattsPerSteradianPerMeter / that.toWattsPerSteradian)

  def toWattsPerSteradianPerMeter = to(WattsPerSteradianPerMeter)
}

object SpectralIntensity extends Dimension[SpectralIntensity] {
  private[radio] def apply[A](n: A, unit: SpectralIntensityUnit)(implicit num: Numeric[A]) = new SpectralIntensity(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "SpectralIntensity"
  def primaryUnit = WattsPerSteradianPerMeter
  def siUnit = WattsPerSteradianPerMeter
  def units = Set(WattsPerSteradianPerMeter)
}

trait SpectralIntensityUnit extends UnitOfMeasure[SpectralIntensity] {
  def apply[A](n: A)(implicit num: Numeric[A]) = SpectralIntensity(n, this)
}

object WattsPerSteradianPerMeter extends SpectralIntensityUnit with PrimaryUnit with SiUnit {
  val symbol = Watts.symbol + "/" + SquaredRadians.symbol + "/" + Meters.symbol
}

object SpectralIntensityConversions {
  lazy val wattPerSteradianPerMeter = WattsPerSteradianPerMeter(1)

  implicit class SpectralIntensityConversions[A](n: A)(implicit num: Numeric[A]) {
    def wattsPerSteradianPerMeter = WattsPerSteradianPerMeter(n)
  }

  implicit object SpectralIntensityNumeric extends AbstractQuantityNumeric[SpectralIntensity](SpectralIntensity.primaryUnit)
}

