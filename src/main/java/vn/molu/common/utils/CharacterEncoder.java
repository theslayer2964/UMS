package vn.molu.common.utils;

import java.io.*;
/*
	Các đối tượng abstract đóng vai trò là khai niệm chung để được các class khác kế thừa để phát triển cho tiết hơn khái niệm đó
	Ví dụ: abstract class Sport(){ abtract void hinhDangquaBong();}
	class Football extend Sport{(abtract void hinhDangquaBong(){return "tron"}}
*/

// Lớp này cung cấp các phương thức để mã hóa dữ liệu từ một luồng đầu vào (input stream) thành
// dạng mã hóa và ghi vào một luồng đầu ra (output stream).
public abstract class CharacterEncoder {
	// Số byte cần mã hóa cho một Atom
	abstract int bytesPerAtom();
	// Số byte tối a cho mỗi dòng mã hóa
	abstract int bytesPerLine();
	// Phương thức trừu tượng này được sử dụng để mã hóa một "atom" (đơn vị mã hóa) dữ liệu và ghi vào luồng đầu ra.
	abstract void encodeAtom(OutputStream outputstream, byte abyte0[], int i,
			int j) throws IOException;
	// Phương thức này nhận một luồng đầu vào và một luồng đầu ra, và thực hiện mã hóa dữ liệu từ luồng đầu vào và ghi vào luồng đầu ra.
	public void encodeBuffer(InputStream inputstream, OutputStream outputstream)
			throws IOException {
		byte abyte0[] = new byte[bytesPerLine()];
		encodeBufferPrefix(outputstream);
		int j;
		do {
			j = readFully(inputstream, abyte0);
			if (j == -1)
				break;
			encodeLinePrefix(outputstream, j);
			for (int i = 0; i < j; i += bytesPerAtom())
				if (i + bytesPerAtom() <= j)
					encodeAtom(outputstream, abyte0, i, bytesPerAtom());
				else
					encodeAtom(outputstream, abyte0, i, j - i);

			encodeLineSuffix(outputstream);
		} while (j >= bytesPerLine());
		encodeBufferSuffix(outputstream);
	}
	// Phương thức này nhận một mảng byte và trả về một chuỗi biểu diễn mã hóa của dữ liệu trong mảng byte đó
	public String encodeBuffer(byte abyte0[]) {
		ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
		ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(
				abyte0);
		try {
			encodeBuffer(((InputStream) (bytearrayinputstream)),
					((OutputStream) (bytearrayoutputstream)));
		} catch (Exception _ex) {
			throw new Error("ChracterEncoder::encodeBuffer internal error");
		}
		String s;
		try {
			s = bytearrayoutputstream.toString("UTF8");
		} catch (UnsupportedEncodingException _ex) {
			throw new Error("CharacterEncoder::encodeBuffer internal error(2)");
		}
		return s;
	}
	// Phương thức này nhận một mảng byte và một luồng đầu ra, và mã hóa dữ liệu trong mảng byte và ghi vào luồng đầu ra.
	public void encodeBuffer(byte abyte0[], OutputStream outputstream)
			throws IOException {
		ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(
				abyte0);
		encodeBuffer(((InputStream) (bytearrayinputstream)), outputstream);
	}
	// Phương thức này được gọi trước khi bắt đầu mã hóa dữ liệu từ luồng đầu vào.
	void encodeBufferPrefix(OutputStream outputstream) throws IOException {
		pStream = new PrintStream(outputstream);
	}

	void encodeBufferSuffix(OutputStream outputstream) throws IOException {
	}

	void encodeLinePrefix(OutputStream outputstream, int i) throws IOException {
	}

	void encodeLineSuffix(OutputStream outputstream) throws IOException {
		pStream.println();
	}
	// Phương thức này trả về số lượng byte đã đọc được từ luồng đầu vào và gán vào mảng byte đích.
	protected static int readFully(InputStream inputstream, byte abyte0[])
			throws IOException {
		for (int i = 0; i < abyte0.length; i++) {
			int j = inputstream.read();
			if (j == -1)
				return i;
			abyte0[i] = (byte) j;
		}

		return abyte0.length;
	}

	protected PrintStream pStream;
}