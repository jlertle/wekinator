/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wekimini.modifiers;

import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author louismccallum
 */

public class Feature 
{
    public enum InputSensor {
        ACCX,ACCY,ACCZ,GYROX,GYROY,GYROZ,EMG1,EMG2,EMG3,EMG4,EMG5,EMG6,EMG7,EMG8,MULTIPLE,UNKNOWN
    }
    
    public final String name;
    public final ArrayList<Integer> modifierIds = new ArrayList();
    public InputSensor sensor = InputSensor.UNKNOWN;
    public ArrayList<String> tags = new ArrayList();
    public String description;
    private ArrayList <Integer> outputIndexes = new ArrayList();
    
//    public enum InputSensor {
//        ACCX,ACCY,ACCZ,GYROX,GYROY,GYROZ,MULTIPLE,UNKNOWN
//    }
    
    public Feature(String name)
    {
        this.name = name;
    }
    
    public void addFeature(ModifierCollection mc)
    {
        
    }  
    
    public int addModifier(ModifierCollection mc, ModifiedInput mod)
    {
        int id = mc.addModifier(mod);
        modifierIds.add(id);
        return id;
    } 
    
    public Integer[] getOutputIndexes()
    {
        Integer[] arr = new Integer[outputIndexes.size()];
        arr = outputIndexes.toArray(arr);
        return arr;
    }
    
    public void setOutputIndexes(ArrayList<Integer> arr)
    {
        outputIndexes = arr;
    }
    
    @Override
    public String toString()
    {
        return name;
    }
    
    @Override
    public boolean equals(Object ft)
    {
        if(!(ft instanceof Feature))
        {
            return false;
        }
        return name.equals(((Feature)ft).name);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.name);
        return hash;
    }
}