package edu.junit5.quickstart;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Provider;
import java.security.Security;


/**
 * Simple application to list installed providers and their available info.
 * @file javafx
 * @author abode
 * @version 1.0
 * @data 2021
 */
public class ListProviders
 {




     public static void main(String[] args){

             //add Provider BC to the list
             Security.addProvider(new BouncyCastleProvider());

             System.out.println("------------List all Providers-------------");
             //Array die alle Provider enthält
             Provider[] installedProvs = Security.getProviders();

               for (int i = 0; i != installedProvs.length; i++)
               {
                  System.out.print(installedProvs[i].getName());
                  System.out.print(": ");
                  System.out.print(installedProvs[i].getInfo());
                  System.out.println();
               }




         }
}

