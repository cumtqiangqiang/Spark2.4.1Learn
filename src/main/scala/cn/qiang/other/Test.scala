package cn.qiang.other

object Test {
  def main(args: Array[String]): Unit = {
//    [0.057073041710340625,0.9429269582896593]
//      val a = new Animal("dog",12)
//     println(a.name)
//     a.eat("cc")
    val e1 =1+ math.exp(-0.057073041710340625)
    val e2 = 1/e1
    println(e1)
    println(e2)

    val p = new People()
    p.eat("aa","Cola").play("Car")

  }

  class Animal(val name:String,val age:Int){

      def eat(food:String): Unit ={
        println(name + " Eat:"+food)
      }
  }
}
