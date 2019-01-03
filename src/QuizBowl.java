import java.io.*; //importing dependencies necessary for this project
import java.sql.*;
import java.util.ArrayList;

public class QuizBowl { //Beginning of QuizBowl class


       public static ArrayList<QuizBowl> getListProductFromTextFile(String filePath) { //Beginning of File reading function
           FileInputStream fis = null; //A stream to take in data from a file
           InputStreamReader isr = null; //Reads stream
           BufferedReader buffy = null; //Allows the program to read the text file data and process it accordingly
           ArrayList<QuizBowl> listResult = new ArrayList<QuizBowl>(); //List where the data will go once organized properly
           filePath="Quiz.txt"; //Defining file that will be used to retrieve data from.
           try {
               fis = new FileInputStream(filePath);
           } catch (FileNotFoundException e) {//Defining what to do if the file mentioned is not found.
               e.printStackTrace();
           }
           isr = new InputStreamReader(fis);//Input Stream Reader reads Fil INput Stream
           buffy = new BufferedReader(isr); //BufferedReader reads data once it's been passed in.
           String line = null; //line that will be used to place pieces of data in before they are put into the SQL table


           while (true) {
               try {
                   line = buffy.readLine(); //BufferedReader reads lines of text file
                   if (line == null) { //While loop breaks when there is a completely blank line in the text file. BufferedReader stops reading.
                       break;
                   } else {
                       String[] strQuizBowl = new String[10]; //String array where BufferedReader data will be organized into the SQL table according to it's place in the array.

                       strQuizBowl = line.split(","); //Commas in the text file are used to separate line's data into different array values  in strQuizBowl
                       if(strQuizBowl.length < 7){ //Errors out if a line has less than 7 array values.
                           System.out.print("Error, Not enough elements");
                       } else {//If no errors, line will be split up where commas are, and each value will be placed in the array temporarily  with ascending indices.
                           listResult.add(new QuizBowl(Integer.parseInt(strQuizBowl[0]), strQuizBowl[1], strQuizBowl[2], strQuizBowl[3], strQuizBowl[4], strQuizBowl[5], strQuizBowl[6])); //These array indices com
                       }

                   }



               } catch (IOException e) { //Catches input and output errors
                   e.printStackTrace();
               }

           }
           try {
               buffy.close(); //Closing stream, file, and buffered readers.
               isr.close();
               fis.close();
           } catch (IOException e) { //Catches input and output errors.
               e.printStackTrace();
           }

           return listResult; //puts full array with all of the SQL rows of data into the SQL table
       }

    public static void main(String args[]) { //Start of main/ executing class
        String url = "jdbc:mysql://localhost:3306/assignment10"; //Location of SQL database
        String user = "root"; //Username and password of root account
        String password = "password";




        try {
            Connection con = DriverManager.getConnection(url, user, password); //Uses the three previous pieces of information to establish a connection with the SQL server.
            String query = "INSERT INTO QUIZ VALUES(?,?,?,?,?,?,?)";
            PreparedStatement state = con.prepareStatement(query); //Java feeds SQL the above query statement  to insert the built array into the SQL table.
            ArrayList<QuizBowl> listQuiz = getListProductFromTextFile("Quiz.txt");

            for(int i = 0; i < listQuiz.size(); i++){ //The SQL connection object takes the different array items and sends them through to the SQL columns.
                state.setInt(1,listQuiz.get(i).getQuestionid());
                state.setString(2,listQuiz.get(i).getQuestion());
                state.setString(3,listQuiz.get(i).getChoicea());
                state.setString(4,listQuiz.get(i).getChoiceb());
                state.setString(5,listQuiz.get(i).getChoicec());
                state.setString(6,listQuiz.get(i).getChoiced());
                state.setString(7,listQuiz.get(i).getAnswer());
                state.executeUpdate();
            }
        } catch (SQLException e) { //Reports database access errors.
            e.printStackTrace();
        }



    }



    public QuizBowl(int questionid, String question, String choicea, String choiceb, String choicec, String choiced, String answer) {
        this.questionid = questionid; //Creating quizbowl object used above to store the different array indices from listQuizBowl and set the data into the SQL table
        this.question = question;
        this.choicea = choicea;
        this.choiceb = choiceb;
        this.choicec = choicec;
        this.choiced = choiced;
        this.answer = answer;
    }

    private int questionid;
    private String question;
    private String choicea;
    private String choiceb;
    private String choicec;
    private String choiced;
    private String answer;
    public int getQuestionid() {
        return questionid;
    } //Making getter and setter functions for getting  the list data and setting it into the database connection object

    public void setQuestionid(int questionid) {
        this.questionid = questionid;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getChoicea() {
        return choicea;
    }

    public void setChoicea(String choicea) {
        this.choicea = choicea;
    }

    public String getChoiceb() {
        return choiceb;
    }

    public void setChoiceb(String choiceb) {
        this.choiceb = choiceb;
    }

    public String getChoicec() {
        return choicec;
    }

    public void setChoicec(String choicec) {
        this.choicec = choicec;
    }

    public String getChoiced() {
        return choiced;
    }

    public void setChoiced(String choiced) {
        this.choiced = choiced;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }



}
