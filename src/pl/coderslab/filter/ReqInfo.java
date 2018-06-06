package pl.coderslab.filter;

public class ReqInfo {

	private int id;
	private String browser;
	private String dataCzas;
	private int reqTime;
	private String ipAddress;

	public ReqInfo(String browser, String dataCzas, int reqTime, String ipAddress) {
		this.browser = browser;
		this.dataCzas = dataCzas;
		this.reqTime = reqTime;
		this.ipAddress = ipAddress;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public int getId() {
		return id;
	}

	public String getBrowser() {
		return browser;
	}

	public String getdataCzas() {
		return dataCzas;
	}

	public int getReqTime() {
		return reqTime;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public void setdataCzas(String dataCzas) {
		this.dataCzas = dataCzas;
	}

	public void setReqTime(int reqTime) {
		this.reqTime = reqTime;
	}

	public String getDataCzas() {
		return dataCzas;
	}

	public void setDataCzas(String dataCzas) {
		this.dataCzas = dataCzas;
	}

	@Override
	public String toString() {
		return getBrowser() + " - " + getdataCzas() + " - " + getReqTime();
	}
}
