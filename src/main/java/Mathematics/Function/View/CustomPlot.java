/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Mathematics.Function.View;

import Mathematics.Function.Model.Function;
import Mathematics.Point;
import MvcPattern.RefreshEvent;
import MvcPattern.View;
import de.congrace.exp4j.UnknownFunctionException;
import de.congrace.exp4j.UnparsableExpressionException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author nono
 */
public abstract class CustomPlot extends JPanel implements View {
    protected int id = 0;
    protected static Color[] colorInd = {Color.RED, Color.PINK, Color.ORANGE, Color.YELLOW};
    
    protected CustomPlot() {
        this.setLayout(new BorderLayout());
    }

    public abstract void addIndividual(Function function, Point point);
    public abstract void setPlot(Function function) throws UnknownFunctionException, UnparsableExpressionException;
    @Override
    public void refresh(RefreshEvent ev) {
        Function func = (Function)ev.getSource();
        try {
            setPlot(func);
        } catch (UnknownFunctionException | UnparsableExpressionException ex) {
            Logger.getLogger(CustomPlot.class.getName()).log(Level.SEVERE, "Incorrect input function.", ex);
        }
    }

    public int getColorSize() {
        return colorInd.length;
    }
}