package cirrus.models;

public enum Role {
	USER, ADMIN;
	
	public static Role fromString(String parameterName) {
        if (parameterName != null) {
            for (Role objType : Role.values()) {
                if (parameterName.equalsIgnoreCase(objType.toString())) {
                    return objType;
                }
            }
        }
        return USER;
    }
}