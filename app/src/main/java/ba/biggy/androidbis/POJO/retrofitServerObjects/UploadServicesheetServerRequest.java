package ba.biggy.androidbis.POJO.retrofitServerObjects;


import ba.biggy.androidbis.POJO.Servicesheet;

public class UploadServicesheetServerRequest {

    private Servicesheet servicesheet;
    private String usedSpareparts;

    public void setServicesheet(Servicesheet servicesheet) {
        this.servicesheet = servicesheet;
    }

    public void setUsedSpareparts(String usedSpareparts) {
        this.usedSpareparts = usedSpareparts;
    }
}
