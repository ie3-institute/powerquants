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
import edu.ie3.quants.space.Meters

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value Double
 */
final class SpectralPower private (val value: Double, val unit: SpectralPowerUnit)
  extends Quantity[SpectralPower] {

  def dimension = SpectralPower

  def *(that: Length): Power = Watts(this.toWattsPerMeter * that.toMeters)
  def /(that: Power): Length = Meters(this.toWattsPerMeter / that.toWatts)

  def toWattsPerMeter = value
}

object SpectralPower extends Dimension[SpectralPower] {
  private[radio] def apply[A](n: A, unit: SpectralPowerUnit)(implicit num: Numeric[A]) = new SpectralPower(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "SpectralPower"
  def primaryUnit = WattsPerMeter
  def siUnit = WattsPerMeter
  def units = Set(WattsPerMeter)
}

trait SpectralPowerUnit extends UnitOfMeasure[SpectralPower] {
  def apply[A](n: A)(implicit num: Numeric[A]) = SpectralPower(n, this)
}

object WattsPerMeter extends SpectralPowerUnit with PrimaryUnit with SiUnit {
  val symbol = Watts.symbol + "/" + Meters.symbol
}

object SpectralPowerConversions {
  lazy val wattPerMeter = WattsPerMeter(1)

  implicit class SpectralPowerConversions[A](n: A)(implicit num: Numeric[A]) {
    def wattsPerMeter = WattsPerMeter(n)
  }

  implicit object SpectralPowerNumeric extends AbstractQuantityNumeric[SpectralPower](SpectralPower.primaryUnit)
}

