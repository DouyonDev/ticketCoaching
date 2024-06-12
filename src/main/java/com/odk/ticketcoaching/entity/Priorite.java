package com.odk.ticketcoaching.entity;

public enum Priorite {
    FAIBLE("Faible"), // Valeur par défaut
    MOYENNE("Moyenne"),
    ELEVEE("Élevée");

    private final String label;

    Priorite(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
