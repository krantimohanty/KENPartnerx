package com.kencloud.partner.user.app_util;

import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;

import java.util.Calendar;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtil {


    // validating email id
    /*public static boolean isValidEmail(final String email) {
    String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }*/


    //restrict sql injection character
    private static String blockCharacterSet = "~#^|$%&*!<>',";

    public static InputFilter filter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            if (source != null && blockCharacterSet.contains(("" + source))) {
                return "";
            }
            return null;
        }
    };


    // validating email id
    public static boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();


    }

           /*  Password that doesn’t match:
            1. mY1A@ , too short, minimum 6 characters
            2. mkyong12@ , uppercase characters is required
            3. mkyoNg12* , special symbol “*” is not allow here
             4. mkyonG$$, digit is required
            5. MKYONG12$ , lower cases character is required

    */


    /*  Password that match:
      mkyong1A@
      mkYOn12$
  */
    public static boolean isValidPassword(String pass) {
        String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";

        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(pass);
        return matcher.matches();

    }

    // validating PAN
    public static boolean isValidPanCard(String pancard) {
        String PANCARD_PATTERN = "^[A-Za-z]{5}[0-9]{4}[A-Za-z]$";

        //"[A-Za-z]{5}[0-9]{4}[A-Z]{1}"
        Pattern pattern = Pattern.compile(PANCARD_PATTERN);
        Matcher matcher = pattern.matcher(pancard);
        return matcher.matches();
    }
    public static boolean isValidZIP(String ZIP) {
        String ZIPCODE_PATTERN = "^[1-9][0-9]{5}$";

        //"[A-Za-z]{5}[0-9]{4}[A-Z]{1}"
        Pattern pattern = Pattern.compile(ZIPCODE_PATTERN);
        Matcher matcher = pattern.matcher(ZIP);
        return matcher.matches();
    }

   /* public final static boolean isValidEmailAddress(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }*/

    //validating username
    public static boolean isValidName(String name) {

        String NAME_PATTERN = "^[a-zA-Z ]{1,50}+$";
        //String NAMEE_PATTERN ="^[a-zA-Z ]{1,50}$"


        //String NAME_PATTERN ="^[A-Za-z]$";


        //String NAME_PATTERN="[A-Za-z]{26}[^.]*";

        Pattern pattern = Pattern.compile(NAME_PATTERN);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    /*public static boolean isValidWord(String w) {
        return w.matches("[A-Za-z][^.]*");
    }*/
    //validating mobile no
    public static boolean isValidMobile(String phone)
    {

        if (!TextUtils.isEmpty(phone)) {
            return Patterns.PHONE.matcher(phone).matches();
        }
        return false;
    }


   /* //Password validation
    public static boolean isValidPassword(String password) {
        if (password.length() == 0 || password.length() > 10) {
            return false;
        } else {
            return true;
        }
    }*/


    //Date Validation
    public static boolean getDiffYears(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        dob.set(year, month, day);
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        if (age >= ageInt) {
            return true;
        } else {
            return false;
        }
    }

    //Address Validation
    //Blank space validation
    public static void removeWhiteSpaceFromFront(final AppCompatEditText editText) {
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0 && s.subSequence(0, 1).toString().equalsIgnoreCase(" ")) {
                    editText.setText("");
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });
    }


    public static String validateNewPass(String pass1, String pass2) {
        StringBuilder retVal = new StringBuilder();

        if (pass1.length() < 1 || pass2.length() < 1) retVal.append("Empty fields <br>");

        Logger logger = null;

        if (pass1 != null && pass2 != null) {

            if (pass1.equals(pass2)) {
                logger.info(pass1 + " = " + pass2);

                pass1 = pass2;
                boolean hasUppercase = !pass1.equals(pass1.toLowerCase());
                boolean hasLowercase = !pass1.equals(pass1.toUpperCase());
                boolean hasNumber = pass1.matches(".*\\d.*");
                boolean noSpecialChar = pass1.matches("[a-zA-Z0-9 ]*");

                if (pass1.length() < 11) {
                    logger.info(pass1 + " is length < 11");
                    retVal.append("Password is too short. Needs to have 11 characters <br>");
                }

                if (!hasUppercase) {
                    logger.info(pass1 + " <-- needs uppercase");
                    retVal.append("Password needs an upper cases <br>");
                }

                if (!hasLowercase) {
                    logger.info(pass1 + " <-- needs lowercase");
                    retVal.append("Password needs a lowercase <br>");
                }

                if (!hasNumber) {
                    logger.info(pass1 + "<-- needs a number");
                    retVal.append("Password needs a number <br>");
                }

                if (noSpecialChar) {
                    logger.info(pass1 + "<-- needs a specail character");
                    retVal.append("Password needs a special character i.e. !,@,#, etc.  <br>");
                }
            } else {
                logger.info(pass1 + " != " + pass2);
                retVal.append("Passwords don't match<br>");
            }
        } else {
            logger.info("Passwords = null");
            retVal.append("Passwords Null <br>");
        }
        if (retVal.length() == 0) {
            logger.info("Password validates");
            retVal.append("Success");
        }

        return retVal.toString();

    }


}
