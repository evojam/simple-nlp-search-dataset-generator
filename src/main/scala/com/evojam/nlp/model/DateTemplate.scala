package com.evojam.nlp.model

import java.util.Locale

import scala.util.Random

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

sealed abstract class AbsDateTemplate(value: String) {
  require(value != null, "value cannot be null")
  require(value.nonEmpty, "value cannot be empty")

  private val MinDate = new DateTime(1990, 1, 1, 0, 0)
  private val MinHours = 4000
  private val MaxHours = 260000

  def pickDates(): (Date, Date) =
    (Date(format(pickDate())), Date(format(pickDate())))

  private def format(date: DateTime): String =
    DateTimeFormat
      .forPattern(value)
      .withLocale(Locale.US)
      .print(date).toLowerCase()

  private def pickDate(greaterThan: DateTime = MinDate): DateTime =
    greaterThan.plusHours(MinHours + Random.nextInt(MaxHours))
}

case class DateTemplate(value: String) extends AbsDateTemplate(value)
case class InDateTemplate(value: String) extends AbsDateTemplate(value)
case class OnDateTemplate(value: String) extends AbsDateTemplate(value)
