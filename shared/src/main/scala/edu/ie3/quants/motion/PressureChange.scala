/*                                                                      *\
** powerquants was derived from squants:                                **
** Scala Quantities and Units of Measure Library and DSL                **
**                                                                      **
** (c) 2013-2015, Gary Keorkunian                                       **
** (c) 2024, Sebastian Peter (ie3)                                      **
\*                                                                      */

package edu.ie3.quants.motion

import edu.ie3.quants.{ PrimaryUnit, SiUnit, UnitConverter, UnitOfMeasure }
import edu.ie3.quants._
import edu.ie3.quants.time.{ Seconds, TimeDerivative }

/**
 * @author  stevebarham
 * @since   0.5.2
 *
 * @param value Double
 */
final class PressureChange private (val value: Double, val unit: PressureChangeUnit)
  extends Quantity[PressureChange]
  with TimeDerivative[Pressure] {

  def dimension = PressureChange

  protected[quants] def timeIntegrated = Pascals(toPascalsPerSecond)
  protected[quants] def time = Seconds(1)

  def toPascalsPerSecond = to(PascalsPerSecond)
  def toBarsPerSecond = to(BarsPerSecond)
  def toPoundsPerSquareInchPerSecond = to(PoundsPerSquareInchPerSecond)
  def toStandardAtmospheresPerSecond = to(StandardAtmospheresPerSecond)
}

object PressureChange extends Dimension[PressureChange] {
  private[motion] def apply[A](n: A, unit: PressureChangeUnit)(implicit num: Numeric[A]) = new PressureChange(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "PressureChange"
  def primaryUnit = PascalsPerSecond
  def siUnit = PascalsPerSecond
  def units = Set(PascalsPerSecond, BarsPerSecond, PoundsPerSquareInchPerSecond, StandardAtmospheresPerSecond)
}

trait PressureChangeUnit extends UnitOfMeasure[PressureChange] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = PressureChange(n, this)
}

object PascalsPerSecond extends PressureChangeUnit with PrimaryUnit with SiUnit {
  val symbol = "Pa/s"
}

object BarsPerSecond extends PressureChangeUnit {
  val symbol = "bar/s"
  val conversionFactor = Bars.conversionFactor / Pascals.conversionFactor
}

object PoundsPerSquareInchPerSecond extends PressureChangeUnit {
  val symbol = "psi/s"
  val conversionFactor = PoundsPerSquareInch.conversionFactor
}

object StandardAtmospheresPerSecond extends PressureChangeUnit {
  val symbol = "atm/s"
  val conversionFactor = StandardAtmospheres.conversionFactor
}

object PressureChangeConversions {
  lazy val pascalsPerSecond = PascalsPerSecond(1)
  lazy val barsPerSecond = BarsPerSecond(1)
  lazy val poundsPerSquareInchPerSecond = PoundsPerSquareInchPerSecond(1)
  lazy val standardAtmospheresPerSecond = StandardAtmospheresPerSecond(1)

  implicit class PressureChangeConversions[A](n: A)(implicit num: Numeric[A]) {
    def pascalsPerSecond = PascalsPerSecond(n)
    def barsPerSecond = BarsPerSecond(n)
    def poundsPerSquareInchPerSecond = PoundsPerSquareInchPerSecond(n)
    def standardAtmospheresPerSecond = StandardAtmospheresPerSecond(n)
  }

  implicit object PressureChangeNumeric extends AbstractQuantityNumeric[PressureChange](PressureChange.primaryUnit)
}
