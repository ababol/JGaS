/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Mathematics.Function.View;

import GraphicalComponents.CustomPlot.Custom2DPlot;
import GraphicalComponents.CustomSpinner;
import GraphicalComponents.CustomTextField;
import GraphicalComponents.CustomTextFieldEvent;
import GraphicalComponents.IdentifiableComponent;
import GraphicalComponents.ObservationEvent;
import GraphicalComponents.Observer;
import GraphicalComponents.SpinnerEvent;
import Mathematics.Points;
import MvcPattern.RefreshEvent;
import MvcPattern.View;
import de.congrace.exp4j.UnknownFunctionException;
import de.congrace.exp4j.UnparsableExpressionException;
import Mathematics.Function.Controller.FunctionController;
import Mathematics.Function.Model.Function;
import Mathematics.Function.Model.Function2D;
import mathematics.Function.Events.DomaineEvent;
import mathematics.Function.Events.FunctionEvent;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author nono
 */
public class Function2DUI extends IdentifiableComponent implements Observer, View {

    private Custom2DPlot plot2D;
    private CustomTextField functionChange;
    private CustomSpinner xMin;
    private CustomSpinner xMax;
    private FunctionController controller;

    public Function2DUI(FunctionController controller) throws UnknownFunctionException, UnparsableExpressionException {
        super(new BorderLayout());

        this.controller = controller;
        this.functionChange = new CustomTextField("sin(x)");
        this.plot2D = new Custom2DPlot((Function2D)controller.getModel());
        this.xMin = new CustomSpinner("xMin", Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 0.01);
        this.xMax = new CustomSpinner("xMax", Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 0.01);

        JPanel footer = new JPanel(new GridLayout(2, 1));

        JPanel xPanel = new JPanel();
        JPanel functionPanel = new JPanel();

        xPanel.add(xMin);
        xPanel.add(xMax);

        functionPanel.add(new JLabel("f(x) = "));
        functionPanel.add(functionChange);

        footer.add(functionPanel);
        footer.add(xPanel);

        this.functionChange.addObserver(this);
        this.xMin.addObserver(this);
        this.xMax.addObserver(this);

        this.add(plot2D, BorderLayout.CENTER);
        this.add(footer, BorderLayout.SOUTH);
    }

    @Override
    public void refresh(RefreshEvent ev) {
        Function func = (Function)ev.getSource();
        try {
            this.plot2D.setPlot((Function2D)func);
        } catch (UnknownFunctionException ex) {
            Logger.getLogger(Function2DUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnparsableExpressionException ex) {
            Logger.getLogger(Function2DUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void reactToChanges(ObservationEvent ev) {
        if (ev instanceof SpinnerEvent) {
            controller.applyChanges(new DomaineEvent(this, new Points(xMin.getValue().doubleValue(), xMax.getValue().doubleValue())));
        }
        if (ev instanceof CustomTextFieldEvent) {
            CustomTextFieldEvent ctfe = (CustomTextFieldEvent) ev;
            String newFunc = ctfe.getValue();
            controller.applyChanges(new FunctionEvent(this, newFunc));
        }
    }
}