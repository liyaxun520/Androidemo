package com.example.http.entity;

public class StoreInfoEntity {


    /**
     * store_info : {"id":"ShopID","name":"名称"}
     */

    private StoreInfoBean store_info;

    public StoreInfoBean getStore_info() {
        return store_info;
    }

    public void setStore_info(StoreInfoBean store_info) {
        this.store_info = store_info;
    }

    public static class StoreInfoBean {
        /**
         * id : ShopID
         * name : 名称
         */

        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "StoreInfoBean{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
