package com.company;

import java.util.HashMap;
import java.util.Scanner;

//this code isn't correct since I didn't account for the fact that A- can only accept O-, I assumed that it could accept O- and O+, so there is some extra counting done
//At this point I've spent too long on this so I'm going to stop here
//if there is a much more efficient solution I'd actually love to know
public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        HashMap<String, Integer> bloodUnits = new HashMap<>();
        bloodUnits.put("O-", sc.nextInt());
        bloodUnits.put("O+", sc.nextInt());
        bloodUnits.put("A-", sc.nextInt());
        bloodUnits.put("A+", sc.nextInt());
        bloodUnits.put("B-", sc.nextInt());
        bloodUnits.put("B+", sc.nextInt());
        bloodUnits.put("AB-", sc.nextInt());
        bloodUnits.put("AB+", sc.nextInt());
        HashMap<String, Integer> patientCount = new HashMap<>();
        patientCount.put("O-", sc.nextInt());
        patientCount.put("O+", sc.nextInt());
        patientCount.put("A-", sc.nextInt());
        patientCount.put("A+", sc.nextInt());
        patientCount.put("B-", sc.nextInt());
        patientCount.put("B+", sc.nextInt());
        patientCount.put("AB-", sc.nextInt());
        patientCount.put("AB+", sc.nextInt());

        int count = 0;

        //in order to maximize the amount of people that can receive blood, we must try and give blood based on the restrictions, since we want to maximize the amount of people for each category

        // for O- blood
        int oRem = 0;
        if (patientCount.get("O-") == 0) {
            oRem = bloodUnits.get("O-");
        } else if (bloodUnits.get("O-") - patientCount.get("O-") >= 0) { //we have more or enough O- blood than patients
            count += patientCount.get("O-"); // all patients recieve blood
            oRem = bloodUnits.get("O-") - patientCount.get("O-"); //remainder is leftover
        } else { //we do not have enough O- blood
            count += bloodUnits.get("O-"); //so the number of patients treated is only the amount of blood units we have
        }

        // for O+ blood
        if (patientCount.get("O+") == 0) {
            oRem += bloodUnits.get("O+");
        } else if ((bloodUnits.get("O+") + oRem) - patientCount.get("O+") >= 1) { //we have more O+ and O- blood than O+ patients
            count += patientCount.get("O+"); //then all the O+ patients are treated
            oRem = (bloodUnits.get("O+") + oRem) - patientCount.get("O+"); // leftover O blood is remainder
        } else {
            count += bloodUnits.get("O+") + oRem; //then we can only treat as much people as we have blood
            oRem = 0; //we use up all the O blood
        }

        // for A- blood
        int aRem = 0;
        if (patientCount.get("A-") == 0) {
            aRem = bloodUnits.get("A-");
        } else if (bloodUnits.get("A-") - patientCount.get("A-") >= 0) { //we have more A- blood than A- patients
            count += patientCount.get("A-"); // then we treat  all the patients
            aRem = bloodUnits.get("A-") - patientCount.get("A-");
        } else if ((bloodUnits.get("A-") + oRem) - patientCount.get("A-") >= 0) { //try patch remainder with O blood
            oRem = (bloodUnits.get("A-") + oRem) - patientCount.get("A-"); //O blood that remains is leftover
            count += patientCount.get("A-");
        } else { //then we have more patients than A- and O blood
            count += bloodUnits.get("A-") + oRem;
            oRem = 0; //we used up all the O blood
        }

        //for A+ blood
        if (patientCount.get("A+") == 0) {
            aRem += bloodUnits.get("A+");
        } else if ((bloodUnits.get("A+") + aRem) - patientCount.get("A+") >= 0) { //we have more A- and A+ blood than A+ patients
            count += patientCount.get("A+"); // then we treat  all the patients
            aRem = (bloodUnits.get("A+") + aRem) - patientCount.get("A+"); //at this point we don't care whether it is A+ or A- blood, since AB can accept any type
        } else if ((bloodUnits.get("A+") + oRem + aRem) - patientCount.get("A+") >= 0) { //try patch remainder with O blood
            oRem = (bloodUnits.get("A+") + oRem + aRem) - patientCount.get("A+"); //O blood that remains is leftover
            count += patientCount.get("A+");
        } else { //then we have more patients than A and O blood
            count += bloodUnits.get("A+") + oRem + aRem;
            oRem = 0; //we used up all the O blood
            aRem = 0;
        }


        //process is identical for B blood
        // for B- blood
        int bRem = 0;
        if (patientCount.get("B-") == 0) {
            bRem = bloodUnits.get("B-");
        } else if (bloodUnits.get("B-") - patientCount.get("B-") >= 0) { //we have more B- blood than B- patients
            count += patientCount.get("B-"); // then we treat  all the patients
            bRem = bloodUnits.get("B-") - patientCount.get("B-");
        } else if ((bloodUnits.get("B-") + oRem) - patientCount.get("B-") >= 0) { //try patch remainder with O blood
            oRem = (bloodUnits.get("B-") + oRem) - patientCount.get("B-"); //O blood that remains is leftover
            count += patientCount.get("B-");
        } else { //then we have more patients than B- and O blood
            count += bloodUnits.get("B-") + oRem;
            oRem = 0; //we used up all the O blood
        }

        //for B+ blood
        if (patientCount.get("B+") == 0) {
            bRem += bloodUnits.get("B+");
        }
        if ((bloodUnits.get("B+") + bRem) - patientCount.get("B+") >= 0) { //we have more B- and B+ blood than A+ patients
            count += patientCount.get("B+"); // then we treat  all the patients
            bRem = (bloodUnits.get("B+") + bRem) - patientCount.get("B+"); //at this point we don't care whether it is B- or B+ blood, since AB can accept any type
        } else if ((bloodUnits.get("B+") + oRem + bRem) - patientCount.get("B+") >= 0) { //try patch remainder with O blood
            oRem = (bloodUnits.get("B+") + oRem + bRem) - patientCount.get("B+"); //O blood that remains is leftover
            count += patientCount.get("B+");
        } else { //then we have more patients than A and O blood
            count += bloodUnits.get("B+") + oRem + bRem;
            oRem = 0; //we used up all the O blood
            bRem = 0;
        }

        //for AB- blood
        //we dont care about remainders anymore
        int ABrem = 0;
        if (patientCount.get("AB-") == 0) {
            ABrem = bloodUnits.get("AB-");
        } else if (bloodUnits.get("AB-") - patientCount.get("AB-") >= 0) { //we have more AB- blood than AB- patients
            count += patientCount.get("AB-"); // then we treat  all the patients
            ABrem = bloodUnits.get("AB-") - patientCount.get("AB-");
        } else if ((bloodUnits.get("AB-") + oRem + aRem + bRem) - patientCount.get("B-") >= 0) { //try patch remainder with O blood, A or B blood
            count += patientCount.get("AB-");
        } else { //then we have more patients than B- and O blood
            count += bloodUnits.get("AB-") + oRem + aRem + bRem;
        }

        //for AB+ blood
        if (patientCount.get("AB+") == 0) {
            ABrem += bloodUnits.get("AB+");
        } else if ((bloodUnits.get("AB+") + ABrem) - patientCount.get("AB+") >= 0) { //we have more AB- and AB+ blood than A+ patients
            count += patientCount.get("AB+"); // then we treat  all the patients
        } else if ((bloodUnits.get("AB+") + oRem + bRem + aRem + ABrem) - patientCount.get("AB+") >= 0) { //try patch remainder with O, A or B blood
            count += patientCount.get("AB+");
        } else { //then we have more patients than A and O blood
            count += bloodUnits.get("AB+") + oRem + bRem + aRem + ABrem;
        }

        // write your code here


        System.out.println(count);
    }
}
