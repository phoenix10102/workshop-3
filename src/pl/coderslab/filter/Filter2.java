package pl.coderslab.filter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter("/*")
public class Filter2 implements Filter {

	public Filter2() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpReq = (HttpServletRequest) request;
		Date date = new Date();

		String browserDetails = httpReq.getHeader("User-Agent");
		String user = browserDetails.toLowerCase();
		String os = "";
		String browser = "";


		if (user.indexOf("windows") >= 0) {
			os = "Windows";
		} else if (user.indexOf("x11") >= 0) {
			os = "Unix";
		} else if (user.indexOf("android") >= 0) {
			os = "Android";
		} else if (user.indexOf("iphone") >= 0) {
			os = "IPhone";
		} else if (user.indexOf("mac") >= 0) {
			os = "Mac";
		} else {
			os = "UnKnown";
		}
		
		
		if (browserDetails.contains("Chrome")) {
			browser = "Chrome";
		} else if (browserDetails.contains("Firefox")) {
			browser = "FireFox";
		} else if (browserDetails.contains("Opera")) {
			browser = "Opera";
		} else if (browserDetails.contains("Safari")) {
			browser = "Safari";
		} else {
			browser = "UnKnown";
		}

		String przeglad = os + " - " + browser;
		String dateTime = date.toString();
		long startTime = System.currentTimeMillis();
		chain.doFilter(request, response);
		long stopTime = System.currentTimeMillis();
		int czasZadania = (int) (stopTime - startTime);
		String ipAddress = httpReq.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}

		try {
			ReqInfoDao.save(newReqInfo(przeglad, dateTime, czasZadania, ipAddress));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ReqInfo newReqInfo(String browser, String dateTime, int reqTime, String ipAddress) {
		return new ReqInfo(browser, dateTime, reqTime, ipAddress);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
