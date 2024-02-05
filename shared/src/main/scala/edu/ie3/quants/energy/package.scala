/*                                                                      *\
** powerquants was derived from squants:                                **
** Scala Quantities and Units of Measure Library and DSL                **
**                                                                      **
** (c) 2013-2015, Gary Keorkunian                                       **
** (c) 2024, Sebastian Peter (ie3)                                      **
\*                                                                      */

package edu.ie3.quants

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
package object energy {

  object KineticEnergy {
    def apply(mass: Mass, velocity: edu.ie3.quants.motion.Velocity): Energy =
      Joules(0.5 * mass.toKilograms * velocity.toMetersPerSecond * velocity.toMetersPerSecond)

    def apply(mass: Mass, momentum: edu.ie3.quants.motion.Momentum): Energy =
      Joules(momentum.toNewtonSeconds / (mass.toKilograms * 2.0))

  }
}
