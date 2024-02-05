package edu.ie3.quants

/**
 * Defines an interface and partial implementation for types that represent a ratio between any two quantities
 *
 * @author garyKeorkunian
 * @since 0.1
 * @tparam A Quantity A
 * @tparam B Quantity B
 */
trait Ratio[A <: Quantity[A], B <: Quantity[B]] {
  def base: A

  def counter: B

  def convertToBase(q: B): A = base * (q / counter)

  def convertToCounter(q: A): B = counter * (q / base)
}
