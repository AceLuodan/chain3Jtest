package com.ethjava;

import com.ethjava.utils.Environment;
import org.web3moac.protocol.Web3moac;
import org.web3moac.protocol.core.methods.response.*;
import org.web3moac.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;

public class EthInfo {
	private static Web3moac web3moac;

	public static void main(String[] args) {

//		Web3jConfig web3jConfig = new Web3jConfig();
//		web3moac = Web3moac.build(web3jConfig.buildService(Environment.RPC_URL));
		web3moac = Web3moac.build(new HttpService(Environment.RPC_URL));
		getEthInfo();
	}

	/**
	 * 请求区块链的信息
	 */
	private static void getEthInfo() {

		Web3ClientVersion web3ClientVersion = null;
		try {
			//客户端版本
			web3ClientVersion = web3moac.web3ClientVersion().send();
			String clientVersion = web3ClientVersion.getWeb3ClientVersion();
			System.out.println("clientVersion " + clientVersion);
			//区块数量
			EthBlockNumber ethBlockNumber = web3moac.ethBlockNumber().send();
			BigInteger blockNumber = ethBlockNumber.getBlockNumber();
			System.out.println(blockNumber);
			//挖矿奖励账户
			EthCoinbase ethCoinbase = web3moac.ethCoinbase().send();
			String coinbaseAddress = ethCoinbase.getAddress();
			System.out.println(coinbaseAddress);
			//是否在同步区块
			EthSyncing ethSyncing = web3moac.ethSyncing().send();
			boolean isSyncing = ethSyncing.isSyncing();
			System.out.println(isSyncing);
			//是否在挖矿
			EthMining ethMining = web3moac.ethMining().send();
			boolean isMining = ethMining.isMining();
			System.out.println(isMining);
			//当前gas price
			EthGasPrice ethGasPrice = web3moac.ethGasPrice().send();
			BigInteger gasPrice = ethGasPrice.getGasPrice();
			System.out.println(gasPrice);
			//挖矿速度
			EthHashrate ethHashrate = web3moac.ethHashrate().send();
			BigInteger hashRate = ethHashrate.getHashrate();
			System.out.println(hashRate);
			//协议版本
			EthProtocolVersion ethProtocolVersion = web3moac.ethProtocolVersion().send();
			String protocolVersion = ethProtocolVersion.getProtocolVersion();
			System.out.println(protocolVersion);
			//连接的节点数
			NetPeerCount netPeerCount = web3moac.netPeerCount().send();
			BigInteger peerCount = netPeerCount.getQuantity();
			System.out.println(peerCount);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
