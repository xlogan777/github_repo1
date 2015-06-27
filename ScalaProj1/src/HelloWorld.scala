
object HelloWorld 
{
  /* This is my first java program.  
    * This will print 'Hello World' as the output
    */
  def main(args: Array[String]) 
  {
    println("Hello, world!"); // prints Hello World
    println("Hello, world!"); // prints Hello World
    
    println("Hello\tWorld\n\n" );
    
    //mutable variable that can change value.
    //use can use var or val to declare an variable.
    //define the variable, then the type, then the value if u want to.
    //not providing a variable type or set value is only for classes not objects.
    var myVar : String = "Foo";//this is a mutable variable.
    
    val myVal : String = "myVal";//using val makes a constant
    val test1 : Int = 100;//using val makes the variable a constant.
    
    //u can assign any variables at once like so.
    //pair only works for 2 vars.
    val (myVal1: Int, myVal2: String) = Pair(40, "Foo");//both of these are now immutable vars.
    
    var sum: Int = test1+myVal1;
    println("sum = "+sum);
    
    //upto Scala Access Modifiers section in tutorialspoint.com
    //http://www.tutorialspoint.com/scala/scala_access_modifiers.htm
  }
}