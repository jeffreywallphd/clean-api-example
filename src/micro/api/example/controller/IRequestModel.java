package micro.api.example.controller;

import java.util.Set;

public interface IRequestModel {
	public Object newRequestObject();
	public Object parse(Object data);
	public Object get(String key);
	public Object containsKey(String key);
	public Set keySet(); 
}
