
//used to break loops.
import scala.util.control._;

//showing scope of out class with private inner classes.
class Outer 
{
   class Inner 
   {
     //belongs to Inner
      private def f() 
      { 
        println("f"); 
      }
      
      protected def g() 
      { 
        println("f");
      }
      
      //this is considered public access if private/ protected
      //is not specified.
      def h()
      {
        println("h from inner class.");
      }
      
      class InnerMost 
      {
         f(); // OK
      }
   }
   
   //allowed to call the protected function from subclass.
   class Sub extends Inner 
   {
      g();
   }
   
   //this class can call the public function from Inner class.
   class Other 
   {
     (new Inner).h(); // Error: f is not accessible
   }
   //(new Inner).f(); // Error: f is not accessible
}

//society package.
package society 
{  //professional package
   package professional 
   {
      class Executive 
      {
         // the [access level] var...allows to specific at a variable level package level scople
         //with in the class members of the fields.
        
         //this vars is accessible only for classes in the professional pkg.
         private[professional] var workDetails = null;
         
         //this vars is accessible via the society package.
         private[society] var friends = null;
         
         //this is accessible via objs of "this" reference.
         private[this] var secrets = null;

         def help(another : Executive) 
         {
            println(another.workDetails);
            //println(another.secrets) //ERROR, not in the society package.
         }
      }
   }
   
   class test1
   {
     def help2(another2 : professional.Executive) 
     {
       //another.
        //println(another.workDetails);
        println(another2.friends); //ERROR, not in the society package.
     }
   }
}

object HelloWorld
{
  /* This is my first java program.  
    * This will print 'Hello World' as the output
    */
  def main(args: Array[String]) 
  {
    val my_outer : Outer = new Outer;
    val my_inner = new my_outer.Inner;
    my_inner.h();
    
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
    
      //showing nested if else statements.
      var x = 10;

      if( x == 10 ){
         println("Value of X is 10");
      }else if( x == 20 ){
         println("Value of X is 20");
      }else if( x == 30 ){
         println("Value of X is 30");
      }else{
         println("This is else statement");
      }
      
      //show diff types of looping in scala and the break approach.
      
      //while loop
       var a = 10;

      // while loop execution
      while( a < 20 ){
         println( "Value of a: " + a );
         a += 1;
      }
      
      a = 0;
      // for loop execution with a range
      for( a <- 1 until 10)
      {
         println( "Value of a: " + a );
      }
    
    a = 0;
    //create a list of numbers here.
    val numList = List(1,2,3,4,5,6,7,8,9,10);

      val loop = new Breaks;//create break obj to allow to break from loop.
      loop.breakable {
         for( a <- numList){
            println( "for loop Value of a: " + a );
            if( a == 4 ){
               loop.break;
            }
         }
      }
      
      println( "After the loop" );
      
      //this shows how to break nested loops with outer and inner
      //break statements and the block its being broken for.
      var b = 0;
      val numList1 = List(1,2,3,4,5);
      val numList2 = List(11,12,13);

      val outer = new Breaks;
      val inner = new Breaks;

      outer.breakable {
         for( a <- numList1){
            println( "Value of a: " + a );
            inner.breakable {
               for( b <- numList2){
                  println( "Value of b: " + b );
                  if( b == 12 ){
                     inner.break;
                  }
               }
            } // inner breakable
         }
      } // outer breakable.
  }
  
  //scala functions
  //http://www.tutorialspoint.com/scala/scala_functions.htm
}