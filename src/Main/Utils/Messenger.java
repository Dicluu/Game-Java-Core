package Main.Utils;

import Main.Utils.Annotations.NeedImprovement;

@NeedImprovement(comment = "add a locales")
public class Messenger {

    private static boolean system, help;
    private static boolean ingame = true;

    public static boolean isIngame() {
        return ingame;
    }

    public static void setIngame(boolean ingame) {
        Messenger.ingame = ingame;
    }

    public static void systemMessage(String message) {
        if (system) {
            System.out.println(message);
        }
    }

    public static void systemMessage(String message, Class clazz) {
        if (system) {
            System.out.println("[" + clazz + "]: " + message);
        }
    }

    public static boolean isSystem() {
        return system;
    }

    public static void setSystem(boolean system) {
        Messenger.system = system;
    }

    public static boolean isHelp() {
        return help;
    }

    public static void setHelp(boolean help) {
        Messenger.help = help;
    }

    public static void helpMessage(String message) {
        if (help) {
            System.out.println(message);
        }
    }

    public static void ingameMessage(String message) {
        if (ingame) {
            System.out.println(message);
        }
    }


}
