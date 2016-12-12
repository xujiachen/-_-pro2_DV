import net.sf.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class Message {
    private IP Source;
    private IP Destination;
    private List<IP> PointList;

    public Message() {
        PointList = new LinkedList<>();
    }

    public void setSource(IP ip) {
        Source = ip;
    }

    public IP getSource() {
        return Source;
    }

    public void setDestination(IP destination) {
        Destination = destination;
    }

    public IP getDestination() {
        return Destination;
    }

    public List<IP> getPointList() {
        return PointList;
    }

    public void addPoint(IP ip) {
        PointList.add(ip);
    }

    public JSONObject toJSONObject() {
        JSONObject obj = new JSONObject();
        obj.put("Source", Source.toString());
        obj.put("Destination", Destination.toString());
        obj.put("PointList", PointList);
        return obj;
    }
}
