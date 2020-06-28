package com.ethjava.sol;

import com.ethjava.utils.Environment;
import org.web3j.contracts.IMOKToken;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;

import java.math.BigInteger;

public class SolSample {
	public static void main(String[] args) {
		deploy();
		use();
	}

	private static void deploy() {
		Web3j web3j = Web3j.build(new HttpService(Environment.RPC_URL));
		Credentials credentials = null;//可以根据私钥生成

		RemoteCall<IMOKToken> deploy = IMOKToken.deploy(web3j, credentials,
				new DefaultGasProvider());
		try {
			IMOKToken tokenERC20 = deploy.send();
			tokenERC20.isValid();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void use() {
		Web3j web3j = Web3j.build(new HttpService(Environment.RPC_URL));
		String contractAddress = null;
		Credentials credentials = null;//可以根据私钥生成
		IMOKToken contract = IMOKToken.load(contractAddress, web3j, credentials,
				Convert.toWei("10", Convert.Unit.GWEI).toBigInteger(),
				BigInteger.valueOf(100000));
		String myAddress = null;
		String toAddress = null;
		BigInteger amount = BigInteger.ONE;
		try {
			BigInteger balance = contract.balanceOf(myAddress).send();
			TransactionReceipt receipt = contract.transfer(toAddress, amount).send();
			//etc..
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
