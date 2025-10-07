
class MyException extends Exception{
    MyException(String message){
        super(message) ;
    }
}

public class CustomExpection {
    public static void main(String[] args) throws MyException {

        myMethod();

        System.out.println("----------------");

        try{
            myMethodII();
        }catch(Exception e){
            System.out.println(e);
        }
    }

    static void myMethod(){
        // Exception is handled in this function only using try and catch
        int value=0;
        try{
            if(value==0){
                throw new MyException("value can be zero") ;
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    static void myMethodII() throws MyException{
        // Exception is handled in parent function (here it is main method)
        String name="";
        if(name==""){
            throw new MyException("Name can't be null") ;
        }
    }
}
