package edu.ie3.quants.electro

import edu.ie3.quants.{ PrimaryUnit, SiUnit, UnitConverter, UnitOfMeasure }
import edu.ie3.quants.space.Meters
import edu.ie3.quants.{ AbstractQuantityNumeric, Dimension, Length, Quantity }

/**
 *
 * @author Nicolas Vinuesa
 * @since 1.4
 *
 * @param value Double
 */
final class MagneticFieldStrength private (val value: Double, val unit: MagneticFieldStrengthUnit)
  extends Quantity[MagneticFieldStrength] {

  def dimension = MagneticFieldStrength

  def *(that: Length): ElectricCurrent = Amperes(this.toAmperesPerMeter * that.toMeters)

  def toAmperesPerMeter = to(AmperesPerMeter)
}

object MagneticFieldStrength extends Dimension[MagneticFieldStrength] {
  private[electro] def apply[A](n: A, unit: MagneticFieldStrengthUnit)(implicit num: Numeric[A]) = new MagneticFieldStrength(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "MagneticFieldStrength"
  def primaryUnit = AmperesPerMeter
  def siUnit = AmperesPerMeter
  def units = Set(AmperesPerMeter)
}

trait MagneticFieldStrengthUnit extends UnitOfMeasure[MagneticFieldStrength] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = MagneticFieldStrength(n, this)
}

object AmperesPerMeter extends MagneticFieldStrengthUnit with PrimaryUnit with SiUnit {
  val symbol = Amperes.symbol + "/" + Meters.symbol
}

object MagneticFieldStrengthConversions {
  lazy val amperePerMeter = AmperesPerMeter(1)

  implicit class MagneticFieldStrengthConversions[A](n: A)(implicit num: Numeric[A]) {
    def amperesPerMeter = AmperesPerMeter(n)
  }

  implicit object MagneticFieldStrengthNumeric extends AbstractQuantityNumeric[MagneticFieldStrength](MagneticFieldStrength.primaryUnit)
}
