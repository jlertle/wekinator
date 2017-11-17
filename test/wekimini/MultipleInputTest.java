/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wekimini;

import static org.junit.Assert.assertEquals;
import wekimini.modifiers.SumInputsWindowOperation;
import wekimini.modifiers.ModifiedInput;
import wekimini.modifiers.MultipleInputWindowedOperation;
import wekimini.modifiers.PassThroughVector;

/**
 *
 * @author louismccallum
 */
public class MultipleInputTest extends ModifierTest {
    
    @Override
    public void setUpFilters(int windowSize)
    {
        w.getDataManager().featureManager.passThroughInputToOutput(false, 0);
        w.getDataManager().featureManager.removeAllModifiersFromOutput(0);
        //Sequential 1->100
        String [] names = {"1","2","3"};
        ModifiedInput raw1 = new PassThroughVector(names,0);
        raw1.inputIndex = 0;
        raw1.addToOutput = false;
        raw1.addRequiredInput(0);
        //All ones
        ModifiedInput raw2 = new PassThroughVector(names,0);
        raw2.inputIndex = 1;
        raw2.addToOutput = false;
        raw2.addRequiredInput(0);
        int rawID1 = w.getDataManager().featureManager.addModifierToOutput(raw1, 0);
        int rawID2 = w.getDataManager().featureManager.addModifierToOutput(raw2, 0);
        ModifiedInput sum = new MultipleInputWindowedOperation("input-1",new SumInputsWindowOperation(),windowSize,0);
        sum.addRequiredInput(rawID1);
        sum.addRequiredInput(rawID2);
        int id = w.getDataManager().featureManager.addModifierToOutput(sum, 0);
    }
    
    @Override
    public void testInputs(int instanceIndex, int windowSize, double[] inputs)
    {
        double sum = 0;
        int countBack = (instanceIndex + 1) < windowSize ? (instanceIndex + 1) : windowSize;
        double[] ones = new double[windowSize];
        double[] sequential = new double[windowSize];
        for(int i = 0; i < countBack; i++)
        {
            sequential[i] = ((instanceIndex+1) - i);
            ones[i] = 1.0;
        }
        for(int i = 0; i < windowSize; i++)
        {
            sum = sum + sequential[i] + ones[i];
        }
        assertEquals(sum,inputs[0],0.0);
    }
    
    @Override
    public void testNumAttributes(int numAttributes, int windowSize)
    {
        assertEquals(numAttributes,2,0.0);
    }
    

    
}
