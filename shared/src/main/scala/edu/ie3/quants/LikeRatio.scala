package edu.ie3.quants

trait LikeRatio[A <: Quantity[A]] extends Ratio[A, A] {
  def ratio = base / counter

  def inverseRatio = counter / base
}
