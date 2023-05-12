package Main;

import Main.Utils.Annotations.NeedImprovement;

import java.io.*;
import java.util.Scanner;

@NeedImprovement(comment = "add a locales")
@NeedImprovement(comment = "add an about")
@NeedImprovement(comment = "add an ability to change speeches")
public class Replicator {

    static boolean print = false;

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

    private static void read(int personID, int speechID) throws IOException {
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
        } catch (FileNotFoundException e) {
            System.out.println("Person with this id not found");
        }
    }

    private static void analyze(String command) throws IOException {
        String[] args = command.split(":");
        try {
            switch (args[0]) {
                case "read":
                    try {
                        read(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
                    } catch (Exception e) {
                        System.out.println("Exception in case 'read'");
                        System.out.println("Use command 'commands' to help");
                    }
                    break;
                case "write":
                    try {
                        if (args.length == 4) {
                            write(Integer.parseInt(args[1]), args[2], "true", args[3]);
                        } else {
                            write(Integer.parseInt(args[1]), args[2], "false", "");
                        }
                    } catch (Exception e) {
                        System.out.println("Exception in case 'write' ");
                        System.out.println("Use command 'commands' to help");
                    }
                    break;
                case "readAll":
                    try {
                        readAll(Integer.parseInt(args[1]));
                    } catch (Exception e) {
                        System.out.println("Exception in case 'readAll' ");
                        System.out.println("Use command 'commands' to help");
                    }
                    break;
                case "create":
                    try {
                        create(Integer.parseInt(args[1]), args[2]);
                    } catch (Exception e) {
                        System.out.println("Exception in case 'create'");
                        System.out.println("Use command 'commands' to help");
                    }
                    break;
                case "info":
                    try {
                        info(Integer.parseInt(args[1]));
                    }
                    catch (Exception e) {
                        System.out.println("Exception in case 'info'");
                    }
                    break;
                case "help":
                    help(0);
                    break;
                case "commands":
                    help(1);
                    break;
                case "print":
                    if (print) {
                        System.out.println("print stack trace disabled");
                        print = false;
                    } else {
                        System.out.println("print stack trace enabled");
                        print = true;
                    }
                    break;
                default:
                    System.out.println("this command not found");
                    System.out.println("Use command 'commands' to help");
            }
        } catch (Exception e) {
            if (print) {
                e.printStackTrace();
            }
        }
    }

    private static void help(int direction) throws IOException {
        File help = null;
        switch (direction) {
            case 0:
                help = new File("src/Main/Resource/help");
                break;
            case 1:
                help = new File("src/Main/Resource/commands");
        }
        BufferedReader br = new BufferedReader(new FileReader(help));
        while (br.ready()) {
            System.out.println(br.readLine());
        }
    }

    private static void write(int personID, String speech, String answerable, String answers) throws IOException {
        File dir = new File("src/Main/Resource/" + personID + "");

        if (!dir.exists()) {
            System.out.println("Person with this id not found");
            System.out.println("To create person you need to use command 'create'");
        }

        File speeches = new File("src/Main/Resource/" + personID + "/speeches.txt");

        if (!speeches.exists()) {
            speeches.createNewFile();
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter(speeches, true));
        BufferedReader br = new BufferedReader(new FileReader(speeches));
        int counter = 0;

        if (Boolean.parseBoolean(answerable)) {
            String[] answersArr = answers.split(",");
            for (String a : answersArr) {
                try {
                    int ans = Integer.parseInt(a);
                    if (ans < 0) {
                        System.out.println("answers IDs must be positive");
                        return;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("answersList have to contain player's speeches IDs divided by symbol ','");
                    return;
                }
            }
        }

        while (br.ready()) {
            br.readLine();
            counter++;
        }

        try {
            if (Boolean.parseBoolean(answerable)) {
                bw.write(counter + ":" + speech + ":" + answerable + ":" + answers);
                bw.newLine();
            } else {
                bw.write(counter + ":" + speech + ":" + answerable);
                bw.newLine();
            }
        }
        catch (Exception e) {
            System.out.println("Exception write() caught");
        }
        bw.flush();
        bw.close();

        System.out.println("speech '" + speech + "' added. speechID: " + counter);

    }

    private static void readAll(int PersonID) throws IOException {
        File speeches = new File("src/Main/Resource/" + PersonID + "/speeches.txt");
        BufferedReader br = new BufferedReader(new FileReader(speeches));
        int count = 0;
        while (br.ready()) {
            System.out.println("[" + count++ + "] " + br.readLine());
        }
    }

    private static void create(int personID, String name) throws IOException {
        File dir = new File("src/Main/Resource/" + personID);
        File nf = new File("src/Main/Resource/" + personID + "/name.txt");
        if (!dir.exists()) {
            dir.mkdir();
        } else {
            System.out.println("person with this id already exists");
        }
        if (!nf.exists()) {
            nf.createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(nf));
            bw.write(name);
            bw.flush();
            bw.close();
            System.out.println("name.txt was created");
        }
    }

    private static void info(int personID) throws IOException {
        File dir = new File("src/Main/Resource/" + personID);
        try {
            File name = new File(dir.getPath() + "/name.txt");
            BufferedReader brn = new BufferedReader(new FileReader(name));
            System.out.println("name: " + brn.readLine());
        }
        catch (FileNotFoundException e) {
            System.out.println("[exception] name not found for this person");
        }
        readAll(personID);
    }
}