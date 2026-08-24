package test1;

/**
 * since we are extending Exception class, MyException is a checked exception, 
 * if we want to create an unchecked exception, we can extend RuntimeException instead of Exception.
 * all checked exceptions must be added to throws clause
 */
public class MyException extends Exception {
    public MyException(String message){
        super(message);
    }
    public MyException(){
        super();
    }
}
