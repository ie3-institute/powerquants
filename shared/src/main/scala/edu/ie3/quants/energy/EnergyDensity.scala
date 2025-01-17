/*                                                                      *\
** powerquants was derived from squants:                                **
** Scala Quantities and Units of Measure Library and DSL                **
**                                                                      **
** (c) 2013-2015, Gary Keorkunian                                       **
** (c) 2024, Sebastian Peter (ie3)                                      **
\*                                                                      */

package edu.ie3.quants.energy

import edu.ie3.quants.{ PrimaryUnit, SiUnit, UnitConverter, UnitOfMeasure }
import edu.ie3.quants._
import edu.ie3.quants.space.CubicMeters

/**
 * Represents a quantity of energy
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value value in [[edu.ie3.quants.energy.WattHours]]
 */
final class EnergyDensity private (val value: Double, val unit: EnergyDensityUnit)
  extends Quantity[EnergyDensity] {

  def dimension = EnergyDensity

  def *(that: Volume): Energy = Joules(this.toJoulesPerCubicMeter * that.toCubicMeters)

  def toJoulesPerCubicMeter = to(JoulesPerCubicMeter)
}

object EnergyDensity extends Dimension[EnergyDensity] {
  private[energy] def apply[A](n: A, unit: EnergyDensityUnit)(implicit num: Numeric[A]) = new EnergyDensity(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "EnergyDensity"
  def primaryUnit = JoulesPerCubicMeter
  def siUnit = JoulesPerCubicMeter
  def units = Set(JoulesPerCubicMeter)
}

trait EnergyDensityUnit extends UnitOfMeasure[EnergyDensity] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = EnergyDensity(n, this)
}

object JoulesPerCubicMeter extends EnergyDensityUnit with PrimaryUnit with SiUnit {
  val symbol = Joules.symbol + "/" + CubicMeters.symbol
}

object EnergyDensityConversions {
  lazy val joulePerCubicMeter = JoulesPerCubicMeter(1)

  implicit class EnergyDensityConversions[A](n: A)(implicit num: Numeric[A]) {
    def joulesPerCubicMeter = JoulesPerCubicMeter(n)
  }

  implicit object EnergyDensityNumeric extends AbstractQuantityNumeric[EnergyDensity](EnergyDensity.primaryUnit)
}
