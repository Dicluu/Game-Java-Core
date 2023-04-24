package Main;

import java.io.*;
import java.util.Scanner;

public class Replicator {
    public static void main(String[] args) throws Exception {

        System.out.println("Welcome to replicator");
        System.out.println("Use command with format like xx:xx:xx:xx");
        System.out.println("To more information you can write 'help'");
        Scanner str = new Scanner(System.in);
        while (true) {
            String arg = str.nextLine();
            if (arg.equals("exit")) {
                break;
            } else {
                analyze(arg);
            }
        }
    }

    private static void read(int personID, int speechID) {
        try {
            File speeches = new File("src/Main/Resource/" + personID + "/" + "speeches.txt");
            int counter = 0;
            BufferedReader br = new BufferedReader(new FileReader(speeches));

            while (br.ready()) {
                if (speechID == counter) {
                    System.out.println(br.readLine());
                    return;
                } else {
                    br.readLine();
                }
                counter++;
            }
            System.out.println("speech with this id not found");
        }
        catch (FileNotFoundException e) {
            System.out.println("Person with this id not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void analyze(String command) throws IOException {
        String[] args = command.split(":");
        switch (args[0]) {
            case "read":
                try {
                    read(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
                } catch (Exception e) {
                    System.out.println("Wrong format of second argument");
                }
                break;
            case "write":
                try {
                    if (Boolean.parseBoolean(args[3])) {
                        write(Integer.parseInt(args[1]), args[2], args[3], args[4]);
                    } else {
                        write(Integer.parseInt(args[1]), args[2], args[3], "");
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Exception in case 'write' ");
                }
                break;
            case "readAll":
                try {
                    readAll(Integer.parseInt(args[1]));
                }
                catch (Exception e) {
                    System.out.println("Exception in case 'readAll' ");
                }
                break;
            case "help":
                help(0);
                break;
            case "commands":
                help(1);
                break;
            default:
                System.out.println("this command not found");
        }
    }

    private static void help(int direction) throws IOException {
        File help = null;
        if (direction == 0) {
            help = new File("src/Main/Resource/help");
        }
        if (direction == 1) {
            help = new File("src/Main/Resource/commands");
        }
        BufferedReader br = new BufferedReader(new FileReader(help));
        while (br.ready()) {
            System.out.println(br.readLine());
        }
    }

    private static void write(int personID, String speech, String answerable, String answers) throws IOException {
        File dir = new File ("src/Main/Resource/" + personID + "");

        if (!dir.exists()) {
            System.out.println(dir.getAbsolutePath());
            dir.mkdir();
        }

        File speeches = new File("src/Main/Resource/" + personID + "/speeches.txt");

        if (!speeches.exists()) {
            speeches.createNewFile();
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter(speeches, true));
        BufferedReader br = new BufferedReader(new FileReader(speeches));
        int counter = 0;


        bw.write(speech + ":" + answerable + ":" + answers);
        bw.newLine();
        bw.flush();
        bw.close();
        while (br.ready()) {
            br.readLine();
            counter++;
        }
        System.out.println("speech '" + speech + "' added. speechID: " + (counter-1));

    }

    private static void readAll(int PersonID) throws IOException {
        File speeches = new File("src/Main/Resource/" + PersonID + "/speeches.txt");
        BufferedReader br = new BufferedReader(new FileReader(speeches));
        int count = 0;
        while (br.ready()) {
            System.out.println("[" + count++ + "] " + br.readLine());
        }
    }

}
