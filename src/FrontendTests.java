import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class FrontendTests {

    private boolean containsBuildings(String output, int[] numbers){
        for (int number : numbers){
            if (!output.contains(String.format("Building %d", number)))
                return false;
        }
        return true;
    }

    @Test void testFrontendBuildingToBuilding(){
        // Remember System.in and System.out
        PrintStream stdout = System.out;
        InputStream stdin  = System.in;

        try {
            // Simulate input
            String input = String.format("p 0 5%sx", System.lineSeparator());
            InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
            System.setIn(inputStreamSimulator);
            // Capture output
            ByteArrayOutputStream outputCaptor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputCaptor));

            // Run test
            new XFrontend(new BackendDummy(), new ArrayList<>());

            // Evaluate results
            String sout = outputCaptor.toString();
            int[] buildingNumbers = {0, 1, 2, 3, 4, 5};

            Assertions.assertTrue(containsBuildings(sout, buildingNumbers));
        } catch (Exception e){
            // Restore System.in and out
            System.setOut(stdout);
            System.setIn(stdin);
            // Fail test
            Assertions.fail();
            e.printStackTrace();
        }

        // Restore System.in and out
        System.setOut(stdout);
        System.setIn(stdin);
    }

    @Test void testFrontendFindDining(){
        // Remember System.in and System.out
        PrintStream stdout = System.out;
        InputStream stdin  = System.in;

        try {
            // Simulate input
            String input = String.format("p 2 dining%sx", System.lineSeparator());
            InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
            System.setIn(inputStreamSimulator);
            // Capture output
            ByteArrayOutputStream outputCaptor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputCaptor));

            // Run test
            new XFrontend(new BackendDummy(), new ArrayList<>());

            // Evaluate results
            String sout = outputCaptor.toString();
            int[] buildingNumbers = {2, 3, 4};

            Assertions.assertTrue(containsBuildings(sout, buildingNumbers));
        } catch (Exception e){
            // Restore System.in and out
            System.setOut(stdout);
            System.setIn(stdin);
            // Fail test
            Assertions.fail();
            e.printStackTrace();
        }

        // Restore System.in and out
        System.setOut(stdout);
        System.setIn(stdin);
    }

    @Test void testFrontendFindLibrary(){
        // Remember System.in and System.out
        PrintStream stdout = System.out;
        InputStream stdin  = System.in;

        try {
            // Simulate input
            String input = String.format("p 1 library%sx", System.lineSeparator());
            InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
            System.setIn(inputStreamSimulator);
            // Capture output
            ByteArrayOutputStream outputCaptor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputCaptor));

            // Run test
            new XFrontend(new BackendDummy(), new ArrayList<>());

            // Evaluate results
            String sout = outputCaptor.toString();
            int[] buildingNumbers = {1, 2};

            Assertions.assertTrue(containsBuildings(sout, buildingNumbers));
        } catch (Exception e){
            // Restore System.in and out
            System.setOut(stdout);
            System.setIn(stdin);
            // Fail test
            Assertions.fail();
            e.printStackTrace();
        }

        // Restore System.in and out
        System.setOut(stdout);
        System.setIn(stdin);
    }

    @Test void testFrontendFindParking(){
        // Remember System.in and System.out
        PrintStream stdout = System.out;
        InputStream stdin  = System.in;

        try {
            // Simulate input
            String input = String.format("p 1 parking%sx", System.lineSeparator());
            InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
            System.setIn(inputStreamSimulator);
            // Capture output
            ByteArrayOutputStream outputCaptor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputCaptor));

            // Run test
            new XFrontend(new BackendDummy(), new ArrayList<>());

            // Evaluate results
            String sout = outputCaptor.toString();
            int[] buildingNumbers = {1};

            Assertions.assertTrue(containsBuildings(sout, buildingNumbers));
        } catch (Exception e){
            // Restore System.in and out
            System.setOut(stdout);
            System.setIn(stdin);
            // Fail test
            Assertions.fail();
            e.printStackTrace();
        }

        // Restore System.in and out
        System.setOut(stdout);
        System.setIn(stdin);
    }

    @Test void testFrontendBuildingList() {
        // Remember System.in and System.out
        PrintStream stdout = System.out;
        InputStream stdin  = System.in;

        try {
            // Simulate input
            String input = String.format("list%sx", System.lineSeparator());
            InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
            System.setIn(inputStreamSimulator);
            // Capture output
            ByteArrayOutputStream outputCaptor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputCaptor));

            // Run test
            ArrayList<Building<Integer>> buildings = new ArrayList<>();
            for (int i = 0; i < 16; i++) {
                buildings.add(new Building<>(i, String.format("Building %d", i), false, false, false));
            }
            new XFrontend(new BackendDummy(), buildings);

            // Evaluate results
            String sout = outputCaptor.toString();
            int[] buildingNumbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};

            Assertions.assertTrue(containsBuildings(sout, buildingNumbers));
        } catch (Exception e){
            // Restore System.in and out
            System.setOut(stdout);
            System.setIn(stdin);
            // Fail test
            Assertions.fail();
            e.printStackTrace();
        }

        // Restore System.in and out
        System.setOut(stdout);
        System.setIn(stdin);
    }

}
