package com.evojam.nlp.model

case class UnknownTagException(tag: String) extends Exception(s"Unknown tag: $tag")

case class Tag(value: String) {
  require(value != null, "value cannot be null")
  require(value.nonEmpty, "value cannot be empty")

  override def toString() = value
}

object Tag {
  val Date = Tag("TIME_EXPRESSION")
  val Preposition = Tag("PREPOSITION")
  val MovieName = Tag("MOVIE_NAME")
  val MovieGenre = Tag("MOVIE_GENRE")
  val TheaterName = Tag("THEATER_NAME")
  val TheaterLocation = Tag("THEATER_LOCATION")

  def tagOf(value: String): Tag = value match {
    case Date.value => Date
    case Preposition.value => Preposition
    case MovieName.value => MovieName
    case MovieGenre.value => MovieGenre
    case TheaterName.value => TheaterName
    case TheaterLocation.value => TheaterLocation
    case tag@_ => throw UnknownTagException(tag)
  }
}
