import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._
import org.graphframes._

object Main {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName("Spark SQL basic example")
      .config("spark.master", "local")
      .getOrCreate()
    val input = spark.read.format("csv").option("header","true").load("/Users/louis_lyu/Desktop/Datasets/201508_trip_data.csv")
    //rmv the dup
    val trip = input.dropDuplicates(input.columns)
    //vertices
    val vertices1 = trip.select("Start Terminal","Start Station")
      .withColumnRenamed("Start Terminal","id")
      .withColumnRenamed("Start Station","station")
    //edges
    val edges1 = trip.select("Start Terminal","End Terminal","Duration")
      .withColumnRenamed("Start Terminal","src")
      .withColumnRenamed("End Terminal","dst")
    val input2 = spark.read.format("csv").option("header","true").load("/Users/louis_lyu/Desktop/Datasets/201508_station_data.csv")
    val station = input2.dropDuplicates(input2.columns)
    //graph
    val g1 = GraphFrame(vertices1,edges1)
    //g1.vertices1.show()
    //g1.edges1.show()

    val vertices2 = station.select("name")
      .withColumnRenamed("name","id")
    //edges
    val edges2 = trip.select("Start Station","End Station")
      .withColumnRenamed("Start Station","src")
      .withColumnRenamed("End Station","dst")
    val g2 = GraphFrame(vertices2,edges2)
    //2. Triangle count
//    val triangle = g2.triangleCount.run()
//    triangle.select("id","count").show()
//    println("Triangle Count")

    //3. Find Shortest
//    val path = g2.shortestPaths.landmarks(Seq("St James Park","Park at Olive")).run()
//    path.show()
//    println("Shortest path")

    //4. Page Rank
//    val Rank = g2.pageRank.resetProbability(0.15).tol(3).run()
//    Rank.vertices.select("id", "pagerank").show(15)

    //BFS
//    val BFS = g2.bfs.fromExpr("id = 'Japantown'").toExpr("dockcount < 10").run()
//    BFS.show()
//
    //Saving to File
    g2.vertices.write.csv("/Users/louis_lyu/Desktop/Datasets/res.csv")

    g2.edges.write.csv("/Users/louis_lyu/Desktop/Datasets/res.csv")
  }
}