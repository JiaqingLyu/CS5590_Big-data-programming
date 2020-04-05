import org.apache.hadoop.yarn.state.Graph
import org.apache.spark.{SparkConf, SparkContext}
import sun.security.provider.certpath.Vertex;

object DFS {
  def DFS(start: Vertex, g: Graph): List[Vertex]={
    def DFS0(v: Vertex, visited : List[Vertex]): List[Vertex] = {
      if(visited.contians(v))
        visited
      else{
        val neighbours : List[Vertex] = g(v)filterNot visited.contains
        neighbours.foldLeft(v :: visited)((b,a) => DFS0(a,b))
      }
    }
    DFS0(start,List()).reverse
  }
  val res = DFS(1,g)
  println("DFS result",res.mkString(","))
}
