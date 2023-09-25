package SmartCard;
import javacard.security.MessageDigest;
import javacard.framework.ISOException;
import javacard.framework.ISO7816;
import javacard.framework.JCSystem;
import javacard.framework.Util;

public class Pin {
    private final byte[] PIN_DEFAULT = new byte[]{(byte)0x31,(byte)0x32,(byte)0x33,(byte)0x34,(byte)0x35,(byte)0x36};
    private static final short MAX_TRY = (short)3;
    private byte[] pin;
    private byte remaining;
    private final MessageDigest messageDigest;

    public Pin() {
        this.pin = new byte[16];
        this.remaining = MAX_TRY;
        this.messageDigest = MessageDigest.getInstance(MessageDigest.ALG_MD5, false);
        this.messageDigest.doFinal(PIN_DEFAULT, (short)0, (short)PIN_DEFAULT.length, pin, (short) 0);
    }

    public boolean checkPin(byte[] buf, short start, short len) {
        if (this.remaining <= 0) return false;
        byte[] hashBuf = new byte[16];
        this.messageDigest.reset();
        this.messageDigest.doFinal(buf, start, len, hashBuf, (short)0);
        if (Util.arrayCompare(hashBuf, (short)0, pin, (short) 0, (short) pin.length) == 0) {
            this.remaining = MAX_TRY;
            return true;
        } else {
            this.remaining--;
            return false;
        }
    }

    public boolean updatePin(byte[] buf, short start, short len) {
        if (len < (short) 1) {
            ISOException.throwIt(ISO7816.SW_WRONG_DATA);
        }
        this.messageDigest.reset();
        this.messageDigest.doFinal(buf, (short) start, len, pin, (short) 0);
        this.remaining = MAX_TRY;
        return true;
    }
    
    public void resetPin() {
	    this.messageDigest.doFinal(PIN_DEFAULT, (short)0, (short)PIN_DEFAULT.length, pin, (short) 0);
	    this.remaining = MAX_TRY;
    }

    public byte[] getPin() {
        return this.pin;
    }

    public byte getRemaining() {
        return this.remaining;
    }
}
