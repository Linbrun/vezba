package vezba;

import java.awt.*;

public class PrvaNit extends Thread {
    private String srecno1 = "SRECNO";
    private String srecno2 = "S  R  E  C  N  O";
    private Label label;
    private boolean spojeno;
    private volatile boolean radi;
    public PrvaNit(Label labela){
        this.label = labela;
        radi = true;
        spojeno = true;
        start();
    }
    public void pokreniNit(){
        this.radi = true;
    }
    public void zaustaviNit(){
        this.radi = false;
    }

    @Override
    public void run() {
        while(true){
            if(radi){
                if(spojeno){
                    label.setText(srecno1);
                    spojeno = false;
                }
                else {
                    label.setText(srecno2);
                    spojeno = true;
                }
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
