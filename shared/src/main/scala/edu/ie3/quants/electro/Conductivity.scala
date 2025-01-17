/*                                                                      *\
** powerquants was derived from squants:                                **
** Scala Quantities and Units of Measure Library and DSL                **
**                                                                      **
** (c) 2013-2015, Gary Keorkunian                                       **
** (c) 2024, Sebastian Peter (ie3)                                      **
\*                                                                      */

package edu.ie3.quants.electro

import edu.ie3.quants.{ PrimaryUnit, SiUnit, UnitConverter, UnitOfMeasure }
import edu.ie3.quants._
import edu.ie3.quants.space.Length

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value value in [[edu.ie3.quants.electro.SiemensPerMeter]]
 */
final class Conductivity private (val value: Double, val unit: ConductivityUnit)
  extends Quantity[Conductivity] {

  def dimension = Conductivity

  def *(that: Length): ElectricalConductance = Siemens(this.toSiemensPerMeter * that.toMeters)

  def toSiemensPerMeter = to(SiemensPerMeter)
  def inOhmMeters = OhmMeters(1d / toSiemensPerMeter)
}

object Conductivity extends Dimension[Conductivity] {
  private[electro] def apply[A](n: A, unit: ConductivityUnit)(implicit num: Numeric[A]) = new Conductivity(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "Conductivity"
  def primaryUnit = SiemensPerMeter
  def siUnit = SiemensPerMeter
  def units = Set(SiemensPerMeter)
}

trait ConductivityUnit extends UnitOfMeasure[Conductivity] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = Conductivity(n, this)
}

object SiemensPerMeter extends ConductivityUnit with PrimaryUnit with SiUnit {
  val symbol = "S/m"
}

object ConductivityConversions {
  lazy val siemenPerMeter = SiemensPerMeter(1)

  implicit class ConductivityConversions[A](n: A)(implicit num: Numeric[A]) {
    def siemensPerMeter = SiemensPerMeter(n)
  }

  implicit object ConductivityNumeric extends AbstractQuantityNumeric[Conductivity](Conductivity.primaryUnit)
}
