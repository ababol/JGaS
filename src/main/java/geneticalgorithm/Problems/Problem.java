/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticalgorithm.Problems;

import MvcPattern.Model;
import MvcPattern.View;
import geneticalgorithm.Operators.CrossOver.CrossOverOperator;
import geneticalgorithm.Operators.Evaluation.EvaluationOperator;
import geneticalgorithm.Operators.Mutation.MutationOperator;
import geneticalgorithm.Operators.Operators;
import geneticalgorithm.Operators.Selection.ProportionalPerfomanceSelectionOperator;
import geneticalgorithm.Operators.Selection.ProportionalRankingSelectionOperator;
import geneticalgorithm.Operators.Selection.SelectionOperator;
import geneticalgorithm.Operators.Selection.TruncationSelectionOperator;
import geneticalgorithm.Population.Population;
import geneticalgorithm.Problems.StopCriteria.StopCriteria;
import java.util.LinkedList;

/**
 *
 * @author simonneau
 */
public abstract class Problem extends Model {

    private LinkedList<MutationOperator> availableMutationOperators = new LinkedList<>();
    private LinkedList<CrossOverOperator> availableCrossOverOperators = new LinkedList<>();
    private LinkedList<SelectionOperator> availableSelectionOperators = new LinkedList<>();
    private LinkedList<EvaluationOperator> availableEvaluationOperator = new LinkedList<>();
    private int populationSize = 20;
    private String label;
    private double mutationProbability = 0.1;
    private double crossProbability = 0.2;
    private Operators operators = new Operators();
    private StopCriteria stopCriteria;

    public Problem(){        
        this.stopCriteria = new StopCriteria();
        this.addSelectionOperator(TruncationSelectionOperator.getInstance());
        this.addSelectionOperator(ProportionalPerfomanceSelectionOperator.getInstance());
        this.addSelectionOperator(ProportionalRankingSelectionOperator.getInstance());
        
    }
    
    
    public StopCriteria getStopCriteria(){
        return this.stopCriteria;
    }

    public int getTimeout() {
        return stopCriteria.getTimeout();
    }

    public LinkedList<MutationOperator> getAvailableMutationOperators() {
        return availableMutationOperators;
    }

    public LinkedList<CrossOverOperator> getAvailableCrossOverOperators() {
        return availableCrossOverOperators;
    }

    public LinkedList<SelectionOperator> getAvailableSelectionOperators() {
        return availableSelectionOperators;
    }

    public LinkedList<EvaluationOperator> getAvailableEvaluationOperator() {
        return availableEvaluationOperator;
    }

    public int getMaxStepCount() {
        return stopCriteria.getMaxStepCount();
    }

    public String getLabel() {
        return label;
    }

    public double getMutationProbability() {
        return mutationProbability;
    }

    public double getCrossProbability() {
        return crossProbability;
    }

    public MutationOperator getSelectedMutationOperator() {
        return operators.getMutationOperator();
    }

    public CrossOverOperator getSelectedCrossOverOperation() {
        return operators.getCrossoverOperator();
    }

    public SelectionOperator getSelectedSelectionOperator() {
        return operators.getSelectionOperator();
    }

    public EvaluationOperator getSelectedEvaluationOperator() {
        return operators.getEvaluationOperator();
    }

    public void setMutationProbability(double mutationProbability) {
        this.mutationProbability = mutationProbability;
    }

    public void setCrossProbability(double crossProbability) {
        this.crossProbability = crossProbability;
    }

    public void setSelectedMutationOperator(MutationOperator selectedMutationOperator) {
        this.operators.setMutationOperator(selectedMutationOperator);
    }

    public void setSelectedCrossOverOperation(CrossOverOperator selectedCrossOverOperation) {
        this.operators.setCrossoverOperator(selectedCrossOverOperation);
    }

    public void setSelectedSelectionOperator(SelectionOperator selectedSelectionOperator) {
        this.operators.setSelectionOperator(selectedSelectionOperator);
    }

    public void setSelectedEvaluationOperator(EvaluationOperator selectedEvaluationOperator) {
        this.operators.setEvaluationOperator(selectedEvaluationOperator);
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    @Override
    public void notifyViews() {
        super.notifyViews(new ProblemRefreshEvent(this));
    }

    public abstract Population createInitialPopulation();

    public String xmlSerialize() {
        String serialisedPopulation = "";
        //TODO
        return serialisedPopulation;
    }

    protected void setSelectedOperators(Operators operators) {
        this.operators = operators;
    }

    public Operators getSelectedOperators() {
        return this.operators;
    }

    public void addMutationOperator(MutationOperator operator) {
        this.availableMutationOperators.add(operator);
        if(this.getSelectedMutationOperator() == null){
            this.setSelectedMutationOperator(operator);
        }
    }

    public void addCrossOverOperator(CrossOverOperator operator) {
        this.availableCrossOverOperators.add(operator);
        
        if(this.getSelectedCrossOverOperation() == null){
            this.setSelectedCrossOverOperation(operator);
        }
    }

    public final void addSelectionOperator(SelectionOperator operator) {
        this.availableSelectionOperators.add(operator);
        
        if(this.getSelectedSelectionOperator() == null){
            this.setSelectedSelectionOperator(operator);
        }
    }

    public void addEvaluationOperator(EvaluationOperator operator) {
        this.availableEvaluationOperator.add(operator);
        
        if(this.getSelectedEvaluationOperator() == null){
            this.setSelectedEvaluationOperator(operator);
        }
    }

    @Override
    public final void addView(View v) {
        super.addView(v);
        v.refresh(new ProblemRefreshEvent(this));
    }
    
    public double getEvolutionCriterion(){
        return this.stopCriteria.getEvolutionCriterion();
    }
    
    public boolean stopCriteriaAreReached(int stepCount, long time, double evolutionCoeff){
        return this.stopCriteria.areReached(stepCount, time, evolutionCoeff);
    }
}
