import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class Message {
    private IP Source;
    private IP Destination;
    private List<IP> PointList;

    public Message() {
        PointList = new LinkedList<>();
    }

    public Message(String jsonString) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonString);
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

    // return a string as the format of json
    @Override
    public String toString() {
        return toJSONObject().toString();
    }

    public JSONObject toJSONObject() {
        try {
            JSONObject obj = new JSONObject();
            obj.put("Source", Source.toString());
            obj.put("Destination", Destination.toString());
            obj.put("PointList", PointList);
            return obj;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void show() {
        MyConsole.log("Source:" + Source.toString());
        MyConsole.log("Destination:" + Destination.toString());
        MyConsole.log("PointList:");
        for (int i = 0; i < PointList.size(); i++) {
            MyConsole.log("          "+PointList.get(i).toString());
        }
    }
}
