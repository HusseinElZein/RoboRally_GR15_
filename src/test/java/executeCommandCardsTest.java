import dk.dtu.compute.se.pisd.roborally.model.Command;
import dk.dtu.compute.se.pisd.roborally.model.CommandCard;

/**
 * Testing out the method for executing the command cards
 */
public class executeCommandCardsTest {

    public static void main(String[] args) {
        /**
         * creating command cards and executing them in numerical order
         */

        Command[] commands = Command.values();
        CommandCard commandcard1 = new CommandCard(commands[0]);
        System.out.println(commandcard1.command);

        CommandCard commandcard2 = new CommandCard(commands[1]);
        System.out.println(commandcard2.command);

        CommandCard commandcard3 = new CommandCard(commands[2]);
        System.out.println(commandcard3.command);

        CommandCard commandcard4 = new CommandCard(commands[3]);
        System.out.println(commandcard4.command);

        CommandCard commandcard5 = new CommandCard(commands[4]);
        System.out.println(commandcard5.command);

        CommandCard commandcard6 = new CommandCard(commands[5]);
        System.out.println(commandcard6.command);

        CommandCard commandcard7 = new CommandCard(commands[6]);
        System.out.println(commandcard7.command);

        CommandCard commandcard8 = new CommandCard(commands[7]);
        System.out.println(commandcard8.command);

        CommandCard commandcard9 = new CommandCard(commands[8]);
        System.out.println(commandcard9.command);

        CommandCard commandcard10 = new CommandCard(commands[9]);
        System.out.println(commandcard10.command);
    }




}
