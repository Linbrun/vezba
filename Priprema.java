package vezba;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Priprema extends Frame {
    private Label srecno = new Label("SRECNO");
    private TextArea oblast = new TextArea();
    public Priprema(){
        super("KLIJENT");
        setSize(500,500);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        dodajElemente();
        setVisible(true);
    }
    public void dodajElemente(){
        setLayout(new GridLayout(5,1));
        Panel prvi = new Panel();
        prvi.setLayout(new BorderLayout());
        srecno.setAlignment(Label.CENTER);
        srecno.setForeground(Color.red);
        prvi.add("North",srecno);
        PrvaNit nit1 = new PrvaNit(srecno);
        Checkbox cb = new Checkbox("Animacija",true);
        prvi.add("West",cb);
        cb.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if(cb.getState()){
                    nit1.pokreniNit();
                }
                else {
                    nit1.zaustaviNit();
                }
            }
        });
        add(prvi);
        Panel drugi = new Panel();
        drugi.setLayout(new GridLayout(3,1));
        CheckboxGroup grupa = new CheckboxGroup();
        Label labela = new Label("Zahtev");
        Checkbox rb1 = new Checkbox("Vreme",grupa,false);
        Checkbox rb2 = new Checkbox("Poruka",grupa,true);
        drugi.add(labela);
        drugi.add(rb1);
        drugi.add(rb2);
        add(drugi);

        Panel treci = new Panel();
        treci.setLayout(new GridLayout(3,1));
        TextField tekstPoruke = new TextField(10);
        treci.add(tekstPoruke);
        Checkbox elvis = new Checkbox("Elvis");
        Checkbox tom = new Checkbox("Tom");
        treci.add(elvis);
        treci.add(tom);
        add(treci);

        Panel cetvrti = new Panel();
        Button zahtev = new Button("SALJI ZAHTEV");
        zahtev.setBackground(Color.red);
        zahtev.setForeground(Color.yellow);
        cetvrti.add(zahtev);
        zahtev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                final int TCP_PORT = 1111;
                try {
                    InetAddress addr = InetAddress.getByName("127.0.0.1");
                    Socket sock = new Socket(addr,TCP_PORT);
                    BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                    PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sock.getOutputStream())),true);
                    if(rb1.getState()){
                        System.out.println("[Client]:VREME");
                        out.println("VREME");
                        String response = in.readLine();
                        System.out.println("[Server]: " + response);
                        oblast.insert("[S] Vreme: " + response + "\n",oblast.getText().length());
                    }
                    else if(rb2.getState()){
                        String prk = tekstPoruke.getText();
                        String kome = "";
                        if(elvis.getState()){
                            kome = "ELVIS";
                        }
                        else {
                            kome = "TOM";
                        }
                        String konacnaPoruak = "PORUKA:" + kome + ":" + prk;
                        out.println(konacnaPoruak);
                        System.out.println("[Client]: " + konacnaPoruak);
                        String response = in.readLine();
                        System.out.println("[Server]: " + response);
                        oblast.insert(response + "\n", oblast.getText().length());
                    }

                    in.close();
                    out.close();
                    sock.close();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        add(cetvrti);
        add(oblast);
    }

    public static void main(String[] args) {
        Priprema priprema = new Priprema();
    }
}
