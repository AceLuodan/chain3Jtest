package com.ethjava;

import com.ethjava.utils.Environment;
import org.web3moac.protocol.Web3moac;
import org.web3moac.protocol.core.methods.response.Web3ClientVersion;
import org.web3moac.protocol.http.HttpService;

import java.io.IOException;


/**
 * 快速开始
 */
public class QuickStart {

	private static Web3moac web3moac;





    public static void main(String[] args) {
		web3moac = Web3moac.build(new HttpService(Environment.RPC_URL));

		Web3ClientVersion web3ClientVersion = null;
		try {
			web3ClientVersion = web3moac.web3ClientVersion().send();
			String clientVersion = web3ClientVersion.getWeb3ClientVersion();
			System.out.println("clientVersion " + clientVersion);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
