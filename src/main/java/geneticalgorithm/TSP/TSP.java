/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticalgorithm.TSP;

import Util.WorldMap.DestinationPool;
import Util.WorldMap.DestinationPoolUI;
import geneticalgorithm.Extremum.FunctionStepMutationOperator;
import geneticalgorithm.Population.Population;
import geneticalgorithm.Problem.Problem;

/**
 *
 * @author simonneau
 */
public class TSP extends Problem<TSPIndividual> {

    private DestinationPool dp;

    /**
     *
     */
    public TSP() {
        this.setLabel("TSP");
        this.dp = new DestinationPool();
        
        this.addCrossOverOperator(new TSPCrossOverOperator());
        this.addEvaluationOperator(new TSPEvaluationOperator());
        this.addMutationOperator(new TSPRandomMutationOperator());
        this.addMutationOperator(new TSPSwapMutationOperator());
        
        this.clearViews();
        this.addView(new TSPUI(this));
    }

    @Override
    public Population createInitialPopulation() {
        
        DestinationPool destinationPool = dp.clone();
        ((DestinationPoolUI)destinationPool.getUI()).setFooterVisible(false);

        TSPPopulation pop = new TSPPopulation(destinationPool);
        int size = this.getPopulationSize();

        for (int i = 0; i < size; i++) {

            pop.add(TSPIndividual.createRandom(destinationPool));
        }

        return pop;
    }

    /**
     *
     * @return
     */
    public DestinationPool getDestinationPool() {
        return dp;
    }
    
    
}
