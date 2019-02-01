package design_patterns;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ManyPatterns
{
   public interface Shape
   {
      public void draw();
   }
   
   public interface AbstractFactory
   {
      public Shape getShape(String shapeName);
   }
   
   public static class Circle implements Shape
   {

      @Override
      public void draw()
      {
         System.out.println("Circle");
      }
      
   }
   
   public static class ShapeFactory implements AbstractFactory
   {
      public ManyPatterns.Shape getShape(String shapeName)
      {
         if("Circle".equalsIgnoreCase(shapeName))
         {
            return new ManyPatterns.Circle();
         }
         
         return null;
      }
   }
   
   public static class FactoryProducer
   {
      public static AbstractFactory getFactory(String factoryname)
      {
         if("ShapeFactory".equalsIgnoreCase(factoryname))
         {
            return new ShapeFactory();
         }
         
         return null;
      }
   }
}

class MySingleton
{
   private static MySingleton instance = new MySingleton();
   
   private MySingleton()
   {
      
   }
   
   public static MySingleton getIntance()
   {
      return instance;
   }
   
   public void printInstance()
   {
      System.out.println("MySingletonInstance");
   }
}

class Burger
{
   private String name;
   private double price;
   
   public Burger(String name, double price)
   {
      this.name = name;
      this.price = price;
   }
   
   public double price() 
   {
      return this.price;
   }

   
   public String name() 
   {
      return this.name;
   }
   
   public String packing()
   {
      return "wrapper";
   }
}

class Meal
{
   private List<Burger> items = new ArrayList<Burger>();  

   public void addItem(Burger item){
      items.add(item);
   }

   public void showCost(){
      double cost = 0.0f;
      
      for (Burger item : items) {
         cost += item.price();
      }     
      
      System.out.println(cost);
   }

   public void showItems(){
   
      for (Burger item : items) {
         System.out.print("Item : " + item.name());
         System.out.print(", Packing : " + item.packing());
         System.out.println(", Price : " + item.price());
      }     
   }  
}

class MealBuilder
{
   public Meal createBurgerMeals()
   {
      Meal meal = new Meal();
      
      meal.addItem(new Burger("chicken burger", 1.0));
      meal.addItem(new Burger("veggie burger", 1.0));
      meal.addItem(new Burger("beef burger", 1.0));
            
            
      return meal;
   }
   
}

class ShapeCloneable implements Cloneable
{
   private String name;
   private int age;
   
   public ShapeCloneable(String name, int age)
   {
      this.name = name;
      this.age = age;
   }
   
   public String getName()
   {
      return this.name;
      
   }
   
   public int getAge()
   {
      return this.age;
   }
   
   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   @Override
   public String toString()
   {
      return "ShapeCloneable [name=" + name + ", age=" + age + "]";
   }

   //override clone method from object class.
   @Override
   public Object clone()
   {
      Object clone = null;
      
      try {
         clone = super.clone();
         
      } catch (CloneNotSupportedException e) {
         e.printStackTrace();
      }
      
      return clone;
   }
}

class ShapeCache
{
   private HashMap<Integer,ShapeCloneable> my_cache = new HashMap<Integer,ShapeCloneable>();
   
   public ShapeCloneable getShape(int id)
   {
      ShapeCloneable rv = null;
      
      rv = my_cache.get(id);
      
      return (ShapeCloneable)rv.clone();
   }
   
   public void loadCache()
   {
      my_cache.put(1, new ShapeCloneable("Shape2", 100));
      my_cache.put(2, new ShapeCloneable("Shape3", 200));
      my_cache.put(3, new ShapeCloneable("Shape1", 300));
   }
}

interface DrawAPI 
{
   public void drawCircle(int radius, int x, int y);
}

class RedCircle implements DrawAPI
{

   @Override
   public void drawCircle(int radius, int x, int y)
   {
      System.out.println("rad = "+radius+", x = "+x+", y = "+y);
   }
   
}

class GreenCircle implements DrawAPI
{

   @Override
   public void drawCircle(int radius, int x, int y)
   {
      System.out.println("rad = "+radius+", x = "+x+", y = "+y);
   }
   
}

abstract class AbstractMyShape
{
   protected DrawAPI drawApi;
   
   public AbstractMyShape(DrawAPI api)
   {
      this.drawApi = api;
   }
   
   public abstract void draw();
}

class MyCircle extends AbstractMyShape
{
   private int radius;
   private int x;
   private int y;
   
   public MyCircle(int radius, int x, int y, DrawAPI api)
   {
      super(api);
      this.radius = radius;
      this.x = x;
      this.y = y;

   }

   @Override
   public void draw()
   {
      drawApi.drawCircle(radius, x, y);
   }
}

class Person 
{
   
   private String name;
   private String gender;
   private String maritalStatus;

   public Person(String name, String gender, String maritalStatus){
      this.name = name;
      this.gender = gender;
      this.maritalStatus = maritalStatus;    
   }

   public String getName() {
      return name;
   }
   public String getGender() {
      return gender;
   }
   public String getMaritalStatus() {
      return maritalStatus;
   }  
}

interface Criteria 
{
   public List<Person> meetCriteria(List<Person> persons);
}

class MaleCriteria implements Criteria
{

   @Override
   public List<Person> meetCriteria(List<Person> persons)
   {
      List<Person> rv = new ArrayList<Person>();
      
      for(Person person : persons)
      {
         if(person.getGender().equalsIgnoreCase("Male"))
         {
            rv.add(person);
         }
      }
      
      return rv;
            
   }   
}

class SingleCriteria implements Criteria
{

   @Override
   public List<Person> meetCriteria(List<Person> persons)
   {
      List<Person> rv = new ArrayList<Person>();
      
      for(Person person : persons)
      {
         if(person.getMaritalStatus().equalsIgnoreCase("Single"))
         {
            rv.add(person);
         }
      }
      
      return rv;
   }
}

class AndCriteria implements Criteria
{

   private Criteria one;
   private Criteria two;
   public AndCriteria(Criteria one, Criteria two)
   {
      this.one = one;
      this.two = two;
   }
   
   @Override
   public List<Person> meetCriteria(List<Person> persons)
   {
      List<Person> ans1 = one.meetCriteria(persons);
      return two.meetCriteria(ans1);
   }
   
}

class Employee {
   private String name;
   private String dept;
   private int salary;
   private List<Employee> subordinates;

   // constructor
   public Employee(String name,String dept, int sal) {
      this.name = name;
      this.dept = dept;
      this.salary = sal;
      subordinates = new ArrayList<Employee>();
   }

   public void add(Employee e) {
      subordinates.add(e);
   }

   public void remove(Employee e) {
      subordinates.remove(e);
   }

   public List<Employee> getSubordinates(){
     return subordinates;
   }
   
   public void printEmployeeTree()
   {
      System.out.println(this.toString());
      
      for(Employee emp : this.subordinates)
      {
         emp.printEmployeeTree();
      }
   }

   public String toString(){
      return ("Employee :[ Name : " + name + ", dept : " + dept + ", salary :" + salary+" ]");
   }   
}

interface MyShape
{
   public void draw();
}

class SampleRectangle implements MyShape {

   @Override
   public void draw() {
      System.out.println("Shape: Rectangle");
   }
}

class SampleCircle implements MyShape {

   @Override
   public void draw() {
      System.out.println("Shape: Circle");
   }
}

class RedShapeDecorator implements MyShape
{
   private MyShape decoratedShape;
   
   public RedShapeDecorator(MyShape decoratedShape) 
   {
      this.decoratedShape = decoratedShape;
   }

   @Override
   public void draw() 
   {
      decoratedShape.draw();         
      setRedBorder(decoratedShape);
   }

   private void setRedBorder(MyShape decoratedShape){
      System.out.println("Border Color: Red");
   }
}

class ShapeMakerFacade
{
   private SampleCircle circle;
   private SampleRectangle rect;
   
   public ShapeMakerFacade()
   {
      circle = new SampleCircle();
      rect = new SampleRectangle();
   }
   
   public void drawShapes()
   {
      circle.draw();
      rect.draw();
   }
}

class NewCircle implements MyShape {
   private String color;
   private int x;
   private int y;
   private int radius;

   public NewCircle(String color){
      this.color = color;     
   }

   public void setX(int x) {
      this.x = x;
   }

   public void setY(int y) {
      this.y = y;
   }

   public void setRadius(int radius) {
      this.radius = radius;
   }

   @Override
   public void draw() {
      System.out.println("Circle: Draw() [Color : " + color + ", x : " + x + ", y :" + y + ", radius :" + radius);
   }
}

class NewCircleFactory
{
   private static HashMap<String, NewCircle> map = new HashMap<String, NewCircle>();
   
   public static NewCircle getCircle(String color)
   {
      NewCircle rv = null;
      
      rv = map.get(color);
      
      if(rv == null)
      {
         rv = new NewCircle(color);
         map.put(color, rv);
         System.out.println("Creating circle of color : " + color);
      }
      
      return rv;
   }
         
}

interface Image {
   void display();
}

class RealImage implements Image {

   private String fileName;

   public RealImage(String fileName){
      this.fileName = fileName;
      loadFromDisk(fileName);
   }

   @Override
   public void display() {
      System.out.println("Displaying " + fileName);
   }

   private void loadFromDisk(String fileName){
      System.out.println("Loading " + fileName);
   }
}

class ProxyImage implements Image
{
   String filename;
   RealImage realImage;
   
   public ProxyImage(String filename)
   {
      this.filename = filename;
   }
   
   @Override
   public void display()
   {
      if(realImage == null)
      {
         realImage = new RealImage(filename);
      }
      
      realImage.display();
   }   
}

abstract class AbstractLogger 
{
   public static int INFO = 1;
   public static int DEBUG = 2;
   public static int ERROR = 3;

   protected int level;

   //next element in chain or responsibility
   protected AbstractLogger nextLogger;

   public void setNextLogger(AbstractLogger nextLogger)
   {
      this.nextLogger = nextLogger;
   }

   public void logMessage(int level, String message)
   {
      if(this.level <= level)
      {
         write(message);
      }
      
      if(nextLogger !=null)
      {
         nextLogger.logMessage(level, message);
      }
   }

   abstract protected void write(String message);
   
}

class ConsoleLogger extends AbstractLogger
{

   public ConsoleLogger(int level)
   {
      this.level = level;
   }

   @Override
   protected void write(String message) 
   {    
      System.out.println("Standard Console::Logger: " + message);
   }
}

class ErrorLogger extends AbstractLogger 
{

   public ErrorLogger(int level)
   {
      this.level = level;
   }

   @Override
   protected void write(String message) 
   {    
      System.out.println("Error Console::Logger: " + message);
   }
}

class FileLogger extends AbstractLogger 
{

   public FileLogger(int level)
   {
      this.level = level;
   }

   @Override
   protected void write(String message) 
   {    
      System.out.println("File::Logger: " + message);
   }
}

interface Order {
   void execute();
}

class Stock {
   
   private String name = "ABC";
   private int quantity = 10;

   public void buy(){
      System.out.println("Stock [ Name: "+name+",Quantity: " + quantity +" ] bought");
   }
   
   public void sell(){
      System.out.println("Stock [ Name: "+name+", Quantity: " + quantity +" ] sold");
   }
}

class BuyStock implements Order {
   private Stock abcStock;

   public BuyStock(Stock abcStock){
      this.abcStock = abcStock;
   }

   public void execute() {
      abcStock.buy();
   }
}

class SellStock implements Order {
   private Stock abcStock;

   public SellStock(Stock abcStock){
      this.abcStock = abcStock;
   }

   public void execute() {
      abcStock.sell();
   }
}

class Broker 
{
   private List<Order> orderList = new ArrayList<Order>(); 

   public void takeOrder(Order order)
   {
      orderList.add(order);      
   }

   public void placeOrders()
   {
      for (Order order : orderList) 
      {
         order.execute();
      }
      
      orderList.clear();
   }
}

interface Iterator 
{
   public boolean hasNext();
   public Object next();
}

interface Container 
{
   public Iterator getIterator();   
}

class NameRepository implements Container 
{
   public String names[] = {"Robert" , "John" ,"Julie" , "Lora"};

   @Override
   public Iterator getIterator()
   {
      return new NameIter();
   }

   private class NameIter implements Iterator
   {
      private int index;
      
      @Override
      public boolean hasNext()
      {
         if(index < names.length)
         {
            return true;
         }
         else
         {
            return false;
         }
      }

      @Override
      public Object next()
      {
         if(hasNext())
         {
            Object tmp = names[index++];
            return tmp;
         }
         
         return null;
      }
   }
}

class User {
   private String name;

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public User(String name){
      this.name  = name;
   }

   public void sendMessage(String message){
      ChatRoom.showMessage(this,message);
   }
}

class ChatRoom {
   public static void showMessage(User user, String message){
      System.out.println(new Date().toString() + " [" + user.getName() + "] : " + message);
   }
}

class Memento 
{
   private String state;

   public Memento(String state){
      this.state = state;
   }

   public String getState(){
      return state;
   }  
}

class Originator 
{
   private String state;

   public void setState(String state){
      this.state = state;
   }

   public String getState(){
      return state;
   }

   public Memento saveStateToMemento(){
      return new Memento(state);
   }

   public void getStateFromMemento(Memento memento){
      state = memento.getState();
   }
}

class CareTaker 
{
   private List<Memento> mementoList = new ArrayList<Memento>();

   public void add(Memento state){
      mementoList.add(state);
   }

   public Memento get(int index){
      return mementoList.get(index);
   }
}

abstract class Observer 
{
   protected Subject subject;
   public abstract void update();
}

class Subject 
{   
   private List<Observer> observers = new ArrayList<Observer>();
   private int state;

   public int getState() {
      return state;
   }

   public void setState(int state) {
      this.state = state;
      notifyAllObservers();
   }

   public void attach(Observer observer){
      observers.add(observer);      
   }

   public void notifyAllObservers(){
      for (Observer observer : observers) {
         observer.update();
      }
   }  
}

class BinaryObserver extends Observer
{
   public BinaryObserver(Subject subject)
   {
      this.subject = subject;
      this.subject.attach(this);
   }

   @Override
   public void update() {
      System.out.println( "Binary String: " + Integer.toBinaryString( subject.getState() ) ); 
   }
}

class OctalObserver extends Observer
{
   public OctalObserver(Subject subject){
      this.subject = subject;
      this.subject.attach(this);
   }

   @Override
   public void update() {
     System.out.println( "Octal String: " + Integer.toOctalString( subject.getState() ) ); 
   }
}

class HexaObserver extends Observer{

   public HexaObserver(Subject subject){
      this.subject = subject;
      this.subject.attach(this);
   }

   @Override
   public void update() {
      System.out.println( "Hex String: " + Integer.toHexString( subject.getState() ).toUpperCase() ); 
   }
}

interface State 
{
   public void doAction(Context context);
}

class Context 
{
   private State state;

   public Context(){
      state = null;
   }

   public void setState(State state){
      this.state = state;     
   }

   public State getState(){
      return state;
   }
}

class StartState implements State 
{

   public void doAction(Context context) 
   {
      System.out.println("Player is in start state");
      context.setState(this); 
   }

   public String toString()
   {
      return "Start State";
   }
}

class StopState implements State 
{

   public void doAction(Context context) 
   {
      System.out.println("Player is in stop state");
      context.setState(this); 
   }

   public String toString(){
      return "Stop State";
   }
}

interface Strategy {
   public int doOperation(int num1, int num2);
}

class OperationAdd implements Strategy{
   @Override
   public int doOperation(int num1, int num2) {
      return num1 + num2;
   }
}

class OperationSubstract implements Strategy{
   @Override
   public int doOperation(int num1, int num2) {
      return num1 - num2;
   }
}

class OperationMultiply implements Strategy{
   @Override
   public int doOperation(int num1, int num2) {
      return num1 * num2;
   }
}

class StrategyContext {
   private Strategy strategy;

   public StrategyContext(Strategy strategy){
      this.strategy = strategy;
   }

   public int executeStrategy(int num1, int num2){
      return strategy.doOperation(num1, num2);
   }
}

abstract class Game {
   abstract void initialize();
   abstract void startPlay();
   abstract void endPlay();

   //template method
   public final void play(){

      //initialize the game
      initialize();

      //start game
      startPlay();

      //end game
      endPlay();
   }
}

class Cricket extends Game {

   @Override
   void endPlay() {
      System.out.println("Cricket Game Finished!");
   }

   @Override
   void initialize() {
      System.out.println("Cricket Game Initialized! Start playing.");
   }

   @Override
   void startPlay() {
      System.out.println("Cricket Game Started. Enjoy the game!");
   }
}

class Football extends Game {

   @Override
   void endPlay() {
      System.out.println("Football Game Finished!");
   }

   @Override
   void initialize() {
      System.out.println("Football Game Initialized! Start playing.");
   }

   @Override
   void startPlay() {
      System.out.println("Football Game Started. Enjoy the game!");
   }

}

interface ComputerPart {
   public void accept(ComputerPartVisitor computerPartVisitor);
}

interface ComputerPartVisitor {
   public void visit(Computer computer);
   public void visit(Mouse mouse);
   public void visit(Keyboard keyboard);
   public void visit(Monitor monitor);
}

class Keyboard implements ComputerPart {

   @Override
   public void accept(ComputerPartVisitor computerPartVisitor) {
      computerPartVisitor.visit(this);
   }
}
class Monitor implements ComputerPart {

   @Override
   public void accept(ComputerPartVisitor computerPartVisitor) {
      computerPartVisitor.visit(this);
   }
}
class Mouse implements ComputerPart {

   @Override
   public void accept(ComputerPartVisitor computerPartVisitor) {
      computerPartVisitor.visit(this);
   }
}

class Computer implements ComputerPart {
   
   ComputerPart[] parts;

   public Computer(){
      parts = new ComputerPart[] {new Mouse(), new Keyboard(), new Monitor()};      
   } 


   @Override
   public void accept(ComputerPartVisitor computerPartVisitor) {
      for (int i = 0; i < parts.length; i++) {
         parts[i].accept(computerPartVisitor);
      }
      computerPartVisitor.visit(this);
   }
}
class ComputerPartDisplayVisitor implements ComputerPartVisitor {

   @Override
   public void visit(Computer computer) {
      System.out.println("Displaying Computer.");
   }

   @Override
   public void visit(Mouse mouse) {
      System.out.println("Displaying Mouse.");
   }

   @Override
   public void visit(Keyboard keyboard) {
      System.out.println("Displaying Keyboard.");
   }

   @Override
   public void visit(Monitor monitor) {
      System.out.println("Displaying Monitor.");
   }
}