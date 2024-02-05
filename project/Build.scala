import org.portablescala.sbtplatformdeps.PlatformDepsPlugin.autoImport._
import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}
import scalanativecrossproject.ScalaNativeCrossPlugin.autoImport._
import sbt.Keys._
import sbt._
import com.typesafe.sbt.osgi.SbtOsgi
import com.typesafe.sbt.osgi.SbtOsgi.autoImport._

object Versions {
  val Scala3 = "3.1.1"
  val Scala = Scala3
  val ScalaCross =
    Seq("2.12.15", "2.13.6", Scala)

  val ScalaTest = "3.2.14"
  val ScalaCheck = "1.16.0"
}

object Dependencies {
  val scalaTest = Def.setting(Seq("org.scalatest" %%% "scalatest" % Versions.ScalaTest % Test))
  val scalaCheck = Def.setting(Seq("org.scalacheck" %%% "scalacheck" % Versions.ScalaCheck % Test))
}

object Resolvers {
  val sonatypeNexusSnapshots = "Sonatype Nexus Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
  val sonatypeNexusReleases = "Sonatype Nexus Releases" at "https://oss.sonatype.org/content/repositories/releases"
  val sonatypeNexusStaging = "Sonatype Nexus Staging" at "https://oss.sonatype.org/service/local/staging/deploy/maven2"
}

object Project {
  val defaultSettings = Seq(
    name := "powerquants",

    autoAPIMappings := true,

    resolvers ++= Seq(
        Resolvers.sonatypeNexusSnapshots,
        Resolvers.sonatypeNexusReleases,
        Resolvers.sonatypeNexusStaging
    ),

    OsgiKeys.exportPackage := Seq("edu.ie3.quants.*"),

    OsgiKeys.privatePackage := Seq() // No private packages
  )
}

object Compiler {
  lazy val newerCompilerLintSwitches = Seq(
    "-Xlint:missing-interpolator",
    "-Ywarn-unused",
    "-Ywarn-numeric-widen",
    "-deprecation:false"
  )

  lazy val defaultCompilerSwitches = Seq(
    "-feature",
    "-deprecation",
    "-encoding", "UTF-8",       // yes, this is 2 args
    "-Xfatal-warnings",
    "-unchecked"
  )

  lazy val defaultSettings = Seq(
    scalacOptions := {CrossVersion.partialVersion(scalaVersion.value) match {
      case Some((2, scalaMajor)) if scalaMajor >= 13 => scalacOptions.value ++ defaultCompilerSwitches ++ newerCompilerLintSwitches
      case Some((2, scalaMajor)) if scalaMajor >= 11 => scalacOptions.value ++ defaultCompilerSwitches ++ newerCompilerLintSwitches :+ "-Ywarn-unused-import"
      case _ => scalacOptions.value ++ defaultCompilerSwitches
    }},

    ThisBuild / scalaVersion := Versions.Scala,

    crossScalaVersions := Versions.ScalaCross
  )

}

object Publish {
  val defaultSettings = Seq(
    Test / publishArtifact := false
  )
}

object Tests {
  val defaultSettings =
    Seq(
      libraryDependencies ++=
        Dependencies.scalaTest.value ++
        Dependencies.scalaCheck.value
    )
}

object Formatting {
  import com.typesafe.sbt.SbtScalariform._
  import com.typesafe.sbt.SbtScalariform.autoImport.scalariformAutoformat

  lazy val defaultSettings = Seq(
    ScalariformKeys.autoformat := false,
    Compile / ScalariformKeys.preferences := defaultPreferences,
    Test / ScalariformKeys.preferences := defaultPreferences
  )

  val defaultPreferences = {
    import scalariform.formatter.preferences._
    FormattingPreferences()
      .setPreference(AlignParameters, false)
      .setPreference(AlignSingleLineCaseStatements, true)
      .setPreference(CompactControlReadability, true)
      .setPreference(CompactStringConcatenation, false)
      .setPreference(DoubleIndentConstructorArguments, true)
      .setPreference(FormatXml, true)
      .setPreference(IndentLocalDefs, false)
      .setPreference(IndentPackageBlocks, true)
      .setPreference(IndentSpaces, 2)
      .setPreference(MultilineScaladocCommentsStartOnFirstLine, false)
      .setPreference(PreserveSpaceBeforeArguments, false)
      .setPreference(RewriteArrowSymbols, true)
      .setPreference(SpaceBeforeColon, false)
      .setPreference(SpaceInsideBrackets, false)
      .setPreference(SpacesWithinPatternBinders, true)
  }
}

object Console {
  val defaultSettings = Seq(
  Compile / console / scalacOptions ~= (_ filterNot (Set("-Xfatal-warnings", "-Ywarn-unused-import").contains)),

  console / initialCommands := """
     import scala.language.postfixOps,
         edu.ie3.quants._,
         edu.ie3.quants.energy._,
         edu.ie3.quants.electro._,
         edu.ie3.quants.information._,
         edu.ie3.quants.market._,
         edu.ie3.quants.mass._,
         edu.ie3.quants.motion._,
         edu.ie3.quants.photo._,
         edu.ie3.quants.radio._,
         edu.ie3.quants.space._,
         edu.ie3.quants.thermal._,
         edu.ie3.quants.time._,
         edu.ie3.quants.experimental.formatter._,
         edu.ie3.quants.experimental.unitgroups.UnitGroup,
         edu.ie3.quants.DimensionlessConversions._,
         edu.ie3.quants.electro.CapacitanceConversions._,
         edu.ie3.quants.electro.ConductivityConversions._,
         edu.ie3.quants.electro.ElectricalConductanceConversions._,
         edu.ie3.quants.electro.ElectricalResistanceConversions._,
         edu.ie3.quants.electro.ElectricChargeConversions._,
         edu.ie3.quants.electro.ElectricCurrentConversions._,
         edu.ie3.quants.electro.ElectricPotentialConversions._,
         edu.ie3.quants.electro.InductanceConversions._,
         edu.ie3.quants.electro.MagneticFluxConversions._,
         edu.ie3.quants.electro.MagneticFluxDensityConversions._,
         edu.ie3.quants.electro.ResistivityConversions._,
         edu.ie3.quants.energy.EnergyConversions._,
         edu.ie3.quants.energy.EnergyDensityConversions._,
         edu.ie3.quants.energy.PowerConversions._,
         edu.ie3.quants.energy.PowerRampConversions._,
         edu.ie3.quants.energy.SpecificEnergyConversions._,
         edu.ie3.quants.information.InformationConversions._,
         edu.ie3.quants.market.MoneyConversions._,
         edu.ie3.quants.mass.AreaDensityConversions._,
         edu.ie3.quants.mass.ChemicalAmountConversions._,
         edu.ie3.quants.mass.DensityConversions._,
         edu.ie3.quants.mass.MassConversions._,
         edu.ie3.quants.motion.AccelerationConversions._,
         edu.ie3.quants.motion.AngularVelocityConversions._,
         edu.ie3.quants.motion.ForceConversions._,
         edu.ie3.quants.motion.JerkConversions._,
         edu.ie3.quants.motion.MassFlowConversions._,
         edu.ie3.quants.motion.MomentumConversions._,
         edu.ie3.quants.motion.PressureConversions._,
         edu.ie3.quants.motion.VelocityConversions._,
         edu.ie3.quants.motion.VolumeFlowConversions._,
         edu.ie3.quants.motion.YankConversions._,
         edu.ie3.quants.photo.IlluminanceConversions._,
         edu.ie3.quants.photo.LuminanceConversions._,
         edu.ie3.quants.photo.LuminousEnergyConversions._,
         edu.ie3.quants.photo.LuminousExposureConversions._,
         edu.ie3.quants.photo.LuminousFluxConversions._,
         edu.ie3.quants.photo.LuminousIntensityConversions._,
         edu.ie3.quants.radio.IrradianceConversions._,
         edu.ie3.quants.radio.RadianceConversions._,
         edu.ie3.quants.radio.RadiantIntensityConversions._,
         edu.ie3.quants.radio.SpectralIntensityConversions._,
         edu.ie3.quants.radio.SpectralPowerConversions._,
         edu.ie3.quants.space.AngleConversions._,
         edu.ie3.quants.space.AreaConversions._,
         edu.ie3.quants.space.LengthConversions._,
         edu.ie3.quants.space.SolidAngleConversions._,
         edu.ie3.quants.space.VolumeConversions._,
         edu.ie3.quants.thermal.TemperatureConversions._,
         edu.ie3.quants.thermal.ThermalCapacityConversions._,
         edu.ie3.quants.time.FrequencyConversions._,
         edu.ie3.quants.time.TimeConversions._""".stripMargin
  )
}

object Docs {
  private def gitHash = sys.process.Process("git rev-parse HEAD").lineStream_!.head
  val defaultSettings = Seq(
    Compile / doc / scalacOptions ++= {
      val (bd, v) = ((LocalRootProject / baseDirectory).value, version.value)
      val tagOrBranch = if(v endsWith "SNAPSHOT") gitHash else "v" + v
      Seq("-sourcepath", bd.getAbsolutePath, "-doc-source-url", "https://github.com/garyKeorkunian/squants/tree/" + tagOrBranch + "€{FILE_PATH}.scala")
    },
  )
}
