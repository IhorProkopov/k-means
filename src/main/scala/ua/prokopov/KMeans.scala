package ua.prokopov

import scala.annotation.tailrec
import scala.util.Random.nextInt

object KMeans {

  val MIN_DIST_DIFF = 0.01

  def findCentroids(points: List[Point], numOfAreas: Int = 1): List[(Point, List[Point])] = {
    calculateCentroids(points, generateCentroids(points, numOfAreas))
  }

  @tailrec
  private def calculateCentroids(points: List[Point], centroids: List[Point]): List[(Point, List[Point])] = {
    val clusters = points.map(point => {
      val nearestCentroid = centroids.map(c => (c, getEuclideanDistance(c, point))).minBy(_._2)._1
      (point, nearestCentroid)
    }).groupBy(_._2).toList.map(it => (it._1, it._2.map(point => point._1)))

    var shouldRepeat = false

    val newCentroids = clusters.map(cluster => {
      val newCentroid = Point(cluster._2.map(_.x).sum / cluster._2.size,
        cluster._2.map(_.y).sum / cluster._2.size)
      if (getEuclideanDistance(newCentroid, cluster._1) > MIN_DIST_DIFF) {
        shouldRepeat = true
      }
      newCentroid
    })

    if (shouldRepeat) calculateCentroids(points, newCentroids) else clusters
  }

  private def generateCentroids(points: List[Point], numOfAreas: Int): List[Point] = {
    val startX = points.map(_.x).min.toInt
    val endX = points.map(_.x).max.toInt
    val startY = points.map(_.y).min.toInt
    val endY = points.map(_.y).max.toInt
    List.fill(numOfAreas)(Point(startX + nextInt((endX - startX) + 1), startY + nextInt((endY - startY) + 1)))
  }

  private def getEuclideanDistance(p1: Point, p2: Point): Double =
    Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2))

}
