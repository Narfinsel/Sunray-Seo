package datamodel;

public enum EnumLtStatus {
    status_ToStart          ("0 - To Start"),
    status_ToSendEmail      ("1 - To Send Email"),
    status_ToResendEmail    ("2 - To Resend Email"),
    status_Done             ("3 - Done"),
    status_Refused          ("9 - Refused"),
    status_Unidentified     ("10 - N/A");

    private final String ltStatus;

    EnumLtStatus (String ltStatus)  {
        this.ltStatus = ltStatus;
    }

    public String getLtStatus ()    {
        return this.ltStatus;
    }

    public static EnumLtStatus stringToEnum (String str) {

        EnumLtStatus state = EnumLtStatus.status_Unidentified;
        if ( str != null )  {

            if      ( str.equalsIgnoreCase( status_ToStart.ltStatus ))
                state = status_ToStart;

            else if ( str.equalsIgnoreCase( status_ToSendEmail.ltStatus ))
                state = status_ToSendEmail;

            else if ( str.equalsIgnoreCase( status_ToResendEmail.ltStatus ))
                state = status_ToResendEmail;

            else if ( str.equalsIgnoreCase( status_Done.ltStatus ))
                state = status_Done;

            else if ( str.equalsIgnoreCase( status_Refused.ltStatus ))
                state = status_Refused;

            else
                state = status_Unidentified;
        }
        return state;
    }

    @Override
    public String toString() {
        return this.ltStatus;
    }


}
