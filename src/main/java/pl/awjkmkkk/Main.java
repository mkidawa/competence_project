package pl.awjkmkkk;

import pl.awjkmkkk.mode.commandline.CommandLineMode;
import pl.awjkmkkk.mode.graphical.GraphicalMode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {

    /*------------------------ FIELDS REGION ------------------------*/
    private static List<String> mainArgs;

    /*------------------------ METHODS REGION ------------------------*/
    public static List<String> getMainArgs() {
        return Collections.unmodifiableList(mainArgs);
    }

    public static void main(String[] args) {
        mainArgs = Arrays.asList(args);

        if (args.length == 0) {
            new GraphicalMode().main();
        } else {
            try {
                new CommandLineMode().main();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
