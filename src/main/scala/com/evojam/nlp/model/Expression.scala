package com.evojam.nlp.model

case class Expression(value: String) {
  require(value != null, "value cannot be null")
  require(value.nonEmpty, "value cannot be empty")

  def render(
    firstDate: Date,
    secondDate: Date,
    movieName: MovieName,
    movieGenre: MovieGenre,
    theaterName: TheaterName,
    theaterLocation: TheaterLocation,
    inDate: Date,
    onDate: Date,
    singularDet: SingularDeterminer,
    pluralDet: PluralDeterminer,
    singularPeriod: SingularPeriod,
    pluralPeriod: PluralPeriod,
    directAdv: DirectAdverb
  ): String =
    value.split("\\s+").foldLeft(List[String]()) {
      case (list, token) => token match {
        case "FDATE" => list ::: firstDate.tokenizeAndTag
        case "SDATE" => list ::: secondDate.tokenizeAndTag
        case "INDATE" => list ::: inDate.tokenizeAndTag
        case "ONDATE" => list ::: onDate.tokenizeAndTag
        case "SINGDET" => list ::: singularDet.tokenizeAndTag
        case "PLURDET" => list ::: pluralDet.tokenizeAndTag
        case "SINGPERIOD" => list ::: singularPeriod.tokenizeAndTag
        case "PLURPERIOD" => list ::: pluralPeriod.tokenizeAndTag
        case "DIRADV" => list ::: directAdv.tokenizeAndTag
        case "MOVIENAME" => list ::: movieName.tokenizeAndTag
        case "MOVIEGENRE" => list ::: movieGenre.tokenizeAndTag
        case "THEATERNAME" => list ::: theaterName.tokenizeAndTag
        case "THEATERLOCATION" => list ::: theaterLocation.tokenizeAndTag
        case prep @ _ => list ::: Preposition(prep).tokenizeAndTag
      }
    } mkString("\n")

}
