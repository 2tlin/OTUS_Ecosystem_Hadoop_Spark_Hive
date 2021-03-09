package Lesson2_ScalaProjectBuilding

import io.circe.generic.auto._
import io.circe.parser
import io.circe.syntax._

import java.io.{File, PrintWriter}
import scala.io.Source

/*
Первое приложение на Scala для работы с файлами в формате JSON
Цель: Данное задание позволит студенту создать свой первый Scala-проект и собрать его с помощью SBT,
а также познакомиться с базовыми инструментами обработки данных нативными средствами языка Scala

Задание:
1) Загрузите файл с географическими данными различных стран (https://raw.githubusercontent.com/mledoze/countries/master/countries.json)
2) Среди стран выберите 10 стран Африки с наибольшей площадью ,
3) Запишите данные о выбранных странах в виде JSON-массива объектов следующей структуры:
[{
“name”: <Официальное название страны на английском языке, строка>,
“capital”: <Название столицы, строка>(если столиц перечисленно несколько, выберите первую), -> "capital": [
                                                                                                            "Kabul"
                                                                                                        ],
“area”: <Площадь страны в квадратных километрах, число>, -> "area": 652230
}]
Обеспечьте проект инструкциями для сборки JAR-файла, принимающего на вход имя выходного файла и осуществляющего запись в него
 */
object Practice2 extends App {
  val outputFilename = Option(args(0)) match {
    case Some(filename) => filename
    case None => throw new ArrayIndexOutOfBoundsException("Please enter output filename")
  }

  val file = Source.fromURL("https://raw.githubusercontent.com/mledoze/countries/master/countries.json")
  val jsonString = file.getLines().mkString

  case class Name(official: String)
  case class Company(name: Name, region: String, capital: Seq[String], area: Float)
  case class Result(name: String, capital: String, area: Int)

  val result = parser.decode[List[Company]](jsonString) match {
    case Left(ex) => ex.getMessage
    case Right(countryList) => countryList
      .filter(_.region == "Africa")
      .sortBy(- _.area)
      .take(10)
      .map(company => Result(company.name.official, company.capital.head, company.area.toInt))
      .asJson
  }

  val fileObject = new File(outputFilename )
  val printWriter = new PrintWriter(fileObject)
  printWriter.write(result.toString)
  printWriter.close()
}
