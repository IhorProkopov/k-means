package ua.prokopov

object Main {

  def main(args: Array[String]): Unit = {
    val points = List(
      Point(1,3),
      Point(3,3),
      Point(4,3),
      Point(5,3),
      Point(1,2),
      Point(4,2),
      Point(1,1),
      Point(2,1)
    )
    val res = KMeans.findCentroids(points, 2)
    println(res)
  }

}
