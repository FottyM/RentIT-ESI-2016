import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");


        String c = "{startDate=2016-05-14, endDate=2016-05-26}";
        Pattern pp = Pattern.compile("startDate=(.*?),");;
        Matcher m2 = pp.matcher(c);
        while(m2.find())
        {
            rentalperiod= m2.group(1);
            //plant =plant.replaceAll("\\D+","");
        }


    }
}
