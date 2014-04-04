package com.base.Indexed.Methods;

import com.base.Indexed.IndexedMethod;
import com.base.Indexed.IndexedObject;
import com.base.Util;

import java.util.ArrayList;
import java.util.HashMap;

public class MethodVoid extends IndexedMethod {

    private int headerLineNumber;
    private String name;
    private Integer braceStart, braceEnd;
    private ArrayList<IndexedObject> objects;
    private HashMap<String, IndexedObject> variables;

    public MethodVoid(int headerLineNumber, String name, int braceStart, int braceEnd, ArrayList<IndexedObject> objects, HashMap<String, IndexedObject> variables)
    {
        this.headerLineNumber = headerLineNumber;
        this.name = name;
        this.braceStart = braceStart;
        this.braceEnd = braceEnd;
        this.objects = objects;
        this.variables = variables;
    }

    public MethodVoid(int headerLineNumber, String name)
    {
        this.headerLineNumber = headerLineNumber;
        this.name = name;
    }

    public MethodVoid()
    {}

    @Override
    public void call()
    {
        System.out.println("Method called:" + this);
    }

    public String toString() {
        return "[Head line:" + getHeaderLineNumber() + " | Start line:" + braceStart + " | End line:" + braceEnd + " | Method type:" + getType() + " | Name: " + name + " | Has content: " + (braceStart != null && braceEnd != null) + "]";
    }

    public void setHeaderLineNumber(int lineNumber) {
        headerLineNumber = lineNumber;
    }
    public int getHeaderLineNumber() {
        return headerLineNumber;
    }

    public ArrayList<IndexedObject> getObjects() {
        return objects;
    }
    public IndexedObject getVariablesWithKey(String name) {
        return variables.get(name);
    }

    public HashMap<String, IndexedObject> getVariables() {
        return variables;
    }
    public IndexedObject getObjectAt(int index) {
        return objects.get(index);
    }

    public void setObjects(ArrayList<IndexedObject> objects) {
        this.objects = objects;
    }
    public void addObject(IndexedObject object) {
        objects.add(object);
    }

    public void setVariables(HashMap<String, IndexedObject> variables) {
        this.variables = variables;
    }
    public void setVariable(String name, IndexedObject variable) {
        variables.put(name, variable);
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Integer getBraceStart() {
        return braceStart;
    }
    public void setBraceStart(Integer braceStart) {
        this.braceStart = braceStart;
    }

    public Integer getBraceEnd() {
        return braceEnd;
    }
    public void setBraceEnd(Integer braceEnd) {
        this.braceEnd = braceEnd;
    }

    public String getType()
    {
        return "void";
    }

    public IndexedObject getReturnObject()
    {
        return null;
    }

    public ArrayList<IndexedObject> getCombinedObjects()
    {
        ArrayList<IndexedObject> combinedObjects = objects;
        //add the variables to ArrayList
        combinedObjects.addAll(Util.hashToArray(variables));

        //sort combinedObjects by line number (see Util class)
        combinedObjects = Util.toSortedArray(combinedObjects);

        return combinedObjects;
    }

}
