import java.io.IOException;
import java.util.HashMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.io.File;
import java.io.FileWriter;
import java.util.*;


public class Processor {
    Map<Status, Set<WorkOrder>> workOrders = new HashMap<>();
    public void processWorkOrders() {
        try {
            while (true) {
                readIt();
                moveIt();
                Thread.sleep(5000l);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
       }




    }

    private void moveIt() {
        Set<WorkOrder> inProgress = workOrders.get(Status.IN_PROGRESS);
        if (inProgress.size() != 0){
            WorkOrder current = inProgress.iterator().next();
            inProgress.remove(current);
            current.setStatus(Status.DONE);
            workOrders.get(Status.DONE).add(current);
            try {
                String fileName = current.getId() + ".json";
                File newFile = new File(fileName);
                FileWriter fileWriter = new FileWriter(newFile);

                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(current);

                fileWriter.write(json);
                fileWriter.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        Set<WorkOrder> assigned = workOrders.get(Status.ASSIGNED);
        if (assigned.size() != 0){
            WorkOrder current = assigned.iterator().next();
            assigned.remove(current);
            current.setStatus(Status.IN_PROGRESS);
            workOrders.get(Status.IN_PROGRESS).add(current);

            try {
                String fileName = current.getId() + ".json";
                File newFile = new File(fileName);
                FileWriter fileWriter = new FileWriter(newFile);

                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(current);

                fileWriter.write(json);
                fileWriter.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        Set<WorkOrder> initial = workOrders.get(Status.INITIAL);
        if (initial.size() != 0){
            WorkOrder current = initial.iterator().next();
            initial.remove(current);
            current.setStatus(Status.ASSIGNED);
            workOrders.get(Status.ASSIGNED).add(current);
            try {
                String fileName = current.getId() + ".json";
                File newFile = new File(fileName);
                FileWriter fileWriter = new FileWriter(newFile);

                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(current);

                fileWriter.write(json);
                fileWriter.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        System.out.println("Initial WorkOrders :" + initial.toString());
        System.out.println("Assigned WorkOrders :" + assigned.toString());
        System.out.println("In Progress WorkOrders: " + inProgress.toString());
        System.out.println("Completed WorkOrders: " + workOrders.toString());

        // move work orders in map from one state to another
    }

    private void readIt() {
        ObjectMapper mapper = new ObjectMapper();
        File currentDirectory = new File(".");
        File files[] = currentDirectory.listFiles();
        Map<Integer, Object> map = new HashMap<Integer, Object>();
        for (File f : files) {
            if (f.getName().endsWith(".json")) {
                // f is a reference to a json file
                    System.out.println(f);
                try {
                    WorkOrder workorder = mapper.readValue(f, WorkOrder.class);
                    System.out.println("workorder #:"+  workorder.getId());
                    Status workOrderStatus = workorder.getStatus();
                    Set<WorkOrder> Set = workOrders.get(workOrderStatus);
                    Set.add(workorder);
                    workOrders.put(workOrderStatus, Set);


                } catch (IOException e) {
                    e.printStackTrace();
                }
                // f.delete(); will delete the file
            }
        }
        // read the json files into WorkOrders and put in map
    }

    public static void main(String args[]) {
        Processor processor = new Processor();
        processor.processWorkOrders();
    }
}