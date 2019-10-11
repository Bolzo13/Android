package com.example.databaseadmin;

public class Ordini {

        private Integer cliente;

        private String data;

        private String ordine ;
        private double totale;

        public int getCliente() {
            return cliente;
        }

        public void setCliente(int cliente) {
            this.cliente = cliente;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getOrdine() {
            return ordine;
        }

        public void setOrdine(String ordine) {
            this.ordine = ordine;
        }

        public double getTotale() {
            return totale;
        }

        public void setTotale(double totale) {
            this.totale = totale;
        }


}
