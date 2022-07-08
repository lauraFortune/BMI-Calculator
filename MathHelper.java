// Laura Fortune, laurafordefortune@gmail.com, 0831221696

public class MathHelper { // all math functions
    
    // bmi calculator
    public static String[] bmiCalculator(double kilos, double cms){
        double metres = cms / 100;
        double bmi = kilos / ( metres * metres ); 
        
        String bmiStatus;
        String bmiCriteria;
        String bmiString = String.format("%.2f", bmi);
        
        if  ( bmi < 18.5){
            bmiStatus = "UNDERWEIGHT";
            bmiCriteria = "less than 18.5";
        } else if ( bmi >= 18.5 && bmi < 25){
            bmiStatus = "NORMAL";
            bmiCriteria = "between 18.5 and 24.9";
        } else if ( bmi >= 25 && bmi < 30){
            bmiStatus = "OVERWEIGHT";
            bmiCriteria = "between 25 and 29.9";
        } else {
            bmiStatus = "OBESE";
            bmiCriteria = "30 or greater";
        }
        String [] bmiData = {bmiString, bmiStatus, bmiCriteria};
        return bmiData; // returns array of string values
    }
    
    // convert kilos to stones/pounds
    public static double[] kilosToStonesPounds(double kilos){ 
        double totalPounds = kilos * 2.204622;
        double pounds = totalPounds % 14;
        double stones = (totalPounds - pounds) / 14;
        double[] stonesPounds = {stones, pounds};
        return stonesPounds; // returns array of double values
    }
    
    // convert stones/pounds to kilos
    public static double stonesPoundsToKilos(double stones, double pounds){
        double totalPounds = pounds + (stones * 14);
        double kilos = totalPounds / 2.204622;
        return kilos;
    }
    
    // convert centimeters to feet/height
    public static double[] cmsToFeetInches(double cms){
        double totalInches = cms / 2.54;
        double inches = totalInches % 12;
        double feet = (totalInches - inches) / 12;
        double[] feetInches = {feet, inches};
        return feetInches; // returns array of double values
    }
    
    //convert feet/inches to centimeters
    public static double feetInchesToCms(double feet, double inches){
        double totalnches = inches + (feet * 12);
        double cms = totalnches * 2.54;
        return cms;
    }

} // end class MathHelper
