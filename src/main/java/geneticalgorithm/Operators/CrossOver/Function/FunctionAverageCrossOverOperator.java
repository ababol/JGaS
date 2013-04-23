/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticalgorithm.Operators.CrossOver.Function;

import Mathematics.Point;
import geneticalgorithm.Operators.CrossOver.CrossOverOperator;
import geneticalgorithm.Population.Individuals.FunctionIndividual.FunctionIndividual;
import geneticalgorithm.Population.Individuals.Individual;

/**
 *
 * @author nono
 */
public class FunctionAverageCrossOverOperator extends CrossOverOperator {

    private static String LABEL = "average";

    public FunctionAverageCrossOverOperator() {
        super(LABEL);
    }

    @Override
    public Individual cross(Individual male, Individual female) {

        if (male instanceof FunctionIndividual && female instanceof FunctionIndividual) {

            FunctionIndividual maleP = (FunctionIndividual) male;
            FunctionIndividual femaleP = (FunctionIndividual) female;

            if (maleP.getDimension() == femaleP.getDimension()) {

                Point newPoints = new Point(maleP.getPoint());
                newPoints.average(femaleP.getPoint());

                return new FunctionIndividual(maleP.getFunction(), newPoints);
            } else {
                // IncorrectIndividualDataException TODO BY ARNAUD
                return null;
            }
        }
        // IncorrectIndividualException TODO BY ARNAUD
        return null;
    }
}
