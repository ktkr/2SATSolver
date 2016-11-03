package twosat;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class Randomizer {

    HashMap<String,Integer> assignment = new HashMap<String,Integer>();
    String[][] clauses;
    int clauseIndex;
    int runCount = 0;
    int varNo;

    public Randomizer(String[][] clauses,int varNo){ //varNo has to be read from Parser
        this.varNo = varNo;
        randomAssignment(this.varNo);
        this.clauses = clauses;
        this.clauseIndex = 0;

    }

    public void run(){
        evaluate();
        this.runCount+=1;
        if(truthCheck()){
            System.out.println("Truth has been found!");
            System.out.println(this);
        }
        else if(this.runCount>=this.varNo*this.varNo){ //should take n^2 number of iterations on average
            System.out.println("No answer found within 10000 iterations.");
        }
        else{
            randomLiteralChange(this.clauseIndex); //change a variable value in that false clause at random.
            run();
        }
    }

    public int randomBool(){
        return ThreadLocalRandom.current().nextInt(0,2);
    }

    public int randomInt(int min,int max){ //spare method if need to randomize clauses
        return ThreadLocalRandom.current().nextInt(min,max+1);
    }

    public boolean literalCheck(String literal){
        if (literal.startsWith("-")){
            return false;
        }
        else{
            return true;
        }
    }

    public void randomLiteralChange(int clauseIndex){
        int literalValue;
        int num = randomBool();
        String literal = this.clauses[clauseIndex][num];
        if (literalCheck(literal)){ //straightforward: gets the boolean of the variable
            literalValue = this.assignment.get(literal);
        }
        else{
            if(assignment.get(literal.substring(1,literal.length()))==0){ //gets the negated boolean form of the variable
                literalValue = 1;
            }
            else{
                literalValue = 0;
            }
        }
        //flip the value of the literal
        if (literalValue == 1){
            this.assignment.put(literal,0);
        }
        else{
            this.assignment.put(literal,1);
        }

    }

    public void evaluate(){
        int negative;
        this.clauseIndex=0;
        int clauseNo = this.clauses.length;
        outerloop:
        for (String[] clause: this.clauses){
            negative = 0;
            for (String literal: clause){
                if (literalCheck(literal)){
                    if (this.assignment.get(literal)==1){
                        break;
                    }
                    else {
                        if (negative == 0) {
                            negative += 1;
                        } else {
                            break outerloop;
                        }
                    }


                    }
                else { //this else accounts for the negative variable
                    if (this.assignment.get(literal.substring(1,literal.length()))==0){
                        break;
                    }
                    else {
                        if (negative == 0) {
                            negative += 1;
                        } else{
                            break outerloop;
                        }
                    }


                }


            }
            this.clauseIndex+=1;
        }

        if (this.clauseIndex == clauseNo){
            this.clauseIndex +=1;
        }

        //need to evaluate all the clauses
        //return false clause as int index

    }

    public boolean truthCheck(){ //take from evaluate
        if (this.clauseIndex>this.clauses.length){
            return true;
        }
        else{
            return false;
        }
    }

    public void randomAssignment(int varNo){
        for (int i=1;i<=varNo;i++){

            this.assignment.put(String.valueOf(i),randomBool()); //assigns random values to every variable

        }
    }

    public String toString(){
        String printOut = "";
        for (int j=1;j<=this.varNo;j++){
            this.assignment.get(String.valueOf(j));
            printOut = printOut + this.assignment.get(String.valueOf(j)).toString() + " ";
        }
        return printOut;
    }
}
