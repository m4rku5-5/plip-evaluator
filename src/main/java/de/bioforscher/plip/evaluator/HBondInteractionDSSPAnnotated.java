package de.bioforscher.plip.evaluator;

public class HBondInteractionDSSPAnnotated extends HBondInteraction{
    public String feature = "";

    public HBondInteractionDSSPAnnotated() {
    }

    public HBondInteractionDSSPAnnotated(int residueNumber, int donor, int accept, String feature) {
        super(residueNumber, donor, accept);
        this.feature = feature;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    @Override
    public String toString() {
        String out = super.toString() + " " + this.getFeature();
        return out;
    }
}
