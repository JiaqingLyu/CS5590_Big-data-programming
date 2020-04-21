import java.io.{BufferedReader, BufferedWriter, FileOutputStream, OutputStreamWriter}

import scala.io.Source
import scala.util.Random

object File {
  def main(args: Array[String]): Unit = {
    var a = 1;
    while(a<=10){
      var ran = Random.nextInt(12)+1
      val fname = Source.fromFile("/Users/louis_lyu/Desktop/asd.txt")
      val file = "/Users/louis_lyu/Desktop/ICP2-4/SourceCode/log/log" + a + ".txt"
      val writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)))
      var buffer = fname.getLines()
      var nextlines=""
      var i = 0
      while(i<ran){
        nextlines = nextlines+buffer.next() + "\n"
        i=i+1
      }
      writer.write(nextlines+"\n")
      writer.close()
      a=a+1
      Thread.sleep(4000)
    }
  }
}
