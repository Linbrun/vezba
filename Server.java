package vezba;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

public class Server {
    public static final int TCP_PORT = 1111;
    static ArrayList<Osoba> lista = new ArrayList<Osoba>();
    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(TCP_PORT);
            System.out.println("Server je pokrenut");
            while(true){
                Socket sock = ss.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sock.getOutputStream())),true);
                String request = in.readLine();
                if(request.equals("VREME")){
                    Date vreme = new Date();
                    System.out.println("[Zahtev]: VREME");
                    System.out.println("[odgovor][S]Vreme: " + vreme.getHours() + ":" + vreme.getMinutes() + ":" + vreme.getSeconds());
                    String strVreme = vreme.getHours() + ":" + vreme.getMinutes() + ":" + vreme.getSeconds();
                    out.println(strVreme);
                }
                else {
                    String nizPoruka[] = request.split(":");
                    System.out.println(request);
                    String mess = nizPoruka[2];
                    String odkoga = nizPoruka[1];
                    Osoba o = new Osoba(nizPoruka[1],nizPoruka[2]);
                    lista.add(o);
                    if(mess.equals("?")){
                        System.out.println(mess);
                        Osoba sacuvana = lista.get(lista.size()-2);
                        if(odkoga.equals(sacuvana.ime)){
                            System.out.println("SACUVANA PORUKA: " + sacuvana.poruka);
                            out.println(sacuvana.poruka);
                        }
                        else {
                            out.println(".");
                        }
                    }
                    else {
                        out.println(nizPoruka[2]);
                    }
                }
                in.close();
                out.close();
                sock.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
