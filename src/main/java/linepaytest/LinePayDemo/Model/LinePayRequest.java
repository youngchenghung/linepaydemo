package linepaytest.LinePayDemo.Model;

import java.util.List;

public class LinePayRequest {
    private int amount;
    private String currency;
    private String orderId;
    private List<Package> packages;
    private RedirectUrls redirectUrls;

    // Getter and Setter for amount
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    // Getter and Setter for currency
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    // Getter and Setter for orderId
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    // Getter and Setter for packages
    public List<Package> getPackages() {
        return packages;
    }

    public void setPackages(List<Package> packages) {
        this.packages = packages;
    }

    // Getter and Setter for redirectUrls
    public RedirectUrls getRedirectUrls() {
        return redirectUrls;
    }

    public void setRedirectUrls(RedirectUrls redirectUrls) {
        this.redirectUrls = redirectUrls;
    }

    // Nested class for Package
    public static class Package {
        private String id;
        private String name;
        private int amount;
        private List<Product> products;

        // Getter and Setter for id
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        // Getter and Setter for name
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        // Getter and Setter for amount
        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        // Getter and Setter for products
        public List<Product> getProducts() {
            return products;
        }

        public void setProducts(List<Product> products) {
            this.products = products;
        }

        // Nested class for Product
        public static class Product {
            private String id;
            private String name;
            private String imageUrl;
            private int quantity;
            private int price;

            // Getter and Setter for id
            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            // Getter and Setter for name
            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            // Getter and Setter for imageUrl
            public String getImageUrl() {
                return imageUrl;
            }

            public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
            }

            // Getter and Setter for quantity
            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }

            // Getter and Setter for price
            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }
        }
    }

    // Nested class for RedirectUrls
    public static class RedirectUrls {
        private String confirmUrl;
        private String cancelUrl;

        // Getter and Setter for confirmUrl
        public String getConfirmUrl() {
            return confirmUrl;
        }

        public void setConfirmUrl(String confirmUrl) {
            this.confirmUrl = confirmUrl;
        }

        // Getter and Setter for cancelUrl
        public String getCancelUrl() {
            return cancelUrl;
        }

        public void setCancelUrl(String cancelUrl) {
            this.cancelUrl = cancelUrl;
        }
    }
}

