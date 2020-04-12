import org.apache.spark.sql.SparkSession
object ICP2_3 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName("Spark SQL basic example")
      .config("spark.master", "local")
      .getOrCreate()
    val df = spark.read.format("csv").option("header","true").load("/Users/louis_lyu/Desktop/ICP2-3/survey.csv")
    //df.show()
    //print("Write to file")
    //df.write.parquet("/Users/louis_lyu/Desktop/ICP2_3/survey")
    //print("Duplicates")
    df.createOrReplaceTempView("tmp")
//    val duplicate = spark.sql("select Country, Count(*) as Number from tmp GROUP BY Country Having Count(*)>1")
//    duplicate.show()
//    println("Apply Union operation on the dataset and order the output by CountryName alphabetically.")
//    val df1 = spark.sql("select Country, remote_work from tmp")
//    val df2 = spark.sql("select Gender, Age from tmp")
//    val union = df1.union(df2)
//    println("sort by character")
//    union.orderBy("Country").show();
//    val treat = spark.sql("select treatment, count(Country) as Number from tmp GROUP BY treatment")
//    treat.show()
    val df3 = df.select("Country","state")
    val df4 = df.select("Country","Timestamp")
    df3.createOrReplaceTempView("df3")
    df4.createOrReplaceTempView("df4")
//    val res1 = spark.sql("select df3.state, df4.Timestamp from df3,df4 where df3.Country = df4.Country")
//    res1.show()
//    val res2 = spark.sql("select df3.state, df4.Timestamp from df3 INNER JOIN df4 ON(df3.Country=df4.Country)")
//    res2.show()
    val get13 = df.take(13).last
    println(get13)
  }
}
