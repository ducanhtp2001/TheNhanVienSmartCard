package SmartCard;

import javacard.framework.*;
// import javacardx.apdu.ExtendedLength;

public class SmartCard extends Applet
{
	
	final static short SW_LEN_OUT = (short) 0x9101;
	final static short SW_LOCK = (short) 0x9201;
	private final short SW_UPDATE_FALSE = (short)0x9102;
	
	final static byte INS_PIN_UPDATE = (byte) 0x00;
	final static byte INS_PIN_CHECK = (byte) 0x01;
	final static byte INS_INPUT = (byte) 0x02;
	final static byte INS_OUTPUT = (byte) 0x03;
	final static byte INS_IN_OUT_AVATAR = (byte) 0x04;
	final static byte INS_RESET_PIN = (byte) 0x05;
	final static byte INS_CHECK_RESET_PIN = (byte) 0x06;
	
	//khai bao p1
	final static byte P1_ID = (byte) 0x00;
	final static byte P1_FULLNAME = (byte) 0x01;
	final static byte P1_BIRTHDAY = (byte) 0x02;
	final static byte P1_ADDRESS = (byte) 0x03;
	final static byte P1_NUMBERPHONE = (byte) 0x04;
	final static byte P1_GMAIL = (byte) 0x05;
	final static byte P1_POSITION = (byte) 0x06;
	final static byte P1_DEPARTMENT = (byte) 0x07;
	final static byte P1_GENDER = (byte) 0x08;
	final static byte P1_CARDID = (byte) 0x09;
	
	final static byte P1_IN_AVATAR = (byte) 0x00;
	final static byte P1_OUT_AVARTAR = (byte) 0x01;
	
	private Pin pin;
	private Aes aes;
	
	// khai bao pin
	boolean isLock;
	boolean isFistUpdatePin;


	
	// khai bao thong tin
	byte[] id, cardId, fullName, birthDay, address, numberPhone, gmail, avatar, position, department, gender;
	short idLen, cardIdLen, fullNameLen, birthDayLen, addressLen, numberPhoneLen, gmailLen, avatarLen, positionLen, departmentLen, genderLen;
	final static short MAX_SIZE = (short)3072;

	public static void install(byte[] bArray, short bOffset, byte bLength) 
	{
		new SmartCard(bArray, bOffset, bArray[bOffset]);
	}
	
	private SmartCard(byte[] bArray, short bOffset, byte bLength) {
		register();
		isLock = false;
		isFistUpdatePin = true;
		
		pin = new Pin();
		aes = new Aes();
		
		avatar = new byte[MAX_SIZE];
		avatarLen = 0;
	}

	public void process(APDU apdu)
	{
		if (selectingApplet())
		{
			return;
		}
		byte[] buf = apdu.getBuffer();
		if(buf[ISO7816.OFFSET_INS] == INS_RESET_PIN) {
			apdu.setIncomingAndReceive();
			pin.resetPin();
			return;
		}
		if(buf[ISO7816.OFFSET_INS] == INS_CHECK_RESET_PIN) {
			byte len = (byte)apdu.setIncomingAndReceive();
			
			if(!pin.checkPin(buf, ISO7816.OFFSET_CDATA, len)) {
				byte remain = pin.getRemaining();
				if(remain <= (byte)0) {
				isLock = true;
				ISOException.throwIt(SW_LOCK);
				} else ISOException.throwIt(ISO7816.SW_WRONG_DATA);
			} else isLock = false;
			
			return;
		}
		if(isLock) ISOException.throwIt(SW_LOCK);
		switch (buf[ISO7816.OFFSET_INS])
		{
		case (byte)INS_PIN_UPDATE:
			byte len = (byte)apdu.setIncomingAndReceive();
			updatePin(buf, len);
			break;
		case (byte)INS_PIN_CHECK:
			byte len1 = (byte)apdu.setIncomingAndReceive();
			checkPin(apdu, buf, len1);
			break;
		case (byte)INS_INPUT:
			input(apdu);
			break;
		case (byte)INS_OUTPUT:
			apdu.setIncomingAndReceive();
			output(apdu, buf);
			break;
		case (byte)INS_IN_OUT_AVATAR:
			switch(buf[ISO7816.OFFSET_P1]) {
				case P1_IN_AVATAR:
					inputAvatar(apdu);
					break;
				case P1_OUT_AVARTAR:
					outputAvatar(apdu);
					break;
				default:
					ISOException.throwIt(ISO7816.SW_INCORRECT_P1P2);
			}
			
			break;
		default:
			ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
		}
	}
	
	public void updatePin(byte[] buf, short len) {
		JCSystem.beginTransaction();
		if(!pin.updatePin(buf, ISO7816.OFFSET_CDATA, len)) ISOException.throwIt(SW_UPDATE_FALSE);
		if(isFistUpdatePin) {
			this.aes.setKey(this.pin.getPin());
			isFistUpdatePin = false;
		}
		JCSystem.commitTransaction();
	}
	
	public void checkPin(APDU apdu, byte[] buf, byte len) {
		if(!pin.checkPin(buf, ISO7816.OFFSET_CDATA, len)) {
			byte remain = pin.getRemaining();
			if(remain <= (byte)0) {
				isLock = true;
				ISOException.throwIt(SW_LOCK);
			}
			else ISOException.throwIt(ISO7816.SW_WRONG_DATA);
		}
	}
	
	public void input(APDU apdu) {
		byte[] buf = apdu.getBuffer();
		short dataLen = apdu.setIncomingAndReceive();
		short quotient = (short)(dataLen / (short)16);
		short surplus = (short)(dataLen % (short)16);
		if(surplus != (short)0) quotient++;
		switch(buf[ISO7816.OFFSET_P1]) {
			case (byte)P1_ID:
				idLen = dataLen;
				id = new byte[(short)(quotient * (short)16)];
				this.aes.encrypt(buf, ISO7816.OFFSET_CDATA, idLen, id, (short)0);
				break;
			case (byte)P1_FULLNAME:
				fullNameLen = dataLen;
				fullName = new byte[(short)(quotient * (short)16)];
				this.aes.encrypt(buf, ISO7816.OFFSET_CDATA, fullNameLen, fullName, (short)0);
				break;
			case (byte)P1_BIRTHDAY:
				birthDayLen = dataLen;
				birthDay = new byte[(short)(quotient * (short)16)];
				this.aes.encrypt(buf, ISO7816.OFFSET_CDATA, birthDayLen, birthDay, (short)0);
				break;
			case (byte)P1_ADDRESS:
				addressLen = dataLen;
				address = new byte[(short)(quotient * (short)16)];
				this.aes.encrypt(buf, ISO7816.OFFSET_CDATA, addressLen, address, (short)0);
				break;
			case (byte)P1_NUMBERPHONE:
				numberPhoneLen = dataLen;
				numberPhone = new byte[(short)(quotient * (short)16)];
				this.aes.encrypt(buf, ISO7816.OFFSET_CDATA, numberPhoneLen, numberPhone, (short)0);
				break;
			case (byte)P1_GMAIL:
				gmailLen = dataLen;
				gmail = new byte[(short)(quotient * (short)16)];
				this.aes.encrypt(buf, ISO7816.OFFSET_CDATA, gmailLen, gmail, (short)0);
				break;
			case (byte)P1_POSITION:
				positionLen = dataLen;
				position = new byte[(short)(quotient * (short)16)];
				this.aes.encrypt(buf, ISO7816.OFFSET_CDATA, positionLen, position, (short)0);
				break;
			case (byte)P1_DEPARTMENT:
				departmentLen = dataLen;
				department = new byte[(short)(quotient * (short)16)];
				this.aes.encrypt(buf, ISO7816.OFFSET_CDATA, departmentLen, department, (short)0);
				break;
			case (byte)P1_GENDER:
				genderLen = dataLen;
				gender = new byte[(short)(quotient * (short)16)];
				this.aes.encrypt(buf, ISO7816.OFFSET_CDATA, genderLen, gender, (short)0);
				break;
			case (byte)P1_CARDID:
				cardIdLen = dataLen;
				cardId = new byte[cardIdLen];
				this.aes.encrypt(buf, ISO7816.OFFSET_CDATA, cardIdLen, cardId, (short)0);
				break;
			default:
			ISOException.throwIt(ISO7816.SW_INCORRECT_P1P2);
		}
	}
	
	public void output(APDU apdu, byte[] buf) {
		
		apdu.setOutgoing();
		switch(buf[ISO7816.OFFSET_P1]) {
			case (byte)P1_ID:
				this.aes.decrypt(id, (short)0, (short)id.length, buf, (short)0);
				apdu.setOutgoingLength(idLen);
				apdu.sendBytesLong(buf, (short)0, idLen);
				break;
			case (byte)P1_FULLNAME:
				this.aes.decrypt(fullName, (short)0, (short)fullName.length, buf, (short)0);
				apdu.setOutgoingLength(fullNameLen);
				apdu.sendBytesLong(buf, (short)0, fullNameLen);
				break;
			case (byte)P1_BIRTHDAY:
				this.aes.decrypt(birthDay, (short)0, (short)birthDay.length, buf, (short)0);
				apdu.setOutgoingLength(birthDayLen);
				apdu.sendBytesLong(buf, (short)0, birthDayLen);
				break;
			case (byte)P1_ADDRESS:
				this.aes.decrypt(address, (short)0, (short)address.length, buf, (short)0);
				apdu.setOutgoingLength(addressLen);
				apdu.sendBytesLong(buf, (short)0, addressLen);
				break;
			case (byte)P1_NUMBERPHONE:
				this.aes.decrypt(numberPhone, (short)0, (short)numberPhone.length, buf, (short)0);
				apdu.setOutgoingLength(numberPhoneLen);
				apdu.sendBytesLong(buf, (short)0, numberPhoneLen);
				break;
			case (byte)P1_GMAIL:
				this.aes.decrypt(gmail, (short)0, (short)gmail.length, buf, (short)0);
				apdu.setOutgoingLength(gmailLen);
				apdu.sendBytesLong(buf, (short)0, gmailLen);
				break;
			case (byte)P1_POSITION:
				this.aes.decrypt(position, (short)0, (short)position.length, buf, (short)0);
				apdu.setOutgoingLength(positionLen);
				apdu.sendBytesLong(buf, (short)0, positionLen);
				break;
			case (byte)P1_DEPARTMENT:
				this.aes.decrypt(department, (short)0, (short)department.length, buf, (short)0);
				apdu.setOutgoingLength(departmentLen);
				apdu.sendBytesLong(buf, (short)0, departmentLen);
				break;
			case (byte)P1_GENDER:
				this.aes.decrypt(gender, (short)0, (short)gender.length, buf, (short)0);
				apdu.setOutgoingLength(genderLen);
				apdu.sendBytesLong(buf, (short)0, genderLen);
				break;
			case (byte)P1_CARDID:
				this.aes.decrypt(cardId, (short)0, (short)cardId.length, buf, (short)0);
				apdu.setOutgoingLength(cardIdLen);
				apdu.sendBytesLong(buf, (short)0, cardIdLen);
				break;
			default:
			ISOException.throwIt(ISO7816.SW_INCORRECT_P1P2);
		}
	}

	public void inputAvatar(APDU apdu) {
		byte[] buf = apdu.getBuffer();
		short len = apdu.setIncomingAndReceive();
		
		if(buf[ISO7816.OFFSET_P2] == (byte)0) {
			Util.arrayCopy(buf, ISO7816.OFFSET_CDATA, avatar, (short)0, len);
			avatarLen = len;
		} else {
			Util.arrayCopy(buf, ISO7816.OFFSET_CDATA, avatar, avatarLen, len);
			avatarLen += len;
		}
		
	}
	
	public void outputAvatar(APDU apdu) {
		byte[] buf = apdu.getBuffer();
		apdu.setIncomingAndReceive();
		byte time = buf[ISO7816.OFFSET_P2];
		short p2 = (short)buf[ISO7816.OFFSET_P2];
		short start = (short)(p2 * (short)255);
		short len = (short)255;
		if(start > avatarLen) ISOException.throwIt(SW_LEN_OUT);
		else {
			if((short)(start + len) > avatarLen) len = (short)(avatarLen - start);
		}
		apdu.setOutgoing();
		apdu.setOutgoingLength(len);
		Util.arrayCopy(avatar, start, buf, (short)0, len);
		apdu.sendBytes((short)0, len);
	}
}
