/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wekimini.learning;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;
import org.jdesktop.swingworker.SwingWorker;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import wekimini.LearningModelBuilder;
import wekimini.Path;
import wekimini.Wekinator;
import wekimini.gui.ModelEvaluationFrame.EvaluationMode;
import wekimini.kadenze.KadenzeLogging;
import wekimini.util.Util;

/**
 *
 * @author rebecca
 */
public class ModelEvaluator {

    public static boolean hasWarned = false;
    public static boolean isEvaluating = false;
    protected EvaluationStatus evaluationStatus = new EvaluationStatus();
    public static final String PROP_PROGRESS = "progress";
    private transient SwingWorker evalWorker = null;

    private transient final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    protected EventListenerList cancelListenerList = new EventListenerList();
    private ChangeEvent cancelEvent = null;
    private boolean wasCancelled = false;
    private boolean hadError = false;
    private static final Logger logger = Logger.getLogger(ModelEvaluator.class.getName());
    private final Wekinator w;
    private String[] results;
    private final EvaluationResultsReceiver receiver;
    private static final DecimalFormat dFormat = new DecimalFormat(" #.##;-#.##");
    public int[] givenIndices = new int[0];

    public static final String PROP_RESULTS = "results";

    /**
     * Get the value of results
     *
     * @return the value of results
     */
    public String[] getResults() {
        return results;
    }

    /**
     * Set the value of results
     *
     * @param results new value of results
     */
    public void setResults(String[] results) {
        String[] oldResults = this.results;
        this.results = results;
        propertyChangeSupport.firePropertyChange(PROP_RESULTS, oldResults, results);
    }

    /**
     * Get the value of results at specified index
     *
     * @param index the index of results
     * @return the value of results at specified index
     */
    public String getResults(int index) {
        return this.results[index];
    }

    /**
     * Set the value of results at specified index.
     *
     * @param index the index of results
     * @param results new value of results at specified index
     */
    public void setResults(int index, String results) {
        String oldResults = this.results[index];
        this.results[index] = results;
        propertyChangeSupport.fireIndexedPropertyChange(PROP_RESULTS, index, oldResults, results);
    }

    
    public ModelEvaluator(Wekinator w, EvaluationResultsReceiver r) {
        this.w = w;
        this.receiver = r;
    }
    
    public boolean wasCancelled() {
        return wasCancelled;
    }
    
    public boolean errorEncountered(){
        return hadError;
    }

    public EvaluationStatus getEvaluationStatus() {
        return evaluationStatus;
    }
    
    public void addCancelledListener(ChangeListener l) {
        cancelListenerList.add(ChangeListener.class, l);
    }

    public void removeCancelledListener(ChangeListener l) {
        cancelListenerList.remove(ChangeListener.class, l);
    }
    
    private void fireCancelled() {
        Object[] listeners = cancelListenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == ChangeListener.class) {
                if (cancelEvent == null) {
                    cancelEvent = new ChangeEvent(this);
                }
                ((ChangeListener) listeners[i + 1]).stateChanged(cancelEvent);
            }
        }
    }
        
            /**
     * Add PropertyChangeListener.
     *
     * @param listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Remove PropertyChangeListener.
     *
     * @param listener
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
    
    public void cancel() {
        evalWorker.cancel(true);
        receiver.cancelled();
        //No need to call "fireCancelled": worker itself will do this.
    }

    public void setEvalStatus(EvaluationStatus evalStatus) {
        EvaluationStatus oldEvalStatus = this.evaluationStatus;
        this.evaluationStatus = evalStatus;
        propertyChangeSupport.firePropertyChange(PROP_PROGRESS, oldEvalStatus, evaluationStatus);
    }
    
    private void cancelMe(Path p) {
        w.getStatusUpdateCenter().update(this, "Evaluation was cancelled");
        wasCancelled = true;
        fireCancelled();
    }
    
    public void evaluateAll(final List<Path> paths, final EvaluationMode mode, final int numFolds, PropertyChangeListener listener) {

        setResults(new String[paths.size()]);
        if (evalWorker != null && evalWorker.getState() != SwingWorker.StateValue.DONE) {
            return;
        }
        
        evalWorker = new SwingWorker<Integer, Void>() {

            //trainingWorker.
            @Override
            public Integer doInBackground() {
                // train(); //TODO: Add status updates
                int progress = 0;
                //setProgress(progress);
                int numToEvaluate = 0;
                for (Path p : paths) {
                    if (p.canBuild()) {
                        numToEvaluate++;
                    }
                }

                int numEvaluated = 0;
                int numErr = 0;
                setEvalStatus(new EvaluationStatus(numToEvaluate, numEvaluated, numErr, false));

                for (int i = 0; i < paths.size(); i++) {
                    Path p = paths.get(i);
                    if (p.canBuild()) {
                        try {
                            System.out.println("Evaluating with " + numFolds);
                            //EVALUATE HERE: TODO 
                            Instances instances;
                            if(givenIndices.length == 0)
                            {
                                instances = w.getSupervisedLearningManager().getTrainingDataForPath(p, false);
                            }
                            else
                            {
                                instances = w.getDataManager().getFeaturesInstancesFromIndices(givenIndices, i, false);
                            }
                            System.out.println("Got training data");
                            Evaluation eval = new Evaluation(instances);
                            Classifier c = ((LearningModelBuilder)p.getModelBuilder()).getClassifier();
                            System.out.println("Got classifier");
                            Classifier c2;
                            switch(mode)
                            {
                                case CROSS_VALIDATION:
                                    Random r = new Random();
                                    System.out.println("Cross validating Model");
                                    eval.crossValidateModel(c, instances, numFolds, r);
                                    System.out.println("Cross validated Model");
                                    break;
                                case TRAINING_SET:
                                    c2 = Classifier.makeCopy(c);
                                    System.out.println("Building classifier");
                                    c2.buildClassifier(instances);
                                    System.out.println("Built classifier");
                                    eval.evaluateModel(c2, instances);
                                    System.out.println("Evaluated Model");
                                    break;
                                case TESTING_SET:
                                    Instances testingInstances;
                                    if(givenIndices.length == 0)
                                    {
                                        testingInstances = w.getSupervisedLearningManager().getTestingDataForPath(p, false);
                                    }
                                    else
                                    {
                                        testingInstances = w.getDataManager().getFeaturesInstancesFromIndices(givenIndices, i, true);
                                    }
                                    eval = new Evaluation(testingInstances);
                                    c2 = Classifier.makeCopy(c);
                                    c2.buildClassifier(instances);
                                    eval.evaluateModel(c2, testingInstances);
                            }
                            String result;
                            if (p.getModelBuilder() instanceof ClassificationModelBuilder) {
                                result = dFormat.format(eval.pctCorrect()) + "%"; //WON"T WORK FOR NN
                            } else {
                                result = dFormat.format(eval.errorRate()) + " (RMS)";
                            }
                            if (mode == EvaluationMode.CROSS_VALIDATION) {
                                KadenzeLogging.getLogger().crossValidationComputed(w, i, numFolds, result);
                            } else {
                                KadenzeLogging.getLogger().trainingAccuracyComputed(w, i, result);
                            }
                            setResults(i, result);
                            String confusion = "";
                            try {
                                confusion = eval.toMatrixString();
                            } 
                            catch (Exception e)
                            {
                                
                            }
                            finishedModel(i, result, confusion);

                            numEvaluated++;

                            if (isCancelled()) {
                                cancelMe(p);
                                setResults(i, "Cancelled");
                                return 0;
                            }

                        } catch (InterruptedException ex) {
                            cancelMe(p);
                            setResults(i, "Cancelled");
                            return 0; //Not sure this will be called...
                        } catch (Exception ex) {
                            numErr++;
                            Util.showPrettyErrorPane(null, "Error encountered during evaluation " + p.getCurrentModelName() + ": " + ex.getMessage());
                            logger.log(Level.SEVERE, ex.getMessage());
                        }
                        setEvalStatus(new EvaluationStatus(numToEvaluate, numEvaluated, numErr, false));
                    } else {
                        logger.log(Level.WARNING, "Could not evaluate path");
                    }

                }
                wasCancelled = false;
                hadError = evaluationStatus.numErrorsEncountered > 0;
                return 0;
            }

            @Override
            public void done() {
                if (isCancelled()) {
                    EvaluationStatus t = new EvaluationStatus(evaluationStatus.numToEvaluate, evaluationStatus.numEvaluated, evaluationStatus.numErrorsEncountered, true);
                    setEvalStatus(t);
                }
                finished();
            }
        };
        evalWorker.addPropertyChangeListener(listener);
        evalWorker.execute();
    }

    public static boolean isEvaluating() {
        return isEvaluating;
    }
    
    protected void finishedModel(int modelNum, String result, String confusion) {
        receiver.finishedModel(modelNum, result, confusion);
    }
    
    protected void finished() {
        receiver.finished(results);
    }

    public interface EvaluationResultsReceiver {
        public void finishedModel(int modelNum, String results, String confusion);
        public void finished(String[] results);
        public void cancelled();
    }

    public static class EvaluationStatus {

        private int numToEvaluate = 0;
        private int numEvaluated = 0;
        private int numErrorsEncountered = 0;
        private boolean wasCancelled = false;

        public EvaluationStatus(int numToEvaluate, int numEvaluated, int numErr, boolean wasCancelled) {
            this.numToEvaluate = numToEvaluate;
            this.numEvaluated = numEvaluated;
            this.numErrorsEncountered = numErr;
            this.wasCancelled = wasCancelled;
        }
        
        public EvaluationStatus() {
        }

        public int getNumToEvaluate() {
            return numToEvaluate;
        }

        public int getNumEvaluated() {
            return numEvaluated;
        }

        public int getNumErrorsEncountered() {
            return numErrorsEncountered;
        }

        public boolean wasCancelled() {
            return wasCancelled;
        }

        @Override
        public String toString() {
            return numEvaluated + "/" + numToEvaluate + " evaluated, " + numErrorsEncountered + " errors, cancelled=" + wasCancelled;
        }
    }

}
