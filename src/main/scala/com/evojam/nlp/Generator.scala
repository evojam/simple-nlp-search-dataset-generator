package com.evojam.nlp

import java.io.FileOutputStream
import java.nio.charset.StandardCharsets

import scala.util.Random
import scala.io.Source

import com.evojam.nlp.model._

object Generator extends App {
  val sampleSize = 200000

  def filterNonAlphaChars(str: String) =
    str
      .replaceAll("[-_]", " ")
      .replaceAll("[^a-z0-9 ]", "")
      .replaceAll("\\s+", " ")

  def load[T](name: String, f: String => T,
    removeNonAlphaChars: Boolean = true,
    toLowerCase: Boolean = true): List[T] =
    Source.fromFile(s"src/main/resources/$name.txt")
      .getLines().toList
      .map(line => if (toLowerCase) line.toLowerCase else line)
      .map(line => if (removeNonAlphaChars) filterNonAlphaChars(line) else line)
      .filter(_.nonEmpty)
      .map(f)

  lazy val dateTemplates = load("date-template", DateTemplate, false, false)
  lazy val inDateTemplates = load("indates", InDateTemplate, false, false)
  lazy val onDateTemplates = load("ondates", OnDateTemplate, false, false)
  lazy val movieNames = load("movie-name", MovieName)
  lazy val movieGenres = load("movie-genre", MovieGenre)
  lazy val theaterNames = load("theater-name", TheaterName)
  lazy val theaterLocations = load("theater-location", TheaterLocation)
  lazy val singularDet = load("singulardet", SingularDeterminer)
  lazy val pluralDet = load("pluraldet", PluralDeterminer)
  lazy val directAdverb = load("diradv", DirectAdverb)
  lazy val singularPeriod = load("singularperiod", SingularPeriod)
  lazy val pluralPeriod = load("pluralperiod", PluralPeriod)
  lazy val expressions = load("expression", Expression, false, false)

  def pickSingle[T](xs: List[T]): T = {
    require(xs.nonEmpty, "xs cannot be empty")

    xs(Random.nextInt(xs.size))
  }

  def generate(targetPath: String) {
    val out = new FileOutputStream(targetPath)

    for (i <- 0 to sampleSize) {
      val (firstDate, secondDate) = pickSingle(dateTemplates).pickDates
      val (inDate, _) = pickSingle(inDateTemplates).pickDates
      val (onDate, _) = pickSingle(onDateTemplates).pickDates

      val expressionBytes = pickSingle(expressions)
        .render(
          firstDate,
          secondDate,
          pickSingle(movieNames),
          pickSingle(movieGenres),
          pickSingle(theaterNames),
          pickSingle(theaterLocations),
          inDate,
          onDate,
          pickSingle(singularDet),
          pickSingle(pluralDet),
          pickSingle(singularPeriod),
          pickSingle(pluralPeriod),
          pickSingle(directAdverb))
       .getBytes(StandardCharsets.UTF_8)

       out.write(expressionBytes)
       out.write("\n\n".getBytes(StandardCharsets.UTF_8))
    }

    out.close()
  }

  generate("out.txt")
}
