/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wekimini.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.NoSuchElementException;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
import javax.swing.Timer;
import weka.core.Instance;
import wekimini.Wekinator;
import wekimini.modifiers.Feature;
import wekimini.modifiers.Feature.InputDiagram;

/**
 *
 * @author louismccallum
 */
public class FeatureDetailPanel extends javax.swing.JPanel {

    private PlotPanel plotPanel;
    private final int REFRESH_RATE = 20;
    private PlotRowModel model;
    private Wekinator w;
    private static final int PLOT_W = 416;
    
    public FeatureDetailPanel() 
    {
        initComponents();
        plotPanel = new PlotPanel(PLOT_W);        
        plotScrollPane.setViewportView(plotPanel);
        plotScrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_ALWAYS);
    }
    
    public void update(Wekinator w)
    {
        this.w = w;
        Timer timer = new Timer(REFRESH_RATE, new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            Instance in = w.getSupervisedLearningManager().getCurrentInputInstance();
            if(in != null)
            {
                if(model.isStreaming)
                {
                    float val = (float) in.value(model.feature.featureIndex);
                    //System.out.println("adding " + val + " to model " + model.feature.featureIndex);
                    model.addPoint(val);
                }
                plotPanel.updateModel(model);
            }
        }    
        });  
        timer.start();
    }
    
    public URL urlToDiagram(InputDiagram diagram) throws NoSuchElementException
    {
        switch(diagram)
        {
            case ACCX : return getClass().getResource("/wekimini/icons/AccX.png");
            case ACCY : return getClass().getResource("/wekimini/icons/AccY.png");
            case ACCZ : return getClass().getResource("/wekimini/icons/AccZ.png");
            case GYROX : return getClass().getResource("/wekimini/icons/GyroX.png");
            case GYROY : return getClass().getResource("/wekimini/icons/GyroY.png");
            case GYROZ : return getClass().getResource("/wekimini/icons/GyroZ.png");
        }
        throw new NoSuchElementException();
    }
    
    public void setModel(PlotRowModel model)
    {
        this.model = model;
        plotPanel.updateModel(model);
        titleLabel.setText(model.feature.name);
        plotPanel.updateWidth(model.isStreaming);
        repaint();
        plotScrollPane.revalidate();
        validate();
        plotScrollPane.setViewportView(plotPanel);
        plotScrollPane.revalidate();
        validate();
        
        try{
            diagramView.loadImage(urlToDiagram(model.feature.diagram));
        } catch (NoSuchElementException e){}
        
        descriptionLabel.setText(model.feature.description);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        plotScrollPane = new javax.swing.JScrollPane();
        titleLabel = new javax.swing.JLabel();
        descriptionLabel = new javax.swing.JLabel();
        diagramView = new wekimini.gui.ImagePanel();

        titleLabel.setBackground(new java.awt.Color(238, 0, 0));
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("Feature");

        descriptionLabel.setText("Description");

        javax.swing.GroupLayout diagramViewLayout = new javax.swing.GroupLayout(diagramView);
        diagramView.setLayout(diagramViewLayout);
        diagramViewLayout.setHorizontalGroup(
            diagramViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 94, Short.MAX_VALUE)
        );
        diagramViewLayout.setVerticalGroup(
            diagramViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(plotScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(diagramView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(descriptionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(plotScrollPane)
                    .addComponent(descriptionLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(diagramView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel descriptionLabel;
    private wekimini.gui.ImagePanel diagramView;
    private javax.swing.JScrollPane plotScrollPane;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
}
