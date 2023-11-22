package com.web.selenium;

import java.util.Date;

public class rough {
	public static void main(String args[]) {
		String result = "{confirmed=true, metadata={UDNS_ID=09bfbb31-9ed9-4918-bf2d-f6ae790b2c26, UDNS_THROTTLE=true, VCG_RTRV=1}, metadataTimestamp=2023-11-21T17:13:46.979Z, prompted=false, purposes={Advertising=Auto, Analytics=Auto, Functional=Auto, SaleOfInfo=true}, timestamp=2023-11-21T17:13:31.449Z, updated=true}";
		if (result.contains("SaleOfInfo=true") || result.contains("auto")) {
			System.out.println("SaleOfInfo=true");
		} else {
			System.out.println("SaleOfInfo=false");
		}

		Date dt = new Date();
		String dateStamp = dt.toString();
		System.out.println(dateStamp);
	}

}
