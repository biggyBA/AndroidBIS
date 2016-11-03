package ba.biggy.androidbis.POJO.retrofitServerObjects;



public class UpdateFaultServerRequest {

    private String id;
    private String serviceman;
    private String phone1;
    private String phone2;
    private String faultDescription;

    public void setId(String id) {
        this.id = id;
    }

    public void setServiceman(String serviceman) {
        this.serviceman = serviceman;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public void setFaultDescription(String faultDescription) {
        this.faultDescription = faultDescription;
    }
}
