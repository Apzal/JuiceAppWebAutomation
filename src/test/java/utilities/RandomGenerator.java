package utilities;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.*;

public class RandomGenerator {

    public String getRandomEmail(){
        return (RandomStringUtils.randomAlphanumeric(8)+"@"+
                RandomStringUtils.randomAlphabetic(5)+".com").toLowerCase();
    }

    public String getRandomPassword(){
        List<String> password = new ArrayList<>(List.of(
                RandomStringUtils.random(2, 65, 90, true, false),
                RandomStringUtils.random(2, 97, 122, true, false),
                RandomStringUtils.randomNumeric(2),
                RandomStringUtils.random(2, "!@#$%^&*()-_=+<>?"),
                RandomStringUtils.randomAlphanumeric(2)
        ));
        Collections.shuffle(password);
        return String.join("", password);

    }

    public String getRandomZipCode(){
        return RandomStringUtils.random(2, 65, 90, true, false)+
                RandomStringUtils.randomNumeric(2);
    }

    public String getRandomMobileNumber(){
        return RandomStringUtils.randomNumeric(10);
    }

    public String getRandomName(){
        String[] names = {"Alice", "Bob", "Charlie", "Diana", "Eve", "Frank"};
        Random random = new Random();
        return names[random.nextInt(names.length)];

    }

    public String getRandomStreetAddress(){
        String[] names = {"Main St", "Highland Ave", "Maple Drive", "Oak Street", "Pine Lane"};
        Random random = new Random();
        return names[random.nextInt(names.length)];
    }

    public String getRandomCity(){
        String[] names = {"Springfield", "Riverside", "Greenville", "Fairview", "Madison"};
        Random random = new Random();
        return names[random.nextInt(names.length)];
    }

    public String getRandomState(){
        String[] names = {"TN", "KL", "DH", "KA", "TE"};
        Random random = new Random();
        return names[random.nextInt(names.length)];
    }

    public String generateRandomCardNumber(){
        return RandomStringUtils.randomNumeric(1).replace("0", "5")
                +RandomStringUtils.randomNumeric(15);
    }
}
