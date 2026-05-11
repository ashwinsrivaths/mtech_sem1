# java
- when jvm encounters a object which is not loaded, it invokes the class loader
    - class loader reads the byte code and loads it to the heap as a class class object
    - class loading happens once per class per program execution
    - class class objects are never garbage collected and JVM maintains reference to them
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
- String class designer has overidden .hashCode function beautifully and any object state can be converted to string on overiding .toString method and .hashCode can be called on this string


## API
- a service that is offered
  - Class user is offered a set of methods
  - Class user is offered a interface of the class to code against
  - object class returns random hash code and NEEDS to be overrided (otherwise every collection or class calling hash code will fail)
  - all hash implementations use has code to assign buckets


## Best Practices
- Hide the state and expose setters and getters and parameterized constructor 
  - validating inputs and ensuring object consistency 
  - IllegalArgumentException thrown for bad input
- behaviors are implemented as instance methods
  - business validation fails
    - checked exception raised
    - custom exception is a class that extends exception with a descriptive name
    - marks throws clause
- Always code to interface referance
- IT IS NON NEGOTIABLE: Always override .equals, .hashCode() and toString  (return .hashCode called on toString)
- Always use generics or type safe collections
- implement comparable interface

## Strings
- StringBuilder is thread safe and mutable
  - StringBuilder sb = new StringBuilder("test sb");
  - .append("abc")
  - .reverse()
  - .toString()
- ALWAYS USE .equals(obj) to compare any object including strings to compare state


## functions
- in function overloading, if there is no exact match and there are two methods that are at the same level when upcasted, error is thrown

## encapsulation
- reusability of code
- easy maintenance
- easy to address complexity


# Optional modifiers

- constructor can not be marked with optional modifiers


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

- Thread
    - java.lang.Thread class obj
    - thread of execution
        - it is 1 job being executed by creating 1 control flow managed by one stack frame
        - Job is a method
            - for every job jvm creates a stack frame to manage control flow and hold local variables
        - multithreading is having multiple threads of execution
        - Asking JVM it execute a method is a job
            - until now we have only asked the JVM to execute the main method
            - some of the internal tasks like garbage collection is done as a separate thread of execution
- How to create a thread of execution
    - create multiple jobs by embedding statements in run method
        - extend class Thread and override run()
        - implement interface Runnable and override run()
    
    - Ask JVM to execute each job as a separate thread of execution
        - invoke start() method on a Thread object
            - start method is called on the main thread
            - the start method contains native calls that creates a new thread
            - the new thread is started with the run() method as the start point
            - the original stack with start method is popped of with the new thread running in parallel

    - Note: 
        - there are only two placeholders for TOEx in java 
            - one is main method 
            - another is run() method
        - one class can have only one run method soo each job must be coded in a separate class
        - invoke start() method on a <Thread object> is the only way to start a TOEx
        - calling run() does not create a new TOEx and executes in current thread
        - start method is inherited from the Thread class and has OS native calls

        - Contract by jvm: Every thread of execution will go to completion
            - no contract regarding the order of execution
            - don't use multithreading if deterministic ordered execution is needed
            - the goal of multithreading is fastest execution possible and not order
        - You can only start the car once
            - start() can be called on one Thread object only once
            - to start 2 threads create 2 thread objects and call start() on them
        - Until all threads of execution complete normally or ubnormally the JVM does not shutdown
        - the parent thread starting the child thread will have 0 control once the thread starts. The parent has control only to start the thread
        - except accessing command line arguments everything else that can be done in main method can be done in run() method
        - exception in run() method will not be caught in main() and must be handled separately. If unhandled exception is found in a thread, that thread is killed.
        - Best Practice
            - Always use Runnable as we can logically extend a class that we want to enhance
            - extends is used only to enhance a class and we are not enhancing the Thread class
            - using Runnable helps create a logical separation between the job and thread and any no of threads can be made to execute the same job. (NOTE: start can  not be called on the same thread object twice)
        - the Thread class implements Runnable interface
        - the run method obviously does not accept any arguments but the class can have state that can be set by having only a parameterized constructor

- JVM Thread Scheduler is does management of the lifecycle of threads with the following states:
    - new
    - runnable
    - running
    - waiting/sleeping/blocked
    - dead

## classThread
- interupt()
- join()
- run()
- start()
- set/getPriority() 1=>lowest Priority, 10=>highest priority
    - gc has priority =1
    - when heap memory comes to 70-80% the priority is bumped to 10
    - we do not know howmany objects are garbage collected as we dont know how long it will be run and if it will run first
    - the contract of priority is that the higher priority threads are more likely to get picked for execution



- FAQ
    - two command prompts made to execute 2 programs => multiprocessing done by OS
    - One Java program executed and within that program there are 2-3 jobs involving 3rd party resource then those jobs are executed within 1 JVM as multiple threads of execution











# important notes
- when an int is added to a collection, auto boxing happens and the int value is stored in a Integer object
- Whenever there is search favour Hashing








# java io
- Scanner sc1 = new Scanner(System.in);
- sop("enter name");
- String name = sc1.next();





