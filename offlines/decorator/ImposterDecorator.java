package com.amongUs;

class ImposterDecorator implements Passenger {
    private Passenger passenger;
    String MAGENTA = "\u001B[35m";
    String RESET = "\u001B[0m";

    public ImposterDecorator(Passenger passenger) {
        this.passenger = passenger;
    }

    public void login() {
        passenger.login();
        System.out.println(MAGENTA+"We won’t tell anyone; you are an imposter."+RESET);
    }

    public void repair() {
        passenger.repair();
        System.out.println(MAGENTA+"Damaging the spaceship."+RESET);
    }

    public void work() {
        passenger.work();
        System.out.println(MAGENTA+"Trying to kill a crewmate.");
        System.out.println("Successfully killed a crewmate."+RESET);
    }

    public void logout() {
        passenger.logout();
        System.out.println(MAGENTA+"See you again Comrade Imposter."+RESET);
    }
}
