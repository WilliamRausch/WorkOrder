import java.util.Scanner;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
public class Creator {
    public void createWorkOrders(){
        Scanner scan = new Scanner(System.in);

        for(int i=0;i<200; i++) {
            int id = i;
            System.out.println("Enter work order description for workorder # " + i);
            String description = scan.nextLine();
            System.out.println("Enter work order sendername for work order #  " + i);
            String sendername = scan.nextLine();
            WorkOrder order = new WorkOrder(id, description, sendername, Status.INITIAL);
            convert(order);


        }

    }
    public static void main(String args[]) {
        //public String json = "";
        Creator creator = new Creator();

        creator.createWorkOrders();

    }
    public static void convert(WorkOrder order){

        try {
            System.out.println(order);
            ObjectMapper mapper = new ObjectMapper();

            String json = mapper.writeValueAsString(order);
            System.out.println(json);
            File file = new File(WorkOrder.getSenderName() + ".json");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(json);
            fileWriter.close();
            //Processor process = new Processor();
            //process.processWorkOrders(json);

        }catch(IOException ex){
            System.out.println("exception");
        }

    }

}
