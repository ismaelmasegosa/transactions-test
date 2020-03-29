package com.ismaelmasegosa.transaction.challenge.domain.transaction.reference;

import java.util.Random;

public class ReferenceGenerator {

  public static String generateSystemReference() {

    int leftLimitOfLetter = 65;
    int rightLimitOfLetter = 90;
    int leftLimitOfNumber = 48;
    int rightLimitOfNumber = 57;
    int letterLength = 1;
    int numberLength = 5;
    Random random = new Random();

    String generatedLetter = random.ints(leftLimitOfLetter, rightLimitOfLetter + 1).filter(i -> i != 79).limit(letterLength)
        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
    String generatedNumber = random.ints(leftLimitOfNumber, rightLimitOfNumber + 1).limit(numberLength)
        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

    return generatedNumber.concat(generatedLetter);
  }
}
