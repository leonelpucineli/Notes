package lp.toolkit;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * To use JSON you must construct the object you want to and then you can add its values.
 */
public class JSON {
    // Atributes:
    private HashMap<String, Object> keysValues = new HashMap<>();
    
// Those lines below must be used to construct the object
// Caution: Both constructors have a limited validation for their inputs only checking if it is not duplicated.
    // Constructors
    public JSON(HashMap<String, Object> keysValues) {
        this.keysValues = keysValues;
    }

    public JSON(String keys) { // Use "keyA,KeyB,keyC,..."
        for (String key : keys.split(",")){
            if (keysValues.containsKey(key)) {
                System.out.println("Duplicate key: "+ key + " has not been added to this json's keys");
                continue;
            }

            keysValues.put(key, null);
        }
    }

    public JSON(ArrayList<String> keys) {
        for (int i = 0; i < keys.size(); i++) {
            if (!keysValues.containsKey(keys.get(i))) {
                keysValues.put(keys.get(i), null);
            }
        }
    }

    // Methods
    public void addKey(String key){
        if (!keysValues.containsKey(key)) {
            keysValues.put(key, null);
        }
        else {
            System.out.println("The '" + key + "' already exists on that json.");
        }
    }

    public void deleteKey(String key) {
        if (keysValues.remove(key) == null) {
            System.out.println("WARNING: There's no key named '" + key + "'. Nothing was removed.");
        }
    }
    
// Following lines are methods to manipulate the object
    public void setValue(String key, String value){
        if (keysValues.containsKey(key)) {
            keysValues.put(key, value);
        }
        else {
            System.out.println("WARNING: There's no key named '" + key + "'. Nothing was added.");
        }
    }

    
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{");
        
        for (String key : keysValues.keySet()) {
            Object value = keysValues.get(key);
            
            if (value != null) {
                Class<?> valueClass = value.getClass();

                System.out.println(value);
                switch (valueClass.getName()) {
                    case "java.lang.String" -> {
                        stringBuilder.append("\"" + key + "\": \"" + value + "\",");
                    }

                    case "java.lang.Character" -> {
                        stringBuilder.append("\"" + key + "\": '" + value + "',");
                    }
                    
                    default -> {
                        stringBuilder.append("\"" + key + "\": " + value + ",");
                    }
                }
            }
            else {
                stringBuilder.append("\"" + key + "\": " + value + ",");
            }
        }
        /* Omitting the check "if (stringBuilder.length() > 1)" intentionally
        to let an exception occur if the method is incorrectly used with an empty map. */
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        // Here we close the method
        stringBuilder.append("}");
        
        return stringBuilder.toString();
    }
    
    public static ArrayList<String> getKeys(String string) {
        ArrayList<String> keys = new ArrayList<>();
        int index = 1; // Ignore {
            
            boolean firstIteration = true;
            
            while (index < string.length()) {
                
                if (firstIteration) {
                    int keyEndIndex = string.indexOf('"', 2);
                    keys.add(string.substring(3, keyEndIndex));
                    firstIteration = false;
                    index = keyEndIndex;    
                continue;
            }

            int colonIndex = string.indexOf(':', index);
            int keyStartIndex = string.indexOf('"', colonIndex)+1;
            int keyEndIndex = string.indexOf('"', keyStartIndex+1);
            if ((keyStartIndex <= 0) || (keyEndIndex <= 0)) {
                break;
            }

            String key = string.substring(keyStartIndex, keyEndIndex);

            keys.add(key);

            index = keyEndIndex;
        }
        
        return keys;
    }

    public ArrayList<String> getValues(String string) {
        ArrayList<String> keys = getKeys(string);
        ArrayList<String> values = new ArrayList<>();
        int index = 0;
        int valueEndIndex = 0;

        while (valueEndIndex >= string.length()) {
            int keyIndex = string.indexOf(keys.get(index));
            int nextKeyIndex = string.indexOf(keys.get(index + 1));
            

            int valueStartIndex = string.indexOf('"',keyIndex) + 3;

            if (nextKeyIndex + 1 < keys.size()) {
                valueEndIndex = nextKeyIndex-2;
            }
            else {
                valueEndIndex = string.indexOf("}");
            }

            index++;

            String value = string.substring(valueStartIndex, valueEndIndex);

            values.add(value);

        }

        return values;
    }

    public Object getValue(String key) {
        return keysValues.get(key);
    }

    public JSON set(String string){
        ArrayList<String> values = getValues(string);
        ArrayList<String> keys = getKeys(string);

        HashMap<String, Object> keysValues = new HashMap<>();

        for (int i = 0; i < values.size(); i++) {
            keysValues.put(keys.get(i), values.get(i));
        }

        return new JSON(keysValues);
    }
}