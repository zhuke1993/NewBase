package sei.system;

import java.sql.*;
import com.alibaba.fastjson.serializer.PropertyFilter;

public class JsonFilter implements PropertyFilter {
	public boolean apply(Object arg0, String arg1, Object arg2) {
		if(arg2 instanceof Connection || arg2 instanceof Statement || arg2 instanceof PreparedStatement || arg2 instanceof ResultSet )
			return false;
		
		return true;
	}

}
