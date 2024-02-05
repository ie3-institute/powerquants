/*                                                                      *\
** powerquants was derived from squants:                                **
** Scala Quantities and Units of Measure Library and DSL                **
**                                                                      **
** (c) 2013-2015, Gary Keorkunian                                       **
** (c) 2024, Sebastian Peter (ie3)                                      **
\*                                                                      */

package edu.ie3.quants.radio

import edu.ie3.quants.{ PrimaryUnit, SiUnit, UnitConverter, UnitOfMeasure }
import edu.ie3.quants._
import edu.ie3.quants.energy.{ ErgsPerSecond, WattHours, Watts }
import edu.ie3.quants.space.{ SquareCentimeters, SquareMeters }
import edu.ie3.quants.time.Hours

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value Double
 */
final class Irradiance private (val value: Double, val unit: IrradianceUnit)
  extends Quantity[Irradiance] {

  def dimension = Irradiance

  def *(that: Area): Power = Watts(this.toWattsPerSquareMeter * that.toSquareMeters)
  // the Hours(1).toSeconds is to convert watt hours to watt seconds which
  // isn't a normal supported type in Squants
  def *(that: AreaTime): Energy = WattHours(
    this.toWattsPerSquareMeter * that.toSquareMeterSeconds / Hours(1).toSeconds)
  def /(that: Energy): ParticleFlux = BecquerelsPerSquareMeterSecond(
    toWattsPerSquareMeter / (that.toWattHours * Hours(1).toSeconds))
  def /(that: ParticleFlux): Energy = WattHours(
    (toWattsPerSquareMeter / that.toBecquerelsPerSquareMeterSecond) /
      Hours(1).toSeconds)

  def toWattsPerSquareMeter = to(WattsPerSquareMeter)
  def toErgsPerSecondPerSquareCentimeter = to(ErgsPerSecondPerSquareCentimeter)
}

object Irradiance extends Dimension[Irradiance] {
  private[radio] def apply[A](n: A, unit: IrradianceUnit)(implicit num: Numeric[A]) = new Irradiance(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "Irradiance"
  def primaryUnit = WattsPerSquareMeter
  def siUnit = WattsPerSquareMeter
  def units = Set(WattsPerSquareMeter, ErgsPerSecondPerSquareCentimeter)
}

trait IrradianceUnit extends UnitOfMeasure[Irradiance] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = Irradiance(n, this)
}

object WattsPerSquareMeter extends IrradianceUnit with PrimaryUnit with SiUnit {
  val symbol = Watts.symbol + "/" + SquareMeters.symbol
}

object ErgsPerSecondPerSquareCentimeter extends IrradianceUnit {
  val conversionFactor = ErgsPerSecond.conversionFactor / SquareCentimeters.conversionFactor
  val symbol = ErgsPerSecond.symbol + "/" + SquareCentimeters.symbol
}

object IrradianceConversions {
  lazy val wattPerSquareMeter = WattsPerSquareMeter(1)
  lazy val ergsPerSecondPerSquareCentimeter = ErgsPerSecondPerSquareCentimeter(1)

  implicit class IrradianceConversions[A](n: A)(implicit num: Numeric[A]) {
    def wattsPerSquareMeter = WattsPerSquareMeter(n)
    def ergsPerSecondPerSquareCentimeter = ErgsPerSecondPerSquareCentimeter(n)
  }

  implicit object IrradianceNumeric extends AbstractQuantityNumeric[Irradiance](Irradiance.primaryUnit)
}
