package com.odk.ticketcoaching.entity;

public enum Statut {
    REPONDU("Répondu"), // Valeur par défaut
    EN_COURS("En cours"),
    RESOLU("Résolu"),
    FERMEE("Fermé");



    private final String label;

    Statut(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
