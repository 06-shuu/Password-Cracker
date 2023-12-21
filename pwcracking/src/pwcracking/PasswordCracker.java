package pwcracking;

import java.io.*;
import java.util.Scanner;

public class PasswordCracker {

    public static void main(String args[]) {
     
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a hash value that you want to crack: ");
        String hash = scanner.next();

        // to check if the password list file exists
        File passwordDictionary = new File("password-dictionary.txt");
        if (!passwordDictionary.exists() || !passwordDictionary.isFile()) {
            System.out.println("Error: password dictionary file not found.");
            System.exit(1);
        }
        
        File outputFile = new File("output.txt");
        if (!outputFile.exists() || !outputFile.isFile()) {
            System.out.println("Error: password dictionary file not found.");
            System.exit(1);
        }
        hash = hash.toLowerCase();
        
        String crackedPassword = getCrackedPasswordFromOutput(hash);
        if(crackedPassword == null) { //if the value isn't cracked and in our output.txt
        // here we keep finding the sha256 values for the dictionary and compare each one of them with the provided one.
        try (Scanner passwordScanner = new Scanner(passwordDictionary)) {

            boolean found = false;

            while (passwordScanner.hasNextLine()) {
                String password = passwordScanner.nextLine();
                String passwordHash = HashFunction.getSHA256Hash(password);
                if (hash.toLowerCase().equals(passwordHash)) {
                    System.out.println("Password cracked: " + password);
                    //write the hash and it's corresponding value into output.txt
                    try (FileWriter fw = new FileWriter(outputFile, true);
                          BufferedWriter bw = new BufferedWriter(fw);
                          PrintWriter pw = new PrintWriter(bw)) {
                         pw.println(hash + " " + password);
                     } catch (IOException e) {
                         System.out.println("Error writing to output file.");
                     }

                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Could not crack password with given dictionary.");
            }

        } catch (FileNotFoundException e) {
            System.out.println(e);
            System.exit(1);
            }
        }
        else {
        	System.out.println("Password already cracked: " + crackedPassword);
        }
    } // end of main

    private static String getCrackedPasswordFromOutput(String hash) {
        try (Scanner outputScanner = new Scanner(new File("output.txt"))) {
            while (outputScanner.hasNextLine()) {
                String line = outputScanner.nextLine();
                String[] parts = line.split(" ");
                if (parts.length == 2 && parts[0].equalsIgnoreCase(hash)) {
                    return parts[1];
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        return null; // Hash not found in output.txt
    }
    
}
