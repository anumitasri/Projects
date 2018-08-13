package server;


import java.util.Currency;

import javax.jws.soap.SOAPBinding;

import wsnetx.CurrencyConvertor;
import wsnetx.CurrencyConvertorSoap;
import wsnetx1.StockQuote;
import wsnetx1.StockQuoteSoap;

	@javax.jws.WebService
	@SOAPBinding(style= SOAPBinding.Style.RPC)
	public class StockServer {
	static double IBM1,AAPL1,BA1,HD1,KO1,totalstockvalue,finalCurrencyvalue;
	static String a,b,c,d,s,z,y,x,t,u,v;

	@javax.jws.WebMethod
	public static String getQuote(java.lang.String symbol)
	{
	        StockQuote service = new StockQuote();
	        StockQuoteSoap port = service.getStockQuoteSoap();
	        s=port.getQuote("IBM");
	        System.out.println(s);
	        z=s.substring(46,52);
	        IBM1=Double.parseDouble(z);
	        return s;
	}

	@javax.jws.WebMethod
	public static String getQuote_1(java.lang.String symbol)
	{
	        StockQuote service = new StockQuote();
	        StockQuoteSoap port = service.getStockQuoteSoap();
	        a=port.getQuote("HD");
	        System.out.println(a);
	        x=a.substring(45,51);
	        HD1=Double.parseDouble(x);
	        return a;
	}
	@javax.jws.WebMethod
	public static String getQuote_2(java.lang.String symbol)
	{
	        StockQuote service = new StockQuote();
	        StockQuoteSoap port = service.getStockQuoteSoap();
	        b=port.getQuote("AAPL");
	        System.out.println(b);
	        y=b.substring(47,53);
	        AAPL1=Double.parseDouble(y);
	        return b;
}
	@javax.jws.WebMethod
	public static String getQuote_3(java.lang.String symbol)
	{
	        StockQuote service = new StockQuote();
	        StockQuoteSoap port = service.getStockQuoteSoap();
	        c=port.getQuote("BA");
	        System.out.println(c);
	        u=c.substring(45,51);
	        BA1=Double.parseDouble(u);
	        return c;
	}
	@javax.jws.WebMethod
	public static String getQuote_4(java.lang.String symbol)
	{
	        StockQuote service = new StockQuote();
	        StockQuoteSoap port = service.getStockQuoteSoap();
	        d=port.getQuote("KO");
	        System.out.println(d);
	        v=d.substring(45,50);
	        KO1=Double.parseDouble(v);
	        return d;
	}


	@javax.jws.WebMethod
	public static double conversionRate(Currency fromCurrency, Currency toCurrency)
	{
	        CurrencyConvertor service = new CurrencyConvertor();
	        CurrencyConvertorSoap port = service.getCurrencyConvertorSoap();
	        return port.conversionRate(fromCurrency, toCurrency);
	}

	@javax.jws.WebMethod
	public static double finalStockValue(double a)
	{
	        totalstockvalue=(IBM1+AAPL1+BA1+HD1+KO1)*1000;
	        finalCurrencyvalue = a * totalstockvalue;
	        return finalCurrencyvalue;
	}
	}
	