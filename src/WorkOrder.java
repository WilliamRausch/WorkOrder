public class WorkOrder {
    public int id;
    public String description;
    public static String senderName;
    public Status status;

    public WorkOrder(int id, String description, String senderName, Status status) {
        this.id = id;
        this.description = description;
        this.senderName = senderName;
        this.status = status;
    }

    public WorkOrder() {

    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public static String getSenderName() {
        return senderName;
    }

    public Status getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
