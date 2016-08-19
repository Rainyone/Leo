package com.larva.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class IPSeeker {
	private static String DbPath = "/qqwry.dat";
	private MappedByteBuffer buffer;
	private HashMap<String, IPLocation> cache = new HashMap<String, IPLocation>();
	private int ipBegin;
	private int ipEnd;
	static IPSeeker ipSeeker = null;

	public static IPSeeker getInstance() {
		String path = IPSeeker.class.getResource("/").toString();
		if(path.startsWith("file")) {
			path = path.substring(5);  
		}
		if (path.indexOf("%20") > 0) {
			path = path.replace("%20", " ");
		}
		path.replace("/", File.separator);

		if (ipSeeker == null) {
			return new IPSeeker(new File(path + DbPath));
		}
		return ipSeeker;
	}

	public IPSeeker(File file) {
		try {
			this.buffer = new RandomAccessFile(file, "r").getChannel().map(
					FileChannel.MapMode.READ_ONLY, 0L, file.length());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (this.buffer.order().toString()
				.equals(ByteOrder.BIG_ENDIAN.toString())) {
			this.buffer.order(ByteOrder.LITTLE_ENDIAN);
		}

		this.ipBegin = readInt(0);
		this.ipEnd = readInt(4);

		if ((this.ipBegin == -1) || (this.ipEnd == -1))
			System.out.println("IP地址信息文件格式有错误，IP显示功能将无法使用");
	}

	public String getAddress(String ip) {
		IPLocation cache = getIpLocation(ip);
		return cache.getAreaId() + " " + cache.getCountry() + " " + cache.getArea();
	}

	public String getCountry(String ip) {
		IPLocation cache = getIpLocation(ip);
		return cache.getCountry();
	}

	public String getArea(String ip) {
		IPLocation cache = getIpLocation(ip);
		return cache.getArea();
	}

	public IPLocation getIpLocation(String ip) {
		IPLocation ipLocation = null;
		try {
			if (this.cache.get(ip) != null) {
				return (IPLocation) this.cache.get(ip);
			}
			ipLocation = getIPLocation(getIpByteArrayFromString(ip));
			if (ipLocation != null)
				this.cache.put(ip, ipLocation);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (ipLocation == null) {
			ipLocation = new IPLocation();
			ipLocation.setCountry("未知国家");
			ipLocation.setArea("未知地区");
		}
		return ipLocation;
	}

	public List<IPEntry> getIPEntries(String s) {
		List<IPEntry> ret = new ArrayList<IPEntry>();
		byte[] b4 = new byte[4];
		int endOffset = this.ipEnd + 4;
		for (int offset = this.ipBegin + 4; offset <= endOffset; offset += 7) {
			int temp = readInt3(offset);

			if (temp != -1) {
				IPLocation loc = getIPLocation(temp);

				if ((loc.getCountry().indexOf(s) != -1)
						|| (loc.getArea().indexOf(s) != -1)) {
					IPEntry entry = new IPEntry();
					entry.country = loc.getCountry();
					entry.area = loc.getArea();

					readIP(offset - 4, b4);
					entry.beginIp = getIpStringFromBytes(b4);

					readIP(temp, b4);
					entry.endIp = getIpStringFromBytes(b4);

					ret.add(entry);
				}
			}
		}
		return ret;
	}

	private IPLocation getIPLocation(byte[] ip) {
		IPLocation info = null;
		int offset = locateIP(ip);
		if (offset != -1) {
			info = getIPLocation(offset);
		}
		return info;
	}

	private int readInt(int offset) {
		this.buffer.position(offset);
		return this.buffer.getInt();
	}

	private int readInt3(int offset) {
		this.buffer.position(offset);
		return this.buffer.getInt() & 0xFFFFFF;
	}

	private String readString(int offset) {
		try {
			byte[] buf = new byte[100];
			this.buffer.position(offset);

			int i = 0;
			for (buf[i] = this.buffer.get(); buf[i] != 0;)
				buf[(++i)] = this.buffer.get();

			if (i != 0)
				return getString(buf, 0, i, "GBK");
		} catch (IllegalArgumentException localIllegalArgumentException) {
		}
		return "";
	}

	private void readIP(int offset, byte[] ip) {
		this.buffer.position(offset);
		this.buffer.get(ip);
		byte temp = ip[0];
		ip[0] = ip[3];
		ip[3] = temp;
		temp = ip[1];
		ip[1] = ip[2];
		ip[2] = temp;
	}

	private int compareIP(byte[] ip, byte[] beginIp) {
		for (int i = 0; i < 4; i++) {
			int r = compareByte(ip[i], beginIp[i]);
			if (r != 0) {
				return r;
			}
		}
		return 0;
	}

	private int compareByte(byte b1, byte b2) {
		if ((b1 & 0xFF) > (b2 & 0xFF)) {
			return 1;
		}
		if ((b1 ^ b2) == 0) {
			return 0;
		}
		return -1;
	}

	private int locateIP(byte[] ip) {
		int m = 0;

		byte[] b4 = new byte[4];

		readIP(this.ipBegin, b4);
		int r = compareIP(ip, b4);
		if (r == 0)
			return this.ipBegin;
		if (r < 0) {
			return -1;
		}

		int i = this.ipBegin;
		for (int j = this.ipEnd; i < j;) {
			m = getMiddleOffset(i, j);
			readIP(m, b4);
			r = compareIP(ip, b4);

			if (r > 0)
				i = m;
			else if (r < 0) {
				if (m == j) {
					j -= 7;
					m = j;
				} else {
					j = m;
				}
			} else
				return readInt3(m + 4);

		}

		m = readInt3(m + 4);
		readIP(m, b4);
		r = compareIP(ip, b4);
		if (r <= 0) {
			return m;
		}
		return -1;
	}

	private int getMiddleOffset(int begin, int end) {
		int records = (end - begin) / 7;
		records >>= 1;
		if (records == 0) {
			records = 1;
		}
		return begin + records * 7;
	}

	private IPLocation getIPLocation(int offset) {
		IPLocation loc = new IPLocation();

		this.buffer.position(offset + 4);

		byte b = this.buffer.get();
		if (b == 1) {
			int countryOffset = readInt3();

			this.buffer.position(countryOffset);

			b = this.buffer.get();
			if (b == 2) {
				loc.setCountry(readString(readInt3()));
				this.buffer.position(countryOffset + 4);
			} else {
				loc.setCountry(readString(countryOffset));
			}

			loc.setArea(readArea(this.buffer.position()));
		} else if (b == 2) {
			loc.setCountry(readString(readInt3()));
			loc.setArea(readArea(offset + 8));
		} else {
			loc.setCountry(readString(this.buffer.position() - 1));
			loc.setArea(readArea(this.buffer.position()));
		}
		
		loc.setAreaId(setAreaId(loc.getCountry()));
		return loc;
	}

	/**
	 * <p><b>Title:</b> setAreaId</p>
	 * <p><b>Description:</b> 设置编码</p>
	 * @author douzi
	 */
	private String setAreaId(String country) {
		country = country.replace("省", "").replace("市", "").replace("内蒙古", "内蒙");
		country = country.substring(0, country.length() > 4 ? 4 : country.length());
		return IPArea.get(country) == null ? "0" : IPArea.get(country);
	}

	private String readArea(int offset) {
		this.buffer.position(offset);
		byte b = this.buffer.get();
		if ((b == 1) || (b == 2)) {
			int areaOffset = readInt3();
			if (areaOffset == 0) {
				return "未知地区";
			}
			return readString(areaOffset);
		}

		return readString(offset);
	}

	private int readInt3() {
		return this.buffer.getInt() & 0xFFFFFF;
	}

	private static byte[] getIpByteArrayFromString(String ip) throws Exception {
		byte[] ret = new byte[4];
		StringTokenizer st = new StringTokenizer(ip, ".");
		try {
			ret[0] = ((byte) (Integer.parseInt(st.nextToken()) & 0xFF));
			ret[1] = ((byte) (Integer.parseInt(st.nextToken()) & 0xFF));
			ret[2] = ((byte) (Integer.parseInt(st.nextToken()) & 0xFF));
			ret[3] = ((byte) (Integer.parseInt(st.nextToken()) & 0xFF));
		} catch (Exception e) {
			throw e;
		}
		return ret;
	}

	private static String getString(byte[] b, int offset, int len,
			String encoding) {
		try {
			return new String(b, offset, len, encoding);
		} catch (UnsupportedEncodingException e) {
		}
		return new String(b, offset, len);
	}

	private static String getIpStringFromBytes(byte[] ip) {
		StringBuffer sb = new StringBuffer();
		sb.append(ip[0] & 0xFF);
		sb.append('.');
		sb.append(ip[1] & 0xFF);
		sb.append('.');
		sb.append(ip[2] & 0xFF);
		sb.append('.');
		sb.append(ip[3] & 0xFF);
		return sb.toString();
	}

	public static void main(String[] args) {
		ipSeeker = getInstance();

		System.out.println(ipSeeker.getAddress("111.124.34.119"));
		System.out.println(ipSeeker.getAddress("112.113.138.181"));
		System.out.println(ipSeeker.getAddress("222.217.9.1"));
		System.out.println(ipSeeker.getAddress("61.167.154.3"));
		System.out.println(ipSeeker.getAddress("36.62.67.84"));
		System.out.println(ipSeeker.getAddress("116.252.120.6"));
		System.out.println(ipSeeker.getAddress("116.112.177.69"));
		System.out.println(ipSeeker.getAddress("60.13.173.252"));
	}
}