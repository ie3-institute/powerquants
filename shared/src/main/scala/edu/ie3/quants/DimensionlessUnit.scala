package edu.ie3.quants

/**
 * Base trait for units of [[squants.Dimensionless]]
 *
 * The DimensionlessUnit is a useful paradox
 */
trait DimensionlessUnit extends UnitOfMeasure[Dimensionless] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = Dimensionless(n, this)
}
