/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wekimini.gui;

import java.awt.CardLayout;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import wekimini.learning.ModelEvaluator;
import wekimini.learning.ModelEvaluator.EvaluationResultsReceiver;
import wekimini.Path;
import wekimini.SupervisedLearningManager;
import wekimini.SupervisedLearningManager.LearningState;
import wekimini.Wekinator;
import wekimini.kadenze.KadenzeLogger;
import wekimini.kadenze.KadenzeLogging;
import wekimini.util.Util;

/**
 *
 * @author rebecca
 */

public class ModelEvaluationFrame extends javax.swing.JFrame {

    private static final Logger logger = Logger.getLogger(ModelEvaluationFrame.class.getName());

    private final String[] outputNames = null;
    private final Wekinator w;
    private final LinkedList<NameValueRow> rows = new LinkedList<>();
    private boolean isComputing = false;
    private ModelEvaluator e = null;
    int numModelsToCompute = 0;
    int modelsComputed = 0;
    int singleModelOffset = 0;
    private boolean wasCancelled = false;
    
    public enum EvaluationMode {
        CROSS_VALIDATION, TRAINING_SET, TESTING_SET
    }

    /**
     * Creates new form PathEditorFrame
     */
    public ModelEvaluationFrame() {
        Wekinator ww = null;
        initComponents();
        try {
            ww = Wekinator.TestingWekinator();
        } catch (IOException ex) {
            Logger.getLogger(ModelEvaluationFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        w = ww;
        testSetSizeLabel.setText(Integer.toString(w.getDataManager().getTestingDataForOutput(0).numInstances()));
    }

    public ModelEvaluationFrame(String[] outputNames, Wekinator w) {
        initComponents();
        this.w = w;
        initModelDropdown(outputNames);
        initRows(outputNames);
        setTitle("Model evaluation");
        testSetSizeLabel.setText(Integer.toString(w.getDataManager().getTestingDataForOutput(0).numInstances()));
    }

    private void initModelDropdown(String[] outputNames) {
        String[] options = new String[outputNames.length + 1];
        options[0] = "All models";
        System.arraycopy(outputNames, 0, options, 1, outputNames.length);
        comboModelToEvaluate.setModel(new javax.swing.DefaultComboBoxModel(options));
        comboModelToEvaluate.repaint();
    }


    /*public void initInputList() {
     String[] inputNames = p.getSelectedInputs();
     labelConnectedInputs.setText(inputNames.length + " connected inputs:");
        
     panelInputList.removeAll();
     for (int i = 0; i < inputNames.length; i++) {
     panelInputList.add(new JLabel(inputNames[i]));
     }
     panelInputList.repaint();
     panelInputList.validate();
     scrollPaneInputs.revalidate();
        
     } */
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        scrollPaneInputs = new javax.swing.JScrollPane();
        panelResultsList = new javax.swing.JPanel();
        nameValueRow1 = new wekimini.gui.NameValueRow();
        nameValueRow2 = new wekimini.gui.NameValueRow();
        nameValueRow3 = new wekimini.gui.NameValueRow();
        nameValueRow4 = new wekimini.gui.NameValueRow();
        jPanel5 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        comboModelToEvaluate = new javax.swing.JComboBox();
        labelConnectedInputs = new javax.swing.JLabel();
        panelFoldsParent = new javax.swing.JPanel();
        cardFolds = new javax.swing.JPanel();
        cardNone = new javax.swing.JPanel();
        recordingButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        labelConnectedInputs3 = new javax.swing.JLabel();
        labelConnectedInputs2 = new javax.swing.JLabel();
        labelConnectedInputs1 = new javax.swing.JLabel();
        textNumFolds = new javax.swing.JTextField();
        comboMethod = new javax.swing.JComboBox();
        progressBar = new javax.swing.JProgressBar();
        buttonCompute = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        testSetSizeLabel = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        panelResultsList.setBackground(new java.awt.Color(255, 255, 255));
        panelResultsList.setLayout(new javax.swing.BoxLayout(panelResultsList, javax.swing.BoxLayout.Y_AXIS));
        panelResultsList.add(nameValueRow1);
        panelResultsList.add(nameValueRow2);
        panelResultsList.add(nameValueRow3);
        panelResultsList.add(nameValueRow4);

        scrollPaneInputs.setViewportView(panelResultsList);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        comboModelToEvaluate.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        labelConnectedInputs.setText("Evaluate:");

        panelFoldsParent.setLayout(new java.awt.CardLayout());

        cardFolds.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout cardFoldsLayout = new javax.swing.GroupLayout(cardFolds);
        cardFolds.setLayout(cardFoldsLayout);
        cardFoldsLayout.setHorizontalGroup(
            cardFoldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 112, Short.MAX_VALUE)
        );
        cardFoldsLayout.setVerticalGroup(
            cardFoldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
        );

        panelFoldsParent.add(cardFolds, "cardFolds");

        cardNone.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout cardNoneLayout = new javax.swing.GroupLayout(cardNone);
        cardNone.setLayout(cardNoneLayout);
        cardNoneLayout.setHorizontalGroup(
            cardNoneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 112, Short.MAX_VALUE)
        );
        cardNoneLayout.setVerticalGroup(
            cardNoneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
        );

        panelFoldsParent.add(cardNone, "cardNone");

        recordingButton.setLabel("Start Recording Test Set");
        recordingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recordingButtonActionPerformed(evt);
            }
        });

        deleteButton.setText("Delete Test Set");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        labelConnectedInputs3.setText("folds");

        labelConnectedInputs2.setText("with");

        labelConnectedInputs1.setText("Method:");

        textNumFolds.setText("10");
        textNumFolds.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textNumFoldsKeyTyped(evt);
            }
        });

        comboMethod.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Cross-validation accuracy", "Training accuracy", "Testing Accuracy" }));
        comboMethod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboMethodActionPerformed(evt);
            }
        });

        buttonCompute.setText("Compute now");
        buttonCompute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonComputeActionPerformed(evt);
            }
        });

        jLabel2.setText("Test Set");

        testSetSizeLabel.setText("0");

        jLabel4.setText("examples");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(labelConnectedInputs)
                                .addGap(1, 1, 1)
                                .addComponent(comboModelToEvaluate, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15)
                                .addComponent(panelFoldsParent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(recordingButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(deleteButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(testSetSizeLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel4)))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(buttonCompute, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(labelConnectedInputs1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboMethod, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelConnectedInputs2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(textNumFolds, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelConnectedInputs3)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(testSetSizeLabel)
                            .addComponent(jLabel4))
                        .addGap(9, 9, 9)
                        .addComponent(panelFoldsParent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(28, 28, 28))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(recordingButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(deleteButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelConnectedInputs, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboModelToEvaluate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelConnectedInputs1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(comboMethod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelConnectedInputs2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textNumFolds, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelConnectedInputs3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonCompute, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(progressBar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel1.setText("Results:");

        jButton4.setText("Close");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPaneInputs, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton4)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrollPaneInputs, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 58, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)); //Necessary to be handled correctly by main gui!
            this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void buttonComputeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonComputeActionPerformed
        if (isComputing) {
            if (e != null) {
                e.cancel();
            } else {
                logger.log(Level.SEVERE, "Evaluation cancelled but worker is null");
            }
            return;
        }
        EvaluationMode eval = EvaluationMode.CROSS_VALIDATION;
        switch(comboMethod.getSelectedIndex())
        {
            case 0:
                eval = EvaluationMode.CROSS_VALIDATION;
                break;
            case 1:
                eval = EvaluationMode.TRAINING_SET;
                break;
            case 2:
                eval = EvaluationMode.TESTING_SET;
                break;
        }

        List<Path> paths;
        if (comboModelToEvaluate.getSelectedIndex() == 0) {
            paths = w.getSupervisedLearningManager().getPaths();
            singleModelOffset = 0;
        } else {
            Path p = w.getSupervisedLearningManager().getPaths().get(comboModelToEvaluate.getSelectedIndex() - 1);
            paths = new LinkedList<>();
            paths.add(p);
            singleModelOffset = comboModelToEvaluate.getSelectedIndex() - 1;
        }

        int numFolds = 0;
        if (eval == EvaluationMode.CROSS_VALIDATION) {
            try {
                numFolds = Integer.parseInt(textNumFolds.getText());
            } catch (NumberFormatException ex) {
                Util.showPrettyErrorPane(this, "Number of folds must be an integer greater than 1");
                return;
            }
            if (numFolds <= 1) {
                Util.showPrettyErrorPane(this, "Number of folds must be an integer greater than 1");
                return;
            }
        }
        LearningState ls = w.getSupervisedLearningManager().getLearningState();
        if (ls == LearningState.NOT_READY_TO_TRAIN) {
            Util.showPrettyErrorPane(this, "Cannot compute evaluation: please supply some training examples first.");
            return;
        }
        int numExamples = w.getDataManager().getNumExamples();
        if (numExamples == 0) {
            Util.showPrettyErrorPane(this, "Cannot compute evaluation: please supply some training examples first.");
            return;
        }
        if (eval == EvaluationMode.CROSS_VALIDATION) {
            if (numFolds > numExamples) {
                Util.showPrettyWarningPromptPane(this, "The number of folds you have chosen is greater than the number of training examples; Setting number of folds to " + numExamples);
                numFolds = numExamples;
            }
        }
        e = new ModelEvaluator(w, new EvaluationResultsReceiver() {

            @Override
            public void finishedModel(int modelNum, String results) {
                cvModelFinished(modelNum, results);
            }

            @Override
            public void finished(String[] results) {
                cvFinished(results);
            }

            @Override
            public void cancelled() {
                cvCancelled();
            }
        });
        isComputing = true;
        wasCancelled = false;
        buttonCompute.setText("Cancel");
        numModelsToCompute = paths.size();
        modelsComputed = 0;
        progressBar.setMaximum(numModelsToCompute + 1);
        progressBar.setValue(1);
        e.evaluateAll(paths, eval, numFolds, new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                cvPropertyChanged(evt);
            }

        });
    }//GEN-LAST:event_buttonComputeActionPerformed

    private void cvPropertyChanged(PropertyChangeEvent evt) {
        System.out.println("CV property changed");
    }

    private void cvModelFinished(int modelNum, String results) {
        System.out.println("Model " + modelNum + ": " + results);
        modelsComputed++;
        rows.get(singleModelOffset + modelNum).setValue(results);
        if (!wasCancelled) {
            progressBar.setValue(modelsComputed + 1);
        }

    }

    private void cvCancelled() {
        System.out.println("CV Cancelled!!!");
        isComputing = false;
        buttonCompute.setText("Compute now");
        progressBar.setValue(0);
        wasCancelled = true;
        if (numModelsToCompute > modelsComputed) {
            for (int i = modelsComputed; i < numModelsToCompute; i++) {
                rows.get(i + singleModelOffset).setValue("Cancelled");
            }
        }
    }

    private void cvFinished(String[] results) {
        System.out.println("CV Finished");
        isComputing = false;
        buttonCompute.setText("Compute now");
        progressBar.setValue(numModelsToCompute + 1);

    }

    private void comboMethodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboMethodActionPerformed
        CardLayout l = (CardLayout) panelFoldsParent.getLayout();

        if (comboMethod.getSelectedIndex() == 0) {
            l.show(panelFoldsParent, "cardFolds");
        } else {
            l.show(panelFoldsParent, "cardNone");
        }
    }//GEN-LAST:event_comboMethodActionPerformed

    private void textNumFoldsKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textNumFoldsKeyTyped
        char enter = evt.getKeyChar();
        if (!(Character.isDigit(enter))) {
            evt.consume();
        }
    }//GEN-LAST:event_textNumFoldsKeyTyped

    private void recordingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recordingButtonActionPerformed
       if(w.getSupervisedLearningManager().getRecordingState() != SupervisedLearningManager.RecordingState.RECORDING_TEST)
       {
           recordingButton.setText("Stop Recording Test Set");
           w.getSupervisedLearningManager().setRecordingState(SupervisedLearningManager.RecordingState.RECORDING_TEST);
       }
       else
       {
           recordingButton.setText("Start Recording Test Set");
           w.getSupervisedLearningManager().setRecordingState(SupervisedLearningManager.RecordingState.NOT_RECORDING);
       }
       testSetSizeLabel.setText(Integer.toString(w.getDataManager().getTestingDataForOutput(0).numInstances()));
    }//GEN-LAST:event_recordingButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        // TODO add your handling code here:
        w.getDataManager().deleteTestSet();
        testSetSizeLabel.setText(Integer.toString(w.getDataManager().getTestingDataForOutput(0).numInstances()));
    }//GEN-LAST:event_deleteButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ModelEvaluationFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ModelEvaluationFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ModelEvaluationFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ModelEvaluationFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                String[] testNames = new String[]{"one", "two", "three"};
                try {
                    new ModelEvaluationFrame(testNames, Wekinator.TestingWekinator()).setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(ModelEvaluationFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonCompute;
    private javax.swing.JPanel cardFolds;
    private javax.swing.JPanel cardNone;
    private javax.swing.JComboBox comboMethod;
    private javax.swing.JComboBox comboModelToEvaluate;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel labelConnectedInputs;
    private javax.swing.JLabel labelConnectedInputs1;
    private javax.swing.JLabel labelConnectedInputs2;
    private javax.swing.JLabel labelConnectedInputs3;
    private wekimini.gui.NameValueRow nameValueRow1;
    private wekimini.gui.NameValueRow nameValueRow2;
    private wekimini.gui.NameValueRow nameValueRow3;
    private wekimini.gui.NameValueRow nameValueRow4;
    private javax.swing.JPanel panelFoldsParent;
    private javax.swing.JPanel panelResultsList;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JButton recordingButton;
    private javax.swing.JScrollPane scrollPaneInputs;
    private javax.swing.JLabel testSetSizeLabel;
    private javax.swing.JTextField textNumFolds;
    // End of variables declaration//GEN-END:variables

    private void initRows(String[] outputNames) {
        panelResultsList.removeAll();
        for (int i = 0; i < outputNames.length; i++) {
            NameValueRow r = new NameValueRow(outputNames[i]);
            rows.add(r);
            panelResultsList.add(r);
        }
        panelResultsList.repaint();
    }

}
