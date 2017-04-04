package sample;

import java.io.*;
import java.util.*;

/**
 * Created by anton on 02.04.2017.
 */
public class ColorFromFile  {

    ArrayList<String[]> listOfColors = new ArrayList<String[]>();


    ArrayList<String[]> firstFiveDE = new ArrayList<String[]>();

    public ArrayList<String[]> getFirstFiveDE() {
        return firstFiveDE;
    }




    public ColorFromFile(File file) throws Exception {
        BufferedReader rf = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line;
        while ((line = rf.readLine()) != null) {
            listOfColors.add(line.split("\t"));
        }
    }

    public  ArrayList<UserColor> similarColors(UserColor color) throws Exception {

        ArrayList<Double> listOfDE = new ArrayList<Double>();
        ArrayList<Double> sortedListOfDE = new ArrayList<Double>();
        ArrayList<UserColor> firsfiveColors = new ArrayList<UserColor>();
        ArrayList<UserColor> userColors = new ArrayList<UserColor>();

        for (int x = 0; x < listOfColors.size(); x++) {
            double l = Double.parseDouble(listOfColors.get(x)[1]);
            double a = Double.parseDouble(listOfColors.get(x)[2]);
            double b = Double.parseDouble(listOfColors.get(x)[3]);
            UserColor color2 = new UserColor(l, a, b);
            userColors.add(color2);
            listOfDE.add(UserColor.colorDifference76(color, color2));
        }
        sortedListOfDE = (ArrayList<Double>)listOfDE.clone();
        Collections.sort(sortedListOfDE, new Comparator<Double>() {
            public int compare(Double o1, Double o2) {
                return o1.toString().compareTo(o2.toString());
            }
        });

        for(int x = 0; x < 5; x++){
            firstFiveDE.add(listOfColors.get(listOfDE.indexOf(sortedListOfDE.get(x))));
            firsfiveColors.add(userColors.get(listOfDE.indexOf(sortedListOfDE.get(x))));
        }
        this.firstFiveDE = firstFiveDE;
        return firsfiveColors;
    }

}
