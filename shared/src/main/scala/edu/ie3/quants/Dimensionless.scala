package edu.ie3.quants

/**
 * Represents a quantity of some thing for which there is no dimension.
 *
 * This may be used to represent counts or other discrete amounts of everyday life,
 * but may also represent ratios between like quantities where the units have cancelled out.
 *
 * @author garyKeorkunian
 * @since 0.1
 * @param value Double the amount
 */
final class Dimensionless private (val value: Double, val unit: DimensionlessUnit)
  extends Quantity[Dimensionless]
  with TimeIntegral[Frequency] {

  def dimension = Dimensionless

  protected def timeDerived = Hertz(toEach)

  protected[squants] def time = Seconds(1)

  def *(that: Dimensionless) = Each(toEach * that.toEach)

  def *(that: Quantity[_]) = that * toEach

  def +(that: Double): Dimensionless = this + Each(that)

  def toPercent = to(Percent)

  def toEach = to(Each)

  def toDozen = to(Dozen)

  def toScore = to(Score)

  def toGross = to(Gross)
}

/**
 * Factory singleton for [[squants.Dimensionless]]
 */
object Dimensionless extends Dimension[Dimensionless] {
  def apply[A](n: A, unit: DimensionlessUnit)(implicit num: Numeric[A]) = new Dimensionless(num.toDouble(n), unit)

  def apply(value: Any) = parse(value)

  def name = "Dimensionless"

  def primaryUnit = Each

  def siUnit = Each

  def units = Set(Each, Percent, Dozen, Score, Gross)
}