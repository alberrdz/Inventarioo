package com.deshielo.inventarioo;

public class Producto {

    String prodId;
    String pUsuario, pUsername, pHostname, pUbicacion, pMarca, pModelo, pAnoMan, pNumSer, pProcesador, pVelocidad, pRam, pDD, pDisco, pWinVersion, pIdiomaSO, pMicroVersion, pAntivirus, pSoftware;
    public Producto(){

    }

    public Producto(String prodId, String pUsuario, String pUsername, String pHostname, String pUbicacion, String pMarca, String pModelo, String pAnoMan, String pNumSer, String pProcesador, String pVelocidad, String pRam, String pDD, String pDisco, String pWinVersion, String pIdiomaSO, String pMicroVersion, String pAntivirus, String pSoftware) {
        this.prodId = prodId;
        this.pUsuario = pUsuario;
        this.pUsername = pUsername;
        this.pHostname = pHostname;
        this.pUbicacion = pUbicacion;
        this.pMarca = pMarca;
        this.pModelo = pModelo;
        this.pAnoMan = pAnoMan;
        this.pNumSer = pNumSer;
        this.pProcesador = pProcesador;
        this.pVelocidad = pVelocidad;
        this.pRam = pRam;
        this.pDD = pDD;
        this.pDisco = pDisco;
        this.pWinVersion = pWinVersion;
        this.pIdiomaSO = pIdiomaSO;
        this.pMicroVersion = pMicroVersion;
        this.pAntivirus = pAntivirus;
        this.pSoftware = pSoftware;
    }

    public String getProdId() {
        return prodId;
    }

    public String getpUsuario() {
        return pUsuario;
    }

    public String getpUsername() {
        return pUsername;
    }

    public String getpHostname() {
        return pHostname;
    }

    public String getpUbicacion() {
        return pUbicacion;
    }

    public String getpMarca() {
        return pMarca;
    }

    public String getpModelo() { return pModelo; }

    public String getpAnoMan() {
        return pAnoMan;
    }

    public String getpNumSer() {
        return pNumSer;
    }

    public String getpProcesador() {
        return pProcesador;
    }

    public String getpVelocidad() {
        return pVelocidad;
    }

    public String getpRam() {
        return pRam;
    }

    public String getpDD() {
        return pDD;
    }

    public String getpDisco() {
        return pDisco;
    }

    public String getpWinVersion() {
        return pWinVersion;
    }

    public String getpIdiomaSO() {
        return pIdiomaSO;
    }

    public String getpMicroVersion() {
        return pMicroVersion;
    }

    public String getpAntivirus() {
        return pAntivirus;
    }

    public String getpSoftware() {
        return pSoftware;
    }
}
