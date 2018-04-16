public class project1 {



  public static void main(String[] args){
        SparseInterface myTest = new SparseMatrix();
        myTest.setSize(3,3);
        myTest.addElement(2,1,2);
        myTest.addElement(1,3,9);

        System.out.println(myTest.toString());
        
    }




}

