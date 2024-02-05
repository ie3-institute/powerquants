package edu.ie3.quants.electro

import edu.ie3.quants.{ PrimaryUnit, SiUnit, UnitConverter, UnitOfMeasure }
import edu.ie3.quants.space.{ Meters, Volume }
import edu.ie3.quants.{ AbstractQuantityNumeric, Area, Dimension, Length, Quantity }

/**
 *
 * @author Nicolas Vinuesa
 * @since 1.4
 *
 * @param value Double
 */
final class ElectricChargeDensity private (val value: Double, val unit: ElectricChargeDensityUnit)
  extends Quantity[ElectricChargeDensity] {

  def dimension = ElectricChargeDensity

  def *(that: Volume): ElectricCharge = Coulombs(this.toCoulombsCubicMeters * that.toCubicMeters)
  def *(that: Area): LinearElectricChargeDensity = CoulombsPerMeter(this.toCoulombsCubicMeters * that.toSquareMeters)
  def *(that: Length): AreaElectricChargeDensity = CoulombsPerSquareMeter(this.toCoulombsCubicMeters * that.toMeters)

  def toCoulombsCubicMeters = to(CoulombsPerCubicMeter)
}

object ElectricChargeDensity extends Dimension[ElectricChargeDensity] {
  private[electro] def apply[A](n: A, unit: ElectricChargeDensityUnit)(implicit num: Numeric[A]) = new ElectricChargeDensity(num.toDouble(n), unit)
  def apply(value: Any) = parse(value)
  def name = "ElectricChargeDensity"
  def primaryUnit = CoulombsPerCubicMeter
  def siUnit = CoulombsPerCubicMeter
  def units = Set(CoulombsPerCubicMeter)
}

trait ElectricChargeDensityUnit extends UnitOfMeasure[ElectricChargeDensity] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = ElectricChargeDensity(n, this)
}

object CoulombsPerCubicMeter extends ElectricChargeDensityUnit with PrimaryUnit with SiUnit {
  val symbol = Coulombs.symbol + "/" + Meters.symbol + "³"
}

object ElectricChargeDensityConversions {
  lazy val coulombPerCubicMeter = CoulombsPerCubicMeter(1)

  implicit class ElectricChargeDensityConversions[A](n: A)(implicit num: Numeric[A]) {
    def coulombsPerCubicMeter = CoulombsPerCubicMeter(n)
  }

  implicit object ElectricChargeDensityNumeric extends AbstractQuantityNumeric[ElectricChargeDensity](ElectricChargeDensity.primaryUnit)
}