package ba.biggy.androidbis.POJO.retrofitServerObjects;


import ba.biggy.androidbis.POJO.Fault;

public class AddFaultServerResponse {

    private Fault[] fault;
    private String result;

    public Fault[] getFault() {
        return fault;
    }

    public String getResult() {
        return result;
    }
}
