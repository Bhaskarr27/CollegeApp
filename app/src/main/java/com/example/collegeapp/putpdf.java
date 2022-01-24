package com.example.collegeapp;

public class putpdf {
    public String name;
    public String pdfdet;
    public String url;

    public putpdf() {

    }

    public putpdf(String name,String pdfdet,String url) {
        this.name = name;
        this.pdfdet=pdfdet;
        this.url = url;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPdfdet() {
        return pdfdet;
    }

    public void setPdfdet(String pdfdet) {
        this.pdfdet = pdfdet;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
