import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Scanner {
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        scan();
    }
    static int  numOfErrors = 0;
    static String token = "";
    static char character;
    static char character2;
    static java.util.Scanner inputCode;

    static HashMap<String, String> keywords = new HashMap<>();
    static HashMap<Character, String> oneLetterOp = new HashMap<>();
    static HashMap<String, String> twoLetterOp = new HashMap<>();

    //eslam & yahia Id
    static boolean isId(String string) {
        int len = string.length();
        char c = string.charAt(0);
        if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_') {
            for ( int i = 1; i < len; ) {
                //e s l a m
                char ch = string.charAt(i);
                if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || ch == '_' || (ch >= '0' && ch <= '9')) {
                    i++;
                } else {
                    return false;
                }

            }
            return true;
        }
        return false;

    }

    // eslam w yehia 2 :dig
    static boolean isDigit(String num) {
        for ( int i = 1; i < num.length(); ) {
            char c = num.charAt(i);
            if ((c >= '0' && c <= '9')) {
                i++;
            } else {
                return false;
            }
        }
        return true;
    }

    public static String[][] scan() throws IOException {
        String[][] output = new String[35][5];

        keywords.put("Pattern", "Class");
        keywords.put("DerivedFrom", "Inheritance");
        keywords.put("TrueFor", "Condition");
        keywords.put("Else", "Condition");
        keywords.put("Ity", "Integer");
        keywords.put("Sity", "SignedInteger");
        keywords.put("Whatever", "Loop");
        keywords.put("Cwq", "Character");
        keywords.put("CwqSequence", "String");
        keywords.put("Ifity", "Float");
        keywords.put("SIfity", "SFloat");
        keywords.put("Valueless", "Void");
        keywords.put("Logical", "Boolean");
        keywords.put("Respondwith", "Return");
        keywords.put("Srap", "Struct");
        keywords.put("Scan", "Switch");
        keywords.put("Conditionof", "Switch");
        keywords.put("Require", "Inclusion");


        oneLetterOp.put('^', "Line Delimiter");
        oneLetterOp.put('@', "Start Symbol");
        oneLetterOp.put('#', "Token Delimiter");
        oneLetterOp.put('~', "Logical Operator");
        oneLetterOp.put(')', "Brace");
        oneLetterOp.put('(', "Brace");
        oneLetterOp.put('{', "Brace");
        oneLetterOp.put('}', "Brace");
        oneLetterOp.put('.', "Line Delimiter");
        oneLetterOp.put('=', "Assignment Operator");
        oneLetterOp.put('<', "relational Operator");
        oneLetterOp.put('+', "Arithmetic Operator");
        oneLetterOp.put('-', "Arithmetic Operator");
        oneLetterOp.put('*', "Arithmetic Operator");
        oneLetterOp.put('/', "Arithmetic Operator");
        oneLetterOp.put('!', "Logical Operator");


        twoLetterOp.put("==", "relational operator");
        twoLetterOp.put("!=", "relational operator");
        twoLetterOp.put("->", "Access Operator");
        twoLetterOp.put("--", "Comment");
        twoLetterOp.put("/-", "Comment");
        twoLetterOp.put("&&", "Logical Operator");
        twoLetterOp.put("||", "Logical Operator");

        int line = 0;

        JFileChooser j = new JFileChooser("f:");

        // Invoke the showsOpenDialog function to show the save dialog
        int dialog = j.showOpenDialog(null);

        // If the user selects a file
        if (dialog == JFileChooser.APPROVE_OPTION) {
            File fi = new File(j.getSelectedFile().getAbsolutePath());

            try {
                FileReader fr = new FileReader(fi);
                BufferedReader br = new BufferedReader(fr);
                int c = 0;
                String op = "";
                int cntRow = 0;
                while ((c = br.read()) != -1) {
                    boolean foundOp = false;
                    boolean foundOp2 = false;
                    character = (char) c;
                    if (character == '\n') {
                        line++;
                        continue;
                    }
                    if (oneLetterOp.containsKey(character)) {
                        op = "";
                        foundOp = true;
                        op = op + character;
                        if ((c = br.read()) != -1) {
                            character2 = (char) c;
                            if (character2 != '\n') {
                                op += character2;
                                if (twoLetterOp.containsKey((op))) {
                                    foundOp2 = true;
                                }
                            }
                            if(character2 == '\n')
                                line++;
                        }
                    }
                    if (foundOp2) {
                        //code Token checking goes here
                        if (token != "") {
                            if (keywords.containsKey(token)) {
                                output[cntRow][0] = String.valueOf(line);
                                output[cntRow][1] = token;
                                output[cntRow][2] = keywords.get(token);
                                output[cntRow][3] = String.valueOf(line);
                                output[cntRow][4] = "Matched";
                                cntRow++;
                                System.out.println(token + " " + keywords.get(token));
                            } else if (isId(token) == true) {
                                System.out.println(token + "Identifier");
                                output[cntRow][0] = String.valueOf(line);
                                output[cntRow][1] = token;
                                output[cntRow][2] = "Identifier";
                                output[cntRow][3] = String.valueOf(line);
                                output[cntRow][4] = "Matched";
                                cntRow++;
                            } else if (isDigit(token) == true) {
                                System.out.println(token + "Constant");
                                output[cntRow][0] = String.valueOf(line);
                                output[cntRow][1] = token;
                                output[cntRow][2] = " Constant ";
                                output[cntRow][3] = String.valueOf(line);
                                output[cntRow][4] = "Matched";
                                cntRow++;

                            } else {
                                numOfErrors++;
                                System.out.println(token + " Error " + line);
                                System.out.println(token + " Error " + line);
                                output[cntRow][0] = String.valueOf(line);
                                output[cntRow][1] = token;
                                output[cntRow][2] = " Error ";
                                output[cntRow][3] = String.valueOf(line);
                                output[cntRow][4] = "Not Matched";
                                cntRow++;
                            }
                        }
                        output[cntRow][0] = String.valueOf(line);
                        output[cntRow][1] = op;
                        output[cntRow][2] = twoLetterOp.get(op);
                        output[cntRow][3] = String.valueOf(line);
                        output[cntRow][4] = "Matched";
                        cntRow++;

                        System.out.println(op + " " + twoLetterOp.get(op));
                        token = "";
                    } else if (foundOp) {
                        //code Token checking goes here
                        if (token != "") {
                            if (keywords.containsKey(token)) {
                                System.out.println(token + " " + keywords.get(token));
                                output[cntRow][0] = String.valueOf(line);
                                output[cntRow][1] = token;
                                output[cntRow][2] = keywords.get(token);
                                output[cntRow][3] = String.valueOf(line);
                                output[cntRow][4] = "Matched";
                                cntRow++;
                            } else if (isId(token) == true) {
                                System.out.println(token + " Identifier " + line);
                                System.out.println(token + "Identifier");
                                output[cntRow][0] = String.valueOf(line);
                                output[cntRow][1] = token;
                                output[cntRow][2] = "Identifier";
                                output[cntRow][3] = String.valueOf(line);
                                output[cntRow][4] = "Matched";
                                cntRow++;

                            } else if (isDigit(token) == true) {
                                System.out.println(token + " Constant " + line);
                                output[cntRow][0] = String.valueOf(line);
                                output[cntRow][1] = token;
                                output[cntRow][2] = " Constant ";
                                output[cntRow][3] = String.valueOf(line);
                                output[cntRow][4] = "Matched";
                                cntRow++;

                            } else {
                                numOfErrors++;
                                System.out.println(token + " Error " + line);
                                output[cntRow][0] = String.valueOf(line);
                                output[cntRow][1] = token;
                                output[cntRow][2] = " Error ";
                                output[cntRow][3] = String.valueOf(line);
                                output[cntRow][4] = "Not Matched";
                                cntRow++;
                            }
                        }
                        output[cntRow][0] = String.valueOf(line);
                        output[cntRow][1] = String.valueOf(character);
                        output[cntRow][2] = oneLetterOp.get(character);
                        output[cntRow][3] = String.valueOf(line);
                        output[cntRow][4] = "Matched";
                        cntRow++;
                        System.out.println(character + " operator " + oneLetterOp.get(character) + line);
                        token = "";
                        if (character2 != '\n') {
                            if (oneLetterOp.containsKey(character2)) {
                                output[cntRow][0] = String.valueOf(line);
                                output[cntRow][1] = String.valueOf(character2);
                                output[cntRow][2] = oneLetterOp.get(character2);
                                output[cntRow][3] = String.valueOf(line);
                                output[cntRow][4] = "Matched";
                                cntRow++;
                                System.out.println(character2 + " operator " + oneLetterOp.get(character2));
                            } else {
                                token += character2;
                            }
                        }
                    } else {
                        token += character;
                    }
                }
            } catch (Exception evt) {
                evt.printStackTrace();
            }
        }
        return output;
    }
}
