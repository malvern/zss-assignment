package zw.co.malvern.utils.response;

public class ErrorResponse extends BasicResponse {
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
