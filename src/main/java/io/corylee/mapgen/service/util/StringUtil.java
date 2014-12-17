package io.corylee.mapgen.service.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StringUtil {
    public static List<String> convertObjectListToString(Collection<Object> objects)
    {
    	List<String> array = new ArrayList<>();
    	if(objects != null)
	    {
			for (Object object : objects) {
	    		array.add(object.toString());
			}
    	}
    	return array;
    }
}
