package com.base;

import com.base.Indexed.IndexedMethod;
import com.base.Indexed.IndexedObject;
import com.base.Indexed.Methods.MethodInteger;
import com.base.Indexed.Methods.MethodString;
import com.base.Indexed.Methods.MethodVoid;

import java.util.*;


/*
    A lot of the Util methods are not necessary, but they are left in for later use.
    Keep in mind to shorten and optimize those methods in every possible way to increase performance.
    Every unnecessary loop has to be prevented.

    The Util method has a loose order: any non-boolean method goes on top, followed by the boolean ones.
    Do pair up methods with the same purpose but different inputs.

    Some Util methods need some work as some of these are somewhat hacked in
    (See removeFromTo() or isAVariableAssignment())
 */

public class Util {

    public static String removeBrackets(String input)
    {
        input = Util.removeCharacter(input, '(');
        input = Util.removeCharacter(input, ')');
        return input;
    }

    public static String[] removeBrackets(String[] input)
    {
        input = Util.removeCharacter(input, '(');
        input = Util.removeCharacter(input, ')');
        return input;
    }

    public static ArrayList<String> removeBrackets(ArrayList<String> input)
    {
        input = Util.removeCharacter(input, '(');
        input = Util.removeCharacter(input, ')');
        return input;
    }

    public static ArrayList<String> removeFromTo(ArrayList<String> input, int start, int end)
    {
        if(end - start != 1)
            for(int i = start; i <= end; i++) {
                input.remove(start);
            }
        else
            for(int i = start; i < end; i++) {
                input.remove(start);
            }

        return input;
    }

    public static String[] removeFromTo(String[] input, int start, int end)
    {
        ArrayList<String> list = new ArrayList<>(Arrays.asList(input));
        removeFromTo(list, start, end);
        String[] array = new String[list.size()];
        list.toArray(array);
        return array;
    }

    public static String getMarkedString(String line)
    {
        int markStart = 0, markEnd = 0, counter = 1;

        for(Character c : line.toCharArray())
        {
            if(c.equals('"') && markStart == 0)
                markStart = counter;
            else if(c.equals('"') && markStart != 0)
                markEnd = counter;
            counter++;
        }

        if(counter >= line.length())
        {
            return line.substring(markStart, markEnd - 1);
        }
        return "ERROR: getMarkedString FAILED";
    }

    public static String removeCharacter(String input, Character removeChar)
    {
        String result = "";
        for(Character c : input.toCharArray())
            if(!c.equals(removeChar))
                result += c;
        return result;
    }

    public static String[] removeCharacter(String input[], Character removeChar)
    {
        for(int i = 0; i < input.length; i++)
            input[i] = removeCharacter(input[i], removeChar);
        return input;
    }

    public static ArrayList<String> removeCharacter(ArrayList<String> input, Character removeChar)
    {
        for(int i = 0; i < input.size(); i++)
            input.set(i, removeCharacter(input.get(i), removeChar));
        return input;
    }

    public static String getMarkedString(String[] tokens)
    {
        return getMarkedString(removeCharacter(Arrays.toString(tokens), ','));
    }

    public static IndexedMethod getMethod(String[] tokens, int line)
    {
        switch(tokens[1])
        {
            case "void": return new MethodVoid(line, Util.removeCharacter(tokens[2], ':'));
            case "int" : return new MethodInteger(line, Util.removeCharacter(tokens[2], ':'));
            case "String" : return new MethodString(line, Util.removeCharacter(tokens[2], ':'));
            default: return null;
        }
    }

    public static IndexedMethod getMethodByKey(String key, HashMap<String, IndexedMethod> methods)
    {
        return methods.get(Util.removeBrackets(key));
    }

    public static ArrayList<IndexedObject> toSortedArray(ArrayList<IndexedObject> input)
    {
        Collections.sort(input, new Comparator<IndexedObject>() {

            public int compare(IndexedObject o1, IndexedObject o2) {
                return o1.getLineNumber() - o2.getLineNumber();
            }
        });
        return input;
    }

    public static ArrayList<IndexedObject> toSortedArray(HashMap<String, IndexedObject> input)
    {
        ArrayList<IndexedObject> list = new ArrayList<>(input.values());

        Collections.sort(list, new Comparator<IndexedObject>() {

            public int compare(IndexedObject o1, IndexedObject o2) {
                return o1.getLineNumber() - o2.getLineNumber();
            }
        });
        return list;
    }

    public static ArrayList<IndexedObject> hashToArray(HashMap<String, IndexedObject> input)
    {
        ArrayList<IndexedObject> returnList = new ArrayList<>();

        for (String key : input.keySet()) {
            returnList.add(input.get(key));
        }
        return returnList;
    }

    public static boolean containsMethodCall(String[] possibleCallTokens, HashMap<String, IndexedMethod> methods)
    {
        boolean returnBoolean = false;

        for(String string : possibleCallTokens)
            returnBoolean = methods.get(Util.removeBrackets(string)) != null;
        return returnBoolean;
    }

    public static boolean containsPossibleMethodCall(String[] possibleCallTokens)
    {
        boolean returnBoolean = false;
        for(String string : possibleCallTokens)
            returnBoolean = string.contains("();");
        return returnBoolean;
    }

    public static boolean isInitedVar(HashMap<String, IndexedObject> variables, String variableName)
    {
        return variables.get(variableName) != null;
    }

    public static boolean isMethodCall(String possibleCall, HashMap<String, IndexedMethod> methods)
    {
        return methods.get(Util.removeBrackets(possibleCall)) != null;
    }

    public static boolean isCompleteStatement(String[] tokens)
    {
        boolean isComplete = false;

        for(String string : tokens)
            for(Character c : string.toCharArray())
                if(c.equals(';'))
                    isComplete = true;

        return isComplete;
    }

    public static boolean isVariableIniter(String[] input)
    {
        boolean returnBoolean = false;
        for(String string : input)
            if(string.equals("int") || string.equals("String"))
                returnBoolean =  true;


        return returnBoolean;
    }

    public static boolean isAReturnMethod(String possibleCall, HashMap<String, IndexedMethod> methods)
    {
        return !methods.get(Util.removeBrackets(possibleCall)).getType().equals("void");
    }

    public static boolean isInteger(String input)
    {
        for (int i = 0; i < input.length(); i++)
            if (!Character.isDigit(input.charAt(i)))
                return false;

        return true;
    }

    public static boolean isInteger(ArrayList<String> input)
    {
        boolean returnBoolean = false;
        for(String string : input)
            returnBoolean = isInteger(string);

        return returnBoolean;
    }

    public static boolean isAMathOperator(String input)
    {
        for (int i = 0; i < input.length(); i++)
            if ((input.charAt(i) == '+' || input.charAt(i) == '-') && input.length() <= 1)
                return true;

        return false;
    }


    public static boolean isAVariableAssignment(String[] tokens)
    {
        return tokens.length >= 3 && (tokens[1].equals("=") || tokens[2].equals("="));
    }

    public static boolean isCommentedOut(String line)
    {
        return line.length() > 1 && line.substring(0, 2).equals("//");
    }
}
