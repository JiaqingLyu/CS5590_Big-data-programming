import org.apache.spark.{SparkConf, SparkContext};

object MergeSort {
  val conf = new SparkConf()
  conf.setMaster("local").setAppName("MyFile")
  val sc = new SparkContext(conf)
  def mergeSort[T](less:(T,T)=>Boolean)(xs:List[T]):List[T]={
    def merge(xs:List[T],ys:List[T]): List[T]={
      (xs,ys) match {
        case(Nil, _) => ys
        case(_,Nil) => xs
          case (x :: xs1, y :: ys1)=>
            if(less (x,y)) x :: merge(xs1,ys)
            else y :: merge(xs, ys1)
      }
    }
    val n = xs.length/2
    if(n==0) xs
    else {
      val (ys, zs) = xs.splitAt(n)
      merge(mergeSort(less)(ys),mergeSort(less)(zs))
    }
  }

  def main(args: Array[String]): Unit = {
    val data = List(1,3,76,3,44,67,0,32)
    def comp(left: Int, right: Int): Boolean ={
      left <= right
    }
    val res = sc.parallelize(mergeSort(comp)(data))
    for(x<-res){
      println(x + "\t")
    }
  }
}
