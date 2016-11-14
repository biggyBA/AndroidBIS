package ba.biggy.androidbis.global;


import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class RandomStringGenerator {
    private String str;
    private int randInt;
    private StringBuilder sb;
    private List<Integer> l;

    public RandomStringGenerator() {
        this.l = new ArrayList<>();
        this.sb = new StringBuilder();

        buildRandomString();
    }

    private void buildRandomString() {

        //Add ASCII numbers of characters commonly acceptable in passwords
        for (int i = 65; i < 90; i++) {
            l.add(i);
        }


        /*Randomise over the ASCII numbers and append respective character
          values into a StringBuilder*/
        for (int i = 0; i < 32; i++) {
            randInt = l.get(new SecureRandom().nextInt(25));
            sb.append((char) randInt);
        }

        str = sb.toString();
    }

    public String generateRandomString() {
        return str;
    }
}
