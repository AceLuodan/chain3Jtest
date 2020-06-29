package com.ethjava;

import com.ethjava.utils.Environment;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import org.web3moac.protocol.Web3moac;
import org.web3moac.protocol.core.DefaultBlockParameter;
import org.web3moac.protocol.core.methods.response.EthBlock;
import org.web3moac.protocol.http.HttpService;
import rx.Subscription;

import java.math.BigInteger;

/**
 * filter相关
 * 监听区块、交易
 * 所有监听都在Web3jRx中
 */
public class Filter {
	private static Web3moac web3moac;

	public static void main(String[] args) {
		web3moac = Web3moac.build(new HttpService(Environment.RPC_URL));
		/**
		 * 新区块监听
		 */
		newBlockFilter(web3moac);
		/**
		 * 新交易监听
		 */
		newTransactionFilter(web3moac);
		/**
		 * 遍历旧区块、交易
		 */
		replayFilter(web3moac);
		/**
		 * 从某一区块开始直到最新区块、交易
		 */
		catchUpFilter(web3moac);

		/**
		 * 取消监听
		 */
		//subscription.unsubscribe();
	}

	private static void newBlockFilter(Web3moac web3moac) {
		Disposable subscription = web3moac.
				blockFlowable(false).
				subscribe(block -> {
					System.out.println("new block come in");
					System.out.println("block number" + block.getBlock().getNumber());
				});
	}

	private static void newTransactionFilter(Web3moac web3moac) {
		Disposable subscription = web3moac.transactionFlowable().
				subscribe(transaction -> {
					System.out.println("transaction come in");
					System.out.println("transaction txHash " + transaction.getHash());
				});
	}

	private static void replayFilter(Web3moac web3moac) {
		BigInteger startBlock = BigInteger.valueOf(2000000);
		BigInteger endBlock = BigInteger.valueOf(2010000);
		/**
		 * 遍历旧区块
		 */
		Disposable subscription = web3moac.
				replayPastBlocksFlowable(
						DefaultBlockParameter.valueOf(startBlock),
						DefaultBlockParameter.valueOf(endBlock),
						false).
				subscribe(ethBlock -> {
					System.out.println("replay block");
					System.out.println(ethBlock.getBlock().getNumber());
				});

		/**
		 * 遍历旧交易
		 */
		Disposable subscription1 = web3moac.replayPastTransactionsFlowable(
						DefaultBlockParameter.valueOf(startBlock),
						DefaultBlockParameter.valueOf(endBlock)).
				subscribe(transaction -> {
					System.out.println("replay transaction");
					System.out.println("txHash " + transaction.getHash());
				});
	}

	private static void catchUpFilter(Web3moac web3moac) {
		BigInteger startBlock = BigInteger.valueOf(2000000);

		/**
		 * 遍历旧区块，监听新区块
		 */
		Disposable subscription = web3moac.replayPastAndFutureBlocksFlowable(
				DefaultBlockParameter.valueOf(startBlock), false)
				.subscribe(block -> {
					System.out.println("block");
					System.out.println(block.getBlock().getNumber());
				});

		/**
		 * 遍历旧交易，监听新交易
		 */
		Disposable subscription2 = web3moac.replayPastTransactionsFlowable(
				DefaultBlockParameter.valueOf(startBlock))
				.subscribe(tx -> {
					System.out.println("transaction");
					System.out.println(tx.getHash());
				});
	}
}
