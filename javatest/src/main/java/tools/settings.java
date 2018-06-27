package main.java.tools;

public class settings {

	public static String env(String env) {

		String url = "opensns";
		if (env == "test") {
			url = "http://192.168.200.198:8008";
		} else if (env == "dev") {
			url = "";
		} else if (env == "online") {
			url = "";
		}
		return url;
	}
	
	public enum Reg_Type {
	    email(0), phone(1);

	    private final int value;
	    private Reg_Type(int value) {
	        this.value = value;
	    }

	    public Reg_Type valueOf(int value) {
	        switch (value) {
	        case 0:
	            return Reg_Type.email;
	        case 1:
	            return Reg_Type.phone;
	        default:
	            return null;
	        }
	    }

	}

}
