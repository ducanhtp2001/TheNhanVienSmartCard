/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Connection;
import java.util.Arrays;
import java.util.List;
import javax.smartcardio.*;

/**
 *
 * @author HP
 */
public class TestConnectCard {
    public static final byte[] AID_APPLET = {(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00};
    private Card card;
    private TerminalFactory factory;
    private CardChannel channel;
    private CardTerminal terminal;
    private List<CardTerminal> terminals;
    private ResponseAPDU response;

    public TestConnectCard() {
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        TestConnectCard sm = new TestConnectCard();
        sm.connectCard();
    }
    
    public boolean connectCard() {
        try {
            factory = TerminalFactory.getDefault();
            terminals = factory.terminals().list();
            terminal = terminals.get(0);
            card = terminal.connect("T=0");
            channel = card.getBasicChannel();
            
            if(channel == null) {
                System.out.println("chanel = null");
                return false;
            }
            byte[] data = {(byte)0x00, (byte)0x01, (byte)0x02, (byte)0x03};
            byte[] data1 = {(byte)'a', (byte)'ê', (byte)'d', (byte)'b'};
            System.out.println(Integer.toHexString((int)'a').toUpperCase());
            System.out.println("" + (char)0xEA);
            
            response = channel.transmit(new CommandAPDU(0x00, (byte)0xA4, 0x04, 0x00, AID_APPLET));
            response = channel.transmit(new CommandAPDU(0x00, (byte)0x00, 0x00, 0x00, data1));
            String check;
            response = channel.transmit(new CommandAPDU(0x00, (byte)0x01, 0x00, 0x00));
            byte[] res = response.getData();    
            check = Integer.toHexString(response.getSW());
            if(check.equals("9000")) {
                System.out.println("9000");
                System.out.println("Data read: " + Arrays.toString(res));
                return true;
            }
            
            if(check.equals("6400")) {
                System.out.println("6400");
                return false;
            }
        } catch (Exception e) {
            
        }
        
        return false;
    }
    
}
