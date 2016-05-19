public class Counter {
    int antal_kvinnor;
    int antal_man;
    int antal_hen;
    int kvinno_dubbel;
    int man_dubbel;
    int hen_dubbel;
    public Counter() {
    	this.antal_kvinnor = 0;
        this.antal_man = 0;
        this.antal_hen = 0;
        this.kvinno_dubbel = 0;
        this.man_dubbel = 0;
        this.hen_dubbel = 0;
    }
    public int getKvinna() {
        return antal_kvinnor - kvinno_dubbel;
    }
    public int getMan() {
        return antal_man - man_dubbel;
    }
    public int getHen() {
        return antal_hen - hen_dubbel;
    }
}