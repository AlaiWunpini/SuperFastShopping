package com.altechinferno.superfastshopping;

public class products {

    String productName, productPrice, productBarcode, productDescription, imageURL;

    public products() {
    }

    public products(String productName, String productPrice, String productBarcode, String productDescription, String imageURL) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productBarcode = productBarcode;
        this.productDescription = productDescription;
        this.imageURL = imageURL;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductBarcode() {
        return productBarcode;
    }

    public void setProductBarcode(String productBarcode) {
        this.productBarcode = productBarcode;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}