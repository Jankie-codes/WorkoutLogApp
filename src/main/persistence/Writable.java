package persistence;

import org.json.JSONObject;

//Code influenced by the JsonSerializationDemo: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
//This interface is implemented by all classes whose data can be saved
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
