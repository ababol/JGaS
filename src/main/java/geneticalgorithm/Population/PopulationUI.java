/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticalgorithm.Population;

import GraphicalComponents.IdentifiableComponent;
import GraphicalComponents.Observable;
import GraphicalComponents.ObservationEvent;
import GraphicalComponents.Observer;
import GraphicalComponents.OptionLine;
import GraphicalComponents.ValidateButton;
import MvcPattern.RefreshEvent;
import MvcPattern.View;
import geneticalgorithm.Population.Individuals.Individual;
import java.awt.FlowLayout;
import java.util.LinkedList;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 *
 * @author simonneau
 */
public class PopulationUI extends IdentifiableComponent implements View, Observable {

    private Header header;
    protected JPanel populationSample;
    protected PopulationController controller;

    public PopulationUI() {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.header = new Header(this);
        this.populationSample = new JPanel(new FlowLayout());

        this.add(this.header);
        this.add(populationSample);
    }

    @Override
    public void refresh(RefreshEvent ev) {

        if (ev instanceof PopulationRefreshEvent) {
            PopulationRefreshEvent event = (PopulationRefreshEvent) ev;
            LinkedList<Individual> samples = event.getSample();

            for (Individual sample : samples) {
                this.add(sample);
            }
        } else if (ev instanceof ObservableVolumeRefreshEvent) {
            
            ObservableVolumeRefreshEvent event = (ObservableVolumeRefreshEvent) ev;
            this.header.setValue(event.getValue());
        }

    }

    public void add(Individual individual) {

        this.populationSample.add(individual.getUI());

    }

    public PopulationController getController() {
        return controller;
    }

    public void setController(PopulationController controller) {
        this.controller = controller;
        this.header.setController(controller);
    }

    @Override
    public void addObserver(Observer o) {
        this.header.addObserver(o);
    }

    @Override
    public void notifyObserver() {
        this.header.notifyObserver();
    }

    private class Header extends IdentifiableComponent implements Observer, Observable {

        private PopulationUI boss;
        private PopulationController controller;
        private OptionLine volumeOption;
        private ValidateButton refresh;
        private LinkedList<Observer> observers = new LinkedList<>();

        public Header(PopulationUI boss) {

            this.boss = boss;

            this.volumeOption = new OptionLine("Sample size", 1, 20, 1);
            this.refresh = new ValidateButton("refresh");

            this.setLayout(new FlowLayout());
            this.add(this.volumeOption);
            this.add(this.refresh);

            this.volumeOption.addObserver(this);
            this.refresh.addObserver(this);
        }

        @Override
        public void reactToChanges(ObservationEvent ev) {

            int id = ((IdentifiableComponent) ev.getSource()).getId();

            if (id == this.refresh.getId()) {

                this.notifyObserver();

            } else if (id == this.volumeOption.getId()) {
                this.controller.applyChanges(new ObservableVolumeUserEvent(boss, this.volumeOption.getValue()));
            }

        }

        public void setValue(int value) {
            this.volumeOption.setValue(value);
        }

        public void setController(PopulationController controller) {
            this.controller = controller;
        }

        @Override
        public void addObserver(Observer o) {
            this.observers.add(o);
        }

        @Override
        public void notifyObserver() {

            for (Observer o : this.observers) {

                o.reactToChanges(new SpreadRefreshOrderEvent(boss));
            }
        }
    }
}
