package com.evojam.nlp.model

import java.util.StringTokenizer

import scala.collection.mutable

sealed abstract class Entity(val value: String, val tag: Tag) {
  require(value != null, "value cannot be null")
  require(value.nonEmpty, "value cannot be empty")
  require(tag != null, "tag cannot be null")

  def tokenize(): List[String] = {
    val tokenizer = new StringTokenizer(value)
    val xs = mutable.ArrayBuffer[String]()

    while(tokenizer.hasMoreTokens) {
      xs += tokenizer.nextToken()
    }

    xs.toList
  }

  def tokenizeAndTag(): List[String] =
    tokenize.map(token => s"$token ${tag.value}")

  override def toString() = value
}

case class Date(override val value: String) extends Entity(value, Tag.Date)
case class Preposition(override val value: String) extends Entity(value, Tag.Preposition)
case class MovieName(override val value: String) extends Entity(value, Tag.MovieName)
case class MovieGenre(override val value: String) extends Entity(value, Tag.MovieGenre)
case class TheaterName(override val value: String) extends Entity(value, Tag.TheaterName)
case class TheaterLocation(override val value: String) extends Entity(value, Tag.TheaterLocation)
case class SingularDeterminer(override val value: String) extends Entity(value, Tag.Date)
case class PluralDeterminer(override val value: String) extends Entity(value, Tag.Date)
case class DirectAdverb(override val value: String) extends Entity(value, Tag.Date)
case class SingularPeriod(override val value: String) extends Entity(value, Tag.Date)
case class PluralPeriod(override val value: String) extends Entity(value, Tag.Date)
