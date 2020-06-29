package com.ethjava;

import com.ethjava.utils.Environment;
import org.web3moac.abi.EventEncoder;
import org.web3moac.abi.TypeReference;
import org.web3moac.abi.datatypes.Address;
import org.web3moac.abi.datatypes.Event;
import org.web3moac.abi.datatypes.generated.Uint256;
import org.web3moac.protocol.Web3moac;
import org.web3moac.protocol.core.DefaultBlockParameterName;
import org.web3moac.protocol.core.methods.request.EthFilter;
import org.web3moac.protocol.core.methods.response.EthLog;
import org.web3moac.protocol.http.HttpService;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

/**
 * Event log相关
 * 监听合约event
 */
public class ContractEvent {
	private static String contractAddress = "0x4c1ae77bc2df45fb68b13fa1b4f000305209b0cb";
	private static Web3moac web3moac;

	public static void main(String[] args) {
		web3moac = Web3moac.build(new HttpService(Environment.RPC_URL));
		/**
		 * 监听ERC20 token 交易
		 */
		EthFilter filter = new EthFilter(
				DefaultBlockParameterName.EARLIEST,
				DefaultBlockParameterName.LATEST,
				contractAddress);
		Event event = new Event("Transfer",
				Arrays.<TypeReference<?>>asList(
						new TypeReference<Address>(true) {
						},
						new TypeReference<Address>(true) {
						}, new TypeReference<Uint256>(false) {
						}
				)
		);

		String topicData = EventEncoder.encode(event);
		filter.addSingleTopic(topicData);
		System.out.println(topicData);


        try{
	        EthLog log= web3moac.ethGetFilterLogs(new BigInteger("500000")).send();
	        System.out.println(log);
        }catch (Exception e){

        }

	}
}
