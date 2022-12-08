import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Analyzer {
    public final String identifier = "$";
    public final String hexadecimalNum = "0123456789ABCDEF";
    public final String nums = "0123456789";
    public final String letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public final String operator = "+-*<>=^!&|";
    public final List<String> keyword = Arrays.asList("abstract", "and", "array", "as",
            "break", "callable", "case", "catch", "class", "clone", "const", "continue",
            "declare", "default", "die", "do", "echo", "else", "elseif", "empty", "enddeclare",
            "endfor", "endforeach", "endif", "endswitch", "endwhile", "eval", "exit", "extends",
            "final", "finally", "fn", "for", "foreach", "function", "global", "goto", "if", "implements",
            "include", "include_once", "instanceof", "insteadof", "interface", "isset", "list",
            "match", "namespace", "new", "or", "print", "private", "protected", "public", "readonly", "return",
            "require", "switch", "throw", "trait", "try", "unset", "use", "var", "while", "xor", "yield", "yield from$");
    public final String delimiter = ";:,";
    public final String brackets = "(){}[]";

    private String inputString;
    int q = 0;
    ArrayList<Token> allLexemes;

    public Analyzer(String inputString) {
        this.inputString = inputString;
        allLexemes = new ArrayList<>();
    }

    public void analysisResult() {
        String temp = "";

        for (int i = 0; i < inputString.length(); i++) {
            switch (q) {
                case 0:
                    if (Character.toString(inputString.charAt(i)).equals(identifier)) {
                        temp += inputString.charAt(i);
                        q = 1;
                    } else if (Character.toString(inputString.charAt(i)).equals("0")) {
                        temp += inputString.charAt(i);
                        q = 7;
                    } else if (nums.contains(Character.toString(inputString.charAt(i)))) {
                        temp += inputString.charAt(i);
                        q = 4;
                    } //ПЕРЕВІРИТИ
                    else if (delimiter.contains(Character.toString(inputString.charAt(i))) ||
                            brackets.contains(Character.toString(inputString.charAt(i)))) {
                        temp += inputString.charAt(i);
                        if (delimiter.contains(Character.toString(inputString.charAt(i))))
                            allLexemes.add(new Token(temp, Lexeme.delimiter));
                        else if (brackets.contains(Character.toString(inputString.charAt(i))))
                            allLexemes.add(new Token(temp, Lexeme.brackets));
                        temp = "";
                        q = 0;
                    } else if (Character.toString(inputString.charAt(i)).equals(" ")) {
                        q = 0;
                    } else if (letters.contains(Character.toString(inputString.charAt(i)))) {
                        temp += inputString.charAt(i);
                        q = 6;
                    } else if (Character.toString(inputString.charAt(i)).equals("\"")) {
                        temp += inputString.charAt(i);
                        q = 9;
                    } else if(Character.toString(inputString.charAt(i)).equals("#")){
                        temp += inputString.charAt(i);
                        q = 10;
                    } else if(Character.toString(inputString.charAt(i)).equals("/")){
                        temp += inputString.charAt(i);
                        q = 11;
                    } else if(operator.contains(Character.toString(inputString.charAt(i)))){
                        temp += inputString.charAt(i);
                        q = 12;
                    } else {
                        temp += inputString.charAt(i);
                        q = 3;
                    }
                    break;

                //identifier
                case 1:
                    if (letters.contains(Character.toString(inputString.charAt(i)))) {
                        temp += inputString.charAt(i);
                        q = 2;
                    } else {
                        temp += inputString.charAt(i);
                        q = 3; //error
                    }
                    break;
                case 2:
                    if (letters.contains(Character.toString(inputString.charAt(i))) ||
                            nums.contains(Character.toString(inputString.charAt(i))) ||
                            Character.toString(inputString.charAt(i)).equals("_")) {
                        temp += inputString.charAt(i);
                        q = 2;
                    } else if (delimiter.contains(Character.toString(inputString.charAt(i))) ||
                            brackets.contains(Character.toString(inputString.charAt(i)))) {
                        allLexemes.add(new Token(temp, Lexeme.identifier));
                        temp = "";
                        temp += inputString.charAt(i);
                        if (delimiter.contains(Character.toString(inputString.charAt(i))))
                            allLexemes.add(new Token(temp, Lexeme.delimiter));
                        else if (brackets.contains(Character.toString(inputString.charAt(i))))
                            allLexemes.add(new Token(temp, Lexeme.brackets));
                        temp = "";
                        q = 0;
                    } else if (Character.toString(inputString.charAt(i)).equals(" ")) {
                        allLexemes.add(new Token(temp, Lexeme.identifier));
                        temp = "";
                        q = 0;
                    } else {
                        temp += inputString.charAt(i);
                        q = 3; //error
                    }
                    break;

                //error
                case 3:
                    allLexemes.add(new Token(temp, Lexeme.error));
                    temp = "";
                    q = 0;
                    break;

                //decimal or float number
                case 4:
                    if (nums.contains(Character.toString(inputString.charAt(i)))) {
                        temp += inputString.charAt(i);
                        q = 4;
                    } else if (Character.toString(inputString.charAt(i)).equals(".")) {
                        temp += inputString.charAt(i);
                        q = 5;
                    } else if (delimiter.contains(Character.toString(inputString.charAt(i))) ||
                            brackets.contains(Character.toString(inputString.charAt(i)))) {
                        allLexemes.add(new Token(temp, Lexeme.decNum));
                        temp = "";
                        temp += inputString.charAt(i);
                        if (delimiter.contains(Character.toString(inputString.charAt(i))))
                            allLexemes.add(new Token(temp, Lexeme.delimiter));
                        else if (brackets.contains(Character.toString(inputString.charAt(i))))
                            allLexemes.add(new Token(temp, Lexeme.brackets));
                        temp = "";
                        q = 0;
                    } else if (Character.toString(inputString.charAt(i)).equals(" ")) {
                        allLexemes.add(new Token(temp, Lexeme.decNum));
                        temp = "";
                        q = 0;
                    }
                    //прописати далі
                    else {
                        temp += inputString.charAt(i);
                        q = 3; //error
                    }
                    break;
                case 5:
                    if (nums.contains(Character.toString(inputString.charAt(i)))) {
                        temp += inputString.charAt(i);
                        q = 5;
                    } else if (delimiter.contains(Character.toString(inputString.charAt(i))) ||
                            brackets.contains(Character.toString(inputString.charAt(i)))) {
                        allLexemes.add(new Token(temp, Lexeme.floatNum));
                        temp = "";
                        temp += inputString.charAt(i);
                        if (delimiter.contains(Character.toString(inputString.charAt(i))))
                            allLexemes.add(new Token(temp, Lexeme.delimiter));
                        else if (brackets.contains(Character.toString(inputString.charAt(i))))
                            allLexemes.add(new Token(temp, Lexeme.brackets));
                        temp = "";
                        q = 0;
                    } else if (Character.toString(inputString.charAt(i)).equals(" ")) {
                        allLexemes.add(new Token(temp, Lexeme.floatNum));
                        temp = "";
                        q = 0;
                    }
                    //прописати далі
                    else {
                        temp += inputString.charAt(i);
                        q = 3; //error
                    }
                    break;

                //words
                case 6:
                    if (letters.contains(Character.toString(inputString.charAt(i)))) {
                        temp += inputString.charAt(i);
                        q = 6;
                    } else if (delimiter.contains(Character.toString(inputString.charAt(i))) ||
                            brackets.contains(Character.toString(inputString.charAt(i)))) {
                        if (keyword.contains(temp)) allLexemes.add(new Token(temp, Lexeme.keyword));
                        else allLexemes.add(new Token(temp, Lexeme.error));
                        temp = "";
                        temp += inputString.charAt(i);
                        if (delimiter.contains(Character.toString(inputString.charAt(i))))
                            allLexemes.add(new Token(temp, Lexeme.delimiter));
                        else if (brackets.contains(Character.toString(inputString.charAt(i))))
                            allLexemes.add(new Token(temp, Lexeme.brackets));
                        temp = "";
                        q = 0;
                    } else if (Character.toString(inputString.charAt(i)).equals(" ")) {
                        if (keyword.contains(temp)) allLexemes.add(new Token(temp, Lexeme.keyword));
                        else allLexemes.add(new Token(temp, Lexeme.error));
                        temp = "";
                        q = 0;
                    } else {
                        temp += inputString.charAt(i);
                        q = 3; //error
                    }
                    break;

                //hexadecimal numbers
                case 7:
                    if (Character.toString(inputString.charAt(i)).equals("x")) {
                        temp += inputString.charAt(i);
                        q = 8;
                    } else if (Character.toString(inputString.charAt(i)).equals(".")) {
                        temp += inputString.charAt(i);
                        q = 5;
                    } else if (nums.contains(Character.toString(inputString.charAt(i)))) {
                        temp += inputString.charAt(i);
                        q = 4;
                    } else {
                        temp += inputString.charAt(i);
                        q = 3; //error
                    }
                    break;
                case 8:
                    if (hexadecimalNum.contains(Character.toString(inputString.charAt(i)))) {
                        temp += inputString.charAt(i);
                        q = 8;
                    } else if (delimiter.contains(Character.toString(inputString.charAt(i))) ||
                            brackets.contains(Character.toString(inputString.charAt(i)))) {
                        allLexemes.add(new Token(temp, Lexeme.hexadecimalNum));
                        temp = "";
                        temp += inputString.charAt(i);
                        if (delimiter.contains(Character.toString(inputString.charAt(i))))
                            allLexemes.add(new Token(temp, Lexeme.delimiter));
                        else if (brackets.contains(Character.toString(inputString.charAt(i))))
                            allLexemes.add(new Token(temp, Lexeme.brackets));
                        temp = "";
                        q = 0;
                    } else if (Character.toString(inputString.charAt(i)).equals(" ")) {
                        allLexemes.add(new Token(temp, Lexeme.hexadecimalNum));
                        temp = "";
                        q = 0;
                    } else {
                        temp += inputString.charAt(i);
                        q = 3; //error
                    }
                    break;

                //custom string
                case 9:
                    if (Character.toString(inputString.charAt(i)).equals("\"")) {
                        temp += inputString.charAt(i);
                        allLexemes.add(new Token(temp, Lexeme.customString));
                        temp = "";
                        q = 0;
                    } else if (inputString.length() == i + 1) {
                        temp += inputString.charAt(i);
                        q = 3;
                    } else {
                        temp += inputString.charAt(i);
                        q = 9;
                    }
                    break;

                //comments
                case 10:
                    if(inputString.length() == i + 1) {
                        temp += inputString.charAt(i);
                        allLexemes.add(new Token(temp, Lexeme.comment));
                        temp = "";
                        q = 0;
                    } else {
                        temp += inputString.charAt(i);
                        q = 10;
                    }
                    break;
                case 11:
                    if(Character.toString(inputString.charAt(i)).equals("/")) {
                        temp += inputString.charAt(i);
                        q = 10;
                    } else if (Character.toString(inputString.charAt(i)).equals("*")) {
                        temp += inputString.charAt(i);
                        q = 13;
                    } else if (Character.toString(inputString.charAt(i)).equals(" ")) {
                        temp += inputString.charAt(i);
                        allLexemes.add(new Token(temp, Lexeme.operator));
                        temp = "";
                        q = 0;
                    } else if (operator.contains(Character.toString(inputString.charAt(i)))) {
                        temp += inputString.charAt(i);
                        q = 12;
                    } else {
                        temp += inputString.charAt(i);
                        q = 3;
                    }
                    break;
                case 13:
                    if(Character.toString(inputString.charAt(i)).equals("*")) {
                        temp += inputString.charAt(i);
                        q = 14;
                    } else {
                        temp += inputString.charAt(i);
                        q = 13;
                    }
                    break;
                case 14:
                    if(Character.toString(inputString.charAt(i)).equals("/")) {
                        temp += inputString.charAt(i);
                        allLexemes.add(new Token(temp, Lexeme.comment));
                        temp = "";
                        q = 0;
                    } else {
                        temp += inputString.charAt(i);
                        q = 3;
                    }
                    
                //operators
                case 12:
                    if(operator.contains(Character.toString(inputString.charAt(i)))){
                        temp += inputString.charAt(i);
                        q = 12;
                    } else if (delimiter.contains(Character.toString(inputString.charAt(i))) ||
                            brackets.contains(Character.toString(inputString.charAt(i)))) {
                        allLexemes.add(new Token(temp, Lexeme.operator));
                        temp = "";
                        temp += inputString.charAt(i);
                        if (delimiter.contains(Character.toString(inputString.charAt(i))))
                            allLexemes.add(new Token(temp, Lexeme.delimiter));
                        else if (brackets.contains(Character.toString(inputString.charAt(i))))
                            allLexemes.add(new Token(temp, Lexeme.brackets));
                        temp = "";
                        q = 0;
                    } else if (Character.toString(inputString.charAt(i)).equals(" ")) {
                        allLexemes.add(new Token(temp, Lexeme.operator));
                        temp = "";
                        q = 0;
                    }
                    break;
            }
        }
    }
}
