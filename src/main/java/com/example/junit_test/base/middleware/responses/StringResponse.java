package com.example.junit_test.base.middleware.responses;

public interface StringResponse {

    String FILE_TOO_LARGE = "File to large !";
    String DATA_MUST_BE_NOT_NULL = "Data must be not null !";
    String OPTION_NOT_VALID = "Option is not valid !";
    String CREDENTIAL_EXIST = "Credential already exist !";
    String CREDENTIAL_INVALID = "Credential invalid !";
    String ACCOUNT_NOT_ACTIVE_OR_NOT_EXIST = "Account not active or not exist.";
    String PROPERTIES_VALUE_INVALID = "Value not exist in system!";
    String PROPERTIES_VALUE_NOT_ACTIVE = "Value is not activated!";
    String PROPERTIES_VALUE_IS_PRIMARY_EXISTED = "This value is already primary or owned by another!";
    String COINCIDE_STATUS = "Coincide status !";
    String SESSION_NOT_EXIST = "There is no corresponding session !";
    String DEVICE_TYPE_BLANK = "deviceType has not blank !";
    String DEVICE_BLANK = "device has not blank !";
    String OK = "SUCCESS";
    String EMPLOYEE_NOT_EXIST = "This account does not exist!";
    String EMPLOYEE_EXTRA_NOT_EXIST = "This account extra does not exist!";
    String EMPLOYEE_EXTRA_DELETED = "This account extra has deleted!";
    String EMPLOYEE_IS_NOT_PRIME = "This account is not primary!";
    String SYSTEM_NULL = "This account have not system!";
    String CODE_NOT_EXIST = "This code does not exist!";
    String USER_FB_DENIED = "user Facebook denied!";
    String STATE_CODE_NOT_EXIST = "This stateCode does not exist!";
    String CHECKER_CODE_NOT_EXIST = "This checker code does not exist!";
    String CANNOT_GET_ROLE_CREDENTIAL = "Can't get info credential role!";
    String USERNAME_ALREADY_EXISTS = "Username existed";
    String EMAIL_NOT_EXISTS = "This account has no mail information.";
    String ACCOUNT_WAIT_ACTIVE = "Account is not activated!";
    String ACCOUNT_IS_DELETED = "Account is deleted!";
    String NOT_CHANGE_STATUS_IS_DELETED = "Cannot change to delete status";
    String ACCOUNT_NOT_NEED_ACTIVE = "Account don't need active!";
    String ACCOUNT_SECOND_NOT_EXIST = "Second Account does not exist!";
    String ACCOUNT_NOT_DELETE = "Account cannot delete!";
    String ACCOUNT_LOCKED = "Account is locked !";
    String ADMIN_NOT_EXIST = "Admin not exist !";
    String ACCOUNT_LOCKED_OR_NOT_ACTIVE = "Account is locked or not active !";
    String OTP_INVALID = "Authentication code does not exist or has expired!";
    String ACCURACY_SUCCESS = "ACCURACY Success !";
    String VERIFY_PHONE_SUCCESS = "Successful sending otp code to phone!";
    String VERIFY_EMAIL_SUCCESS = "Successful sending otp code to email!";
    String PHONE_NO_FAIL = "Invalid phone number !";
    String PROPERTIES_NOT_NEED_ACTIVE = "Phone number or email is activated !";
    String PROPERTIES_PRIME = "Phone number or email is prime !";
    String PROPERTIES_NOT_EXIST = "Phone number or email does not exist !";
    String PROPERTIES_NOT_VALID = "Phone number or email is incorrect !";
    String NEW_PROPERTIES_NOT_VALID = "New phone number or email is incorrect !";
    String PROPERTIES_EXIST = "user already owns this properties !";
    String SENT_OTP_TO_PHONE_FALSE = "Could not send otp to phone number please contact the administrator !";
    String SENT_OTP_TO_EMAIL_FALSE = "Could not send otp to email please contact the administrator !";
    String CAN_NOT_SUPPORT_ACCURACY = "The system does not support authentication via this method!";
    String CLIENT_ID_INVALID = "clientId is invalid or not exist !";
    String INVALID_TOKEN = "Token is invalid or expired !";
    String INVALID_CODE_FROM_FACEBOOK = "FaceBook code is invalid or expired !";
    String PHONE_NUMBER_ALREADY_EXISTS = "Phone number existed";
    String NEW_USERNAME_EXISTED = "New username is existed !";
    String LOGIN_FAILED = "Authentication is invalid";
    String EMAIL_ALREADY_EXISTS = "Email already exists in the system";
    String ACTION_NOT_SUPPORT = "Action cannot be performed!";
    String EMAIL_NOT_ACTIVE = "Email is not verified !";
    String LOGIN_EXCEEDED = "Sign in more than the specified number of times";
    String ERROR_SERVER = "System error. Something went wrong. Please try again later";
    String INVALID_ACCOUNT = "Invalid account";
    String TRACKER_NULL = "Tracker null !";
    String LOGIN_INFORMATION_INCORRECT = "Login information is incorrect!";
    String INVALID_USERNAME_PASS = "Username or password is invalid !";

    //end user
    String INVALID_CODE = "Code is invalid !";
    String EMAIL_NOT_VALID = "Email is incorrect !";
    String PASSWORD_SAME_OLD_PASSWORD = "The password cannot be the same as the old password";
    String PASSWORD_IS_INVALID = "Password is invalid!";
    String OLD_PASSWORD_IS_INCORRECT = "Old password is incorrect";
    String SYSTEM_END_USER_NOT_FOUND = "System end user not found!";
    String SYSTEM_END_USER_EXISTED = "System end user existed!";
    //upload file
    //payment card
    String INVALID_CARD_NUMBER = "Card number is incorrect !";
    String CARD_NOT_EXIST = "Payment card not exist !";
    String CARD_EXISTED = "Payment card is existed !";
    String REDIRECT_URL_INVALID = "Redirect url invalid !";
    String IMAGE_URL_INVALID = "Url image invalid !";

    //notify config
    String CONF_NOT_EXIST = "Notification configuration not exist !";

    //logo
    String LOGO_NOT_EXIST = "Logo is not exist !";

    //Role
    String ROLE_NOT_EXIST = "Role not exist";
    String CANNOT_DELETE_THIS_ROLE = "This role has assign to someone";
    String ROLE_NAME_IS_EXISTED = "Role name is existed";
    String ROLE_HAVE_NO_MODULES = "This role has no modules";
    String ROLE_HAVE_NO_FEATURES = "This role has no features";

    //System_integration
    String SYSTEM_NOT_EXIST = "System not exist";
    String SYSTEM_ALREADY_EXIST = "System already exist";
    String CHANGE_NOT_ALLOW = "Change not allow";
    String ID_INVALID = "Account id inValid";

    //policy
    String POLICY_NOT_EXIST = "Policy not exist";
    String POLICY_IS_DELETED = "Policy is deleted";
    String CAN_NO_CHANGE_TO_DELETE = "Cannot be changed to delete status";

    String INVALID_MODULE_LIST = "Invalid module list";

    //Complain
    String COMPLAIN_NOT_EXIST = "Complain not exist";

    String FIELD_IN_VALID = "Field invalid !";

    //EndUser
    String DATE_NOT_VALID = "Date is invalid";
    //ModuleFeature
    String INVALID_FEATURE_LIST = "Invalid feature list";

    //Create Feature
    String FEATURE_IS_NULL = "feature is null";
    String FEATURE_IS_EXIST = "feature is exist";
    String FEATURE_IS_NOT_EXIST = "This feature is not exist";

    //Module
    String MODULE_NOT_EXIST = "Module not exist";
    String TYPE_NOT_EXIST_ROLE = "Type not exist role";
    String USER_CAN_NOT_UPDATE = "Can not be found in the system or cannot be updated";
    String MODULE_HAVE_NO_FEATURES = "These modules have no features";
    String CREDENTIAL_NOT_EXIST = "Credential not exist";

    String URL_REDIRECTS_INVALID = "Url redirects invalid";
    String GOOGLE_URL_REDIRECTS_INVALID = "Google Url redirects invalid";
    String LANGUAGE_NOT_SUPPORTED = "This language is not supported";

    String EMAIL_PROPERTIES_NOT_ACTIVE = "Email is not active";
    String END_USER_PRIMARY_NOT_EXIST = "EndUser primary not exist is not active";
    String PHONE_PROPERTIES_NOT_ACTIVE = "Phone is not active";

    String EMAIL_PROPERTIES_IS_PRIMARY_EXISTED = "This email is already primary or owned by another!";
    String PHONE_PROPERTIES_IS_PRIMARY_EXISTED = "This phone is already primary or owned by another!";

    //images
    String IMAGE_NOT_EXISTED = "The images of this user is not exit!";

    String UNIID_INVALID_ACCESS = "You don't have permission to access this source";

    String OVER_LIMIT_OTP = "You mistyped too many. Please try again after 24h.";
    String HAVING_TOKEN = "Having token to take for logIn3G4G";

    String USER_NOT_FOUND = "Not found EndUser";

    String TOKEN_USER_NOT_FOUND = "Not found Token";

    String DEVICE_NOT_FOUND = "Device not found";

    String INVALID_CODE_FROM_APPLE = "Invalid code from Apple!";

    String INVALID_PHONE = "Invalid phone number";

    String PHONE_NOT_REGISTERED = "Phone has not registered yet, please login again to access";

    // DeviceId
    String INVALID_DEVICEID = "DeviceID is invalid!";

    String DEVICEID_NOT_NULL = "deviceId not null !";


    //CMS
    String INVALID_CATEGORY = "Not found category";

    String CATEGORY_IS_PARENT = "Category is parent category";

    String CATEGORY_CONTAIN_CONTENT = "Category contains content";

    // Persional info value
    String VALUE_NOT_EXITS = "Value not exits";
    String INSERT_DATA_FAILED = "INSERT DATA FAILED";

    String CheckKeyLaoApp = "INVALID KEY CODE";
}
