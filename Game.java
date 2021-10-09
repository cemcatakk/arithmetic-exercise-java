import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Game {
    //This method returns a random operator
    static char getRandomOperator() {
        Random rnd = new Random();
        int s = rnd.nextInt(4);//Get a random number
        if (s == 0) //And return an operator depend on integer
            return '+';
        else if (s == 1)
            return '-';
        else if (s == 2)
            return '*';
        else return '/';
    }

    //This method asks a question depends on level, then return true if answer is correct
    static boolean getQuestion(int level, Scanner sc) {
        Random rnd = new Random();
        boolean result = false;
        double n1, n2, n3=0, res = 0;
        char op1 = getRandomOperator(), op2 = getRandomOperator();
        //2 numbers are always generated
        //All numbers generated between 1-50 * level
        //Means second level is harder than first one
        n1 = rnd.nextInt(50*level)+1;
        n2 = rnd.nextInt(50*level)+1;
        //Calculate result by random operation
        if (op1 == '+') {
            res = n1 + n2;
        } else if (op1 == '-') {
            res = n1 - n2;
        } else if (op1 == '*') {
            res = n1 * n2;
        } else {
            res = n1 + n2;
            op1='+';
        }
        //If level is 2, we should consider arithmetic rules
        if (level == 2) {
            //So if operation 1 is + or - and operation 2 is * or /
            //We should calculate result again
            n3 = rnd.nextInt(50*level)+1;
            if((op1=='+'||op1=='-')&&(op2=='*'||op2=='/')){
                if(op2=='*'){
                    if(op1=='+'){
                        res = n1 + n2 * n3;
                    }
                    else{
                        res = n1 - n2 * n3;
                    }
                }
                else{
                    if(op1=='+'){
                        res = n1 + n2 / n3;
                    }
                    else{
                        res = n1 - n2 / n3;
                    }
                }
            }
            else{
                //Otherwise we will just do the operations normally
                if (op2 == '*') {
                    res *= n3;
                } else res /= n3;
            }
            if (op2 == '+') {
                res += n3;
            } else if (op2 == '-') {
                res -= n3;
            }
        }
        //Print question
        if (level==1){
            //Print only 2 numbers and 1 operation if level is 1
            System.out.printf("%.0f%c%.0f = ",n1,op1,n2);
        }
        else{
            //Print all nums&operations
            System.out.printf("%.0f%c%.0f%c%.0f = ",n1,op1,n2,op2,n3);
        }
        //Input answer
        double answer = sc.nextDouble();
        //If answer is correct
        if (res == answer) {
            System.out.print("Correct");
            return true; //return true
        } else {
            //Otherwise return false
            System.out.print("Wrong");
            return false;
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        int limit = 60, choice = '0';
        long elapsedSeconds;
        int correctAnswers = 0, tempCount = 0, level = 1,qCount;
        //Print welcome message
        System.out.println("Welcome to Arithmetic's Exercise Program");
        System.out.println("Answer as many questions in total 60 seconds");
        System.out.println("You get 5 seconds bonus if you answer 5 questions in a row");
        System.out.println("Use java arithmetic precedense rules!");
        System.out.println("Input 'q' to quit or any key to start:");
        if (System.in.read() != 'q') {
            //If user does not quit
            do {
                //Start the game
                qCount=0;
                correctAnswers=0;
                tempCount=0;
                limit=60;
                //Reset settings
                long start = System.currentTimeMillis();
                do {
                    //Questions asked in this loop
                    qCount++; //Increase question count by 1
                    if (getQuestion(level, sc)) { //If answer is true
                        correctAnswers++; //Then increase correctAnswers and tempCount by 1
                        tempCount++; //Temp count resets if user answers wrong
                    } else {
                        tempCount = 0;
                    }
                    if (tempCount == 5) { //Then fi tempCount is 5, 5 seconds will be added
                        System.out.println(" (5 seconds added)");
                        tempCount = 0;
                        limit += 5;
                    }
                    else
                        System.out.println();
                    //Calculate elapsed time
                    elapsedSeconds = (System.currentTimeMillis() - start) / 1000;
                } while (elapsedSeconds < limit);
                if (correctAnswers >= 15 && level == 1) {
                    //If level 1 and user correctly answered more than 15 questions
                    System.out.printf("You are really fast!\nYou answered %d questions correctly out of %d in %d seconds." +
                            "Input 'n' to advance to next level.\nInput 'q' to quit or any key to restart\n",correctAnswers,qCount,limit);
                    choice = sc.next().charAt(0);
                    //Then user will advance to next level
                    if (choice == 'n') {
                        level++;
                    }
                }
                else if(level==2){
                    //If level is already 2, user will downgrade its level
                    System.out.printf("You are great!\nYou answered %d questions correctly out of %d in %d seconds." +
                            "Input 'l' to downgrade your level.\nInput 'q' to quit or any key to restart?",correctAnswers,qCount,limit);
                    choice = sc.next().charAt(0);
                    if(choice=='l'){
                        level=1;
                    }
                }
                else {
                    //Otherwise user will only restart or quit game
                    System.out.printf("You are as fast as a turtle!\nYou answered %d questions correctly out of %d in %d seconds." +
                            "You can not advance to the next level\nInput 'q' to quit or any key to restart?",correctAnswers,qCount,limit);
                    choice = sc.next().charAt(0);
                }
            } while (choice != 'q');
        }
    }
}
