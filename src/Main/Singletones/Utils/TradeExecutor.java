package Main.Singletones.Utils;

import Main.Items.Item;
import Main.Objects.Characters.Character;
import Main.Objects.Characters.Player;
import Main.Utils.Messenger;
import Main.Utils.FileLoaders.TextReader;

import java.util.Scanner;

public class TradeExecutor {

    private static Player player;
    private static Character companion;
    private static boolean active;

    public static void start(Player player, Character companion) {
        TradeExecutor.player = player;
        TradeExecutor.companion = companion;
        TradeExecutor.active = true;
        render();
    }

    private static void render() {
        Scanner str = new Scanner(System.in);
        Messenger.ingameMessage("What you can suggest?");
        while (active) {
            Messenger.helpMessage("You are in trade menu, use commands 'sell' to sell and 'buy' to buy something");
           String arg = str.nextLine();
            switch (arg) {
                case "buy":
                    buy();
                    break;
                case "sell":
                    sell();
                    break;
                case "mute":
                    return;
                case "help":
                    help();
                    break;
                default:
                    Messenger.helpMessage("You can write 'help' to get help");
                    break;
            }
        }
        Messenger.helpMessage("You left trade menu");
    }

    private static void sell() {
        Scanner num = new Scanner(System.in);
        String arg;
        Messenger.ingameMessage("What do you want to sell?");
        while(true) {
            try {
                player.showInventory();
                player.showInventoryPrice();
                Messenger.helpMessage("Use number to sell the item");
                arg = num.nextLine();
                switch (arg) {
                    case "back":
                        return;
                    case "mute":
                        active = false;
                        return;
                }
                Item i = player.getItem(Integer.parseInt(arg));
                if (companion.putItem(i)) {
                    player.addMoney(i.getPrice());
                    player.deleteItemFromInventory(Integer.parseInt(arg));
                    Messenger.helpMessage("You successfully sold " + i.getName());
                    Messenger.helpMessage("Now you have " + player.getWallet() + "$");
                } else {
                    Messenger.ingameMessage("I'm sorry, but i have nowhere to store your product, come another time");
                }
            }
            catch (NumberFormatException e) {
                Messenger.systemMessage("NumberFormatException in method sell()", TradeExecutor.class);
            }
            catch (NullPointerException e) {
                Messenger.systemMessage("NullPointerException in method sell()", TradeExecutor.class);
                Messenger.helpMessage("You tried to sell the air");
            }
            catch (ArrayIndexOutOfBoundsException e) {
                Messenger.systemMessage("ArrayIndexOutOfBoundsException in method sell()", TradeExecutor.class);
                Messenger.helpMessage("You wrote wrong number");
            }
        }
    }

    private static void buy() {
        Scanner num = new Scanner(System.in);
        String arg;
        Messenger.ingameMessage("What do you want to buy?");
        while (true) {
            try {
                Messenger.helpMessage("Use number to buy the item");
                companion.showInventory();
                companion.showInventoryPrice();
                Messenger.ingameMessage("You have " + player.getWallet() + "$");
                arg = num.nextLine();
                switch (arg) {
                    case "help":
                        help();
                        break;
                    case "back":
                        return;
                    case "mute":
                        active = false;
                        return;
                }
                int idx = Integer.parseInt(arg);
                Item i = companion.getItem(idx);
                if (player.getWallet() >= i.getPrice()) {
                    if (player.putItem(i)) {
                        player.takeMoney(i.getPrice());
                        companion.deleteItemFromInventory(idx);
                        Messenger.helpMessage("You successfully buy " + i.getName());
                    }
                    else {
                        Messenger.ingameMessage("Your inventory is full");
                    }
                } else {
                    Messenger.ingameMessage("You don't have enough money");
                }
            }
            catch (NullPointerException e) {
                Messenger.systemMessage("NulNullPointerException in buy()", TradeExecutor.class);
            }
            catch (NumberFormatException e) {
                Messenger.systemMessage("NumberFormatException in method sell()", TradeExecutor.class);
            }
        }
    }

    private static void help() {
        TextReader.read("helpTrade");
    }

}
