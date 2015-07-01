
//used to break loops.
import scala.util.control._;
import java.util.Date;
import Array._;
import scala.collection.mutable.MutableList;
import scala.util.matching.Regex;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io._;//using java io pkg in scala to do io stuff.
import scala.Console;
import scala.io.Source;

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
    //goes over the diff collections types in scala.
    //http://www.tutorialspoint.com/scala/scala_collections.htm
  
    // Define List of integers.
    //must be same type stored..similar to array
    //but variable len container.
    val x1 = List(1,2,3,4);
    x1.+:(100);//add item to list.
    
    //this is another way to define a list..where ending item tail
    //list in scala are linked lists.
    val fruit = "apples" :: ("oranges" :: ("pears" :: Nil));
    
    //defined type up front
    val fruit2: List[String] = List("apples", "oranges", "pears");

    
    // Define a set.
    //must be same type
    var x2 = Set(1,3,5,7);
    x2.+(10);//add element to set.
    
    var s : Set[Int] = Set(1,3,5,7);
    
    // Define a map. K/V type of data structure.
    val x3 = Map("one" -> 1, "two" -> 2, "three" -> 3);
    var A:Map[String,Int] = Map();//define data types for map.
    A += ("A1" -> 1);//adding item to map.
    
    x3.keys.foreach{ i =>  
                     print( "Key = " + i )
                     println(" Value = " + x3(i) )}
    
    // Create a tuple of two elements.
    //this list type can contain different data types.
    val x4 = (10, "Scala");
    
    //define a tuple and access a tuple.
    val t = (1,2,3,4);
    val sum = t._1 + t._2 + t._3 + t._4;
    
    // Define an option
    //provides a container for zero or one element of a given type
    val x5: Option[Int] = Some(5);
}

//class syntax with a constructor that has 2 args
class Point(val xc: Int, val yc: Int)
{
   //class members
   var x: Int = xc;
   var y: Int = yc;
   
   //method for this method
   def move(dx: Int, dy: Int) =  
   {
      x = x + dx;
      y = y + dy;
      
      println ("Point x location : " + x);
      println ("Point y location : " + y);
   }
}

//this is showing how to extend a base class and how to override the args for 
//the base class contructor.
//method overriding requires the "override" keyword, 
//and only the primary constructor can pass parameters to the base constructor
//scala cannot have static members, pure OO.
class Location(override val xc: Int, override val yc: Int, val zc :Int) extends Point(xc, yc)
{
   var z: Int = zc

   def move(dx: Int, dy: Int, dz: Int) = 
   {
      x = x + dx
      y = y + dy
      z = z + dz
      println ("Point x location : " + x);
      println ("Point y location : " + y);
      println ("Point z location : " + z);
   }
}

/*
A trait encapsulates method and field definitions, 
which can then be reused by mixing them into classes. Unlike class inheritance,
in which each class must inherit from just one superclass, 
a class can mix in any number of traits.

Traits are used to define object types by specifying the signature 
of the supported methods. Scala also allows traits to be partially
implemented but traits may not have constructor parameters.

Child classes extending a trait can give implementation for the 
un-implemented methods. So a trait is very similar to what we 
have abstract classes in Java.

 */

//this is definining some of the methods for this trait.
//very similar to abstract class in java
//a class can extend any number of traits and implement what it needs.
trait Equal
{
  //this method needs an implementation when called.
  //this is an abstract method in java.
  def isEqual(x: Any): Boolean;
  
  //this is already implemented and ready to use.
  def isNotEqual(x: Any): Boolean =
  {
     return !isEqual(x); 
  }
}

//this class is extending the trait "equal"
//and providing a definition for the "isEqual" method.
//http://www.tutorialspoint.com/scala/scala_traits.htm
//this link above tells reasons to use traits.
//When to use traits?
class MyPoint(xc: Int, yc: Int) extends Equal 
{
  var x: Int = xc;
  var y: Int = yc;
  
  //this "any" type is defining to be any obj type being called here.
  def isEqual(obj: Any): Boolean =
  {
     return obj.isInstanceOf[Point] && obj.asInstanceOf[Point].x == x; 
  }  
}

//pattern matching
object PatternMatching
{
  //this is defining to use the x vars to match a case.
  //and returns a string. define by the string type
  //we need to "x match" to evaluate the cases for the input.
  //similar to a switch statement in java.
  def matchTest(x: Int): String = x match 
  {
     case 1 => "one"
     case 2 => "two"
     case _ => "many"
  }
  
  //this will pattern match any data type to the 
  //case statement and return back any type.
  def matchTest2(x: Any): Any = x match 
  {
      case 1 => "one"
      case "two" => 2
      case y: Int => "scala.Int"
      case _ => "many"
   }
  
  //this function is the same as the matchTest2, but using brackets on match keyword.
  def matchTest3(x: Any) =
  {
      x match 
      {
         case 1 => "one";
         case "two" => 2;
         case y: Int => "scala.Int";
         case _ => "many";
      }
  }
  
   // case class pattern matching.
   /*
    Adding the case keyword causes the compiler to add a number of useful features automatically.
    The keyword suggests an association with case expressions in pattern matching.

    First, the compiler automatically converts the constructor arguments into 
    immutable fields (vals). The val keyword is optional. If you want mutable fields, 
    use the var keyword. So, our constructor argument lists are now shorter.
    
    Second, the compiler automatically implements equals, hashCode,
     and toString methods to the class, which use the fields specified as constructor 
     arguments. So, we no longer need our own toString methods.
    
    Finally, also, the body of Person class is gone because there
     are no methods that we need to define!    
    */
   case class Person(name: String, age: Int);
}

//Scala doesn't actually have checked exceptions.
//When you want to handle exceptions, you use a try{...}catch{...} block like you would in 
//Java except that the catch block uses matching to identify and handle the exceptions.
object ScalaExceptions
{
   def testScalaExceptions() =
   {
      //do try and catch, but u need to pattern match the 
      //different types of exceptions being thrown.
      //there are no checked exceptions in scala.
      try 
      {
         val f = new FileReader("input.txt");
      } 
      catch 
      {
         case ex: FileNotFoundException =>
         {
            println("Missing file exception");
         }
         case ex: IOException => 
         {
            println("IO Exception");
         }
      }
      finally
      {
         println("run anyways");
      }
      
   }
}

/*
  An extractor in Scala is an object that has a method called unapply as one of its members. 
  The purpose of that unapply method is to match a value and take it apart. 
  Often, the extractor object also defines a dual method apply for building values, 
  but this is not required.
 */
//defining the unapply method makes this obj an extractor type.
//where the apply method does the opposite.

/*
 When an instance of a class is followed by parentheses with a list of zero or more parameters,
 the compiler invokes the apply method on that instance. We can define apply
 both in objects and in classes.

 As mentioned above, the purpose of the unapply method is to extract a specific
 value we are looking for. It does the opposite operation apply does.
 When comparing an extractor object using the match statement the unapply 
 method will be automatically executed => example below
 
 object Test {
   def main(args: Array[String]) {
      
      val x = Test(5)//this is calling the "apply" method on Test obj
      println(x)

      x match
      {
         //this case state on obj is calling the unapply method.
         case Test(num) => println(x+" is bigger two times than "+num)
         //unapply is invoked
         case _ => println("i cannot calculate")
      }

   }
   def apply(x: Int) = x*2
   def unapply(z: Int): Option[Int] = if (z%2==0) Some(z/2) else None
}
 
 */
object ScalaExtractor
{
   //The injection method (optional)
   def apply(user: String, domain: String): String = 
   {
      return user +"@"+ domain;
   }

   //The extraction method (mandatory)
   /*
    The unapply must also handle the case where the given string is not an email address. 
    That's why unapply returns an Option-type over pairs of strings. 
    Its result is either Some(user, domain) if the string str is an email address 
    with the given user and domain parts, or None, if str is not an email address.   
    */
   def unapply(str: String): Option[(String, String)] = 
   {
      val parts = str split "@";
      
      if(parts.length == 2)
      {
         Some(parts(0), parts(1)); 
      }
      else
      {
         None;
      }
   }
}

object ScalaFileIO
{
   //write to a file using the java io classes.
   //read from command line.
   def writeToFile() = 
   {
      print("Please enter your filename : " )
      val line = Console.readLine;//read input from command line.
      
      println("Thanks, you just typed: " + line);
      var fname = line+".txt";
      val writer = new PrintWriter(new File(fname));
      writer.println("Hello Scala");
      writer.close();//close the file.
      
      readFromFile(fname);
   }
   
   //read from a file and print the lines.
   def readFromFile(fname: String) =
   {
      println("Following is the content read:" )

      //using the source class to open the file and print each line in a
      //foreach loop.
      //this is printing 1 char at a time from the file.
      //need a way to do buffered reader per line of string
      //not per char.
      Source.fromFile(fname).
      foreach
      {
        print
      };
   }
}

//this is a singelton obj in scala.
//anything wrapped with the "object" keyword, makes a singleton
//obj, therefore u can just call the methods and vars inside the obj 
//wrapper code.
/* NOTE
 Scala is more object-oriented than Java because in Scala we cannot have static members. 
 Instead, Scala has singleton objects. A singleton is a class that can have only one instance,
  i.e., object. You create singleton using the keyword object instead of class keyword. 
  Since you can't instantiate a singleton object, you can't pass 
  parameters to the primary constructor.
 */
object HelloWorld
{
  /* This is my first java program.  
    * This will print 'Hello World' as the output
    */
  def main(args: Array[String]) 
  {
     ScalaFileIO.writeToFile();

     println ("Apply method : " + ScalaExtractor.apply("Zara", "gmail.com"));
     println ("Unapply method : " + ScalaExtractor.unapply("Zara@gmail.com"));
     println ("Unapply method : " + ScalaExtractor.unapply("Zara Ali"));
    
    //testing scala exceptions.
    ScalaExceptions.testScalaExceptions();
    
    //scala regular expressions
    var pattern = "Scala".r;//implicit give u a regex obj back to do pattern matching.
    var str = "Scala is Scalable and cool";
    println(pattern findFirstIn str);//using pattern, us the findStr fnc.
    
    //create a regex with regex syntax
    pattern = new Regex("(S|s)cala");
    str = "Scala is scalable and cool";
      
    //the mkString allow to make a concat string from the results. of the regex execution.
    println((pattern findAllIn str).mkString(","))
    
    //this person obj will contain immutable fields since we have "val"" 
    val alice = new PatternMatching.Person("Alice", 25);
    
    //both of these are immutable, this is being generated by the compiler.
    var bob = new PatternMatching.Person("Bob", 32);
    var charlie = new PatternMatching.Person("Charlie", 32);
   
    for(person <- List(alice, bob, charlie)) 
    {
       //this is case class pattern matching.
       person match
       {
          case PatternMatching.Person("Alice", 25) => 
            println("Hi Alice!");
            
          case PatternMatching.Person("Bob", 32) => 
            println("Hi Bob!");
            
          case PatternMatching.Person(name, age) => 
            println("Age: " + age + " year, name: " + name + "?");
       }
    }
    
    println(PatternMatching.matchTest2("two"));
    println(PatternMatching.matchTest2("test"));
    println(PatternMatching.matchTest2(1));
    
    val p1 = new MyPoint(2, 3);
    val p2 = new MyPoint(2, 4);
    val p3 = new MyPoint(3, 3);

    println(p1.isNotEqual(p2));
    println(p1.isNotEqual(p3));
    println(p1.isNotEqual(2));
    
    val loc = new Location(10, 20, 15);

    // Move to a new location
    loc.move(10, 10, 5);
    
    //create point objs
    var pt = new Point(10, 20);

    // Move to a new location;
    //call method on pt objs.
    pt.move(10, 10);
    
    val x1 = MutableList(1,2,3,4);
    x1.+=:(100);//add item to list.
    
    for(jj <- x1)
    {
       println(jj);
    }
    
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