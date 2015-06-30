
//used to break loops.
import scala.util.control._;
import java.util.Date;
import Array._;

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

//These are functions that take other functions as parameters,
//or whose result is a function.
object HighOrderFncs
{
   //"f" param is a function that uses param "v" as input into the "f" function.
   //"f: Int => String", "f" is the function ref pointer
   //"Int => String" means that the input is a Int type
   //and the "String" is the return type for this function ref param.
   //"f: Int => String", so this means, for function ref "f", the input to it is an Int type
   //and the return value is a String
    def apply(f: Int => String, v: Int) = 
    {
       f(v);//this is calling the "f" fnc using the v param.
    }
   
    //the "[]" around the A is saying that the "A" is a data type for the func args of x
    //so x is of type "A"..and in this case, "A" is really of type int.
    def layout1[A](x: A) = 
    {
      "[" + x.toString() + "]";
    }
    
    //this function is equivalent to the layout1 function.
    //this is explicitly saying that a String type needs to be returned
    //back with a return statement.
    def layout2[A](x: A) : String =
    {
      return ("[" + x.toString() + "]");
    }
}

object AnonymousFncs
{
   //this is a i++ type of anonymous function
   //or in the declaration at compile time its called
   //fnc literal. at runtime it is called, function value.
  
   //this is creating a function that takes and Int type
   //and increments the value by 1, and returns that value;
   var inc = (x:Int) => x+1;
   
   //this is defining more args to the function literal and 
   //does multiplicaton and returns the result.
   var multiply = (x: Int, y: Int) => x*y;
  
   //this is uisng no args as a func call, and returns its value
   var userDir = () => {System.getProperty("user.dir")};
}

object CurryingFncs
{
   //this is a currying function that allows for
   //many params to now be defined by func with just 1 param each.
   //returns a String back to the caller.
   def str_cat1(a:String)(b:String) =
   {
     a + b;
   }
   
   //this function is the same as the function above in terms of syntax.
   //returns a string back to the caller.
   def str_cat2(a: String) = (b: String) =>
   {
     a + b;
   }
}

//A closure is a function, whose return value depends on the value of one or
//more variables declared outside this function.
//If a function has no external references, 
//then it is trivially closed over itself. No external context is required.
object ClosureFncs
{
   //declared a var to be used in the anonymous fnc below.
   var factor = 10;   
   
   //this is an anonymous fnc with a reference to a variable outside of the fnc definition.
   //this fnc is considered a closure because it uses the factor vars outside of the anonymous fnc def.
   var multiplier = (i:Int) =>
   {
      i * factor;
   }
}

object ScalaCollectionsTypes
{
    // Define List of integers.
    //must be same type stored..similar to array
    //but variable len container.
    val x1 = List(1,2,3,4);
    
    //this is another way to define a list..where ending item tail
    //list in scala are linked lists.
    val fruit = "apples" :: ("oranges" :: ("pears" :: Nil));
    
    // Define a set.
    //must be same type
    var x2 = Set(1,3,5,7);
    
    // Define a map. K/V type of data structure.
    val x3 = Map("one" -> 1, "two" -> 2, "three" -> 3);
    
    x3.keys.foreach{ i =>  
                     print( "Key = " + i )
                     println(" Value = " + x3(i) )}
    
    // Create a tuple of two elements.
    //this list type can contain different data types.
    val x4 = (10, "Scala");
    
    // Define an option
    //provides a container for zero or one element of a given type
    val x5: Option[Int] = Some(5);
    
    //read here
    //http://www.tutorialspoint.com/scala/scala_collections.htm
    //go over the diff collection types, especially the
    //tuple, and options collections.
}

object HelloWorld
{
  /* This is my first java program.  
    * This will print 'Hello World' as the output
    */
  def main(args: Array[String]) 
  {
    //create range arrays
    //this is using the range api to make a array of step 2,
    //so like 10, 12, 14..etc upto 20 but not including 20.
    var my_range= range(10,20,2);
    for ( x <- my_range ) 
    {
       println(x);
    }
    
    //combine 2 array, concat array.
    var array_1_cc = Array(1,2,3);
    var array_2_cc = Array(4,5,6);
    var ans_2 = concat(array_1_cc, array_2_cc);
    for ( x <- ans_2 ) 
    {
       println(x);
    }
    
    //mutli dimensional arrays are not directly supported by scala
    //but u can use it.
    //this is defining a multi dimensional array with scala api support.
    //import this "import Array._;" to use mutli dim arrays.
    var myMatrix = ofDim[Int](3,3);
    var ints:Int = 200;
    for (i <- 0 to 2) 
    {
       for ( j <- 0 to 2) 
       {
          myMatrix(i)(j) = ints;
          ints = ints + 1;
       }
    }
    
    for (i <- 0 to 2) 
    {
       for ( j <- 0 to 2) 
       {
          print(" " + myMatrix(i)(j));
       }
       println();
     }
    
    //declaring array vars.
    //both of these vars array declarations are valid.
    //this declared array type shows the type defined for the vars on the 
    //left hand side of the "=" sign
    var my_array1:Array[String] = new Array[String](3);
    my_array1(0) = "100";
    
    for(x <- my_array1)
    {
      println(x);
    }
    
    //this one allows u to declare the vars as an array type.
    var my_array2 = new Array[String](3);
    my_array2(1) = "200";
    
    //this show how to create an array with Array obj construct.
    var my_array3 = Array("Zara", "Nuha", "Ayan");
    for(x <- 0 to (my_array3.length-1) )
    {
      println(my_array3(x));
    }
    
    println("array 1 = "+my_array1+", array 2 = "+my_array2+", array 3 = "+my_array3); 
    
    println("closure = "+ClosureFncs.multiplier(10));
    
    println(CurryingFncs.str_cat1("Hello")("World"));
    println(CurryingFncs.str_cat2("Jim")("bo"));
    
    //call the anonymous fncs now
    var my_inc = AnonymousFncs.inc(5);
    println(my_inc);
    
    var my_mult = AnonymousFncs.multiply(6,5);
    println(my_mult);
    
    var my_curr_dir = AnonymousFncs.userDir();
    println(my_curr_dir);
    
    println( HighOrderFncs.apply(HighOrderFncs.layout1, 10));
    println( HighOrderFncs.apply(HighOrderFncs.layout2, 455));
    
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
}