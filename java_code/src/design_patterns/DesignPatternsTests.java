package design_patterns;

import java.util.ArrayList;
import java.util.List;

import design_patterns.ManyPatterns.AbstractFactory;
import design_patterns.ManyPatterns.Circle;

public class DesignPatternsTests
{

   public static void printPersons(List<Person> persons)
   {
      for (Person person : persons) 
      {
         System.out.println("Person : [ Name : " + person.getName() + ", Gender : " + person.getGender() + ", Marital Status : " + person.getMaritalStatus() + " ]");
      }
   }
   
   private static String getRandomColor(String [] colors) {
      return colors[(int)(Math.random()*colors.length)];
   }
   private static int getRandomX() {
      return (int)(Math.random()*100 );
   }
   private static int getRandomY() {
      return (int)(Math.random()*100);
   }
   
   private static AbstractLogger getChainOfLoggers()
   {
      AbstractLogger errorLogger = new ErrorLogger(AbstractLogger.ERROR);
      AbstractLogger fileLogger = new FileLogger(AbstractLogger.DEBUG);
      AbstractLogger consoleLogger = new ConsoleLogger(AbstractLogger.INFO);

      errorLogger.setNextLogger(fileLogger);
      fileLogger.setNextLogger(consoleLogger);

      return errorLogger;  
   }
   
   public static void main(String[] args)
   {
      AbstractFactory af = ManyPatterns.FactoryProducer.getFactory("ShapeFactory");
      Circle circle = (Circle)af.getShape("Circle");
      circle.draw();
      
      MySingleton.getIntance().printInstance();
      
      MealBuilder mb = new MealBuilder();
      Meal meal = mb.createBurgerMeals();
      meal.showItems();
      meal.showCost();
      
      ShapeCache sc = new ShapeCache();
      
      sc.loadCache();
      
      ShapeCloneable my_clone = sc.getShape(2);      
      System.out.println(my_clone);
      
      AbstractMyShape redCircle = new MyCircle(100,10,10, new RedCircle());
      AbstractMyShape greenCircle = new MyCircle(100,10,10, new GreenCircle());
      greenCircle.draw();
      redCircle.draw();
      
      List<Person> persons = new ArrayList<Person>();

      persons.add(new Person("Robert","Male", "Single"));
      persons.add(new Person("John", "Male", "Married"));
      persons.add(new Person("Laura", "Female", "Married"));
      persons.add(new Person("Diana", "Female", "Single"));
      persons.add(new Person("Mike", "Male", "Single"));
      persons.add(new Person("Bobby", "Male", "Single"));

      Criteria male = new MaleCriteria();
      Criteria single = new SingleCriteria();
      Criteria singleMale = new AndCriteria(single, male);
      
      System.out.println("Males: ");
      printPersons(male.meetCriteria(persons));
      
      System.out.println("Single: ");
      printPersons(single.meetCriteria(persons));

      System.out.println("\nSingle Males: ");
      printPersons(singleMale.meetCriteria(persons));

      Employee CEO = new Employee("John","CEO", 30000);
      Employee headSales = new Employee("Robert","Head Sales", 20000);
      Employee headMarketing = new Employee("Michel","Head Marketing", 20000);
      Employee clerk1 = new Employee("Laura","Marketing", 10000);
      Employee clerk2 = new Employee("Bob","Marketing", 10000);
      Employee salesExecutive1 = new Employee("Richard","Sales", 10000);
      Employee salesExecutive2 = new Employee("Rob","Sales", 10000);

      CEO.add(headSales);
      CEO.add(headMarketing);

      headSales.add(salesExecutive1);
      headSales.add(salesExecutive2);

      headMarketing.add(clerk1);
      headMarketing.add(clerk2);
      
      System.out.println();
      
      //print tree
      CEO.printEmployeeTree();
      
      MyShape my_circle = new SampleCircle();
      MyShape my_redCircle = new RedShapeDecorator(new SampleCircle());
      MyShape my_redRectangle = new RedShapeDecorator(new SampleRectangle());
      
      System.out.println("Circle with normal border");
      my_circle.draw();

      System.out.println("\nCircle of red border");
      my_redCircle.draw();

      System.out.println("\nRectangle of red border");
      my_redRectangle.draw();
      
      System.out.println();
      ShapeMakerFacade smf = new ShapeMakerFacade();
      smf.drawShapes();
            

      String colors[] = { "Red", "Green", "Blue", "White", "Black" };
    
      for(int i=0; i < 20; ++i) 
      {
         NewCircle mycircle = (NewCircle)NewCircleFactory.getCircle(getRandomColor(colors));
         mycircle.setX(getRandomX());
         mycircle.setY(getRandomY());
         mycircle.setRadius(100);
         mycircle.draw();
      }
      
      Image image = new ProxyImage("test_10mb.jpg");

      //image will be loaded from disk
      image.display(); 
      System.out.println("");
      
      //image will not be loaded from disk
      image.display();  
      
      //chain of responsibilty
      AbstractLogger loggerChain = getChainOfLoggers();

      loggerChain.logMessage(AbstractLogger.INFO, 
         "This is an information.");

      loggerChain.logMessage(AbstractLogger.DEBUG, 
         "This is an debug level information.");

      loggerChain.logMessage(AbstractLogger.ERROR, 
         "This is an error information.");
      
      Stock abcStock = new Stock();

      //command pattern
      BuyStock buyStockOrder = new BuyStock(abcStock);
      SellStock sellStockOrder = new SellStock(abcStock);

      Broker broker = new Broker();
      broker.takeOrder(buyStockOrder);
      broker.takeOrder(sellStockOrder);

      broker.placeOrders();
      
      //iterator
      NameRepository nm = new NameRepository();
      for(Iterator iter = nm.getIterator(); iter.hasNext(); )
      {
         String tmp = (String)iter.next();
         System.out.println(tmp);
      }
      
      //mediator pattern
      User robert = new User("Robert");
      User john = new User("John");

      robert.sendMessage("Hi! John!");
      john.sendMessage("Hello! Robert!");
      
      //memento pattern
      Originator originator = new Originator();
      CareTaker careTaker = new CareTaker();
      
      originator.setState("State #1");
      originator.setState("State #2");
      careTaker.add(originator.saveStateToMemento());
      
      originator.setState("State #3");
      careTaker.add(originator.saveStateToMemento());
      
      originator.setState("State #4");
      System.out.println("Current State: " + originator.getState());    
      
      originator.getStateFromMemento(careTaker.get(0));
      System.out.println("First saved State: " + originator.getState());
      originator.getStateFromMemento(careTaker.get(1));
      System.out.println("Second saved State: " + originator.getState());
      
      //observer
      Subject subject = new Subject();

      new HexaObserver(subject);
      new OctalObserver(subject);
      new BinaryObserver(subject);

      System.out.println("First state change: 15");   
      subject.setState(15);
      System.out.println("Second state change: 10");  
      subject.setState(10);
      
      //state pattern.
      Context context = new Context();

      StartState startState = new StartState();
      startState.doAction(context);

      System.out.println(context.getState().toString());

      StopState stopState = new StopState();
      stopState.doAction(context);

      System.out.println(context.getState().toString());
      
      //strategy
      StrategyContext mycontext = new StrategyContext(new OperationAdd());    
      System.out.println("10 + 5 = " + mycontext.executeStrategy(10, 5));

      mycontext = new StrategyContext(new OperationSubstract());      
      System.out.println("10 - 5 = " + mycontext.executeStrategy(10, 5));

      mycontext = new StrategyContext(new OperationMultiply());    
      System.out.println("10 * 5 = " + mycontext.executeStrategy(10, 5));
      
      //template
      Game game = new Cricket();
      game.play();
      System.out.println();
      game = new Football();
      game.play();
      
      //visitor
      ComputerPart computer = new Computer();
      computer.accept(new ComputerPartDisplayVisitor());
   }
}
