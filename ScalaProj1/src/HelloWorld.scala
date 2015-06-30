
//used to break loops.
import scala.util.control._;
import java.util.Date;

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

         //this is a scala method, which lives inside a class.
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

//this is a way of defining a scala function which lives in Object Add
object Add
{
  //function name, with input args, and return type.
  //all functions in scala are abstract until u define a method body.
  //fnc name = addInt
  //params, a/b are both int types
  //return type is int
  def addInt( a:Int, b:Int ) : Int = 
  {
      var sum:Int = 0;
      sum = a + b;
      return sum;
   }
  
  //this is called unit, which is like returning void in java
  //Unit = void as the return type.
  def printMe() : Unit =
  {
    println("print me");
  }
}

//functions for scala types
object CallByName
{
  //this function takes a call_by_name param, when using the "=>"
  //in the method params. the method will get evaluated when the vars
  //"t" is used.
  def delayed(t: => Long) = 
  {
    println("In the delayed method");
    
    //each time the vars t called, the method is evaluated on the spot.
    println("param = "+t);//evaluate the method passed in as a vars.
    
    t;//evaluate the method passed in as a vars.
  }
  
  //this function has an implicit return statement, eventhough we dont
  //use the "return" keyword, since using the return keyword, is for 
  //defining a type. it is optional to do. but also the last statement in the
  //function is returned to the caller.
  def time() = 
  {
    println("getting time in nano");
    System.nanoTime();
  }
}

object FuncWithVariableArgs
{
   //this is definning a function with variable args to be passed to the
   //code block. the "*" after the data type is showing how this is done.
   //"String*" is really considered as Array[String]
   def printStrings( args:String* ) = 
   {
      //count how many vars are in the variable list.
      var i : Int = 0;
      for( arg <- args )
      {
         println("Arg value[" + i + "] = " + arg );
         i = i + 1;
      }
   }
}

object DefaultParamValuesFnc
{
  //added default values to the params passed in
  //u can call the function without any params and it will 
  // use those as defaults.
  def addNums(x: Int = 5, y: Int = 10) : Int =
  {
    var added = x + y;
    return added;
  }
}

//functions defined inside functions have local scope
//they cant be called from outside the outer function.
object NestedFuncs
{
   //returns int type
   def factorial(x: Int ) : Int =
   {
     //returns int type and uses the accumalator to store the answer.
     //this function is a recursive function that functional programming
     //allows in scala.
     def factAns(y: Int, accumaltor: Int) : Int =
     {
       if(y <= 1)
         accumaltor;
       else
         factAns(y-1, y*accumaltor);
     }
     
     //this has an implicit return statement back to the caller
     //which returns an Int type.
     factAns(x,1);
     //return factAns(x,1);//this line is just as good as the line above it.
   }
   
   //recursive function which is supported in scala.
   def factorial2(n: BigInt): BigInt = 
   {  
      if (n <= 1)
      {
         1
      }
      else
      {
        n * factorial2(n - 1);
      }
   }
}

object PartiallyAppliedFncs
{
   //create a function that allows for partially
   //loaded function in memory.
   //this will avoid the annoyance of always passing in the
   //date param each time for just the message param being changed.
   def logData(date: Date, message: String) = 
   {
      println(date+"-----"+message);
   }
}

object FuncWithNamesArgs
{
   def printInts(a: Int, b: Int) = 
   {
     println("a = "+a);
     println("b = "+b);
   }
}

object HelloWorld
{
  /* This is my first java program.  
    * This will print 'Hello World' as the output
    */
  def main(args: Array[String]) 
  {
    //loop to call a recursive function
    for(jj <- 5 to 15)
    {
      println("factorial value = "+NestedFuncs.factorial2(jj));
    }
    
    //this shows the named args being passed to the function,
    //this allows any order to be passed to the function, to order
    //the params any way u want.
    FuncWithNamesArgs.printInts(b=10, a=20);

    //create date obj here
    var my_date = new Date();
    
    //bind the date param to the log data function but we need to
    //provide the "_:Type" to allow for partially bounded type
    //now our var "logWithDateBound" can be used as a function to just 
    //provide the change for that 1 param that isnt bound.
    var logWithDateBound = PartiallyAppliedFncs.logData(my_date, _:String);
    
    //here we are just passing the changing param with the same date ref type.
    logWithDateBound("message1");
    logWithDateBound("message2");
    logWithDateBound("message3");
    
    //calling the function directly.
    PartiallyAppliedFncs.logData(new Date(), "message 4, normal call");
    
    
    println("fact val = "+ NestedFuncs.factorial(0));
    println("fact val = "+ NestedFuncs.factorial(1));
    println("fact val = "+ NestedFuncs.factorial(2));
    println("fact val = "+ NestedFuncs.factorial(3));
    
    println("def values added = "+DefaultParamValuesFnc.addNums());
    
    FuncWithVariableArgs.printStrings("hello", "scala","python");
    
    CallByName.delayed(CallByName.time());
    
    //add 2 numbers.
    var sum2 = Add.addInt(1,2);
    println("sum = "+sum2);
    
    Add.addInt(1,2);
    
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
  
  //continue with functions subsections
  //http://www.tutorialspoint.com/scala/scala_functions.htm
  //Higher-Order Functions, as the link to finish the functions tutorial.
  //finish the functions section and continue with the rest.
  
}