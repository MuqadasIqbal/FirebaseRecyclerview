package com.example.reycclercrud;

public class model {
    public model(String first, String second, String third, String imageUri) {
        this.first = first;
        this.second = second;
        this.third = third;
        ImageUri = imageUri;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public String getThird() {
        return third;
    }

    public void setThird(String third) {
        this.third = third;
    }

    public String getImageUri() {
        return ImageUri;
    }

    public void setImageUri(String imageUri) {
        ImageUri = imageUri;
    }

    public model() {
    }

    private String first,second,third,ImageUri;
}
