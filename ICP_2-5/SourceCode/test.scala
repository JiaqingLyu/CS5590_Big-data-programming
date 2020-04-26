import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._
import org.graphframes._

object test {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName("Spark SQL basic example")
      .config("spark.master", "local")
      .getOrCreate()
    val df1 = spark.read.format("csv").option("header","true").load("/Users/louis_lyu/Desktop/Datasets/201508_station_data.csv")
    df1.createOrReplaceTempView("station")
    df1.show()
    val df2 = spark.read.format("csv").option("header","true").load("/Users/louis_lyu/Desktop/Datasets/201508_trip_data.csv")
    df2.createOrReplaceTempView("trip")
    df2.show()
    val df3 = spark.read.format("csv").option("header","true").load("/Users/louis_lyu/Desktop/Datasets/ConsumerComplaints.csv")
    df3.createOrReplaceTempView("complaints")
    df3.show()

    //2. Concatenate chunks into list & convert to DataFrame
    val res = df1.select(col("station_id"),col("name"),lit(" "))
    res.toDF().show()
    println("2. Concatenate chunks into list & convert to DataFrame")


    //3. Rmv duplicates
    df1.distinct().show()
    println("3. Rmv duplicates")

    //4. Name Cols
    var tripGraph = df2.withColumnRenamed("Trip ID", "id")
    tripGraph.show()
    println("4. Rename cols")

    //5. output dataframe
    df1.show()

    //6. Create vertices
    var station = df1.withColumnRenamed("station_id", "src")
    station = station.withColumnRenamed("landmark", "dst")
    val res6 = GraphFrame(tripGraph,station)

    //7. Show some vertices
    println("Station numbers: "+ res6.vertices.count)

    //8. Show some edges
    println("Trip numbers: "+ res6.edges.count)


    //9. Vertex in-Degree
    res6.inDegrees.show(10)

    //10. Vertex out-Degree
    res6.outDegrees.show()

    //11. Apply the motif findings.
    val res11: DataFrame = res6.find("(a)-[e]->(b); (b)-[e2]->(a)")
    res11.show()

    //    val res1 = spark.sql("select text from tmp1")


    //df.write.parquet("/Users/louis_lyu/Desktop/ICP2_3/survey")
  }
}
