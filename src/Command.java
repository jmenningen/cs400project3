import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public interface Command {

    String getKeyword();

    String getSummary();

    void onRunCommand(String[] args, BackendInterface backendInterface, AtomicBoolean appRunning, ArrayList<Building<Integer>> buildingList);

}
