import java.io.*;
import java.util.HashMap;

public class LexicalAnalyzer {
    static int  numOfErrors = 0;

    static String token = "";
    static char character;
    static char character2;
    static java.util.Scanner inputCode;

    static HashMap<String, String> reservedKeywords = new HashMap<>();
    static HashMap<Character, String> oneletterop2 = new HashMap<>();
    static HashMap<String, String> twoLetterOp2 = new HashMap<>();

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

    public static String[][] compile(String code) throws IOException {
        String[][] output = new String[35][5];

        reservedKeywords.put("Pattern", "Class");
        reservedKeywords.put("DerivedFrom", "Inheritance");
        reservedKeywords.put("TrueFor", "Condition");
        reservedKeywords.put("Else", "Condition");
        reservedKeywords.put("Ity", "Integer");
        reservedKeywords.put("Sity", "SignedInteger");
        reservedKeywords.put("Whatever", "Loop");
        reservedKeywords.put("Cwq", "Character");
        reservedKeywords.put("CwqSequence", "String");
        reservedKeywords.put("Ifity", "Float");
        reservedKeywords.put("SIfity", "SFloat");
        reservedKeywords.put("Valueless", "Void");
        reservedKeywords.put("Logical", "Boolean");
        reservedKeywords.put("Respondwith", "Return");
        reservedKeywords.put("Srap", "Struct");
        reservedKeywords.put("Scan", "Switch");
        reservedKeywords.put("Conditionof", "Switch");
        reservedKeywords.put("Require", "Inclusion");


        oneletterop2.put('^', "Line Delimiter");
        oneletterop2.put('@', "Start Symbol");
        oneletterop2.put('#', "Token Delimiter");
        oneletterop2.put('@', "Start Program");
        oneletterop2.put('~', "Logical Operator");
        oneletterop2.put('(', "Brace"); //begin the condition
        oneletterop2.put(')', "Brace"); //end the condition
        oneletterop2.put('{', "Brace"); //begin the loop
        oneletterop2.put('}', "Brace"); //end for loop
        oneletterop2.put('.', "Line Delimiter");
        oneletterop2.put('=', "Assignment Operator");
        oneletterop2.put('<', "relational Operator");
        oneletterop2.put('+', "Arithmetic Operator");
        oneletterop2.put('-', "Arithmetic Operator");
        oneletterop2.put('*', "Arithmetic Operator");
        oneletterop2.put('/', "Arithmetic Operator");
        oneletterop2.put('!', "Logical Operator");


        twoLetterOp2.put("==", "relational operator");
        twoLetterOp2.put("!=", "relational operator");
        twoLetterOp2.put("->", "Access Operator");
        twoLetterOp2.put("--", "Comment");
        twoLetterOp2.put("/-", "Comment");
        twoLetterOp2.put("&&", "Logical Operator");
        twoLetterOp2.put("||", "Logical Operator");

        int line = 0;

            try {
                int c = 0;
                String op = "";
                int cntRow = 0;
                Reader inputString = new StringReader(code);
                BufferedReader br = new BufferedReader(inputString);
                System.out.println(code);
                while ((c = br.read()) != -1) {
                    boolean foundOp = false;
                    boolean foundOp2 = false;
                    character = (char) c;
                    System.out.println(character);

                    if (character == '\n') {
                        line++;
                        continue;
                    }
                    if (oneletterop2.containsKey(character)) {
                        op = "";
                        foundOp = true;
                        op = op + character;
                        if ((c = br.read()) != -1) {
                            character2 = (char) c;
                            if (character2 != '\n') {
                                op += character2;
                                if (twoLetterOp2.containsKey((op))) {
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
                            if (reservedKeywords.containsKey(token)) {
                                output[cntRow][0] = String.valueOf(line);
                                output[cntRow][1] = token;
                                output[cntRow][2] = reservedKeywords.get(token);
                                output[cntRow][3] = String.valueOf(line);
                                output[cntRow][4] = "Matched";
                                cntRow++;
                                System.out.println(token + " " + reservedKeywords.get(token));
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
                        output[cntRow][2] = twoLetterOp2.get(op);
                        output[cntRow][3] = String.valueOf(line);
                        output[cntRow][4] = "Matched";
                        cntRow++;

                        System.out.println(op + " " + twoLetterOp2.get(op));
                        token = "";
                    } else if (foundOp) {
                        //code Token checking goes here
                        if (token != "") {
                            if (reservedKeywords.containsKey(token)) {
                                System.out.println(token + " " + reservedKeywords.get(token));
                                output[cntRow][0] = String.valueOf(line);
                                output[cntRow][1] = token;
                                output[cntRow][2] = reservedKeywords.get(token);
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
                        output[cntRow][2] = oneletterop2.get(character);
                        output[cntRow][3] = String.valueOf(line);
                        output[cntRow][4] = "Matched";
                        cntRow++;
                        System.out.println(character + " operator " + oneletterop2.get(character) + line);
                        token = "";
                        if (character2 != '\n') {
                            if (oneletterop2.containsKey(character2)) {
                                output[cntRow][0] = String.valueOf(line);
                                output[cntRow][1] = String.valueOf(character2);
                                output[cntRow][2] = oneletterop2.get(character2);
                                output[cntRow][3] = String.valueOf(line);
                                output[cntRow][4] = "Matched";
                                cntRow++;
                                System.out.println(character2 + " operator " + oneletterop2.get(character2));
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

        return output;
    }
}
