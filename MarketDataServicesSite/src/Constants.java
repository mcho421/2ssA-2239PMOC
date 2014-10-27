
public enum Constants {
	instance;
	
	public String serviceBase;
	public String namespace;
	private String currencyConvertServiceName;
	public String currencyConvertMarketDataResponseName;
	public String currencyConvertMarketDataRequestName;

	
	private Constants() {
        String isGrieg = System.getenv("grieg");
        if (isGrieg != null) {
        	System.out.println("'grieg' environment variable set. Using production settings");
        	System.setProperty("http.proxyHost", "tlan184.srvr.cse.edu.au");
    		System.setProperty("http.proxyPort", "80");
    		// Note, change url to "CurrencyConvertService" in tlan184
//			serviceBase = "http://tlan184.srvr:8080/axis2/services";
        	serviceBase = "http://vcas720.srvr.cse.unsw.edu.au/axis2/services";

        } else {
        	System.out.println("'grieg' environment variable NOT set. Using development settings");
//        	serviceBase = "http://localhost:8080/axis2/services";
        	serviceBase = "http://vcas720.srvr.cse.unsw.edu.au/axis2/services";
		}
		currencyConvertServiceName = "CurrencyConvertService";
//		currencyConvertMarketDataResponseName = "currencyConvertMarketDataResponse";
		currencyConvertMarketDataResponseName = "currenyConvertMarketDataResponse";
//		currencyConvertMarketDataRequestName = "currencyConvertMarketData";
		currencyConvertMarketDataRequestName = "currenyConvertMarketData";


        namespace = "http://sltf.unsw.edu.au/services";

	}
	
	public String getImportDownloadUrl() {
		return serviceBase + "/ImportDownloadServices?wsdl";
	}
	
	public String getCurrencyConvertUrl() {
		return serviceBase + "/" + currencyConvertServiceName + "?wsdl";
	}
	
	public String getSummaryUrl() {
		return serviceBase + "/SummaryMarketDataService?wsdl";
	}

}
