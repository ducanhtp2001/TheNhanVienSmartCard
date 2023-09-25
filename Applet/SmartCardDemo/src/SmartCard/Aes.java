package SmartCard;
import javacard.framework.*;
import javacardx.crypto.Cipher;
import javacard.security.*;


public class Aes {
	private AESKey aesKey;
	private Cipher cipher;
	private final short keyLen = (short)16;
	private byte[] temp;
	
	public Aes() {
		temp = JCSystem.makeTransientByteArray(keyLen, JCSystem.CLEAR_ON_RESET);
		this.cipher = Cipher.getInstance(Cipher.ALG_AES_BLOCK_128_ECB_NOPAD, false);
		this.aesKey = (AESKey)KeyBuilder.buildKey(KeyBuilder.TYPE_AES, (short)(8*keyLen), false);
	}
	
	public void setKey(byte[] keyData) {
		if(keyData.length < keyLen) ISOException.throwIt(ISO7816.SW_WRONG_LENGTH);
		this.aesKey.setKey(keyData, (short)0);
	}
	
	public void encrypt(byte[] in, short inStart, short len, byte[] out, short outStart) {
		if(len < (short)1) ISOException.throwIt(ISO7816.SW_WRONG_LENGTH);
		short surplus = (short) (len % keyLen);
		short outLen = (short) (len / keyLen);
		cipher.init(this.aesKey, Cipher.MODE_ENCRYPT);
		if(surplus == (short)0) {
			cipher.doFinal(in, inStart, len, out, outStart);
		} else {
			outLen++;
			cipher.update(in, inStart, len, out, outStart);
			cipher.doFinal(temp, (short)0, (short)(keyLen - surplus), out, outStart);
		}
	}
	
	public void decrypt(byte[] in, short inStart, short len, byte[] out, short outStart) {
		if((short)(len % keyLen) != (short)0) ISOException.throwIt(ISO7816.SW_WRONG_LENGTH);
		cipher.init(this.aesKey, Cipher.MODE_DECRYPT);
		cipher.doFinal(in, inStart, len, out, outStart);
	}
}
