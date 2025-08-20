package com.mc.sdl.constants;

/**
 * Unified constants for the shared database library
 * Contains all field names and query parameters used across modules
 */
public class SDLConstants {
    
    // Common status field used across all modules
    public static final String STATUS = "status";
    
    // Auth Module Constants
    public static final String SERVICE_CODE = "serviceCode";
    public static final String SERVICE_AUTH_KEY = "serviceAuthKey";
    public static final String ALLOWED_IPS = "allowedIps";
    public static final String IP_ADDRESS = "ipAddress";
    
    // User Module Constants
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String MOBILE_NO = "mobileNo";
    public static final String ROLE_ID = "roleId";
    public static final String OPERATOR_ID = "operatorId";
    public static final String CLIENT_ID = "clientId";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String NAME = "name";
    
    // Common audit fields (if needed in future)
    public static final String CREATED_AT = "createdAt";
    public static final String UPDATED_AT = "updatedAt";
    public static final String CREATED_BY = "createdBy";
    public static final String UPDATED_BY = "updatedBy";
    
    private SDLConstants() {
        // Private constructor to prevent instantiation
    }
}
