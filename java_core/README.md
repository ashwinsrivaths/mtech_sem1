# java
- when jvm encounters a object which is not loaded, it invokes the class loader
    - class loader reads the byte code and loads it to the heap as a Class class object (Class is the name of the class of which the object is being created)
    - class loading happens once per class per program execution
    - class class objects are never garbage collected and JVM maintains reference to them till program execution is completed (properly or eceptionally (all threads) orr one of the thread calls System.exit())
    - static variables and all byte code for methods reside in class class object
    - class loading happens for the class first time it is used
    - it is destroyed when the program ends and heap memory is returned to the os
- 
## .equals and equality
- o1 == o2 => this is going to compare the object reference and check if the variables are pointing to the same object => this is comparison by identity check
- Comparison by equality check
  - comparison done by state
  - class designer must override the equals method ensure the comparison takes place properly
    - by default the equals method of the Object class does identity check (are the reference variables pointing to the same object or not)
- EVERY CLASS MUST OVERRIDE EQUALS IF IT HAS INSTANCE VARIABLES else silent failure will occur
- collections to work correctly depends on the user to override the equals method for search method

## .hashCode()
- equal object (same state) must return same hash code
- use object state to generate hashCode
- String class designer has overridden .hashCode function beautifully and any object state can be converted to string on overriding .toString method and .hashCode can be called on this string


## API
- a service that is offered
  - Class user is offered a set of methods
  - Class user is offered a interface of the class to code against
  - object class returns random hash code and NEEDS to be overridden (otherwise every collection or class calling hash code will fail)
  - all hash implementations use hash code to assign buckets


## Best Practices

1) Best Practices a class designer should follow
   1) Create 1 class definition per .java file with the same name as the file
   2) OOAD steps
      1) identify objects
      2) per object identify "has a" (instance variable) and "is a" (extends class) relationships and behaviors (methods)
      3) for methods identify multiplicity (1-1, 1-n). for 1-n create a Collection typed to the inst var and return it
   3) understand how state impacts behavior and vice versa
      1) because state affects behavior, hide direct access of state from user
      2) mark state as private
      3) create setters, getters and parameterized constructor. Only set state on validation of inputs and raise exceptions(IllegalArgumentException) if bad inputs are passes
      4) per method if method behavior depends on invokers input 
         1) apply input validations and throw IllegalArgumentException if bad inputs are passes
         2) apply business validations and per business validation
            1) create a exception class with proper exception name
            2) add throws declaration to the method header (enforced by compiler)
            3) user forced to handle the exception or throw it
         3) Only if all validations succeed then perform business logic
   4) Test the class using a tester class (box unit testing)
   5) per class generate javadoc (javadoc *.java)
   6) Always for param reference variable, instance reference variable and local reference -> code to parent reference!!!
   7) Override equals(), hashCode(), toString() methods inherited from Object class
   8) Only use try-catch and catch the exceptions you know how to handle
      1) use specific catch block for the exceptions that you know how to handle
      2) use generic catch block for user facing methods to catch all failures(and log them for debugging)
      3) have a finally block to release third party resources only
      4) PREVENT silent failure by NOT returning in finally block, having empty catch, very generic catch in methods and suppressing failure not related to the method etc
   9)  To implement natural ordering, implement the Comparable interface
2) Best practices the class user should follow
   1) embed all user facing methods in generic try-catch and print/log the stack trace for debugging
   2) read the javadoc of the class being used
      1) understand the input and outputs to the methods and the exceptions raised 
      2) if business exceptions are raised, then handle them in try catch and give appropriate message to user
      3) if we do not know how to handle the business exception, then throw it
   3) for testing if two objects are equal in state, use o1.equals(o2) for state comparison
   4) for testing if a obj is smaller/bigger 
      1) check if the designer has implemented Comparable
      2) if yes, use o1.compareTo(o2) => +ve => o1>o2
      3) else pass a Comparator implemented class object myComparatorObject.compare(o1,o2); => +ve => o1>o2
   5) for many elements use Collection based on requirement
   6) code to parent reference always for all reference variables
   7) If an element is being added to a HashSet or any hash based implementation
      1) check if designer has overridden both .equals() and .hashCode()
      2) Never add StringBuilder to collections as the above methods are not overridden by designer
   8) Create as many objects with as many reference variables as needed but deference the reference variables once the object is no longer needed to make it eligible for garbage collection
   9) CODE AT LEAST 10 PER TOPIC
3)  ensure all access to mutable state is synchronized
    1)  we must ensure that our class is thread safe (when ever a object of our class is accessed by multiple threads of execution, there are chances of data corruption and hence synchronization is necessary)
    2)  ensure that the ordering of obtaining of locks is in same order to prevent deadlock when having nested synchronization blocks


4) slNo integer PRIMARY KEY auto_increment to be implemented as the first column in all tables



- Hide the state and expose setters and getters and parameterized constructor 
  - validating inputs and ensuring object consistency 
  - IllegalArgumentException thrown for bad input
- behaviors are implemented as instance methods
  - business validation fails
    - checked exception raised
    - custom exception is a class that extends exception with a descriptive name
    - mark throws clause
- Always code to interface reference to have generic polymorphic code
- IT IS NON NEGOTIABLE: Always override .equals, .hashCode() and toString  (return .hashCode can be called on toString)
- Always use generics or type safe collections
- implement comparable interface
- implement thread safety for access to all instance and static mutable variables

## Strings
- StringBuilder is thread safe and mutable
  - StringBuilder sb = new StringBuilder("test sb");
  - .append("abc")
  - .reverse()
  - .toString()
  - StringBuilder class designer has not overridden .equals method and hence equality check will not work
  - convert sb toString() and then use .equals equality check
  - hence never put sb to collections beacuse
    - sb .equals is not overriden by designer
    - sb is mutable and in maps keys MUST not be mutable
- ALWAYS USE .equals(obj) to compare any object including strings to compare state


## Date
- SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
- Date myBirthday = sdf.parse("01/12/1998");
- long time = myBirthday.getTime();
- String bDay = sdf.format(d);

## functions
- in function overloading, if there is no exact match and there are two methods that are at the same level when upcasted, error is thrown

## encapsulation
- reusability of code
- easy maintenance
- easy to address complexity


# Optional modifiers

- constructor can not be marked with optional modifiers
- abstract -> method, class
- static -> method, variable, inner class, initializer
- final -> class, method, variable
- synchronized -> method, code block
- native -> method


### static
- static variables and all byte code for methods reside in class class object
- can be used with: -
    - method 
        - class scoped method
        - non behavioral method 
        - Best Practice: always call static methods using class name and never use reference variable
          - the reference name is internally replaced with class name by jvm during compilation
          - readability reduces and it is not best practice to use variable name for static calls
          - ALWAYS USE BEST PRACTICE
        - used for utility functionality (methods that are only dependent on input and not state of object)
        - static methods cant access non static members without creating object
    - variable 
        - belongs to the class and only one copy per class
        - stored in class class object
        - initialized like instance variables (local variables are not initialized )
    - initializer- there are 3 types of initializer shown in execution order (lower members in the execution order can access higher members as they will be available at the time of execution)
        - static field initializer- executed first when the class is loaded for static variables
            - static int a = 10; //executed when the class class object is loaded
            - static double d = Math.random(); 
        - static initializer- executed when the class is loaded
            - static {a=5;}
            - executed once per class
        - instance field initializer- instance variables initialized inside class
            - int x = 20; //executed when the object is created 
        - instance initializer-
            - {} code block coded inside a class outside other members
            - can access instance members and static members
    - inner class


### final modifier
- a final class can not be extended
- a final method can not be overridden
- a final variable's value can not be changed


# Inheritance and overriding
- i don't use inheritance to reuse code
- whatever the parent can do the child can do
- overridden methods can widen access specifier (not reduce access specifier)
  - private
  - package
  - protected
  - public
- only visible methods are inherited
  - hance no question of overriding private methods but new method with same signature can be created
- Covariance: return datatype can be changed to subtype while overriding 
- any modifier can be used
- overridden method can not throw more checked exceptions than the parent method




- composition: used to reuse code
    - is a feature where i have an object obj2 reference inside my class. Now i can call obj2's methods inside my class and reuse code


- run time polymorphism
    - to write polymorphism code, we must code to parent interface and let jvm pick implementation in run time
    - enhanced implementation is picked in runtime

    - when you write the code if you dont know where the object referance is pointing to, then the code is polymorphic
    - where and all can you use polymorphic code
        - to accept a parameter
            - test(Animal A)
            - test(Vehicle V)
            - where Animal and Vehicle are interfaces
        - accept a generic parameter from a function
            - Animal shop("animal name")
        - Instance variable
            - instance variable can be coded to parent object and can be initialized to any child object
            - clearly we don't want to initialize it and user of the class should be allowed to initialize the variable (through setter or parameterized constructor)
        - local reference variable
    - polymorphic code is generic and flexible as its implementation is not fixed and changes during runtime
    - IMPORTANT: only public or accessible members of a class are inherited although all the parent classes are created in an onion manner. just cant be accessed
    - constructor is not inherited


- when ever a child object is created the parent initializers and constructors must be fired
    - class loader is called and loads all classes starting from parent class
    - 
    - Object class static field initializer
    - Object class static initializer
    - Hippo class static field initializer
    - Hippo class static  initializer
    - MarriHippo class static field initializer
    - MarriHippo class static instance initializer
    - MarriMarriHippo class static field initializer (called if this is the first object)
    - MarriMarriHippo class static instance initializer(if this is the first call to marri hippo then this is definitely the first call to this class)


    - JVM calculates memory needed and allocates it

    - Object class instance field initializer
    - Object class instance initializer
    - Object class constructor

    - Hippo class instance field initializer
    - Hippo class instance initializer
    - Hippo class constructor

    - MarriHippo class instance field initializer
    - MarriHippo class instance initializer
    - MarriHippo class constructor

    - MarriMarriHippo class instance field initializer
    - MarriMarriHippo class instance initializer
    - MarriMarriHippo class constructor

- constructor execution == state creation
- compilation fails if parent obj has only parameterized constructor and child class does not explicitly call super(parameter);
 

- private methods can not be overridden as it can not be inherited but new method with same name can be created

- compiler adds (if not there) "super();" as the first statement of every constructor
    - super(); calls the parents constructor
    - constructor chaining: process by which jvm executes multiple constructors of the object and its parents to create one object
    - since super(); is the first statement of every constructor, the Object class state is created first
- Object creation happens from inside to outside



## super
- access direct parent class AND CAN NOT ACCESS PRIVATE MEMBERS OF THE CLASS
- Uses: 
    - constructor chaining- child constructor to call the parent constructor, super(); must be the first statement of every constructor (only one call per constructor)
    - Access pre-overridden Methods: enables subclass to call parent's original method implementation rather than the overridden version
    - removes ambiguity when a subclass variable shadows a parent variable
        - note that a child class can have the same public instance variable name as the parent with different datatype.



## Down casting referance variable
- done to access subtype specific functionality
    - implicit up casting
        - Animal a = new Dog();
    - explicit down casting
        - Animal a = new Dog();
        - if( a instanceof Dog){
        - Dog d = (Dog) a; }// explicit down casting





## Interfaces
- implement multiple Is-A
- to hold constants
- to represent markers
- to create most generic flexible polymorphic code
    - objects from different class heighrachies can be passed
- interface members are by default marked public unlike classes where the default is packaged

### java 8 Default Keyword
- Default: Keyword modifier added in java 8
  - until java 8 any method added to an interfaces must be marked abstract
  - Definition: A non-abstract method within an interface that includes a body, marked with the default keyword.
  - Purpose: Enables interface evolution by providing a standard implementation that classes can inherit or override.
    - If i have a interface and I want to enhance it and add a new abstract method, then all the 1000 classes that extends it will break
    - this allows us to provide a dummy implementation in the interface that the class designer are expected to override
    - static methods can also be added to interfaces now using default (static methods can not be marked abstract)
  - Conflict Resolution: If a class implements multiple interfaces with the same default method signature, it must override the method to resolve the ambiguity.
  - default methods cant be marked final in interfaces as it is expected that the class user overrides the method
- Covariance: return datatype can be changed to subtype while overriding //introduced in jdk 5
  - 



# Access Specifiers and packages
- overridden methods can widen access specifier (not reduce access specifier)
  - private
  - package
  - protected
  - public
- Class and Interface and be marked only public and package
- package is default inside classes
- package in java is used to create a grouping of related classes
- javac -d ../classes A.java
- RULE: Whenever a java program is complied, the class file must be stored under a folder named with the package name
- BestPractice: all classes must belong to a package


- package names resolves naming conflict across all classes
    - for unique package name, we follow reverse domain name naming convention
    - class users to use classes from different packages will need to use fully qualified class name or import it
        - packageNmae.className
        - import com.uttara.ashwin.zoo.Animal;
        - if I am using 2 classes with same name from different packages, then no choice but using fully qualified class name
        - use of import is soo that I do not need to use fully qualified class name and there is no other use (no linking is happening and size is not increasing)
        - package zoo is different from package zoo.test and package scoped members of zoo can not be accessed by classes in zoo.test

- protected is packaged access + access to all subclasses



# exceptions
- have a polymorphic catch for user facing method like main.
- do not have polymorphic catch for non user facing methods.
    - the catch block is to handle error. Only have catch errors that you can handle.
    - code failing is bad
    - code failing is better than code not failing and not performing its job - SILENT FAILURE
    - You should not have empty catch - SILENT FAILURE
- In catch block
    - log stack trace to file
    - print generic message to customer
- In finally code block
    - release 3rd party resources and cleanup
    - finally is executed no matter what
    - returning in finally causes silent failure NEVER RETURN IN FINALLY
        - finally return is supersedes the return in normal code block
        - finally return is supersedes the return in catch block
        - finally return is supersedes the thrown exception
    - FINALLY IS USED ONLY WHEN 3RD PARTY RESOURCES ARE USED IN TRY AND IT NEEDS TO BE RELEASED
    - If there is exception in finally the finally exception is the only exception that is thrown
    - There can only be one reason for returning
    - only if System.exit(0); is executed then finally is not executed
- throw <Throwable reference>
- Forcing caller to catch exception
    - adding the throws clause in the method header
    - this is done for checked exceptions where we dont want to blame the invoker and the user has given bad inputs
    - compiler compels the invoker of the method to:
     - HANDLE: catch this exception 
     - DUCK: throws the same exception 
- runtime exceptions happen due to bad code
- checked Exceptions - non runtime exceptions
  - all Throwables happens at runtime
  - checked exceptions are checked by the compiler
    - if throws has been marked in the function header for the used checked exception
    - if invoker of this function handles it
  - no checking by compiler for unchecked exceptions and developer is expected to change code
  - checked exceptions represent business or environment failure that the user should correct


# ecplise
- ctrl + " " => code complete
- ctrl + 1 
- ctrl + shift + o => import dependencies
- source
- workspace is a folder where all the projects are stored
- First thing to do when eclipse is launched is create a new project => File->New->Project
- java -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=8000 -jar ./target/collections-1.0.0.jar asdf asdf wer uwrtywywre




# Maven
- It is a build automation tool
- while sharing b to b is recommended to share only .class file
- zip of all .class files into one folder is called build 
  - .jar => java archive => .class files
  - .war => web archive =. .class files + Web Resources
  - .ear => Enterprise Archive
- library: collection of .jar files
- GAV
  - group id: organization that has built the framework
  - artifact id: project or jar name
  - version: 5.3.12 => MajorVersion.MinorVersion.BugFixes
- Maven server or central repository or .m1 contains all the libraries and dependencies in one place
- .m2 or local repository or our system takes the jar files from .m1
- pom.xml captures all dependencies
  - this is requested to the m1 repo
  - the m1 repo responds with the relevant .jar files that is stored in the .m2 or local repo
- dependency hierarchy is a tree of dependencies
  - for every dependency we add, its dependencies are added creating a tree of dependencies
  - exclusions allows us to exclude the dependency that we do not need
- <scope> tag => when the dependency will be available
  - compile (default): From compile time till execution
  - runtime: only at runtime
  - provided: Given by server/container - coming from docker container
  - test: only for unit testing
  - system: from local system (our .jar file that is not in m1 repo) (<systemPath> tag should be provided)
- <properties> tag is used for version only
  - define: <key>value</key> inside the property tag
  - use: <version>${key}</version>
- BOM stands for Bill Of Materials. A BOM is a special kind of POM that is used to control the versions of a project’s dependencies and provide a central place to define and update those versions. BOM provides the flexibility to add a dependency to our module without worrying about the version that we should depend on.
- Transitive Dependencies
  - Maven can discover the libraries that are needed by our own dependencies in our pom.xml and includes them automatically. There’s no limit to the number of dependency levels that the libraries are gathered from.
  - The conflict here comes when 2 dependencies refer to different versions of a specific artifact. Which one will be included by Maven?
  - The answer here is the “nearest definition”. This means that the version used will be the closest one to our project in the tree of dependencies. This is called dependency mediation.
- parent pom
  - contains all necessary dependencies
  - child poms can inherit from the parent pom
- mvn clean package
- java -jar target/test-1.0.0.jar



# junit
- Used by developer to write test cases
- maven-surefire-plugin is used to test only a subset of test cases per environment
  - <plugin>
    - <artifactId>maven-surefire-plugin</artifactId>
    - <configuration>
      - <groups>dev</groups> // the test cases that need to be executed
      - <excludedGroups>QA</excludedGroups> // the test cases that should be excluded
    - </configuration>
  - </plugin>
- Bae64.getDecoder().decode("encrypted pwd")
  - when saving passwords, to ensure that anyone does not just see it and understand the password, it is encoded in base 64
- if multiple reference variables are pointing to the same object then => assertSame
- if multiple reference variables are pointing to multiple objects with the same content then => assertEquals



# collections library and data structures
- collections is a container of elements
- collections holds Object references

## Array data structure:
- Demerits
  - preallocation of memory needed
  - methods not provided
  - slow insertion and deletion and other manipulation operations
  - slow search O(n)
- Features
  - continuous memory allowing constant time super fast random indexed access
  - only one datatype to ensure same sized boxes
  - fixed size

## interface Collection (behaviors)
- OPERATIONS ON SELF
  - boolean add(Object e)
  - boolean contains(Object e)
  - boolean remove(Object e) => removes only the first occurrence of the element like this element
  - int size()
  - Iterator iterator() => retrieve one one element at a time
  - void clear() => collection becomes empty(all the elements are made to default values)
  - boolean isEmpty()
- TWO COLLECTIONS OPERATIONS
  - boolean addAll(Collection c) => add all elements from the passed collection to the collection
  - boolean removeAll(Collection c) => all elements from the passed collection will be searched and removed
  - boolean retainAll(Collection c) => only elements from the passed collection will be retained in the collection
  - boolean containsAll(Collection c) => checks if all elements from the passed collection is present in my collection or not
  - Object[] toArray() => converts the collection to array
- Collections.sort(myCollection) //sorts based on natural order (class designer must have implemented comparable interface and provided compareTo method implementation)
- MyComparator compr = new MyComparator()
- Colletions.sort(myCollection, compr) // compares based on provided Comparator. MyComparator class must implement Comparator interface
## interface List extends Collections
- position backed ordered collection offering indexed access
  - ordered implies that on inserting, the retrieval happens in a particular order
- allows duplicates
- Additional Methods in List interface are position backed methods
  - boolean add(int pos, Object ele) // overloading
  - Object remove(int pos) //overloading
  - int indexOf(Object ele)
  - Object get(int pos)
- Implementations
  - ArrayList
      - ArrayList has super fast random access
      - insert and delete operations are slow
      - pre allocation of memory needed STATIC DATA STRUCTURE
      - slow search
      - add -> o(1)
      - get -> o(1)
      - insert -> o(n - m)
      - contains -> o(n)
      - remove by search -> n-m + m = n
      - remove by position -> n-m
  - LinkedList
      - Doubly linked list of nodes
      - pre allocation of memory not needed and a big advantage when dealing with large data sets DYNAMIC DATA STRUCTURE
      - LinkedList has super fast insert and delete but slower random access
      - add -> o(1)
      - get -> del(n/2)
      - insert -> del(n - m)
      - contains -> o(n)
      - remove by search -> n-m => o(n/2)
      - remove by position -> del(n-m)
  - CopyOnWriteList 
## interface stack
- push(Object ele)
- Object pop()
- boolean isEmpty()
## interface Set extends Collections
- no duplicates
- unordered and no position
- Implementations
  - HashSet - hashed bucket of nodes
    - unordered retrieval
    - fastest set
  - LinkedHashSet - linked hashed bucket of nodes
    - insertion ordered retrieval
  - TreeSet - balanced binary tree of nodes
    - sorted order retrieval
    - slowest set

## interface Queue extends Collections
- Ordered retrieval
- Implementations
  - LinkedList => IMPLEMENTS BOTH List and Queue interfaces
  - PriorityQueue
  - Dequeue
  - BlockingQueue


## interface Map 
- in parallel with Collections and does not extend it. It deals with elements and not element
- container of entries (key value pair)
- Implementation
  - HashMap
  - LinkedHashMap
  - TreeMap
  - 

# generics
- We want to make teh generic type specefic
- If the collection is generic and not type safe,
  - ArrayList col = new ArrayList();
  - for(Object o: col){
    - if(o instanceof Integer){
      - Integer i = (Integer) o;
    - }
  - }
- ArrayList<Integer> col = new ArrayList<Integer>();
  - now all elements in the col are of type Integer
  - I can add child class of the type as usual
  - the generic on both sides should be same and cant be a child object

## public interface Comparable 
- for using tree based implementation or compare any two objects the class must implement Comparable
  - public int compareTo(Object o)
    - if o instanceof MyClass
      - MyClass cls = (MyClass) o
    - else
      - throw new IllegalArgumentException("only MyClass objects can be compared)
- p1.compareTo(p2)
  - +ve => p1>p2
  - -ve => p1<p2
  - 0 => p1 == p2
- This is implemented by class designer

## public interface Comparator
- it is a interface implemented by the user of the class
- create a seperate class and implement this interface
- implement the public int compare(Object o1, Object o2) (same as the compareTo method)
- pass a object of this class to the tree set
- Slc slc = new Slc(); // This is my class implementing the Comparator interface.
- Set ts = new TreeSet(slc) // this will use our custom ordering and not natural ordering or the Comparable compareTo implementation



# Important Notes
- AS FAR AS POSSIBLE CODE TO PARENT REFERENCE
- what you inherit from the parent is what you can see
- Interface called Constants is usually created to hold all constants used in the application
- TO WRITE GENERIC FLEXIBLE POLYMORPHIC ALWAYS CODE TO INTERFACE REFERENCE
- classes with only private constructors can not be inherited
- Every class provides a service to its class user 
- 



# Collections static methods


java.util
    - Arrays
        - sort()
        - binarySearch()
        - copy(a1,a2)
    - Collections:
        - In Java, Collections (with an "s") is a powerful utility class in the java.util package that provides static methods for operating on and returning collections
        - Static methods:
            - void sort(List l)
            - void shuffle(List l)
            - int binarySearch(List l)  // on sorted list
            - int frequency(List l, Object ele)

# Comparable vs Comparator
- Comparable
    - int compareTo(Object o)
    - java.lang package
    - designer implements Comparable interface to provide natural ordering
    - implemented only once per class
    - used by
        - TreeSet
        - Collections.sort
        - Arrays.sort
- Comparator
    - int compare(Object o1, Object o2)
    - java util package
    - user implements implements Comparator to provide comparison based on non-natural ordering (custom order)
    - can have any no of classes implementing3rd party Comparator
    - class implementing Comparator interface should be explicitly passed as a parameter
        - TreeSet(comp1)
        - Collections.sort(list, comp2)
        - Arrays.sort(arr, comp3)


# pubic interface Iterator 
- Collection col = new ArrayList();
- Iterator it = col.iterator;
- a way to traverse the elements of a collection
- Methods
    - boolean hasNext()
    - Object next()
    - Object remove()
- for each loop internally uses iterator
- If I modify the collection while iterating, ConcurrentModificationException Hence:
    - Collections boolean remove(Object e) can not be used 
    - while iterating directly or using for each loop
    - use Iterator object it .remove() method to remove the object returned by the it.next() method



Collections revision
- only comparable elements can be passed to 
    - class designer has implemented Comparable interface
    - class implementing Comparator is passed
- TreeSet
- Array sort method
- collection.sort



# public Interface Maps
- keys are unique
    - ALWAYS HAVE ONLY IMMUTABLE AS KEYS
- Behaviors
    - Object put(Object key, Object value) // if key exists, it replaces it and returns the old value
    - size()
    - boolean containsKey(Object key)
    - boolean containsValue(Object value)
    - remove(Object key)
    - Object get(Object key)
    - clear() // empties the map completely
    - isEmpty()
    - entrySet() // set fo entries;
        - Set<Entry<String, String>> entries = m.entrySet()
        - // Set typed to Entry of String key and String value
        - for (Entry<String, String> en : entries)
            - en.getKey()
            - en.getValue()
    - putAll(Map m)
    - Set keySet()
    - Collection values()
- HashMap - unordered
- LinkedHashMap - insertion order
- TreeMap
- ConcurrentHashMap

# Collections usage
- print unique words in a sentence in:
    - no particular order => HashSet
    - in insertion order => LinkedHashSet
    - sorted order => TreeSet




# Threads
- parallel processing -> multiple processors involved
- multitasking -> single processor handling multiple tasks
    - benefit: higher thruput (lower time taken)
    - perquisites
        - multiple tasks should be there
        - tasks should be independent of each other
        - context switching takes time hence sequential tasking is 99% of the time faster and preferred unless
            - one of the tasks should involve 3rd party resource that is taking time
                - eg. hard disk read,or network api call
            - GUI or user facing applications should always be responsive and one task should always face the user
            - when the system has multiple processors
    - multitasking is a concept that is implemented as a feature called multiprocessing by OS
    - multithreading is a feature of the JVM that gives us the ability to multitask

- Thread can refer to one of the two
    - java.lang.Thread class obj
      - invoke start() method on this object to create a thread of execution
    - thread of execution
        - it is 1 job being executed by creating 1 control flow managed by one stack frame
        - Job is a method
            - for every job jvm creates a stack frame to manage control flow and hold local variables
        - multithreading is having multiple threads of execution
        - Asking JVM it execute a method is a job
            - until now we have only asked the JVM to execute the main method
            - some of the internal tasks like garbage collection is done as a separate thread of execution
- How to create a thread of execution
    - create multiple jobs either by
        - extend class Thread and override run()
        - implement interface Runnable and override run() (BEST PRACTICE)
    
    - Ask JVM to execute each job as a separate thread of execution
        - Create a new thread object
          - if the class is extending thread then simply create the class object and call start method on it
          - if the class is implementing runnable then create a new Thread object by passing the runnable class object as a parameter to the thread constructor
            - Runnable runn1 = new MyRunnableClass()
            - Thread t1 = new thread(runn1);
            - t1.start();
        - invoke start() method on a Thread object
            - the start method contains native calls that creates a new thread
            - the new thread is started with the run() method as the start point (first fn in its stack frame)
            - the original stack with start method is popped of with the new thread running in parallel

    - Note: 
        - there are only two placeholders for TOEx in java 
            - one is main method 
            - another is run() method
        - one class can have only one run method soo each job must be coded in a separate class
        - invoke start() method on a <Thread object> is the only way to start a TOEx
        - calling run() does not create a new TOEx and executes the run method in current thread
        - start method is inherited from the Thread class and has OS native calls

        - Contract by jvm: Every thread of execution will go to completion
            - no contract regarding the order of execution
            - don't use multithreading if deterministic ordered execution is needed
            - the goal of multithreading is fastest execution possible and not order
            - NOTE: if any running Thread calls System.exit() then all running, waiting/sleeping/blocked and runnable threads are closed and the program shuts down
        - You can only start the car once
            - start() can be called on one Thread object only once
            - to start 2 threads create 2 thread objects and call start() on them (thread object can be created either by passing a runnable object to thread constructor or by creating an object of a class extending thread class)
        - Until all threads of execution complete (move to dead state) normally or ubnormally the JVM does not shutdown (unhandled exception kills the thread)
        - the parent thread starting the child thread will have 0 control once the thread starts. The parent has control only to start the thread
        - except accessing command line arguments everything else that can be done in main method can be done in run() method (even though there are multiple threads, typically the main method is the user facing method where we primarily do input and output)
        - exception in run() method will not be caught in main() and must be handled separately. If unhandled exception is found in a thread, that thread is killed and it is moved to dead state form running state.
        - Best Practice
            - Always use Runnable as we logically extend a class that we want to enhance
            - extends is used only to enhance a class and we are not enhancing the Thread class
            - using Runnable helps create a logical separation between the job and thread and any no of threads can be made to execute the same job. (NOTE: start can  not be called on the same thread object twice)
              - I can pass the same runnable object to multiple Thread constructors and create multiple thread objects with the same runnable object or job.
              - hence when start() is called on all these Thread objects, they will have the same Runnable reference and will execute the same job
        - the Thread class implements Runnable interface
        - the run method obviously does not accept any arguments but the class can have state that can be set by having parameterized constructor
          - force the user to pass state by only exposing a parameterized constructor

- Runnable interface
  - create a object of the class implementing this interface
  - multiple Thread objects can be created using the object created from this class
  - hence the same job can be done by multiple threads created by calling start() method on the Thread objects created
  - This is not possible in thread class extension 


## classThread
### instance methods: -
- interupt() => waiting/sleeping/blocked threads can be intrupted. this will raise an InterruptedException and will enter the catch block of the run method
- run() => does not create a new thread but simply executes the function in the same thread
- start() => puts the run function as the start stack frame in a new thread and allows it to execute in parallel
- set/getPriority() 1=>lowest Priority, 10=>highest priority (default = 5)
    - gc has priority =1
    - when heap memory comes to 70-80% the priority is bumped to 10
    - we do not know how many objects are garbage collected as we dont know how long it will be run and if it will run first
    - the contract of priority is that the higher priority threads are more likely to get picked for execution
- join() => pause the execution of the current thread until the thread on which it is called completes
  - the main thread calling this is blocked until thread1 completes its execution
  - this forces sequential execution
  - the thread enters the blocked state
  - Typically the thread starting a new thread uses the new thread reference to call this method to ensure the new thread completes execution before continuing execution
### Static methods: -
  - Thread.currentThread() => returns current running thread Thread object
    - Thread.currentThread().getName() => gets current running thread's name
  - Thread.sleep(<no of milliseconds>) => puts the thread to the sleeping state for the time passed
    - the thread enters the sleep state
  - Thread.yield() => give way to other threads
    - I am ok to be bumped back to runnable state
    - No guarantee that this will happen
    - the same thread can be picked up once yielding :)


- FAQ
    - two command prompts made to execute 2 programs => multiprocessing done by OS
    - One Java program executed and within that program there are 2-3 jobs involving 3rd party resource then those jobs are executed within 1 JVM as multiple threads of execution
    - if a thread is made to sleep by calling static method Thread.sleep(1000) for one second then when will it become running again?
      - Ans) we have no idea. It will become runnable after 1 second :)
      - typically used for animations and retrying for third party resources
      - Time schedulers are used for deterministic timed execution and not Thread.sleep(10)
    - What is race condition -> when multiple threads shared data in undeterministic manner leading to data corruption



## lifecycle
- JVM Thread Scheduler does management of the lifecycle of threads with the following states:
    - new
      - when an object of thread class is created the thread is in new state
      - new can be moved to runnable only (runnable can not be moved to new)
    - runnable
      - when the start method is invoked, the thread enters this state
      - runnable is waiting state. if there are many threads, and not enough processors, then every thread is given some process time and runnable state is when this is waiting
      - runnable can be moved to running and running can be bumped back to runnable 
        - this is done by the jvm and WE HAVE NO CONTROL OVER IT.
        - we can only request order by setting priority where there is higher priority for jvm to pick a higher priority thread or by calling yield
    - running
      - we have no full proof way of pushing a thread to this state but only runnable thread can enter this state
      - the thread in execution
      - can be bumped back to runnable to give another thread a chance
      - can be moved to waiting sleeping or blocked (not bidirectional - waiting/sleeping/blocked threads can only be moved to runnable and not running)
    - waiting/sleeping/blocked
      - sleeping state
        - when Thread.sleep(1000) static method is called inside current running thread, it enters this state for 1 second or 1000 ms
      - blocked state
        - when I perform a blocking operation like IO, eg sc1.readLine() the thread automatically enters this state until the hard disk returns data after which it will be bumped back to runnable
        - when database or 3rd party resource is queried the thread enters this state (happens automatically)
        - when explicitly thread1.join() is called  inside current running thread then the calling thread enters the blocked state until thread1 goes to dead state (completes normally or ubnormally)
        - when i call a method on a locked Object (NOTE THAT A METHOD IS NOT LOCKED AND ONLY A OBJECT CAN BE LOCKED WITH ONE AND ONLY ONE LOCK THAT IS ACQUIRED BY A THREAD)the thread enters the blocked state until the lock is released
    - dead
      - thread can completed execution correctly or unhandled exception has resulted in the killing of the thread




## influcing thread scheduling
- setPriority 
- yield
- sleep

## Sharing data among Threads and Thread Safety
- we might have created a class today and tomorrow this class can be shared across multiple threads of execution. 
- race condition can occur when one thread partially modifies the object state and is bumped back to runnable and the second threads reads bad data or corrupts the objects state by differently modifying the data and the first thread continuous execution on a changed object state
- Classes that are thread save
  - Stateless class
  - Immutable class/read only class (final class with final variables and parameterized constructor)
  - synchronization implemented to mutable classes for access to all shared data (static or instance variables)

- synchronized
  - can be applied to method or code block
  - only one thread can enter any synchronized method of a object at any given point of time 
    - NOTE ONLY METHODS ARE SYNCHRONIZED BUT THE LOCK IS ON THE OBJECT AND NOT ON THE METHOD
    - when a method is marked synchronized, its containing Object has a lock.
    - whenever a thread calls this method, it acquires the lock ON THE OBJECT
    - once the method finishes execution, the lock is returned to the object
    - if another thread calls any synchronized method on a locked Object, it tries to acquire the lock on the object. it enters the blocked state until the lock is released back to the Object.
  - NOTE: there is only one lock per object and not method.
    - Soo if a lock is acquired it is done for the entire object
  - IMPORTANT: all access to shared data must be marked synchronized (both read and write)

- synchronized code block
  - why should the entire method of multiple lines be blocked for only a few lines of data access
  - syncronised(this){}
  - we can pass any object referance and a lock will be accquired on that object
  - this allows me to have multiple locks for each variable


  - deadlock is a scenario that occurs when multiple threads of execution are waiting for locks held by each other
  - this occurs only when atleast two nested synchronized code blocks are involved with different object locks
  - Prevention
    - enforce order of lock acquisition across jobs (generally followed)
    - lock timeout: release the acquired lock after a timeout if second lock is not got
    - lock mapping

## Object class methods that affect thread scheduling
- myColl.wait()
- myColl.notify()
- myColl.notifyAll()

- (Note: Thread.sleep(10) puts the thread to sleep state and the lock on the object is not released (if in a synchronized code block))
- .wait() Object class method puts the thread to waiting state and releases the lock on the object if inside a synchronized code block

- dhobi example
  - there is a dhobi and a helper
  - both the classes will accept in and out butti through constructor
    - Collection inButti
    - Collection outButti
  - run method of dhobi
    - synchronized(inButti){inButti.wait();}
      - calling .wait() on inButti will put the thread to waiting state RELEASING THE LOCK ON inButti (no resources wasted)
      - the moment wait is called the lock is released
      - the thread will come back to runnable only when 
        - inButti.notifyAll() is called
  - run method of helper
    - collect cloths
    - add them to inButti
    - synchronized(inButti){inButti.notifyAll();}
- if notify() is called, any one waiting thread is bumped back to runnable
- notifyAll() bumps back all waiting threads to runnable
- these methods are called on an object
- for calling these methods, the lock of the object must be acquired (and hence must be inside a synchronized code block)
- wait releases the lock and puts the thread to waiting state
## java.util.concurrency
- since the above implementation is hard we have collections that are implementing this and we just need to use them
- BlockingQueue => it is a concurrent queue where any no of threads can write to it and any number of threads can read from it and there is no problem




# java IO

- java.io.File
  - a class that represents the file path and not the actual file (however we can create an delete a file/directory we can not write or modify a file)
  - boolean exists()
  - boolean isFile()
  - boolean isDirectory()
  - File[] listFiles() => null for file
  - String getAbsolutePath()
  - int f.length()
  - boolean f.canRead()
  -  f.delete() //delete file
  - String f.getName()
  - File[] listFiles()

## Stream => ordered sequence of data
- methods
    - abstract int read()
            - although a byte is needed to signify end of file, an int is returned
            - if -1 is returned then end of file is reached
            - the lower 8 bits signify the data and the int should be down-casted to a Byte to read the data
    - abstract write(int i)
            - where an int is expected i can pass a byte and there is implicit up casting
    - close() // in finally block
    - all the above methods throws IoException which is a checked exception and user must handle it (duck or catch)

- There are 4 main types of stream
  - Byte based stream      
        - abstract class InputStream concrete implementations => FileInputStream, ByteArrayInputStream, SequenceInputStream
        - abstract class OutputStream concrete implementations => FileOutputStream, ByteArrayOutputStream, SequenceOutputStream
  - Character based stream
        - abstract class Reader concrete implementations => FileReader, CharacterArrayReader, PipedReader, SequenceReader
        - abstract class Writer concrete implementations  => FileWriter, CharacterArrayWriter, PipedWriter, SequenceWriter
  - primitive based stream
        - DataInputStream
        - DataOutputStream
  - Object Based stream (Serialization and Deserialization used mainly for RMI-remote program invocation)
        - ObjectOutputStream (concrete class)
              - ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(new File("./myFilePath/myFile.dat")));
              - objOut.writeObject(new String("test object to be serialized. the class designer must have implemented the Serializable marker interface"));
              - objOut.close(); // in finally block
          - only one object can be serialized to one file
        - ObjectInputStream (concrete class)
              - ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(new File("./myFilePath/myFile.dat")));
              - Object obj = objIn.readObject(); // throws classNotFoundException as well
              - objIn.close(); // in finally block
          - The entire Object graph (object and all objects that its instance variables are pointing to) is serialized during serialization
          - to allow serialization, our class must implement Serializable marker interface (it is a interface with 0 methods) (if this marker is not present, on the object or any object its instance variable is pointing to then NotSerializableException is raised)
          - Bypassing serialization
            - Statics are not a part of the state and a brand new static variable is created when deserialized
            - transient is a optional modifier that can only be attached to instance variables in a class that is marked Serializable and such variables will be null when deserialized (not included in serialization)
            - If a child class marks the class as Serializable and the parent class has not marked itself Serializable then only the child's objects state is serialized and the parents instance variables will be null and not serialized

- BufferedReader: better performance due to buffering
  - Basically a wrapper on file reader
  - br = new BufferedReader(new FileReader(new File("./path/file.txt")));
    - String line = br.readLine(); // is a additional method only for (file character based) buffer reader (note that bytes don't have a concept of new line)
  - BufferedInputStream can be used with FileInputStream for byte based data
    - bin = new BufferedInputStream(new FileInputStream(f));
    - bout = new BufferedOutputStream(new FileOutputStream("./a.txt")); 
    - no concept of line here
    - this class also offers same read() and write() methods and internally implements buffering 


- lr = new LineNumberReader(new BufferedReader(new FileReader(f)));
  - LineNumberReader is a wrapper around BufferedReader which is a wrapper of FileReader which we have understood
  - each wrapper adds functionality as required and provides additional functionality
  - this class gives line number.




## Cloning
- Memo
      - Memo: when overriding methods minimum I need to retain parents access specifier but I can increase
      - clone method of Object class must be overridden to support cloning and it is a protected marked method.
      - when i override this method I can mark it public
- to clone any object: -
  - the class must implement Cloneable interface (or CLoneNotSupportedException will be thrown) (marker interface)
  - override the clone() method from Object class (by default Object class clone method is marked protected and does a shallow copy)
    - shallow copy is where premitives are copied fine but referances are exactly copied and the new object referances points to the old objects referance
- Ways to support cloning
  - Forbid cloning:  (best practice)
      - dont implement Cloneable marker interface and just throw CloneNotSupportedException in the clone method
  - support cloning 
      - make clone method public and use try catch and not throw any exception
      - ensure all instance variables are also pointing to cloned objects
  - conditionally support cloning
      - throw exception making the user handle it(rest is support cloning)
      - this is done if we have an instance variable that we dont know if it supports cloning or not
  - support cloning only for subclasses
      - clone method implementation provided but class not marked Cloneable to facilitate subclassers to make their object cloneable

- since cloning is complex we generally forbid it and use alternatives
  - copy constructors
    - overload constructor to accept the same object reference and handle it appropriately
  - static factories



# Singleton design pattern
- only one object should exist
  - have a static final variable pointing to same class object 
    - in static field initializer 
    - static initializer 
    - when user asks for object (lazy initialization)
  - mark constructor private
  - have a static function to return current object reference. if reference is null then create a new object
  - As always all variables should be marked private
- used in logger, connection poller object


# logger
- 





# App Development
- prototype
  - build a non working model to understand if the requirements are understood correctly
  - it is a non working model of the final solution that will be shown to the end user
  - built on paper or using simple tools
    - views
      - start with home view (what the user sees when he starts the app)
      - actions that can be performed and and their flow
      - control flow
      - input validations and messages displayed
      - business validations



# MVC Design pattern
- Model View Controller
- View (agent)
  - user facing 
  - show UI
  - show output 
  - accept input
  - SHOULD NOT DO CONTROLLER LOGIC/ business logic/ data logic
  - _
  - When we want to pass multiple data points to the next layer we will not use collections as we have different data types
  - we create a class and pass an object of the said class
  - such a class that contains only data is commonly called Java Beans
  - JAVA BEAN is a ordinary java class with setters and getters
- Controller (branch office)
  - control flow management
  - Data flow management
  - No UI/ business logic/ data logic
  - Invoke the correct business logic
  - pass the input data from the view to the Model
- Model (main office creating the contract)
  - perform business logic
  - use core oo features
    - encapsulation
    - inheritance 
    - polymorphism
  - No UI/ control logic
- Data

## java bean
- java bean is a normal java object built with conventions
  - is a data holder without business logic
  - must be in a package
  - must have a public no-arg constructor
  - inst variables as many has a attributes with same name as attribute name
  - setters and getters (all instance variables should be private) (take data type of the variable only)
    - the getter method for boolean is called isVariable and not getVariable
  - implement natural ordering by implementing comparable
  - implement serializable marker interface
- Benefits
  - can be passed across layers as a data holder
  - discoverable/auto data injection because best practices are followed


# steps to build applications
- understand the requirements
- prototype - non working model of the final app
  - paper
  - wireframe
  - low fidelity prototype
  - high fidelity prototype
- Ideally code is written once high fidelity prototype is available and at a minimum low fidelity prototype is needed
- 
- bean or model is created by the UI and passed to controller to service (BL) and is finally stored in the database


# Database
- sql based relation database 
- no sql based database



## JDBC (used only with sql databases (java.sql))
- connect a java application to any database and execute DDL and DML commands
- our application should be completely independent of database

- our program should establish a socket to the chosen database app and this is automated by JDBC driver
  - type 1 bridge driver => connects the java app with another driver which connects with the database
    - one driver and be used to communicate with many databases
    - slower in performance
  - type 2 - native => directly communicates with database (make database native api calls to a particular db)
    - database dependent and os dependent
    - super fast
  - type 3 - middleware => communicates with a middleware (part hardware part software)
    - middleware can do asynchronous processing and hence faster
    - proprietary drivers that need to be purchased
    - credit card, the payment is processed and is asynchronously processed (sometimes the bank calls after some time confirming the transactions)
  - type 4 - pure java => the database api are built on java and the java application is making java calls (java to java unlike type 2 which is java to c)
    - fast
    - os independent
    - db dependent


- load the driver
  - Class.forName("fully qualified class name of driver class");
  - the static initializer registers the driver with the driver manager

## SQL
- sql is a standard that is implemented be all RDBMS solution providers like MySql to enable developers to communicate with the database. It forms the interface between the database and the application.
- table is a unit data structure is used to store data (for one thing)
- DDL cmds => create, alter, drop => (data definition logic) can be used to manipulate the table/data structure
  - tables 
  - views
  - sequences
  - indexes
- DML cmds (data manipulation logic)
  - insert
  - delete 
  - update 
  - select
- DCL - data control logic - data base administrator - grant and revoke access


- row is a set of values for all chosen column values that have been chosen during DDL stage
- one object represents one row and a collections of objects represent a table


- Transaction is a unit of work that spans multiple SQL cmds which either succeeds fully or is revoked fully
  - example student registration
  - first we store the details and then create a library account
  - if if address storage fails then the entire transactions should be revoked

- data types
  - char - store characters and occupies all the size allocated
    - id char(10) => fixed size
  - varchar - stores characters and occupies only the size needed but should be less than the max space allocated
    - name varchar(10) => varying size
  - dec - decimal
    - salary dec(15,3) => total no of digits is 15 and there are 3 decimal places
  - integer
  - datetime
    - YYYY-MM-DD HH:MI:SS
  - timestamp
    - used for system generated data and is a num of ms from a epoch time
  - lob => blob

- CREATE TABLE tmDatabase(id char(12), name varchar(200), creationDate date);
- INSERT INTO tmDatabase(id, name, creationDate) VALUES('1001', 'test', '2020-12-25');
- SELECT * FROM tmDatabase;
- UPDATE tmDatabase SET creationDate = '2020-12-26' WHERE id='1001';
- DELETE FROM tmDatabase WHERE id='1001';


- delete - DML cmd
- drop - DDL cmd

- normalization rules are rules to be followed while designing a table (data modeling rules)
  - non redundant data
    - in one table have only parameters/values with one is to one cardinality
    - have a separate secondary table to capture non one is to one cardinality
  - every thing in the table should be directly dependent on every other thing in the table

- null in database is the absence of a value and one null in the SQL world is not equal to another null

- Constraints
  - CREATE TABLE tmDatabase(slNo integer PRIMARY KEY, id char(12) UNIQUE NOT NULL, name varchar(200) NOT NULL, creationDate date);
  - NOT NULL =>  mandatory to provide the value (null not allowed)
  - UNIQUE KEY 
    - value if given should unique within the table across all the rows (for that column) 
    - null is allowed => since in sql one null is not equal to another null, any number of nulls can be added
    - used to ensure that values inserted are unique
  - PRIMARY KEY => UNIQUE + NOT NULL 
    - never choose a business driven column for this purpose
    - only one per table
    - BEST PRACTICE => slNo INTEGER PRIMARY KEY AUTO_INCREMENT as first column for every table
    - used to identify the row uniquely and used to link with secondary tables foreign key
  - FOREIGN KEY => value should be one from the primary key column that it is associated with
  - CHECK

- whenever I have a enum (one amongst many) => I need to create a master table and store the value as a foreign key in my table

- between(10,20)
- in(10,20,30)
- order by col desc limit fromIdex,numberOfRows

## Aggregate functions => placed after select in the column names section
- max 
- min
- avg
- count
- sum

- select distinct name form emp => for unique values (set)

- having applied after group by and where applied before group by
- order by and limit is applied in the end



- NEW TABLE FOR (first normal form)
  - time bound quantities
  - non atomic quantities (address has sub fields) 
    - we must not have multiple columns storing the same value
    - no aggregated columns (comma separated values)
  - columns with cardinality 1:n and m:n
  - values are one from a fixed set of values (enum)

- 2nd normal form
  - should be in first normal form
  - all columns must be dependent on the full primary key (all the primary key columns)
  - because we are following the best practice of creating a non business surrogate column as primary key we are always satisfying this condition

- 3rd normal form
  - should be in 2nd normal form
  - no transative dependency
    - all columns should depend on the table and not on each other


- index
  - to enable binary searching the index table is created where all the data references is stored in sorted order
  - by default the primary key is indexed
  - CREATE INDEX idx_lastname ON employees (lastname); => now search on lastname will be in log2(n) time

cross join - cartician join
inner join - only common elements


- select * from dep1 UNION select * from dep2
  - union: all records returned. for duplicate records, only one copy is returned
  - union all: all records returned and copies are also returned
  - intersect: select only single copy of common records across the intersected statements
  - except: records that are not present in the intersection are preserved


- delete from emp where slno=1 CASCADE
  - CASCADE deletes rows that are referencing this record


# important notes
- when an int is added to a collection, auto boxing happens and the int value is stored in a Integer object
- Whenever there is search favour Hashing
- singleton design pattern => private constructor, synchronized method to return single object instance
- sequential execution is necessary whenever there is some check done and then some action and hence a lock on the object must be acquired
- obj.wait() does not wait for change in object state but waits for obj.notifyAll to be called
- design pattern is a best practice strategy to adopt for a recurring requirement
  - what is the repeating requirement
  - what is the best practice strategy
- immutable classes are marked final
- Date dt = new Date();
- to create a class class object by calling the class loader use -> Class.forName("com.uis.Test")


SELECT DISTINCT <COLUMN NAMES / * / AGGRIGATE FUNCTIONS AS name>
FROM <tables>
WHERE <condition>
GROUP BY <column names>
HAVING <condition>
ORDER BY <col names>
LIMIT StartIndex,numberOfRows




# Internet
- isp - internet service provider
- www is a service that runs on the internet infrastructure
- HTTP sits on top of TCP protocol that allows us to ask for resources
  - text based protocol
  - 1 response with 1 mime type
  - pull based protocol
    - the client has to specifically pull data and the server cant push data
    - either a new request every few seconds to check for update (polling)
    - orr websocket based tcp connection can be used
- web server is a software that responds to http requests

- server in android application can push data to the client device where as a webapp server using http can not push data to the application



2009 - 2016 native android

2017 2018 => flutter / react native

- yesterday
- today
- any blockers


# java io
- Scanner sc1 = new Scanner(System.in);
- sop("enter name");
- String name = sc1.next();







# InnerClass
- anonymous inner class
  new Thread(){
      public void run(){
          for(int i=0; i<100; i++>){.....}
      }
  }.start(); 

  - IMP: we are passing a anonomous inner class object to the Thread constructor
  - this object implementing runnable represents a job

  - If I have a requirement such that i want to use a class but extend one of its methods
    - conventional approach
      - create a new class extending the current class
      - override the method whose implementation you want to enhance
      - pass this child object wherever the parent is expected
    - But I want a simpler approach => Anonymous Inner Class
    - Syntax
      - X obj = new X(){
        - // I can override methods here
        - public void testMethod(){
            //New implementation
          }
      - };
- Method local Inner class
  - class defined inside a method
  - main(...){
    - class Z extends X {
      - public void myMethod(){
        - // ......
      - }
    - }
    - X obj1 = new Z();
    - X obj2 = new Z();
  - }


  - anonymous inner class is used when i want only one object of a class and don't want to create a separate class file
  - method local inner class is used when i want multiple objects of a class but only inside a method and don't want to create a separate class file
