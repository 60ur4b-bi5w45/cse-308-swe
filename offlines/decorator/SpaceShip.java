package com.amongUs;

import com.amongUs.ImposterDecorator;
import com.amongUs.Passenger;

import java.util.Scanner;

public class SpaceShip {
    public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);

            String o;
            String p;
            Passenger crewmate = new Crewmate();

            do{
                    o = scanner.next();
                    if(o.equalsIgnoreCase("login"))
                    {
                            p = scanner.next();

                            if(p.equalsIgnoreCase("imp1"))
                            {
                                    crewmate = new ImposterDecorator(new Crewmate());
                                    crewmate.login();
                            }
                            else if(p.equalsIgnoreCase("crew1"))
                            {
                                    crewmate = new Crewmate();
                                    crewmate.login();
                            }
                    }
                    if(o.equalsIgnoreCase("repair"))
                    {
                            crewmate.repair();
                    }
                    if(o.equalsIgnoreCase("work"))
                    {
                            crewmate.work();
                    }
                    if(o.equalsIgnoreCase("logout"))
                    {
                            crewmate.logout();
                    }

            } while (!o.equalsIgnoreCase("quit"));
        }
}
