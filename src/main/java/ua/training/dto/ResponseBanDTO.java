package ua.training.dto;

/**
 * Created by Adrien on 28.03.2017.
 */
public class ResponseBanDTO extends AuthDTO {
    private String success;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
