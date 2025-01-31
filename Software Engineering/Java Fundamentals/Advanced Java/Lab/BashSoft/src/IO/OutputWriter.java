package IO;

import java.util.List;

public class OutputWriter {

    public static void printStudent(String name, List<Integer> marks) {
        String output = String.format("%s - %s", name, marks.toString());
        OutputWriter.writeMessageOnNewLine(output);
    }

    public static void writeMessage(String message) {
        System.out.print(message);
    }

    public static void writeMessageOnNewLine(String message) {
        System.out.println(message);
    }

    public static void writeEmptyLine() {
        System.out.println();
    }

    public static void displayException(String message) {
        System.out.println(message);
    }
}
