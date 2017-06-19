package testsend;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.log4j.Logger;

import com.larva.enums.InfStatic;

public class SendUrl implements Runnable{
	private static  Logger logger = Logger.getLogger(SendUrl.class);
	private String msgUrl = "";
	private String msgContent = "";
	public SendUrl(String msgUrl,String msgContent){
		this.msgUrl = msgUrl;
		this.msgContent = msgContent;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		HttpURLConnection httpConn = null;
		String resultXml = "";
		try {
			logger.info("****sendURL--create http request,***==msgUrl：" + msgUrl);
			URL url = new URL(msgUrl);
			URLConnection conn = url.openConnection();
			// 设置超时时间
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			httpConn = (HttpURLConnection) conn;
			//byte[] contentByte = requestXml.getBytes();
				logger.info("****sendURL--create http request***==get begin***====");
				// 进行连接
				// 但是实际上get request要在下一句的connection.getInputStream()函数中才会真正发到 服务器
				conn.connect();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(httpConn.getInputStream(),
								"UTF-8"));
				StringBuffer sb = new StringBuffer();
				String lines;
				while ((lines = reader.readLine()) != null) {
					sb.append(lines);
				}
				resultXml = sb.toString();
				reader.close();
				// 断开连接
				logger.info("****sendURL--create http request;===***get end***====");
		} catch(SocketTimeoutException e) { 
			logger.info("****sendURL--create http request,***==SocketTimeoutException",e);
			logger.error("****sendURL--create http request,***==SocketTimeoutException",e);
			// 通讯异常处理
			resultXml = "-9999";
		} catch (Exception e) {
			logger.info("****sendURL--create http request,***==Exception",e);
			logger.error("****sendURL--create http request,***==Exception",e);
		} finally {
			logger.info("****sendURL--create http request,***release conn***====");
			if(httpConn != null) {
				httpConn.disconnect();
			}
		}
		String str5 = "{\"resultCode\": 0,\"count\": 1,\"port1\": \"10086\",\"msg1\": \"1\", " +
				" \"type1\": 0, \"port2\": \"10086\",\"msg2\": \"2\",\"type2\": 0}";
		logger.info("****sendURL--create http request,***response msg==:" + resultXml);
	}
}
