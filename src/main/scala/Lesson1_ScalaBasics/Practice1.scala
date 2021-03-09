package Lesson1_ScalaBasics

import scala.io.Source
/*
1. Загрузите сводку теннисных матчей в одиночном мужском разряде за 2020 год
2. Вычислите следующие величины:
  а) Общее кол-во матчей Даниила Медведева (Daniil Medvedev) за 2020 год
3. Самостоятельно попробуйте вычислить следующие агрегаты:
  а) Количество побед Даниила Медведева в разрезе по турнирам
  б) Количество различных турниров в разрезе по месяцам
 */
object Practice1 {
  val file = Source.fromURL("https://raw.githubusercontent.com/JeffSackmann/tennis_atp/master/atp_matches_2020.csv")

  val lines = file.getLines().toList
  val header :: records = lines
  val headerMap = header.split(",").zipWithIndex.toMap

  println("Общее кол-во матчей Даниила Мекдведева (Daniil Medvedev) за 2020 год:")

  val totalMatchesbyDM = records.filter(record => {
    val recordArr = record.split(",")
    val loser = recordArr(headerMap("loser_name"))
    val winner = recordArr(headerMap("winner_name"))
    loser == "Daniil Medvedev" || winner == "Daniil Medvedev"
  })
  println(totalMatchesbyDM.size)

  println("Количество побед Даниила Медведева в разрезе по турнирам:")

  val recordsWinMap = records
    .filter(record => {
    val recordArr = record.split(",")
    recordArr(headerMap("winner_name")) == "Daniil Medvedev"
    })
    .map(record => {
      val recordArr = record.split(",")
      recordArr(headerMap("tourney_name"))
    })
    .groupBy(identity)
    .map {
      case (name, list) => name -> list.size
    }
    recordsWinMap.foreach(println)

    println("Количество различных турниров в разрезе по месяцам:")

    val jan = "....01..".r
    val feb = "....02..".r
    val mar = "....03..".r
    val apr = "....04..".r
    val may = "....05..".r
    val jun = "....06..".r
    val jul = "....07..".r
    val aug = "....08..".r
    val sep = "....09..".r
    val oct = "....10..".r
    val nov = "....11..".r
    val dec = "....12..".r

  val recordsByMonthsAndDistinctTourneys = records
        .map { record => {
          val recordArr = record.split(",")
          recordArr(headerMap("tourney_date")) match {
              case jan() => "January"    -> recordArr(headerMap("tourney_name"))
              case feb() => "February"   -> recordArr(headerMap("tourney_name"))
              case mar() => "March"      -> recordArr(headerMap("tourney_name"))
              case apr() => "April"      -> recordArr(headerMap("tourney_name"))
              case may() => "May"        -> recordArr(headerMap("tourney_name"))
              case jun() => "June"       -> recordArr(headerMap("tourney_name"))
              case jul() => "July"       -> recordArr(headerMap("tourney_name"))
              case aug() => "August"     -> recordArr(headerMap("tourney_name"))
              case sep() => "September"  -> recordArr(headerMap("tourney_name"))
              case oct() => "October"    -> recordArr(headerMap("tourney_name"))
              case nov() => "November"   -> recordArr(headerMap("tourney_name"))
              case dec() => "December"   -> recordArr(headerMap("tourney_name"))
              case _ =>
            }
          }
        } // List((January,Atp Cup), (January,Atp Cup), (January,Atp Cup), (January,Atp Cup), (February,Santiago), (August,Cincinnati Masters), (August,Cincinnati Masters), ...
        .toSet // HashSet((January,Adelaide), (February,Santiago), (March,Davis Cup QLS R1: CRO vs IND), (October,Cologne 2), ...
        .foldLeft(Map[String, List[String]]()) {
          case (map, (month: String, tourney_name: String)) =>
            map + (month -> (tourney_name :: map.getOrElse(month, Nil)))
        } // (August,List(Us Open, Cincinnati Masters))
        .map {
          case (month, list) => month -> list.size
        } //
         recordsByMonthsAndDistinctTourneys.foreach(println)
}
