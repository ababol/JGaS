/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Mathematics.Function.Model;

import Mathematics.Points;
import MvcPattern.RefreshEvent;
import MvcPattern.View;
import de.congrace.exp4j.UnknownFunctionException;
import de.congrace.exp4j.UnparsableExpressionException;
import Mathematics.Function.View.Function2DUI;
import javax.swing.JPanel;

/**
 *
 * @author nono
 */
public class Function2D extends Function {
    public Function2D(String function, Points domaine) throws UnknownFunctionException, UnparsableExpressionException {
        super(function, domaine);
    }

    public double getY(double x) {
        this.calc.setVariable("x", x);
        return this.calc.calculate();
    }

    public double getResult(Points points) {
        return getY(points.get(0));
    }

    public void inDomaine(Points points) {
        super.minMax(points.get(0), domaine.get(0), domaine.get(1));
    }
}