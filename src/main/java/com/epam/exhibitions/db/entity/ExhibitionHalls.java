package com.epam.exhibitions.db.entity;

public class ExhibitionHalls {
    private int id_exhibition;
    private boolean HALL1;
    private boolean HALL2;
    private boolean HALL3;
    private boolean HALL4;
    private boolean HALL5;

    public ExhibitionHalls(int id_exhibition,boolean HALL1,boolean HALL2,boolean HALL3,boolean HALL4,boolean HALL5){
        this.id_exhibition=id_exhibition;
        this.HALL1=HALL1;
        this.HALL2=HALL2;
        this.HALL3=HALL3;
        this.HALL4=HALL4;
        this.HALL5=HALL5;
    }

    public int getId_exhibition() {
        return id_exhibition;
    }

    public boolean isHALL1() {
        return HALL1;
    }

    public boolean isHALL2() {
        return HALL2;
    }

    public boolean isHALL3() {
        return HALL3;
    }

    public boolean isHALL4() {
        return HALL4;
    }

    public boolean isHALL5() {
        return HALL5;
    }
}
